package org.smither.ircWebBot;

import lombok.Data;

import java.util.Optional;

@Data
public class PostChannelDTO {
	private String channel;
	private Optional<String> key;
	private String password;
}
