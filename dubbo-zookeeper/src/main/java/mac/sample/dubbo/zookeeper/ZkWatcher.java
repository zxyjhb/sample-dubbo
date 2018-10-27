package mac.sample.dubbo.zookeeper;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZkWatcher implements Watcher{
	
	
	private final static Logger logger = Logger.getLogger(ZkWatcher.class);
	
	private ZooKeeper zk;
	
	
	private String hostPort;
	
	
	public ZkWatcher(String hostPort) {
		this.hostPort = hostPort;
	}
	
	public void connect() throws IOException {
		this.zk = new ZooKeeper(hostPort, 15000, this);
	}

	public void process(WatchedEvent event) {
		logger.info(event);
		
	}
	
	public static void main(String[] args) throws IOException {
		
		ZkWatcher zkw = new ZkWatcher("127.0.0.1:2181");
		zkw.connect();
		
		
	}
	
	

}
