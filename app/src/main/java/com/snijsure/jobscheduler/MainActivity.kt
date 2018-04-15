package com.snijsure.jobscheduler
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

         JobSchedulerHelper.initialize(applicationContext.applicationContext)
    }

    fun  clickJobSchedule(view: View) {
        JobSchedulerHelper.startJob()
        Snackbar.make(view,"Job Scheduled",Toast.LENGTH_LONG).show()
    }

    fun  clickStopJob(view: View) {
        JobSchedulerHelper.stopJob()
        Snackbar.make(view,"Job Cancelled",Toast.LENGTH_LONG).show()
    }
}
