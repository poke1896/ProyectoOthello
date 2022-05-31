/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectoothello;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Poke
 */

public class Board {

    //hace el primer nodo
    private Node first;

    /**
     * Metodo que crea la lista enlazada. Se llama a createAdjacentNodes() para
     * nodo que se va creando Se recorre de izquierda a derecha y al llegar al
     * final del tablero, se devuelve al inicio Posteriomente se baja una fila y
     * se recorre nuevamente hacia la derecha
     */
    public void populateList() {//1            COMPLEJIDAD    6+7n+10n2+2n3
        first = generateNode(0, 0); //3
        Node currentNode = first; //1
        for (int i = 0; i < 8; i++) { //1+3n
            for (int j = 0; j < 8; j++) { //4n
                currentNode = createAdjacentNodes(currentNode);//2n2

                if (j == 7) {//1n2
                    for (int k = 0; k < 7; k++) {//4n2
                        currentNode = currentNode.getLeft();//2n3

                    }
                    currentNode = currentNode.getDown();//2n2
                } else {
                    currentNode = currentNode.getRight();//2n2
                }
            }
        }
    }

    /**
     * Metodo que intenta poner una ficha en las coordenadas dadas
     *
     * @param x Coordenada X
     * @param y Coordenada Y
     * @param color Color de la ficha a poner
     * @param isInitialChip Variable que indica si realizar las validaciones
     * requeridas o no. En caso de ser las fichas iniciales, no se realizan
     * @return Retorna falso en caso de no poder poner la ficha
     */
    public boolean placeChip(int x, int y, String color, boolean isInitialChip) {
        Node result = findNode(x, y);
        if (result == null) {
            return false;
        } else {
            if (isInitialChip) {
                result.getElement().setChip(new Chip(true, color));
                return true;
            } else {
                if (checkAdjacentNodes(result)) {
                    result.getElement().setChip(new Chip(true, color));
                    return checkAllDirections(result);
                } else {
                    System.out.println("cant place the chip");
                    return false;
                }

            }

        }
    }

    /**
     * Metodo que busca el ultimo nodo de una direccion dada
     *
     * @param node Nodo para comprobar
     * @param direction Direccion en la que se va a encontrar el ultimo nodo
     * @return El ultimo nodo valido encontrado (Con ficha)
     */
    public Node findLastChip(Node node, int direction) {//1
        Node lastNode = null;//1
        Node currentNode = node;//1
        System.out.println("CHECKING DIRECTION ===  " + direction);//2
        while (currentNode.getAdjadcentNode(direction) != null) {//3
            if (currentNode.getElement().getChip() != null) {//2
                lastNode = currentNode;//1n2
            }
            currentNode = currentNode.getAdjadcentNode(direction);//2n
        }

        if (currentNode.getElement().getChip() == null) {//3

            return lastNode;//1
        }

        return currentNode;//1
    }
   //1n2+2n+15

    /**
     * Metodo que comprueba una direccion para ver si es posible convertir
     * fichas
     *
     * @param first Primer nodo
     * @param lastNode Ultimo nodo
     * @param direction Direccion a donde comprobar
     * @return La cantidad de fichas convertidas
     */
    public int turnChips(Node first, Node lastNode, int direction) {
        int chipsTurned = 0;
        Node currentNode = first.getAdjadcentNode(direction);
        System.out.println("CURRENT NODE IS >>>>" + currentNode.getElement().printInfo());
        System.out.println("LAST NODE IS ......" + lastNode.getElement().printInfo());
        while (!currentNode.equals(lastNode)) {
            System.out.println("TURNED 1 CHIP");
            if (currentNode.getElement().getChip() != null) {
                currentNode.getElement().getChip().setColor(first.getElement().getChip().getColor());
            }

            currentNode = currentNode.getAdjadcentNode(direction);
            chipsTurned++;
        }
        return chipsTurned;
    }

