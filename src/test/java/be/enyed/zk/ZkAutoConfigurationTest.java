package be.enyed.zk;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.junit.After;
import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
/**
 * 
 * @author Dirk
 *
 */
public class ZkAutoConfigurationTest {
	
	private ConfigurableApplicationContext context;

	@After
	public void closeContext() {
		if (this.context != null) {
			this.context.close();
		}
	}
	
	@Test
	public void setupBasicAnnotatedApplication() throws Exception {
		load(BasicAnnotatedApplication.class);
		
		assertThat(getHelloView()).isEqualToIgnoringCase("/WEB-INF/pages//hello.zul");
	}
	
	@Test
	public void setupOverrideViewResolverApplication() throws Exception {
		load(OverrideViewResolverApplication.class);

		assertThat(getHelloView()).isEqualToIgnoringCase("/custom//hello.zul");
	}
	
	protected String getHelloView() throws Exception{
		return ((InternalResourceView)context.getBean(ViewResolver.class)
					  .resolveViewName("/hello", Locale.getDefault()))
				.getUrl();
	}
	
	protected void load(Class<?> config, String... environment) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(config);
		EnvironmentTestUtils.addEnvironment(ctx, environment);
		ctx.refresh();
		this.context = ctx;
	}

	
	
	@Configuration
	static class BasicAnnotatedApplication extends ZkAutoConfiguration {

		public BasicAnnotatedApplication() {
			super(new ZkProperties());
		}

	}
	
	@Configuration
	static class OverrideViewResolverApplication extends ZkAutoConfiguration {
		
		public OverrideViewResolverApplication() {
			super(new ZkProperties());
		}

		@Bean
		public ViewResolver viewResolver() {		
			InternalResourceViewResolver resolver = new InternalResourceViewResolver();
			resolver.setPrefix("/custom/");		
			resolver.setSuffix(".zul");
			return resolver;
		}
		
		
	}

}
