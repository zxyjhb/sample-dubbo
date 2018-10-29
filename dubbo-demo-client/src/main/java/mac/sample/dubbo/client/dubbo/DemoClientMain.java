package mac.sample.dubbo.client.dubbo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoClientMain {
	
	private static DemoClient demoClient;
	
	private static ApplicationContext appContext;
	
	static {
		appContext = new ClassPathXmlApplicationContext("classpath*:/META-INF/spring-dubbo.xml");
		demoClient = (DemoClient) appContext.getBean("demoClient");
	}
	
	public static void main(String[] args) {
		
		demoClient.getDemoDTO();
	}

}
