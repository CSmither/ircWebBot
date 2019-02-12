package org.smither.ircWebBot;

import com.rollbar.notifier.Rollbar;
import com.rollbar.web.provider.RequestProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rollbar.notifier.config.ConfigBuilder.withAccessToken;

@Service
public class ExceptionController extends SimpleMappingExceptionResolver {

	private String accessToken = "f42e52144a9d4159815aeb48bb6ba363";

	private Rollbar rollbar;

	@Autowired
	private BuildProperties buildProperties;

	public ExceptionController() {
		setWarnLogCategory(ExceptionController.class.getName());
	}

	@Override
	public String buildLogMessage(Exception e, HttpServletRequest req) {
		System.out.println("Exception : " + e.toString());
		RequestProvider requestProvider = new RequestProvider.Builder().userIpHeaderName(req.getRemoteAddr()).build();
		rollbar =
			Rollbar.init(withAccessToken(accessToken).codeVersion(buildProperties.getVersion()).request(requestProvider).build());
		rollbar.error(e);
		return "MVC exception: " + e.getLocalizedMessage();
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest req, HttpServletResponse resp, Object handler,
	                                          Exception ex) {
		ModelAndView mav = super.doResolveException(req, resp, handler, ex);
		mav.addObject("url", req.getRequestURL());
		return mav;
	}

}
