package org.example;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static String BASE_URL = "https://dog.ceo/api/breeds/image/random";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject jsonResponse = new JSONObject(response.toString());

            System.out.println(responseCode);

            System.out.println(jsonResponse.get("message"));
        }
        catch (Exception e){
            System.out.println("Ошибка: " + e);
        }
    }
}