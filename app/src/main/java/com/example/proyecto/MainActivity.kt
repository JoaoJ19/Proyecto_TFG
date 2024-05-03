package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// Obtener referencias a los botones
        val botonRegistrarse = findViewById<Button>(R.id.BotonInicioRegistrase)
        val botonIniciarSesion = findViewById<Button>(R.id.BotonIniciodeInicioSesion)

        // Configurar un OnClickListener para el botón de Registrarse
        botonRegistrarse.setOnClickListener {
            // Abrir la actividad de registro
            val intent = Intent(this, Registrarse::class.java)
            startActivity(intent)
        }

        // Configurar un OnClickListener para el botón de Iniciar Sesión
        botonIniciarSesion.setOnClickListener {
            // Abrir la actividad de inicio de sesión
            val intent = Intent(this, IniciarSesion::class.java)
            startActivity(intent)
        }
    }
}