package mac.sample.dubbo.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * dubbo启动类
 * 该类会启动一个dubbo监听，进行dubbo bean的初始化
 * @author jihaibo
 *
 */
@SuppressWarnings("rawtypes")
public class DubboApplicationContextInitializer implements ApplicationContextInitializer{

	
	public void initialize(ConfigurableApplicationContext applicationContext) {
		
		applicationContext.addApplicationListener(new DubboApplicationListener());
	}

}
