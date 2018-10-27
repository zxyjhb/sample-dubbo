package mac.sample.dubbo.common.config;

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
	
	private String name;
	
	private RegistryConfig registryConfig;
	
	private String interfaceName;
	
    private Class<?> interfaceClass;
    
    private T ref;
    
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

	public void setInterfaceClass(Class<?> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public T getRef() {
		return ref;
	}

	public void setRef(T ref) {
		this.ref = ref;
	}

	@Override
	public String toString() {
		return "ServiceConfig [name=" + name + ", registryConfig=" + registryConfig + ", interfaceName=" + interfaceName
				+ ", interfaceClass=" + interfaceClass + ", ref=" + ref + "]";
	}
    
    //methods  先不搞了

}