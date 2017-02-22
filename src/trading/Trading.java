/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trading;

//import
import bittrex.BittrexMarketRequest;
import global.FileSystem;

/**
 *
 * @author michel
 */
public class Trading {

    //maak objecten aan
    BittrexMarketRequest r = new BittrexMarketRequest();
    FileSystem filesystem = new FileSystem();
    
    /**
     * @param args the command line arguments
     */
   
    public static void main(String[] args) {
        //maak objecten aan
        BittrexMarketRequest r = new BittrexMarketRequest();
        FileSystem filesystem = new FileSystem();
        
        //run code
        filesystem.folderExist();
        r.bittrexmarketrequest();
    }
}
