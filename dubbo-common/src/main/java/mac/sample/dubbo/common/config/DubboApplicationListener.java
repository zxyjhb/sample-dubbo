package mac.sample.dubbo.common.config;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
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
public class DubboApplicationListener implements ApplicationListener<ApplicationEvent> {

	private DubboBootstrap dubboBootstrap;
	
	public DubboApplicationListener() {
        dubboBootstrap = new DubboBootstrap();
    }

	public void onApplicationEvent(ApplicationEvent event) {
		
		if (event instanceof ContextRefreshedEvent) {
            dubboBootstrap.start();
        } else if (event instanceof ContextClosedEvent) {
            dubboBootstrap.stop();
        }
		
	}

}
