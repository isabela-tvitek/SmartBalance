package br.edu.utfpr.smartbalance

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.smartbalance.database.DatabaseHandler
import br.edu.utfpr.smartbalance.entity.Movimentacao
import java.util.Calendar
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.AdapterView

class LancamentoActivity : AppCompatActivity() {
    private lateinit var spTipo : Spinner
    private lateinit var spDetalhe : Spinner
    private lateinit var etValor : EditText
    private lateinit var etData : EditText
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lancamento)

        spTipo = findViewById(R.id.spTipo)
        spDetalhe = findViewById( R.id.spDetalhe)
        etValor = findViewById( R.id.etValor)
        etData = findViewById( R.id.etData)
        banco = DatabaseHandler( this )

        etData.setOnClickListener {
            showDatePickerDialog(etData)
        }

        val tipos = arrayOf("Crédito", "Débito")
        val adapterTipo = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipo.adapter = adapterTipo

        spTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val tipoSelecionado = spTipo.selectedItem.toString()
                updateDetalheSpinner(tipoSelecionado)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updateDetalheSpinner(tipo: String) {
        val detalhes = when (tipo) {
            "Crédito" -> arrayOf("Salário", "Extras")
            "Débito" -> arrayOf("Alimentação", "Transporte", "Saúde", "Moradia")
            else -> emptyArray()
        }

        val detalheAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, detalhes)
        detalheAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spDetalhe.adapter = detalheAdapter
    }

    private fun showDatePickerDialog(etData: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                etData.setText(formattedDate)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    fun btIncluirOnClick(view: View) {

        val tipoSelecionado = when (spTipo.selectedItem.toString()) {
            "Crédito" -> "c"
            "Débito" -> "d"
            else -> ""
        }

        val cadastro = Movimentacao(
            0,
            tipoSelecionado,
            spDetalhe.selectedItem.toString(),
            etValor.text.toString().toFloat(),
            etData.text.toString(),
        )

        banco.insert( cadastro )

        Toast.makeText( this, "Sucesso!", Toast.LENGTH_LONG ).show()
    }

    fun btHistoricoOnClick(view: View) {
        val intent = Intent( this, HistoricoActivity::class.java )
        startActivity( intent )
    }

    fun btSaldoOnClick(view: View) {
        val saldo = banco.calcularSaldo()
        Toast.makeText(this, "O saldo é R$ %.2f".format(saldo), Toast.LENGTH_LONG).show()
    }
}