package org.smither.ircWebBot;

import lombok.Data;

@Data
public class PostMsgDTO {
	private String password;
	private String channel;
	private String message;
}
