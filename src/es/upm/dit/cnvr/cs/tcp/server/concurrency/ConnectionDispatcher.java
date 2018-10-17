package es.upm.dit.cnvr.cs.tcp.server.concurrency;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This class is intented to wait for messages from a connection and to create a handler, passing the information
 * The server creates an object ConnectionDispatcher when a socket is accepted and, then, a
 * connection is created
 * @author Alejandro Alonso
 * @version v1.0 20170427
 */
public class ConnectionDispatcher extends Thread {

    private String message;
    private Socket connection;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private int id;

    /**
     * Constructor
     * @param id The identifier of the connection
     * @param connection The connection handle for sending a message to the client
     */
    public ConnectionDispatcher(Socket connection, int id) {
        this.connection = connection;
        this.id         = id;
    }

    public void run () {

        Handler handler;
        int sequence = 0;

        try {
            System.out.println("ConnHandler " + id + ": socket waiting messages.");
            inFromClient = new BufferedReader
                    (new InputStreamReader(connection.getInputStream()));

            while (true) {
                message = inFromClient.readLine();
                if (message == null) break;

                handler = new Handler(id, sequence, message, connection);
                handler.start();
                sequence ++;
            }
        }
        catch (NullPointerException e) {
            System.out.println("Exception. Connection " + id + " Connection closed");
        }
        catch (Exception e) {
            System.out.println(e);
        }

        try {
            //id ++;
            System.out.println("Comnection " + id + ": connection closed");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
