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
import com.example.proyecto.zonas.RestauranteCosta

class Inicio : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_inicio, container, false)

        val monumentosCostita: CardView = view.findViewById(R.id.monumentosCosta)
        val restauranCosta: CardView = view.findViewById(R.id.restaurantesCosta)

        monumentosCostita.setOnClickListener {
            val intent = Intent(activity, LugaresCosta::class.java)
            startActivity(intent)
        }

        restauranCosta.setOnClickListener {
            val intent = Intent(activity, RestauranteCosta::class.java)
            startActivity(intent)
        }

        return view
    }
}
