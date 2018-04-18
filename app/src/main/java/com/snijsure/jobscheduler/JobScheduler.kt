package com.snijsure.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.math.BigInteger


class JobScheduler : JobService() {
    var TAG = JobScheduler::class.java.simpleName
    private var currentComputationJob: Job? = null

    override fun onStartJob(params: JobParameters?): Boolean {
        computeFib(params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Cancel pending computational job")
        currentComputationJob?.cancel()
        JobSchedulerHelper.printAllPendingJobs()
        return true
    }

    private fun fib(i: BigInteger): BigInteger {
        tailrec fun go(k: BigInteger, p: BigInteger, c: BigInteger): BigInteger {
            return if (k == BigInteger("0")) p
            else go(k - BigInteger("1"), c, p + c)
        }

        return go(i, BigInteger("0"), BigInteger("1"))
    }

    private fun computeFib(params: JobParameters?) {

        currentComputationJob = launch(UI) {
            val starTime = System.currentTimeMillis()
            val result = async(CommonPool) {
                fib(BigInteger("1000000"))
            }.await()
            val endTime = System.currentTimeMillis()
            val delta = endTime - starTime;
            val strToLong = result.toString()
            Log.d(TAG, "Fibonacci computation took $delta ms, result -> $strToLong ")
            jobFinished(params, true)
            JobSchedulerHelper.printAllPendingJobs()
        }
    }
}




