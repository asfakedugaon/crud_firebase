package com.example.crudfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class UpdateActivity : AppCompatActivity() {

    private lateinit var updateNameEditText: EditText
    private lateinit var updateEmailEditText: EditText
    private lateinit var updateNumberEditText: EditText
    private lateinit var updateBt: Button
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        updateBt = findViewById(R.id.update)
        updateNameEditText = findViewById(R.id.update_EditName)
        updateEmailEditText = findViewById(R.id.update_EditEmail)
        updateNumberEditText = findViewById(R.id.update_EditNumber)

        val id = intent.extras?.getString("key_id")
        val name = intent.extras?.getString("key_name")
        val email = intent.extras?.getString("key_email")
        val number = intent.extras?.getString("key_number")

        updateNameEditText.setText(name)
        updateEmailEditText.setText(email)
        updateNumberEditText.setText(number)

        updateBt.setOnClickListener {

            val sName = updateNameEditText.text.toString()
            val sEmail = updateEmailEditText.text.toString()
            val sNumber = updateNumberEditText.text.toString()

            val updateMap = mapOf(
                "name" to sName,
                "number" to sEmail,
                "email" to sNumber,
            )

            db.collection("student").document("$id").update(updateMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Update SuccessFull", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Update fail", Toast.LENGTH_SHORT).show()
                }
        }
    }
}