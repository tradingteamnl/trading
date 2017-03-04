/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bittrex;

//import
import global.DatumCheck;
import global.Time;
import org.json.JSONObject;
import org.json.JSONArray;
import http.HttpRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import mysql.Mysqlconnector;

/**
 *
 * @author michel
 */
public class BittrexMarketRequest {
    
    HttpRequest httprequest = new HttpRequest();
    Time time = new Time();
    DatumCheck datumcheck = new DatumCheck();
    Mysqlconnector mysqlconnector = new Mysqlconnector();
    
    private final String USERNAME = mysqlconnector.getUsername();
    private final String PASSWORD = mysqlconnector.getPassword();
    private final String CONN_STRING = mysqlconnector.getUrlmysql();
    private int datumIddatum;
    
    public void bittrexmarketrequest (){
        
        Connection conn;
        try {
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            Statement stmt = (Statement) conn.createStatement();
            String uri = "https://bittrex.com/api/v1.1/public/getmarketsummaries";
            
            //vul datum id in 
           this.datumIddatum = datumcheck.datumCheck();
            
            /**
             * Vraag dat op en kijkt of het antwoord false is
             */
            String data = httprequest.GetHttp(uri);
            if(!"false".equals(data)){
                //maak een json object
                JSONObject obj = new JSONObject(data);
                
                JSONArray array = obj.getJSONArray("result");
                for(int i = 0 ; i < array.length() ; i++){
                    
                    //list
                    String  markt = array.getJSONObject(i).getString("MarketName");
                    double high = array.getJSONObject(i).getDouble("High");
                    double low = array.getJSONObject(i).getDouble("Low");
                    double last = array.getJSONObject(i).getDouble("Last");
                    double volume = array.getJSONObject(i).getDouble("Volume");
                    double volumeBTC =  volume * last;
                    double bid = array.getJSONObject(i).getDouble("Bid");
                    double ask = array.getJSONObject(i).getDouble("Ask");
                    
                    //query
                    String query = "SELECT COUNT(*) AS total FROM marktdata WHERE Handelsplaats = 'bittrex' AND Markt='"+markt+"'";
                    ResultSet rs1 = stmt.executeQuery(query);
                    int count = 0;
                    while (rs1.next()) {
                        count = rs1.getInt("total");
                    }
                    
                    if (count == 0) {
                        String sql = "INSERT INTO marktdata (Handelsplaats, Markt, High, Low, Volume, VolumeBTC, Bid, Ask, Last)"
                            +"VALUES('bittrex', '"+markt+"', '"+high+"', '"+low+"', '"+volume+"', '"+volumeBTC+"', '"+bid+"', '"+ask
                            +"', '"+last+"')";

                        //voer sql uit
                        stmt.execute(sql);
                    } else {
                        String sql = "UPDATE marktdata SET High='"+high+"', Low='"+low+"',  Volume='"+volume+"',"
                            +"volumeBTC='"+volumeBTC+"', Bid='"+bid+"', Ask='"+ask+"', Last='"+last+"'"
                            + "WHERE Handelsplaats='bittrex' AND Markt='"+markt+"';";
                        stmt.execute(sql);
                    }
                    
                    //insert string
                    String sql = "INSERT INTO bittrexmarktdata (Handelsplaats, Markt, High, Low, Volume, VolumeBTC, Bid, Ask, Last, Datum, Tijd)"
                            +"VALUES('bittrex', '"+markt+"', '"+high+"', '"+low+"', '"+volume+"', '"+volumeBTC+"', '"+bid+"', '"+ask
                            +"', '"+last+"', '"+datumIddatum+"', '"+time.getTimeStamp()+"')";
                    //System.out.println(sql);
                    //voer sql uit
                    stmt.execute(sql);
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
