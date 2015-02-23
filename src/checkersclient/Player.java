/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lab Admin
 */
public class Player implements CheckersPlayer {
    
    public Player(){
        this("RED");
    }
    
    public Player(String name){
        this.name = name;
        console = new BufferedReader(new InputStreamReader(System.in));
    }
    

    @Override
    public String getPlayerMove() {
        System.out.println(">> ");
        String response;
        try {
            response = console.readLine();
        } catch (IOException ex) {
            response = "BYE";
        }
        return response;
    }

    @Override
    public void setOpponentMove(String move) {
        System.out.println(name + " received " + move);
        if(move.equals("LOSE") || move.equals("DRAW") || move.equals("BYE")) {
            System.exit(0);
        }
    }
    
    @Override
    public void setPlayerColor(String name) {
        this.name = name;
    }
    
    private String name;
    private BufferedReader console;
    
    
}
