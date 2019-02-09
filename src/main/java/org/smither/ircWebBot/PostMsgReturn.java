package org.smither.ircWebBot;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostMsgReturn {
	private int code;
	private String message;
}
