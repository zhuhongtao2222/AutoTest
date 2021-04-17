package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {
    private String url;
    private ResourceBundle bundle;
    private BasicCookieStore store;

    @BeforeTest
    public void beforeTest(){
        //获取url,获取到http://localhost:8888
        bundle = ResourceBundle.getBundle("application",Locale.CHINA);
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
    public void testPostWithCookies() throws IOException {
        //获取url
        String uri = bundle.getString("test.post.with.cookies");
        String testurl = this.url + uri;
        //声明post请求
        HttpPost post = new HttpPost(testurl);
        //添加参数
        JSONObject param = new JSONObject();
        param.put("name","huhansan");
        param.put("age","18");
        //设置请求头header信息，把参数放进header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //实例化client对象，用来进行方法的执行，并且设置cookies
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(this.store).build();
        //声明一个对象进行响应结果的存储
        String result;
        //执行post方法
        CloseableHttpResponse response = client.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        //处理结果，判断返回结果是否返回预期
        //将返回的响应结果转换成json对象
        JSONObject resultjson = new JSONObject(result);
        //获取结果值
        String success = (String) resultjson.get("huhansan");
        String status = (String) resultjson.get("status");
        System.out.println("success = " + success);
        System.out.println("status  = " + status );
        //判断返回结果的值
        Assert.assertEquals(success,"success");
        Assert.assertEquals(status,"1");
    }
}
