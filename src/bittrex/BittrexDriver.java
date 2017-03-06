/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bittrex;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author michel
 */
public class BittrexDriver {
    
    //maak object aan
    BittrexMarketRequest bittrexmarketrequest = new BittrexMarketRequest();
    BittrexBalance bittrexbalance = new BittrexBalance();
    
    public void main(){
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
               bittrexbalance.UpdateBalance();
            }
        };
        
        Timer timer = new Timer();
        timer.schedule(task, new Date(), 60000);
        
        System.out.println("Bittrex driver.");
    };
}
