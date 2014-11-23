package server.net.packets;

public class Packet01Connect extends Packet {
	
	public Packet01Connect(String username) {
		super(PacketType.CONNECT, username, "");
		this.username = username;
		this.code = PacketType.CONNECT.sayCode();
	}

	public String returnMessage() {
		return code + "," + time + "," + username + "\n";
	}
	
}
