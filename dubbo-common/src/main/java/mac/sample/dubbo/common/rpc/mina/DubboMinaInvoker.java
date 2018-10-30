package mac.sample.dubbo.common.rpc.mina;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import mac.sample.dubbo.common.rpc.IDubboInvoker;
import mac.sample.dubbo.common.rpc.domain.RpcTransDTO;

/**
 * 
 * 客户端发送远程class，method信息，服务端返回远程调用的对象
 * 这里是使用mina的一个简单实现  ，很简陋，每次都会new对象，其实可以封装一下，懒得搞了
 * 
 * @author jihaibo
 *
 */
public class DubboMinaInvoker<T> implements IDubboInvoker<T>{
	/**
	 * 日志
	 */
	private final static Logger logger = Logger.getLogger(DubboMinaInvoker.class);
	

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public T invoker(final Class<T> invokerClass, final String host, final int port) {
		//返回一个动态代理的对象（参数的重点是new Class<?>[] { invokerClass }）
		//这个是告诉Proxy  需要代码哪个接口的实现
		//这里通过socket发送代理的className，method和参数去请求服务端，
		//服务端收到请求后，进行实例化本地的实现类调用方法，将执行返回值返回出去
        return (T) Proxy.newProxyInstance(invokerClass.getClassLoader(),
        		new Class<?>[] { invokerClass }, 
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						/*
				         * 1.创建一个socket连接,连接到服务器
				         */
						SocketConnector connector = new NioSocketConnector();
						connector.getFilterChain().addLast("logger", new LoggingFilter());
						connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
						connector.getSessionConfig().setUseReadOperation(true);
				        IoSession session = connector.connect(new InetSocketAddress(host, port)).awaitUninterruptibly().getSession();
				        try {
							//发送远程调用数据
							session.write(new RpcTransDTO("mac.sample.dubbo.demo.server.dubbo.DemoServer",method.getName(), method.getParameterTypes(),args)).awaitUninterruptibly();
							// 接收,并返回
							return session.read().getMessage();
							
				        }catch(Exception e) {
				        	logger.error("invoke fail ", e);
				        	
				        }finally {
				              // 断开
				              session.closeOnFlush();
				              session.getService().dispose();
				        }
				        return null;
					
					}
				});
		
	
	}	
	
}