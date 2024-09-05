package com.yedam.common;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/*
 * 컨트롤에서 파라미터를 등록하고 값을 등록하면 
 * 이후에 요청attribute에 전달하기 위한 객체.
 */
@Data
public class ModelVO {
	private Map<String, Object> model = new HashMap<>();

	public void addProp(String key, Object value) {
		model.put(key, value);
	}

	public Object getProp(String key) {
		return model.get(key);
	}
}
