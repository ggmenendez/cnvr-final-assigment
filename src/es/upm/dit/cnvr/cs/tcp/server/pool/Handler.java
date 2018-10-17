package es.upm.dit.cnvr.cs.tcp.server.pool;

import java.util.Random;

/**
 * A handler (thread) is intended to process a message received from the client.
 * It waits from a received at a SyncPool. It invokes a ReceiverClient for sending
 * the message to the client.
 * @author Alejandro Alonso
 * @version v1.0 20170427
 */


public class Handler extends Thread{

    // The id of the server
    private int id;
    // The order of the message received from the client
    private ReceiverClient receiverClient;
    // The synchronized pool for the connection
    private SyncPool syncPool;

    /**
     * Constructor
     * @param id Identifier of the associated commection dispatcher
     * @param syncPool Object for syncronized handlers on a pool
     * @param receiverClient Object for sending the information to the client
     */
    public Handler(int id, SyncPool syncPool, ReceiverClient receiverClient) {
        this.id             = id;
        this.syncPool       = syncPool;
        this.receiverClient = receiverClient;
    }

    public void run() {

        HandlerInfo handlerInfo;
        Random random = new Random();
        int bound = 10;

        while (true) {
            // Simulate processing time for handling the message
            try {
                handlerInfo = syncPool.get();
                System.out.println(">>> Connection: " + id + " Sequence: " + handlerInfo.getSequence());
                Thread.sleep(random.nextInt(bound) * 1000);
                receiverClient.send(handlerInfo);
            } catch (InterruptedException e) {
                System.out.println("!!! Unexpected exception.");
                e.printStackTrace();
            }
        }
        //try {
        //    connection.close();
        //} catch (Exception e) {
        //    return;
        //}

    }

}
