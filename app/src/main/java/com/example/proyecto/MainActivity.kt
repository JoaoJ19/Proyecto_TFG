package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var navegar : BottomNavigationView
    private val menuNavegacion = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        // Aqui vamos a hacer dependiendo de que icono das te lleve a si pestaña.
        when (item.itemId) {
            R.id.inicio -> {
                supportFragmentManager.commit {
                    replace<Inicio>(R.id.fragmentoPrincipal)
                    setReorderingAllowed(true)
                    addToBackStack("remplazar sino ...")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.cuenta -> {
                supportFragmentManager.commit {
                    replace<Cuenta>(R.id.fragmentoPrincipal)
                    setReorderingAllowed(true)
                    addToBackStack("remplazar sino ...")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.favoritos -> {
                supportFragmentManager.commit {
                    replace<Favoritos>(R.id.fragmentoPrincipal)
                    setReorderingAllowed(true)
                    addToBackStack("remplazar sino ...")
                }
                return@OnNavigationItemSelectedListener true
            }
    }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navegar = findViewById(R.id.bottomNavigationView)
        navegar.setOnNavigationItemSelectedListener(menuNavegacion)

        supportFragmentManager.commit {
            replace<Inicio>(R.id.fragmentoPrincipal)
            setReorderingAllowed(true)
            addToBackStack("remplazar sino ...")
        }

    }
}