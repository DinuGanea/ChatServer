package server.user;

import java.net.InetAddress;
import java.net.Socket;

public class User {
	
	private InetAddress ip;
	private int port;
	
	private Socket socket;
	
	private String name;
	
	public User(Socket socket, InetAddress ip, int port, String name) {
		this.socket = socket;
		this.ip = ip;
		this.port = port;
		this.name = name;
	}
	
	public Socket giveConnectionSocket() {
		return socket;
	}
	
	public String tellYourName(InetAddress ip) {
		return name;
	}
	
	public InetAddress tellYouIpAddress() {
		return ip;
	}
	
	public int tellYourConnectionPort() {
		return port;
	}
	
	public String tellYourName() {
		return name;
	}
	
}
