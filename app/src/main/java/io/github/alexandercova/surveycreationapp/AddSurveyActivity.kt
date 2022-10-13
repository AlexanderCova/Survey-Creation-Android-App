package io.github.alexandercova.surveycreationapp


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddSurveyActivity : AppCompatActivity() {

    var surveyFields = arrayListOf<ArrayList<Any>>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_survey)

        val addFieldButton = findViewById<Button>(R.id.addFieldButton)

        addFieldButton.setOnClickListener {
            createField()
        }
    }

    fun createField(){
        val parent = findViewById<LinearLayout>(R.id.fieldLayout)

        val linearLayout = LinearLayout(this)
        linearLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.HORIZONTAL

        val textView = TextView(this)
        textView.text = "Field Hint:"

        val editText = EditText(this)
        editText.hint = "Field Hint"

        val typeSpinner = Spinner(this)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.survey_types,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

        typeSpinner.adapter = adapter

        surveyFields.add(arrayListOf(editText, typeSpinner))


        linearLayout.addView(textView)
        linearLayout.addView(editText)
        linearLayout.addView(typeSpinner)

        parent.addView(linearLayout)


    }

    fun uploadSurvey() {
        val auth = Firebase.auth
        val db = Firebase.firestore

        val data = mutableMapOf<String, String> ()
        for (i in surveyFields) {
            data[(i[0] as EditText).text.toString()] = (i[1] as Spinner).selectedItem.toString()
        }

        db.collection(auth.uid!!)
            .add(data)
            .addOnSuccessListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

    }
}