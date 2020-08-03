package com.ytlabs.mytodoapp.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.ytlabs.mytodoapp.BuildConfig
import com.ytlabs.mytodoapp.R
import com.ytlabs.mytodoapp.utils.AppConstant
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNotesActivity : AppCompatActivity() {
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var imageViewNotes: ImageView
    lateinit var buttonSubmit: Button
    private val REQUEST_CODE_GALLERY = 2
    private val REQUEST_CODE_CAMERA = 1
    var picturePath: String? = ""
    private val MY_PERMISSION_CODE = 124

    private val TAG = "AddNotesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        bindViews()
        clickListeners()
    }

    private fun clickListeners() {
        imageViewNotes.setOnClickListener {
            if (checkAndRequestPermission()) {
                setupDialog()
            }
        }

        buttonSubmit.setOnClickListener {
            val intent = Intent()
            intent.putExtra(AppConstant.TITLE, editTextTitle.text.toString())
            intent.putExtra(AppConstant.DESCRIPTION, editTextDescription.text.toString())
            intent.putExtra(AppConstant.IMAGE_PATH, picturePath)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun checkAndRequestPermission(): Boolean {
        val cameraPermission =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val listPermissionNeeded = ArrayList<String>()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(android.Manifest.permission.CAMERA)
        }
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (listPermissionNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionNeeded.toTypedArray<String>(),
                MY_PERMISSION_CODE
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            MY_PERMISSION_CODE->{
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    setupDialog()
                }
            }
        }
    }

    private fun setupDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_selector, null)
        val textViewCamera: TextView = view.findViewById(R.id.textViewCamera)
        val textViewGallery: TextView = view.findViewById(R.id.textViewGallery)
        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(true)
            .create()

        textViewCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var photoFile: File? = null
            photoFile = createImage()
            if(photoFile != null){
                val photoURI = FileProvider.getUriForFile(this@AddNotesActivity, BuildConfig.APPLICATION_ID + ".provider", photoFile)
                picturePath = photoFile.absolutePath
                Log.d(TAG, "$picturePath: PICTURE PATH ")
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
                dialog.hide()
            }
        }

        textViewGallery.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            this.startActivityForResult(intent, REQUEST_CODE_GALLERY)
            dialog.hide()
        }
        dialog.show()
    }

    private fun createImage(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName = "JPEG_$timeStamp" + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDir)



    }

    private fun bindViews() {
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        imageViewNotes = findViewById(R.id.imageViewNotes)
        buttonSubmit = findViewById(R.id.buttonSubmit)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_GALLERY -> {
                    val selectedImage = data?.data
                    val filePath = arrayOf(MediaStore.Images.Media.DATA)
                    val c = selectedImage?.let { contentResolver.query(it, filePath, null, null,null) }
                    c?.moveToFirst()
                    val columnIndex = c?.getColumnIndex(filePath[0])
                    picturePath = columnIndex?.let { c?.getString(it) }
                    c?.close()
                    Log.d(TAG, "$picturePath: ");
                    Glide.with(this).load(picturePath).into(imageViewNotes)
                }
                REQUEST_CODE_CAMERA -> {
                    Glide.with(this).load(picturePath).into(imageViewNotes)
                }
            }
        }
    }
}