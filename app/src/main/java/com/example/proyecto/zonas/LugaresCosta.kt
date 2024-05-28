package com.example.proyecto.zonas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.proyecto.R
import com.example.proyecto.pruebasMenu.Ciudad
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class LugaresCosta : AppCompatActivity() {

    private val dbFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lugares_prueba)

        val linearLayout = findViewById<LinearLayout>(R.id.todorestcost)

        // Consultar las ciudades desde Firestore
        dbFirestore.collection("ciudades")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val ciudad = document.toObject(Ciudad::class.java)

                    // Crear un nuevo layout para cada ciudad
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
                        // Aquí puedes guardar la ciudad favorita en Firestore
                        // Por ejemplo, podrías guardar el ID de la ciudad en la colección de favoritos del usuario actual
                        // Puedes obtener el ID de la ciudad usando ciudad.id_ciudad
                        // Y el ID del usuario actual usando FirebaseAuth.getInstance().currentUser?.uid
                        guardarCiudadFavorita(ciudad)
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores de la consulta
            }
    }

    private fun guardarCiudadFavorita(ciudad: Ciudad) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            dbFirestore.collection("usuarios").document(userId).collection("favoritos")
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
}

