package com.ramnia.a30_12_2023.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramnia.a30_12_2023.MainActivity
import com.ramnia.a30_12_2023.R

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvGoToLogin = findViewById<TextView>(R.id.tvGoToLogin)

        tvGoToLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        btnRegister.setOnClickListener {
            registerUser()
        }


    }

    private fun registerUser(){
        val username = findViewById<EditText>(R.id.etRegisterUsername).text.toString().trim()
        val email = findViewById<EditText>(R.id.etRegisterEmail).text.toString().trim()
        val contrasena = findViewById<EditText>(R.id.etRegisterPassword).text.toString().trim()

        if (username.isEmpty()||email.isEmpty()||contrasena.isEmpty()){
            Toast.makeText(baseContext, "Falta completar los campos", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email,contrasena).addOnCompleteListener(this) {
            Task->
            if(Task.isSuccessful){
                val user = auth.currentUser
                user?.let {
                    val userProfile = mapOf(
                        "username"  to username,
                        "email"  to email,
                        "userType" to "cliente"
                    )
                    firestore.collection("users").document(it.uid).set(userProfile).addOnSuccessListener {
                        Toast.makeText(baseContext, "Bienvenido", Toast.LENGTH_SHORT).show()
                        val mainIntent = Intent(this,MainActivity::class.java)
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(mainIntent)
                    }
                        .addOnFailureListener {e->
                            Toast.makeText(baseContext, "Fallo en el registro", Toast.LENGTH_SHORT).show()
                        }

                }
            }else{
                Toast.makeText(baseContext, "No se pudo realizar el registro", Toast.LENGTH_SHORT).show()
            }
        }
    }
}