package com.neonusa.kp.ui.detailmateri

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.github.barteksc.pdfviewer.PDFView
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.data.request.TambahExpRequest
import com.neonusa.kp.databinding.ActivityDetailMateriBinding
import com.neonusa.kp.ui.challenge.ChallengesActivity
import com.techiness.progressdialoglibrary.ProgressDialog
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class DetailMateriActivity : AppCompatActivity() {
    var id = 0
    var materiLevel = 0
    var userMateriLevel = 0
    var userTantanganLevel = 0
    var materiNama = ""

    private lateinit var binding: ActivityDetailMateriBinding

    private lateinit var viewModel: DetailMateriViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(ChallengesActivity.MATERI_ID,0)
        materiLevel = intent.getIntExtra(ChallengesActivity.MATERI_LEVEL,0)
        materiNama = intent.getStringExtra(ChallengesActivity.MATERI_NAMA).toString()
        userMateriLevel = intent.getIntExtra(ChallengesActivity.MATERI_LEVEL_USER,0)
        userTantanganLevel = intent.getIntExtra(ChallengesActivity.TANTANGAN_LEVEL_USER,0)

        // debug
//        Toast.makeText(this, "materi level ${materiLevel}\nusertlevel ${userTantanganLevel}\nusermlevel ${userMateriLevel}", Toast.LENGTH_SHORT).show()

        progressDialog = ProgressDialog(this)
        viewModel = ViewModelProvider(this)[DetailMateriViewModel::class.java]

        binding.btnSudah.setOnClickListener {
            MaterialDialog(this).show {
                title(text = "Sudah Dibaca")
                message(text = "Tandai sebagai sudah dibaca? Kamu akan mendapatkan +10 exp dan dialihkan ke halaman tantangan")
                negativeButton(text = "Tidak")
                positiveButton(text = "Ya") {
                    update()
                }

            }
        }

        getMateriDetail()

        // baru bisa ditekan setelah minimal 1 menit
        Handler().postDelayed({
            binding.btnSudah.isEnabled = true

        },3000)
    }

    private fun getMateriDetail(){
        viewModel.getDataMateri(id.toString()).observe(this){
            when(it.state){
                Resource.State.SUCCESS -> {
                    val data = it.data
                    progressDialog.dismiss()
                    binding.tvNama.text = data?.nama.toString()
                    RetrievePDFFromURL(binding.pdfview).execute("https://neonusa.my.id/storage/pdf/${data?.konten}")

                }
                Resource.State.ERROR -> {
                    progressDialog.dismiss()
                }
                Resource.State.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }

    //todo: kasih minimal waktu baca materi misalnya 3 menit

    private fun update() {
        val userId = Kotpreference.getUser()?.id
        val currentExp = Kotpreference.getUser()?.exp
        val totalExp = currentExp?.plus(10)

        val body = TambahExpRequest(
            userId ?: 0,
            totalExp
        )

        viewModel.tambahExp(body).observe(this) {
            when (it.state) {
                Resource.State.SUCCESS -> {
                    val intent = Intent(this@DetailMateriActivity, ChallengesActivity::class.java)
                    intent.putExtra(ChallengesActivity.MATERI_ID, id)
                    intent.putExtra(ChallengesActivity.MATERI_NAMA, materiNama)
                    intent.putExtra(ChallengesActivity.MATERI_LEVEL, materiLevel)
                    intent.putExtra(ChallengesActivity.MATERI_LEVEL_USER,userMateriLevel)
                    intent.putExtra(ChallengesActivity.TANTANGAN_LEVEL_USER, userTantanganLevel)
                    startActivity(intent)
                    finish()
                }

                Resource.State.ERROR -> {
                    progressDialog.dismiss()
                }
                Resource.State.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }
}

class RetrievePDFFromURL(pdfView: PDFView) :
    AsyncTask<String, Void, InputStream>() {

    private val mypdfView: PDFView = pdfView

    // on below line we are calling our do in background method.
    override fun doInBackground(vararg params: String?): InputStream? {
        var inputStream: InputStream? = null
        try {
            val url = URL(params[0])

            val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection

            // 200 response code means response is successful
            if (urlConnection.responseCode == 200) {
                inputStream = BufferedInputStream(urlConnection.inputStream)
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            return null;
        }
        return inputStream;
    }

    override fun onPostExecute(result: InputStream?) {
        mypdfView.fromStream(result).load()
    }
}