package com.cmcc.algo.common.utils;

import com.alibaba.fastjson.JSON;
import com.sun.net.ssl.HttpsURLConnection;
import jodd.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * @author kk
 * http请求工具
 */
@Slf4j
@Component
public class HttpRequest {

    /**
     * 发送Post请求-json数据
     * @param url : 请求的连接
     * @param params ：  请求参数，无参时传null
     * @return
     */
    public static JSONObject sendPostToJson(String url,Map<String,String> params){
        log.info("发送数据:"+params);
        JSONObject jsonObject = new JSONObject();
        jodd.http.HttpRequest request = jodd.http.HttpRequest.post(url);
        request.contentType("application/json");
        request.charset("utf-8");
        request.header("token",String.valueOf(params.getOrDefault("token","")));
//        request.header("token","dsfdsf4sd56f465sd47f65ds44fdsf");
        //参数详情
        if(params!=null) {
            request.body(JSON.toJSONString(params));
        }
        HttpResponse response = request.send();
        String bodyText = response.bodyText();
		if(response.statusCode()==200&&StringUtils.isNotBlank(bodyText)){
			jsonObject = JSONObject.fromObject(bodyText);
		}
		jsonObject.put("msg",response.body());
		if(response.statusCode()==500){
			jsonObject.put("msg",response.body());
		}
		log.info("接口返回:"+bodyText);
		jsonObject.put("code",response.statusCode());
        return jsonObject;
    }

	/**
	 * post
	 * @param dataMap  请求的数据
	 * @param url 请求url
	 * @return
	 */
	public static JSONObject doPostJson_HttpClient(Map<String, Object> dataMap, String url){
		log.info("发送数据:"+dataMap);
		JSONObject jsonObject = new JSONObject();
		//post方法和参数
		HttpResponse request = jodd.http.HttpRequest.post(url)
				.form(dataMap)
				.queryEncoding("UTF-8")
				.send();
		String bodyText = request.bodyText();
		log.info("接口返回:"+bodyText);
		jsonObject = JSONObject.fromObject(bodyText);
		return jsonObject;
	}

    /**
     * 发送Get请求
     * @param url : 请求的连接
     * @param params ： 请求参数，无参时传null
     * @return
     */
    public static JSONObject sendGet(String url,Map<String,String> params){
        log.info("发送数据:"+params);
        JSONObject jsonObject = new JSONObject();
        jodd.http.HttpRequest request = jodd.http.HttpRequest.get(url);
        if(params!=null) {
            request.query(params);
        }
        HttpResponse response = request.send();
        String bodyText = response.bodyText();
		if(response.statusCode()==200&&StringUtils.isNotBlank(bodyText)){
			jsonObject = JSONObject.fromObject(bodyText);
		}
		if(response.statusCode()==500){
			jsonObject.put("msg",response.body());
		}
		log.info("接口返回:"+bodyText);
		jsonObject.put("code",response.statusCode());

		return jsonObject;
    }
    /**
     * 发送Delete请求
     * @param url : 请求的连接
     * @param params ：  请求参数，无参时传null
     * @return
     */
    public static JSONObject sendDelete(String url,Map<String,Object> params){
        log.info("发送数据:"+params);
        JSONObject jsonObject = new JSONObject();
        jodd.http.HttpRequest request = jodd.http.HttpRequest.delete(url);
        request.contentType("application/json");
        request.charset("utf-8");
        request.header("token",String.valueOf(params.getOrDefault("token","")));
        if(params!=null) {
            request.form(params);
        }
        HttpResponse response = request.send();
        String bodyText = response.bodyText();
        if(response.statusCode()==200&&StringUtils.isNotBlank(bodyText)){
			jsonObject = JSONObject.fromObject(bodyText);
		}
        if(response.statusCode()==500){
			jsonObject.put("msg",response.body());
		}
        log.info("接口返回:"+bodyText);
        jsonObject.put("code",response.statusCode());
        return jsonObject;
    }
	/**
	 * 普通GET请求使用使用
	 * get请求
	 * @param url
	 * @return
	 */
	public static JSONObject doGetJson_HttpClient(String url) {
		JSONObject jsonObject = new JSONObject();
		HttpGet httpGet = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			httpGet = new HttpGet(url);
			org.apache.http.HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				String result = EntityUtils.toString(entity,"UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			//释放连接
			httpGet.releaseConnection();
		}
		return jsonObject;

	}





	/**
	 * https请求使用
	 * @param requestUrl 要请求的url
	 * @param requestMethod  请求方式
	 * @param outputStr  发送求的提交数据
	 * @return  返回请求结果
	 */
	public String doHttpsJson_HttpClient(String requestUrl, String requestMethod, String outputStr){

		try {
			URL url = new URL(null,requestUrl,new com.sun.net.ssl.internal.www.protocol.https.Handler());
			//URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}


			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}


			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();
			return buffer.toString();
		} catch (MalformedURLException e) {
			System.out.println("连接超时：{}");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("https请求异常：{}");
			e.printStackTrace();
		}
		return null;
	}
}
