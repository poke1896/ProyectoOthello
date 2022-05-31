/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectoothello;



import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Poke
 */
public class Menu {

    private MessageCenter messageCenter = new MessageCenter();
    private Board board = new Board();
    private int cont = 0;
    private String[] nameWin;// Guarda los nombres de los ganadores o empates

    private String playerOneName = ""; //Se escribe el nombre del primer jugador
    private String playerTwoName = ""; //Se escribe el nombre del segundo jugador

    private boolean isAgainstComputer = false; //Este boolean se inicializa en falso para que determinar si va el pc o no

    /**
     * Metodo que comprueba el ganador o empate
     */
    public void checkWinner() {
           int winCase = board.getFinalScore();
        switch (winCase) {
            case 0:
                messageCenter.showMessage(playerOneName + " WINS!!!!");
                nameWin[cont] = playerOneName;
                cont++;
                break;
            case 1:
                messageCenter.showMessage(playerTwoName + " WINS!!!!");
                nameWin[cont] = playerTwoName;
                cont++;
                break;
            case 2:
                messageCenter.showMessage("Draw....");
                nameWin[cont] = "Tie";
                cont++;
                break;
            default:
                break;
        }
    }

    /**
     * Metodo que muestra el menu inicial
     */
    public void showFirstMenu() {
        String playerSelectionMenu = "1)VS CPU\n2)VS Player"; // Se selecciona contra quién se quiere jugar

        boolean exitFirstMenu = false; //Se inicializa en false el boolean para que no se salga del menú

        int menuOption = 0;
        do {
            try {
                menuOption = messageCenter.getInt(playerSelectionMenu);
                exitFirstMenu = true; //Cierra el menú
            } catch (Exception e) {
                menuOption = -1;
            }

            switch (menuOption) { //Menu de opciones para ver contra quién se quiere jugar
                case 1: 
                    playerOneName = messageCenter.getString("Please type in the name of the player One."); //Pide el nombre del primer jugador
                    playerTwoName = "Computer"; //Determina al computador como oponente automaticamente
                    isAgainstComputer = true; // Cede el turno al computador
                    break;
                case 2:
                    playerOneName = messageCenter.getString("Please type in the name of the player One.");//Pide el nombre del primer jugador
                    playerTwoName = messageCenter.getString("Please type in the name of the player Two.");//Pide el nombre del segundo jugador
                    break;
                default:
                    messageCenter.showMessage("Please select a valid option.");
                    break;
            }
        } while (exitFirstMenu == false);

    }

    /**
     * Muestra el menu principal
     */
    public void showMainMenu() {
        showFirstMenu();
        board.populateList(); //llama al metodo de llenar la tabla
        board.placeChip(3, 3, "White", true); // le da las coordenadas a las fichas de las pocisionarse
        board.placeChip(3, 4, "Black", true); // le da las coordenadas a las fichas de las pocisionarse
        board.placeChip(4, 3, "Black", true); // le da las coordenadas a las fichas de las pocisionarse
        board.placeChip(4, 4, "White", true); // le da las coordenadas a las fichas de pocisionarse

        int playerOneTurnCount = 32; // Determina la cantidad maxima de fichas que podra abarcar el jugador
        int playerTwoTurnCount = 32; // Determina la cantidad maxima de fichas que podra abarcar el jugador

        boolean isPlayerOneTurn = true; //Determina si es turno del primer jugador
        String chipColor = ""; //Declara la variable color de ficha
        boolean exitGame = false; //Determina si se sale del juego 

       String menu = "1)Place chip\n2)Show board\n3)Finish turn\n4)List Win\n5)End game"; //Muestra el menú del juego
        String menuWithPlayerInfo = "";
        int menuOption = 0;
        do {
            menuWithPlayerInfo = "";
            if (isPlayerOneTurn) {
                menuWithPlayerInfo += menu + "\n\n" + playerOneName + "'s turn. White Chips."; //imprime el turno de un determinado jugador
            } else {
                menuWithPlayerInfo += menu + "\n\n" + playerTwoName + "'s turn. Black Chips."; //imprime el turno de un determinado jugador
            }
            try {
                menuOption = messageCenter.getInt(menuWithPlayerInfo);
            } catch (Exception e) {
                menuOption = -1;
            }

            switch (menuOption) {
                case 1:
                    if (isAgainstComputer && !isPlayerOneTurn) { //Si es nuevamente el turno de la computadora y no es el turno del jugador 1
                        if (board.randomizeElection()) { // Entonces el tablero va a tener una ficha en una posición random
                            playerTwoTurnCount--;
                            isPlayerOneTurn = !isPlayerOneTurn;
                            System.out.println("COMPUTER MADE A MOVE.... CHECK THE BOARD"); //Manda un mensaje diciendo que chequee la jugada del computador
                        } else {
                            checkWinner(); //Se llama al metodo que chequea si hay algún ganador
                        }
                    } else {
                        try {
                            int x = messageCenter.getInt("Please type in the X position for your Chip"); //Pide una coordenada X para la ficha
                            int y = messageCenter.getInt("Please type in the Y position for your Chip"); // Pide una coordenada Y para la ficha

                            if (x >= 0 && x <= 7 && y >= 0 && y <= 7) { // Verifica que las fichas esten dentro de los limites establecidos
                                if (board.findNode(x, y).getElement().getChip() != null) {
                                    messageCenter.showMessage("There is a Chip already on those coordinates."); //Muestra un mensaje comunicando que las coordenadas con incorrectas
                                } else {
                                    if (isPlayerOneTurn) {
                                        chipColor = "White"; //Coloca una ficha blanca si es el turno del jugador uno
                                    } else {
                                        chipColor = "Black"; //Coloca una ficha negra si es el turno del jugador dos 
                                    }

                                    if (!board.placeChip(x, y, chipColor, false)) { // Muestra un mensaje dicienco que necesita comer al menos una ficha del oponente al realizar el movimiento
                                        messageCenter.showMessage("You have to at least turn 1 Chip from your enemy to be able to place your Chip.");
                                    } else {
                                        if (isPlayerOneTurn) {
                                            playerOneTurnCount--; //Reduce el turno del jugador uno
                                        } else {
                                            playerTwoTurnCount--; //Reduce el turno del jugador dos
                                        }
                                        isPlayerOneTurn = !isPlayerOneTurn;
                                    }

                                }

                            } else {
                                messageCenter.showMessage("The coordinates you typed are out of bounds of the board.");
                            }

                            if (playerOneTurnCount == 0 || playerTwoTurnCount == 0) {
                                checkWinner(); // Si ya los dos jugadores van por 0 movimientos se chequea quién es el ganador 
                            }
                        } catch (Exception e) {
                            menuOption = -1;
                        }
                    }

                    break;

                case 2:
                    board.printBoard(); // Imprime el tablero
                    break;
                case 3:
                    isPlayerOneTurn = !isPlayerOneTurn; //Finaliza el turno
                    break;
                case 4:
                    if (cont == 0) {
                      messageCenter.showMessage("List is empty, there are no winners or ties");
                       break;
                    } 
                    System.out.print( "List of winners or ties" + "\n"+ "\n");
                    
                    for (int i = 0; i < nameWin.length; i++) {
                        
                        System.out.print( "  " + nameWin[ i ] + "\n");
                    }
                   
                    break;
                case 5:
                    exitGame = true; //Sale del juego
                    checkWinner(); //Chequea quién es el ganador
                    messageCenter.showMessage("Thanks for playing Othello. World funniest game.");
                    break;
                default:
                    messageCenter.showMessage("Please select a valid option.");
                    break;
            }
        } while (exitGame == false);
    }
}
