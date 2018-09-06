package com.liupeiqing.spring.cloud.utils;


import com.liupeiqing.spring.cloud.constants.CommonConstant;
import com.liupeiqing.spring.cloud.exception.CheckedException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Web相关工具类
 * 
 * @author liuweijw
 */
public class WebUtils {
	/**
	 * 对String-Integer转换，如果转换异常则返回默认值
	 *
	 * @param intId
	 * @param defaultIntId
	 *            返回默认值
	 * @return Integer
	 */
	public static Integer parseStrToInteger(String intId, Integer defaultIntId) {
		if (StringUtils.isEmpty(intId)) { return defaultIntId; }
		try {
			return Integer.parseInt(intId.trim());
		} catch (Exception e) {
			return defaultIntId;
		}
	}

	/**
	 * 对String-Long转换，如果转换异常则返回默认值
	 *
	 * @param intId
	 * @param defaultIntId
	 *            返回默认值
	 * @return Integer
	 */
	public static Long parseStrToLong(String intId, Long defaultIntId) {
		if (StringUtils.isEmpty(intId)) { return defaultIntId; }
		try {
			return Long.parseLong(intId.trim());
		} catch (Exception e) {
			return defaultIntId;
		}
	}

	/**
	 * @param intId
	 * @return
	 */
	public static Double parseStrToDouble(String intId, Double defaultDoubleId) {
		if (StringUtils.isEmpty(intId)) { return new Double(0); }
		try {
			return Double.parseDouble(intId.trim());
		} catch (Exception e) {
			return new Double(0);
		}
	}

	public static String buildURLEncoder(String param) {
		if (StringHelper.isEmpty(param)) { return ""; }
		try {
			return URLEncoder.encode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String buildURLDecoder(String param) {
		try {
			return java.net.URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}


	/**
	 * 判断是否是微信浏览器发出的请求
	 * 
	 * @param userAgent
	 */
	public static boolean isWxRequest(String userAgent) {
		return userAgent.indexOf("micromessenger") > -1;
	}

	public static void handleWithResponse(HttpServletResponse response, String responseStr) {
		PrintWriter printWriter;
		try {
			response.setCharacterEncoding(CommonConstant.UTF8);
			response.setContentType(CommonConstant.CONTENT_TYPE);
			printWriter = response.getWriter();
			printWriter.append(responseStr);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CheckedException("Failed to response");
		}
	}

	public static String buildURLParams(String returnUrl, String filterKey) {
		if (StringHelper.isBlank(returnUrl)) return "";
		if (!returnUrl.contains("?")) return returnUrl;
		if (!returnUrl.contains("=")) return returnUrl.split("\\?")[0];
		if (!returnUrl.contains(filterKey + "=")) return returnUrl;
		String[] urls = returnUrl.split("\\?");
		String params[] = urls[1].split("&");
		StringBuilder returnUrlBuffer = new StringBuilder("");
		for (String param : params) {
			if (!param.contains("=")) continue;
			if (!param.startsWith(filterKey + "=")) returnUrlBuffer.append(param).append("&");
		}

		if (returnUrlBuffer.length() > 0) {
			returnUrlBuffer = returnUrlBuffer.deleteCharAt(returnUrlBuffer.length() - 1);
		}

		return urls[0] + "?" + returnUrlBuffer.toString();
	}

	public static String buildAppendURLParams(String returnUrl, String... params) {
		if (StringHelper.isBlank(returnUrl) || params.length == 0) return returnUrl;
		StringBuilder builder = new StringBuilder();
		builder.append(returnUrl).append((returnUrl.contains("?") ? "&" : "?"));
		for (String urlParam : params) {
			builder.append(urlParam).append("&");
		}
		if (builder.length() > 0) {
			builder = builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}

	public static String buildRequestBaseURL(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath();
	}

}
