package gedistribueerde.server;


import gedistribueerde.client.Document;

public interface Server {
    void log(Document document);
    Document create(String s);
    void toUpper(@CallByRef Document document);
    void toLower(@CallByRef Document document);
    void type(@CallByRef Document document, String text);
}
