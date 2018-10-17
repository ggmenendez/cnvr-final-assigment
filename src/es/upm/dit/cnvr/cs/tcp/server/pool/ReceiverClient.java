package es.upm.dit.cnvr.cs.tcp.server.pool;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * This class sends the processed information to the client. All handlers provide
 * this information for the communciation,
 * @author Alejandro Alonso
 * @version v1.0 20170427
 */

public class ReceiverClient {

    private Socket connection;
    // The id of the server
    private int id;
    private DataOutputStream outToClient;

    /**
     * Constructor
     * @param id The identifier of the connection
     * @param connection The connection handle for sending a message to the client
     */
    public ReceiverClient(int id, Socket connection) {
        this.id        = id;
        this.connection = connection;
        try {
            outToClient = new DataOutputStream(connection.getOutputStream());
        } catch (Exception e) {
            System.out.println("Unexpected exception");
        }
    }

    /**
     * Invoked for sending a message to the client.
     * @param handlerInfo
     */
    public synchronized void send (HandlerInfo handlerInfo) {
        try {
            outToClient.writeBytes(handlerInfo.getMessage() + "\n");
            outToClient.flush();
            System.out.println("<<< Connection: " + id + " Sequence: " + handlerInfo.getSequence()
                    + " Sent: " + handlerInfo.getMessage());
        } catch (java.io.IOException e) {
            System.out.println("!!! Exception. Connection " + id + " Connection closed");
        } catch (Exception e) {
            System.out.println("!!! Unexpected exception");
        }
    }

}
