package com.example.clone_

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
class User (
    var email : String,
    var password : String
    ){
    @PrimaryKey(autoGenerate = true) var id : Int=0
}
