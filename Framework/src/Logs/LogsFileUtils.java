/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Equipo1
 */
public class LogsFileUtils {
    
    public File createNewFile(HashMap<String, String> config, int fileNum) {
        File file = null;
        try {
            String path = config.get("path");
            String prefix = config.get("prefix");
            String fileCompletePath = path + prefix + "-logs-" + fileNum + ".txt";

            file = new File(fileCompletePath);
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            String text = getCurrentDate() + " Created logs file #" + fileNum;
            bw.write(text);
            bw.close();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(LogsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }    
    
    public void appendToFile(File file, String text) {
        try {
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.newLine();
            bw.write(getCurrentDate() + " " + text);          
            bw.close();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(LogsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }    
    
}
