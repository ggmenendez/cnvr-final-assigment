package es.upm.dit.cnvr.cs.tcp.server.pool;

/**
 * This class includes the information to interact
 * classes handling messages and receiving the clientes
 * @author Alejandro Alonso
 * @version v1.0 20170427
 */

public class HandlerInfo {

    private String message;
    private int sequence;

    /**
     * Provide the information
     * @param sequence The sequence of the message
     * @param message  The content of the message
     */
    public HandlerInfo(int sequence, String message) {
        this.sequence      = sequence;
        this.message       = message;
    }

    /**
     * Getter
     * @return the sequence
     */
    public int getSequence () {
        return sequence;
    }

    /**
     * Getter
     * @return the message
     */
    public String getMessage() {
        return message;
    }

}
