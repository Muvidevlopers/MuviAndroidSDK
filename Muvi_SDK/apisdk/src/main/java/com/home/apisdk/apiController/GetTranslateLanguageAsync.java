/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.LanguageListInputModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This Class Translate the language according to the user's choice.
 *
 * @author MUVI
 */

public class GetTranslateLanguageAsync extends AsyncTask<Void, Void, String> {

    private GetTranslateLanguageInfoListener listener;
    private Context context;
    private LanguageListInputModel languageListInputModel;
    private String PACKAGE_NAME;
    private String message = "";
    private String responseStr;
    private int code;
    private String resultJsonString = "";

    /**
     * Interface used to allow the caller of a GetTranslateLanguageAsync to run some code when get
     * responses.
     */

    public interface GetTranslateLanguageInfoListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetTranslateLanguagePreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param jsonResponse For Getting The Result Of JSON Response
         * @param status       Response Code From The Server
         */

        void onGetTranslateLanguagePostExecuteCompleted(String jsonResponse, int status);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param languageListInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                               For Example: to use this API we have to set following attributes:
     *                               setAuthToken(),setLangCode() etc.
     * @param listener               GetTranslateLanguageInfo Listener
     * @param context                android.content.Context
     */

    public GetTranslateLanguageAsync(LanguageListInputModel languageListInputModel,
                                     GetTranslateLanguageInfoListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        this.languageListInputModel = languageListInputModel;

        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "register user payment");
    }

    /**
     * Background thread to execute.
     *
     * @return resultJsonString
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected String doInBackground(Void... params) {

        String urlRouteList = APIUrlConstant.getLanguageTranslation();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urlRouteList);
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, languageListInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.LANG_CODE, languageListInputModel.getLangCode());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = (EntityUtils.toString(response.getEntity())).trim();
            } catch (Exception e) {
            }
            if (responseStr != null) {
                JSONObject parent_json = new JSONObject(responseStr);
                JSONObject resultJson = parent_json.getJSONObject("translation");
                resultJsonString = resultJson.toString();
                try {
                    code = Integer.parseInt(parent_json.optString("code"));

                } catch (Exception e) {
                    code = 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJsonString;
    }

    @Override
    protected void onPostExecute(String resultJsonString) {
        listener.onGetTranslateLanguagePostExecuteCompleted(resultJsonString, code);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetTranslateLanguagePreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetTranslateLanguagePostExecuteCompleted(resultJsonString, code);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetTranslateLanguagePostExecuteCompleted(resultJsonString, code);
        }
    }
}