    /**
     * Metodo que comprueba un nodo y todas sus direcciones para ver si puede
     * convertir fichas en esa direccion
     *
     * @param node Nodo para comprar las fichas adjacentes
     * @return Si logra convertir fichas o no
     */
    public boolean checkAllDirections(Node node) {
        int chipsTurned = 0;
        Node lastNode = null;
        System.out.println("PLACINNG THE CHIP ==" + node.getElement().printInfo());
        for (int i = 0; i < 8; i++) {
            lastNode = findLastChip(node, i);
            if (lastNode != null) {
                if (!node.equals(lastNode)) {

                    if (lastNode.getElement().getChip() != null) {

                        if (lastNode.getElement().getChip().getColor().equals(node.getElement().getChip().getColor())) {
                            System.out.println("turning chips");
                            chipsTurned += turnChips(node, lastNode, i);

                        }
                    }

                }
            }

        }
        if (chipsTurned == 0) {
            node.getElement().setChip(null);
            return false;
        }
        return true;
    }

    /**
     * Metodo que revisa las 8 posiciones cardinales de un nodo para ver si es
     * posible ponerlo. De no tener nodos adjacentes, no se puede realizar un
     * movmiento
     *
     * @param node El nodo para revisar
     * @return Si tiene nodos vacios en todas las direcciones o no
     */
    
   //Complejidad 6+14n
    public boolean checkAdjacentNodes(Node node) { //2
        int counter = 0;//1
        for (int i = 0; i < 7; i++) {//1+3n

            if (node.getAdjadcentNode(i) == null) { //2n
              
                counter++; //2n
            } else {
                if (node.getAdjadcentNode(i).getElement().getChip() == null) {//5n
                
                    counter++;//2n
                }
            }
        }
      
        return (counter != 7); //2
    }
    
