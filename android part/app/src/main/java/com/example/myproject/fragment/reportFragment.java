package com.example.myproject.fragment;
import android.app.DatePickerDialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myproject.Activity.MainActivity;
import com.example.myproject.R;
import com.example.myproject.RestClient;
import com.example.myproject.model.Users;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.support.v4.app.Fragment;
import javax.xml.validation.Validator;

public class reportFragment extends Fragment {
    View vMain;
    PieChart _pieChart;
    BarChart _barChart;
    Button btn_pieChart;
    Button  btn_barChart;
    EditText ed_startDate;
    EditText ed_endDate;
    EditText ed_pieDate;
    Users loginUser;
    DatePickerDialog picker;
    Long DaysBetween;
    List<String> xValue;
    BarChart barChart;
    float groupSpace = 0.04f;
    float barSpace = 0.02f;
    float barWidth = 0.46f;

    List<BarEntry> yValue1 = new ArrayList<BarEntry>();
    List<BarEntry> yValue2 = new ArrayList<BarEntry>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        loginUser = ((MainActivity)getActivity()).getLoginUser();
        vMain = inflater.inflate(R.layout.fragment_report, container, false);
        _pieChart = vMain.findViewById(R.id.piechart);
        _barChart = vMain.findViewById(R.id.barchart);
        btn_barChart = vMain.findViewById(R.id.barButton);
        btn_pieChart = vMain.findViewById(R.id.pieButton);
        ed_endDate = vMain.findViewById(R.id.endDate);
        ed_startDate = vMain.findViewById(R.id.FromDate);
        ed_pieDate = vMain.findViewById(R.id.PiechartDate);
        barChart = vMain.findViewById(R.id.barchart);
       ed_pieDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
               int year = cldr.get(Calendar.YEAR);

