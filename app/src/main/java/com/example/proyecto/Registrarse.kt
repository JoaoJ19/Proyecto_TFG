package com.example.proyecto

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.pruebasMenu.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
            val intent = Intent(this@Registrarse, IniciarSesion::class.java)
            startActivity(intent)
            finish()
        }

        registrarse.setOnClickListener {
            val email = editarTextoEmail.text.toString()
            val clave = editarTextoClave.text.toString()
            val nombre = email.substringBefore('@')

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
                        val userId = firebaseAuth.currentUser?.uid
                        val userEmail = firebaseAuth.currentUser?.email

                        val user = User(
                            id = userId.toString(),
                            name = nombre
                        ).toMap()

                        FirebaseFirestore.getInstance().collection("users")
                            .document(userEmail.toString())
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(TAG, "Usuario creado en la base de datos")
                            }.addOnFailureListener {
                                Log.d(TAG, "Ocurrió un error: $it")
                            }

                        val intent = Intent(this@Registrarse, MainActivity2::class.java).apply {
                            putExtra("email", email)
                            putExtra("password", clave)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@Registrarse, "Error al Registrarse", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
