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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.myproject.Activity.MainActivity;
import com.example.myproject.R;
import com.example.myproject.StepDatabase;
import com.example.myproject.StepEntity;
import com.example.myproject.model.Users;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class stepsFragment extends Fragment {
    View vMain;
    StepDatabase sb = null;
    Users loginUser;
    EditText _setSteps;
    TextView _viewSteps;
    ListView _viewTotalSteps;
    TextView _viewUpdateSteps;
    Button _btnSetSteps;
    Button _btnAllSteps;
    Button _btnUpdateSteps;
    Button btn_PostAllData;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_steps, container, false);
        _setSteps = (EditText) vMain.findViewById(R.id.editStep);
        loginUser = ((MainActivity) getActivity()).getLoginUser();
        SharedPreferences calorieGoal = getActivity().getSharedPreferences("myGoal", Context.MODE_PRIVATE);
        String goalCalorie = calorieGoal.getString("message", null);
        _viewSteps = (TextView) vMain.findViewById(R.id.viewStep);
        _viewTotalSteps = (ListView) vMain.findViewById(R.id.allStepView);
        _viewUpdateSteps = (TextView) vMain.findViewById(R.id.upDatestep);
        _btnSetSteps = (Button) vMain.findViewById(R.id.btn_step);
        _btnAllSteps = (Button) vMain.findViewById(R.id.btn_allStep);
        _btnUpdateSteps = (Button) vMain.findViewById(R.id.btn_upDatestep);

        sb = Room.databaseBuilder(vMain.getContext().getApplicationContext(), StepDatabase.class, "step_database").fallbackToDestructiveMigration()
                .build();
        _btnSetSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDatabase addDatabase = new InsertDatabase();
                addDatabase.execute();
            }
        });
        _btnAllSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadDatabase showDatabase = new ReadDatabase();
                showDatabase.execute();
            }
        });

        _btnUpdateSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDatabase updateStep = new UpdateDatabase();
                updateStep.execute();
            }
        });
        return vMain;
    }

    private class ReadDatabase extends AsyncTask<Void,Void,List<StepEntity>>
    {
        @Override
        protected List<StepEntity> doInBackground(Void ... param)
        {
            List<StepEntity> step = sb.stepDao().getAll();
            return step;
        }
        @Override
        protected void onPostExecute(List<StepEntity> stepData)
        {
            List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
            for(StepEntity newsteep: stepData)
            {
                HashMap<String,Object> input = new HashMap<String,Object>();
                input.put("id",newsteep.getUserid());
                input.put("step",newsteep.getSteps());
                input.put("time",newsteep.getDate());
                result.add(input);
            }
            SimpleAdapter adapter = new SimpleAdapter(vMain.getContext(),result,R.layout.steplist,
                    new String[]{"id","step","time"},new int[]{R.id.stepId,R.id.showSteps,R.id.stepTime});
            _viewTotalSteps.setAdapter(adapter);
        }
    }
    private class InsertDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            if (!(_setSteps.getText().toString().isEmpty())) {
                Integer setSteps = Integer.parseInt(_setSteps.getText().toString());
                int userId = loginUser.getUserid();
                String currentDate = LocalTime.now().toString();
                StepEntity stepentity = new StepEntity(userId,setSteps,currentDate);
                long id = sb.stepDao().insert(stepentity);
                return "StepID:"+ id +"UserID: " + userId + " Time:" + currentDate + " Steps: " + setSteps ;

            }
            return "Please input valid steps!";
        }

        @Override
        protected void onPostExecute(String steps) {
            _viewSteps.setText("New Record: " + steps);
        }
    }


    private class UpdateDatabase extends AsyncTask<Void, Void, String> {
        @Override protected String doInBackground(Void... params) {
            StepEntity step = null;
            String[] details = _setSteps.getText().toString().split(" ");
            if (details.length ==  3) {
                int id = Integer.parseInt(details[0]);
                step = sb.stepDao().findByuserId(id);
                step.setSteps(Integer.parseInt(details[1]));
                step.setDate(LocalTime.now().toString());
            }
            if (step!=null) {
                sb.stepDao().updateStep(step);
                return ("Success!");
            }
            return "Enter new Steps!";
        }
        @Override
        protected void onPostExecute(String details) {
            _viewUpdateSteps.setText(details);
        }
    }
}

