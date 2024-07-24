package com.neonusa.kp.ui.challenge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.R
import com.neonusa.kp.data.model.Soal
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.data.request.AddLevelMateriUserRequest
import com.neonusa.kp.data.request.AddLevelTantanganUserRequest
import com.neonusa.kp.data.request.TambahExpRequest
import com.neonusa.kp.databinding.ActivityQuizBinding
import com.techiness.progressdialoglibrary.ProgressDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChallengeActivity : AppCompatActivity() {
    companion object {
        const val TANTANGAN_ID = "TANTANGAN_ID"
        const val MATERI_LEVEL = "MATERI_LEVEL"
        const val TANTANGAN_TOTAL = "TANTANGAN_TOTAL"
        const val LEVEL_TANTANGAN_USER = "LEVEL_TANTANGAN_USER"
        const val LEVEL_MATERI_USER = "LEVEL_MATERI_USER"
    }

    private lateinit var progressDialog: ProgressDialog

    private lateinit var viewModel: ChallengeViewModel
    private lateinit var binding: ActivityQuizBinding
    private var questionSequence = 0

    private lateinit var chosenAnswer: IntArray
    private lateinit var correctAnswer: IntArray
    var id = 0
    private var current_level_materi = 0
    private var tantanganTotal = 0
    private var level_tantangan_user = 0
    private var level_materi_user = 0
    private var unlockStatus = ""

    var correctAnswerSum = 0
    var incorrectAnswerSum = 0
    var notAnswered = 0
    var totalSoal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)

        viewModel = ViewModelProvider(this)[ChallengeViewModel::class.java]

        id = intent.getIntExtra(TANTANGAN_ID,0)
        current_level_materi = intent.getIntExtra(MATERI_LEVEL,0)
        tantanganTotal = intent.getIntExtra(TANTANGAN_TOTAL,0)
        level_tantangan_user = intent.getIntExtra(LEVEL_TANTANGAN_USER,0)
        level_materi_user = intent.getIntExtra(LEVEL_MATERI_USER,0)

        // untuk debug
