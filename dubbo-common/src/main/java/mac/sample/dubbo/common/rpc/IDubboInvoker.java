package mac.sample.dubbo.common.rpc;


/**
 * dubbo 反射类
 * 
 * 用户，客户端调用时，通过rpc反射服务端的发布类
 * @author jihaibo
 *
 * @param <T>
 */
public interface IDubboInvoker<T> {
	/**
	 * 映射远程类，到本地对象中
	 * @param invokerClass
	 * @param host
	 * @param port
	 * @return
	 */
	public T invoker(final Class<T> invokerClass, final String host, final int port);
}
