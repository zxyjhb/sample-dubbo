package mac.sample.dubbo.demo.server.dubbo;

import mac.sample.dubbo.demo.domain.DemoDTO;
import mac.sample.dubbo.demo.server.IDemoServer;

/**
 * 
 * @author jihaibo
 *
 */
public class DemoServer implements IDemoServer{

	private final static DemoDTO demoDTO = new DemoDTO();
	
	static {
		
		demoDTO.setAge("28");
		demoDTO.setId("1001");
		demoDTO.setName("jihaibo");
	}
	
	
	public DemoDTO getDemoDTO() {
		
		return demoDTO;
	}
	
	
	

}
