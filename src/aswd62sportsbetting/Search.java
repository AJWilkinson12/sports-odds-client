/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aswd62sportsbetting;

/**
 *
 * @author ajwilkinson
 */
public interface Search {
    
    //Pretty much just keeps all the api hosting address information.
    final String HOST = "https://api.the-odds-api.com/v3/odds/?apiKey=";
    final String APIKEY = "c605f728f09cae187e198bb6f7f43c2f";
    final String SPORT = "&sport=";
    final String REGION = "&region=us";
    final String MKT = "&mkt=spreads";
    
    
    public void load(String searchString) throws Exception;
}
