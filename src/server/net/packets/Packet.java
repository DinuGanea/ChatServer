package server.net.packets;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Packet {
	
	protected PacketType type;
	protected String username;
	protected String message;
	protected String code;
	protected String time;
	

	public Packet(PacketType type, String username, String message) {
		this.type = type;
		this.username = username;
		this.message = message;
		
		LocalDateTime date = LocalDateTime.now();
		this.time = date.format(DateTimeFormatter.ofPattern("dd-MM-YYYY hh:mm:ss"));
	}
	
	public abstract String returnMessage();		
}
