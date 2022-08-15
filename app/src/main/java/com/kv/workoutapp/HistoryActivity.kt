package com.kv.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Index
import com.kv.workoutapp.databinding.ActivityHistoryBinding
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding?=null
    private val lis = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarHistory)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
        getAllData()
        binding?.clrbtn?.setOnClickListener {
            clear()
        }
    }
    private fun getAllData(){
        val helper=EkUmeed(applicationContext)
        val db = helper.readableDatabase
        val res=db.rawQuery("select * from history",null)
        while(res.moveToNext()){
            lis.add(res.getString(0))
        }
        res.close()
        if(lis.isNotEmpty()){
            binding?.historyRecycleView?.visibility= VISIBLE
            binding?.txt?.visibility= GONE
            binding?.historyRecycleView?.layoutManager=LinearLayoutManager(this)
            binding?.clrbtn?.visibility= VISIBLE
            val historyAdapter=HistoryAdapter(lis)
            binding?.historyRecycleView?.adapter=historyAdapter
        }else{
            binding?.historyRecycleView?.visibility= GONE
            binding?.txt?.visibility= VISIBLE
            binding?.clrbtn?.visibility=GONE
        }

    }
    private fun clear(){
        val helper =EkUmeed(applicationContext)
        val db = helper.readableDatabase
        db.execSQL("delete from history")
        lis.clear()
        getAllData()
        Toast.makeText(this,"History cleared",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}