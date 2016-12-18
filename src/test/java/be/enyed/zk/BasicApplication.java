package be.enyed.zk;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ViewResolver;

@SpringBootApplication
@Controller
public class BasicApplication {
	
	@Autowired
	public ViewResolver resolver;
	

	@RequestMapping("/hello")
	public String hello() throws Exception{
		System.err.println(resolver.resolveViewName("hello", Locale.getDefault()));
		return "hello";
	}

}
