/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectoothello;


import javax.swing.JOptionPane;

/**
 *
 * @author Poke
 */
// esta clase MensasCenter lo que hace esas ahorrar  los mensajes  de los  joptionpane 
public class MessageCenter {
    
    public void showMessage(String message){
        JOptionPane.showMessageDialog(null, message);
    }
    
    public String getString(String message){
        return JOptionPane.showInputDialog(message);
    }
    
    public int getInt(String message){
        return Integer.parseInt(getString(message));
    }
    
    
}
