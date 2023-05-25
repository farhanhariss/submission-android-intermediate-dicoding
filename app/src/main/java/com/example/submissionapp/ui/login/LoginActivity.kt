package com.example.submissionapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.databinding.ActivityLoginBinding
import com.example.submissionapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()
        setMyButtonEnable()

        //obtain viewmodel
        val tokenPreferences = TokenPreferences(this)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.isLoading.observe(this){showLoading(it)}


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
        val charPassword : Int = resultPassword!!.length
        binding.btnLogin.isEnabled = charPassword >= 8 && resultPassword != null && resultPassword.toString().isNotEmpty() && resultUsername != null && resultUsername.toString().isNotEmpty()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun showLoading(isLoading : Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.loginPicture, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val greetLog = ObjectAnimator.ofFloat(binding.loginGreeting,View.ALPHA, 1f).setDuration(500)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvLoginName,View.ALPHA, 1f).setDuration(500)
        val edEmail = ObjectAnimator.ofFloat(binding.editTextLoginUsername,View.ALPHA, 1f).setDuration(500)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvLoginPassword,View.ALPHA, 1f).setDuration(500)
        val edPassword = ObjectAnimator.ofFloat(binding.editTextLoginPassword,View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin,View.ALPHA, 1f).setDuration(500)
        val linearLayout = ObjectAnimator.ofFloat(binding.linearLayout,View.ALPHA, 1f).setDuration(500)


        val together = AnimatorSet().apply {
            playTogether(tvEmail,edEmail,tvPassword,edPassword)
        }

        AnimatorSet().apply {
            playSequentially(greetLog,together, btnLogin, linearLayout)
            start()
        }
    }
}