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
import com.example.proyecto.zonas.LugaresSierra
import com.example.proyecto.zonas.RestauranteCosta
import com.example.proyecto.zonas.RestaurantesPlayas
import com.example.proyecto.zonas.RestaurantesSierra
import com.example.proyecto.zonas.playas

class Inicio : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_inicio, container, false)

        val monumentosCostita: CardView = view.findViewById(R.id.monumentosCosta)
        val restauranCosta: CardView = view.findViewById(R.id.restaurantesCosta)
        val monumentosSierra: CardView = view.findViewById(R.id.monumentosSierra)
        val restauranSierra: CardView = view.findViewById(R.id.restaurantesSierra)
        val playitas: CardView = view.findViewById(R.id.mejoresPlayas)
        val restauranplayitas: CardView = view.findViewById(R.id.restaurantesPlayas)

        monumentosCostita.setOnClickListener {
            val intent = Intent(activity, LugaresCosta::class.java)
            startActivity(intent)
        }

        restauranCosta.setOnClickListener {
            val intent = Intent(activity, RestauranteCosta::class.java)
            startActivity(intent)
        }

        monumentosSierra.setOnClickListener {
            val intent = Intent(activity, LugaresSierra::class.java)
            startActivity(intent)
        }

        restauranSierra.setOnClickListener {
            val intent = Intent(activity, RestaurantesSierra::class.java)
            startActivity(intent)
        }

        playitas.setOnClickListener {
            val intent = Intent(activity, playas::class.java)
            startActivity(intent)
        }

        restauranplayitas.setOnClickListener {
            val intent = Intent(activity, RestaurantesPlayas::class.java)
            startActivity(intent)
        }

        return view
    }
}
