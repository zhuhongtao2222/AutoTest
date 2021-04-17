package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {

    private String url;
    private ResourceBundle bundle;
    private BasicCookieStore store;

    @BeforeTest
    public void beforeTest(){
        //获取url,获取到http://localhost:8888
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        String result;
        //从配置文件获取拼接url
        String uri = bundle.getString("getCookies.uri");
        String testUrl = this.url + uri;
        //实例化httpget
        HttpGet get = new HttpGet(testUrl);
        //实例化httpClient,获取cookie信息
        this.store = new BasicCookieStore();
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(store).build();
        //调用get请求获取返回值
        CloseableHttpResponse response = client.execute(get);
        //result存储返回的响应
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //获取cookies信息
        List<Cookie> cookieList = store.getCookies();

        //打印cookie信息
        for(Cookie cookie:cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name =" + name + " cookie value =" + value);
        }
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testgetwithcookies() throws IOException {
        //获取到具体的url进行拼接
        String uri = bundle.getString("test.get.with.cookies");
        String testurl = this.url + uri;
        //实例化get请求
        HttpGet get = new HttpGet(testurl);
        //实例化客户端并设置cookie信息
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(this.store).build();
        //实例化请求响应
        CloseableHttpResponse response = client.execute(get);
        //获取返回值
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        //获取状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode = " + statusCode);
        //显示最终结果
        if(statusCode == 200){
            System.out.println(result);
        }else {
            System.out.println("访问test.get.with.cookies接口失败");
        }
    }



}
