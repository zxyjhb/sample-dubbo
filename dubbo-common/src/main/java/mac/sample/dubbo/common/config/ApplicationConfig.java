package mac.sample.dubbo.common.config;


/**
 * application配置，发布的应用名称
 * @author jihaibo
 *
 */
public class ApplicationConfig extends AbstractConfig {

    private static final long serialVersionUID = 5508512956753757169L;

    // application name
    private String name;

	public ApplicationConfig() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
    
    
}