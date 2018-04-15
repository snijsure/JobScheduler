package com.snijsure.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobInfo.BACKOFF_POLICY_LINEAR
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.util.Log

object JobSchedulerHelper {
    val JOB_ID = 1001
    private val TAG = JobSchedulerHelper::class.java.simpleName
    private var jobScheduler : android.app.job.JobScheduler? = null
    private var jobInfo: JobInfo? = null
    private var refreshInterval = (15 * 60 * 1000).toLong()

    fun initialize(context: Context) {
        val name = ComponentName(context,JobScheduler::class.java)
        val builder = JobInfo.Builder(JOB_ID,name)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            refreshInterval = JobInfo.getMinPeriodMillis()
        Log.d(TAG, "Job scheduled refresh interval $refreshInterval")

        builder.setMinimumLatency(refreshInterval)
        builder.setBackoffCriteria(5000, BACKOFF_POLICY_LINEAR)
        builder.setOverrideDeadline(refreshInterval)

        jobInfo = builder.build()
        jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as android.app.job.JobScheduler?
    }

    fun startJob() {
        val result = jobScheduler?.schedule(jobInfo)
        result?.let {
            Log.d(TAG, "Job scheduler result $it")
            printAllPendingJobs()
        }
    }

    fun stopJob() {
        jobScheduler?.cancel(JOB_ID)
        printAllPendingJobs()
    }

    fun printAllPendingJobs() {
        val jobList = jobScheduler?.allPendingJobs
        Log.d(TAG, " All pending jobs size ${jobList?.size} and list $jobList")
    }
}