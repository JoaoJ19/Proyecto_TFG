package com.example.proyecto

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class IniciarSesion : AppCompatActivity() {

    private lateinit var editarTextoEmail: TextInputEditText
    private lateinit var editarTextoClave: TextInputEditText
    private lateinit var logearseOtraVez: Button
    private lateinit var registrarseTexto: TextView
    private val firebaseAuth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        editarTextoEmail = findViewById(R.id.emailjj)
        editarTextoClave = findViewById(R.id.clavejj)
        logearseOtraVez = findViewById(R.id.entrarNuevo)
        registrarseTexto = findViewById(R.id.letrasRegistrarse)

        registrarseTexto.setOnClickListener {
            val intent = Intent(this@IniciarSesion, Registrarse::class.java)
            startActivity(intent)
            finish()
        }

        logearseOtraVez.setOnClickListener {
            val email = editarTextoEmail.text.toString()
            val clave = editarTextoClave.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@IniciarSesion, "Introduzca el Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(clave)) {
                Toast.makeText(this@IniciarSesion, "Introduzca la Clave", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            firebaseAuth.signInWithEmailAndPassword(email, clave)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@IniciarSesion, "Iniciando sesión", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@IniciarSesion, CuentaPruebita::class.java).apply {
                            putExtra("email", email)  // Aquí se pasa el correo electrónico
                            putExtra("password", clave)  // Aquí se pasa la contraseña
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@IniciarSesion, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
