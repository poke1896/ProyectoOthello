/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectoothello;
public class Chip {
  
    
    private boolean isCurrentMove;
    private String color;

    public Chip(boolean isCurrentMove, String color) {
        this.isCurrentMove = isCurrentMove;
        this.color = color;
    }

    public boolean isIsCurrentMove() {
        return isCurrentMove;
    }

    public void setIsCurrentMove(boolean isCurrentMove) {
        this.isCurrentMove = isCurrentMove;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