              // date picker dialog
                picker = new DatePickerDialog((MainActivity) getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                           @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                               ed_pieDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            };
       });

       ed_startDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                 int Year = c.get(Calendar.YEAR);
                int Month = c.get(Calendar.MONTH);
                int Day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateTimePick = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                  public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ed_startDate.setText((Integer.toString(year) + "-" + Integer.toString(monthOfYear + 1) + "-" + Integer.toString(dayOfMonth)));
                    }
               }, Year, Month, Day);
               dateTimePick.show();
            }
        });
        ed_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
               int Year = c.get(Calendar.YEAR);
                int Month = c.get(Calendar.MONTH);
                int Day = c.get(Calendar.DAY_OF_MONTH);
               DatePickerDialog dateTimePick = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                   public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ed_endDate.setText((Integer.toString(year) + "-" + Integer.toString(monthOfYear + 1) + "-" + Integer.toString(dayOfMonth)));
                    }
               }, Year, Month, Day);
               dateTimePick.show();
           }
        });

        btn_pieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _pieChartDate = ed_pieDate.getText().toString();
                ShowPieChart showPieChart = new ShowPieChart();
                showPieChart.execute(_pieChartDate);
            }
        });
        btn_barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                barChart.setDrawBarShadow(false);
                barChart.setDrawValueAboveBar(true);
                Description description = new Description();
                description.setText("");
                barChart.setDescription(description);
                barChart.setMaxVisibleValueCount(50);
                barChart.setPinchZoom(false);
                barChart.setDrawGridBackground(false);

                XAxis newXnum = barChart.getXAxis();
                newXnum.setGranularity(1f);
                newXnum.setCenterAxisLabels(true);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setSpaceTop(30f);
                barChart.getAxisRight().setEnabled(false);

                String start_date = ed_startDate.getText().toString();
                String end_date = ed_endDate.getText().toString();
                //Parsing the date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
                LocalDate dateBefore = LocalDate.parse(start_date, formatter);
                LocalDate dateAfter = LocalDate.parse(end_date, formatter);

                //calculating number of days in between
                DaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
                xValue = new ArrayList<>();
                String incre_date = start_date;
                for (int i = 0; i < DaysBetween; i++) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    try {
                        c.setTime(sdf.parse(incre_date));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //Incrementing the date by 1 day
                    incre_date = sdf.format(c.getTime());
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    xValue.add(incre_date);
                }
                newXnum.setValueFormatter(new IndexAxisValueFormatter(xValue));
                BarChartDisplay barchart = new BarChartDisplay();
                barchart.execute();
            }
        });


        return vMain;
    }
    private class ShowPieChart extends AsyncTask<String,Void,List<Double>>
    {
        @Override
        protected List<Double> doInBackground(String...params)
        {
            List<Double> resultList = new ArrayList<Double>();
            String pickDate = params[0];
            String userId = String.valueOf(loginUser.getUserid());
            String getRemainingCalorie = RestClient.findRemainingCalorie(userId,pickDate);
            String ReportList = RestClient.findReportByUserId(userId);
            JSONArray ReportArray = null;
            JSONObject RemainArray = null;
            try{
                ReportArray = new JSONArray(ReportList);
                RemainArray = new JSONObject(getRemainingCalorie);
                JSONObject reportJsonO = ReportArray.getJSONObject(0);
                Double totalStepNum = reportJsonO.getDouble("stepsPerMile");
                Double totalCalConsumed = RemainArray.getDouble("totalcaloriesConsumed");
                Double remainCalories = RemainArray.getDouble("remainCalories");
                String BMRStrng = RestClient.findBMRByUserId(userId);
                JSONObject bmrJson = new JSONObject(BMRStrng);
                Double BMRNumber = bmrJson.getDouble("BMR");
                String perSteps = RestClient.findCalculateBurnPerStepByUserId(userId);
                JSONObject perStepJson = new JSONObject(perSteps);
                Double BurnPerStep = perStepJson.getDouble("BurnPerStep");
                Double numBurnStep = BurnPerStep * totalStepNum;
                Double totalNumber = totalCalConsumed + BMRNumber + remainCalories + numBurnStep;
                DecimalFormat getFormat = new DecimalFormat("0.00");
                resultList.add(Double.parseDouble(getFormat.format(totalCalConsumed/totalNumber)));
                resultList.add(Double.parseDouble(getFormat.format(remainCalories/totalNumber)));
                resultList.add(Double.parseDouble(getFormat.format(BMRNumber/totalNumber)));
                resultList.add(Double.parseDouble(getFormat.format(numBurnStep/totalNumber)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultList;
        }
                @Override
                protected void onPostExecute(List<Double> Probability)
                {
                    double d_Consum = Probability.get(0)*100;
                    float f_Consum= (float)d_Consum;
                    double d_Remain = Probability.get(1)*100;
                    float f_Remain = (float)d_Remain;
                    double d_BMR = Probability.get(2)*100;
                    float f_BMR = (float)d_BMR;
                    double d_BurnStep = Probability.get(3)*100;
                    float f_BurnStep = (float)d_BurnStep;

                   if (Probability.size() == 4)
                    {
                        List<PieEntry> giveValue = new ArrayList<>();
                        giveValue.add(new PieEntry(f_Consum,"Consumed Part"));
                        if(Probability.get(1) < 0 ) {
                        giveValue.add(new PieEntry(f_Remain,"Remain Part"));
                        }else{
                            giveValue.add(new PieEntry(f_BMR,"BMR Part"));
                            giveValue.add(new PieEntry(f_BurnStep,"BurnStep Part"));
                            PieDataSet pieDataSet = new PieDataSet(giveValue, "");
                            pieDataSet.setColors(PIE_COLORS);
                            pieDataSet.setValueFormatter(new PercentFormatter());
                            PieData pieData = new PieData(pieDataSet);
                           showChart(_pieChart,pieData);
                        }


                    }
        }
    }

    private class BarChartDisplay extends AsyncTask<Void, Void, List<Double>> {

        @Override
        protected List<Double> doInBackground(Void... voids) {
            String userId = String.valueOf(loginUser.getUserid());
            List<Double> listOfElement = new ArrayList<>();
            for (String date : xValue) {
                try {
                    JSONObject infoArray = RestClient.FindRemainingCalorie(userId,date);
                    Double totalCon = infoArray.getDouble("totalcaloriesConsumed");
                    Double totalBurn = infoArray.getDouble("totalCaloriesBurned");
                    listOfElement.add(totalCon);
                    listOfElement.add(totalBurn);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return listOfElement;
        }
        @Override
         protected void onPostExecute (List<Double> infolist)
        {

            List<Double> infoList = infolist;
            int j = 0;
            for (int i = 0; i < xValue.size(); i++) {
                yValue1.add(new BarEntry(i, Float.valueOf(infoList.get(j).toString())));
                yValue2.add(new BarEntry(i, Float.valueOf(infoList.get(j + 1).toString())));

            }
            BarDataSet setValue1, setValue2;

            if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
                setValue1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
                setValue2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
                setValue1.setValues(yValue1);
                setValue2.setValues(yValue2);
                barChart.getData().notifyDataChanged();
                barChart.notifyDataSetChanged();
            } else {
                setValue1 = new BarDataSet(yValue1, "totalCalConsumed");
                setValue1.setColor(Color.rgb(85, 241, 175));
                setValue2 = new BarDataSet(yValue2, "totalCalBurned");
                setValue2.setColor(Color.rgb(40, 120, 180));

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(setValue1);
                dataSets.add(setValue2);

                BarData data = new BarData(dataSets);
                barChart.setData(data);
            }

            barChart.getBarData().setBarWidth(barWidth);
            barChart.groupBars(0,groupSpace, barSpace);

            barChart.invalidate();

        }

    }



    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleRadius(60f);
        pieChart.setTransparentCircleRadius(64f);
        pieChart.setDrawCenterText(true);
        Description description = new Description();
        description.setText("Pie Chart");
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(90);
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Calorie Report");
        pieData.setValueTextSize(15);
        pieChart.setData(pieData);
        Legend mLegend = pieChart.getLegend();
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        pieChart.animateXY(1000, 1000);
    }
    public static final int[] PIE_COLORS = {
            Color.rgb(181, 194, 202), Color.rgb(129, 216, 200), Color.rgb(241, 214, 145),
            Color.rgb(108, 176, 223)};
}
