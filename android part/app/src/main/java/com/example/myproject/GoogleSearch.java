package com.example.myproject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GoogleSearch{
    private static final String key = "AIzaSyAj-L5n1GmdNjhP5ITb859CMvP9Q7qO5-4";
    private static final String id = "010833869936706857633:aokdwe1x00q";

    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+
                    key+ "&cx="+ id + "&q="+ keyword + query_parameter);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }
    public static String getImageLink(String string) {
       String link = new String();
       try{
            JSONObject jsonObject = new JSONObject(string);
           JSONArray jsonArray = jsonObject.getJSONArray("items");
        if(jsonArray != null && jsonArray.length() > 0) {
              JSONObject pageJson = new JSONObject(jsonArray.getJSONObject(0).getString("pagemap"));
               JSONArray imageArray = new JSONArray(pageJson.get("cse_image").toString());
               link = imageArray.getJSONObject(0).get("src").toString();
            }
       }catch (Exception e){
            e.printStackTrace();
            link = "NO INFO FOUND";
       }
      return link;
   }

    public static String getSnippet(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                snippet =jsonArray.getJSONObject(0).getString("snippet");
            }
        }catch (Exception e){
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }


}