/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.Utils;
import com.home.apisdk.apiModel.LanguageListInputModel;
import com.home.apisdk.apiModel.LanguageListOutputModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;


import javax.net.ssl.HttpsURLConnection;

/**
 * This Class gives the list of the languages to the users.
 * Users can choose their prefer language among the list of languages.
 *
 * @author MUVI
 */
public class GetLanguageListAsynTask extends AsyncTask<LanguageListInputModel, Void, Void> {

    private LanguageListInputModel languageListInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private String defaultLanguage = "en";
    private GetLanguageListListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetLanguageListAsynTask to run some code when get
     * responses.
     */

    public interface GetLanguageListListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetLanguageListPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param languageListOutputArray A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                  Response From The Server
         * @param message                 On Success Message
         * @param defaultLanguage         For Getting the Default Language
         */

        void onGetLanguageListPostExecuteCompleted(ArrayList<LanguageListOutputModel> languageListOutputArray, int status, String message, String defaultLanguage);
    }

    ArrayList<LanguageListOutputModel> languageListOutputArray = new ArrayList<LanguageListOutputModel>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param languageListInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                               For Example: to use this API we have to set following attributes:
     *                               setAuthToken() etc.
     * @param listener               GetLanguageList Listener
     * @param context                android.content.Context
     */

    public GetLanguageListAsynTask(LanguageListInputModel languageListInputModel, GetLanguageListListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.languageListInputModel = languageListInputModel;
        PACKAGE_NAME = context.getPackageName();

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(LanguageListInputModel... params) {
        Log.v("MUVISDK", "this.languageListInputModel.getAuthToken()" + this.languageListInputModel.getAuthToken());

        try {
                URL url = new URL(APIUrlConstant.getGetLanguageListUrl());
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("authToken", this.languageListInputModel.getAuthToken());
                String query = builder.build().getEncodedQuery();
                responseStr = Utils.handleHttpAndHttpsRequest(url, query, status, message);

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                if (myJson.has("msg")) {
                    message = myJson.optString("msg");
                }
                if (myJson.has("default_lang")) {
                    defaultLanguage = myJson.optString("default_lang");
                }
            }


            if (status > 0 && status == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("lang_list");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        LanguageListOutputModel languageListOutputModel = new LanguageListOutputModel();

                        Log.v("MUVISDK", "LAN_CODE" + jsonChildNode.optString("code"));
                        if ((jsonChildNode.has("code")) && jsonChildNode.optString("code").trim() != null && !jsonChildNode.optString("code").trim().isEmpty() && !jsonChildNode.optString("code").trim().equals("null") && !jsonChildNode.optString("code").trim().matches("")) {
                            languageListOutputModel.setLanguageCode(jsonChildNode.optString("code"));

                        }
                        if ((jsonChildNode.has("language")) && jsonChildNode.optString("language").trim() != null && !jsonChildNode.optString("language").trim().isEmpty() && !jsonChildNode.optString("language").trim().equals("null") && !jsonChildNode.optString("language").trim().matches("")) {
                            languageListOutputModel.setLanguageName(jsonChildNode.optString("language"));
                        }


                        languageListOutputArray.add(languageListOutputModel);
                    } catch (Exception e) {
                        status = 0;
                        message = "";
                    }

                }

            }

        } catch (Exception e) {
            Log.v("MUVISDK", "Exception" + e.toString());

            status = 0;
            message = "";
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetLanguageListPreExecuteStarted();
        status = 0;
        responseStr = "0";
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetLanguageListPostExecuteCompleted(languageListOutputArray, status, message, defaultLanguage);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetLanguageListPostExecuteCompleted(languageListOutputArray, status, message, defaultLanguage);
        }
    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetLanguageListPostExecuteCompleted(languageListOutputArray, status, message, defaultLanguage);

    }

    //}
}
