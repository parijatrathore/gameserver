package com.junglee.assignment.utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class JSONUtils {

	private static ObjectMapper mapper = new ObjectMapper();

	public static <T> T stringToJson(String json, Class<T> typeOfT) {
		try {
			return mapper.readValue(json, typeOfT);
		} catch (JsonParseException e) {
			throw new IllegalArgumentException("Invalid json " + json);
		} catch (JsonMappingException e) {
			throw new IllegalArgumentException("Invalid type " + typeOfT.getName());
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid type " + typeOfT.getName());
		}
	}

	public static Object stringToJson(String json, String type) {
		try {
			Class typeClass = Class.forName(type);
			return stringToJson(json, typeClass);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Invalid type " + type);
		}
	}

	/*public static Object stringToJson(String json) {
		return new Json
		
	}*/

	public static String objectToString(Object jsonObject) {
		try {
			return mapper.writeValueAsString(jsonObject);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Invalid json " + jsonObject);
		}
	}

	public static Map<String, String> jsonToMap(String json) {
		return stringToJson(json, Map.class);
	}
}
