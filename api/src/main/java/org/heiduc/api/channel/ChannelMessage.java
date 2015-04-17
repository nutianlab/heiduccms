package org.heiduc.api.channel;

public class ChannelMessage {

	private final String clientId;
	private final String message;

	public ChannelMessage(String clientId, String message) {
		this.clientId = clientId;
		this.message = message;
	}

	public String getClientId() {
		return this.clientId;
	}

	public String getMessage() {
		return this.message;
	}

}
