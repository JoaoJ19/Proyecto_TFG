package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.NavController

class otroInicio : AppCompatActivity() {


    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otro_inicio)


        val naturalRecipesCardView: CardView = findViewById(R.id.monumentosCosta)
        val snackRecipesCardView: CardView = findViewById(R.id.restaurantesCosta)

        naturalRecipesCardView.setOnClickListener {
            val intent = Intent(this, InicioPruebita::class.java)
            startActivity(intent)
        }

        snackRecipesCardView.setOnClickListener {
            val intent = Intent(this, InicioPruebita::class.java)
            startActivity(intent)
        }
    }
}