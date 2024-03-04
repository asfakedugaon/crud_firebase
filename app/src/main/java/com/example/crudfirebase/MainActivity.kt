package com.example.crudfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class MainActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var number: EditText
    lateinit var addBT: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.enterName_EditText)
        email = findViewById(R.id.enterEmail_EditText)
        number = findViewById(R.id.phoneNo_EditText)
        addBT = findViewById(R.id.addBtn_Button)

        addBT.setOnClickListener {
            addStudent()
        }
    }

    fun addStudent() {
        val username = name.text.toString()
        val usernumber = number.text.toString()
        val useremail = email.text.toString()

        val uid = UUID.randomUUID().toString()

        val hasmap =
            hashMapOf("name" to username, "number" to usernumber, "email" to useremail, "id" to uid)

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
