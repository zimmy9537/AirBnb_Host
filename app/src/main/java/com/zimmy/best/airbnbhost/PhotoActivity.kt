package com.zimmy.best.airbnbhost

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.zimmy.best.airbnbhost.Konstants.Konstants
import com.zimmy.best.airbnbhost.databinding.ActivityPhotoBinding
import com.zimmy.best.airbnbhost.model.BasicDetails
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class PhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoBinding
    private var count = 0
    private var resultLauncher: ActivityResultLauncher<Intent>? = null
    private val PERMISSION_REQUEST_CODE = 1
    private val CAMERA_REQUEST = 1888
    private lateinit var imageUri: Uri
    private lateinit var imageUriString: String
    private lateinit var basicData:BasicDetails
    private lateinit var roomList:ArrayList<String>
    private lateinit var detailMap:HashMap<String,Boolean>
    private lateinit var photoList:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        basicData=intent.getSerializableExtra(Konstants.DATA) as BasicDetails
        roomList=intent.getStringArrayListExtra(Konstants.DATA2) as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */
        detailMap=intent.getSerializableExtra(Konstants.DATA3) as HashMap<String, Boolean> /* = java.util.HashMap<kotlin.String, kotlin.Boolean> */


        binding.saveAndNext.setOnClickListener {
            if (count == 0) {
                Toast.makeText(this, "Select atleast one image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this@PhotoActivity, CalenderActivity::class.java)
            intent.putExtra(Konstants.DATA,basicData)
            intent.putExtra(Konstants.DATA2,roomList)
            intent.putExtra(Konstants.DATA3,detailMap)
            intent.putExtra(Konstants.DATA4,photoList)
        }

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data != null) {
                    imageUri = result.data!!.data!!
                    imageUriString = imageUri.toString()
                    fillTheLinearLayout(imageUriString)
                }
            }
        }

        binding.addPhoto.setOnClickListener {
            val dialog = Dialog(this@PhotoActivity)
            dialog.setContentView(R.layout.dialog_picture_capture)
            val camera: Button = dialog.findViewById(R.id.camera) as Button
            val storage: Button = dialog.findViewById(R.id.storage) as Button
            storage.setOnClickListener(View.OnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                resultLauncher!!.launch(intent)
                dialog.dismiss()
            })
            camera.setOnClickListener(View.OnClickListener {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (ContextCompat.checkSelfPermission(
                        this@PhotoActivity,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getExternalFilesDir(Environment.DIRECTORY_DCIM)?.let { it1 ->
                        ImagePicker.with(this@PhotoActivity)
                            .cameraOnly()
                            .saveDir(it1)
                            .start(CAMERA_REQUEST)
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        this@PhotoActivity, arrayOf(Manifest.permission.CAMERA),
                        PERMISSION_REQUEST_CODE
                    )
                }
                dialog.dismiss()
            })
            Objects.requireNonNull(dialog.window)
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.data!!
                imageUriString = imageUri.toString()
                fillTheLinearLayout(imageUriString)
            }
        }
    }

    private fun fillTheLinearLayout(imageUriString: String) {
        val view = LayoutInflater.from(this@PhotoActivity)
            .inflate(R.layout.room_view, null, false)
        val resultTextView = view.findViewById<TextView>(R.id.roomTextView)
        resultTextView.text = "Photo ${++count} added"
        binding.photoLl.addView(view)
        photoList.add(imageUriString)

    }
}