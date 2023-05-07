package com.example.submissionapp.ui.add_story

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.data.remote.network.ApiConfig
import com.example.submissionapp.data.remote.response.FileUploadResponse
import com.example.submissionapp.databinding.ActivityAddStoryBinding
import com.example.submissionapp.ui.home.HomeActivity
import com.example.submissionapp.ui.home.HomeViewModel
import com.example.submissionapp.utils.convUriToFile
import com.example.submissionapp.utils.createTemporaryFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddStoryBinding
    private lateinit var currPhotoPath : String
    private lateinit var tokenPreferences: TokenPreferences

    private var getFile: File? = null

    companion object{
        const val TAG = "AddStoryActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tokenPreferences = TokenPreferences(this)

        //Action when click camera button
        binding.btnInputCamera.setOnClickListener{
            photoAction()
        }

        //Action when click gallery button
        binding.btnInputGallery.setOnClickListener{
            galleryAction()
        }

        //Action when click the upload button
        binding.btnUpload.setOnClickListener {
            addStory(tokenPreferences.getToken())
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

    //Melakukan Action Photo
    private fun photoAction(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        Log.d("AddStoryActivity","Intent Camera berhasil di eksekusi")
        createTemporaryFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddStoryActivity,
                "com.example.submissionapp",
                it
            )
        currPhotoPath = it.absolutePath
        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == RESULT_OK){
            val myFile = File(currPhotoPath)
            getFile = myFile

            val result = BitmapFactory.decodeFile(getFile?.path)
            binding.imgInput.setImageBitmap(result)
        }
    }

    //Melakukan Action gallery
    private fun galleryAction(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == RESULT_OK){
            val selectedImage: Uri = it.data?.data as Uri
            val myFile = convUriToFile(selectedImage, this@AddStoryActivity)
            getFile = myFile
            binding.imgInput.setImageURI(selectedImage)
        }
    }

    private fun addStory(token:String?){
        if (getFile != null){
            val file = getFile
            val descriptionText = binding.editTextInputDesc.text.toString()
            val description = descriptionText.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file?.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file?.name,
                requestImageFile!!
            )

            val client = ApiConfig.getApiService().uploadStory("Bearer $token",imageMultipart,description)
            Log.d("AddStoryActivity","print $token, $imageMultipart, $description")
            client.enqueue(object : Callback<FileUploadResponse>{
                override fun onResponse(
                    call: Call<FileUploadResponse>,
                    response: Response<FileUploadResponse>
                ) {
                    if(response.isSuccessful){
                        Log.d(TAG,"Successful : Story berhasil diupload")
                    }else {
                        Log.e(TAG, "onFailure: Story gagal diupload")
                    }
                }

                override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                    Log.d(TAG,t.message.toString())
                    Log.d(TAG,"Error : Gatau kenapa")
                }

            })
        }
    }
}