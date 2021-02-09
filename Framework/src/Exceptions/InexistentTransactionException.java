/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Equipo1
 */
public class InexistentTransactionException extends RuntimeException {

    public InexistentTransactionException(String message) {
        super(message);
    }

}
