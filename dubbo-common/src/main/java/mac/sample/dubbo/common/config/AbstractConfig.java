package mac.sample.dubbo.common.config;

import java.io.Serializable;
/**
 * 配置
 * @author jihaibo
 *
 */
public class AbstractConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	

}
