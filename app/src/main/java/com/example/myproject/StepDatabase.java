package com.example.myproject;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {StepEntity.class}, version = 2,exportSchema = false)
public abstract class StepDatabase extends RoomDatabase {
public abstract StepDao stepDao();

private  static  volatile  StepDatabase INSTANCE;
static  StepDatabase getDataBase (final Context context)
{
    if(INSTANCE ==null)
    {
        synchronized (StepDatabase.class){
            if(INSTANCE == null)
            {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),StepDatabase.class,"step_database").build();
            }
        }
    }
    return INSTANCE;
}

}
