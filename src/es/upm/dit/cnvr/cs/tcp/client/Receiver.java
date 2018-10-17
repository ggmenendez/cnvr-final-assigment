package es.upm.dit.cnvr.cs.tcp.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Alejandro Alonso
 * @version v1.0 20170427
 */
public class Receiver extends Thread{

    private Socket clientSocket;
    private boolean finished = false;
    private boolean error    = false;
    private int nValue       = 0;

    public Receiver (Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void finish (boolean finished, boolean error, int nValue) {
        this.finished = finished;
        this.nValue   = nValue;
        this.error    = error;
    }

    public void run() {

        BufferedReader inFromServer;
        int nReceiver    = 0;

        while(!error && !(finished && nValue  <= nReceiver)) {
            try {
                // 1. Create the Stream for the imput
                inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message;
                // 2. Read from the connection
                message = inFromServer.readLine();
                if (message == null) break;
                System.out.println("             Client got: " + message);
                nReceiver ++;
            } catch (Exception e) {
                break;
            }
        }
        try {
            System.out.println("Socket closed.");
            clientSocket.close();
        } catch (Exception e) { // IOException
            System.out.println("Unexpected exception " + e);
        }
    }
}
