/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transactions;

import Exceptions.FrameworkException;
import Exceptions.InexistentTransactionException;
import Logs.LogsManager;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.NodeList;

/**
 *
 * @author Equipo1
 */
public class TransactionInvoker {

    private final String FILE_PATH = "src/config.xml";
    private final String TRANSACTIONS_TAG = "transaction";
    private static TransactionInvoker transactionInvoker;
    private HashMap<String, Object> models = new HashMap();
    private HashMap<String, Transaction> actions = new HashMap();

    private TransactionInvoker() {
        try {
            
            XMLReader reader = new XMLReader();
            TransactionParser parser = new TransactionParser();

            NodeList nodes = reader.readNodes(FILE_PATH, TRANSACTIONS_TAG);
            HashMap<String, Object> transactionsData = parser.parseNodes(nodes);

            this.models = (HashMap<String, Object>) transactionsData.get("Models");
            this.actions = (HashMap<String, Transaction>) transactionsData.get("Transactions");            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TransactionInvoker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TransactionInvoker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TransactionInvoker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TransactionInvoker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(TransactionInvoker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void init() {
        if(transactionInvoker == null) {
            transactionInvoker = new TransactionInvoker();
        }
        else {
            throw new FrameworkException("TransactionInvoker is already inited");
        }
    }    

    public static TransactionInvoker getInstance() {
        if(transactionInvoker == null) {
            throw new FrameworkException("TransactionInvoker needs to be inited");
        }        
        return transactionInvoker;
    }

    public Object invokeAction(String actionName, Event event) {
        try {
            Transaction transaction = actions.get(actionName);
            if(transaction == null) {
                RuntimeException ex = new InexistentTransactionException("inexistent transaction: " + actionName);
                LogsManager.getInstance().addExceptionLog(ex);
                throw ex;
            }
            LogsManager.getInstance().addTransactionLog(transaction, event);
            return transaction.callMethod(event);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(TransactionInvoker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TransactionInvoker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Object getModel(String modelId) {
        return models.get(modelId);
    }

    public Object getActionModel(String actionName) {
        return actions.get(actionName).getModelInstance();
    }

}
