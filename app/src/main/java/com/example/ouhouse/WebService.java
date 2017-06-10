package com.example.ouhouse;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;


/**
 * Created by Magot on 2017/5/22.
 */

public class WebService {
    private static String IP = "192.168.232.132";

    public static HashMap<String, String> executeHttpGet(String username,String password){

        HttpURLConnection conn = null;
        InputStream is = null;
         HashMap<String, String> session =new HashMap<String, String>();
        try {
            String path = "http://" + IP + "/index.php";
            path = path + "?username=" + username + "&password=" + password;

            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
//                is = conn.getInputStream();
//                return parseInfo(is);
                StringBuilder builder = new StringBuilder();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                for(String s = buffer.readLine();s!=null;s=buffer.readLine()){
                    builder.append(s);
                }
                String name = "";
                String sex = "";
                String number = "";
                String session_id= "";
                String flag = "";
                try {
                    JSONObject jsonObject = new JSONObject(builder.toString());
                    name = jsonObject.getString("name");
                    sex = jsonObject.getString("sex");
                    number = jsonObject.getString("number");
                    session_id = jsonObject.getString("session_id");
                    flag = jsonObject.getString("flag");
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(flag.equals("true")){
                    session.put("flag",flag);
                    session.put("s_username",username);
                    session.put("s_name",name);
                    session.put("s_sex",sex);
                    session.put("s_number",number);
                    session.put("s_session_id",session_id);
                    return session;
                }else{
                    session.put("flag",flag);
                    return session;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //意外退出时进行连接关闭保护
            if(conn != null){
                conn.disconnect();
            }
            if(is != null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String regeditUser(String username,String password,String name,String sex,String number){
        HttpURLConnection conn = null;
        InputStream is = null;
        try{
            String path = "http://" + IP + "/regedit.php";
            path = path + "?username=" + username + "&password=" + password+ "&name=" + name + "&sex=" + sex + "&number=" + number;

            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            //意外退出时进行连接关闭保护
            if(conn != null){
                conn.disconnect();
            }
            if(is != null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static JSONObject house_show(String house_name){
        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            String path = "http://" + IP + "/house_show.php";
            path = path + "?house_name=" + house_name;

            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
//                is = conn.getInputStream();
//                return parseInfo(is);
                //返回JSon数据
                StringBuilder builder = new StringBuilder();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                for(String s = buffer.readLine();s!=null;s=buffer.readLine()){
                    builder.append(s);
                }
                    JSONObject jsonObject = new JSONObject(builder.toString());

                return jsonObject;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally{
            //意外退出时进行连接关闭保护
            if(conn != null){
                conn.disconnect();
            }
            if(is != null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static StringBuilder  user_like_select(String username){
        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            String path = "http://" + IP + "/house_like.php";
            path = path + "?username=" + username;

            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
//                is = conn.getInputStream();
//                return parseInfo(is);
                //返回JSon数据
                StringBuilder builder = new StringBuilder();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                for(String s = buffer.readLine();s!=null;s=buffer.readLine()){
                    builder.append(s);
                }
               //JSONObject jsonObject = new JSONObject(builder.toString());

                return builder;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //意外退出时进行连接关闭保护
            if(conn != null){
                conn.disconnect();
            }
            if(is != null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static String user_like_insert(String username,String name,String img,String number,String value){
        HttpURLConnection conn = null;
        InputStream is = null;
        try{
            String path = "http://" + IP + "/house_like.php";
            path = path + "?username="+username+"&楼盘别名="+name+"&图片="+img+"&联系号码="+number+"&价格="+value;

            Log.d("Path",path);
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            //意外退出时进行连接关闭保护
            if(conn != null){
                conn.disconnect();
            }
            if(is != null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static String user_like_delete(String username,String name){
        HttpURLConnection conn = null;
        InputStream is = null;
        try{
            String path = "http://" + IP + "/house_like.php";
            path = path + "?username="+username+"&楼盘别名="+name;

            Log.d("Path",path);
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            //意外退出时进行连接关闭保护
            if(conn != null){
                conn.disconnect();
            }
            if(is != null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    private static String parseInfo(InputStream inStream) throws Exception{
        byte[] data = read(inStream);

        //转化为字符串
        return new String (data,"UTF-8");
    }

    public static byte[] read(InputStream inStream) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = inStream.read(buffer)) != -1){
            outputStream.write(buffer,0,len);
        }
        inStream.close();
        return outputStream.toByteArray();
    }
}
