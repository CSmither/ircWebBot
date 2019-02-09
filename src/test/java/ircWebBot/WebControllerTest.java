package ircWebBot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.smither.ircWebBot.IrcController;
import org.smither.ircWebBot.PostMsgDTO;
import org.smither.ircWebBot.PostMsgReturn;
import org.smither.ircWebBot.WebController;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebControllerTest {

	private static final String PASSWORD = "testPasswd";
	private WebController webController;

	@Mock
	private IrcController ircController;

	@Before
	public void setUp() throws Exception {
		webController = new WebController(ircController, PASSWORD);
	}

	@Test
	public void testSendMsg() {
		PostMsgDTO dto = new PostMsgDTO();
		dto.setPassword("testPasswd");
		dto.setChannel("#test");
		dto.setMessage("Test message");
		when(ircController.sendMessage(anyString(), anyString())).thenReturn(Optional.empty());
		PostMsgReturn returned = webController.postMsg(dto);
		assertEquals(200, returned.getCode());
	}
}
