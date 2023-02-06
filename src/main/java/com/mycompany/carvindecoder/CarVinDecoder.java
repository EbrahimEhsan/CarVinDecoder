/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.carvindecoder;

import java.io.BufferedReader;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
        

        Display();

    }
    /**
     * Display method that displays the user friendly interface to enter user info for vehicle 
     * @throws IOException 
     */
    public static void Display() throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Car Vin Decoder! Enter Year and Vin to Decode you Cars info. \nExample, VIN - 1HGCR2F35EA000787, Year - 2014.");
        System.out.println("-----------------------------");
        System.out.println("");
        System.out.println("");
        System.out.print("Enter the year of the vehicle: ");
        String year = input.nextLine();
        System.out.print("Enter the VIN number: ");
        String vin = input.nextLine();
        System.out.println("");
        System.out.println("-----------------------------");
        readJson(year, vin);


    }
    /**
     * this method is reading the user input and parsing through the Json data then returning the info of that car
     * @param year
     * @param vin
     * @throws MalformedURLException
     * @throws IOException 
     */
    public static void readJson(String year, String vin) throws MalformedURLException, IOException {
       
        String link = "https://vpic.nhtsa.dot.gov/api//vehicles/DecodeVin/" + vin + "?format=json&modelyear=" + year;
        URL url = new URL(link);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

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

            String value = explrObject.get("Value").toString();
            if (value != "null" && value != "") {
                String variable = explrObject.getString("Variable").toString();
                if (variable == "Error Code" && value != "0") {
                    System.out.println(value);
                    break;
                }

                System.out.println("- " + variable);
                System.out.println(explrObject.get("Value").toString());
                System.out.println("");
            }

        }

        br.close();

    }

}
