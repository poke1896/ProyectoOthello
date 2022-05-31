/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectoothello;
/**
 *
 * @author Poke
 */

public class BoardSlot {
    private Chip chip;
    
    private int x;
    private int y;

    public BoardSlot( int x, int y) {
        
        this.x = x;
        this.y = y;
    }

    public Chip getChip() {
        return chip;
    }

    public void setChip(Chip chip) {
        this.chip = chip;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public String printInfo(){
        return "["+x+","+y+"]";
    }
}