/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author alan.whitehurst
 */
public class ClientThread extends Thread {
    /*
     * The checkers client thread. This client thread opens the input and the output
     * streams for a particular client, ask the client's name, informs all the
     * clients connected to the server about the fact that a new client has joined
     * the game, and as long as it receive data, echos that data back to all
     * other clients. The thread broadcasts the incoming messages to all clients and
     * routes the private message to the particular client. When a client leaves the
     * chat room this thread informs all the clients about that and terminates.
     */

    private String clientName = null;
    private BufferedReader is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final ClientThread[] threads;
    private int maxClientsCount;
    private int idNum;
    private long moveTime = 0L;

    public ClientThread(Socket clientSocket, ClientThread[] threads, int idNum) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
        this.idNum = idNum;
    }

    @Override
    public void run() {

        try {
            /*
             * Create input and output streams for this client.
             */
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            String name;
            if (idNum % 2 == 0) {
                name = "RED";
            } else {
                name = "BLACK";
            }
            /* Welcome the new the client. */
            os.println(name);
            /* Start the conversation. */
            while (true) {
                String line = is.readLine();
                moveTime = System.currentTimeMillis();
                //System.out.println(idNum + " sent " + line);
                if (line == null) {
                    os.close();
                    clientSocket.close();
                    threads[idNum] = null;
                } else if (!line.isEmpty() && !line.equals("BYE")) {
                    int oppId = (idNum + 1) % 2;
                    if (threads[oppId].moveTime != 0) {
                        long diffTime = moveTime - threads[oppId].moveTime;
                        System.out.println(name + " took " + diffTime + " milliseconds.");
                        if(diffTime>1000){
                            System.out.println(name + " forfeited.");
                            threads[oppId].os.println("LOSE");
                            break;
                        }
                    }
                    //System.out.println("opponent is thread " + oppId);
                    synchronized (this) {
                        if (threads[oppId] != null && threads[oppId] != this) {
                            System.out.println(name + " sending: '" + line);
                            threads[oppId].os.println(line);
                            //break;
                        }
                    }
                }
                if (line.equals("WIN") || line.equals("LOSE")
                        || line.equals("DRAW") || line.equals("BYE")) {
                    System.out.println(name + " received a " + line);
                    threads[idNum] = null;
                    break;
                }

            }

            /*
             * Clean up. Set the current thread variable to null so that a new client
             * could be accepted by the server.
             */
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == this) {
                        os.close();
                        clientSocket.close();
                        threads[idNum] = null;
                    }
                }
            }
            /*
             * Close the output stream, close the input stream, close the socket.
             */
            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
