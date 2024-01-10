package com.ramnia.a30_12_2023.Activitys

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ramnia.a30_12_2023.MainActivity
import com.ramnia.a30_12_2023.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var progressDialog : ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnLogin.setOnClickListener {
            validarInfo()
        }

        binding.tvGoToRegistro.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        /*binding.TxtRegistrarme.setOnClickListener {
            startActivity(Intent(this@Login_email, Registro_email::class.java))
        }*/

    }

    private var email = ""
    private var password = ""
    private fun validarInfo() {

        email = binding.edittextEmail.text.toString().trim()
        password = binding.edittextPassword.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edittextEmail.error = "Email invalido"
            binding.edittextEmail.requestFocus()
        }
        else if (email.isEmpty()){
            binding.edittextEmail.error = "Ingrese Email"
            binding.edittextEmail.requestFocus()
        }
        else if (password.isEmpty()){
            binding.edittextPassword.error = "Ingrese Password"
            binding.edittextPassword.requestFocus()
        }
        else{
            loginUsuario()
        }
    }

    private fun loginUsuario() {
        progressDialog.setMessage("Ingresando")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
                Toast.makeText(
                    this,
                    "Bienvenido(a)",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se pudo iniciar sesion debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}