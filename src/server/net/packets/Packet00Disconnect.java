package server.net.packets;

public class Packet00Disconnect extends Packet {

	public Packet00Disconnect(String username) {
		super(PacketType.DISCONNECT, username, "");
		
		this.code = PacketType.DISCONNECT.sayCode();
		this.username = username;
	}
	
	public String returnMessage() {
		return code + "," + time + "," + username + "\n";
	}

}
