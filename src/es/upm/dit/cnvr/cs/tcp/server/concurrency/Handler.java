package es.upm.dit.cnvr.cs.tcp.server.concurrency;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * Created by aalonso on 31/3/17.
 */

/**
 * A handler (thread) is intended to process a message received from the client.
 * The dispatcher provides the message by the constructor. The handler processes the message
 * and send the generated information to the client.
 * @author Alejandro Alonso
 * @version v1.0 20170427
 */
public class Handler extends Thread{

    // The id of the server
    private int id;
    // The order of the message received from the client
    private int sequence;
    private String message;
    private Socket connection;

    /** Constructor
     * @param id Identifier of the associated commection dispatcher
     * @param sequence Sequence of the received message from the client
     * @param message The message content
     * @param connection The connection for sending the processed information
     *                   to the client
     */
    public Handler(int id, int sequence, String message, Socket connection) {
        this.id         = id;
        this.sequence   = sequence;
        this.message    = message;
        this.connection = connection;
    }

    public void run() {
        Random random = new Random();
        int bound = 10;
        DataOutputStream outToClient;


        // Simulate processing time for handling the message
        try {
            System.out.println(">>> Connection: " + id + " Sequence: " + sequence);
            Thread.sleep(random.nextInt(bound) * 1000);

            outToClient = new DataOutputStream(connection.getOutputStream());
            outToClient.writeBytes(message + "\n");
            outToClient.flush();

            //counter.Add()
            System.out.println("<< Connection: " + id + " Sequence: " + sequence + " Sent: " + message);
        } catch (InterruptedException e) {

        } catch (java.io.IOException e) {
            System.out.println("!!! Exception. Socket " + id + " Connection closed");
        } catch (Exception e) {
            System.out.println("!!! Unexpected exception");
        }
        //try {
        //    connection.close();
        //} catch (Exception e) {
        //    return;
        //}

    }

}
