package mac.sample.dubbo.client.dubbo;

import org.apache.log4j.Logger;

import mac.sample.dubbo.demo.domain.DemoDTO;
import mac.sample.dubbo.demo.server.IDemoServer;
/**
 * demo clent
 * @author jihaibo
 *
 */
public class DemoClient {
	
	/**
	 * 日志
	 */
	private final static Logger logger = Logger.getLogger(DemoClient.class);
	
	private IDemoServer demoServer;

	public IDemoServer getDemoServer() {
		return demoServer;
	}

	public void setDemoServer(IDemoServer demoServer) {
		this.demoServer = demoServer;
	}
	
	public void getDemoDTO() {
		DemoDTO dto = demoServer.getDemoDTO();
		logger.info("rev: " + dto.toString());
	}
	
	public void demo() {
		String demo = demoServer.demo();
		logger.info("rev: " + demo);
	}
}
