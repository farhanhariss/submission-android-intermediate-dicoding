package com.example.submissionapp.ui.add_story

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.submissionapp.databinding.ActivityAddStoryBinding

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddStoryBinding

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1000
        const val IMAGE_PICK_CODE = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Action when click camera button
        binding.btnInputCamera.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }

        //Action when click gallery button
        binding.btnInputGallery.setOnClickListener{
            val pickPhotoIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhotoIntent, IMAGE_PICK_CODE)
        }
    }

        override fun onActivityResult(requestCode:Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode,resultCode, data)

            //gallery action result
            if(requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null){
                val selectedImageUri: Uri = data.data!! //image selected
                binding.imgInput.setImageURI(selectedImageUri) ////set img to ImageView
            }
            // photo action result
            else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
                val selectedImagePhoto = data?.extras?.get("data") as Bitmap //photo selected
                binding.imgInput.setImageBitmap(selectedImagePhoto) //set img to ImageView
            }
        }
}