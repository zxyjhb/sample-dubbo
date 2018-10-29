package mac.sample.dubbo.common.config;

import mac.sample.dubbo.common.rpc.IDubboInvoker;
import mac.sample.dubbo.common.rpc.DubboRpcFactory;

/**
 * clent 映射调用
 * @author jihaibo
 *
 */
public class ReferenceConfig<T> extends AbstractConfig{

	/**
	 * 版本
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 接口class
	 */
    private Class<T> interfaceClass;
	/**
	 * 
	 */
	private String interfaceName;
	
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
	 * rpc代理之后返回对象
	 */
	private T ref;
	/**
	 * rpc反射类
	 */
	@SuppressWarnings("unchecked")
	private IDubboInvoker<T> invoker = DubboRpcFactory.getInvoker();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	@SuppressWarnings("unchecked")
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public RegistryConfig getRegistryConfig() {
		return registryConfig;
	}

	public void setRegistryConfig(RegistryConfig registryConfig) {
		this.registryConfig = registryConfig;
	}

	public ProtocolConfig getProtocolConfig() {
		return protocolConfig;
	}

	public void setProtocolConfig(ProtocolConfig protocolConfig) {
		this.protocolConfig = protocolConfig;
	}

	/**
	 * 简单一点的get方法
	 * @return
	 */
	public T get() {
		
		if(ref == null) {
			ref = invoker.invoker(interfaceClass, protocolConfig.getHost(),protocolConfig.getPort());
		}
		return ref;
	}

	public void setRef(T ref) {
		this.ref = ref;
	}

	public Class<T> getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	@Override
	public String toString() {
		return "ReferenceConfig [name=" + name + ", interfaceClass=" + interfaceClass + ", interfaceName="
				+ interfaceName + ", registryConfig=" + registryConfig + ", protocolConfig=" + protocolConfig
				+ ", applicationConfig=" + applicationConfig + ", ref=" + ref + ", invoker=" + invoker + "]";
	}
}
