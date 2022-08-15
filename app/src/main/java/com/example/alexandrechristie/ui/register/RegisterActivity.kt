package com.example.alexandrechristie.ui.register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.example.alexandrechristie.data.entity.User
import com.example.alexandrechristie.databinding.ActivityRegisterBinding
import com.example.alexandrechristie.ui.PatternUtils
import com.example.alexandrechristie.ui.login.LoginActivity
import com.example.alexandrechristie.viewModel.UserViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private var id:Int ?= null
    private val viewModel: UserViewModel by viewModels()
    private var mProfileUri: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkValidate()
        onClick()

    }

    private fun onClick() {
        binding.register.setOnClickListener {
            if(binding.titConfirm.isErrorEnabled || binding.titPwd.isErrorEnabled || binding.titPhone.isErrorEnabled
                || binding.name.text.toString().isNullOrBlank() || !binding.chkAgree.isChecked
                || binding.phone.text.isNullOrBlank() || binding.pwd.text.isNullOrBlank()
                || binding.pwd.text.isNullOrBlank()){
                Snackbar.make(it,"Fix Correct Data", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                if(mProfileUri!=null){
                    upsertUser()
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this, "Please upload your image", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.imgProfile.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
    }
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!

                    mProfileUri = fileUri
                    binding.imgProfile.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun upsertUser() {
        val name=binding.name.text.toString().trim()
        val email=binding.email.text.toString().trim()
        val phone=binding.phone.text.toString().trim()
        val pwd=binding.pwd.text.toString().trim()
        val confirm=binding.confirm.text.toString().trim()
        val gender = if(binding.female.isChecked) "Female" else "Male"
        val addresses=binding.address.text.toString().trim()

        val user=User(
            id,name, email , phone,pwd,confirm,gender,addresses,mProfileUri.toString()
        )
        viewModel.insertUserForm(user)
    }

    private fun checkValidate() {
        binding.register.setOnClickListener {
            binding.phone.doAfterTextChanged { s->
                if(!s.isNullOrEmpty()) {
                    if (PhoneNumberUtils.isGlobalPhoneNumber(s.toString())){
                        binding.titPhone.isErrorEnabled = false
                    }else{
                        binding.titPhone.error = "Enter Correct Number"
                        return@doAfterTextChanged
                    }
                }
            }
            binding.pwd.doAfterTextChanged { s->
                if(!s.isNullOrEmpty()){
                    val password = PatternUtils.PASSWORD_PATTERN.matcher(s)
                    if(!password.matches()){
                        val error = checkError(s.toString())
                        binding.titPwd.error = error
                        return@doAfterTextChanged
                    }else{
                        binding.titPwd.isErrorEnabled =false
                    }
                }
            }
            binding.confirm.doAfterTextChanged { s->
                if(binding.pwd.text.toString().isNullOrBlank()){
                    binding.titConfirm.error = "Enter Password First"
                    return@doAfterTextChanged
                }else{
                    if(binding.pwd.text.toString() == s.toString()){
                        binding.titConfirm.isErrorEnabled = false
                    }else{
                        binding.titConfirm.error = "Password Not Match"
                        return@doAfterTextChanged
                    }
                }
            }
        }

        binding.signin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun checkError(password: String):String {
        return when {
            /* Rule 1 */
            !password.contains(Regex("[A-Z]")) -> "Password must contain one capital letter"
            /* Rule 2 */
            !password.contains(Regex("[0-9]")) -> "Password must contain one digit"
            /* Rule 3, not counting space as special character */
            !password.contains(Regex("[^a-zA-Z0-9 ]")) -> "Password must contain one special character"

            !password.contains(Regex("\\S+\$"))-> "Password not allowed to add space"
            else -> "Password is too short."
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}