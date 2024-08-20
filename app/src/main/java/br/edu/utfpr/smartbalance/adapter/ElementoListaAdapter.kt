package br.edu.utfpr.smartbalance.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.edu.utfpr.smartbalance.R
import br.edu.utfpr.smartbalance.database.DatabaseHandler.Companion.ID
import br.edu.utfpr.smartbalance.database.DatabaseHandler.Companion.TIPO
import br.edu.utfpr.smartbalance.database.DatabaseHandler.Companion.DETALHE
import br.edu.utfpr.smartbalance.database.DatabaseHandler.Companion.VALOR
import br.edu.utfpr.smartbalance.database.DatabaseHandler.Companion.DATA
import br.edu.utfpr.smartbalance.entity.Movimentacao

class ElementoListaAdapter (val context : Context, val cursor : Cursor) : BaseAdapter() {

    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(position: Int): Any {
        cursor.moveToPosition( position )

        return Movimentacao(
            cursor.getInt( ID ),
            cursor.getString( TIPO ),
            cursor.getString( DETALHE ),
            cursor.getFloat( VALOR ),
            cursor.getString( DATA )
        )
    }

    override fun getItemId(position: Int): Long {
        cursor.moveToPosition( position )
        return cursor.getInt( ID ).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate( R.layout.elemento_lista, null )

        val ivTipoElementoLista = v.findViewById<TextView>( R.id.ivTipoElementoLista )
        val tvDataElementoLista = v.findViewById<TextView>( R.id.tvDataElementoLista )
        val tvDetalheElementoLista = v.findViewById<TextView>( R.id.tvDetalheElementoLista )
        val tvValorElementoLista = v.findViewById<TextView>( R.id.tvValorElementoLista )

        cursor.moveToPosition( position )

//        ivTipoElementoLista.setText( cursor.getString( TIPO ) )
        tvDetalheElementoLista.setText( cursor.getString( DETALHE ) )
        tvValorElementoLista.setText( cursor.getString( VALOR ) )
        tvDataElementoLista.setText( cursor.getString( DATA ) )

        return v
    }
}