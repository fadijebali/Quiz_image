package com.example.quiz_image

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Modèle de données pour une question du quiz.
 */
data class Question(
    val text: String,
    val imageResId: Int,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val contentDescription: String
)

class MainActivity : AppCompatActivity() {

    // Widgets de l'interface
    private lateinit var txtScore: TextView
    private lateinit var progressQuiz: ProgressBar
    private lateinit var txtQuestionNumber: TextView
    private lateinit var imgQuestion: ImageView
    private lateinit var txtQuestion: TextView
    private lateinit var btnChoix1: Button
    private lateinit var btnChoix2: Button
    private lateinit var btnChoix3: Button
    private lateinit var txtFeedback: TextView
    private lateinit var btnNext: Button
    private lateinit var layoutQuiz: LinearLayout
    private lateinit var layoutResult: LinearLayout
    private lateinit var txtFinalScore: TextView
    private lateinit var btnRestart: Button

    // Données du quiz
    private val questions = listOf(
        Question(
            "Quelle est cette forme géométrique ?",
            R.drawable.triangle,
            listOf("Cercle", "Triangle", "Carré"),
            1,
            "Un triangle rouge"
        ),
        Question(
            "Quelle forme voyez-vous ici ?",
            R.drawable.square,
            listOf("Carré", "Rectangle", "Étoile"),
            0,
            "Un carré vert"
        ),
        Question(
            "Identifiez cette forme :",
            R.drawable.circle,
            listOf("Triangle", "Cercle", "Rectangle"),
            1,
            "Un cercle bleu"
        ),
        Question(
            "De quelle forme s'agit-il ?",
            R.drawable.rectangle,
            listOf("Étoile", "Carré", "Rectangle"),
            2,
            "Un rectangle jaune"
        ),
        Question(
            "Quelle est cette forme particulière ?",
            R.drawable.star,
            listOf("Étoile", "Cercle", "Triangle"),
            0,
            "Une étoile rose"
        )
    )

    // État du quiz
    private var currentQuestionIndex = 0
    private var score = 0
    private var isAnswered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Gestion des insets pour le mode Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialisation des vues
        initializeViews()

        // Restauration de l'état si nécessaire
        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("currentIndex", 0)
            score = savedInstanceState.getInt("score", 0)
            isAnswered = savedInstanceState.getBoolean("isAnswered", false)
        }

        // Configuration des listeners
        setupListeners()

        // Affichage de la question courante
        if (currentQuestionIndex < questions.size) {
            showQuestion()
        } else {
            showFinalResults()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentIndex", currentQuestionIndex)
        outState.putInt("score", score)
        outState.putBoolean("isAnswered", isAnswered)
    }

    private fun initializeViews() {
        txtScore = findViewById(R.id.txt_score)
        progressQuiz = findViewById(R.id.progress_quiz)
        txtQuestionNumber = findViewById(R.id.txt_question_number)
        imgQuestion = findViewById(R.id.img_question)
        txtQuestion = findViewById(R.id.txt_question)
        btnChoix1 = findViewById(R.id.btn_choix1)
        btnChoix2 = findViewById(R.id.btn_choix2)
        btnChoix3 = findViewById(R.id.btn_choix3)
        txtFeedback = findViewById(R.id.txt_feedback)
        btnNext = findViewById(R.id.btn_next)
        layoutQuiz = findViewById(R.id.layout_quiz)
        layoutResult = findViewById(R.id.layout_result)
        txtFinalScore = findViewById(R.id.txt_final_score)
        btnRestart = findViewById(R.id.btn_restart)
        
        progressQuiz.max = questions.size
    }

    private fun setupListeners() {
        btnChoix1.setOnClickListener { checkAnswer(0) }
        btnChoix2.setOnClickListener { checkAnswer(1) }
        btnChoix3.setOnClickListener { checkAnswer(2) }

        btnNext.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                showQuestion()
            } else {
                showFinalResults()
            }
        }

        btnRestart.setOnClickListener {
            restartQuiz()
        }
    }

    private fun showQuestion() {
        val question = questions[currentQuestionIndex]
        isAnswered = false

        // Mise à jour des textes et image en utilisant les ressources strings
        txtQuestionNumber.text = getString(R.string.question_number_format, currentQuestionIndex + 1, questions.size)
        imgQuestion.setImageResource(question.imageResId)
        imgQuestion.contentDescription = question.contentDescription
        txtQuestion.text = question.text
        
        btnChoix1.text = question.options[0]
        btnChoix2.text = question.options[1]
        btnChoix3.text = question.options[2]

        // Réinitialisation de l'état visuel
        setButtonsEnabled(true)
        txtFeedback.visibility = View.INVISIBLE
        btnNext.visibility = View.GONE

        // Mise à jour de la progression et du score
        progressQuiz.progress = currentQuestionIndex
        updateScoreDisplay()
    }

    private fun checkAnswer(selectedIndex: Int) {
        if (isAnswered) return
        isAnswered = true

        val question = questions[currentQuestionIndex]
        if (selectedIndex == question.correctAnswerIndex) {
            score++
            txtFeedback.text = getString(R.string.feedback_correct)
            txtFeedback.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        } else {
            txtFeedback.text = getString(R.string.feedback_wrong, question.options[question.correctAnswerIndex])
            txtFeedback.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        }

        txtFeedback.visibility = View.VISIBLE
        setButtonsEnabled(false)
        updateScoreDisplay()
        
        // La progression avance après la réponse
        progressQuiz.progress = currentQuestionIndex + 1

        // Bouton de navigation
        if (currentQuestionIndex == questions.size - 1) {
            btnNext.text = getString(R.string.see_result)
        } else {
            btnNext.text = getString(R.string.next_question)
        }
        btnNext.visibility = View.VISIBLE
    }

    private fun setButtonsEnabled(enabled: Boolean) {
        btnChoix1.isEnabled = enabled
        btnChoix2.isEnabled = enabled
        btnChoix3.isEnabled = enabled
    }

    private fun updateScoreDisplay() {
        txtScore.text = getString(R.string.score_format, score, questions.size)
    }

    private fun showFinalResults() {
        layoutQuiz.visibility = View.GONE
        layoutResult.visibility = View.VISIBLE
        txtFinalScore.text = getString(R.string.final_score_format, score, questions.size)
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        layoutQuiz.visibility = View.VISIBLE
        layoutResult.visibility = View.GONE
        showQuestion()
    }
}
