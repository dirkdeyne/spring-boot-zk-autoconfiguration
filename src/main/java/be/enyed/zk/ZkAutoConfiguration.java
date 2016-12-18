package be.enyed.zk;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ZkProperties.class)
public class ZkAutoConfiguration {
	
	private final ZkProperties zkProperties;
	
	public ZkAutoConfiguration(ZkProperties zkProperties) {
		this.zkProperties = zkProperties;
	}

	@Bean
	public static ServletRegistrationBean dHtmlLayoutServlet() {
		Map<String, String> params = new HashMap<>();
		params.put("update-uri", "/zkau");

		DHtmlLayoutServlet dHtmlLayoutServlet = new DHtmlLayoutServlet();
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dHtmlLayoutServlet, "*.zul","*.zhtml");
		servletRegistrationBean.setLoadOnStartup(1);
		servletRegistrationBean.setInitParameters(params);
		return servletRegistrationBean;
	}

	@Bean
	public static ServletRegistrationBean dHtmlUpdateServlet() {
		DHtmlUpdateServlet updateServlet = new DHtmlUpdateServlet();
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(updateServlet, "/zkau/*");
		return servletRegistrationBean;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ViewResolver viewResolver() {		
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix(zkProperties.getBaseFolder());		
		resolver.setSuffix(zkProperties.getExtension());
		return resolver;
	}
	
}
