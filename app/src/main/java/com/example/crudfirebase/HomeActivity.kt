package com.example.crudfirebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import com.example.crudfirebase.SetOnStudentClickListener
import com.example.crudfirebase.StudentModel
import com.example.crudfirebase.UpdateActivity
import com.example.crudfirebase.UserAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso
import java.util.UUID

class HomeActivity : AppCompatActivity(), SetOnStudentClickListener {

    val db = FirebaseFirestore.getInstance()
    private lateinit var userStudentAdapter: UserAdapter
    private lateinit var listView: ListView


    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        listView = findViewById(R.id.recycle)



        getStudentList()
        val id = UUID.randomUUID().toString()
        getStudent(id)

    }

    private fun getStudentList() {
        db.collection("student")
            .get()
            .addOnSuccessListener {result ->
                val userArray = ArrayList<StudentModel>()
                for (document in result){
                    val data = document.toObject(StudentModel::class.java)
                    userArray.add(data)
                }

                userStudentAdapter = UserAdapter(userArray, this, this)
                listView.adapter = userStudentAdapter
                Toast.makeText(this,"updated",Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }
            .addOnFailureListener {
                Toast.makeText(this,"updated fail",Toast.LENGTH_SHORT).show()
                return@addOnFailureListener
            }
    }

    fun getStudent(id: String) {
        db.collection("student").document(id).get()
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

    override
    fun onUpdateClick(student: StudentModel) {
        val intent = Intent(this,UpdateActivity::class.java)
        intent.putExtra("key_id", student.id.toString())
        intent.putExtra("key_name", student.name.toString())
        intent.putExtra("key_email", student.email.toString())
        intent.putExtra("key_number", student.number.toString())
        startActivity(intent)
    }

    override fun onDeleteClick(student: StudentModel) {
        deleteStudent(student.id.toString())
    }
    fun deleteStudent(id: String){
        db.collection("student").document(id).delete()
            .addOnSuccessListener {
                Toast.makeText(this,"deleted",Toast.LENGTH_SHORT).show()
                DeleteStudentList()
            }
            .addOnFailureListener {
                Toast.makeText(this,"deleted fail",Toast.LENGTH_SHORT).show()
            }
    }

    private fun DeleteStudentList() {
        db.collection("student").get()
            .addOnSuccessListener {
                val data = it.toObjects(StudentModel::class.java)

                userStudentAdapter = UserAdapter(data, this, this)
                listView.adapter = userStudentAdapter

                Toast.makeText(this,"file delete",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"file delete fail",Toast.LENGTH_SHORT).show()
            }
    }
}
