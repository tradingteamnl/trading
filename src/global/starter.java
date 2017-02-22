/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import ui.Home;
/**
 *
 * @author michel
 */
public class starter{
    public static void main(String[] args) {
        
        //maak objecten aan
        Drivers drivers = new Drivers();
        Home home = new Home();
        
        //start een nieuw Thread zodat alle background code blijven draaien
        Thread t = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()){
                drivers.main();
                
                try{
                    Thread.sleep(1200000000);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        });
        t.start();
        
        
        Home.main(args);
    }
}
