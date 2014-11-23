package server.net.packets;


public class Packet02Message extends Packet {
	
	public Packet02Message(String username, String message) {
		super(PacketType.MESSAGE, username, message);
		this.username = username;
		this.message = message;
		this.code = PacketType.MESSAGE.sayCode();
	}

	public String returnMessage() {
		return code + "," + time + "," + username + "," + message + "\n";
	}
	
}
