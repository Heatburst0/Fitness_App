package com.kv.workoutapp

class Exerscise(
    var id: Int,
    private var name: String,
    var image: Int,
    var isCompleted: Boolean,
    var isSelected : Boolean
) {
//    @JvmName("getName1")
    fun getName():String{
        return name
    }
    fun setName(name: String){
        this.name=name
    }

}