package mac.sample.dubbo.common.config;

/**
 * RegistryConfig
 *
 * @export
 */
public class RegistryConfig extends AbstractConfig {

    private static final long serialVersionUID = 5508512956753757169L;
    // register center address
    private String address;
    
    
    public RegistryConfig() {
    	
    }
    
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "RegistryConfig [address=" + address + "]";
	}
    
    
    
    
    
}