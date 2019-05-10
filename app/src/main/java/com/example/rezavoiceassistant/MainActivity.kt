package com.example.rezavoiceassistant

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import android.widget.ViewAnimator
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE=100
    private var myClipboard: ClipboardManager? = null
    private var myClip: ClipData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        btn.setOnClickListener {
          speak()
        }
        btnsend.setOnClickListener {
            val txt=textTv.text.toString()
            val action=Intent(Intent.ACTION_SEND)
            action.putExtra(Intent.EXTRA_TEXT,txt)
            action.type="type/plain"
            startActivity(Intent.createChooser(action,"کجا بفرستم"))
        }
        btncopy.setOnClickListener {
            copyText()
        }
           }
    fun copyText() {
        myClip = ClipData.newPlainText("text", textTv.text);
        myClipboard?.setPrimaryClip(myClip);
        Toast.makeText(this, "رونوشت برداشته شد", Toast.LENGTH_SHORT).show();
    }


    private fun speak() {
        var mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"fa-IR")
        try {
            startActivityForResult(mIntent,REQUEST_CODE)

        }catch (e:Exception){
            Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE->{
                if (resultCode==Activity.RESULT_OK&&null!=data) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    textTv.text = result[0]
                }
            }
        }
    }
}
