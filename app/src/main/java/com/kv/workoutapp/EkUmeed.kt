package com.kv.workoutapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class EkUmeed(context : Context) : SQLiteOpenHelper(context,"UserDb",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE history(date TEXT PRIMARY KEY)")
//        db?.execSQL("INSERT INTO history(date,UNAME,PWD) VALUES('29-Nov-12','blabla','bla')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}