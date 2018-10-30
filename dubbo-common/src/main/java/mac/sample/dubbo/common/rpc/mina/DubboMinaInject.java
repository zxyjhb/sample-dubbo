package mac.sample.dubbo.common.rpc.mina;


import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import mac.sample.dubbo.common.rpc.IDubboInject;
import mac.sample.dubbo.common.rpc.domain.RpcTransDTO;
import mac.sample.dubbo.common.rpc.utils.RpcUtils;

/**
 * 
 * @author jihaibo
 *
 * @param <T>
 */
public class DubboMinaInject<T> extends IoHandlerAdapter implements IDubboInject<T> {
	
	/**
	 * 日志
	 */
	private final static Logger logger = Logger.getLogger(DubboMinaInject.class);
	    
	public void inject(Class<T> injectClass, String host, int port) {
		
		logger.info("[DubboMinaInject] dubbo rpc inject: " + injectClass.getName() + ", host:" + host + ",port:" + port);
		IoAcceptor ioAcceptor=new NioSocketAcceptor();
		logger.info("begin server....");
        ioAcceptor.getFilterChain().addLast("logger", new LoggingFilter());
        ioAcceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        ioAcceptor.setHandler(new DubboInjectIoHandlerAdapter());
        ioAcceptor.getSessionConfig().setReadBufferSize(2048);
        ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        try {
            ioAcceptor.bind(new InetSocketAddress(host, port));
        } catch (Exception e) {
        	logger.error("inject fail ", e);
        }
	}  
	
	
	class DubboInjectIoHandlerAdapter extends IoHandlerAdapter{
		
		//当一个客户端连接进入时  
	    @Override  
	    public void sessionOpened(IoSession session)throws Exception {  
	    	logger.info("incoming client:"+session.getRemoteAddress());  
	    }  
	    //当客户端发送消息到达时  
	    @Override  
	    public void messageReceived(IoSession session, Object message)throws Exception {  
	    	RpcTransDTO trans = (RpcTransDTO) message;
	        logger.info("client send message is:"+ trans.toString()); 
	        Object result = RpcUtils.invoker(trans.getClassName(), trans.getMethodName(), trans.getParameterTypes(), trans.getArgs());
	        session.write(result);// 返回当前时间的字符串  
	        logger.info("message written...");  
	    }  
	    //当一个客户端连接关闭时  
	    @Override  
	    public void sessionClosed(IoSession session)throws Exception {  
	    	logger.info("one client closed");  
	    }

	}

}
