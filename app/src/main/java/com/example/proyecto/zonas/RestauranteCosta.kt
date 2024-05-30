package com.example.proyecto.zonas

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.proyecto.R
import com.example.proyecto.pruebasMenu.Restaurante
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class RestauranteCosta : AppCompatActivity() {

    private val dbFirestore = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurante_costa)
        val linearLayout = findViewById<LinearLayout>(R.id.todorestcost)

// Consultar los restaurantes desde Firestore
        dbFirestore.collection("restaurantes")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val restaurante = document.toObject(Restaurante::class.java)

                    // Crear un nuevo layout para cada restaurante
                    val itemLayout =
                        layoutInflater.inflate(R.layout.item_restaurante, null) as LinearLayout


                    // Obtener referencias de vistas dentro del layout del restaurante
                    val imageView = itemLayout.findViewById<ImageView>(R.id.imagen_restauran)
                    val tituloTextView =
                        itemLayout.findViewById<TextView>(R.id.titulo_restCosta)
                    val descripcionTextView =
                        itemLayout.findViewById<TextView>(R.id.descripcion_restCosta)
                    val imageButton = itemLayout.findViewById<ImageButton>(R.id.laikesito)

                    // Cargar la imagen utilizando Picasso
                    Picasso.get().load(restaurante.imagen).into(imageView)

                    // Establecer el nombre y la descripción del restaurante
                    tituloTextView.text = restaurante.nombre
                    descripcionTextView.text = restaurante.descripcion

                    // Agregar el layout del restaurante al LinearLayout principal
                    linearLayout.addView(itemLayout)

                    // Agregar un OnClickListener al botón de laikesito
                    imageButton.setOnClickListener {
                        // Cambiar la imagen del ImageButton
                        if (imageButton.tag == "no_favorito") {
                            imageButton.setImageResource(R.drawable.relleno)
                            imageButton.tag = "favorito"
                            guardarRestauranteFavorito(restaurante)
                        } else {
                            imageButton.setImageResource(R.drawable.corazon_vacio)
                            imageButton.tag = "no_favorito"
                            eliminarRestauranteFavorito(restaurante)
                        }
                    }

                    // Establecer el tag inicial del ImageButton
                    imageButton.tag = "no_favorito"
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores de la consulta
                Toast.makeText(this, "Error al obtener datos: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun guardarRestauranteFavorito(restaurante: Restaurante) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            dbFirestore.collection("usuarios").document(userId).collection("favoritosrest")
                .document(restaurante.id_rest.toString())
                .set(restaurante)
                .addOnSuccessListener {
                    Toast.makeText(this, "Restaurante agregado a favoritos", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener { exception ->
                    // Manejar errores al guardar el restaurante favorito
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

    private fun eliminarRestauranteFavorito(restaurante: Restaurante) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            dbFirestore.collection("usuarios").document(userId).collection("favoritosrest")
                .document(restaurante.id_rest.toString())
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Restaurante eliminado de favoritos", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    // Manejar errores al eliminar el restaurante favorito
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
