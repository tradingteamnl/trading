/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import bittrex.BittrexMarketRequest;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import trading.BerekenGemiddelde;

/**
 *
 * @author michel
 */
public class Drivers {
    
    BittrexMarketRequest r = new BittrexMarketRequest();
    BerekenGemiddelde berekengemiddelde = new BerekenGemiddelde();
    
    public void main(){
        
        
        FileSystem filesystem = new FileSystem();
        String OS = System.getProperty("os.name").toLowerCase();
        
        //run code
        filesystem.folderExist();
        //r.bittrexmarketrequest();
        
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                r.bittrexmarketrequest();
                berekengemiddelde.forLoop();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, new Date(), 60000);
        
        
        System.out.println("fafafasd");
    }
}
