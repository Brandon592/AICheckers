
package checkersserver;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * CheckersServer allows connections from two clients.
 * 
 * @author alan.whitehurst
 */
public class CheckersServer {

    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static final int maxClientsCount = 2;
    private static final ClientThread[] threads = new ClientThread[maxClientsCount];

    public static void main(String args[]) {
        // The default port number.
        int portNumber = 2222;
        if (args.length < 1) {
            System.out.println("Usage: java CheckersServer <portNumber>\n"
                    + "Now using port number=" + portNumber);
        } else {
            portNumber = Integer.valueOf(args[0]).intValue();
        }

        /*
         * Open a server socket on the portNumber (default 2222). Note that we can
         * not choose a port less than 1023 if we are not privileged users (root).
         */
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        /*
         * Create a client socket for each connection and pass it to a new client
         * thread.
         */
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                while (i < maxClientsCount) {
                    if (threads[i] == null) {
                        threads[i] = new ClientThread(clientSocket, threads,i);
                        //threads[i].start();
                        break;
                    }
                    ++i;
                }
                if (i == 1){
                    threads[1].start();
                    threads[0].start();
                }
                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("FULL");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
