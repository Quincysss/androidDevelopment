package com.example.myproject.fragment;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myproject.Activity.MainActivity;
import com.example.myproject.GoogleSearch;
import com.example.myproject.R;
import com.example.myproject.RestClient;
import com.example.myproject.fatsecret;
import com.example.myproject.model.Consumption;
import com.example.myproject.model.Food;
import com.example.myproject.model.Users;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dailyDietFragment extends Fragment {
    View vMain;
    TextView _showCataAndFood;
    ListView _viewCatalog;
    ListView _ViewFood;
    ListView _searchFood;
    Button  btn_submitSearch;
    Button btn_submitNumber;
    EditText _search;
    EditText _sumbitNumber;
    String catalogueView;
    String foodId;
    ImageView _food_Image;
    TextView _foodSearchList;
    EditText _inputCatagory;
    TextView _foodSearchDesc;
    Button btn_addFood;
    String searchName;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        vMain = inflater.inflate(R.layout.fragment_my_daily_diet, container, false);
        _viewCatalog = (ListView) vMain.findViewById(R.id.viewCatalog);
        _ViewFood = (ListView) vMain.findViewById(R.id.ViewFood);
        btn_submitSearch = (Button)vMain.findViewById(R.id.submitSearch);
        btn_submitNumber = (Button)vMain.findViewById(R.id.btn_sumbitnum) ;
        _sumbitNumber = (EditText) vMain.findViewById(R.id.sumbitnum) ;
        _showCataAndFood = (TextView) vMain.findViewById(R.id.showfood);
        _search = (EditText) vMain.findViewById(R.id.search);
        _searchFood = (ListView)vMain.findViewById(R.id.searchFood);
        _food_Image = (ImageView) vMain.findViewById(R.id.showimageview);
        _foodSearchList = (TextView) vMain.findViewById(R.id.searchfoodName);
        _foodSearchDesc = (TextView) vMain.findViewById(R.id.searchfoodDesc);
        _inputCatagory = (EditText) vMain.findViewById(R.id.inputCatagory) ;
        btn_addFood = vMain.findViewById(R.id.postSearchFood);
        readCatalogue  catalogue = new readCatalogue();
        catalogue.execute();
        _viewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> foodCategory = (HashMap<String, Object>) _viewCatalog.getItemAtPosition(position);
               catalogueView = foodCategory.get("Category").toString();
               readFood readfood = new readFood();
                readfood.execute(catalogueView.replace("\"",""));
           }
        });
        _ViewFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,Object> data = (HashMap<String, Object>)_ViewFood.getItemAtPosition(position);
                String foodName = data.get("Food").toString();
                foodId = data.get("Foodid").toString();
                _showCataAndFood.setText(catalogueView + " : " + foodName);
           }
        });
        _searchFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> getSearchName = (HashMap<String, String>) _searchFood.getItemAtPosition(position);
                searchName = getSearchName.get("FoodSearchList");
                String newSearchName = "";
                newSearchName = searchName.split(",")[0];
                GetSearchDescription getSearchDescription = new GetSearchDescription();
                FoodImageAsync getFoodImageAsync = new FoodImageAsync();
                getSearchDescription.execute(newSearchName);
                getFoodImageAsync.execute(newSearchName);
            }
        });

        btn_submitNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitConsumption sumbitConsumption = new SubmitConsumption();
                sumbitConsumption.execute(foodId);
                Toast toast=Toast.makeText(vMain.getContext().getApplicationContext(), "Successful!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER, -30, 140);
                toast.show();
            }
        });

        btn_submitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               FoodSearchAsync searhFoodAsyncTask = new FoodSearchAsync();
                String searchFoodName = _search.getText().toString();
                searhFoodAsyncTask.execute(searchFoodName);
            }
        });
        btn_addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpCatagory = _inputCatagory.getText().toString();
                PostFoodInLocalAsync postFoodInLocal = new PostFoodInLocalAsync();
                postFoodInLocal.execute(searchName,tmpCatagory);
            }
        });
        return vMain;
    }
    private class PostFoodInLocalAsync extends AsyncTask<String,Void,Void>
    {
        @Override
        protected Void doInBackground(String...params)
        {
            String searchFoodName = params[0].split(",")[0];
            Double searchFoodAmount = Double.valueOf(params[0].split(",")[1].split(" ")[5]);
            Double searchFoodFat = Double.valueOf(params[0].split(",")[1].split(" ")[3]);
            String searchFoodUnit = params[0].split(",")[1].split(" ")[7];
            Double searchFoodCalorie = Double.valueOf(params[0].split(",")[1].split(" ")[1]);
            String searchFoodCatagory = params[1];
            if (searchFoodCatagory.isEmpty())
            {
                Toast toast=Toast.makeText(vMain.getContext().getApplicationContext(), "No Catagory!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER, -30, 140);
                toast.show();
                return null;
            }
            else
            {
            Food food = new Food(searchFoodName,searchFoodCatagory,searchFoodCalorie,searchFoodUnit,searchFoodAmount,searchFoodFat);
            RestClient.createFood(food);
            }
            return null;
        }
    }
private class readCatalogue extends AsyncTask<Void,Void,Void>
{
    List<String> catalogue = new ArrayList<>();
    @Override
    protected Void doInBackground(Void... voids)
    {
        catalogue = RestClient.showCatalogue();
        return null;
    }

  @Override
    protected void onPostExecute(Void data) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        for (String tmpcatagory : catalogue) {
            HashMap<String, String> Cata = new HashMap<>();
            Cata.put("Category", tmpcatagory);
            result.add(Cata);
        }
        SimpleAdapter food_Cata = new SimpleAdapter(vMain.getContext(), result, R.layout.cataloguelist,
                new String[]{"Category"}, new int[]{R.id.catalogue});
        _viewCatalog.setAdapter(food_Cata);
    }
}
    private class readFood extends AsyncTask<String, Void, List<String>> {
        List<String> FoodNameList = new ArrayList<>();

        @Override
        protected List<String> doInBackground(String... params)
        {
            FoodNameList = RestClient.getFoodByCategory(params[0]);
            return FoodNameList;
        }
        @Override
        protected void onPostExecute(List<String> foodList) {
            List<HashMap<String, String>> foodListResult = new ArrayList<HashMap<String, String>>();
            for (int i=0; i<foodList.size();i +=2) {
                HashMap<String, String> tmpFood = new HashMap<>();
                tmpFood.put("Food", foodList.get(i));
                tmpFood.put("Foodid",foodList.get(i+1));
                foodListResult.add(tmpFood);
            }

            SimpleAdapter listfood = new SimpleAdapter(vMain.getContext(), foodListResult, R.layout.foodlist,
                    new String[]{"Food"}, new int[]{R.id.food});
            _ViewFood.setAdapter(listfood);
        }
    }
