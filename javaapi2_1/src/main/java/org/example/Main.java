package org.example;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static String BASE_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
    public static void main(String[] args) {
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

            JSONObject all = new JSONObject(response.toString());

            JSONObject time =  new JSONObject(all.get("time").toString());
            String timeUpdated = time.getString("updated");

            JSONObject bpi = new JSONObject(all.get("bpi").toString());
            JSONObject bpiUsd = new JSONObject(bpi.get("USD").toString());
            String usdDescription = bpiUsd.getString("description");
            Double usdFloat = bpiUsd.getDouble("rate_float");

            System.out.println("Bitcoin now:");
            System.out.println(timeUpdated);
            System.out.println(usdDescription);
            System.out.println(usdFloat + " $");


            try(FileWriter writer = new FileWriter("infoBTC.txt", true)) {
                writer.append("Bitcoin time: ");
                writer.append(timeUpdated + "\n");
                writer.append("Currency is: ");
                writer.append(usdDescription + "\n");
                writer.append("Cost: ");
                writer.append(usdFloat + " $\n\n");

                writer.flush();
            }
            catch (IOException ex){
                System.out.println(ex);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}