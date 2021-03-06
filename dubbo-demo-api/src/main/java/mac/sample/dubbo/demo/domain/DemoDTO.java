package mac.sample.dubbo.demo.domain;

import java.io.Serializable;

/**
 * 
 * @author jihaibo
 *
 */
public class DemoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1827220658153383478L;

	private String id;
	
	private String name;
	
	private String age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "DemoDTO [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
	

}
