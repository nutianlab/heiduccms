package org.heiduc.api.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: api请求代理类<br>
 * Copyright: Copyright (c) 2011 <br>
 * Create DateTime: 2011-8-8 下午04:30:39 <br>
 * 
 * @author 谭小波
 */
public class HttpProxy {
	
	protected static final Logger logger = LoggerFactory.getLogger(HttpProxy.class);

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
	public static String doGet(String reqUrl, Map<String,String> parameters,String recvEncoding) {
		String response = null;
		try {
			HttpClient client = new HttpClient();
			if(parameters != null){
				StringBuffer params = new StringBuffer();
				for (Map.Entry<String, String> entry : parameters.entrySet()) {
					params.append(entry.getKey().toString());
					params.append("=");
					params.append(URLEncoder.encode(entry.getValue()+"",HttpProxy.requestEncoding));
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
			}
			GetMethod method = new GetMethod(reqUrl);
			try {
				client.executeMethod(method);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				response = new String(method.getResponseBody(),recvEncoding);
				//打印返回的信息  
			    method.releaseConnection();  
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}  
		return response;
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
		return doGet(reqUrl,null,recvEncoding);
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
	public static byte[] doGet(String reqUrl) {
		return doGet(reqUrl,null,"UTF-8").getBytes();
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
	public static String doPost(String reqUrl, Map<String, String> parameters, String recvEncoding) {
		return doPost(reqUrl,null,parameters,recvEncoding);
	}
	
	/**
	 * <pre>
	 * 发送带参数的POST的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl HTTP请求URL
	 * @return HTTP响应的字符串
	 */
	public static String doPost(String reqUrl,String recvEncoding) {
		return doPost(reqUrl,null,null,recvEncoding);
	}
	
	public static String doPost(String reqUrl,Map<String, String> headers,Map<String, String> parameters,String recvEncoding) {
		
		String response = null;
		try {
			
			HttpClient client = new HttpClient();
			PostMethod  method = new PostMethod(reqUrl);
			
			if(headers != null){
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					method.addRequestHeader(new Header(entry.getKey(), entry.getValue()));
				}
			}
			if(parameters != null){
				NameValuePair[] nameValuePair = new NameValuePair[parameters.size()];
		        int i = 0;
		        for (Map.Entry<String, String> entry : parameters.entrySet()) {
		            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		        } 
		        method.setRequestBody(nameValuePair);
			}
			try {
				client.executeMethod(method);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				response = new String(method.getResponseBodyAsString().getBytes(recvEncoding));
				//打印返回的信息  
			    method.releaseConnection();  
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}  
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			
		}
		return response;
	}
	

	/**
	 * 上传文件
	 * @param reqUrl,地址
	 * @param parameters,普通参数
	 * @param files,文件类型参数
	 * @param recvEncoding
	 * @return
	 */
	public static String doUpload(String reqUrl, Map<String,String> parameters,Map<String,String> files,
			String recvEncoding){
		HttpURLConnection httpURLConnection = null;
		String responseContent = null;
		try {
			String BOUNDARY = "---------HEIDUC"; // 定义数据分隔线
			URL url = new URL(reqUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setUseCaches(false);
			
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			httpURLConnection.setRequestProperty("Charsert", HttpProxy.requestEncoding);
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			OutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
			byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
			
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				StringBuffer sb = new StringBuffer();
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data;name=\""+entry.getKey()+"\"\r\n");
				sb.append("\r\n");
				sb.append(URLEncoder.encode(entry.getValue()+"", HttpProxy.requestEncoding));
				sb.append("\r\n");
				
				byte[] data = sb.toString().getBytes();
				out.write(data);
				System.out.println(sb.toString());
			}
			
			//循环文件列表
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				String fileName = entry.getValue()+"";
				File file = new File(fileName);
				StringBuffer sb = new StringBuffer();
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data;name=\""+entry.getKey()+"\";filename=\""+ file.getName() + "\"\r\n");
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
			}
			out.write(end_data);
			out.flush();
			out.close();
			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String line = null;
			
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			responseContent = sb.toString();
		} catch (IOException e) {
			logger.error(e.getMessage());
			try {
				logger.info(httpURLConnection.getResponseCode()+"");
			} catch (IOException e1) {
			}
		}  catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			
		}
		return responseContent;
	}
	
	
	
	/**
	 * @return 连接超时(毫秒)
	 */
	public static int getConnectTimeOut() {
		return HttpProxy.connectTimeOut;
	}

	/**
	 * @return 读取数据超时(毫秒)
	 */
	public static int getReadTimeOut() {
		return HttpProxy.readTimeOut;
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
	public static void setConnectTimeOut(int connectTimeOut) {
		HttpProxy.connectTimeOut = connectTimeOut;
	}

	/**
	 * @param readTimeOut
	 *            读取数据超时(毫秒)
	 */
	public static void setReadTimeOut(int readTimeOut) {
		HttpProxy.readTimeOut = readTimeOut;
	}

	/**
	 * @param requestEncoding
	 *            请求编码
	 */
	public static void setRequestEncoding(String requestEncoding) {
		HttpProxy.requestEncoding = requestEncoding;
	}

}
