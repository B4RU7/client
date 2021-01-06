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
        log.setParameter("documentText",document.getText());
        messageManager.send(log,serverNetworkAddress);

        MethodCallMessage reply = messageManager.wReceive();
        if (!reply.getMethodName().equals("logReply")) {
            throw new RuntimeException("Received reply is not equal to 'logReply'! Instead received:  " + reply.getMethodName());
        } else System.out.println("Received reply with name: " + reply.getMethodName());
    }

    @Override
    public Document create(String s) {
        MethodCallMessage create = new MethodCallMessage(messageManager.getMyAddress(), "create");
        create.setParameter("s",s);
        messageManager.send(create,serverNetworkAddress);

        MethodCallMessage reply = messageManager.wReceive();
        if (!reply.getMethodName().equals("createReply")) {
            throw new RuntimeException("Received reply is not equal to 'createReply'! Instead received:  " + reply.getMethodName());
        } else {
            System.out.println("Received reply with name: " + reply.getMethodName());
           return new DocumentImpl(reply.getParameter("s"));
        }
    }

    @Override
    public void toUpper(Document document) {
        MethodCallMessage toUpper = new MethodCallMessage(messageManager.getMyAddress(),"toUpper");
     //   toUpper.setParameter("documentToUpperText", document.getText());
        toUpper.setParameter("documentSkeletonAddress", documentSkeletonAddress.toString());

        messageManager.send(toUpper,serverNetworkAddress);

        MethodCallMessage reply = messageManager.wReceive();
        if (!reply.getMethodName().equals("toUpperReply")) {
            throw new RuntimeException("Received reply is not equal to 'toUpperReply'! Instead received:  " + reply.getMethodName());
        } else {
            System.out.println("Received reply with name: " + reply.getMethodName());
        }
    }

    @Override
    public void toLower(Document document) {
        MethodCallMessage toLower = new MethodCallMessage(messageManager.getMyAddress(),"toLower");
     //   toUpper.setParameter("documentToUpperText", document.getText());
        toLower.setParameter("documentSkeletonAddress", documentSkeletonAddress.toString());

        messageManager.send(toLower,serverNetworkAddress);

        MethodCallMessage reply = messageManager.wReceive();
        if (!reply.getMethodName().equals("toLowerReply")) {
            throw new RuntimeException("Received reply is not equal to 'toLowerReply'! Instead received:  " + reply.getMethodName());
        } else {
            System.out.println("Received reply with name: " + reply.getMethodName());
        }
    }

    @Override
    public void type(Document document, String text) {
        MethodCallMessage append = new MethodCallMessage(messageManager.getMyAddress(),"type");
        append.setParameter("typeText",text);
        append.setParameter("documentSkeletonAddress", documentSkeletonAddress.toString());

        messageManager.send(append,serverNetworkAddress);

        MethodCallMessage reply = messageManager.wReceive();
        if (!reply.getMethodName().equals("typeReply")) {
            throw new RuntimeException("Received reply is not equal to 'typeReply'! Instead received:  " + reply.getMethodName());
        } else {
            System.out.println("Received reply with name: " + reply.getMethodName());
        }

    }
}
