package com.example.proyecto.zonas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.proyecto.R
import com.example.proyecto.pruebasMenu.Restaurante
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class RestaurantesPlayas : AppCompatActivity() {

    private val dbFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurantes_playas)

        val linearLayout = findViewById<LinearLayout>(R.id.todorestplaya)

        // Verificar si el linearLayout se inicializó correctamente
        if (linearLayout == null) {
            Toast.makeText(this, "Error: LinearLayout no encontrado", Toast.LENGTH_SHORT).show()
            return
        }

        // Consultar los restaurantes desde Firestore
        dbFirestore.collection("resplayas")
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
                        guardarRestauranteFavorito(restaurante)
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores de la consulta
                Toast.makeText(
                    this,
                    "Error al obtener datos: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun guardarRestauranteFavorito(restaurante: Restaurante) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            // Corregir la referencia para que tenga un número par de segmentos
            dbFirestore.collection("usuarios").document(userId).collection("favoritosRestPlayas")
                .document(restaurante.id_rest) // Asegúrate de que este valor sea válido
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
}