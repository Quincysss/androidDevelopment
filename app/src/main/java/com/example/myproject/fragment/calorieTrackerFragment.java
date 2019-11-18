package com.example.myproject.fragment;

import android.support.v4.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myproject.Activity.MainActivity;
import com.example.myproject.R;
import com.example.myproject.RestClient;
import com.example.myproject.StepDatabase;
import com.example.myproject.StepEntity;
import com.example.myproject.model.Report;
import com.example.myproject.model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.support.v7.widget.AppCompatDrawableManager.get;

public class calorieTrackerFragment extends Fragment {
    View vMain;
    Users loginUser;
    TextView _calGoal;
    TextView _totalStep;
    TextView _burnCalorie;
    TextView _CalorieConsum;
    TextView _showPostServer;
    Button _btn_PostServer;
    Button _btn_calGoal;
    Button _btn_totalStep;
    Button _btn_burnCalorie;
    Button _btn_CalorieConsum;
    StepDatabase sb;
    String goalCalorie;
    Integer totalSteps =0;
    double totalSpendCalorie;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_calorie_tracker, container, false);
        _calGoal = vMain.findViewById(R.id.setCalView);
        _CalorieConsum = vMain.findViewById(R.id.calConsumView);
        _btn_calGoal = vMain.findViewById(R.id.btn_setCal);
        _totalStep = vMain.findViewById(R.id.showtotalstep);
        _burnCalorie = vMain.findViewById(R.id.showburncalorie);
        _showPostServer = vMain.findViewById(R.id.showpostserver);
        _btn_PostServer = vMain.findViewById(R.id.btn_postserver);
        _btn_totalStep = vMain.findViewById(R.id.btn_totalstep);
        _btn_burnCalorie = vMain.findViewById(R.id.btn_burncarlorie);
        _btn_CalorieConsum = vMain.findViewById(R.id.btn_calConsum);
        loginUser = ((MainActivity) getActivity()).getLoginUser();

        _btn_calGoal.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {

                SharedPreferences displayGoal = getActivity().getSharedPreferences("myGoal", Context.MODE_PRIVATE);
                goalCalorie = displayGoal.getString("message",null);

                _calGoal.setText(goalCalorie);

            }
       });
        _btn_burnCalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = loginUser.getUserid().toString();
                getCalorieBurnAsync getCaloriebrunAsync = new getCalorieBurnAsync();
                getCaloriebrunAsync.execute(userId);
            }
        });
        _btn_totalStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTotalStepAsync getTotalSteps = new getTotalStepAsync();
                getTotalSteps.execute();
            }

        });
        _btn_CalorieConsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String startTime = sdf.format(currentDate);
                String userId = loginUser.getUserid().toString();
                getCalorieConsumAsync getCalorieConsumAsync = new getCalorieConsumAsync();
                getCalorieConsumAsync.execute(userId,startTime);
            }
        });
        _btn_PostServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calorieBurnAsync = "";
                String numCalConsum = "";
                //String numTotalSteps = "";
                String tmp_userId = String.valueOf(loginUser.getUserid());
                Date currentDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String startTime = sdf.format(currentDate);
                getCalorieBurnAsync _numCalorieBurn = new getCalorieBurnAsync();
                getCalorieConsumAsync _numCalConsum = new getCalorieConsumAsync();
                //getTotalStepAsync _numTotalSteps = new getTotalStepAsync();

                try {
                    //numTotalSteps = _numTotalSteps.execute().get();
                    calorieBurnAsync = _numCalorieBurn.execute(tmp_userId).get();
                     numCalConsum = _numCalConsum.execute(tmp_userId,startTime).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PostToServerAsync postToServerAsync = new PostToServerAsync();
               postToServerAsync.execute(calorieBurnAsync,numCalConsum);

            }
        });

    return vMain;

    }

    private class PostToServerAsync extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String...params)
        {
            String numGoalCalorie = goalCalorie;
            Date currentDate = new Date();
            Report report = new Report();
            Users loginUserId = new Users(((MainActivity)getActivity()).getLoginUser().getUserid());
            //report.setStepsPerMile(Integer.valueOf(params[2]));
            report.setStepsPerMile(totalSteps);
            report.setBurnedCalories(Double.valueOf(params[0]));
            report.setCaloriesConsumed(Double.valueOf(params[1]));
            report.setReportDate(currentDate);
            report.setUserid(loginUserId);
            report.setCaloriesGoal(Double.valueOf(numGoalCalorie));
            RestClient.createReport(report);
            sb.stepDao().deleteAll();
            return null;
        }
    }

    private class getCalorieBurnAsync extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String...params)
        {
            JSONObject t_BMRNumber = null;
            Double BMRNumber = 0.0;
            JSONObject t_perStepCalorie = null;
            Double perStepCalorie = 0.0;
            try {
                t_perStepCalorie = new JSONObject(RestClient.findCalculateBurnPerStepByUserId(params[0]));
                t_BMRNumber = new JSONObject(RestClient.findBMRByUserId(params[0]));
                BMRNumber = t_BMRNumber.getDouble("BMR");
                perStepCalorie = t_perStepCalorie.getDouble("BurnPerStep");
            } catch (JSONException e) {
                e.printStackTrace();
            }
             totalSpendCalorie = perStepCalorie * totalSteps + BMRNumber;
             String tmpTotalSpend = String.valueOf(totalSpendCalorie);
            return tmpTotalSpend;
        }
        @Override
        protected void onPostExecute(String totalSpend)
        {
            _burnCalorie.setText(totalSpend);
        }
    }

    private class getCalorieConsumAsync extends  AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String...params)
        {
        JSONObject _totalCalorieCunsum = null;
        double totalCalorieCunsum = 0.0;
            try {
                _totalCalorieCunsum = new JSONObject(RestClient.findCalorieConsumByUserId(params[0],params[1]));
                totalCalorieCunsum = _totalCalorieCunsum.getDouble("TodayTotalCalories");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String st_totalCalorieCunsum = String.valueOf(totalCalorieCunsum);
           return st_totalCalorieCunsum;
        }
@Override
protected void onPostExecute(String totalCalorieCunsum)
{
    _CalorieConsum.setText(totalCalorieCunsum);
}
    }
    private class getTotalStepAsync extends AsyncTask<Void,Void,String>
    {
        @Override
        protected String doInBackground(Void...voids)
        {
            sb = Room.databaseBuilder(vMain.getContext().getApplicationContext(), StepDatabase.class, "step_database").fallbackToDestructiveMigration()
                    .build();

            List<StepEntity> getSteps = sb.stepDao().getAll();
            for(StepEntity tmpStep :getSteps)
            {
                totalSteps += tmpStep.getSteps();
            }
            SharedPreferences spTotalSteps = getActivity().getSharedPreferences("TotalSteps",
                    Context.MODE_PRIVATE);
            String steps = String.valueOf(totalSteps);
            SharedPreferences.Editor eTotalStep = spTotalSteps.edit();
            eTotalStep.putString("steps", steps);
            eTotalStep.apply();
            return String.valueOf(totalSteps);
        }
        @Override
        protected void onPostExecute(String totalSteps)
        {
            _totalStep.setText(String.valueOf(totalSteps));
        }
    }

}
