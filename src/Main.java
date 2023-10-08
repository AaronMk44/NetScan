import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        List<NetworkInterface> availableNetworks = Environment.getAvailableNetwork();

        System.out.println("Welcome to NetScan");
        System.out.println("Which Network would you like to scan?");
        System.out.println("");

        try{
            int count = 1;
            for (NetworkInterface networkInterface : availableNetworks) {

                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                InetAddress inetAddress = inetAddresses.nextElement();
                System.out.println(count++ + ". Interface: " + networkInterface.getName());
                System.out.println("Your IP Address: " + inetAddress.getHostAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("Pick a number: ");
        Scanner input = new Scanner(System.in);
        int selectedNetwork = 0;
        selectedNetwork = input.nextInt();

        System.out.println("");
        System.out.println("You've selected network " + availableNetworks.get(selectedNetwork-1).getName());
        System.out.println("Now proceeding to scan it! Please wait...");
        System.out.println("");

        Environment.scanNetwork(availableNetworks.get(selectedNetwork-1));
    }

}