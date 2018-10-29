package mac.sample.dubbo.common.rpc.netty;

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
 * @author jihaibo
 *
 * @param <T>
 */
public class DubboNettyInject<T> implements IDubboInject<T> {
	
	

	/**
	 * 日志
	 */
	private final static Logger logger = Logger.getLogger(DubboNettyInject.class);

	private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	

	public void inject(Class<T> proxyClass, String host, int port) {
		ServerSocket server = null;
		try{
			logger.info("[DubboNettyInject] dubbo rpc register: " + proxyClass.getName());
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
