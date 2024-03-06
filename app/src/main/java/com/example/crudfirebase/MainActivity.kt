package com.example.crudfirebase

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso
import java.util.UUID

class MainActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var number: EditText
    lateinit var addBT: Button
    lateinit var image: ImageView

    val chaild = "image/${UUID.randomUUID()}.png"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.enterName_EditText)
        email = findViewById(R.id.enterEmail_EditText)
        number = findViewById(R.id.phoneNo_EditText)
        addBT = findViewById(R.id.addBtn_Button)

        image = findViewById(R.id.image_imageView)

        addBT.setOnClickListener {
            addStudent()
        }

        image.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            imageLauncher.launch(intent)
        }
    }

    val imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null) {
                    val ref = Firebase.storage.reference.child(chaild)

                    ref.putFile(it.data!!.data!!).addOnSuccessListener {
                        ref.downloadUrl.addOnSuccessListener { uri ->

                            val imageUrl = uri.toString()
//                        image.setImageURI(it)
                            Toast.makeText(this@MainActivity, "Photo uploaded", Toast.LENGTH_LONG)
                                .show()
//                        Picasso.get().load(it.toString()).into(image)
                            Picasso.get().load(imageUrl).into(image).apply {
                                intent.putExtra("imageUri", imageUrl)
                            }

                        }
                    }
                }
            }
        }

    fun addStudent() {
        val username = name.text.toString()
        val usernumber = number.text.toString()
        val useremail = email.text.toString()

        val uid = UUID.randomUUID().toString()
        val hasmap =
            hashMapOf(
                "name" to username,
                "number" to usernumber,
                "email" to useremail,
                "id" to uid,
                "chaild" to chaild
            )

        db.collection("student").document(uid).set(hasmap)
            .addOnSuccessListener {
                Toast.makeText(this, "Data adding completed", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(this, "Data adding fail", Toast.LENGTH_SHORT).show()
            }
    }
}
