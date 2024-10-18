package org.example;


import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main {
    public static String BASE_URL_JOKE = "https://official-joke-api.appspot.com/jokes/";

    public static String BASE_URL_CATS = "https://catfact.ninja/fact";

    public static String TRANSLATE_BASE_URL = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=ru&dt=t&q=";

    public static String TRANSLATE_BASE_URL_RUnEN = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=ru&tl=en&dt=t&q=";


    public static String BASE_URL_DOGS = "https://dog.ceo/api/breeds/image/random";



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int aLang = 0;
        boolean isCont;
        String check = null;
        int text = 0;

        while (text != 5){
            System.out.println("Выберите опцию: " + "\n1. Случайная шутка про программирование." +
                    "\n2. Случайный факт о кошках." + "\n3. Фотографии собак, для поднятия настроения" + "\n4. Переводчик" +
                    "\n5. Выход.");
            text = scanner.nextInt();
            switch (text){
                case 1:
                    System.out.println(jokes());
                    break;
                case 2:
                    System.out.println(cats());
                    break;
                case 3:
                    System.out.println(dogs());
                    break;
                case 4:
                        isCont = true;
                        System.out.println("Выберите язык перевода:" +
                                "\n1. Английский-Русский" +
                                "\n2. Русский-Английский" +
                                "\n3. Выход из режима перевода");
                        aLang = scanner.nextInt();
                        while (isCont) {
                            switch (aLang) {
                                case 1:
                                    check = translaterAB(1);
                                    System.out.println(check);
                                    if (check.equals("Выход"))
                                        aLang = 3;
                                    break;
                                case 2:
                                    check = translaterAB(2);
                                    System.out.println(check);
                                    if (check.equals("Выход"))
                                        aLang = 3;
                                    break;
                                case 3:
                                    isCont = false;
                                    break;
                                default:
                                    System.out.println("Язык не добавлен или не существует");
                                    break;
                            }
                        }
                        break;
                case 5:
                    System.out.println("Выход из системы.");
                    break;
                default:
                    System.out.println("Нет такой опции.");
                    break;
            }
        }

    }


    public static String dogs(){
        try {
            URL url = new URL(BASE_URL_DOGS);
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

            if (responseCode != 200)
                System.out.println(responseCode);

            return jsonResponse.get("message").toString();
        }
        catch (Exception e){
            System.out.println("Ошибка: " + e);
        }
        return "Error";
    }

    public static String translaterAB(int choosedLang){
        try {
            URL tUrl;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите текст для перевода (-1 - для выхода):");
            String text = scanner.nextLine();
            HttpURLConnection translate = null;

            if (!text.equals("-1")) {
                switch (choosedLang) {
                    case 1:
                        tUrl = new URL(TRANSLATE_BASE_URL + URLEncoder.encode(text, "UTF-8"));
                        translate = (HttpURLConnection) tUrl.openConnection();
                        break;
                    case 2:
                        tUrl = new URL(TRANSLATE_BASE_URL_RUnEN + URLEncoder.encode(text, "UTF-8"));
                        translate = (HttpURLConnection) tUrl.openConnection();
                        break;
                    default:
                        return "Error";
                }

                translate.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader((translate.getInputStream())));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                String translateA = new JSONArray(response.toString()).getJSONArray(0).getJSONArray(0).get(0).toString();
                String translateB = new JSONArray(response.toString()).getJSONArray(0).getJSONArray(0).get(1).toString();

                String result = "Текст:\n" + translateB + "\nПеревод:\n" + translateA;

                in.close();
                translate.disconnect();

                return result;
            }
            return "Выход";
        }
        catch (Exception e){
            System.out.println("Translate Error = " + e);
        }
        return "Error";
    }


    public static String translate(String textS){
        try {
            URL tUrl = new URL(TRANSLATE_BASE_URL + URLEncoder.encode(textS,"UTF-8"));
            HttpURLConnection translate = (HttpURLConnection) tUrl.openConnection();
            translate.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader((translate.getInputStream())));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            String translateA = new JSONArray(response.toString()).getJSONArray(0).getJSONArray(0).get(0).toString();


            in.close();
            translate.disconnect();

            return translateA;
        }
        catch (Exception e){
            System.out.println("Translate Error = " + e);
        }
        return "Error";
    }

    public static String jokes(){
        Scanner scanner = new Scanner(System.in);
        try{
            URL url = new URL(BASE_URL_JOKE + "programming/random");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200)
                System.out.println(responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONArray joke = new JSONArray(response.toString());

            Object jokeSetup = joke.getJSONObject(0).get("setup") ;
            Object jokePunchLine = joke.getJSONObject(0).get("punchline");



            return translate(jokeSetup.toString()) + "\n" + translate(jokePunchLine.toString());
        }
        catch (Exception e){
            System.out.println(e);
        }
        return "Error";
    }

    public static String cats() {
        try {
            URL url = new URL(BASE_URL_CATS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            if (responseCode != 200)
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

            return translate(jsonResponse.get("fact").toString());

        }
        catch(Exception e){
            System.out.println(e);
        }
        return "Error cats facts";
    }
}