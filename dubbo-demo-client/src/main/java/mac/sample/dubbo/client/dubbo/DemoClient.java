package mac.sample.dubbo.client.dubbo;

import org.apache.log4j.Logger;
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
		
		logger.info("receive: " + demoServer.getDemoDTO());
	}
	
	public void demo() {
		
		logger.info("receive: " + demoServer.demo());
	}
}
