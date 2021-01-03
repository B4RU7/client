package gedistribueerde.client;

import gedistribueerde.communication.MessageManager;
import gedistribueerde.communication.MethodCallMessage;
import gedistribueerde.communication.NetworkAddress;
import gedistribueerde.server.Server;

public class ServerStub implements Server {
    private final MessageManager messageManager;
    private final NetworkAddress serverNetworkAddress;
    private final NetworkAddress documentSkeletonAddress;

    public ServerStub(NetworkAddress serverNetworkAddress, NetworkAddress documentSkeletonAddress) {
        this.messageManager = new MessageManager();
        this.serverNetworkAddress = serverNetworkAddress;
        this.documentSkeletonAddress = documentSkeletonAddress;
    }

    @Override
    public void log(Document document) {
        MethodCallMessage log = new MethodCallMessage(messageManager.getMyAddress(),"log");
        log.setParameter("documentSkeletonAddress",documentSkeletonAddress.toString());
        messageManager.send(log,serverNetworkAddress);

        MethodCallMessage reply = messageManager.wReceive();
        if (!reply.getMethodName().equals("logReply")) {
            throw new RuntimeException("Received reply is not equal to 'logReply'! Instead received:  " + reply.getMethodName());
        } else System.out.println("Received reply with name: " + reply.getMethodName());
    }

    @Override
    public Document create(String s) {
        return null;
    }

    @Override
    public void toUpper(Document document) {

    }

    @Override
    public void toLower(Document document) {

    }

    @Override
    public void type(Document document, String text) {

    }
}
