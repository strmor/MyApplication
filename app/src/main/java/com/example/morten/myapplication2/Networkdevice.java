package com.example.morten.myapplication2;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.List;

public class Networkdevice {
    private final NetworkInterface n;

    //private NetworkInterface n;


    public Networkdevice(NetworkInterface n) {

        this.n = n;
    }

    @Override
    public String toString() {

        try {
            String returnString = "";
            returnString += n.getDisplayName() + " Multicast=" + n.supportsMulticast() + " IsLoopback=" + n.isLoopback() +" IsUp=" + n.isUp() +" isVirtual=" + n.isVirtual();



            return returnString;

        } catch (SocketException e) {
            e.printStackTrace();
            return " ERR: " + e;
        }


    }

    public String toDetailedString() {

        try {
            String returnString = "";

            List<InterfaceAddress> interfaceAddresses = n.getInterfaceAddresses();

            if(interfaceAddresses.size() == 0)
                returnString = "No adresses registered";

            for (InterfaceAddress ia : interfaceAddresses)
            {

                InetAddress address = ia.getAddress();

                returnString += " Address=" + ia.getAddress().getAddress() + " HostAddress" +ia.getAddress().getHostAddress() +'\n';

                if(address instanceof Inet4Address)
                    returnString += " v4 address!";


                if(address instanceof Inet6Address)
                    returnString += " v6 address!";

                if(address.isLoopbackAddress())
                {
                    returnString += " (loopback)";
                }





            }



            return returnString;

        } catch (Exception e) {
            e.printStackTrace();
            return " ERR: " + e;
        }


    }
}
