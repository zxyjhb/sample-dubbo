package mac.sample.dubbo.common.rpc;

import mac.sample.dubbo.common.rpc.socket.DubboSocketInvoker;
import mac.sample.dubbo.common.rpc.socket.DubboSocketInject;

/**
 * 
 * @author jihaibo
 *
 */


@SuppressWarnings("rawtypes")
public class DubboRpcFactory {
	
	
	private final static IDubboInvoker invoker = new DubboSocketInvoker();
	
	
	private final static IDubboInject inject = new DubboSocketInject();
	
	
	public static IDubboInvoker getInvoker() {
		
		return invoker;
		
	}
	
	public static IDubboInject getInject() {
		
		return inject;
		
	}

}
