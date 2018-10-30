package mac.sample.dubbo.initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import mac.sample.dubbo.common.config.ReferenceConfig;
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
	 * 上下文对象
	 */
    private transient ApplicationContext applicationContext;
    /**
     * 消费者列表
     */
	private transient List<ServiceConfig> serviceConfigList;	
	
	 /**
     * 生产者列表
     */
	private transient List<ReferenceConfig> referenceConfigList;	
	
	
	public DubboBootstrap() {
		
	}
	
	public DubboBootstrap(ApplicationContext applicationContext) {
	
		this.applicationContext = applicationContext;
		this.serviceConfigList = new ArrayList<ServiceConfig>(); 
		this.referenceConfigList = new ArrayList<ReferenceConfig>(); 
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
		
		Map<String, ReferenceConfig> referenceConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ReferenceConfig.class, false, false);
        //存在dubbo配置
		if(referenceConfigMap != null & referenceConfigMap.size()>0) {
        	for(ReferenceConfig referenceConfig : referenceConfigMap.values()) {
        		logger.info("[DubboBootstrap] config reference:" + referenceConfig);
        		referenceConfigList.add(referenceConfig);
        		registerBean(referenceConfig.getId(),referenceConfig.getInterfaceName());
        	}
        }
	}

	/**
	 * dubbo服务停止
	 */
	public void stop() {
		logger.info("-------------------------------[DubboBootstrap] stop-----------------------------------------");
	}
	
	
	
	private void registerBean(String beanName,String beanClassNmae) {
		try {
			//将applicationContext转换为ConfigurableApplicationContext
		    ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
		    // 获取bean工厂并转换为DefaultListableBeanFactory
		    DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
		    // 通过BeanDefinitionBuilder创建bean定义
		    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Class.forName(beanClassNmae));
		    // 注册bean
		    defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getRawBeanDefinition());
			
		}catch(Exception e) {

    		logger.error("[DubboBootstrap] registerBean beanName:" + beanName, e);
		}
		
	}
}
