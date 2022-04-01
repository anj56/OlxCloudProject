package com.zensar.olx.db;

import java.util.HashMap;
import java.util.Map;

public class TokenStorage {

	private static Map<String, String> tokenCache;

	static 
	{
		tokenCache = new HashMap<>();
	}
//for storing token in server
	//both token key and token value 
	public static void storeToken(String key, String token) {
		tokenCache.put(key, token);
	}
	
//for removing token fpr server
	public static String removeToken(String key) {
		System.out.println(tokenCache.get(key));
		return tokenCache.remove(key);
	}
	
//this is check if token is present or not
	public static String getToken(String key) {
		return tokenCache.get(key);
	}

}
