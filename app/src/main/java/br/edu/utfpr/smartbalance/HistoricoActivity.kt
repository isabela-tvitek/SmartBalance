package br.edu.utfpr.smartbalance

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.smartbalance.adapter.ElementoListaAdapter
import br.edu.utfpr.smartbalance.database.DatabaseHandler

class HistoricoActivity : AppCompatActivity() {
    private lateinit var lvRegistro : ListView
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico)

        lvRegistro = findViewById( R.id.lvRegistros)
        banco = DatabaseHandler( this )

        val registros = banco.listCursor()
        val adapter = ElementoListaAdapter( this, registros )
        lvRegistro.adapter = adapter
    }
}