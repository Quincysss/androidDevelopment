package com.example.myproject.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myproject.Activity.MainActivity;
import com.example.myproject.R;
import com.example.myproject.model.Users;

import java.util.Date;

public class mainFragment extends Fragment{
    Users loginUser;
    View vMain;
    EditText _goalCalorie;
    Button _goalButton;
    Button _displayButton;
    TextView _welcomeText,_showtime;
    TextView _viewGoal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        loginUser = ((MainActivity)getActivity()).getLoginUser();
        _goalCalorie = (EditText) vMain.findViewById(R.id.setgoalCalorie);
        _goalButton = (Button) vMain.findViewById(R.id.btn_setGoal);
        _viewGoal = (TextView) vMain.findViewById(R.id.viewgoal);
        _showtime = vMain.findViewById(R.id.showtime);
        _welcomeText = (TextView) vMain.findViewById(R.id.welcome);
        _welcomeText.setText("Hello : " + loginUser.getFirstname());
        Date currentdate = new Date();
        _showtime.setText(String.valueOf(currentdate));
        _goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String goalCaloire = _goalCalorie.getText().toString();
                if (goalCaloire.isEmpty())
                {
                    _goalCalorie.setError("Goal Calroie is necessary!");
                    return;
                }
                SharedPreferences spMyGoalCalorie = getActivity().getSharedPreferences("myGoal",
                        Context.MODE_PRIVATE);
                String myGoal = goalCaloire;
                SharedPreferences.Editor eMyGoals = spMyGoalCalorie.edit();
                eMyGoals.putString("message", myGoal);
                eMyGoals.apply();

                _viewGoal.setText("Your Goal Calorie is: " + myGoal);

            }
        });
//        _displayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences displayGoal = getActivity().getSharedPreferences("myGoal",Context.MODE_PRIVATE);
//                String goalCalorie = displayGoal.getString("message",null);
//                if (goalCalorie .isEmpty())
//                {
//                    _goalCalorie.setError("Please enter Calorie first!");
//                }
//                _goalCalorie.setText(goalCalorie);
//            }
//
//        });
        return vMain;


}

}
