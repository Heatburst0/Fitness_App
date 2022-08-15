package com.kv.workoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kv.workoutapp.databinding.ActivityExerciseBinding
import com.kv.workoutapp.databinding.ActivityMainBinding
import com.kv.workoutapp.databinding.Dialogue2Binding
import com.kv.workoutapp.databinding.DialogueBinding
import kotlinx.android.synthetic.main.activity_exercise.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private var start : Boolean = false
    private var restProgress = 0
    private var tts : TextToSpeech?=null
    private var binding : ActivityExerciseBinding?=null
    private var restTimer : CountDownTimer?=null
    private var exerciseList : ArrayList<Exerscise>?=null
    private var currentExercise=-1
    private var player : MediaPlayer?=null
    private var adapter : ExerciseAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbar?.setNavigationOnClickListener {
            customBackDialog()
        }
        tts=TextToSpeech(this,this)
        exerciseList = Constants.exerciseList()
        binding?.strt?.setOnClickListener {
            if(!start){
                setupRestView()
                start=true
            }else{
                customSkip()
            }
        }
        setupRecycleView()
    }

    private fun customSkip() {
        val dialog = Dialog(this)
        val Dialogbinding =Dialogue2Binding.inflate(layoutInflater)
        dialog.setContentView(Dialogbinding.root)
        dialog.setCanceledOnTouchOutside(true)
        Dialogbinding.agreeBtn.setOnClickListener {
            setupRestView()
            dialog.dismiss()
        }
        Dialogbinding.denyBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onBackPressed() {
        customBackDialog()
//        super.onBackPressed()
    }

    private fun customBackDialog() {
        val dialog = Dialog(this)
        val dialogbinding  = DialogueBinding.inflate(layoutInflater)
        dialog.setContentView(dialogbinding.root)
        dialog.setCanceledOnTouchOutside(true)
        dialogbinding.agreeBtn.setOnClickListener {
            this@ExerciseActivity.finish()
            dialog.dismiss()
        }
        dialogbinding.denyBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setupRecycleView(){
        binding?.rvExercise?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        adapter = ExerciseAdapter(exerciseList!!)
        rvExercise.adapter =adapter
    }
    private var switch=false
    private fun speak(str : String){
        tts?.speak(str,TextToSpeech.QUEUE_FLUSH,null,"")
    }
    private fun setupRestView() {

        /**
         * Here firstly we will check if the timer is running the and it is not null then cancel the running timer and start the new one.
         * And set the progress to initial which is 0.
         */
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        // This function is used to set the progress details.
        setRestProgressBar()
    }
    private fun setRestProgressBar() {
        currentExercise++
        if(currentExercise<exerciseList!!.size){
            restProgress=0
            binding?.up?.visibility= View.VISIBLE
            binding?.upText?.visibility= View.VISIBLE
            binding?.upText?.text=exerciseList!![currentExercise].getName()
//            speak("Upcoming Exercise"+" "+exerciseList!![currentExercise].getName())
            binding?.proggBar?.progress = restProgress
            restTimer = object : CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    restProgress++ // It is increased by 1
                    binding?.proggBar?.max=10
                    binding?.proggBar?.progress = 10 - restProgress // Indicates progress bar progress
                    binding?.Timer?.text =
                        (10 - restProgress).toString()  // Current progress is set to text view in terms of seconds.
                }

                override fun onFinish() {
                    binding?.up?.visibility= View.INVISIBLE
                    binding?.upText?.visibility= View.INVISIBLE
                    if(currentExercise<exerciseList!!.size){
                        binding?.text?.setText(exerciseList!![currentExercise].getName())
                        binding?.img?.setImageResource(exerciseList!![currentExercise].image)
                        exerciseList!![currentExercise].isSelected=true
                        adapter?.notifyDataSetChanged()
                        binding?.proggBar?.max=30
                        binding?.proggBar?.progress=300
                        switch=true
                        restProgress=0
                        restTimer=object : CountDownTimer(30000,1000){
                            override fun onTick(millisUntilFinished: Long) {
                                restProgress++
                                binding?.proggBar?.progress =
                                    30 - restProgress // Indicates progress bar progress
                                binding?.Timer?.text =
                                    (30 - restProgress).toString()
                            }

                            override fun onFinish() {
                                exerciseList!![currentExercise].isSelected=false
                                exerciseList!![currentExercise].isCompleted=true
                                adapter?.notifyDataSetChanged()
                                try{
                                    val sounduri= Uri.parse("android.resource://com.kv.workoutapp/"+R.raw.finish)
                                    player=MediaPlayer.create(applicationContext,sounduri)
                                    player?.isLooping=false
                                    player?.start()
                                }catch(e : Exception){
                                    e.printStackTrace()
                                }
                                setRestProgressBar()
                            }
                        }.start()
                    }
                }
            }.start()
        }else{
            val intent = Intent(this,finish_screen::class.java)
            startActivity(intent)
        }
    }
    override fun onInit(status : Int){
        if(status==TextToSpeech.SUCCESS){
            val result=tts!!.setLanguage(Locale.ENGLISH)
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Doosi lang choose kar")
            }else{
                Log.e("tts","Code bekar h")
            }
        }
    }
    public override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        super.onDestroy()
        if(tts!=null){
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }
}