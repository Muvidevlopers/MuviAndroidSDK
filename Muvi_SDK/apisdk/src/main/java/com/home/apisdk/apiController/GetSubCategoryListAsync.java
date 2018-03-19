package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.SubCategoryListInput;
import com.home.apisdk.apiModel.SubCategoryOutput;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This Class loads the Sub Category list.
 *
 * @author Abhishek
 */

public class GetSubCategoryListAsync extends AsyncTask<SubCategoryListInput, Void, Void> {

    private SubCategoryListInput subCategoryListInput;
    private String responseStr;
    private int code;
    private String status;
    private String PACKAGE_NAME;
    private Context context;
    private GetSubCategoryListener listener;

    /**
     * Interface used to allow the caller of a GetSubCategoryListAsync to run some code when get
     * responses.
     */

    public interface GetSubCategoryListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetSubCategoryListPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param subCategoryOutputs A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param code               Response Code From The Server
         * @param status             On Success Message
         */

        void onGetSubCategoryListPostExecuteCompleted(ArrayList<SubCategoryOutput> subCategoryOutputs, int code, String status);
    }

    ArrayList<SubCategoryOutput> subCategoryOutputs = new ArrayList<SubCategoryOutput>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param subCategoryListInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                             For Example: to use this API we have to set following attributes:
     *                             setAuthToken(),setLang_code() etc.
     * @param listener             GetSubCategoryListener listener
     * @param context              android.content.Context
     */

    public GetSubCategoryListAsync(SubCategoryListInput subCategoryListInput, GetSubCategoryListener listener, Context context) {

        this.listener = listener;
        this.context = context;
        this.subCategoryListInput = subCategoryListInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetMenusAsynTask");
    }

    /**
     * Background thread to execute.
     *
     * @return Null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(SubCategoryListInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetSubCategoryList());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.subCategoryListInput.getAuthToken());
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.subCategoryListInput.getCategory_id());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MuviSDK", "ResponseStr::" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {

                code = 0;
                status = "Error";

            } catch (IOException e) {
                code = 0;
                status = "Error";
            }

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
                status = myJson.optString("status");

            }
            if (code == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("sub_category_list");

                int lengthJsonArr = jsonMainNode.length();

                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {

                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        SubCategoryOutput content = new SubCategoryOutput();
                        content.setPermalink(jsonChildNode.optString("permalink"));
                        content.setSubcat_name(jsonChildNode.optString("subcat_name"));
                        content.setSubcategory_id(jsonChildNode.optString("subcategory_id"));
                        subCategoryOutputs.add(content);

                    } catch (Exception e) {
                        status = "Error";
                        code = 0;
                    }
                }
            }
        } catch (Exception e) {
            status = "Error";
            code = 0;
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetSubCategoryListPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            status = "Packge Name Not Matched";
            listener.onGetSubCategoryListPostExecuteCompleted(subCategoryOutputs, code, status);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            listener.onGetSubCategoryListPostExecuteCompleted(subCategoryOutputs, code, status);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onGetSubCategoryListPostExecuteCompleted(subCategoryOutputs, code, status);
    }
}