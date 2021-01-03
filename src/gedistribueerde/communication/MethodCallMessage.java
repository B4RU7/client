package gedistribueerde.communication;

import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a message that is used to call a method of a component.
 * the message contains:
 * - the originator (sender) of the message
 * - the name of the method to be called
 * - parameters (only strings are supported)
 */
public final class MethodCallMessage {
    private final String methodName;
    private final Map parameters;
    private final NetworkAddress originator;

    /**
     * Constructs a new message.
     *
     * @param originator the sender of the message.
     * @param methodName the method to be called.
     */
    public MethodCallMessage(NetworkAddress originator, String methodName) {
        this.originator = originator;
        this.methodName = methodName;
        this.parameters = new TreeMap();
    }

    /**
     * Constructs a new message.
     *
     * @param originator the sender of the message
     * @param methodName the name of the method to be called
     * @param parameters the parameters of the method
     */
    public MethodCallMessage(NetworkAddress originator, String methodName, Map parameters) {
        this.originator = originator;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    /**
     * Returns the parameter-value associated with the given parameter-name.
     *
     * @param name the name of the parameter
     * @return the value of the parameter
     */
    public String getParameter(String name) {
        return (String) parameters.get(name);
    }

    public String getMethodName() {
        return methodName;
    }

    public Map getParameters() {
        return parameters;
    }

    public NetworkAddress getOriginator() {
        return originator;
    }

    /**
     * Associates a parameter-name with a parameter-value.
     *
     * @param name  the parameter-name
     * @param value the parameter-value
     */
    public void setParameter(String name, String value) {
        parameters.put(name, value);
    }

    public String toString() {
        return "Message: " + originator + ", " + methodName + ", " + parameters;
    }
}
