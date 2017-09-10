package com.rest.utils;

import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

	public static final String STATUS_KEY = "status";
	public static final String DESCRIPTION_KEY = "description";
	public static final String VALUE_OBJECT_KEY = "valueObject";

	public static Map<String, Object> getDefaultMap() {
		Map<String, Object> map = new HashMap<>();
		map.put(STATUS_KEY, null);
		map.put(DESCRIPTION_KEY, null);
		map.put(VALUE_OBJECT_KEY, null);
		return map;
	}

}
