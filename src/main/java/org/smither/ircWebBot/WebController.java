package org.smither.ircWebBot;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WebController {

	private static Logger log = LoggerFactory.getLogger(WebController.class);

	private IrcController ircController;
	private String password;

	@Autowired
	public WebController(IrcController ircController, @Value("${password}") String password) {
		this.ircController = ircController;
		this.password = password;
	}

	@PostMapping("/sendMsg")
	public PostMsgReturn postMsg(@RequestBody PostMsgDTO dto) {
		if (!dto.getPassword().equals(password)) {
			return PostMsgReturn.builder().code(HttpResponseStatus.EXPECTATION_FAILED.code()).message("You are not " + "this").build();
		}
		Optional<Throwable> thrown = ircController.sendMessage(dto.getChannel(), dto.getMessage());
		if (thrown.isEmpty()) {
			return PostMsgReturn.builder().code(HttpResponseStatus.OK.code()).message("OK").build();
		}
		return PostMsgReturn.builder().code(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).message(thrown.get().getMessage()).build();
	}

	@PostMapping("/connectChannel")
	public PostChannelReturn postChannel(@RequestBody PostChannelDTO dto) {
		if (!dto.getPassword().equals(password)) {
			return PostChannelReturn.builder().code(HttpResponseStatus.EXPECTATION_FAILED.code()).message("You are not" +
				                                                                                              " " +
				                                                                                              "this").build();
		}
		Optional<Throwable> thrown = ircController.connectChannel(dto.getChannel(), dto.getKey());
		if (thrown.isEmpty()) {
			return PostChannelReturn.builder().code(HttpResponseStatus.OK.code()).message("OK").build();
		}
		return PostChannelReturn.builder().code(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).message(thrown.get().getMessage()).build();
	}
}
