package server;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.net.packets.Packet;
import server.net.packets.Packet01Connect;
import server.net.packets.Packet02Message;
import server.net.packets.Packet99Error;
import server.net.socket.MessagesTCP;
import server.user.User;
import server.util.YamlUtil;


public class Server {
	
	
	final static File CFG_FILE = new File("cfg/server.cfg");
	
	final static Logger log = LoggerFactory.getLogger(Server.class);
	
	private ArrayList<User> connectedUsers = new ArrayList<User>();
	
	private static Map<String, Object> cfgs;
	private ServerSocket serverSocket;
	private MessagesTCP msTCP;
	private Thread recieve;
	private Server server;
	private int port;
	

	private Packet packet;

	public Server() {
		log.info("Server started!");
		
		init();
		
		server = this;
	}
	
	private void receive() {
		Socket socket = null;
		log.info("Accepting connection socket!");
		while (true) {
			try {
				socket = serverSocket.accept();
				
				String text = msTCP.recieveMessage(socket);
				
				if (text != null) {
					parseMessage(text, socket);	
				}
			} catch (IOException e) {
				if (socket != null) {
					log.warn("Cannot accept client [{}:{}] connection request!", socket.getInetAddress(), socket.getPort());
					e.printStackTrace();
				} else {
					log.warn("Cannot accept client [unknown:unknown] connection request!");
				}
			}
		}
	}
	
	private void parseMessage(String message, Socket socket) throws IOException {
		String parts[] = message.split(",");
		
		if (parts.length < 2) {
			return;
		}
		
		String code = parts[0];
		String username = parts[1];
		String text = "";
		if (parts.length > 2) {
			 text = parts[2];
		}

		switch(code) {
			case "00" :
				removeUser(username, socket);
				break;
			case "01" :
				if (addUser(username, socket)) {
					packet = new Packet01Connect(username);
					sendMessage(packet);
				}
				break;
			case "02" :
				packet = new Packet02Message(username, text);
				sendMessage(packet);
				break;
		}
		

	}
	
	private boolean userExist(String username) {
		for (User user : connectedUsers) {
			if (user.tellYourName().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}
	
	
	private void removeUser(String username, Socket socket) {
		for (int i = 0; i < connectedUsers.size(); i++) {
			if (connectedUsers.get(i).tellYourName().equalsIgnoreCase(username)) {
				connectedUsers.remove(i);
				break;
			}
		}
	}
	
	private boolean addUser(String username, Socket socket) throws IOException {
		if (!userExist(username)) {
			User user = new User(socket, socket.getInetAddress(), socket.getPort(), username);
			connectedUsers.add(user);
			return true;
		} else {
			packet = new Packet99Error("Name: " + username + " already exist!", username);
			sendMessage(packet);
			return false;
		}
	}
	
	private void sendMessage(Packet packet) throws IOException {
		Socket socket = null;
		for (User user : connectedUsers) {
			socket = user.giveConnectionSocket();
			msTCP.sendMessage(socket, packet.returnMessage());
		}
	}
	
	private Map<String, Object> loadCfg(File cfgFile) {
		Map<String, Object> cfgs = new HashMap<String, Object>();
		try {
			cfgs = new YamlUtil(CFG_FILE).loadYaml();
		} catch (FileNotFoundException e) {
			log.error("Can't load cfg file! {}", e.getMessage());
			System.exit(0);
		}
		
		return cfgs; 
	}
	
	
	private void init() {
		try {
			cfgs = loadCfg(CFG_FILE);

			this.port = Integer.parseInt(cfgs.get("port") + ""); 
			
		} catch (NumberFormatException e) {
			log.error("Invalid server port value.");
			System.exit(2);
		}
		
		
		msTCP = new MessagesTCP();
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			log.error("Cannot start the server on port {}", this.port);
			System.exit(0);
		}
		
		recieve = new Thread("RecieveMessages") {
			public void run() {
				receive();
			}
		};
		
		recieve.start();			
	}

	
	
	public static void main(String args[]) {
		new Server();
	}
	

}
