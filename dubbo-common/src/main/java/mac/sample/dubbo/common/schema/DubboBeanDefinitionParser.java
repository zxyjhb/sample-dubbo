package mac.sample.dubbo.common.schema;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import mac.sample.dubbo.common.bean.ReferenceBean;
import mac.sample.dubbo.common.bean.ServiceBean;
import mac.sample.dubbo.common.config.ProtocolConfig;
import mac.sample.dubbo.common.config.RegistryConfig;
import mac.sample.dubbo.common.config.ServiceConfig;


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
public class DubboBeanDefinitionParser implements BeanDefinitionParser {
	
	private final static Logger logger = Logger.getLogger(DubboBeanDefinitionParser.class);
	
	private final Class<?> beanClass;
	
	public DubboBeanDefinitionParser(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	/**
	 * 自定义解析器
	 * 
	 * 这里单独处理referenceConfig吧
	 * 本来是在这里解析dubbo bean的时候，进行rpc调用之后返回对象给reference的bean定义
	 * 但是呢，处于代码布局的考虑，这里只是加载bean的解析，不用加入过多的rpc逻辑，因此就放到dubbobootstrap里面去加载
	 * 
	 */
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        //看看是否配置了ID（我只是想简单去实现一个自定义解析器，所以可以不用考虑ID的情况）
        String id = element.getAttribute("id");
        //没有ID的时候呢，那就按照name去找
        if(id == null || id.length() == 0) {
        	String generatedBeanName = element.getAttribute("name");
        	//name属性也没有呢。那我们就解析dubbo标签的
        	if (generatedBeanName == null || generatedBeanName.length() == 0) {
        		//如果是端口配置呢，默认bean  name就是dubbo
                if (ProtocolConfig.class.equals(beanClass)) {
                    generatedBeanName = "dubbo-protocol";
                } 
                //如果是ZK配置呢，默认bean  name就是dubbo
                else if (RegistryConfig.class.equals(beanClass)) {
                    generatedBeanName = "dubbo-registry";
                } 
                //服务配置呢，默认bean name  就是   interface  （后面有处理，如果有多个，那就顺序加1）
                else if (ServiceConfig.class.equals(beanClass)) {
                    generatedBeanName = element.getAttribute("interface");
                }
            }
        	//实在找不到呢，那就用类名称吧
            if (generatedBeanName == null || generatedBeanName.length() == 0) {
                generatedBeanName = beanClass.getName();
            }
            //重复ID，进行加一处理
        	id = generatedBeanName;
        	int counter = 2;
            while (parserContext.getRegistry().containsBeanDefinition(id)) {
                id = generatedBeanName + (counter++);
            }
        }
        
        //开始注册
        if (id != null && id.length() > 0) {
            if (parserContext.getRegistry().containsBeanDefinition(id)) {
                throw new IllegalStateException("Duplicate spring bean id " + id);
            }
            logger.info("[DubboBeanDefinitionParser parse] : bean registry id " + id);
            parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
            beanDefinition.getPropertyValues().addPropertyValue("id", id);
        }
        //dubbo注册的配置文件
        if (RegistryConfig.class.equals(beanClass)) {
        	BeanDefinition definition = parserContext.getRegistry().getBeanDefinition(id);             
            definition.getPropertyValues().addPropertyValue("address", element.getAttribute("address"));     
        }
        //dubbo端口配置（这里都不校验bean配置）
        if (ProtocolConfig.class.equals(beanClass)) {
            BeanDefinition definition = parserContext.getRegistry().getBeanDefinition(id);
            definition.getPropertyValues().addPropertyValue("host", element.getAttribute("host"));
            definition.getPropertyValues().addPropertyValue("port", element.getAttribute("port"));
        }
        //dubbo端口配置
        if (ServiceBean.class.equals(beanClass)) {
        	BeanDefinition definition = parserContext.getRegistry().getBeanDefinition(id);
        	definition.getPropertyValues().addPropertyValue("registryConfig", new RuntimeBeanReference(element.getAttribute("registry")));
        	definition.getPropertyValues().addPropertyValue("protocolConfig", new RuntimeBeanReference("dubbo-protocol"));
            definition.getPropertyValues().addPropertyValue("ref", new RuntimeBeanReference(element.getAttribute("ref")));
            definition.getPropertyValues().addPropertyValue("interfaceClass", element.getAttribute("interface"));
            definition.getPropertyValues().addPropertyValue("interfaceName", element.getAttribute("interface"));
        }
        
        //dubbo端口配置
        if (ReferenceBean.class.equals(beanClass)) {
        	BeanDefinition definition = parserContext.getRegistry().getBeanDefinition(id);
        	definition.getPropertyValues().addPropertyValue("interfaceName", element.getAttribute("interface"));
            definition.getPropertyValues().addPropertyValue("interfaceClass", element.getAttribute("interface"));
        }
		return beanDefinition;
	}

}
