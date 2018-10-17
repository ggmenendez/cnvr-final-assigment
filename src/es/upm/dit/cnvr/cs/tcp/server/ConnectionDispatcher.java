package es.upm.dit.cnvr.cs.tcp.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;


/**
 * This class is intented to wait for messages from a connection and passing the information
 * to a handler.<
 * The server creates an object ConnectionDispatcher when a socket is accepted and, then, a
 * connection is created
 * @author Alejandro Alonso
 * @version v1.0 20170427
 */
public class ConnectionDispatcher extends Thread {

    private int msg;
    private String clientSentence;
    private String capitalizedSentence;
    private Socket connection;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private int id;

    public ConnectionDispatcher(Socket connection, int id) {
        this.connection = connection;
        this.id         = id;
    }

    public void run () {
        try {
            System.out.println("ConnHandler " + id + ": socket waiting messages.");

            // Creating input and output Streams
            inFromClient = new BufferedReader
                    (new InputStreamReader(connection.getInputStream()));
            outToClient = new DataOutputStream(connection.getOutputStream());
            while (true) {
                //msg = inFromClient.read();
                // Read from the connection
                clientSentence = inFromClient.readLine();
                //capitalizedSentence = clientSentence.toUpperCase() + '\n';
                if (clientSentence == null) break;
                System.out.println("Socket " + id + " got: " + clientSentence);
                // Write to the connection
                outToClient.writeBytes(clientSentence + "\n");
                outToClient.flush();
            }
        }
        catch (NullPointerException e) {
            System.out.println("Exception. Socket " + id + " Connection closed");
        }
        catch (Exception e) {
            System.out.println(e);
        }

        try {
            //id ++;
            System.out.println("Server " + id + ": connection closed");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
