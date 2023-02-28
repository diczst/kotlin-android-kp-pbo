package com.neonusa.kp.ui.challenge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.neonusa.kp.R
import com.neonusa.kp.data.model.Soal
import com.neonusa.kp.databinding.ActivityQuizBinding

class ChallengeActivity : AppCompatActivity() {
    private lateinit var viewModel: ChallengeViewModel
    private lateinit var binding: ActivityQuizBinding
    private var questionSequence = 0
    private var listSoal = ArrayList<Soal>()

    private lateinit var chosenAnswer: IntArray
    private lateinit var correctAnswer: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ChallengeViewModel::class.java]
        listSoal.addAll(viewModel.getListSoal())

        // -1 : belum dijawab
        // buat array int baru dengan semua elemennya bernilai -1
        chosenAnswer = IntArray(listSoal.size){-1}
        correctAnswer = IntArray(listSoal.size){-1}

        showQuestion()

        binding.btnNext.setOnClickListener {
            if(questionSequence < listSoal.size - 1){
                optionChosen()
                questionSequence++

                if (questionSequence == listSoal.size - 1) {
                    binding.btnFinish.visibility = View.VISIBLE
                } else {
                    binding.btnFinish.visibility = View.INVISIBLE
                }

                showQuestion()
            }
        }

        binding.btnPrev.setOnClickListener {
            if(questionSequence > 0){
                optionChosen()
                questionSequence--

                if(questionSequence < listSoal.size - 1){
                    binding.btnFinish.visibility = View.INVISIBLE;
                }
                showQuestion()
            }
        }

        binding.btnFinish.setOnClickListener {
            MaterialDialog(this).show {
                title(text = "Selesai")
                message(text = "Apakah kamu yakin ingin menyelesaikan tantangan?")
                negativeButton(text = "Tidak")
                positiveButton(text = "Ya") {
                    optionChosen()
                    var correctAnswerSum = 0
                    var incorrectAnswerSum = 0
                    var notAnswered = 0

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

                    val intent = Intent(this@ChallengeActivity, ChallengeFinishActivity::class.java)
                    intent.putExtra(ChallengeFinishActivity.EXTRA_CORRECT_SUM, correctAnswerSum)
                    intent.putExtra(ChallengeFinishActivity.EXTRA_INCORRECT_SUM, incorrectAnswerSum)
                    intent.putExtra(ChallengeFinishActivity.EXTRA_UNANSWERED, notAnswered)
                    intent.putExtra(ChallengeFinishActivity.EXTRA_CHOSEN_ANSWER, chosenAnswer)
                    startActivity(intent);
                }
            }
        }
    }

    private fun showQuestion(){
        val currentSoal = listSoal[questionSequence]

        // atur jawaban yang benar
        if (correctAnswer[questionSequence] == -1) {
            correctAnswer[questionSequence] = currentSoal.jawaban
        }

        with(binding){
            radioGroup.clearCheck();
            txtQuestionNumber.text = "Soal ke-" + (questionSequence + 1) + " dari " + listSoal.size
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