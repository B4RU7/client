package gedistribueerde.communication;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Reads and writes messages from and to streams.
 */
final class MessageReaderWriter {
    /**
     * Reads a message from an input-stream.
     *
     * @param in the input-stream from which to read the message.
     * @return the message contained in the input-stream.
     */
    public static MethodCallMessage read(InputStream in) {
        StreamTokenizer tokenizer =
                new StreamTokenizer(new BufferedReader(new InputStreamReader(in)));
        tokenizer.quoteChar('\"');
        try {
            checkHeader(tokenizer);
            return readMessage(tokenizer);
        } catch (IOException e) {
            System.err.println("Failed to read message from stream");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Writes a message to an output-stream.
     *
     * @param message the message to be written.
     * @param out     the output-stream to which the message should be written.
     */
    public static void write(MethodCallMessage message, OutputStream out) {
        PrintWriter writer = new PrintWriter(out, true);
        writeHeader(writer);
        writeMessage(message, writer);
    }

    /**
     * Checks if a stream begins with the right header.
     * The header is defined as the string 'MethodCallMessage'.
     *
     * @param tokenizer connected to the message.
     * @throws IOException is thrown when the stream cannot be read.
     */
    private static void checkHeader(StreamTokenizer tokenizer) throws IOException {
        int token = tokenizer.nextToken();
        if (token != StreamTokenizer.TT_WORD && token != '\"') {
            throw new IOException("stream does not contain a message");
        }
        if (!"MethodCallMessage".equals(tokenizer.sval)) {
            throw new IOException("stream does not contain a message");
        }
    }

    /**
     * Writes the header to a stream.
     * The header is defined as the string 'MethodCallMessage'.
     *
     * @param writer connected to the message.
     */
    private static void writeHeader(PrintWriter writer) {
        writer.println("MethodCallMessage");
    }

    /**
     * Reads a message from a stream, without the header.
     *
     * @param tokenizer connected to the message.
     * @return the message that was read.
     * @throws IOException is thrown when the stream cannot be read.
     */
    private static MethodCallMessage readMessage(StreamTokenizer tokenizer) throws IOException {
        NetworkAddress originator = readOriginator(tokenizer);
        String methodName = readMethodName(tokenizer);
        Map params = readParameters(tokenizer);
        return new MethodCallMessage(originator, methodName, params);
    }

    /**
     * Writes a message (without header) to a stream.
     *
     * @param message the message to be written.
     * @param writer  the stream.
     */
    private static void writeMessage(MethodCallMessage message, PrintWriter writer) {
        writeOriginator(message.getOriginator(), writer);
        writeMethodName(message.getMethodName(), writer);
        writeParameters(message.getParameters(), writer);
    }

    /**
     * Reads the address of the originator of a message from a stream.
     *
     * @param tokenizer connected to the stream.
     * @return the address of the originator of the message.
     * @throws IOException is thrown when the stream cannot be read.
     */
    private static NetworkAddress readOriginator(StreamTokenizer tokenizer) throws IOException {
        NameValuePair pair = readNameValuePair(tokenizer);
        if (!"originator.IP".equals(pair.getName())) {
            throw new IOException("stream does not contain the originator of the message");
        }
        String ipAddress = pair.getValue();
        pair = readNameValuePair(tokenizer);
        if (!"originator.port".equals(pair.getName())) {
            throw new IOException("stream does not contain the originator of the message");
        }
        int port = Integer.parseInt(pair.getValue());
        return new NetworkAddress(ipAddress, port);
    }

    /**
     * Writes the originator of a message to a stream.
     *
     * @param originator the originator of the message.
     * @param writer     the stream.
     */
    private static void writeOriginator(NetworkAddress originator, PrintWriter writer) {
        writer.println("originator.IP \"" + originator.getIpAddress() + "\"");
        writer.println("originator.port \"" + originator.getPortNumber() + "\"");
    }

    /**
     * Reads the name of the method to be called from a stream.
     *
     * @param tokenizer connected to the stream.
     * @return the name of the method.
     * @throws IOException is thrown when the stream cannot be read.
     */
    private static String readMethodName(StreamTokenizer tokenizer) throws IOException {
        NameValuePair pair = readNameValuePair(tokenizer);
        if (!"methodName".equals(pair.getName())) {
            throw new IOException("stream does not contain the methodName of the message");
        }
        return pair.getValue();
    }

    /**
     * Writes the name of the method to be called to a stream.
     *
     * @param methodName the name of the method.
     * @param writer     the stream.
     */
    private static void writeMethodName(String methodName, PrintWriter writer) {
        writer.println("methodName " + methodName);
    }

    /**
     * Reads the parameters that are passed with a procedure-call from a stream.
     *
     * @param tokenizer connected to the stream.
     * @return the parameters as (String parameterName, String parameterValue) values.
     * @throws IOException is thrown when the stream cannot be read.
     */
    private static Map readParameters(StreamTokenizer tokenizer) throws IOException {
        Map result = new TreeMap();
        int token = tokenizer.nextToken();
        while (token != StreamTokenizer.TT_EOF) {
            tokenizer.pushBack();
            NameValuePair pair = readNameValuePair(tokenizer);
            result.put(pair.getName(), pair.getValue());
            token = tokenizer.nextToken();
        }
        return result;
    }

    /**
     * Writes the parameters that are passed with a procedure-call to a stream.
     *
     * @param parameters the parameters as (String parameterName, String parameterValue) values.
     * @param writer     the stream.
     */
    private static void writeParameters(Map parameters, PrintWriter writer) {
        Set keys = parameters.keySet();
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            String name = (String) iterator.next();
            String value = (String) parameters.get(name);
            writer.println(name + " \"" + value + "\"");
        }
    }

    /**
     * Reads a name and a value from a stream.
     *
     * @param tokenizer connected to the stream.
     * @return the name and value read from the stream.
     * @throws IOException is thrown when the stream cannot be read.
     */
    private static NameValuePair readNameValuePair(StreamTokenizer tokenizer) throws IOException {
        int token = tokenizer.nextToken();
        checkThatTokenIsWord(token);
        String name = tokenizer.sval;
        token = tokenizer.nextToken();
        checkThatTokenIsWord(token);
        String value = tokenizer.sval;
        return new NameValuePair(name, value);
    }

    /**
     * Checks if a token is a word or string.
     *
     * @param token the token that was read from a StreamTokenizer.
     * @throws IOException is thrown when the stream cannot be read.
     * @see StreamTokenizer
     */
    private static void checkThatTokenIsWord(int token) throws IOException {
        if (token != StreamTokenizer.TT_WORD && token != '\"') {
            throw new IOException("word expected but " + token + " found");
        }
    }
}
