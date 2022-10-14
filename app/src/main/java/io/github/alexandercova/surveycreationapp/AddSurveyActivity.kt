package io.github.alexandercova.surveycreationapp


import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddSurveyActivity : AppCompatActivity() {

    var surveyFields = arrayListOf<ArrayList<Any>>()
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_survey)

        val addFieldButton = findViewById<Button>(R.id.addFieldButton)
        val uploadButton = findViewById<ImageButton>(R.id.uploadSurveyButton)
        val backButton = findViewById<ImageButton>(R.id.backButton)

        addFieldButton.setOnClickListener {
            createField()
        }

        uploadButton.setOnClickListener {
            uploadSurvey()
        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
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

        val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        param.weight=0.4F
        param.gravity = Gravity.CENTER_VERTICAL
        textView.layoutParams = param


        val editText = EditText(this)
        editText.hint = "Field Hint"

        val editParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200)
        editParam.weight = 0.3F
        editText.layoutParams = editParam


        val typeSpinner = Spinner(this)
        val spinnerParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        spinnerParam.weight= 0.3F
        typeSpinner.layoutParams = spinnerParam

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
        val nameEdit = findViewById<EditText>(R.id.surveyNameEdit)

        val data = mutableMapOf<String, String> ()
        data["name"] = nameEdit.text.toString()

        for (i in surveyFields) {
            data[(i[0] as EditText).text.toString()] = (i[1] as Spinner).selectedItem.toString()
        }
        var randomString = ""

        for(i in 0..3) {
            randomString += charPool[(0..60).random()]
        }
        db.collection(auth.uid!!.substring(auth.uid!!.length - 3))
            .document(randomString)
            .set(data)
            .addOnSuccessListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("created", true)
                intent.putExtra("id", randomString + auth.uid!!.substring(auth.uid!!.length - 3))
                startActivity(intent)
                finish()
            }

    }
}