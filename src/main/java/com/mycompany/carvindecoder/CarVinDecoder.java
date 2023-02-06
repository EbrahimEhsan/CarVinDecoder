/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.carvindecoder;

import java.io.BufferedReader;
import java.util.Scanner;
import java.net.HttpURLConnection;
//import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONString;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author ebrahimehsan
 */
public class CarVinDecoder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        // Example : 1HGCR2F35EA000787 2014
        // System.out.println("Hello! Enter Year and Vin to Decode you Cars info: ");
        String vin = "";
        String year = "";
        //String url = "https://vpic.nhtsa.dot.gov/api//vehicles/DecodeVin/" + vin + "?format=json&modelyear="+ year;
        //RestTemplate restTemplate = new RestTemplate();
        // ObjectMapper mapper = new ObjectMapper();

        // System.out.println(url);
        // Display();
        readJson();

    }

    public static void Display() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Car Vin Decoder!");
        System.out.println("-----------------------------");
        System.out.println("");
        System.out.println("");

    }

    public static void readJson() throws MalformedURLException, IOException {
        String vin = "1HGCR2F35EA000787";
        String year = "2014";
        String link = "https://vpic.nhtsa.dot.gov/api//vehicles/DecodeVin/" + vin + "?format=json&modelyear=" + year;
        URL url = new URL(link);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        System.out.println(status);

        BufferedReader br = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
        
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            content.append(inputLine);
        }

        JSONObject contentJson = new JSONObject(content.toString());

        JSONArray result = contentJson.getJSONArray("Results");
        for (int i = 0; i < result.length(); i++) {
            JSONObject explrObject = result.getJSONObject(i);

            //if (explrObject.getString("Error Code").toString() != "0") {
            //    System.out.println(explrObject.get("Error Text").toString());
            //}
            String value = explrObject.get("Value").toString();
            if (value != "null" && value != "") {
                String variable = explrObject.getString("Variable").toString();
                if (variable == "Error Code" && value != "0") {
                    System.out.println(value);
                    break;
                }

                System.out.println(variable);
                System.out.println(explrObject.get("Value").toString());
            }

        }

        br.close();

    }

}
