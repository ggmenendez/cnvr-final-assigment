package es.upm.dit.cnvr.cs.tcp.server.pool;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is to synchronize the dispatcher with the handlers.
 * The dispacther invokes "put" when a message is received
 * A handler waits until a message is notified.
 * @author Alejandro Alonso
 * @version v1.0 20170427
 */

public class SyncPool {


    private List<HandlerInfo> queue = new ArrayList<HandlerInfo>();

    /**
     * Invoked when the dispatcher has received a message
     * @param handlerInfo The information associated to the message
     */
    public synchronized void put(HandlerInfo handlerInfo) {
        queue.add(handlerInfo);
        notifyAll();
    }

    /**
     * Invoked for getting a message information. It is potentially blocking
     * @return The information associated to the message
     */
    public synchronized HandlerInfo get() {
        try {
            while (queue.size() == 0)
                wait();
        } catch (Exception E){
            System.out.println("Unexpected exception");
        }
        return queue.remove(0);
    }
}
