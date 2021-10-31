package com.example.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var clickButton: Button
    private lateinit var clickCalcul: Button;
    private var nbClick = 0;
    private lateinit var textArea: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initText();
        initButton();
        initButtonCalcul();
    }

    private fun initButtonCalcul() {
        clickCalcul = findViewById(R.id.button_calcule)
        clickCalcul.setOnClickListener { actionBoutonCalcul() }
    }

    private fun initButton() {
        clickButton = findViewById(R.id.btn_click_me)
        clickButton.setOnClickListener { actionButton() };
    }

    private fun actionButton(){
        nbClick++
        clickButton.isEnabled = nbClick < 5;
        refreshText();
    }

    private fun actionBoutonCalcul(){
        val intent = Intent(baseContext, ComputeActivity::class.java)
        startActivity(intent)
    }

    private fun initText() {
        textArea = findViewById(R.id.textView);
        refreshText()
    }

    private fun refreshText(){
        textArea.visibility = if (nbClick > 0) View.VISIBLE else View.INVISIBLE;
        textArea.text = "Click value : $nbClick";
    }
}