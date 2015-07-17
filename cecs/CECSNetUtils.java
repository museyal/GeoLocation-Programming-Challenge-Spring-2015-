package cecs;

import java.util.Map;

public class CECSNetUtils {
	
	private CECSNetUtils() {}
	
	public static String mapToQueryString(Map<String, String> parameters) {
		
		String paramStr = "?";
		int i = 1;
		for (String j : parameters.keySet()){
			paramStr += j + "=" + parameters.get(j);
			if(i++ < parameters.size())
				paramStr += '&';
		}
		
		
		return formatted(paramStr);
	}

	private static String formatted(String paramStr) {
		paramStr = paramStr.replace('?', '&');
		String[] parts = paramStr.split("&");
		
		String str = "?";
		for(int i=parts.length-1;i>0;i--){
			str += parts[i] + "&";
		}
		str = str.substring(0, str.length()-1);
		return str;
	}

}
