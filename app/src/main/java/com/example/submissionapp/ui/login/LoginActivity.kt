package com.example.submissionapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.submissionapp.R
import com.example.submissionapp.customview.Button
import com.example.submissionapp.customview.MyEditText
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.ui.register.RegisterActivity
import com.example.submissionapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMyButtonEnable()

        //obtain viewmodel
        val tokenPreferences = TokenPreferences(this)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        binding.editTextLoginUsername.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.editTextLoginPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        //Pindah halaman register ketika text register di klik
        binding.tvRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            loginViewModel.checkUserKey(binding.editTextLoginUsername.text.toString(), binding.editTextLoginPassword.text.toString(),this@LoginActivity,tokenPreferences)
        }


    }
    private fun setMyButtonEnable() {
        val resultUsername= binding.editTextLoginUsername.text
        val resultPassword = binding.editTextLoginPassword.text
        binding.btnLogin.isEnabled = resultPassword != null && resultPassword.toString().isNotEmpty() && resultUsername != null && resultUsername.toString().isNotEmpty()
    }
}