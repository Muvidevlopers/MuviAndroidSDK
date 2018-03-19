package com.home.apisdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by MUVI on 11/16/2017.
 */

public class Utils {

    //for handle http and https request

    public static String handleHttpAndHttpsRequest(URL url,String query,int status,String message){
        //InputStream ins=null;
        String responseStr = "";
        try {
            if(url.toString().contains("https")){
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                try{
                    String requestParameter[] = query.split("&");
                    for(int i=0;i<requestParameter.length;i++){


                        String key_value[] = requestParameter[i].split("=");
                        if(requestParameter[i].trim().endsWith("=")){
                            conn.setRequestProperty(key_value[0].trim(),"");
                            Log.v("MUVI1","key="+key_value[0]+"  ======  value=");
                        }else{
                            conn.setRequestProperty(key_value[0].trim(),key_value[1].trim());
                            Log.v("MUVI1","key="+key_value[0]+"  ======  value="+key_value[1]);
                        }


//
                    }
                }catch (Exception e){
                    Log.v("BIBHU1","Exception ==="+e.toString());
                }


                InputStream ins = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(ins);
                BufferedReader in = new BufferedReader(isr);

                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    responseStr = inputLine;
                }

            }else{


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                try{
                    String requestParameter[] = query.split("&");
                    for(int i=0;i<requestParameter.length;i++){
                        String key_value[] = requestParameter[i].split("=");
                        conn.setRequestProperty(key_value[0].trim(),key_value[1].trim());
                    }
                }catch (Exception e){}


                InputStream ins = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(ins);
                BufferedReader in = new BufferedReader(isr);

                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    responseStr = inputLine;
                }

            }
        }catch (org.apache.http.conn.ConnectTimeoutException e) {
            Log.v("MUVISDK", "org.apache.http.conn.ConnectTimeoutException e" + e.toString());

            status = 0;
            message = "";

        } catch (IOException e) {
            Log.v("MUVISDK", "IOException" + e.toString());

            status = 0;
            message = "";
        }

        return responseStr;
    }
}
