/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersclient;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Brandon
 */
public class AIPlayer extends JPanel implements CheckersPlayer {

    public AIPlayer() {
        try {
            InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("redKing.png");
            REDKING = ImageIO.read(inStream);
            inStream = this.getClass().getClassLoader().getResourceAsStream("blackking.png");
            BLACKKING = ImageIO.read(inStream);
            inStream = this.getClass().getClassLoader().getResourceAsStream("red.png");
            RED = ImageIO.read(inStream);
            inStream = this.getClass().getClassLoader().getResourceAsStream("black.png");
            BLACK = ImageIO.read(inStream);
            inStream = this.getClass().getClassLoader().getResourceAsStream("board.jpg");
            BACKGROUND = ImageIO.read(inStream);
        } catch (Exception ex) {
            System.err.println("File Not Found");
        }
        this.setBounds(0, 0, 800, 800);
    }

    @Override
    public String getPlayerMove() {
        String retMove = "";
        Double alpha = -1000.0;
        Double beta = 1000.0;
        ArrayList<String> moves = board.getPossibleMoves(red);
        if (moves.isEmpty()){
            return "LOSE";
        }
        for (String move : moves) {
            CheckersBoard next = new CheckersBoard(board);
            next.move(move);
            if (red) {
                Double nextVal = minValue(next, alpha, beta, 5);
                if (alpha <= nextVal) {
                    alpha = nextVal;
                    retMove = move;
                }
            } else {
                Double nextVal = maxValue(next, alpha, beta, 5);
                if (beta >= nextVal) {
                    beta = nextVal;
                    retMove = move;
                }
            }
        }
        board.move(retMove);
        repaint();
        return retMove;
    }

    @Override
    public void setOpponentMove(String move) {
        if (move.equals("Lose") || move.equals("BYE")){
            System.out.println("You win!");
            System.exit(0);
        }else if (move.equals("DRAW")){
            System.out.println("It's a draw!");
            System.exit(0);
        }
        board.move(move);
        repaint();
    }

    @Override
    public void setPlayerColor(String name) {
        if (name.equalsIgnoreCase("RED")) {
            red = true;
        }
    }

    private double maxValue(CheckersBoard state, double alpha, double beta, int depth) {
        if (depth == 0) {
            return state.evaluate();
        }
        ArrayList<String> moves = state.getPossibleMoves(red);
        for (String move : moves) {
            CheckersBoard next = new CheckersBoard(state);
            next.move(move);
            alpha = Math.max(alpha, minValue(next, alpha, beta, depth - 1));
            if (alpha >= beta) {
                return alpha;
            }
        }
        return alpha;
    }

    private double minValue(CheckersBoard state, double alpha, double beta, int depth) {
        if (depth == 0) {
            return state.evaluate();
        }
        ArrayList<String> moves = state.getPossibleMoves(red);
        for (String move : moves) {
            CheckersBoard next = new CheckersBoard(state);
            next.move(move);
            beta = Math.min(beta, minValue(next, alpha, beta, depth - 1));
            if (beta <= alpha) {
                return beta;
            }
        }
        return beta;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BACKGROUND, 0, 0, null);
        int[][] currentSetup = board.getPiecePositions();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (currentSetup[i][j]) {
                    case -1:
                        g.drawImage(BLACK, 100 * j, 100 * i, null);
                        break;
                    case -2:
                        g.drawImage(BLACKKING, 100 * j, 100 * i, null);
                        break;
                    case 1:
                        g.drawImage(RED, 100 * j, 100 * i, null);
                        break;
                    case 2:
                        g.drawImage(REDKING, 100 * j, 100 * i, null);
                }
            }
        }
    }

    private boolean red = false;
    private CheckersBoard board = new CheckersBoard();
    JFrame frame = new JFrame("Checkers");
    private BufferedImage BACKGROUND;
    private BufferedImage REDKING;
    private BufferedImage BLACKKING;
    private BufferedImage RED;
    private BufferedImage BLACK;
}
