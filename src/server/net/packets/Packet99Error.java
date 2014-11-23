package server.net.packets;

public class Packet99Error extends Packet {

	
	public Packet99Error(String message, String username) {
		super(PacketType.ERROR, username, message);
		
		this.code = PacketType.ERROR.sayCode();
		this.message = message;
		this.username = username;
	}

	public String returnMessage() {
		return code + "," + time + "," + username + "," + message + "\n";
	}
	
}
