package br.edu.utfpr.smartbalance.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.smartbalance.entity.Movimentacao


class DatabaseHandler (context : Context) : SQLiteOpenHelper ( context, DATABASE_NAME, null, DATABASE_VERSION ) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL( "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} ( _id INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT, detalhe TEXT, valor FLOAT, data TEXT )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL( "DROP TABLE IF EXISTS ${TABLE_NAME}" )
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 4
        private const val TABLE_NAME = "movimentacao"
        const val ID = 0
        const val TIPO = 1
        const val DETALHE = 2
        const val VALOR = 3
        const val DATA = 4
    }

    fun insert( movimentacao: Movimentacao) {
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put( "tipo", movimentacao.tipo )
        registro.put( "detalhe", movimentacao.detalhe )
        registro.put( "valor", movimentacao.valor )
        registro.put( "data", movimentacao.data )

        db.insert( TABLE_NAME, null, registro )
    }

    fun update( movimentacao: Movimentacao ) {
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put( "tipo", movimentacao.tipo )
        registro.put( "detalhe", movimentacao.detalhe )
        registro.put( "valor", movimentacao.valor )
        registro.put( "data", movimentacao.data )

        db.update( TABLE_NAME, registro, "_id=${movimentacao._id}", null )
    }

    fun delete( id : Int ) {
        val db = this.writableDatabase
        db.delete( TABLE_NAME, "_id=${id}", null )
    }

    fun find(id : Int) : Movimentacao? {
        val db = this.writableDatabase

        val registro : Cursor = db.query( TABLE_NAME,
            null,
            "_id=${id}",
            null,
            null,
            null,
            null
        )

        if ( registro.moveToNext() ) {
            val cadastro = Movimentacao(
                id,
                registro.getString( TIPO ),
                registro.getString( DETALHE ),
                registro.getFloat( VALOR ),
                registro.getString( DATA )
            )
            return cadastro
        } else {
            return null
        }
    }

    fun list() : MutableList<Movimentacao> {
        val db = this.writableDatabase

        val registro = db.query( TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        var registros = mutableListOf<Movimentacao>()

        while ( registro.moveToNext() ) {
            val cadastro = Movimentacao(
                registro.getInt( ID ),
                registro.getString( TIPO ),
                registro.getString( DETALHE ),
                registro.getFloat( VALOR ),
                registro.getString( DATA )
            )
            registros.add( cadastro )
        }
        return registros
    }

    fun listCursor() : Cursor {
        val db = this.writableDatabase

        val registro = db.query( TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        return registro
    }

    fun calcularSaldo(): Float {
        val db = this.writableDatabase

        val columns = arrayOf(
            "SUM(CASE WHEN $TABLE_NAME.tipo = 'c' THEN $TABLE_NAME.valor ELSE 0 END) AS totalCredito",
            "SUM(CASE WHEN $TABLE_NAME.tipo = 'd' THEN $TABLE_NAME.valor ELSE 0 END) AS totalDebito"
        )

        val cursor = db.query(
            TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        var saldo = 0f
        if (cursor.moveToFirst()) {
            val totalCredito = cursor.getFloat(cursor.getColumnIndexOrThrow("totalCredito"))
            val totalDebito = cursor.getFloat(cursor.getColumnIndexOrThrow("totalDebito"))
            saldo = totalCredito - totalDebito
        }
        cursor.close()
        return saldo
    }
}
