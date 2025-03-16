package com.alesgaroth.zuv.protocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSender {
  InetAddress host;
  int port;
  public UDPSender(InetAddress host, int port) {
    this.host = host;
    this.port = port;
  }

  public void send(String text, DatagramSocket socket) throws IOException {
    byte [] data = text.getBytes();
    DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
    System.out.println("Sending to " + host + ":" + port);
    socket.send(packet);
  }
}
