package com.example.myproject;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public  interface StepDao {
    @Query("SELECT * FROM StepEntity")
List <StepEntity> getAll();
    @Query("SELECT * FROM StepEntity WHERE Steps == :Step limit 1 ")
    StepEntity findBySteps(Integer Step);
    @Query("SELECT * FROM StepEntity WHERE userid == :id limit 1 ")
    StepEntity findByuserId(Integer id);
    @Insert
    void insertAll(StepEntity... stepentity);
    @Insert
    long insert(StepEntity stepEntity);
    @Update(onConflict = REPLACE)
    void updateStep(StepEntity steps);

    @Query("DELETE FROM StepEntity")
    void deleteAll();

}
