/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFrame;

/**
 *
 * @author alan.whitehurst
 */
public class CheckersClient extends JFrame{

    private Socket clientSocket = null;
    private PrintStream os = null;
    private BufferedReader is = null;
    //private BufferedReader inputLine = null;
    private boolean closed = false;
    private AIPlayer player = null;
    

    public CheckersClient(AIPlayer player, String[] args){
        this.player = player;
        int portNumber = 2222;
        String host = "localhost";
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(player);
        this.setSize(800, 820);
        this.setVisible(true);
        if (args.length < 2) {
            System.out
                    .println("Usage: java CheckersClient <host> <portNumber>\n"
                    + "Now using host=" + host + ", portNumber=" + portNumber);
        } else {
            host = args[0];
            portNumber = Integer.valueOf(args[1]).intValue();
        }
        try {
            clientSocket = new Socket(host, portNumber);
            //inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "
                    + host);
        }
    }

    public void play() {
        /*
         * Keep on reading from the socket till we receive "Bye" from the
         * server. Once we received that then we want to break.
         */
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                System.out.println(responseLine);
                if (responseLine.equals("BYE")) {
                    break;
                } else if(responseLine.equals("BLACK")){
                    player.setPlayerColor("BLACK");
                } else if(responseLine.equals("RED")){
                    player.setPlayerColor("RED");
                    os.println(player.getPlayerMove().trim());
                    player.repaint();
                } else {
                    try{
                    player.setOpponentMove(responseLine);
                    player.repaint();
                    os.println(player.getPlayerMove().trim());
                    player.repaint();
                    }catch (Exception ex){
                        System.err.println("Something went wrong");
                    }
                }
            }
            os.close();
            is.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
    
    public static void main(String[] args) {
        CheckersClient client = new CheckersClient(new AIPlayer(), args);
        client.play();
    }
}
