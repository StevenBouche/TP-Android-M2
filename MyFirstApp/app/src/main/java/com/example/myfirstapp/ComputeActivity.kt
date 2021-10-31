package com.example.myfirstapp

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged

class ComputeActivity : AppCompatActivity(){

    private lateinit var inputOne: EditText;
    private lateinit var inputTwo: EditText;
    private lateinit var resultText: TextView;
    private lateinit var button: Button;

    private var resultValue: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compute_activity)


        initInputs()
        initButton()
        initText()
    }

    private fun initButton(){
        button = findViewById(R.id.btn_calculer)
        button.setOnClickListener { actionButton() }
        changeStateButton()
    }

    private fun initInputs(){
        inputOne = findViewById(R.id.field_1)
        inputTwo = findViewById(R.id.field_2)
        inputOne.setRawInputType(InputType.TYPE_CLASS_NUMBER)
        inputTwo.setRawInputType(InputType.TYPE_CLASS_NUMBER)

        inputOne.doAfterTextChanged { s ->
            if (s != null) {
                actionEditText(inputOne, s)
            }
        }
        inputTwo.doAfterTextChanged { s ->
            if (s != null) {
                actionEditText(inputTwo, s)
            }
        }
    }

    private fun initText(){
        resultText = findViewById(R.id.resultat);
        refreshText()
    }

    private fun actionEditText(edit: EditText, s: Editable){
        changeStateButton();
    }

    private fun actionButton(){
        val val1 = inputOne.text.toString().trim().toInt()
        val val2 = inputTwo.text.toString().trim().toInt()
        resultValue = val1 + val2;
        refreshText()
    }

    private fun changeStateButton(){
        button.isEnabled = isNumber(inputOne.text.toString().trim()) &&
                isNumber(inputTwo.text.toString().trim())
    }

    private fun refreshText(){
        resultText.visibility = if(resultValue > 0) View.VISIBLE else View.INVISIBLE;
        resultText.text = "Result : $resultValue";
    }

    private fun isNumber(s: String?): Boolean {
        return !s.isNullOrEmpty() && s.matches(Regex("\\d+"))
    }

}