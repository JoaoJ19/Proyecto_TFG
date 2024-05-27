package com.example.proyecto

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proyecto.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.hide() // Ocultar el Toolbar

        val navView: BottomNavigationView = binding.navView

        try {
            val navController = findNavController(R.id.fragmento_navegacion)
            Log.d("MainActivity2", "NavController initialized successfully")
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home, R.id.navegacion_favoritos, R.id.navegacion_cuenta
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
            Log.d("MainActivity2", "BottomNavigationView setup successfully")

            navView.setOnNavigationItemSelectedListener { item ->
                Log.d("MainActivity2", "Item selected: ${item.itemId}")
                when (item.itemId) {
                    R.id.navigation_home -> {
                        navController.navigate(R.id.navigation_home)
                        true
                    }
                    R.id.navegacion_favoritos -> {
                        navController.navigate(R.id.navegacion_favoritos)
                        true
                    }
                    R.id.navegacion_cuenta -> {
                        navController.navigate(R.id.navegacion_cuenta)
                        true
                    }
                    else -> false
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity2", "Error setting up navigation", e)
        }
    }

}
