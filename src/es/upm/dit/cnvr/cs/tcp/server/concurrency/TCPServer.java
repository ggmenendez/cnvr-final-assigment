package es.upm.dit.cnvr.cs.tcp.server.concurrency;

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

        Socket connectionSocket;
        int id = 0;

        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            try {
                connectionSocket = welcomeSocket.accept();

                ConnectionDispatcher conHandler = new ConnectionDispatcher(connectionSocket, id);
                System.out.println("Get a socket connection");
                conHandler.start();
                id++;
            } catch (Exception e) {
                System.out.println("Closed the socket");
            }
        }
        //welcomeSocket.close();
    }

}