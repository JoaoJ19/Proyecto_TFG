package com.example.proyecto.ventanas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.proyecto.R
import com.example.proyecto.zonas.LugaresCosta

class Inicio : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_inicio, container, false)

        val naturalRecipesCardView: CardView = view.findViewById(R.id.monumentosCosta)
        val snackRecipesCardView: CardView = view.findViewById(R.id.restaurantesCosta)

        naturalRecipesCardView.setOnClickListener {
            val intent = Intent(activity, LugaresCosta::class.java)
            startActivity(intent)
        }

        snackRecipesCardView.setOnClickListener {
            val intent = Intent(activity, LugaresCosta::class.java)
            startActivity(intent)
        }

        return view
    }
}
