package gedistribueerde.client;

import gedistribueerde.communication.MessageManager;
import gedistribueerde.communication.MethodCallMessage;
import gedistribueerde.communication.NetworkAddress;

public class DocumentSkeleton implements Runnable{
    private final Document document;
    private final MessageManager messageManager;

    public DocumentSkeleton(Document document) {
        this.document = document;
        this.messageManager = new MessageManager();
        System.out.println("my address = " + messageManager.getMyAddress());
    }

    public NetworkAddress getAddress() {
        return messageManager.getMyAddress();
    }

    private void handleGetText(MethodCallMessage request){
        MethodCallMessage replyMessage = new MethodCallMessage(messageManager.getMyAddress(), "getTextReply");
        replyMessage.setParameter("textString", document.getText());
        messageManager.send(replyMessage, request.getOriginator());
    }

    private void handleSetText(MethodCallMessage request){
        document.setText(request.getParameter("text"));
        MethodCallMessage replyMessage = new MethodCallMessage(messageManager.getMyAddress(), "setTextReply");
        messageManager.send(replyMessage, request.getOriginator());
    }

    private void handleAppend(MethodCallMessage request){

    }

    private void handleSetChar(MethodCallMessage request){
        int position = Integer.parseInt(request.getParameter("position"));
        char c =  request.getParameter("c").charAt(0);
        document.setChar(position,c);
        MethodCallMessage replyMessage = new MethodCallMessage(messageManager.getMyAddress(),"setCharReply");
        messageManager.send(replyMessage, request.getOriginator());
    }

    private void handleRequest(MethodCallMessage request){
        String methodName = request.getMethodName();
        switch (methodName) {
            case "getText": handleGetText(request); break;
            case "setText": handleSetText(request); break;
            case "append":handleAppend(request); break;
            case "setChar":handleSetChar(request); break;
            default:
                System.out.println("Unknown request!");
        }
    }

    @Override
    public void run() {
        while (true) {
            MethodCallMessage request = messageManager.wReceive();
            handleRequest(request);
        }
    }
}
