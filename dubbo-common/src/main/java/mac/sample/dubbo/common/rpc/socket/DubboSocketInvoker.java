package mac.sample.dubbo.common.rpc.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

import mac.sample.dubbo.common.rpc.IDubboInvoker;

/**
 * 
 * 
 * 通过动态代理进行远程调用
 * 
 * 发送需要代理的class name/method /以及参数
 * 其实就是在常规的动态代理上，对于参数的传递采用socket的形式
 * 发送参数给socket server。服务端收到代理请求后，进行调用指定的类的方法，将方法的返回值，返回给客户端
 * 
 * 这里先不考虑代码的耦合以及设计规范了。实现socket再说
 * @author jihaibo
 *
 */
public class DubboSocketInvoker<T> implements IDubboInvoker<T>{

	/**
	 * 日志
	 */
	private final static Logger logger = Logger.getLogger(DubboSocketInvoker.class);
	
	/**
	 * 客户端调用socket类
	 */
	@SuppressWarnings("unchecked")
	public T invoker(final Class<T> invokerClass, final String host, final int port) {
		
		logger.info("[DubboSocketInvoker] dubbo rpc invoker: " + invokerClass.getName() + ", host:" + host + ",port:" + port);
		//返回一个动态代理的对象（参数的重点是new Class<?>[] { invokerClass }）
		//这个是告诉Proxy  需要代码哪个接口的实现
		//这里通过socket发送代理的className，method和参数去请求服务端，
		//服务端收到请求后，进行实例化本地的实现类调用方法，将执行返回值返回出去
		return (T) Proxy.newProxyInstance(invokerClass.getClassLoader(),
				new Class<?>[] { invokerClass }, new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						logger.info("[DubboSocketInvoker] dubbo rpc proxy: " + method.getName());
						Socket socket = null;
						ObjectOutputStream output = null;
						ObjectInputStream input = null;
						try {
							socket = new Socket();
							socket.connect(new InetSocketAddress(host,port));
							output = new ObjectOutputStream(socket.getOutputStream());
							//这里先写死，测试完了，看这个值怎么传入吧，可以在referenceBean初始化的时候通过对于的interface去找服务端的配置的ref的class
							//正常来说，dubbo使用zk的话会将服务的配置存入zk，这样由客户端通过zk可以找到对于的服务配置
							//也就包含了这个接口类的实现类（供远程调用使用）
							output.writeUTF("mac.sample.dubbo.demo.server.dubbo.DemoServer");
							output.writeUTF(method.getName());
							output.writeObject(method.getParameterTypes());
							output.writeObject(args);
							input = new ObjectInputStream(socket.getInputStream());
							return input.readObject();
						} finally {

							if (socket != null) {
								socket.close();
							}
							if (output != null) {
								output.close();
							}
							if (input != null) {
								input.close();
							}
						}
					}
				});
	}
}
