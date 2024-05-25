package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonRegistrarse = findViewById<Button>(R.id.BotonInicioRegistrase)
        val botonIniciarSesion = findViewById<Button>(R.id.BotonIniciodeInicioSesion)

        botonRegistrarse.setOnClickListener {
            val intent = Intent(this, Registrarse::class.java)
            startActivity(intent)
        }

        botonIniciarSesion.setOnClickListener {
            val intent = Intent(this, IniciarSesion::class.java)
            startActivity(intent)
        }
    }
}
