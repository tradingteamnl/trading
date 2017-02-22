/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author michel
 */
public class FileSystem {
    
    //maakt objecten aan
    JFileChooser fr = new JFileChooser();
    
    
    
    
    
    public void folderExist (){
        
        FileSystemView fw = fr.getFileSystemView();
        Path test = Paths.get(fw.getDefaultDirectory() + "/Documents/trading");
        System.out.println(fw.getDefaultDirectory() + "/Documents//trading");
        //kijk of de folder bestaat
        if(!Files.exists(test)){
            System.out.println("No Folder");
            
            //folder word aangemaakt.
            File file = new File(fw.getDefaultDirectory() + "/Documents/trading");
            file.mkdir();
            System.out.println("Folder created");
         } else {
            System.out.println("Folder exists");
        }
    } 
    
    public String saveFile (String fileName, String FileData){
        try{
            PrintWriter writer = new PrintWriter(fileName+".txt", "UTF-8");
            writer.println(FileData);
            writer.close();
            return "true";
        } catch (IOException e) {
           return "false";
        }
    }
    
    public String readFile (String file){
        try {
            FileSystemView fw = fr.getFileSystemView();
            return new String(Files.readAllBytes(Paths.get(fw.getDefaultDirectory() + "/Documents/trading/"+file)));
        } catch (IOException ex) {
            return "false";
        }
    }
    
    public String readConfig (){
        return readFile("config.json");
    }
}
