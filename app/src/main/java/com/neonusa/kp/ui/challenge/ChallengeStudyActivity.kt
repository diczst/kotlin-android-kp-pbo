package com.neonusa.kp.ui.challenge

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.neonusa.kp.MainActivity
import com.neonusa.kp.R
import com.neonusa.kp.data.model.Soal
import com.neonusa.kp.databinding.ActivityQuizStudyBinding

class ChallengeStudyActivity : AppCompatActivity() {

    companion object {
        const val CHOSEN_ANSWER_EXTRA = "CHOSEN_ANSWER"
    }

    private lateinit var chosenAnswer: IntArray

    private lateinit var viewModel: ChallengeViewModel
    private lateinit var binding: ActivityQuizStudyBinding
    private var questionSequence = 0
    private var listSoal = ArrayList<Soal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ChallengeViewModel::class.java]
        chosenAnswer = intent.getIntArrayExtra(CHOSEN_ANSWER_EXTRA)!!
        listSoal.addAll(viewModel.getListSoal())

        showQuestion()

        binding.btnGoHome.setOnClickListener {
            MaterialDialog(this).show {
                title(text = "Halaman Utama")
                message(text = "Kembali ke halaman utama?")
                negativeButton(text = "Batal")
                positiveButton(text = "Ya") {
                    val intent = Intent(this@ChallengeStudyActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        binding.btnNextStudy.setOnClickListener {
            if(questionSequence < listSoal.size - 1){
//                optionChosen()
                questionSequence++

                if (questionSequence == listSoal.size - 1) {
//                    binding.btnFinish.visibility = View.VISIBLE
                } else {
//                    binding.btnFinish.visibility = View.INVISIBLE
                }

                showQuestion()
            }
        }

        binding.btnPrevStudy.setOnClickListener {
            if(questionSequence > 0){
//                optionChosen()
                questionSequence--

                if(questionSequence < listSoal.size - 1){
//                    binding.btnFinish.visibility = View.INVISIBLE;
                }
                showQuestion()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showQuestion(){
        val currentSoal = listSoal[questionSequence]
        with(binding){
            radioGroupStudy.clearCheck();
            txtQuestionNumberStudy.text = "Soal ke-" + (questionSequence + 1) + " dari " + listSoal.size
            txtQuestionStudy.text = currentSoal.soal
            txtStudy.text = currentSoal.pembahasan
            rdAStudy.text = currentSoal.option_a
            rdBStudy.text = currentSoal.option_b
            rdCStudy.text = currentSoal.option_c
            rdDStudy.text = currentSoal.option_d

            when(currentSoal.jawaban){
                0 -> {
                    rdAStudy.setTextColor(Color.parseColor("#77d632"))
                    rdBStudy.setTextColor(Color.BLACK)
                    rdCStudy.setTextColor(Color.BLACK)
                    rdDStudy.setTextColor(Color.BLACK)
                }
                1 -> {
                    rdAStudy.setTextColor(Color.BLACK)
                    rdBStudy.setTextColor(Color.parseColor("#77d632"))
                    rdCStudy.setTextColor(Color.BLACK)
                    rdDStudy.setTextColor(Color.BLACK)
                }
                2 -> {
                    rdAStudy.setTextColor(Color.BLACK)
                    rdBStudy.setTextColor(Color.BLACK)
                    rdCStudy.setTextColor(Color.parseColor("#77d632"))
                    rdDStudy.setTextColor(Color.BLACK)
                }
                3 -> {
                    rdAStudy.setTextColor(Color.BLACK)
                    rdBStudy.setTextColor(Color.BLACK)
                    rdCStudy.setTextColor(Color.BLACK)
                    rdDStudy.setTextColor(Color.parseColor("#77d632"))
                }
            }

            when(chosenAnswer[questionSequence]){
                0 -> radioGroupStudy.check(R.id.rd_a_study)
                1 -> radioGroupStudy.check(R.id.rd_b_study)
                2 -> radioGroupStudy.check(R.id.rd_c_study)
                3 -> radioGroupStudy.check(R.id.rd_d_study)
            }
        }
    }


}