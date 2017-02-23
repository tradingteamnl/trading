/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import userinterface.Home;
import mysql.MysqlError;
/**
 *
 * @author michel
 */
public class starter{
    
    public static void main(String[] args) {
        
        //maak objecten aan
        Drivers drivers = new Drivers();
        Home home = new Home();
        FileSystem filesystem = new FileSystem();
        MysqlError mysqlerror = new MysqlError();
        
        
        //kijk of folder bestaat
        filesystem.folderExist();
        
        Thread thread = new Thread("Back software is start.") {
            public void run(){
                drivers.main();
                System.out.println("run by: " + getName());
            }
        };

        thread.start();
        
        String sql = "lol";
        String message = "lol";
        mysqlerror.mysqlError(sql, message, "*");
        
        Home.main(args);
    }
}