private class SubmitConsumption extends AsyncTask<String,Void,Void>
{
    @Override
    protected Void doInBackground(String... params)
    {

        Users loginUserId = new Users(((MainActivity)getActivity()).getLoginUser().getUserid());
        Food foodId = new Food(Integer.valueOf(params[0]));
        Consumption consumption = new Consumption();
        Date currentDate = new Date();
        consumption.setConDate(currentDate);
        consumption.setFoodid(foodId);
        consumption.setUserid(loginUserId);
        boolean isDigits = TextUtils.isDigitsOnly(_sumbitNumber.getText().toString());
        if (isDigits = true)
        {
            String sumbitNumber = _sumbitNumber.getText().toString();
            consumption.setQuantityFood(Double.valueOf(sumbitNumber));
            RestClient.createConsumption(consumption);
        }
        else {
            _sumbitNumber.setError("You should input number!");
        }
        return null; }
}
  private  class FoodSearchAsync extends AsyncTask<String,Void,List<HashMap<String, String>>>
  {   @RequiresApi(api = Build.VERSION_CODES.KITKAT)
      @Override
      protected List<HashMap<String, String>> doInBackground(String ... params)
      {
          List<HashMap<String,String>> foodSecret = new ArrayList<HashMap<String, String>>();
          JSONArray searchFoodArray = new JSONArray();
          try {
              searchFoodArray = new JSONArray(fatsecret.getFood(params[0]).get("food").toString());
          } catch (JSONException e) {
              e.printStackTrace();
          }
          for (int i = 0; i < searchFoodArray.length(); i++) {
              HashMap<String, String> searchFood = new HashMap<>();
              HashMap<String,String> _searchFoodName = new HashMap<>();
              JSONObject foodJson = new JSONObject();
              String searchFoodName = new String();
              String searchFoodDesc = new String();
              try {
                  foodJson = searchFoodArray.getJSONObject(i);
                  searchFoodDesc = foodJson.get("food_description").toString();
                  searchFoodName = foodJson.get("food_name").toString();
              } catch (JSONException e) {
                  e.printStackTrace();
              }
              String newFoodName = searchFoodName;
              String[] newFoodDesc = searchFoodDesc.split(" ");
              Double _totalServing = ExtractNumber(newFoodDesc[1]);
              String servingunit = ExtractWord(newFoodDesc[1]);
              Double _fat = ExtractNumber(newFoodDesc[7]);
              Double _totalCalorie = ExtractNumber(newFoodDesc[4]);

              String allSearchInfo =  newFoodName + ",Calorie: " + _totalCalorie.toString() + " Fat: " + _fat.toString() +
                      " TotalServing: " + _totalServing.toString() + " SERVING_UNIT: " + servingunit;
              searchFood.put("FoodSearchList",allSearchInfo);
              foodSecret.add(searchFood);
          }
             return foodSecret;
          }
      @Override
      protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
          SimpleAdapter adapter_foodsearch = new SimpleAdapter(getActivity(),hashMaps,R.layout.foodsecret,new String[]{"FoodSearchList"},new int[]{R.id.searchfoodName});
          _searchFood.setAdapter(adapter_foodsearch);
      }
      }

    private class GetSearchDescription extends AsyncTask<String,Void, String>{
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {
            return GoogleSearch.search(params[0], new String[]{"num"}, new
                    String[]{"1"});
        }
        @Override
        protected void onPostExecute(String result) {
            String a = new String();
            a = GoogleSearch.getSnippet(result);
            _foodSearchDesc.setText(a);
        }
    }

    private class FoodImageAsync extends AsyncTask<String,Void, Bitmap>{
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Bitmap doInBackground(String... strings) {
            String foodString = GoogleSearch.search(strings[0], new String[]{"num"}, new String[]{"1"});
            String imageLink = GoogleSearch.getImageLink(foodString);
            Bitmap image = null;
            try {
                InputStream in = new URL(imageLink).openStream();
                image = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
           _food_Image.setImageBitmap(bitmap);
        }
    }

    public static String ExtractWord(String str){
        Pattern unitPattern = Pattern.compile("\\d+([ A-Za-z]+)");
        Matcher unitMatch = unitPattern.matcher(str);
        if(unitMatch.find()){
            String unit = unitMatch.group(1);
            return unit;
        }
        return null;
    }
    public static double ExtractNumber(String str){
        Pattern amountPattern = Pattern.compile("(\\d+\\.?\\d?)");
        Matcher amountMatch = amountPattern.matcher(str);
        if(amountMatch.find()){
            double number = Double.parseDouble(amountMatch.group());
            return number;
        }
        return 0.0;
    }
}
