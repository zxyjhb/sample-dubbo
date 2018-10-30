package mac.sample.dubbo.common.rpc.utils;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class RpcUtils {
	

	/**
	 * 日志
	 */
	private final static Logger logger = Logger.getLogger(RpcUtils.class);
	
	
	public static Object invoker(final String className, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
		
		try {
			Class<?> service = Class.forName(className);
			Method method = service.getMethod(methodName, parameterTypes);
			return method.invoke(service.newInstance(), args);
			
		}catch(Exception e) {
			logger.error("invoke fail ", e);
		}
		return null;
		
	}

}
