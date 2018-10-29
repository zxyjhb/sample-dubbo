package mac.sample.dubbo.common.bean;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import mac.sample.dubbo.common.config.ApplicationConfig;
import mac.sample.dubbo.common.config.RegistryConfig;
import mac.sample.dubbo.common.config.ServiceConfig;

public class ServiceBean<T> extends ServiceConfig<T> implements InitializingBean, DisposableBean, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(ServiceBean.class);
	
	private transient ApplicationContext applicationContext;
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//发布
		inject();
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public void destroy() throws Exception {
		//这里destroy先不管吧，只是标配加了InitializingBean那就加一个DisposableBean罢了
	}
	
	public void afterPropertiesSet() throws Exception {

		logger.info("-----------------[ReferenceBean] start--------------------------");
		Map<String, ApplicationConfig> applicationConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ApplicationConfig.class, false, false);
        //存在application配置，只取第一个哈（也就是说配置了多个application也没有用）
		if(applicationConfigMap!=null & applicationConfigMap.size()>0) {
			for(ApplicationConfig applicationConfig : applicationConfigMap.values()) {
				setApplicationConfig(applicationConfig);
				break;
			}
        }
		
		Map<String, RegistryConfig> registryConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, RegistryConfig.class, false, false);
        //存在registry配置，只取第一个哈（也就是说配置了多个application也没有用）
		if(registryConfigMap!=null & registryConfigMap.size()>0) {
			for(RegistryConfig registryConfig : registryConfigMap.values()) {
				setRegistryConfig(registryConfig);
				break;
			}
        }
		this.toString();
	}
}
