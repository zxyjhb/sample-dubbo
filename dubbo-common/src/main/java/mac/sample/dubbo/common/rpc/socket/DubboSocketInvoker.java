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
 * @author jihaibo
 *
 */
public class DubboSocketInvoker<T> implements IDubboInvoker<T>{

	/**
	 * 日志
	 */
	private final static Logger logger = Logger.getLogger(DubboSocketInvoker.class);
	
	
	@SuppressWarnings("unchecked")
	public T invoker(final Class<T> invokerClass, final String host, final int port) {
		
		return (T) Proxy.newProxyInstance(invokerClass.getClassLoader(),
				invokerClass.getInterfaces(), new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Socket socket = null;
						ObjectOutputStream output = null;
						ObjectInputStream input = null;
						try {
							socket = new Socket();
							socket.connect(new InetSocketAddress(host,port));
							output = new ObjectOutputStream(socket.getOutputStream());
							output.writeUTF(invokerClass.getName());
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