//        Toast.makeText(this@ChallengeActivity, "{$level_tantangan_user} < $tantanganTotal", Toast.LENGTH_SHORT).show()

        viewModel.getListSoal(id.toString()).observe(this){
            when (it.state) {
                Resource.State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    /*
                        -1 : belum dijawab
                        buat array int baru dengan semua elemennya bernilai -1
                    */
                    chosenAnswer = IntArray(data.size){-1}
                    correctAnswer = IntArray(data.size){-1}
                    totalSoal = data.size

                    showQuestion(data)
                    binding.btnNext.setOnClickListener {
                        if(questionSequence < data.size - 1){
                            optionChosen()
                            questionSequence++

                            if (questionSequence == data.size - 1) {
                                binding.btnFinish.visibility = View.VISIBLE
                            } else {
                                binding.btnFinish.visibility = View.INVISIBLE
                            }

                            if(questionSequence == 1){
                                binding.btnPrev.isEnabled = true
                            }
                            showQuestion(data)
                        } else {
                            Toast.makeText(this, "Sudah berada di soal terakhir", Toast.LENGTH_SHORT).show()
                        }
                    }

                    binding.btnPrev.setOnClickListener {
                        if(questionSequence > 0){
                            optionChosen()
                            questionSequence--

                            if(questionSequence < data.size - 1){
                                binding.btnFinish.visibility = View.INVISIBLE;
                            }

                            if(questionSequence == 0){
                                binding.btnPrev.isEnabled = false
                            }
                            showQuestion(data)
                        }
                    }

                    binding.btnFinish.setOnClickListener {
                        MaterialDialog(this).show {
                            title(text = "Selesai")
                            message(text = "Apakah kamu yakin ingin menyelesaikan tantangan?")
                            negativeButton(text = "Tidak")
                            positiveButton(text = "Ya") {
                                optionChosen()

                                // biar mulai dari i = 0 pakai correctAnswer.indices
                                for(i in correctAnswer.indices){
                                    Log.i("TAG", "index: $i : ${correctAnswer[i]}")
                                    if((correctAnswer[i] != -1) && (correctAnswer[i] == chosenAnswer[i])){
                                        correctAnswerSum++
                                    }

                                    if ((chosenAnswer[i] != -1)) {
                                        if (correctAnswer[i] != chosenAnswer[i]) {
                                            incorrectAnswerSum++;
                                        }
                                    }

                                    if (chosenAnswer[i] == -1) {
                                        notAnswered++;
                                    }
                                }

                                if(correctAnswerSum == data.size){
//                                    if(Kotpreference.level <= level){
//                                        Toast.makeText(this@ChallengeActivity, "menambahkan level", Toast.LENGTH_SHORT).show()
//                                        Kotpreference.addLevel(Kotpreference.level)
//                                    }
                                }

                                update(correctAnswerSum * 5)
                                val intent = Intent(this@ChallengeActivity, ChallengeFinishActivity::class.java)
                                intent.putExtra(ChallengeFinishActivity.EXTRA_CORRECT_SUM, correctAnswerSum)
                                intent.putExtra(ChallengeFinishActivity.EXTRA_INCORRECT_SUM, incorrectAnswerSum)
                                intent.putExtra(ChallengeFinishActivity.EXTRA_UNANSWERED, notAnswered)
                                intent.putExtra(ChallengeFinishActivity.EXTRA_CHOSEN_ANSWER, chosenAnswer)
                                intent.putExtra(ChallengeFinishActivity.EXTRA_ID_TANTANGAN, id)
                                intent.putExtra(ChallengeFinishActivity.EXTRA_TOTAL_SOAL, data.size)
                                intent.putExtra(ChallengeFinishActivity.UNLOCK_STATUS, unlockStatus)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                    // loading berhubungan dengan ui jadi pakai lifecycle scope
                    lifecycleScope.launch {
                        delay(3000)
                        progressDialog.dismiss()
                    }

                    //TODO: masih error masukkan data soal dari api
                    if (!data.isEmpty()) {
                    }
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

    private fun update(exp: Int) {
        val userId = Kotpreference.getUser()?.id
        val currentExp = Kotpreference.getUser()?.exp
        val totalExp = currentExp?.plus(exp)

        val newLevelTantangan = level_tantangan_user.plus(1)
        val newLevelMateri = current_level_materi.plus(1)

        val body2 = AddLevelTantanganUserRequest(
            userId ?: 0,
            newLevelTantangan
        )

        // kembalikan level tantangan jadi 1 jika update level materi
        val body3 = AddLevelMateriUserRequest(
            userId ?: 0,
            newLevelMateri,
            1
        )

        // jika user sudah di materi 2 tantangan 1 tapi ia pergi ke materi 1 tantangan 1
        // jangan update tantangannya

        // update tantangan dan materi hanya jika benar semua
        if(correctAnswerSum == totalSoal){
            if(current_level_materi >= level_materi_user){
                if(level_tantangan_user < tantanganTotal){
                    unlockStatus = "new_tantangan"
                    viewModel.updateLevelTantangan(body2).observe(this){
                        when (it.state) {
                            Resource.State.SUCCESS -> {
                                progressDialog.dismiss()
                            }

                            Resource.State.ERROR -> {
                                progressDialog.dismiss()
                            }
                            Resource.State.LOADING -> {
                                progressDialog.show()
                            }
                        }
                    }

                    // ADA BUG KALAU NGERJAIN TANTANGAN TERAKHIR DI MATERI PERTAMA SAAT MATERI KEDUA
                    // SUDAH UNLOCK DUA TANTANGAN
                    // MATERI KEDUA JADI TERLOCK KEMBALI
                } else if(level_materi_user == current_level_materi && level_tantangan_user == tantanganTotal) {
                    unlockStatus = "new_materi"

                    // percobaan kembalikan ke nol
                    Kotpreference.tryCount = 0

                    viewModel.updateLevelMateri(body3).observe(this){
                        when (it.state) {
                            Resource.State.SUCCESS -> {
                                progressDialog.dismiss()

                                // test
//                                Toast.makeText(this@ChallengeActivity, "${Kotpreference.tryCount}", Toast.LENGTH_SHORT).show()
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
        // jika tidak benar semua
        } else {
            if(Kotpreference.tryCount < 3){
                Kotpreference.tryCount++

                // test
//                Toast.makeText(this@ChallengeActivity, "${Kotpreference.tryCount}", Toast.LENGTH_SHORT).show()
            } else {
                Kotpreference.tryCount = 0
            }
        }

        tambahExp(userId,totalExp)
    }

    private fun tambahExp(userId: Int?, totalExp: Int?){
        val body = TambahExpRequest(
            userId ?: 0,
            totalExp
        )
        viewModel.tambahExp(body).observe(this) {
            when (it.state) {
                Resource.State.SUCCESS -> {
                    progressDialog.dismiss()
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

    private fun showQuestion(soals: List<Soal>){
        val currentSoal = soals[questionSequence]

        // atur jawaban yang benar
        if (correctAnswer[questionSequence] == -1) {
            correctAnswer[questionSequence] = currentSoal.jawaban
        }

        with(binding){
            radioGroup.clearCheck();
            txtQuestionNumber.text = "Soal ke-" + (questionSequence + 1) + " dari " + soals.size
            txtQuestion.text = currentSoal.soal
            rdA.text = currentSoal.option_a
            rdB.text = currentSoal.option_b
            rdC.text = currentSoal.option_c
            rdD.text = currentSoal.option_d

            when(chosenAnswer[questionSequence]){
                0 -> radioGroup.check(R.id.rd_a)
                1 -> radioGroup.check(R.id.rd_b)
                2 -> radioGroup.check(R.id.rd_c)
                3 -> radioGroup.check(R.id.rd_d)
            }
        }
    }

    private fun optionChosen(){
        with(binding){
            if (rdA.isChecked){
            chosenAnswer[questionSequence] = 0
            }

            if (rdB.isChecked){
                chosenAnswer[questionSequence] = 1
            }

            if (rdC.isChecked){
                chosenAnswer[questionSequence] = 2
            }

            if (rdD.isChecked){
                chosenAnswer[questionSequence] = 3
            }
        }
    }

}