package com.snijsure.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import java.math.BigInteger


class JobScheduler : JobService() {
    var TAG = JobScheduler::class.java.simpleName
    private var currentComputationJob: Job? = null

    override fun onStartJob(params: JobParameters?): Boolean {
        computerFib(params)
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Cancel pending computational job")
        currentComputationJob?.cancel()
        JobSchedulerHelper.printAllPendingJobs()
        return true
    }



    // Lame implementation of Fibonacci computation
    private fun iterativeFib(n:Long):Long {
        var result = 0L
        var  next = 1L
        for (i in 1..n) {
            val temporary: Long = result
            result = next
            next += temporary
        }
        return result
    }

    private fun computerFib(params: JobParameters?) {

        currentComputationJob = launch(UI) {
            val result = async(CommonPool) {
                iterativeFib(10000)
            }.await()
            val strToLong = result.toString()
            Log.d(TAG, "Fibonacci result -> $strToLong")
            jobFinished(params,true)
            // Strange if we don't explicitly do startJob this job only runs once
            // but if we call startJob explicitly it works, WTF!
            // I thought jobFinished(...,true) is supposed to reschedule the job
            JobSchedulerHelper.startJob()
            JobSchedulerHelper.printAllPendingJobs()
        }
    }
}




