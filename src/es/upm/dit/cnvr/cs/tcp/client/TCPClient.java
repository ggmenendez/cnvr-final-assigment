package es.upm.dit.cnvr.cs.tcp.client;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * @author Alejandro Alonso
 * @version v1.0 20170427
 */
public class TCPClient {

    public static void main(String[] args) { //throws Exception{

        Socket clientSocket;
        DataOutputStream outToServer;

        int nTimes      = 4;
        String hostname = "127.0.0.1";
        int port        = 6789;
        Random random = new Random();
        int bound       = 10;

        try {
            //1. creating a socket to connect to the server
            clientSocket = new Socket(hostname, port);
            //2. Create a stream for output information to the connection
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // 3. Create a thread for handling the imptuts from the connection
        Receiver receiver = new Receiver(clientSocket);
        receiver.start();

        try {

            for (int i = 0; i < nTimes; i++) {
                System.out.println("Sequence: " + i);
                // 4. Write information to the connection
                outToServer.writeBytes(i + "\n");
                outToServer.flush();

                Thread.sleep(random.nextInt(bound) * 1000);
            }
        } catch (Exception e) { // IOException
            receiver.finish(false, true, 0);
            e.printStackTrace();
        }

        receiver.finish(true, false, nTimes);

    }
}


