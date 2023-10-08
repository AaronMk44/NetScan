import java.io.IOException;
import java.net.InetAddress;

public class NetworkDevice {
    private String hostIp;
    private String hostName;

    public NetworkDevice(String hostIp) {
        this.hostIp = hostIp;
    }

    public boolean isOnline() {
        try {
            InetAddress host = InetAddress.getByName(hostIp);
            System.out.println("Pinging " + hostIp);
            if (host.isReachable(500)) {
                hostName = host.getHostName();
                return true;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("IP: %s \t Name: %s", hostIp, hostName);
    }
}