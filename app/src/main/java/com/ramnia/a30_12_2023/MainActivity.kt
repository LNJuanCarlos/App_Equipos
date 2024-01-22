package com.ramnia.a30_12_2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.ramnia.a30_12_2023.Fragments.FragmentCuenta
import com.ramnia.a30_12_2023.Fragments.FragmentDisponibles
import com.ramnia.a30_12_2023.Fragments.FragmentExportar
import com.ramnia.a30_12_2023.Fragments.FragmentInicio
import com.ramnia.a30_12_2023.Fragments.FragmentMiReserva
import com.ramnia.a30_12_2023.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        verFragmentInicio()

        binding.BottomNV.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Item_Inicio -> {
                    verFragmentInicio()
                    true
                }

                R.id.Item_Disponibles -> {
                    verFragmentDisponibles()
                    true
                }

                R.id.Item_Mi_Reserva -> {
                    verFragmentMiReserva()
                    true
                }

                else -> {
                    false
                }
            }
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val fab = findViewById<FloatingActionButton>(R.id.FAB)

        fab.setOnClickListener {
            // Abrir el panel lateral al hacer clic en el FAB
            drawerLayout.openDrawer(navView, true)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            // Manejar los clics en las opciones del panel lateral
            when (menuItem.itemId) {
                // Acciones para las opciones del panel lateral
                R.id.menu_option1 -> {
                    // Acción para la opción 1
                    verFragmentExportar()
                    drawerLayout.closeDrawer(navView, true)
                    true
                }
                R.id.menu_option2 -> {
                    // Acción para la opción 2
                    drawerLayout.closeDrawer(navView, true)
                    true
                }
                R.id.menu_option3 -> {
                    // Acción para la opción 3
                    verFragmentCuenta()
                    drawerLayout.closeDrawer(navView, true)
                    true
                }
                // Agregar más opciones según sea necesario
                else -> false
            }
        }


    }

    private fun verFragmentInicio() {
        binding.TituloRL.text = "Inicio"
        val fragment = FragmentInicio()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id,fragment,"FragmentInicio")
        fragmentTransition.commit()
    }

    private fun verFragmentDisponibles() {
        binding.TituloRL.text = "Disponibles"
        val fragment = FragmentDisponibles()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id,fragment,"FragmentDisponibles")
        fragmentTransition.commit()
    }

    private fun verFragmentMiReserva() {
        binding.TituloRL.text = "Mi Reserva"
        val fragment = FragmentMiReserva()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id,fragment,"FragmentMiReserva")
        fragmentTransition.commit()
    }

    private fun verFragmentExportar() {
        binding.TituloRL.text = "Exportar Datos"
        val fragment = FragmentExportar()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id,fragment,"FragmentExportar")
        fragmentTransition.commit()
    }

    private fun verFragmentCuenta() {
        binding.TituloRL.text = "Mi Cuenta"
        val fragment = FragmentCuenta()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id,fragment,"FragmentCuenta")
        fragmentTransition.commit()
    }

}