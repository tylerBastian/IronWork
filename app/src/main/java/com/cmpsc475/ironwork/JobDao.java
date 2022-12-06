package com.cmpsc475.ironwork;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JobDao {

    @Query("SELECT * FROM jobs WHERE jobTitle LIKE :jobTitle")
    Job findByTitle(String jobTitle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Job job);

    @Insert
    void insertAll(Job... jobs);

    @Delete
    void delete(Job job);

    @Query("Select * from jobs")
    List<Job> getAllJobs();

}