    /**
     * Metodo que encuentra un nodo con base en una posicion de X y Y
     *
     * @param x Posicion X
     * @param y Posicion Y
     * @return El nodo encontrado
     */
    public Node findNode(int x, int y) {
        Node currentNode = first;
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
                // si el nodo actual esta en la x y y actuales son iguales a parametros
                //devuelvame esa posicion 
                if (currentNode == null) {
                    break;
                }

                if (currentNode.getElement().getX() == x && currentNode.getElement().getY() == y) {

                    return currentNode;
                }
                //significa que aqui ya esta en el ultimo nodo a la derecha fila 1 
                // columna 7, se devuelve atras y baja una fila y se mueve a la derecha
                if (j == 7) {
                    for (int k = 0; k < 7; k++) {
                        currentNode = currentNode.getLeft();
                    }
                    currentNode = currentNode.getDown();// baja una fila

                } else {
                    currentNode = currentNode.getRight();// solo se mueve a la derecha
                }
            }
        }

        return null;
    }

    /**
     * Metodo que genera un nuevo nodo
     *
     * @param x Coordenada X
     * @param y Coordenada Y
     * @return El nodo encontrado
     */
    public Node generateNode(int x, int y) {
        //System.out.println("NEW NODE IS [" + x + "," + y + "]");

        Node newNode = new Node(new BoardSlot(x, y));
        //System.out.println("NEW HASH IS >>>>>" + newNode);
        return newNode;
    }

    //SI SE INSERTAN CELDAS ARRIBA O EN DIAGONAL, BUSCAR CON FIND NODE, YA EXISTEN Y PUEDEN SER ENCONTRADAS
    //SI SE INSERTAN CELDAS ABAJO PREGUNTAR POR LA DIAGONAL DE LA CELDA ANTERIOR (TAMBIEN BUSCANDO)
    /**
     * Este metodo es el principal para la creacion de la lista. A traves de
     * patrones para cada uno de las posiciones cardinales, se generan los
     * nodos. Son 8 posiciones, se comprueba si existen y se crean sino. Existen
     * casos especiales que comprobar, para generar ciertas posicion se utiliza
     * el metodo findNode() para encontra el nodo ya existente y no
     * sobreescribirlo.
     *
     * @param currentNode
     * @return El nodo actual modificado (Con sus nodos adjacentes agregados)
     */
    public Node createAdjacentNodes(Node currentNode) {
        int newX = 0;
        int newY = 0;
        // busc, redirecciona y ponel el puntero para llenar
        // crea (llena) por que esta creada diagonal izquierda arriba
        int createdNodes = 0;
        //System.out.println("CREATING NODES FOR ++++++ " + currentNode.getElement().printInfo());
        newX = currentNode.getElement().getX() - 1;
        newY = currentNode.getElement().getY() - 1;
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (currentNode.getUpperLeftDiagonal() == null) {
                //System.out.println("CREATED DIAGONAL UPPER LEFT");

                //CASO ESPECIAL
                currentNode.setUpperLeftDiagonal(findNode(newX, newY));
                currentNode.getUpperLeftDiagonal().setLowerRightDiagonal(currentNode);
                createdNodes++;
            }
        }
        // crea (llena) por que esta creada izquierda 
        newX = currentNode.getElement().getX() - 1;
        newY = currentNode.getElement().getY();
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (currentNode.getUp() == null) {
                // System.out.println("CREATED UP");

                //CASO ESPECIAL
                currentNode.setUp(findNode(newX, newY));
                currentNode.getUp().setDown(currentNode);
                createdNodes++;
            }
        }
        // crea (llena) por que esta creada diagonal izquierda abajo
        newX = currentNode.getElement().getX() - 1;
        newY = currentNode.getElement().getY() + 1;
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (currentNode.getUpperRightDiagonal() == null) {
                //System.out.println("CREATED DIAGONAL UPPER RIGHT");

                //CASO ESPECIAL
                currentNode.setUpperRightDiagonal(findNode(newX, newY));
                currentNode.getUpperRightDiagonal().setLowerLeftDiagonal(currentNode);
                createdNodes++;
            }
        }
        // crea (llena) por que esta creada abajo
        newX = currentNode.getElement().getX();
        newY = currentNode.getElement().getY() + 1;
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (currentNode.getRight() == null) {
                //System.out.println("CREATED RIGHT");
                currentNode.setRight(generateNode(newX, newY));
                currentNode.getRight().setLeft(currentNode);
                createdNodes++;
            }
        }
        // crea (llena) por que esta creada arriba
        newX = currentNode.getElement().getX();
        newY = currentNode.getElement().getY() - 1;
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (currentNode.getLeft() == null) {
                // System.out.println("CREATED LEFT");
                currentNode.setLeft(generateNode(newX, newY));
                currentNode.getLeft().setRight(currentNode);

                createdNodes++;
            }
        }
        // crea (llena) por que esta creada diagonal derecha abajo  
        newX = currentNode.getElement().getX() + 1;
        newY = currentNode.getElement().getY() + 1;
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (currentNode.getLowerRightDiagonal() == null) {

                // System.out.println("CREATED DIAGONAL LOWER RIGHT");
                currentNode.setLowerRightDiagonal(generateNode(newX, newY));
                currentNode.getLowerRightDiagonal().setUpperLeftDiagonal(currentNode);
                createdNodes++;

            }
        }
        // crea (llena) por que esta creada derecha
        newX = currentNode.getElement().getX() + 1;
        newY = currentNode.getElement().getY();
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (currentNode.getDown() == null) {
                //System.out.println("CREATED DOWN");

                //CASO ESPECIAL
                Node newNode = findNode(newX + 1, newY);
                if (newNode == null) {
                    currentNode.setDown(generateNode(newX, newY));
                } else {
                    currentNode.setDown(newNode);
                }

                currentNode.getDown().setUp(currentNode);
                createdNodes++;
            }
        }
        // crea (llena) por que esta creada diagonal derecha arriba
        newX = currentNode.getElement().getX() + 1;
        newY = currentNode.getElement().getY() - 1;
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (currentNode.getLowerLeftDiagonal() == null) {
                //System.out.println("CREATED DIAGONAL LOWER LEFT");

                //CASO ESPECIAL
                currentNode.setLowerLeftDiagonal(findNode(currentNode.getElement().getX(), currentNode.getElement().getY() - 1).getDown());
                currentNode.getLowerLeftDiagonal().setUpperRightDiagonal(currentNode);
                createdNodes++;
            }
        }

        return currentNode;
    }

    /**
     * Realiza una sumatoria de las fichas blancas y negras
     *
     * @return El caso generado. Gana jugador 1, jugador 2 o empate
     */
    public int getFinalScore() {

        int playerOneScore = 0;
        int playerTwoScore = 0;
        Node currentNode = first;

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
                if (currentNode.getElement().getChip() != null) {

                    switch (currentNode.getElement().getChip().getColor()) {
                        case "White":
                            playerOneScore++;

                            break;
                        case "Black":

                            playerTwoScore++;
                            break;
                        default:
                            break;

                    }

                }
                if (j == 7) {

                    for (int k = 0; k < 7; k++) {
                        currentNode = currentNode.getLeft();
                    }
                    currentNode = currentNode.getDown();

                } else {
                    currentNode = currentNode.getRight();
                }

            }

        }
        System.out.println("player 1 score is =" + playerOneScore);
        if (playerOneScore == playerTwoScore) {

            return 2;
        } else {
            if (playerOneScore > playerTwoScore) {

                return 0;
            } else {

                return 1;
            }
        }

    }

    /**
     * Metodo que encuentra todos los slots vacios
     *
     * @return Devuelve una lista de slots disponibles para poner fichas
     */
    public ArrayList<BoardSlot> getFreeBoardSlots() {
        ArrayList<BoardSlot> freeSlots = new ArrayList<>();
        Node currentNode = first;

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
                if (currentNode.getElement().getChip() == null) {
                    freeSlots.add(currentNode.getElement());
                }
                if (j == 7) {

                    for (int k = 0; k < 7; k++) {
                        currentNode = currentNode.getLeft();
                    }
                    currentNode = currentNode.getDown();

                } else {
                    currentNode = currentNode.getRight();
                }

            }

        }
        return freeSlots;
    }

    /**
     * Gerardo Metodo que genera movimientos aleatorios basados en jugadas de
     * profecionales para la computadora, hasta que agota sus opciones o logra
     * hacer el movimiento
     *
     * @return Si puede hacer un movimiento
     */
    public boolean randomizeElection() {
        ArrayList<BoardSlot> freeSlots = getFreeBoardSlots();// guarda la cantidad de fichas vacias
        Random rand = new Random();
        boolean exitLoop = false;
        int attempCounter = 0;
        int max = freeSlots.size();

        boolean ableToMakeMove = true; //  Este boolean tiene el turno en true para que juegue la computadora
        while (exitLoop == false) {
            int x = 0;
            int y = 0;
            BoardSlot randomBoardSlot = freeSlots.get(rand.nextInt(max));
            exitLoop = placeChip(randomBoardSlot.getX(), randomBoardSlot.getY(), "Black", false);
            attempCounter++;
            if (attempCounter >= freeSlots.size()) {
                //  Este boolean tiene el turno en falso por que el contador llego a la cantidad de fichas vacias 
                //cambia de turno
                ableToMakeMove = false;
                break;
            }

        }
        return ableToMakeMove;
    }

    /**
     * Metodo que imprime la lista enlazada en un tablero 8x8 con caracteres
     * especiales para las fichas blancas y negras
     */
    public void printBoard() {
        String board = "";
        Node currentNode = first;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentNode.getElement().getChip() == null) {
                    //Caracter para los espacios disponibles
                    board += String.format("%1$" + 10 + "s", "▣").replace(" ", "    ");
                } else {
                    switch (currentNode.getElement().getChip().getColor()) {
                        case "White":
                            board += String.format("%1$" + 10 + "s", "○").replace(" ", "    ");
                            //board += "      ◯      ";
                            break;
                        case "Black":
                            //board += "      ⬤      ";
                            board += String.format("%1$" + 10 + "s", "●").replace(" ", "    ");
                            break;
                        default:
                            break;
                    }
                }

                if (j == 7) {
                    // para que no se salga del tablero
                    for (int k = 0; k < 7; k++) {
                        currentNode = currentNode.getLeft();
                    }
                    currentNode = currentNode.getDown();
                    board += "\n\n\n";
                } else {
                    currentNode = currentNode.getRight();
                }

            }
        }
        board = "\n" + board;
        JOptionPane.showMessageDialog(null, board);
    }
}
