package mac.sample.dubbo.common.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboBeanDefinitionParserTest {
	
	
	public static ApplicationConfig applicationConfig;
	
	public static RegistryConfig registryConfig;
	
	public static ServiceConfig serviceConfig;
	
	public static ProtocolConfig protocolConfig;
	
	static {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath*:/META-INF/spring-test.xml");
		applicationConfig = (ApplicationConfig) appContext.getBean("dubboSample");
		registryConfig = (RegistryConfig) appContext.getBean("macRegistry"); 
		serviceConfig = (ServiceConfig) appContext.getBean("mac.sample.dubbo.common.config.ApplicationConfig");
		protocolConfig = (ProtocolConfig) appContext.getBean("dubbo");
	}
	
	
	public static void main(String[] args) {
		
		
		System.out.println(applicationConfig.getId());
		System.out.println(registryConfig);

		System.out.println(protocolConfig);
		System.out.println(serviceConfig);
	}

}
