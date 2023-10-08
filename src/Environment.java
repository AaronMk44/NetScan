import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Environment {

    public static List<NetworkInterface> getAvailableNetwork(){
        List<NetworkInterface> availableNetworks = new ArrayList<>();

       try {
           Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
           while (networkInterfaces.hasMoreElements()) {
               NetworkInterface networkInterface = networkInterfaces.nextElement();

               // This filters available networks to only those that are active
               if(networkInterface.isUp() && !networkInterface.isLoopback()){
                   availableNetworks.add(networkInterface);
               }

           }
       }catch (Exception ex){
           ex.printStackTrace();
       }

        return availableNetworks;
    }

    public static NetworkScanReport scanNetwork(NetworkInterface network){

        List<NetworkDevice> possibleNetworkDevices = Environment
                .getPossibleNetworkDevicesOnNetwork(network);

        long start = System.currentTimeMillis();

        Object[] onlineDevices = scanForOnlineDevicesInParallel(possibleNetworkDevices);

        long end = System.currentTimeMillis();

        return new NetworkScanReport(onlineDevices, (end - start) );
    }

    private static List<NetworkDevice> getPossibleNetworkDevicesOnNetwork(NetworkInterface network){
        byte[] localHostIp = network.getInetAddresses().nextElement().getAddress();
        List<NetworkDevice> possibleNetworkDevices = new ArrayList<>();
        for (int i = 1; i < 255; i++) {
            // Assuming IPV4
            localHostIp[3] = (byte) i;
            InetAddress address = null;

            try {
                address = InetAddress.getByAddress(localHostIp);
                possibleNetworkDevices.add(new NetworkDevice(address.getHostAddress()));

            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }

        }
        return possibleNetworkDevices;
    }

    private static Object[] scanForOnlineDevicesInParallel(List<NetworkDevice> networkDevices) {

        return networkDevices
                .parallelStream()
                .filter(NetworkDevice::isOnline).toArray();
    }


}
