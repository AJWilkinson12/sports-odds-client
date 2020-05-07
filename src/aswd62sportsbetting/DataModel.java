/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aswd62sportsbetting;

import java.util.Date;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author ajwilkinson
 */
public class DataModel implements Search {
    
    private URL url;
    
    private ArrayList<Sports> sports;
    private String searchString;
    private String urlString;
    private Page2Controller except = new Page2Controller();
    
    public DataModel(){
        
        sports = new ArrayList<>();
        
    }
    
    
    @Override
    public void load(String searchString) throws Exception {
        try{
            if (searchString == null || searchString.equals("")) {
                throw new Exception("The search string was empty.");
            }
        }catch(Exception ex){
            throw ex;
        }
        
        //Gets the appropriate Sport parameter.
        try{
            if(searchString.equals("COLLEGE FOOTBALL")){
                searchString = "americanfootball_ncaaf";
            }else if(searchString.equals("FOOTBALL")){
                searchString = "americanfootball_nfl";
            }else if(searchString.equals("BASEBALL")){
                searchString = "baseball_mlb";
            }else if(searchString.equals("SOCCER")){
                searchString = "soccer_usa_mls";
            }else{
                throw new Exception("That sport is not yet supported");
            }
        }catch(Exception ex){
            except.displayErrorAlert("That sport either does not exist or is not supported yet.");
            return;
        }
        
//        create the URL String
        String encodedSearchString;
        
        try{
            encodedSearchString = URLEncoder.encode(searchString, "UTF-8");
        }catch(UnsupportedEncodingException ex){
            throw ex;
        }
        
        urlString = HOST + APIKEY + SPORT + encodedSearchString + REGION + MKT;
        
        System.out.println(urlString);
        
        try{
            url = new URL(urlString);
        } catch (MalformedURLException ex){
            throw ex;
        }
        
        
        String jsonString = ""; 
        
        //Read in the JSON information
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream())); 
            
            String inputLine; 
            
            while((inputLine = in.readLine()) != null){
                jsonString += inputLine; 
            }
            
            in.close();
            
        } catch(IOException ioex){
            throw ioex; 
        }
        
        System.out.print("jsonString: " + jsonString + "\n\n");
        
        try {
            parseJsonSportFeed(jsonString); 
        } catch (Exception ex){
            throw ex; 
        }
    }
    
    private void parseJsonSportFeed(String jsonString) throws Exception {
        
        sports.clear();
        
        if (jsonString == null || jsonString.equals("")){
            return;
        }
        
        JSONObject jsonObj;
        
        //Exception handleing is in this section a lot 
        try {
            jsonObj = (JSONObject)JSONValue.parse(jsonString);
        } catch (Exception ex) {
            throw ex;
        }
        
        if (jsonObj == null) return;
        
        boolean status;
        
        try {
            status = (boolean) jsonObj.get("success");
        } catch (Exception ex) {
            throw ex;
        }
        
        if (!status) {
            throw new Exception("Status returned from API was not true.");
        }
        
        
        //Begin to parse through the JSON data
        JSONArray data;
        
        try {
            data = (JSONArray)jsonObj.get("data");
        } catch (Exception ex) {
            throw ex;
        }
        
        if(data.size() == 0){
            except.displayErrorAlert("Sorry. There is currently no available data for that sport. Please try again later.");
            return;
        }
      
        for (Object doc : data) {
            try {
                JSONObject game = (JSONObject)doc;
                String hometeam = (String)game.getOrDefault("home_team", "");
                long timestamp = (long)game.getOrDefault("commence_time", "");
                JSONArray teamArray = (JSONArray)game.get("teams");
                String awayteam = (String)teamArray.get(0);
                if(awayteam.equals(hometeam)){
                    awayteam = (String)teamArray.get(1);
                }
                JSONArray siteArray = (JSONArray)game.get("sites");
                String site = "";
                String odds = "";
                
                //Checks to see if there is any available data for searched sport
                if(!siteArray.isEmpty()){
                    JSONObject siteObj = (JSONObject) siteArray.get(0);
                    
                    if (siteObj != null) {
                    
                        site = (String)siteObj.getOrDefault("site_nice", "");
                        
                        // Accessing nested arrays within the API
                        JSONObject oddObj = (JSONObject)siteObj.getOrDefault("odds", null);
                        JSONObject spreadObj = (JSONObject)oddObj.getOrDefault("spreads", null);
                        JSONArray pointsArray = (JSONArray) spreadObj.get("points");
                
                        odds = (String) pointsArray.get(0);
                        
                 
                    }
                }else{
                    site = "N/A";
                    odds = "N/A";
                }
                
                Date t = new Date((long)timestamp*1000);
                
                
                Sports sport = new Sports(hometeam, timestamp, awayteam, odds, site);
                sports.add(sport);
                
                System.out.println("Game: " + awayteam + " @ " + hometeam + "\n");
                System.out.println("Homteam: " + hometeam + "\n");
                System.out.println("Time: " + t + "\n");
                System.out.println("odds: +/- " + odds + "\n");
                System.out.println("Source: " + site + "\n");
                System.out.println("------------------------------------------------------\n");
                
                
                
            } catch (Exception ex) {
                throw ex;
            }
            
        }
        
    }
    
    public ArrayList<Sports> getSports() {
        return sports;
    }
    
    public int getNumSports() {        
        return sports.size();
    }
    
    
}
