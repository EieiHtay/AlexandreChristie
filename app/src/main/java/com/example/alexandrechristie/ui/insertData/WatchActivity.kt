package com.example.alexandrechristie.ui.insertData

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.alexandrechristie.data.entity.Watch
import com.example.alexandrechristie.databinding.ActivityWatchBinding
import com.example.alexandrechristie.viewModel.WatchViewModel
import com.github.dhaval2404.imagepicker.ImagePicker

class WatchActivity : AppCompatActivity() {
    private var mProfileUri: Uri?=null
    private var id:Int ?= null
    private var isEdit = false

    private val viewModel:WatchViewModel by viewModels()
    private lateinit var binding: ActivityWatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isEdit = intent.getBooleanExtra("Edit",false)
        if(isEdit){
            id = intent.getIntExtra("id",0)
            getWatch(id!!)
        }

        onClick()
    }
    private fun getWatch(id: Int) {
        viewModel.getWatchById(id).observe(this){
            setUpData(it!!)
        }
    }
    private fun setUpData(it: Watch) {
        setUpSpinnerCategory(it.category)
        binding.apply {
            codeNo.setText(it.code)
            watchPrice.setText(it.price.toString())
            watchDesc.setText(it.description)
            mProfileUri = Uri.parse(it.img)
            imgProfile.setImageURI(Uri.parse(it.img))
        }
    }

    private fun setUpSpinnerCategory(category: String) {
        when(category){
            "Men Collection"->{binding.spinnerCategory.setSelection(0)}
            "Women Collection"->{binding.spinnerCategory.setSelection(1)}
            else->{binding.spinnerCategory.setSelection(2)}

        }
    }

    private fun onClick() {
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.foodAdd.setOnClickListener{
            if (mProfileUri != null){
                upsertWatch()
                finish()
            }else{
                Toast.makeText(this, "Please upload food image", Toast.LENGTH_SHORT).show()
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
    private fun upsertWatch() {
        val codeNo=binding.codeNo.text.toString().trim()
        val price=binding.watchPrice.text.toString().trim()
        val desc=binding.watchDesc.text.toString().trim()
        val category=binding.spinnerCategory.selectedItem.toString()

        val watch=Watch(
            id,codeNo, price.toInt(),desc,mProfileUri.toString(),category
        )
        viewModel.insertWatchForm(watch)
    }
}