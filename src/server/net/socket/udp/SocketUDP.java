package server.net.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SocketUDP {

	private DatagramSocket socket;
	private byte[] sendData;
	private byte[] receiveData;
	
	private InetAddress ip;
	private int port;
	
	public SocketUDP(int port) throws SocketException {
		socket = new DatagramSocket(port);
	}
	
	
	public String receiveMessage(int dataLength) throws IOException {
		receiveData = new byte[dataLength];
		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, dataLength);
		
		socket.receive(receivePacket);
		
		if (ip == null) {
			ip = receivePacket.getAddress();
			port = receivePacket.getPort();
		}
		
		return new String(receivePacket.getData());
	}
	
	public void sendMessage(InetAddress ip, int port, String message, int dataLength) throws IOException {
		sendData = new byte[dataLength];
		sendData = message.getBytes();
		
		DatagramPacket packetSend = new DatagramPacket(sendData, sendData.length, ip, port);
		
		socket.send(packetSend);
	}
	
	public InetAddress getIp() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
	
	
	public void closeConnection() {
		socket.close();
		ip = null;
		port = 0;
	}
	
	
	
	
}
