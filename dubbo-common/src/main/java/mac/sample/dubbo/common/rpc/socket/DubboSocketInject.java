package mac.sample.dubbo.common.rpc.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import mac.sample.dubbo.common.rpc.IDubboInject;

/**
 * 
 * 
 * sockct服务端，这个其实可以采用lienter的形式进行单独监听，作为一个socket的服务监听
 * @author jihaibo
 *
 * @param <T>
 */
public class DubboSocketInject<T> implements IDubboInject<T> {

	/**
	 * 日志
	 */
	private final static Logger logger = Logger.getLogger(DubboSocketInject.class);

	private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	/**
	 * socket实现服务注入，这里采用多线程去代理调用
	 */
	public void inject(Class<T> injectClass, String host, int port) {
		ServerSocket server = null;
		try{
			logger.info("[DubboSocketInject] dubbo rpc inject: " + injectClass.getName() + ", host:" + host + ",port:" + port);
			server = new ServerSocket();
			server.bind(new InetSocketAddress(host, port));
			while(true){
				executor.execute(new ServiceTask(server.accept()));
			}
		}catch(Exception e) {
			logger.error("service fail ", e);
		}
		finally{
			if(server != null) {
				try {
					server.close();
				} catch (IOException e) {
					logger.error("service server close fail", e);
				}
			}
		}
	}

	/**
	 * 
	 * 接收客户端发送的远程调用请求，实例化本地对象进行调用，返回调用结果给客户端
	 * 
	 * 简单实现一下
	 * @author jihaibo
	 *
	 */
	private class ServiceTask implements Runnable{
		
		Socket client = null;

		public ServiceTask(Socket client) {
		
			this.client = client;
		}
		public void run() {
			ObjectInputStream input = null;
			ObjectOutputStream output = null;
			try{
				input = new ObjectInputStream(client.getInputStream());
				String interfaceName = input.readUTF();
				logger.info("InjectName:" + interfaceName);
				Class<?> service = Class.forName(interfaceName);
				String methodName = input.readUTF();
				Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
				Object[] arguments = (Object[]) input.readObject();
				Method method = service.getMethod(methodName, parameterTypes);
				Object result = method.invoke(service.newInstance(), arguments);
				output = new ObjectOutputStream(client.getOutputStream());
				output.writeObject(result);
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				if(output != null){
					try{
						output.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
				if(input != null){
					try{
						input.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
				if(client != null){
					try{
						client.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}
			
			
		}
		
	}

}
