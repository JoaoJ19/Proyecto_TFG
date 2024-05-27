package com.example.proyecto.ventanas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyecto.R
import com.example.proyecto.pruebasMenu.Ciudad
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class Favoritos : Fragment() {
    private val dbFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_favorito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayout = view.findViewById<LinearLayout>(R.id.fragmento_navegacion)
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            // Consultar favoritos de monumentos
            consultarFavoritos("favoritos", userId, linearLayout)
            // Consultar favoritos de restaurantes
            consultarFavoritos("favoritosrest", userId, linearLayout)
        } else {
            Toast.makeText(context, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun consultarFavoritos(
        coleccion: String,
        userId: String,
        linearLayout: LinearLayout
    ) {
        dbFirestore.collection("usuarios").document(userId).collection(coleccion)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val itemLayout =
                        layoutInflater.inflate(R.layout.item_ciudad, null) as LinearLayout

                    val imageView = itemLayout.findViewById<ImageView>(R.id.imagen_monumentoCosta)
                    val tituloTextView = itemLayout.findViewById<TextView>(R.id.titulo_monumentoCosta)
                    val descripcionTextView =
                        itemLayout.findViewById<TextView>(R.id.descripcion_monumentoCosta)
                    val imageButton = itemLayout.findViewById<ImageButton>(R.id.laikesito)

                    // Obtener datos del documento y establecer en las vistas
                    val nombre = document.getString("nombre")
                    val descripcion = document.getString("descripcion")
                    val imagenUrl = document.getString("imagen")

                    Picasso.get().load(imagenUrl).into(imageView)
                    tituloTextView.text = nombre
                    descripcionTextView.text = descripcion

                    // Cambiar el icono si ya está en favoritos
                    dbFirestore.collection("usuarios").document(userId).collection(coleccion)
                        .document(document.id)
                        .get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                imageButton.setImageResource(R.drawable.relleno)
                            } else {
                                imageButton.setImageResource(R.drawable.corazon_vacio)
                            }
                        }

                    imageButton.setOnClickListener {
                        toggleFavorite(document.id, userId, coleccion, imageButton)
                    }

                    linearLayout.addView(itemLayout)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Error al cargar favoritos: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun toggleFavorite(
        documentoId: String,
        userId: String,
        coleccion: String,
        imageButton: ImageButton
    ) {
        val favoritosRef =
            dbFirestore.collection("usuarios").document(userId).collection(coleccion)
                .document(documentoId)

        favoritosRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                // Si ya está en favoritos, eliminarlo
                favoritosRef.delete()
                    .addOnSuccessListener {
                        imageButton.setImageResource(R.drawable.corazon_vacio)
                        Toast.makeText(context, "Eliminado de favoritos", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(
                            context,
                            "Error al eliminar de favoritos: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                // Si no está en favoritos, agregarlo
                favoritosRef.set(document)
                    .addOnSuccessListener {
                        imageButton.setImageResource(R.drawable.relleno)
                        Toast.makeText(context, "Añadido a favoritos", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(
                            context,
                            "Error al añadir a favoritos: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }
}
