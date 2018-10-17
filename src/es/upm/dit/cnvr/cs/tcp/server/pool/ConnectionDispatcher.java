package es.upm.dit.cnvr.cs.tcp.server.pool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This class is intented to wait for messages from a connection and passing the information
 * to a handler. For this purpose, it invokes a SyncPool
 * The server creates an object ConnectionDispatcher when a socket is accepted and, then, a
 * connection is created
 * @author Alejandro Alonso
 * @version v1.0 20170427
 */
public class ConnectionDispatcher extends Thread {

    private Socket connection;
    private int id;
    private static final int  N_HEADERS_POOL = 5;
    private SyncPool syncPool;
    private ReceiverClient receiverClient;

    /**
     * Constructor
     * @param id The identifier of the connection
     * @param connection The connection handle for sending a message to the client
     */
    public ConnectionDispatcher(Socket connection, int id) {
        this.connection     = connection;
        this.id             = id;
        this.syncPool       = new SyncPool();
        this.receiverClient = new ReceiverClient(id, connection);
    }

    public void run () {

        BufferedReader inFromClient;
        String message;
        Handler handler;

        // Create handlers in a pool
        for (int i = 0; i < N_HEADERS_POOL; i++) {
            handler =  new Handler(id, syncPool, receiverClient);
            handler.start();
        }

        int sequence = 0;

        try {
            System.out.println("ConnHandler " + id + ": connection waiting messages.");
            inFromClient = new BufferedReader
                    (new InputStreamReader(connection.getInputStream()));

            while (true) {
                message = inFromClient.readLine();
                if (message == null) break;
                syncPool.put(new HandlerInfo(sequence, message));
                sequence ++;
            }
        }
        catch (NullPointerException e) {
            System.out.println("!!! Exception. Connection " + id + " Connection closed");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Connection " + id + ": connection closed");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
