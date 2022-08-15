package com.kv.workoutapp

object Constants {
    fun exerciseList(): ArrayList<Exerscise>{
        val exercise=ArrayList<Exerscise>()
//        val pullUps=
        exercise.add(Exerscise(
            1,
            "Pull ups",
            R.drawable.pull_ups,
            false,
            false
        ))
        exercise.add(
            Exerscise(
                2,
                "Plank",
                R.drawable.plank,
                false,
                false
            )
        )
        exercise.add(
            Exerscise(
                3,
                "Push-Up",
                R.drawable.push_ups,
                false,
                false
            )
        )
        exercise.add(
            Exerscise(
                4,
                "Squats",
                R.drawable.squats,
                false,
                false
            )
        )
        return exercise
    }
}