package gedistribueerde.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Creates a new network address and contains utilities to send and receive messages.
 *
 * @see NetworkAddress
 */
public final class MessageManager {
    private ServerSocket serverSocket;
    private NetworkAddress myAddress;

    /**
     * Constructs a new MessageManager object.
     * A server socket is created on a random port.
     */
    public MessageManager() {
        try {
            serverSocket = new ServerSocket(0);
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            int portNumber = serverSocket.getLocalPort();
            myAddress = new NetworkAddress(ipAddress, portNumber);
        } catch (UnknownHostException e) {
            System.err.println("Error finding hostname");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Failed to create server socket");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public NetworkAddress getMyAddress() {
        return myAddress;
    }

    /**
     * Receives one message.
     * If no message is available, then the current thread is blocked until a message is available.
     *
     * @return the received message.
     */
    public MethodCallMessage wReceive() {
        MethodCallMessage result = null;
        try {
            Socket client = serverSocket.accept();
            InputStream in = client.getInputStream();
            result = MessageReaderWriter.read(in);
            client.close();
        } catch (IOException e) {
            System.err.println("Failed to receive a message");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Sends a message.
     *
     * @param message the message to be sent.
     * @param address the destination address to which the message is sent.
     */
    public void send(MethodCallMessage message, NetworkAddress address) {
        try {
            Socket destination = new Socket(address.getIpAddress(), address.getPortNumber());
            OutputStream out = destination.getOutputStream();
            MessageReaderWriter.write(message, out);
            destination.close();
        } catch (IOException e) {
            System.err.println("Failed to send message");
            e.printStackTrace();
        }
    }
}
