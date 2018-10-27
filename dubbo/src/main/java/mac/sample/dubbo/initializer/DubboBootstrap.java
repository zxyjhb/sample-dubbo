package mac.sample.dubbo.initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import mac.sample.dubbo.common.config.ServiceConfig;

/**
 * 
 * 
 * dubbo启动类
 * 
 * 服务端/客户端：服务端，发布一个dubbo，其实就是一个远程调用
 * @author jihaibo
 *
 */
@SuppressWarnings("rawtypes")
public class DubboBootstrap {
	
	/**
	 * 日志
	 */
	private final static Logger logger = Logger.getLogger(DubboBootstrap.class);
	/**
	 * 上下文2对象
	 */
    private transient ApplicationContext applicationContext;
    /**
     * 服务列表
     */
	private transient List<ServiceConfig> serviceConfigList;	
	
	public DubboBootstrap() {
		
	}
	
	public DubboBootstrap(ApplicationContext applicationContext) {
	
		this.applicationContext = applicationContext;
		this.serviceConfigList = new ArrayList<ServiceConfig>(); 
	}
	/**
	 * 注册服务
	 * @param serviceConfig
	 * @return
	 */
	public DubboBootstrap registerServiceConfig(ServiceConfig serviceConfig) {
        serviceConfigList.add(serviceConfig);
        return this;
    }
	
	/**
	 * 监听开始，进行bean的注入
	 */
	public void start() {
		logger.info("-----------------[DubboBootstrap] start--------------------------");
		Map<String, ServiceConfig> serviceConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ServiceConfig.class, false, false);
        //存在dubbo配置
		if(serviceConfigMap!=null & serviceConfigMap.size()>0) {
        	for(ServiceConfig serviceConfig : serviceConfigMap.values()) {
        		logger.info("[DubboBootstrap] config service:" + serviceConfig);
        		serviceConfigList.add(serviceConfig);
        	}
        }
	}

	/**
	 * dubbo服务停止
	 */
	public void stop() {
		logger.info("-------------------------------[DubboBootstrap] stop-----------------------------------------");
	}
}
