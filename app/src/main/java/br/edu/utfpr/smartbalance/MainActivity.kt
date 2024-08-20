package br.edu.utfpr.smartbalance

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btEntrar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btEntrar = findViewById( R.id.btEntrar )

        btEntrar.setOnClickListener {
            btCalculateOnClick();
        }
    }
    private fun btCalculateOnClick() {
        val intent = Intent( this, LancamentoActivity::class.java )
        startActivity( intent )
    }
}