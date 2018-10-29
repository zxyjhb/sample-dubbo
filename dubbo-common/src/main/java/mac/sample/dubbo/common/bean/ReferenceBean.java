package mac.sample.dubbo.common.bean;

import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import mac.sample.dubbo.common.config.ApplicationConfig;
import mac.sample.dubbo.common.config.ProtocolConfig;
import mac.sample.dubbo.common.config.ReferenceConfig;
import mac.sample.dubbo.common.config.RegistryConfig;

/**
 * 
 * @author jihaibo
 *
 */
@SuppressWarnings("rawtypes")
public class ReferenceBean extends ReferenceConfig implements FactoryBean, ApplicationContextAware, InitializingBean, DisposableBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(ReferenceBean.class);
	
	private transient ApplicationContext applicationContext;
	
	public void destroy() throws Exception {
		//这里destroy先不管吧，只是标配加了InitializingBean那就加一个DisposableBean罢了
	}

	/**
	 * 设置bean属性
	 */
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
		
		Map<String, ProtocolConfig> protocolConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProtocolConfig.class, false, false);
        //存在Protocol配置，只取第一个哈（也就是说配置了多个application也没有用）
		if(protocolConfigMap!=null & protocolConfigMap.size()>0) {
			for(ProtocolConfig protocolConfig : protocolConfigMap.values()) {
				setProtocolConfig(protocolConfig);
				break;
			}
        }
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
        
	}
	/**
	 * 设置bean的实例化对象
	 */
	public Object getObject() throws Exception {
		return this.get();
	}

	/**
	 * 设置对象类型
	 */
	public Class getObjectType() {
		return this.getInterfaceClass();
	}

	/**
	 * 设置是否单例
	 */
	public boolean isSingleton() {
		return true;
	}

}
