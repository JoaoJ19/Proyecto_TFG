package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Cuenta : AppCompatActivity() {
    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var nombreTexto: TextView
    private lateinit var botonSalir: Button
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuenta)

        emailTextView = findViewById(R.id.elcorreo)
        passwordTextView = findViewById(R.id.lacontrasena)
        nombreTexto = findViewById(R.id.fotoPerfildesc)
        botonSalir = findViewById(R.id.salir)

        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val nombre = intent.getStringExtra("email")
        nombreTexto.text = nombre?.substringBefore("@") ?: "Correo no disponible"
        emailTextView.text = email ?: "Correo no disponible"
        passwordTextView.text = password ?: "Contraseña no disponible"

        botonSalir.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Seguro que quieres cerrar sesión?")
            .setPositiveButton("Sí") { dialog, id ->
                // Cerrar sesión y volver a MainActivity
                firebaseAuth.signOut()
                val intent = Intent(this@Cuenta, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No") { dialog, id ->
                // Cancelar el diálogo
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}
