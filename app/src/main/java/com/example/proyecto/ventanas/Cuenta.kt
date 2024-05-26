package com.example.proyecto.ventanas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.proyecto.MainActivity
import com.example.proyecto.R
import com.google.firebase.auth.FirebaseAuth

class Cuenta : Fragment() {
    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var nombreTexto: TextView
    private lateinit var botonSalir: Button
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_cuenta, container, false)

        emailTextView = root.findViewById(R.id.elcorreo)
        passwordTextView = root.findViewById(R.id.lacontrasena)
        nombreTexto = root.findViewById(R.id.fotoPerfildesc)
        botonSalir = root.findViewById(R.id.salir)

        val email = activity?.intent?.getStringExtra("email")
        val password = activity?.intent?.getStringExtra("password")
        val nombre = activity?.intent?.getStringExtra("email")
        nombreTexto.text = nombre?.substringBefore("@") ?: "Correo no disponible"
        emailTextView.text = email ?: "Correo no disponible"
        passwordTextView.text = password ?: "Contraseña no disponible"

        botonSalir.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        return root
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("¿Seguro que quieres cerrar sesión?")
            .setPositiveButton("Sí") { dialog, id ->
                // Cerrar sesión y volver a MainActivity
                firebaseAuth.signOut()
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton("No") { dialog, id ->
                // Cancelar el diálogo
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}
