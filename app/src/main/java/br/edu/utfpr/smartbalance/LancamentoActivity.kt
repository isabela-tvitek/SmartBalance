package br.edu.utfpr.smartbalance

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.smartbalance.database.DatabaseHandler
import br.edu.utfpr.smartbalance.entity.Movimentacao

class LancamentoActivity : AppCompatActivity() {

    private lateinit var etTipo : EditText
    private lateinit var etDetalhe : EditText
    private lateinit var etValor : EditText
    private lateinit var etData : EditText

    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lancamento)

        etTipo = findViewById( R.id.etTipo)
        etDetalhe = findViewById( R.id.etDetalhe)
        etValor = findViewById( R.id.etValor)
        etData = findViewById( R.id.etData)

        banco = DatabaseHandler( this )
    }

    fun btIncluirOnClick(view: View) {

        val cadastro = Movimentacao(
            0,
            etTipo.text.toString(),
            etDetalhe.text.toString(),
            etValor.text.toString().toFloat(),
            etData.text.toString(),
        )

        banco.insert( cadastro )

        Toast.makeText( this, "Sucesso!", Toast.LENGTH_LONG ).show()
    }
}