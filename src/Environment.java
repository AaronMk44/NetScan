import java.io.IOException;
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

    public static void scanNetwork(NetworkInterface network){


        List<NetworkDevice> possibleNetworkDevices = Environment
                .getPossibleNetworkDeviceOnNetwork(network);

        long start = System.currentTimeMillis();

        Object[] onlineDevices = scanForOnlineDevicesInParallel(possibleNetworkDevices);

        long end = System.currentTimeMillis();

        if (onlineDevices.length > 0){
            System.out.println("Found the following devices online: "+onlineDevices.length);
            for (Object obj : onlineDevices) {
                System.out.println(obj);
            }
        }
        System.out.println("Elapsed: " + (end - start) + "milliseconds");
        System.out.println();
    }

    private static List<NetworkDevice> getPossibleNetworkDeviceOnNetwork(NetworkInterface network){
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
