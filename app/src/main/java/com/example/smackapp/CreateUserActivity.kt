package com.example.smackapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_create_user.*
import java.net.URI
import java.util.*

class CreateUserActivity : AppCompatActivity() {
    var userAvatar = "profileDefault"
    var colorAvatar = "[0.5,0.5,0.5,1]"
    private lateinit var auth:FirebaseAuth
    private  lateinit var storage:FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

    }
    fun imgCreateavatarClick(view: View){
        val random = Random()
        val color = random.nextInt(2)
        var avatar = random.nextInt(28)
        if (color == 0){
            userAvatar = "light$avatar"
        }else{
            userAvatar = "dark$avatar"
        }
        val resourceId = resources.getIdentifier(userAvatar,"drawable",packageName)
        imgavatarCreate.setImageResource(resourceId)
    }
    fun btnCreatebackgoungcolorClick(view: View){
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)
        imgavatarCreate.setBackgroundColor(Color.rgb(r,g,b))
        val saveR = r.toDouble()/255
        val saveG = r.toDouble()/255
        val saveB = r.toDouble()/255
        colorAvatar = "[$saveR,$saveG,$saveB,1]"
        println(colorAvatar)

    }
    fun btnCreateUserClick(view: View){
      //  val user = edtuserCreate.text.toString()
        val email = edtemailCreate.text.toString()
        val password = edtpasswordCreate.text.toString()
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"email or password is empty",Toast.LENGTH_SHORT).show()
        }
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){ task ->
            if (task.isSuccessful){
                Log.d("Main","create successfully")
            }
        }.addOnFailureListener {
            Log.d("Main","error")
        }
        

       }
    val uri:URI? = null
    fun upLoadImage(){
        val filename = UUID.randomUUID().toString()
        storage = FirebaseStorage.getInstance()
        val ref = storage.getReference("/image/$filename")
        ref.putFile(userAvatar).addOnCompleteListener(this){

        }

    }
    }
