package mac.sample.dubbo.common.config;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * dubbo启动类
 * 
 * 服务端/客户端：服务端，发布一个dubbo，其实就是一个远程调用
 * @author jihaibo
 *
 */
public class DubboBootstrap {
	
	
	
	private final static Logger logger = Logger.getLogger(DubboBootstrap.class);
	
	private List<ServiceConfig> serviceConfigList;
	
	public DubboBootstrap registerServiceConfig(ServiceConfig serviceConfig) {
        serviceConfigList.add(serviceConfig);
        return this;
    }
	
	public void start() {
		logger.info("[DubboBootstrap] start");
	}

	public void stop() {
		
		logger.info("[DubboBootstrap] stop");
		
	}
}
