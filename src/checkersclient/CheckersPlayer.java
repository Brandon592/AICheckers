/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersclient;

/**
 * Interface that defines the requirements for being a Checkers player.
 * The Syntax for moves is r:c-r:c[-r:c]*. A valid move always has at
 * least one from square and one to square.
 * If the move has more than one destination square (as in the case of a
 * compound jump), subsequent destinations can be specified separated by
 * a dash. 
 * 
 * @author alan.whitehurst
 */
public interface CheckersPlayer {
    /**
     * Get the next move of the player. The player should respond
     * with a move in the correct move syntax.
     * @return A move in the correct move syntax.
     * Other valid responses are: "LOSE","DRAW".
     */
    public String getPlayerMove();
    /**
     * Sets the next move of the opponent. 
     * @param move A move in the correct move syntax. 
     * Other valid values are: "LOSE","DRAW".
     */
    public void setOpponentMove(String move);
    /**
     * Sets the color of the player.
     * @param name The color of the player. 
     * This should be either "RED" or "BLACK".
     */
    public void setPlayerColor(String name);
    
}
