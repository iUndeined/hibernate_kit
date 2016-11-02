package me.cjd.kit;

import java.lang.reflect.ParameterizedType;

import org.apache.log4j.Logger;

public class ClassKit {
	
	private static final Logger log = Logger.getLogger(ClassKit.class);
	
	@SuppressWarnings("unchecked")
	public static final <T> Class<T> getPatternClass(Class<?> clazz, int index){
		try {
			return (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[index];
		} catch (IndexOutOfBoundsException e) {
			log.error("参数 index 输入不正确", e);
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			log.error("其它错误", e);
			throw new IllegalArgumentException(e);
		}
	}
	
}