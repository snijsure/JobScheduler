# JobScheduler

This is sample application to experiment with Google's JobScheduler/JobService API
IMHO API and documentation of it is fucked up.
All stack-overflow discussion indicate that background job can not be scheduled with duration shorter that 15 minutes starting Android N
I have yet to see clear statement indicating as such in google API documentation.

- Also the job is supposed to get rescheduled if one calls `jobFinished(params,true)` which never seems to happen.

