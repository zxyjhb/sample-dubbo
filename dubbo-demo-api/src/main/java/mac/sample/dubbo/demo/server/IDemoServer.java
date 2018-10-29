package mac.sample.dubbo.demo.server;

import mac.sample.dubbo.demo.domain.DemoDTO;

/**
 * dubbo 测试API
 * @author jihaibo
 *
 */
public interface IDemoServer {
	

	public String sample();
	
	public String demo();
	
	public DemoDTO getDemoDTO();

}
