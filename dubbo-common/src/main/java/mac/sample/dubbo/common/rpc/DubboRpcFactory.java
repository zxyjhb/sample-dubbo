package mac.sample.dubbo.common.rpc;

import mac.sample.dubbo.common.rpc.socket.DubboSocketInvoker;
import mac.sample.dubbo.common.rpc.mina.DubboMinaInject;
import mac.sample.dubbo.common.rpc.mina.DubboMinaInvoker;
import mac.sample.dubbo.common.rpc.socket.DubboSocketInject;

/**
 * 
 * 
 * dubbo rpc 工厂类
 * 用于获取invoker/inject实例对象
 * @author jihaibo
 *
 */
@SuppressWarnings("rawtypes")
public class DubboRpcFactory {
	
	/**
	 * 客户端发送代理调用
	 */
	private final static IDubboInvoker invoker = new DubboMinaInvoker();
	
	/**
	 * 服务端实现代理使用
	 */
	private final static IDubboInject inject = new DubboMinaInject();
	
	
	public static IDubboInvoker getInvoker() {
		return invoker;
		
	}
	
	public static IDubboInject getInject() {	
		return inject;
	}

}
