package com.kv.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.kv.workoutapp.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        frameLayout.setOnClickListener {
            var intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }
        binding?.bmi?.setOnClickListener {
            val intent = Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }

        binding?.History?.setOnClickListener{
            startActivity(Intent(this,HistoryActivity::class.java))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}