package mac.sample.dubbo.client.dubbo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 这里有一个坑纠结了半天。mark一下
 * 因为想将client也发布成web，但是client这个项目创建的时候，并不是maven web骨架的
 * 就转了一下project faces 加了web。这样呢，允许main的时候一直报错。（这个保错呢是一直没有拉到api的最新的jar）
 * 后来把项目还原了 ，去掉了web的faces  就好了
 * 这里也不晓得为啥，通过faces转化的web项目，无法拉到最新的api包。奇怪
 * 所以不知道究竟是maven打包的问题呢 ，还是因为我的骨架不对，无法拉取
 * 
 * 感觉后者的可能性比较大（不知道是否是因为转了web之后，项目的路径以及引入规则啥的变了）
 * 
 * 还原之后，问题解决
 * @author jihaibo
 *
 */
public class DemoClientMain {
	
	private static DemoClient demoClient;
	
	private static ApplicationContext appContext;
	
	static {
		appContext = new ClassPathXmlApplicationContext("classpath*:/META-INF/spring-dubbo.xml");
		demoClient = (DemoClient) appContext.getBean("demoClient");
	}
	
	public static void main(String[] args) {
		

		demoClient.getDemoDTO();
		demoClient.demo();
	}

}
