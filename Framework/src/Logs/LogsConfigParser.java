/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logs;

import Exceptions.IncorrectFileStructure;
import Exceptions.IncorrectLogsFileSizeConfig;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Equipo1
 */
public class LogsConfigParser {

    private final int MINIMUM_FILE_SIZE_KB = 10;
    private final String DEFAULT_PREFIX = "project";
    private final String DEFAULT_PATH = "";

    /*
        <logs>
            <size>32</size>
            <prefix>votos</prefix>
            <path>c:/users/equipo1/desktop/</path>
        </logs>
     */
    public HashMap<String, String> parseConfig(NodeList nodeList) {
        if(nodeList.getLength() == 0) {
            throw new IncorrectFileStructure("Missing <logs></logs> tag");
        }
        
        HashMap<String, String> configData = new HashMap();
        Node node = nodeList.item(0);
        Element element = (Element) node;
        
        validateStructure(element);
        
        int size = Integer.parseInt(element.getElementsByTagName("size").item(0).getTextContent());
        NodeList prefixTag = element.getElementsByTagName("prefix");      
        NodeList pathTag = element.getElementsByTagName("path"); 
        
        if(prefixTag.getLength() == 0) {
            configData.put("prefix", DEFAULT_PREFIX);
        } else {
            configData.put("prefix", prefixTag.item(0).getTextContent());
        }
        
        if(pathTag.getLength() == 0) {
            configData.put("path", DEFAULT_PATH);
            configData.put("isCustomPath", "false");
        } else {
            configData.put("path", pathTag.item(0).getTextContent());
            configData.put("isCustomPath", "true");            
        }        
        
        configData.put("maximumFileSize", Integer.toString(getFileSizeBytes(size)));
        
        return configData;
    }

    private void validateStructure(Element element) {
        NodeList sizeTag = element.getElementsByTagName("size");

        if (sizeTag.getLength() == 0) {
            throw new IncorrectFileStructure("Missing <size></size> tag");
        }

        String size = sizeTag.item(0).getTextContent();
        if (!isNumeric(size)) {
            throw new IncorrectFileStructure("The content of <size></size> must be numeric");
        }
        if (!validateSize(Integer.parseInt(size))) {
            throw new IncorrectLogsFileSizeConfig("The minimum size in KiloBytes is " + MINIMUM_FILE_SIZE_KB + "kb");
        }
    }

    private boolean isNumeric(String str) {
        boolean isNumeric = true;
        try {
            int size = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            isNumeric = false;
        }
        return isNumeric;
    }

    private boolean validateSize(int sizeBytes) {
        return sizeBytes >= MINIMUM_FILE_SIZE_KB;
    }

    private int getFileSizeKB(int sizeBytes) {
        return (sizeBytes / 1024);
    }
    
    private int getFileSizeBytes(int sizeKiloBytes) {
        return (sizeKiloBytes * 1024);
    }

}
