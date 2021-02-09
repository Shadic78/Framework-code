/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transactions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Transaction {

    private String actionName;
    private Method methodInstance;
    private Object modelInstance;
    private Object controllerInstance;

    public Transaction(String actionName, Object modelInstance, Object controllerInstance, Method methodInstance) {
        this.actionName = actionName;
        this.modelInstance = modelInstance;
        this.controllerInstance = controllerInstance;
        this.methodInstance = methodInstance;
    }

    public Method getMethodInstance() {
        return methodInstance;
    }

    public Object getModelInstance() {
        return modelInstance;
    }

    public Object getControllerInstance() {
        return controllerInstance;
    }

    public Object callMethod(Event event) throws InvocationTargetException, IllegalAccessException {
        return methodInstance.invoke(controllerInstance, event);
    }

    public String getActionName() {
        return actionName;
    }

    @Override
    public String toString() {
        return "Transaction{" + "actionName=" + actionName + ", methodInstance=" + methodInstance + ", modelInstance=" + modelInstance + ", controllerInstance=" + controllerInstance + '}';
    }
    
}
