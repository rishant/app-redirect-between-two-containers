package com.example.springboot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@ConfigurationProperties
@RequestMapping("app1")
public class HomeController {

	@Value("${clients.app1}")
	private String app1;
	@Value("${clients.app2}")
	private String app2;

	//https://www.baeldung.com/spring-redirect-and-forward
		
	/*
	 * redirect will respond with a 302 and the new URL in the Location header; the
	 * browser/client will then make another request to the new URL
	 */
	
	/*
	 * forward happens entirely on a server side; the Servlet container forwards the
	 * same request to the target URL; the URL won't change in the browser
	 */
    
	@RequestMapping("homePage")
	public ModelAndView homePage(@RequestParam Map<String, Object> attributes) {
		System.out.println("I am in App1 Home method 'homePage'");
		
		System.out.println("App1 url: " + app1);
		System.out.println("App2 url: " + app2);

		ModelAndView mv = new ModelAndView();
		if(attributes.get("whereiam") != null) {
			mv.addObject("whereiam", attributes.get("whereiam") + " redirected to App1 Home");
		}else {
			mv.addObject("whereiam", "App1 Home");			
		}

		mv.setViewName("home-page");
		return mv;
	}
	
	@RequestMapping("homePage/redirectApp")
	public RedirectView homePageRedirectApp() {
		System.out.println("I am in App1 Home method 'homePageRedirectApp'");
		RedirectView rv = new RedirectView();
		Map<String, Object> map = new HashMap<>();
		map.put("whereiam", "App1 Home");
		rv.setAttributesMap(map);
		rv.setUrl(app2);
		return rv;
	}
	
	@RequestMapping("homePage/typeRedirectApp")
	public String homePageTypeRedirectApp() {
		System.out.println("I am in App1 Home method 'homePageTypeRedirectApp'");
		return "redirect:"+ app2 + "?whereiam=App1 Home";
	}
	
	@RequestMapping("homePage/typeForwardApp")
	public String homePageTypeForwardApp() {
		System.out.println("I am in App1 Home method 'homePageTypeForwardApp'");
		return "forward:"+ app2 + "?whereiam=App1 Home";
	}
	
	@RequestMapping("homePage/typeForwardAppSameContainer")
	public ModelAndView homePageTypeForwardAppSameContainer(@RequestParam Map<String, Object> attributes) {
		System.out.println("I am in App1 Home method 'homePageTypeForwardAppSameContainer'");

		ModelAndView mv = new ModelAndView();
		if(attributes.get("whereiam") != null) {
			mv.addObject("whereiam", attributes.get("whereiam") + " forward to App1 Home");
		}else {
			mv.addObject("whereiam", "App1 Home");			
		}

		mv.setViewName("home-page");
		return mv;
	}

}
