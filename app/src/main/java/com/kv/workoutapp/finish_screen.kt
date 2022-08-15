package com.kv.workoutapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.kv.workoutapp.databinding.ActivityFinishScreenBinding
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class finish_screen : AppCompatActivity() {
    private var binding : ActivityFinishScreenBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.finishBtn?.setOnClickListener {
            val intent = Intent(this@finish_screen,MainActivity::class.java)
            startActivity(intent)
        }
        addData()

    }
    private fun addData(){
        val calendar=Calendar.getInstance()
        val dateTime = calendar.time
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
        val date = sdf.format(dateTime)
//        hIstoryDao.insert(WorkoutEntity(date))
        var helper=EkUmeed(applicationContext)
        var db = helper.readableDatabase
        lifecycleScope.launch {
//            db?.execSQL("TRUNCATE TABLE history")
            var cv = ContentValues()
            cv.put("date",date)
            db.insert("history",null,cv)
//            db.delete("history","DATE='29-Nov-12'",null)
//            db.execSQL("delete from history")

        }


    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}