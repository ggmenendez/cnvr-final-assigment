package es.upm.dit.cnvr.cs.tcp.server;

/**
 * Created by aalonso on 30/3/17.
 */

import java.net.ServerSocket;
import java.net.Socket;

/**
 * The main in the server. It accepts the socket and creates
 * a dispatcher for managing the messages from the client
 * @author Alejandro Alonso
 * @version v1.0 20170427
 */
public class TCPServer {

    public static void main(String[] args) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        Socket connectionSocket;

        int id = 0;

        //1. creating a server socket
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            try {
                //2. Wait for connection
                connectionSocket = welcomeSocket.accept();

                //3. Create a thread for handling the connection
                ConnectionDispatcher handler = new ConnectionDispatcher(connectionSocket, id);
                System.out.println("Get a socket connection");
                handler.start();
                id++;
            } catch (Exception e) {

            }
        }
        //welcomeSocket.close();
    }

}