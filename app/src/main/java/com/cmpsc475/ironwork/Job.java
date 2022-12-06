package com.cmpsc475.ironwork;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "jobs")
public class Job {
    @PrimaryKey(autoGenerate = true)
    private int jobID;
    private String jobTitle;
    private String jobDescription;
    private String jobLocation;
    private String jobPay;
    private String jobDate;
    private String jobTime;
    private String jobDuration;
    private String jobContact;
    private String jobContactPhone;
    private String jobContactEmail;

    public Job(int jobID, String jobTitle, String jobDescription, String jobLocation, String jobPay, String jobDate, String jobTime, String jobDuration, String jobContact, String jobContactPhone, String jobContactEmail) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobLocation = jobLocation;
        this.jobPay = jobPay;
        this.jobDate = jobDate;
        this.jobTime = jobTime;
        this.jobDuration = jobDuration;
        this.jobContact = jobContact;
        this.jobContactPhone = jobContactPhone;
        this.jobContactEmail = jobContactEmail;
    }

    public int getJobID() {
        return jobID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public String getJobPay() {
        return jobPay;
    }

    public String getJobDate() {
        return jobDate;
    }

    public String getJobTime() {
        return jobTime;
    }

    public String getJobDuration() {
        return jobDuration;
    }

    public String getJobContact() {
        return jobContact;
    }

    public String getJobContactPhone() {
        return jobContactPhone;
    }

    public String getJobContactEmail() {
        return jobContactEmail;
    }
}
