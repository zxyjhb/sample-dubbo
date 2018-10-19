package mac.sample.dubbo.common.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboBeanDefinitionParserTest {
	
	
	public static ApplicationConfig applicationConfig;
	
	public static RegistryConfig registryConfig;
	
	static {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath*:/META-INF/spring-test.xml");
		applicationConfig = (ApplicationConfig) appContext.getBean("dubboSample");
		registryConfig = (RegistryConfig) appContext.getBean("macRegistry");
	}
	
	
	public static void main(String[] args) {
		
		
		System.out.println(applicationConfig.getId());
		System.out.println(registryConfig.getAddress());
	}

}
