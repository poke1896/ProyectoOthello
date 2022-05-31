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
public class Node {

    private BoardSlot element;
    private Node up;
    private Node upperLeftDiagonal;
    private Node upperRightDiagonal;
    private Node right;
    private Node left;
    private Node lowerLeftDiagonal;
    private Node lowerRightDiagonal;
    private Node down;

    public Node(BoardSlot element) {
        this.element = element;
    }

    public BoardSlot getElement() {
        return element;
    }

    public void setElement(BoardSlot element) {
        this.element = element;
    }

    public Node getUp() {
        return up;
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public Node getUpperLeftDiagonal() {
        return upperLeftDiagonal;
    }

    public void setUpperLeftDiagonal(Node upperLeftDiagonal) {
        this.upperLeftDiagonal = upperLeftDiagonal;
    }

    public Node getUpperRightDiagonal() {
        return upperRightDiagonal;
    }

    public void setUpperRightDiagonal(Node upperRightDiagonal) {
        this.upperRightDiagonal = upperRightDiagonal;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getLowerLeftDiagonal() {
        return lowerLeftDiagonal;
    }

    public void setLowerLeftDiagonal(Node lowerLeftDiagonal) {
        this.lowerLeftDiagonal = lowerLeftDiagonal;
    }

    public Node getLowerRightDiagonal() {
        return lowerRightDiagonal;
    }

    public void setLowerRightDiagonal(Node lowerRightDiagonal) {
        this.lowerRightDiagonal = lowerRightDiagonal;
    }

    public Node getDown() {
        return down;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    /**
     * Metodo para ahorrarse codigo y poder elegir direcciones de forma dinamica
     * @param direction
     * @return El nodo de la direccion elegida
     */
    public Node getAdjadcentNode (int direction){
        Node result = null;
        
        switch (direction) {
            case 0:
                result= this.up; 
                break;
            case 1:
                result = this.upperRightDiagonal;
                break;
            case 2:
                result = this.right;
                break;
            case 3:
                result = this.lowerRightDiagonal;
                break;
            case 4:
                result = this.down;
                break;
            case 5:
                result = this.lowerLeftDiagonal;
                break;
            case 6:
                result = this.left;
                break;
            case 7:
                result = this.upperLeftDiagonal;
                break;
            default:
                break;
        }
        return result;
    }
   
 

}