package com.example.myproject.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import com.example.myproject.GoogleSearch;
import com.example.myproject.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class searchFragment extends Fragment{
    View vMain;
    TextView _searchView;
    Button _searchButton;
    EditText _searchText;
    ImageView fatSecret_image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_search, container, false);
        _searchView = (TextView) vMain.findViewById(R.id.textSearch);
        _searchText = (EditText) vMain.findViewById(R.id.editSearch);
        _searchButton = (Button) vMain.findViewById(R.id.btn_Search) ;

        _searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = _searchText.getText().toString();
                SearchAsyncTask searchAsyncTask=new SearchAsyncTask();
                searchAsyncTask.execute(keyword);
            }
        });
        return vMain;
    }
    public class SearchAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return GoogleSearch.search(params[0], new String[]{"num"}, new
                    String[]{"1"});
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tv= (TextView) vMain.findViewById(R.id.textSearch);
            tv.setText(GoogleSearch.getSnippet(result));
        }
    }
    private class GetFoodImageAsync extends AsyncTask<String,Void, Bitmap>{
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
        protected void onPostExecute(Bitmap bitmap)
        {

            fatSecret_image.setImageBitmap(bitmap);
        }
    }
}

