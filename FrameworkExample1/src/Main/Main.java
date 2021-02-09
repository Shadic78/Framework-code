/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Logs.LogsManager;
import Modelo.Calculadora;
import Transactions.TransactionInvoker;
import Vista.FormSumar;

/**
 *
 * @author Equipo1
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LogsManager.init();
        TransactionInvoker.init();
        
        TransactionInvoker invoker = TransactionInvoker.getInstance();
        Calculadora calc = (Calculadora) invoker.getModel("calculadora");
        FormSumar form = new FormSumar(calc);
    }
    
}
