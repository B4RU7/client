package gedistribueerde.communication;

/**
 * Represents an address for TCP/IP communication.
 * It contains an IP-address and a port-number.
 */
public final class NetworkAddress {
    private final String ipAddress;
    private final int portNumber;

    /**
     * Constructs a new NetworkAddress given the IP-address and the port-number.
     *
     * @param ipAddress  the IP-address in the form '123.456.789.123'.
     * @param portNumber the port-number.
     */
    public NetworkAddress(String ipAddress, int portNumber) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String toString() {
        return ipAddress + ":" + portNumber;
    }
}
