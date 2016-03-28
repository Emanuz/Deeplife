package com.gcme.deeplife;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.Toast;

import com.gcme.deeplife.SyncService.SyncService;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class DeepLife extends Application {
    private static final int JOB_ID = 100;
    private JobScheduler myJobScheduler;
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this,SyncService.class);
        startService(intent);
        myJobScheduler  = JobScheduler.getInstance(this);
        JobConstr();
    }
    public void JobConstr(){
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this,SyncService.class));
        builder.setPeriodic(5000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        myJobScheduler.schedule(builder.build());
    }
}