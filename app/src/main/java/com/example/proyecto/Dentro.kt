package com.example.proyecto

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace

class Dentro : AppCompatActivity() {

        private val menuNavegacion = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.boton_inicio -> {
                    supportFragmentManager.commit {
                        replace<Inicio>(R.id.fragmento_navegacion)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    return@OnNavigationItemSelectedListener true
                }

                R.id.boton_cuenta -> {
                    supportFragmentManager.commit {
                        replace<Cuenta>(R.id.fragmento_navegacion)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    return@OnNavigationItemSelectedListener true
                }

                R.id.boton_favoritos -> {
                    supportFragmentManager.commit {
                        replace<Favorito>(R.id.fragmento_navegacion)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_dentro)

            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            navView.setOnNavigationItemSelectedListener(menuNavegacion)

            // Mostrar el fragmento inicial al iniciar la actividad
            if (savedInstanceState == null) {
                supportFragmentManager.commit {
                    replace<Inicio>(R.id.fragmento_navegacion)
                    setReorderingAllowed(false)
                    addToBackStack(null)
                }
            }
        }
    }