package com.kv.workoutapp

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.core.widget.addTextChangedListener
import com.kv.workoutapp.databinding.ActivityBmiBinding
import java.lang.Exception
import kotlin.math.roundToInt

class BMIActivity : AppCompatActivity(){
    private var toggle : Boolean=true
    private var text : TextWatcher?=null
    private var binding : ActivityBmiBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmi)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarBmi?.setNavigationOnClickListener {
            onBackPressed()
        }
        text=object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onCheck()
            }

            override fun afterTextChanged(s: Editable?) {
                onCheck()
            }

        }
        binding?.inputWeight?.addTextChangedListener(text)
        binding?.inputHeight?.addTextChangedListener(text)
        binding?.inputWeightlbs?.addTextChangedListener(text)
        binding?.inputFeet?.addTextChangedListener(text)
        binding?.inputInch?.addTextChangedListener(text)
        binding?.calculate?.setOnClickListener {
//            if(binding?.inputWeight?.text.toString().isEmpty()){
//                Toast.makeText(this,"Please enter your weight",Toast.LENGTH_LONG).show()
//            }
//            if(binding?.inputHeight?.text.toString().isEmpty()){
//                Toast.makeText(this,"Please enter your height",Toast.LENGTH_LONG).show()
//            }
//            if(binding?.inputHeight?.text!!.isNotEmpty() && !binding?.inputWeight?.text!!.isEmpty()){
                try{
                    if(toggle){
                        calculateBmi(binding?.inputWeight?.text.toString().toFloat(),binding?.inputHeight?.text.toString().toFloat())
                    }else{
                        usmetrics(binding?.inputWeightlbs?.text.toString().toFloat(),binding?.inputFeet?.text.toString().toFloat(),binding?.inputInch?.text.toString().toFloat())
                    }
                }catch(e : Exception){
                    Toast.makeText(this,"Please enter the required information",Toast.LENGTH_LONG).show()
                }

        }
        binding?.UsSystem?.setOnClickListener {
            binding?.resLayout?.visibility=View.INVISIBLE
            binding?.inputWeight?.text?.clear()
            binding?.inputHeight?.text?.clear()
            binding?.height?.visibility=View.GONE
            binding?.Usmetrics?.visibility=View.VISIBLE
            binding?.weightlbs?.visibility=View.VISIBLE
            binding?.weight?.visibility=View.GONE
            toggle=false
        }
        binding?.metric?.setOnClickListener {
            binding?.inputFeet?.text?.clear()
            binding?.inputWeightlbs?.text?.clear()
            binding?.inputInch?.text?.clear()
            binding?.resLayout?.visibility=View.INVISIBLE
            binding?.height?.visibility=View.VISIBLE
            binding?.Usmetrics?.visibility=View.GONE
            binding?.weightlbs?.visibility=View.GONE
            binding?.weight?.visibility=View.VISIBLE
            toggle=true
        }
    }
    private fun onCheck(){
        if((binding?.inputWeight?.text.toString().isNotEmpty() && binding?.inputHeight?.text.toString().isNotEmpty()) || (binding?.inputWeightlbs?.text.toString().isNotEmpty() && binding?.inputFeet?.text.toString().isNotEmpty() && binding?.inputInch?.text.toString().isNotEmpty())){
            binding?.calculateUnpresssed?.visibility=View.GONE
            binding?.calculate?.visibility=View.VISIBLE
        }else{
            binding?.calculateUnpresssed?.visibility=View.VISIBLE
            binding?.calculate?.visibility=View.GONE
        }
    }
    private fun usmetrics(weight : Float,feet : Float,inches: Float){
        val weight =weight*0.453592
        val height=feet*30.48+inches*2.54
        calculateBmi(weight.toFloat(), height.toFloat())
    }

    private fun calculateBmi(weight: Float, height: Float) {
        val h=height/100
        val result=(weight/(h*h)*100.0).roundToInt()/100.0
        binding?.resLayout?.visibility= View.VISIBLE
        binding?.res?.text="$result"
        if (result in 18.5..24.9){
            binding?.remark?.text="Perfect"
            binding?.suggest?.text="Sahi h londe/londiya"
        }else if(result<18.5){
            binding?.remark?.text="UnderWeight"
            binding?.suggest?.text="Khana khaye kar"
        }else if(result>=25){
            binding?.remark?.text="Overweight"
            binding?.suggest?.text="Bhai kam kar le thoda khana"
        }
    }


}