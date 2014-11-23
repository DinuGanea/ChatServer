package server.net.packets;

public enum PacketType {
	
	CONNECT("01"), DISCONNECT("00"), MESSAGE("02"), ERROR("99");
	
	private String code;
	
	private PacketType(String code) {
		this.code = code;
	}
	
	public String sayCode() {
		return code;
	}
	
}
