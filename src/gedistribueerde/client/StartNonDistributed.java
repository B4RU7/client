package gedistribueerde.client;


import gedistribueerde.communication.NetworkAddress;
import gedistribueerde.server.Server;

public class StartNonDistributed {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.exit(1);
        }
        String serverIP = args[0];
        int serverPort = Integer.parseInt(args[1]);
        NetworkAddress serverAddress = new NetworkAddress(serverIP, serverPort);
        DocumentImpl document = new DocumentImpl();
        DocumentSkeleton documentSkeleton = new DocumentSkeleton(document);
        new Thread(documentSkeleton).start();

        Server serverStub = new ServerStub(serverAddress, documentSkeleton.getAddress());
        Client client = new Client(serverStub, document);
        client.run();
    }
}
