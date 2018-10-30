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

/**
 * 
 * 
 * 备注一下：
 * bean的加载顺序
 * 
 * 
 * 一、Spring装配Bean的过程
 *1. 实例化;
 *2. 设置属性值;
 *3. 如果实现了BeanNameAware接口,调用setBeanName设置Bean的ID或者Name;
 *4. 如果实现BeanFactoryAware接口,调用setBeanFactory 设置BeanFactory;
 *5. 如果实现ApplicationContextAware,调用setApplicationContext设置ApplicationContext
 *6. 调用BeanPostProcessor的预先初始化方法;
 *7. 调用InitializingBean的afterPropertiesSet()方法;
 *8. 调用定制init-method方法；
 *9. 调用BeanPostProcessor的后初始化方法;
 *
 *二、Spring容器关闭过程
 *1. 调用DisposableBean的destroy();
 *2. 调用定制的destroy-method方法;
 *
 *
 *这个类是整个实现xml配置调用rpc（也就是sample dubbo）的核心
 *首先通过解析自定义的xml进行referenceBean配置（先在DubboBeanDefinitionParser里面配置referenceConfig）
 *这里通过实现FactoryBean接口，进行bean实例化的重写，bean实例化的是一个远程的代理对象
 *
 *这里是涉及一个bean的加载顺序，DubboBeanDefinitionParser进行bean加载的时候，由于ReferenceBean实现了FactoryBean，InitializingBean
 *所以会等其他的bean加载完了之后，返回FactoryBean指定的getObject()远程的代理对象
 *
 * @author jihaibo
 *
 */
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
