package com.cmpsc475.ironwork;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobViewModel extends AndroidViewModel {
    private JobDao jobDao;
    private ExecutorService executorService;
    private List<Job> allJobs;

    public JobViewModel(Application application) {
        super(application);
        jobDao = AppDatabase.getInstance(application).JobDao();
        executorService = Executors.newSingleThreadExecutor();
        allJobs = jobDao.getAllJobs();
    }

    public List<Job> getAllJobs() {
        return allJobs;
    }


    void insert(Job job) {
        executorService.execute(() -> jobDao.save(job));
    }

    void saveJob(Job job) {
        executorService.execute(() -> jobDao.save(job));
    }

    void deleteJob(Job job) {
        executorService.execute(() -> jobDao.delete(job));
    }

    public void insertAll(Job... jobs) {
        executorService.execute(() -> jobDao.insertAll(jobs));
    }
}
