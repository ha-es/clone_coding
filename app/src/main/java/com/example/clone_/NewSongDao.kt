package com.example.clone_

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NewSongDao {

    private var databaseReference: DatabaseReference?=null

    init{
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference("title")

    }

    //등록
    fun add(song: Song): Task<Void>{
        return databaseReference!!.push().setValue(song)
    }


    //삭제
    fun delete(song: Song): Task<Void>{
        return databaseReference!!.removeValue()
    }

}