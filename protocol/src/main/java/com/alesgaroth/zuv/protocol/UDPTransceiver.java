package com.alesgaroth.zuv.protocol;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UDPTransceiver implements TextSender {
  DatagramSocket serverSocket = null;
  int port;
  Map<String, UDPSender> replicas = new HashMap<>();
  Replicator replicator;

  public UDPTransceiver(int listeningPort) {
    this.port = listeningPort;
  }

  public void setReplicator(Replicator r) {
    this.replicator = r;
  }

  public void send(String text) {
    try {
        bindIt();
      for(UDPSender s: replicas.values()) {
        s.send(text, serverSocket);
      }
    } catch (IOException ioe) {
      // packet was lost...
      System.err.println(" losing packet while sending " + ioe);
    }
  }
  public void addReplica(String replicaName, UDPSender sender){
    replicas.put(replicaName, sender);
  }
  
  void receiveIfYouCan() {
    try {
      System.err.println("Trying to receive from port " + port);
      bindIt();
      byte[] data = new byte [1024];
      DatagramPacket packet = new DatagramPacket(data, data.length);
      serverSocket.receive(packet);
      String msg = new String(packet.getData(), 0, packet.getLength());
      replicator.receive(msg);
    } catch (IOException ioe) {
      // packet was lost
      System.err.println(" losing packet while receiving " + ioe);
    }
  }

  void bindIt() throws IOException {
      if (serverSocket == null) {
        System.out.println("binding to port " + port);
        realBindIt();
      }
    }

  synchronized void realBindIt() throws IOException {
    serverSocket = new DatagramSocket(port);
  }
}
