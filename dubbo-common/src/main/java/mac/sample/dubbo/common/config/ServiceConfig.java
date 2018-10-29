package mac.sample.dubbo.common.config;

import mac.sample.dubbo.common.rpc.DubboRpcFactory;
import mac.sample.dubbo.common.rpc.IDubboInject;
import mac.sample.dubbo.common.rpc.IDubboInvoker;

/**
 * ServiceConfig
 * 
 * 
 * 就简单版本吧，单纯的映射一下
 * 
 *
 * @export
 */
public class ServiceConfig<T> extends AbstractConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 服务配置名称
	 */
	private String name;
	/**
	 * 注册地址配置
	 */
	private RegistryConfig registryConfig;
	/**
	 * 注册端口配置
	 */
	private ProtocolConfig protocolConfig;
	/**
	 * 应用配置（用于将应用发布到zk里面，就是做一个单应用的标记）
	 */
	private ApplicationConfig applicationConfig;
	
	/**
	 * 服务名称
	 */
	private String interfaceName;
	
    private Class<T> interfaceClass;
    
    private T ref; 
    /**
	 * rpc反射类
	 */
	@SuppressWarnings("unchecked")
	private IDubboInject<T> inject = DubboRpcFactory.getInject();
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RegistryConfig getRegistryConfig() {
		return registryConfig;
	}

	public void setRegistryConfig(RegistryConfig registryConfig) {
		this.registryConfig = registryConfig;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public T get() {
		return ref;
	}

	public void setRef(T ref) {
		this.ref = ref;
	}

	public ProtocolConfig getProtocolConfig() {
		return protocolConfig;
	}

	public void setProtocolConfig(ProtocolConfig protocolConfig) {
		this.protocolConfig = protocolConfig;
	}

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	@Override
	public String toString() {
		return "ServiceConfig [name=" + name + ", registryConfig=" + registryConfig + ", protocolConfig="
				+ protocolConfig + ", applicationConfig=" + applicationConfig + ", interfaceName=" + interfaceName
				+ ", interfaceClass=" + interfaceClass + ", ref=" + ref + "]";
	}

	/**
	 * 发布rpc服务
	 */
	public void inject() {
		inject.inject(this.interfaceClass, this.protocolConfig.getHost(), this.protocolConfig.getPort());
	}
    //methods  先不搞了

}