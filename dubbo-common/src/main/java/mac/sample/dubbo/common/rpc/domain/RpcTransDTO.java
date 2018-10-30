package mac.sample.dubbo.common.rpc.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 用于RPC数据传输
 * @author jihaibo
 *
 */
public class RpcTransDTO implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4118456682425648891L;
	/**
	 * 类名称
	 */
	private String className;
	/**
	 * 方法名称
	 */
	private String methodName;
	/**
	 * 参数类型
	 */
	private Class<?>[] parameterTypes;
	/**
	 * 参数值
	 */
	private Object[] args;
	
	public RpcTransDTO(String className, String methodName, Class<?>[] parameterTypes, Object[] args) {
		super();
		this.className = className;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.args = args;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	@Override
	public String toString() {
		return "RpcTransDTO [className=" + className + ", methodName=" + methodName + ", parameterTypes="
				+ Arrays.toString(parameterTypes) + ", args=" + Arrays.toString(args) + "]";
	}
	
}
