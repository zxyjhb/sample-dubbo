package mac.sample.dubbo.common.config;

import org.apache.log4j.Logger;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;


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
	 */
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		
		logger.info("parse id : "+ element.getAttribute("id"));
		logger.info("parse name : "+ element.getAttribute("name"));
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
                if (ProtocolConfig.class.equals(beanClass)) {
                    generatedBeanName = "dubbo";
                } else {
                    generatedBeanName = element.getAttribute("interface");
                }
            }
            if (generatedBeanName == null || generatedBeanName.length() == 0) {
                generatedBeanName = beanClass.getName();
            }
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
            parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
            beanDefinition.getPropertyValues().addPropertyValue("id", id);
        }
        //dubbo注册的配置文件
        if (RegistryConfig.class.equals(beanClass)) {
        	BeanDefinition definition = parserContext.getRegistry().getBeanDefinition(id);             
            definition.getPropertyValues().addPropertyValue("address", element.getAttribute("address"));     
        }
        //dubbo端口配置
        if (ProtocolConfig.class.equals(beanClass)) {
            BeanDefinition definition = parserContext.getRegistry().getBeanDefinition(id);
            PropertyValue property = definition.getPropertyValues().getPropertyValue("protocol");
            if (property != null) {
                Object value = property.getValue();
                if (value instanceof ProtocolConfig && id.equals(((ProtocolConfig) value).getName())) {
                    definition.getPropertyValues().addPropertyValue("protocol", new RuntimeBeanReference(id));
                }
            }
            
        }
        	
        
		// TODO Auto-generated method stub
		return null;
	}

}
