package com.neonusa.kp.ui.challenge

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.neonusa.kp.MainActivity
import com.neonusa.kp.R
import com.neonusa.kp.databinding.ActivityQuizFinishBinding

class ChallengeFinishActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CORRECT_SUM = "EXTRASUM";
        const val EXTRA_INCORRECT_SUM = "QUIESTIONSUM"
        const val EXTRA_UNANSWERED = "UNANSWERED"
        const val EXTRA_CHOSEN_ANSWER = "CHOSEN_ANSWER"
        const val EXTRA_ID_TANTANGAN = "TANTANGAN_ID"
        const val EXTRA_TOTAL_SOAL = "TOTAL_SOAL"
        const val UNLOCK_STATUS = "UNLOCK_STATUS"
    }

    private lateinit var binding: ActivityQuizFinishBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val correctAnswerSum = intent.getIntExtra(EXTRA_CORRECT_SUM, 0)
        val incorrectSum = intent.getIntExtra(EXTRA_INCORRECT_SUM,0)
        val unAnswerred = intent.getIntExtra(EXTRA_UNANSWERED,0)
        val chosenAnswer = intent.getIntArrayExtra(EXTRA_CHOSEN_ANSWER)
        val tantanganId = intent.getIntExtra(EXTRA_ID_TANTANGAN,0)
        val totalSoal = intent.getIntExtra(EXTRA_TOTAL_SOAL, 0)
        val unlockStatus = intent.getStringExtra(UNLOCK_STATUS)


        if(correctAnswerSum == totalSoal && unlockStatus.equals("new_tantangan")){
            binding.lottiemation.setAnimation(R.raw.new_tantangan)
            mediaPlayer = MediaPlayer.create(this, Uri.parse("android.resource://" + packageName + "/" + R.raw.win))
            mediaPlayer.start()
            binding.tvStatus.text = "Selamat kamu telah membuka tantangan baru"
        } else if(correctAnswerSum == totalSoal && unlockStatus.equals("new_materi")){
            binding.lottiemation.setAnimation(R.raw.book)
            mediaPlayer = MediaPlayer.create(this, Uri.parse("android.resource://" + packageName + "/" + R.raw.win))
            mediaPlayer.start()
            binding.tvStatus.text = "Selamat kamu telah membuka materi baru"
        } else {
            binding.lottiemation.setAnimation(R.raw.xp)
            mediaPlayer = MediaPlayer.create(this, Uri.parse("android.resource://" + packageName + "/" + R.raw.exp))
            mediaPlayer.start()
            binding.tvStatus.text = "Kamu mendapatkan exp"
        }

        val scoreValue = correctAnswerSum * 5

        with(binding){
            btnRestart.setOnClickListener {
                MaterialDialog(this@ChallengeFinishActivity).show {
                    title(text = "Selesai")
                    message(text = "Apakah kamu yakin ingin mengulangi tantangan?")
                    negativeButton(text = "Tidak")
                    positiveButton(text = "Ya") {
                        val intent = Intent(this@ChallengeFinishActivity, ChallengeActivity::class.java)
                        intent.putExtra(ChallengeActivity.TANTANGAN_ID, tantanganId)
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
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
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
                        intent.putExtra(ChallengeStudyActivity.TANTANGAN_ID, tantanganId)
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