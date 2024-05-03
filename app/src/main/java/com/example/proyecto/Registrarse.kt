package com.example.proyecto

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Registrarse : AppCompatActivity() {
    private lateinit var editarTextoEmail: TextInputEditText
    private lateinit var editarTextoClave: TextInputEditText
    private lateinit var registrarse: Button
    private lateinit var iniciarSesionTexto: TextView
    private val firebaseAuth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        editarTextoEmail = findViewById(R.id.emailjj)
        editarTextoClave = findViewById(R.id.clavejj)
        registrarse = findViewById(R.id.registrarse)
        iniciarSesionTexto = findViewById(R.id.entrarnuevo)

        iniciarSesionTexto.setOnClickListener {
            val intent = Intent(this@Registrarse, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        registrarse.setOnClickListener {
            val email = editarTextoEmail.text.toString()
            val clave = editarTextoClave.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@Registrarse, "Introduzca el Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(clave)) {
                Toast.makeText(this@Registrarse, "Introduzca la Clave", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            firebaseAuth.createUserWithEmailAndPassword(email, clave)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@Registrarse, "Usuario Registrado", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Registrarse, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@Registrarse, "Error al Registrarse", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}