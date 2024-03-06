package com.example.crudfirebase

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class UserAdapter (
    private val user: List<StudentModel>,
    private val context: Context, private val onStudentClickListener:SetOnStudentClickListener ): BaseAdapter(){

    override fun getCount(): Int {
       return user.size
    }

    override fun getItem(position: Int): Any {
        return user[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    @SuppressLint("ViewHolder", "MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view=LayoutInflater.from(context).inflate(R.layout.home_layout_xml,parent,false)

        val name=view.findViewById<TextView>(R.id.userName)
        val email=view.findViewById<TextView>(R.id.userEmail)
        val number=view.findViewById<TextView>(R.id.userNumber)
        val images=view.findViewById<ImageView>(R.id.userimage_imageView)

        val update=view.findViewById<ImageView>(R.id.updateBtn)
        val delete=view.findViewById<ImageView>(R.id.delete)

        name.text=user[position].name
        email.text=user[position].email
        number.text=user[position].number

        val storage = FirebaseStorage.getInstance().reference
        storage.child(user[position].chaild!!)
            .downloadUrl
            .addOnSuccessListener {
                Picasso.get().load(it)
                    .into(images)
                Toast.makeText(context, "download image Success", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }
            .addOnFailureListener {
                Toast.makeText(context, "download image failed", Toast.LENGTH_SHORT).show()
                return@addOnFailureListener
            }
        delete.setOnClickListener {
            onStudentClickListener.onDeleteClick(user[position])
        }

        update.setOnClickListener {
            onStudentClickListener.onUpdateClick(user[position])
        }
        return view
        }
    }

interface SetOnStudentClickListener{

    fun onUpdateClick(student:StudentModel)
    fun onDeleteClick(student: StudentModel)
}
