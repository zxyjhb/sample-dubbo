package mac.sample.dubbo.common.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SuppressWarnings("rawtypes")
public class DubboApplicationContextInitializer implements ApplicationContextInitializer{

	public void initialize(ConfigurableApplicationContext applicationContext) {
		applicationContext.addApplicationListener(new DubboApplicationListener());
	}

}
