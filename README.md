# JobScheduler

This is sample application to experiment with Google's JobScheduler/JobService API

IMHO API and documentation of it is fucked up.

All stack-overflow discussions around JobService/JobScheduler indicate that background job can not be scheduled with 
duration shorter that 15 minutes, starting Android N. I have yet to see clear statement indicating as such in google API documentation.

It seems like calling `jobFinished(params,true)` should re-schdule the job, which never seems to happen, one has to explicitly call
jobScheduler.schedule()

