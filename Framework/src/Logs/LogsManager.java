/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logs;

import Exceptions.FrameworkException;
import Transactions.Event;
import Transactions.Transaction;
import Transactions.XMLReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Equipo1
 */
public class LogsManager {

    private final String CONFIG_FILE_PATH = "src/logsConfig.xml";
    private final String LOGS_TAG = "logs";
    private static LogsManager logsManager;
    private HashMap<String, String> config = new HashMap();
    private File currentFile;
    private int currentFileNum;
    private LogsFileUtils utils;

    private LogsManager() {
        try {
            this.utils = new LogsFileUtils();
            
            XMLReader reader = new XMLReader();
            LogsConfigParser parser = new LogsConfigParser();

            NodeList nodes = reader.readNodes(CONFIG_FILE_PATH, LOGS_TAG);
            this.config = parser.parseConfig(nodes);
            this.currentFileNum = 1;
            this.currentFile = utils.createNewFile(config, currentFileNum);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(LogsManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LogsManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(LogsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void init() {
        if(logsManager == null) {
            logsManager = new LogsManager();
        }
        else {
            throw new FrameworkException("LogsManager is already inited");
        }
    }

    private void moveToNextFile() {
        this.currentFileNum++;
        this.currentFile = utils.createNewFile(config, currentFileNum);
    }

    private boolean isCurrentFileBelowMaximumSize() {
        int maximumSize = Integer.parseInt(config.get("maximumFileSize"));
        return currentFile.length() < maximumSize;
    }

    public void addTransactionLog(Transaction transaction, Event event) {
        if (!isCurrentFileBelowMaximumSize()) {
            moveToNextFile();
        }
        String text = "TRANSACTION: " + transaction.getActionName() + ", EVENT: " + event.toString();
        utils.appendToFile(currentFile, text);
    }

    public void addExceptionLog(Exception ex) {
        if (!isCurrentFileBelowMaximumSize()) {
            moveToNextFile();
        }
        utils.appendToFile(currentFile, "EXCEPTION: " + ex.getMessage());
    }
    
    public void addCustomLog(String log) {
        if (!isCurrentFileBelowMaximumSize()) {
            moveToNextFile();
        }
        utils.appendToFile(currentFile, "CUSTOM-LOG: " + log);        
    }
    
    public static LogsManager getInstance() {
        if(logsManager == null) {
            throw new FrameworkException("LogsManager needs to be inited");
        }
        return logsManager;
    }    

}
