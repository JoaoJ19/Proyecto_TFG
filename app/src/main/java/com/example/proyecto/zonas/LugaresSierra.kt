package com.example.proyecto.zonas

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.R
import com.example.proyecto.pruebasMenu.Ciudad
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class LugaresSierra : AppCompatActivity() {

    private val dbFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lugares_sierra)

        val linearLayout = findViewById<LinearLayout>(R.id.todosi)

        // Consultar las ciudades desde Firestore
        dbFirestore.collection("csierra")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val ciudad = document.toObject(Ciudad::class.java)

                    // Crear un nuevo layout para cada csierra
                    val itemLayout =
                        layoutInflater.inflate(R.layout.item_ciudad, null) as LinearLayout

                    // Obtener referencias de vistas dentro del layout de la ciudad
                    val imageView = itemLayout.findViewById<ImageView>(R.id.imagen_monumentoCosta)
                    val tituloTextView =
                        itemLayout.findViewById<TextView>(R.id.titulo_monumentoCosta)
                    val descripcionTextView =
                        itemLayout.findViewById<TextView>(R.id.descripcion_monumentoCosta)
                    val imageButton = itemLayout.findViewById<ImageButton>(R.id.laikesito)

                    // Cargar la imagen utilizando Picasso
                    Picasso.get().load(ciudad.imagen).into(imageView)

                    // Establecer el nombre y la descripción de la ciudad
                    tituloTextView.text = ciudad.nombre
                    descripcionTextView.text = ciudad.descripcion

                    // Agregar el layout de la ciudad al LinearLayout principal
                    linearLayout.addView(itemLayout)

                    // Agregar un OnClickListener al botón de laikesito
                    imageButton.setOnClickListener {
                        // Cambiar la imagen del ImageButton
                        if (imageButton.tag == "no_favorito") {
                            imageButton.setImageResource(R.drawable.relleno)
                            imageButton.tag = "favorito"
                            guardarCiudadFavorita(ciudad)
                        } else {
                            imageButton.setImageResource(R.drawable.corazon_vacio)
                            imageButton.tag = "no_favorito"
                            eliminarCiudadFavorita(ciudad)
                        }
                    }

                    // Establecer el tag inicial del ImageButton
                    imageButton.tag = "no_favorito"
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores de la consulta
                Toast.makeText(
                    this,
                    "Error al obtener ciudades: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun guardarCiudadFavorita(ciudad: Ciudad) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            dbFirestore.collection("usuarios").document(userId).collection("favoritosCSierra")
                .document(ciudad.id_ciudad)
                .set(ciudad)
                .addOnSuccessListener {
                    Toast.makeText(this, "Ciudad agregada a favoritos", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    // Manejar errores al guardar la ciudad favorita
                    Toast.makeText(
                        this,
                        "Error al agregar a favoritos: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }
    }


    private fun eliminarCiudadFavorita(ciudad: Ciudad) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            dbFirestore.collection("usuarios").document(userId).collection("favoritosCSierra")
                .document(ciudad.id_ciudad)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Ciudad eliminada de favoritos", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    // Manejar errores al eliminar la ciudad favorita
                    Toast.makeText(
                        this,
                        "Error al eliminar de favoritos: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }
    }
}

