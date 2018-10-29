package mac.sample.dubbo.common.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("unchecked")
public class DubboBeanDefinitionParserTest {
	
	private static ApplicationContext appContext;
	
	public static ApplicationConfig applicationConfig;
	
	public static RegistryConfig registryConfig;
	
	public static ServiceConfig<ApplicationConfig> serviceConfig;
	
	public static ProtocolConfig protocolConfig;
	
	private static ReferenceConfig referenceConfig;
	
	static {
		appContext = new ClassPathXmlApplicationContext("classpath*:/META-INF/spring-test.xml");
		applicationConfig = (ApplicationConfig) appContext.getBean("dubboSample");
		registryConfig = (RegistryConfig) appContext.getBean("macRegistry"); 
		serviceConfig = (ServiceConfig<ApplicationConfig>) appContext.getBean("mac.sample.dubbo.common.config.ApplicationConfig");
		protocolConfig = (ProtocolConfig) appContext.getBean("dubbo");
		referenceConfig = (ReferenceConfig) appContext.getBean("demoClient");
	}
	
	
	public static void main(String[] args) {
		
		
		System.out.println(applicationConfig.getId());
		System.out.println(registryConfig);
		System.out.println(protocolConfig);
		System.out.println(serviceConfig);
		System.out.println(referenceConfig);
	}

}
