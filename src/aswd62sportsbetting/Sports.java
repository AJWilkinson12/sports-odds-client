/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aswd62sportsbetting;
import java.util.Date;

/**
 *
 * @author ajwilkinson
 */
public class Sports {
    //Basic object creating class
    public String hometeam;
    public long timestamp;
    public Date time;
    public String awayteam;
    public String odds;
    public String site;
    public String game;
    
    public Sports(String hometeam, long timestamp, String awayteam, String odds, String site){
        
        this.hometeam = hometeam;
        //Converting Unix Timestamp from API to readable date and time.
        this.time = new Date((long)timestamp * 1000);
        this.awayteam = awayteam;
        this.odds = "+/-" + odds;
        this.site = site;
        this.game = awayteam + " @ " + hometeam;
        
    }
}
