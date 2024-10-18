package org.example;


import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.util.Scanner;

public class Main {



    public static String BASE_URL = "https://catfact.ninja/fact";

    public static String TRANSLATE_BASE_URL = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=ru&dt=t&q=";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            //facts
            URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");


        int responseCode = conn.getResponseCode();
        System.out.println(responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject jsonResponse = new JSONObject(response.toString());

            System.out.println(jsonResponse.get("fact"));

            //translate

            URL tUrl = new URL(TRANSLATE_BASE_URL + URLEncoder.encode(jsonResponse.get("fact").toString(),"UTF-8"));
            HttpURLConnection translate = (HttpURLConnection) tUrl.openConnection();
            translate.setRequestMethod("GET");

            in = new BufferedReader(new InputStreamReader((translate.getInputStream())));
            response = new StringBuilder();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }


            Object translateA = new JSONArray(response.toString()).getJSONArray(0).getJSONArray(0).get(0);

            System.out.println(translateA);

            in.close();
            translate.disconnect();

            //retry translate

            TRANSLATE_BASE_URL = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=ru&tl=en&dt=t&q=";

            tUrl = new URL(TRANSLATE_BASE_URL + URLEncoder.encode(translateA.toString(),"UTF-8"));
            translate = (HttpURLConnection) tUrl.openConnection();
            translate.setRequestMethod("GET");

            in = new BufferedReader(new InputStreamReader((translate.getInputStream())));
            response = new StringBuilder();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }


            Object translateB = new JSONArray(response.toString()).getJSONArray(0).getJSONArray(0).get(0);

            System.out.println(translateB);

            in.close();
            translate.disconnect();


        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}