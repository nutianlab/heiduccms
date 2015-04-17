package org.heiduc.api.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Title: <br>
 * Description: HTTP请求代理类<br>
 * Copyright: Copyright (c) 2011 <br>
 * Create DateTime: 2011-8-8 下午04:30:39 <br>
 * 
 * @author 谭小波
 */
public class HttpProxy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 连接超时
	 */
	private static int connectTimeOut = 5000;

	/**
	 * 读取数据超时
	 */
	private static int readTimeOut = 10000;

	/**
	 * 请求编码
	 */
	private static String requestEncoding = "UTF-8";

	/**
	 * <pre>
	 * 发送带参数的GET的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @param parameters
	 *            参数映射表
	 * @return HTTP响应的字符串
	 */
	public static String doGet(String reqUrl, Map parameters,
			String recvEncoding) {
		HttpURLConnection httpURLConnection = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator iter = parameters.entrySet().iterator(); iter
					.hasNext();) {
				Entry element = (Entry) iter.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(),
						requestEncoding));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}

			if(reqUrl.indexOf("?")>-1){
				reqUrl += "&"+params.toString(); 
			}else{
				reqUrl += "?"+params.toString(); 
			}
			
			System.out.println(params.toString());
			System.out.println(reqUrl);
			URL url = new URL(reqUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			//System.setProperty("sun.net.client.defaultConnectTimeout", String
			//		.valueOf(HttpRequestProxy.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
			//System.setProperty("sun.net.client.defaultReadTimeout", String
			//		.valueOf(HttpRequestProxy.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
			httpURLConnection.setConnectTimeout(5000);//（单位：毫秒）jdk
			// 1.5换成这个,连接超时
			httpURLConnection.setReadTimeout(5000);//（单位：毫秒）jdk 1.5换成这个,读操作超时

			InputStream in = httpURLConnection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				temp.append(tempLine);
				temp.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = temp.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			// logger.error("网络故障", e);
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}

		return responseContent;
	}

	/**
	 * <pre>
	 * 发送不带参数的GET的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @return HTTP响应的字符串
	 */
	public static String doGet(String reqUrl, String recvEncoding) {
		HttpURLConnection httpURLConnection = null;
		String responseContent = null;
		try {
			URL url = new URL(reqUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			//System.setProperty("sun.net.client.defaultConnectTimeout", String
			//		.valueOf(HttpRequestProxy.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
			//System.setProperty("sun.net.client.defaultReadTimeout", String
			//		.valueOf(HttpRequestProxy.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
			httpURLConnection.setConnectTimeout(5000);//（单位：毫秒）jdk
			// 1.5换成这个,连接超时
			httpURLConnection.setReadTimeout(5000);//（单位：毫秒）jdk 1.5换成这个,读操作超时
			InputStream in = httpURLConnection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				temp.append(tempLine);
				temp.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = temp.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			// logger.error("网络故障", e);
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}

		return responseContent;
	}

	/**
	 * <pre>
	 * 发送带参数的POST的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @param parameters
	 *            参数映射表
	 * @return HTTP响应的字符串
	 */
	public static String doPost(String reqUrl, Map parameters,
			String recvEncoding) {
		HttpURLConnection httpURLConnection = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator iter = parameters.entrySet().iterator(); iter
					.hasNext();) {
				Entry element = (Entry) iter.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(),
						requestEncoding));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(reqUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			System.setProperty("sun.net.client.defaultConnectTimeout", String
					.valueOf(connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
			System.setProperty("sun.net.client.defaultReadTimeout", String
					.valueOf(readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
			// httpURLConnection.setConnectTimeout(5000);//（单位：毫秒）jdk
			// 1.5换成这个,连接超时
			// httpURLConnection.setReadTimeout(5000);//（单位：毫秒）jdk 1.5换成这个,读操作超时
			httpURLConnection.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			httpURLConnection.getOutputStream().write(b, 0, b.length);
			httpURLConnection.getOutputStream().flush();
			httpURLConnection.getOutputStream().close();

			InputStream in = httpURLConnection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
	//		String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
		//		tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			// logger.error("网络故障", e);
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		return responseContent;
	}
	
	/**
	 * <pre>
	 * 发送带参数的POST的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @param parameters
	 *            参数映射表
	 * @return HTTP响应的字符串
	 */
	public static String doPost(String reqUrl,
			String recvEncoding) {
		HttpURLConnection httpURLConnection = null;
		String responseContent = null;
		try {
			//StringBuffer params = new StringBuffer();

			//if (params.length() > 0) {
			//	params = params.deleteCharAt(params.length() - 1);
			//}
			String params = "";
			if(reqUrl.indexOf("?") > -1){
				String[] requestParams = reqUrl.split("\\?");
				reqUrl = requestParams[0];
				params = requestParams[1];
			}
			
			URL url = new URL(reqUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			//System.setProperty("sun.net.client.defaultConnectTimeout", String
			//		.valueOf(HttpRequestProxy.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
			//System.setProperty("sun.net.client.defaultReadTimeout", String
			//		.valueOf(HttpRequestProxy.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
			// httpURLConnection.setConnectTimeout(5000);//（单位：毫秒）jdk
			// 1.5换成这个,连接超时
			httpURLConnection.setReadTimeout(5000);//（单位：毫秒）jdk 1.5换成这个,读操作超时
			httpURLConnection.setDoOutput(true);
			
			if(!"".equals(params)){
				byte[] b = params.toString().getBytes();
				httpURLConnection.getOutputStream().write(b, 0, b.length);
				httpURLConnection.getOutputStream().flush();
				httpURLConnection.getOutputStream().close();
			}

			InputStream in = httpURLConnection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
		//	String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
			//	tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			// logger.error("网络故障", e);
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		return responseContent;
	}
	
	public static String doPost(Map headers,String reqUrl,String recvEncoding) {
		HttpURLConnection httpURLConnection = null;
		String responseContent = null;
		try {
			
			
			String params = "";
			if(reqUrl.indexOf("?") > -1){
				String[] requestParams = reqUrl.split("\\?");
				reqUrl = requestParams[0];
				params = requestParams[1];
			}
			
			URL url = new URL(reqUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			
			for (Iterator iter = headers.entrySet().iterator(); iter
			.hasNext();) {
				Entry element = (Entry) iter.next();
				httpURLConnection.setRequestProperty(element.getKey().toString(), element.getValue().toString());
			}
			
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
			//httpURLConnection.setRequestProperty("contentType", "utf-8");
			//System.setProperty("sun.net.client.defaultConnectTimeout", String
			//		.valueOf(HttpRequestProxy.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
			//System.setProperty("sun.net.client.defaultReadTimeout", String
			//		.valueOf(HttpRequestProxy.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
			// httpURLConnection.setConnectTimeout(5000);//（单位：毫秒）jdk
			// 1.5换成这个,连接超时
			httpURLConnection.setReadTimeout(36000);//（单位：毫秒）jdk 1.5换成这个,读操作超时
			httpURLConnection.setDoOutput(true);
			
			if(!"".equals(params)){
				byte[] b = params.toString().getBytes("UTF-8");
				httpURLConnection.getOutputStream().write(b, 0, b.length);
				httpURLConnection.getOutputStream().flush();
				httpURLConnection.getOutputStream().close();
			}

			InputStream in = httpURLConnection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
		//	String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
			//	tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			try {
				System.out.println("状态码："+httpURLConnection.getResponseCode());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		return responseContent;
	}

	/**
	 * 上传文件
	 * @param reqUrl,地址
	 * @param parameters,普通参数
	 * @param files,文件类型参数
	 * @param recvEncoding
	 * @return
	 */
	public static String doUpload(String reqUrl, Map parameters,Map files,
			String recvEncoding){
		HttpURLConnection httpURLConnection = null;
		String responseContent = null;
		try {
			String BOUNDARY = "---------travelbookapi"; // 定义数据分隔线
			URL url = new URL(reqUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setUseCaches(false);
			
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			httpURLConnection.setRequestProperty("Charsert", requestEncoding);
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			OutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
			byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
			
			//
			for (Iterator iter = parameters.entrySet().iterator(); iter
					.hasNext();) {
				Entry element = (Entry) iter.next();
				StringBuffer sb = new StringBuffer();
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data;name=\""+element.getKey()+"\"\r\n");
				sb.append("\r\n");
				sb.append(URLEncoder.encode(element.getValue().toString(), requestEncoding));
				//sb.append(element.getValue().toString());
				sb.append("\r\n");
				
				byte[] data = sb.toString().getBytes();
				out.write(data);
				System.out.println(sb.toString());
			}
			
			//循环文件列表
			for (Iterator iter = files.entrySet().iterator(); iter
					.hasNext();) {
				Entry element = (Entry) iter.next();
				String fileName = element.getValue().toString();
				
				File file = new File(fileName);
				StringBuffer sb = new StringBuffer();
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data;name=\""+element.getKey()+"\";filename=\""+ file.getName() + "\"\r\n");
				sb.append("Content-Type:application/octet-stream\r\n\r\n");
				
				byte[] data = sb.toString().getBytes();
				out.write(data);
				DataInputStream in = new DataInputStream(new FileInputStream(file));
				int bytes = 0;
				byte[] bufferOut = new byte[1024];
				
				while ((bytes = in.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, bytes);
				}
				out.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个
				in.close();
				System.out.println(sb.toString()+"\r\n");
			}
			out.write(end_data);
			out.flush();
			out.close();
			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String line = null;
			
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
				sb.append(line);
			}
			reader.close();
			responseContent = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return responseContent;
	}
	
	
	
	/**
	 * @return 连接超时(毫秒)
	 */
	public static int getConnectTimeOut() {
		return connectTimeOut;
	}

	/**
	 * @return 读取数据超时(毫秒)
	 */
	public static int getReadTimeOut() {
		return readTimeOut;
	}

	/**
	 * @return 请求编码
	 */
	public static String getRequestEncoding() {
		return requestEncoding;
	}

	/**
	 * @param connectTimeOut
	 *            连接超时(毫秒)
	 */
	public static void setConnectTimeOut(int timeout) {
		connectTimeOut = timeout;
	}

	/**
	 * @param readTimeOut
	 *            读取数据超时(毫秒)
	 */
	public static void setReadTimeOut(int timeout) {
		readTimeOut = timeout;
	}

	/**
	 * @param requestEncoding
	 *            请求编码
	 */
	public static void setRequestEncoding(String encoding) {
		requestEncoding = encoding;
	}

}
