/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transactions;

import Exceptions.IncorrectFileStructure;
import Exceptions.InexistentControllerException;
import Exceptions.InexistentControllerMethodException;
import Exceptions.InexistentModelException;
import Logs.LogsManager;
import Mvc.Model;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Equipo1
 */
public class TransactionParser {

    private Object instantiateModel(String modelName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object instance = null;
        try {
            Class instanceClass = Class.forName(modelName);
            Constructor[] cons = instanceClass.getConstructors();
            instance = cons[0].newInstance();
        } catch (ClassNotFoundException ex) {
            RuntimeException ex1 = new InexistentModelException("inexistent model class: " + ex.getLocalizedMessage());
            LogsManager.getInstance().addExceptionLog(ex1);                  
            throw ex1;
        }
        return instance;
    }

    private Object instantiateController(String controllerName, Object modelInstance) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InexistentControllerException {
        Object instance = null;
        try {
            Class instanceClass = Class.forName(controllerName);
            Class partypes[] = new Class[1];
            partypes[0] = Model.class;
            Constructor cons = instanceClass.getConstructor(partypes);
            Object arglist[] = new Object[1];
            arglist[0] = modelInstance;
            instance = cons.newInstance(arglist);
        } catch (ClassNotFoundException ex) {
            RuntimeException ex1 = new InexistentControllerException("inexistent controller class: " + ex.getLocalizedMessage());
            LogsManager.getInstance().addExceptionLog(ex1);                  
            throw ex1;
        }
        return instance;
    }

    private Method instantiateMethod(String controllerName, String methodName) throws ClassNotFoundException {
        Method instance = null;
        try {
            Class instanceClass = Class.forName(controllerName);
            instance = instanceClass.getMethod(methodName, Class.forName("Transactions.Event"));
        } catch (NoSuchMethodException ex) {
            String message = "inexistent method: Controller: " + controllerName + ", Method: " + methodName;
            RuntimeException ex1 = new InexistentControllerMethodException(message);
            LogsManager.getInstance().addExceptionLog(ex1);      
            throw ex1;
        } catch (SecurityException ex) {
            LogsManager.getInstance().addExceptionLog(ex);                              
            Logger.getLogger(TransactionParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instance;
    }

    private boolean isModelInstantiated(HashMap<String, Object> modelsList, String id) {
        boolean result = true;
        Object model = modelsList.get(id);
        if (id == "" || model == null) {
            result = false;
        }
        return result;
    }

    public HashMap<String, Object> parseNodes(NodeList nodeList) throws IllegalAccessException, InvocationTargetException, InexistentControllerException {
        HashMap<String, Object> transactionsData = new HashMap();
        HashMap<String, Transaction> actions = new HashMap();
        HashMap<String, Object> modelsList = new HashMap();

        int nodeLength = nodeList.getLength();
        for (int index = 0; index < nodeLength; index++) {
            Node node = nodeList.item(index);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                try {
                    Element element = (Element) node;
                    validateStructure(element);
                    
                    String id = element.getAttribute("id");

                    Node model = element.getElementsByTagName("model").item(0);
                    String modelName = model.getTextContent();
                    String modelId = ((Element) model).getAttribute("id");

                    String controllerName = element.getElementsByTagName("controller").item(0).getTextContent();
                    String methodName = element.getElementsByTagName("method").item(0).getTextContent();

                    Object modelInstance = null;
                    if (isModelInstantiated(modelsList, modelId)) {
                        modelInstance = modelsList.get(modelId);
                    } else {
                        modelInstance = instantiateModel(modelName);
                    }

                    Object controllerInstance = instantiateController(controllerName, modelInstance);
                    Method method = instantiateMethod(controllerName, methodName);

                    modelsList.put(modelId, modelInstance);
                    actions.put(id, new Transaction(id, modelInstance, controllerInstance, method));
                } catch (ClassNotFoundException ex) {
                    LogsManager.getInstance().addExceptionLog(ex);
                    Logger.getLogger(TransactionParser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    LogsManager.getInstance().addExceptionLog(ex);                    
                    Logger.getLogger(TransactionParser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    LogsManager.getInstance().addExceptionLog(ex);                    
                    Logger.getLogger(TransactionParser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    LogsManager.getInstance().addExceptionLog(ex);                    
                    Logger.getLogger(TransactionParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        transactionsData.put("Models", modelsList);
        transactionsData.put("Transactions", actions);
        return transactionsData;
    }

    private void validateStructure(Element element) {
        String id = element.getAttribute("id");
        NodeList modelTag = element.getElementsByTagName("model");
        NodeList controllerTag = element.getElementsByTagName("controller");
        NodeList methodTag = element.getElementsByTagName("method");

        if (id.equals("")) {
            RuntimeException ex =  new IncorrectFileStructure("Missing id attribute on <transaction></transaction>");
            LogsManager.getInstance().addExceptionLog(ex);
            throw ex;
        } else if (modelTag.getLength() == 0) {
            RuntimeException ex = new IncorrectFileStructure("Missing <model></model> tag for Transaction: " + id);
            LogsManager.getInstance().addExceptionLog(ex);
            throw ex;            
        } else if (controllerTag.getLength() == 0) {
            RuntimeException ex = new IncorrectFileStructure("Missing <controller></controller> tag for Transaction: " + id);
            LogsManager.getInstance().addExceptionLog(ex);
            throw ex;            
        } else if (methodTag.getLength() == 0) {
            RuntimeException ex = new IncorrectFileStructure("Missing <method></method> tag for Transaction: " + id);
            LogsManager.getInstance().addExceptionLog(ex);
            throw ex;            
        }
        
    }

}
