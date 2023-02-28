package com.neonusa.kp.ui.challenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.neonusa.kp.MainActivity
import com.neonusa.kp.databinding.ActivityQuizFinishBinding

class ChallengeFinishActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CORRECT_SUM = "EXTRASUM";
        const val EXTRA_INCORRECT_SUM = "QUIESTIONSUM"
        const val EXTRA_UNANSWERED = "UNANSWERED"
        const val EXTRA_CHOSEN_ANSWER = "CHOSEN_ANSWER"
    }

    private lateinit var binding: ActivityQuizFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val correctAnswerSum = intent.getIntExtra(EXTRA_CORRECT_SUM, 0);
        val incorrectSum = intent.getIntExtra(EXTRA_INCORRECT_SUM,0);
        val unAnswerred = intent.getIntExtra(EXTRA_UNANSWERED,0);
        val chosenAnswer = intent.getIntArrayExtra(EXTRA_CHOSEN_ANSWER)

        //todo : pakai gerbang if untuk logika nilai (bergantung jumlah soal rumus beda2) (next update)
        val scoreValue = correctAnswerSum * 5

        // 5 ini nanti fleksibel insyaa allah

        with(binding){

            btnRestart.setOnClickListener {
                MaterialDialog(this@ChallengeFinishActivity).show {
                    title(text = "Selesai")
                    message(text = "Apakah kamu yakin ingin mengulangi tantangan?")
                    negativeButton(text = "Tidak")
                    positiveButton(text = "Ya") {
                        val intent = Intent(this@ChallengeFinishActivity, ChallengeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            btnBack.setOnClickListener {
                MaterialDialog(this@ChallengeFinishActivity).show {
                    title(text = "Halaman Utama")
                    message(text = "Kembali ke halaman utama?")
                    negativeButton(text = "Batal")
                    positiveButton(text = "Ya") {
                        val intent = Intent(this@ChallengeFinishActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            btnStudy.setOnClickListener {
                MaterialDialog(this@ChallengeFinishActivity).show {
                    title(text = "Pembahasan")
                    message(text = "Ke halaman pembahasan?")
                    negativeButton(text = "Batal")
                    positiveButton(text = "Ya") {
                        val intent = Intent(this@ChallengeFinishActivity, ChallengeStudyActivity::class.java)
                        intent.putExtra(ChallengeStudyActivity.CHOSEN_ANSWER_EXTRA, chosenAnswer)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            txtCorrectValue.text = correctAnswerSum.toString()
            txtQuestionIncorrectSum.text = incorrectSum.toString()
            txtNotAnswered.text = unAnswerred.toString()
            txtScoreValue.text = "+$scoreValue Exp"
        }
    }


}