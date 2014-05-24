package com.wroblicky.andrew.joust.core.general;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A map that can have an arbitrary number of independent keys mapping to a single value
 * 
 * @author Andrew Wroblicky
 *
 */
@SuppressWarnings("rawtypes")
public class MultiKeyMap {
	
	private Map<String, Map<Object,Object>> mapOfMaps;
	private int length;
	
	public MultiKeyMap(Class... keys) {
		mapOfMaps = new LinkedHashMap<String, Map<Object,Object>>();
		length = keys.length;
		for (Class key : keys) {
			mapOfMaps.put(key.getCanonicalName(), new HashMap<Object, Object>());
		}
	}
	
	public void put(Object... objects) {
		if (objects.length > length) {
			throw new RuntimeException("too many objects");
		}
		
		Object value = objects[objects.length - 1];
		for (Object object : objects) {
			Map<Object, Object> map = mapOfMaps.get(object.getClass().getCanonicalName());
			map.put(object, value);
		}
	}
	
	public Object get(Object object) {
		Map<Object, Object> map = mapOfMaps.get(object.getClass().getCanonicalName());
		return map.get(object);
	}
	
	public void remove(Object... objects) {
		if (objects.length > length) {
			throw new RuntimeException("too many objects");
		}
		
		for (Object object : objects) {
			Map<Object, Object> map = mapOfMaps.get(object.getClass().getCanonicalName());
			map.remove(object);
		}
	}
}