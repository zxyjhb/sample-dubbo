package mac.sample.dubbo.common.rpc;

/**
 * dubbo 服务注入接口
 * 
 * 用于将服务端的实现类，发布到rpc远程服务中
 * 
 * @author jihaibo
 *
 * @param <T>
 */
public interface IDubboInject<T> {
	/**
	 * 注入指定类，到服务中
	 * @param proxyClass
	 * @param host
	 * @param port
	 */
	public void inject(final Class<T> injectClass, final String host, final int port);

}
