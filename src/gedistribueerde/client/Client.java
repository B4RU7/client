package gedistribueerde.client;


import gedistribueerde.server.Server;

public class Client {
    private final Server server;
    private final Document document;
    private final ClientFrame clientFrame;

    public Client(Server server, DocumentImpl document) {
        this.server = server;
        this.document = document;
        this.clientFrame = new ClientFrame(document);
        document.setTextListener(clientFrame);
    }

    public void run() {
        // stap 1
        server.log(new DocumentImpl("Dit is een testje"));

        /*// stap 2
        Document doc = server.create("Dit is nog een testje...");
        document.setText(doc.getText());
        server.log(document);

        // stap 3
        server.toUpper(document);
        server.log(document);
        server.toLower(document);
        server.log(document);

        // stap 4
        server.type(document, "Hello, world!");
        server.log(document);*/
    }
}
