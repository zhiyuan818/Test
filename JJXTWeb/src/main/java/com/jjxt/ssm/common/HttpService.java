package com.jjxt.ssm.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjxt.ssm.utils.StringUtil;

@Service
public class HttpService {

	private static Logger logger = Logger.getLogger(HttpService.class);
	// 创建Httpclient对象
	@Autowired(required = false)
	private CloseableHttpClient httpClient;

	// 请求信息的配置
	@Autowired(required = false)
	private RequestConfig requestConfig;

	/**
	 * 执行Get请求
	 * 
	 * @param url
	 * @return 请求到的内容
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String doGet(String url) throws URISyntaxException, ClientProtocolException, IOException {
		return doGet(url, null);
	}

	/**
	 * 执行Get请求
	 * 
	 * @param url
	 * @param params
	 *            请求中的参数
	 * @return 请求到的内容
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String doGet(String url, Map<String, Object> params)
			throws URISyntaxException, ClientProtocolException, IOException {
		// 定义请求的参数
		URI uri = null;
		if (params != null) {
			URIBuilder builder = new URIBuilder(url);
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				builder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
			}
			uri = builder.build();
		}

		// 创建http GET请求
		HttpGet httpGet = null;
		if (uri != null) {
			httpGet = new HttpGet(uri);
		} else {
			httpGet = new HttpGet(url);
		}
		// 设置请求参数
		httpGet.setConfig(this.requestConfig);

		// 请求的结果
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获取服务端返回的数据,并返回
				logger.debug("[BING][HEEPCLIENT]状态码：" + response.getStatusLine().getStatusCode());
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			} else {
				logger.debug("[BING][HEEPCLIENT]状态码：" + response.getStatusLine().getStatusCode());
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 * @param params
	 *            请求中的参数
	 * @return 请求到的内容
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doPost(String url, Map<String, Object> params)
			throws URISyntaxException, ClientProtocolException, IOException {
		// 设置post参数
		List<NameValuePair> parameters = null;
		// 构造一个form表单式的实体
		UrlEncodedFormEntity formEntity = null;

		// 定义请求的参数
		if (params != null) {
			// 设置post参数
			parameters = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				// 添加参数
				parameters.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
			}
			// 构造一个form表单式的实体
			formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
		}

		// 创建http GET请求
		HttpPost httpPost = null;
		if (formEntity != null) {
			httpPost = new HttpPost(url);
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);
			// 伪装浏览器请求
			httpPost.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
		} else {
			httpPost = new HttpPost(url);
			// 伪装浏览器请求
			httpPost.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
		}
		// 设置请求参数
		httpPost.setConfig(this.requestConfig);

		// 请求的结果
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获取服务端返回的数据,并返回
				logger.debug("[BING][HTTPCLIENT]状态码：" + response.getStatusLine().getStatusCode());
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			} else {
				logger.debug("[BING][HTTPCLIENT]状态码：" + response.getStatusLine().getStatusCode());
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 * @param params
	 *            请求中的参数
	 * @return 请求到的内容
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doPost(String url) throws URISyntaxException, ClientProtocolException, IOException {
		return doPost(url, null);
	}

	public String doBooleanPost(HttpService httpService, String url1,String url2,Map<String, Object> map) throws URISyntaxException, ClientProtocolException, IOException {
		String result = httpService.doPost(url1, map);
		if(StringUtil.isEmpty(result)){
			result = httpService.doPost(url2,map);
		}
		return result;
	}
	
	
	public  String httpClientPostJson(String url,String param) {
		String message = null;
		URL httpUrl = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			httpUrl = new URL(url);
			HttpURLConnection http = (HttpURLConnection) httpUrl.openConnection();
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			http.setRequestProperty("Accept","application/json;charset=utf-8");
			http.setRequestProperty("Connection","close");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
	        System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
	        http.connect();
	        os = http.getOutputStream();
	        os.write(param.getBytes("UTF-8"));
	        is = http.getInputStream();
	        int size = is.available();
	        byte[] jsonBytes = new byte[size];
	        is.read(jsonBytes);
	        message = new String(jsonBytes, "UTF-8");
		} catch (Exception e) {
			logger.error("[ERR] httpClientPost is error, Exception={}", e);
		}finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return message; 
	}
}
