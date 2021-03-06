package mac.sample.dubbo.common.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import mac.sample.dubbo.common.bean.ReferenceBean;
import mac.sample.dubbo.common.bean.ServiceBean;
import mac.sample.dubbo.common.config.ApplicationConfig;
import mac.sample.dubbo.common.config.ProtocolConfig;
import mac.sample.dubbo.common.config.RegistryConfig;


/**
 * 简单实现dubbo基础注解(仅供学习参考使用)
 * 
 * 注册的dubbo服务的应用名称（作为监控使用）
 * <dubbo:appliaction name=""/>
 * 注册的dubbo服务器地址
 * <dubbo:registry address =""/>
 * 注册的dubbo 端口号
 * <dubbo:protocol name="" port=""/>
 * 注册的dubbo服务
 * <dubbo:service interface="" ref="" protocol ="" />
 * 
 * @author jihaibo
 *
 */
public class DubboNamespaceHandler extends NamespaceHandlerSupport{

	public void init() {
		

		registerBeanDefinitionParser("application", new DubboBeanDefinitionParser(ApplicationConfig.class));
		registerBeanDefinitionParser("registry", new DubboBeanDefinitionParser(RegistryConfig.class));
		registerBeanDefinitionParser("protocol", new DubboBeanDefinitionParser(ProtocolConfig.class));
        registerBeanDefinitionParser("service", new DubboBeanDefinitionParser(ServiceBean.class));
        registerBeanDefinitionParser("reference", new DubboBeanDefinitionParser(ReferenceBean.class));
        
	}

}
