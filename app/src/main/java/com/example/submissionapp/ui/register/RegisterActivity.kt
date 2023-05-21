package com.example.submissionapp.ui.register

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.submissionapp.R
import com.example.submissionapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var registerViewModel : RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        registerViewModel.isLoading.observe(this){showLoading(it)}


        binding.editTextRegisterName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.editTextRegisterUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.editTextRegisterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.btnRegister.setOnClickListener {
            registerViewModel.registerUser(
                binding.editTextRegisterName.text.toString(),
                binding.editTextRegisterUsername.text.toString(),
                binding.editTextRegisterPassword.text.toString(),
                this@RegisterActivity
            )
        }
    }

    private fun setMyButtonEnable() {
        val resultName= binding.editTextRegisterName.text.toString()
        val resultUsername =  binding.editTextRegisterUsername.text.toString()
        val resultPassword = binding.editTextRegisterPassword.text.toString()
        val charPassword : Int = resultPassword.length
        binding.btnRegister.isEnabled = charPassword>=8 && resultPassword != null && resultPassword.isNotEmpty() && resultUsername != null && resultUsername.isNotEmpty() && resultName != null && resultName.isNotEmpty()
    }

    private fun showLoading(isLoading : Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}