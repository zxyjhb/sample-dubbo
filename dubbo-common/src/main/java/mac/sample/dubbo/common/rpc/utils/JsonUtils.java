package mac.sample.dubbo.common.rpc.utils;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

	
	
	public static <T> T json2Object(String json, Class<T> clazz) {
		
		return JSONObject.parseObject(json, clazz);
	}
	
	
	public static <T> String object2Json(T object) {
		
		return JSONObject.toJSONString(object);
	}
	
}
