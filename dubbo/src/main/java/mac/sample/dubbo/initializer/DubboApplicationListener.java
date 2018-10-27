package mac.sample.dubbo.initializer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 
 * dubbo启动的spring监听
 * 跟spring一起跑喽
 * 
 * @author jihaibo
 *
 */
public class DubboApplicationListener implements ApplicationListener<ApplicationContextEvent> {

	private DubboBootstrap dubboBootstrap;
	
	public DubboApplicationListener() {
    }

	public void onApplicationEvent(ApplicationContextEvent event) {
		
		if (event instanceof ContextRefreshedEvent) {
			dubboBootstrap = new DubboBootstrap(event.getApplicationContext());
            dubboBootstrap.start();
        } else if (event instanceof ContextClosedEvent) {
            dubboBootstrap.stop();
        }
		
	}

}
