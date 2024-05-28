package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonRegistrarse = findViewById<Button>(R.id.BotonInicioRegistrase)
        val botonIniciarSesion = findViewById<Button>(R.id.BotonIniciodeInicioSesion)

        // Verificar el estado de autenticación al abrir la aplicación
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // El usuario ya está autenticado, redirigir a la pantalla principal (MainActivity2)
            val intent = Intent(this, MainActivity2::class.java).apply {
                // Pasar el correo electrónico del usuario como un extra
                putExtra("email", currentUser.email)


            }
            startActivity(intent)
            finish()
        }

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