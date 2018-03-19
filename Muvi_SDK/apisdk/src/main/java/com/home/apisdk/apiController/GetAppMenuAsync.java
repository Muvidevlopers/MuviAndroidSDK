package com.home.apisdk.apiController;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.GetMenusInputModel;
import com.home.apisdk.apiModel.MenusOutputModel;

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
 * This Class loads the Menus.
 *
 * @author Abhishek
 */

public class GetAppMenuAsync extends AsyncTask<GetMenusInputModel, Void, Void> {

    private GetMenusInputModel getMenusInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private String title;
    private String Permalink;
    private String ID;
    private String TitleChild;
    private String PermalinkChild;
    private String IDChild;
    private String UserTitleChild;
    private String UserIDChild;
    private String UserParentIdChild;
    private String UserPermalinkChild;
    private String UserClasChild;
    private String fdomain;
    private String flink_type;
    private String fid;
    private String fdisplay_name;
    private String fpermalink;
    private String furl;
    private String ParentIdChild;
    private String LinkTypeChild;
    private String ParentId;
    private String Clas;
    private String LinkType;
    private String UserTitle;
    private String UserPermalink;
    private String UserID;
    private String UserParentId;
    private String UserClas;
    private GetMenusListener listener;
    private Context context;
    private String str = "#";
    String value;
    String id_seq;
    String language_id;
    String language_parent_id;
    String child_value;
    String child_id_seq;
    String child_language_id;
    String child_language_parent_id;
    String isSubcategoryPresent;
    String category_id;


    /**
     * Interface used to allow the caller of a GetMenusAsynTask to run some code when get
     * responses.
     */


    public interface GetMenusListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetMenusPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param menusOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status           Response Code From The Server
         * @param message          On Success Message
         */

        void onGetMenusPostExecuteCompleted(MenusOutputModel menusOutputModel, int status, String message);
    }


    MenusOutputModel menusOutputModel;
    MenusOutputModel.MainMenu mainMenu;
    MenusOutputModel.UserMenu userMenu;
    MenusOutputModel.FooterMenu footerMenu;
    MenusOutputModel.MainMenu.MainMenuChild mainMenuChild;
    MenusOutputModel.UserMenu.UserMenuChild userMenuChild;
    ArrayList<MenusOutputModel.MainMenu.MainMenuChild> mainMenuChildArrayList;
    ArrayList<MenusOutputModel.UserMenu.UserMenuChild> userMenuChildArrayList;
    ArrayList<MenusOutputModel.MainMenu> mainMenuArrayList;
    ArrayList<MenusOutputModel.UserMenu> userMenuArrayList;
    ArrayList<MenusOutputModel.FooterMenu> footerMenuArrayList;

    /**
     * Constructor to initialise the private data members.
     *
     * @param getMenusInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                           For Example: to use this API we have to set following attributes:
     *                           setAuthToken(),setLang_code() etc.
     * @param listener           GetMenus Listener
     * @param context            android.content.Context
     */

    public GetAppMenuAsync(GetMenusInputModel getMenusInputModel, GetMenusListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        this.getMenusInputModel = getMenusInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetMenusAsynTask");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(GetMenusInputModel... params) {


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetAppMenu());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.getMenusInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.getMenusInputModel.getLang_code());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MuviSDK", "ResponseStr::" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {

                status = 0;
                message = "Error";

            } catch (IOException e) {
                status = 0;
                message = "Error";
            }

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("status");

            }
            Log.v("MuviSDK", "ResponseStr:: status" + status);
            if (status > 0) {
                Log.v("MuviSDK", "ResponseStr:: status" + status);
                if (status == 200) {
                    menusOutputModel = new MenusOutputModel();

                    if ((myJson.has("msg")) && myJson.optString("msg").trim() != null && !myJson.optString("msg").trim().isEmpty() && !myJson.optString("msg").trim().equals("null") && !myJson.optString("msg").trim().matches("")) {
                        menusOutputModel.setMsg(myJson.optString("msg"));

                    }


                    try {

                        JSONArray jsonMainMenu = myJson.optJSONArray("menu_items");
                        mainMenuArrayList = new ArrayList<>();
                        footerMenuArrayList = new ArrayList<>();



                        for (int i = 0; i < jsonMainMenu.length(); i++) {
                            mainMenu = new MenusOutputModel().new MainMenu();
                            footerMenu = new MenusOutputModel().new FooterMenu();
                            footerMenu.setEnable(false);
                            mainMenu.setEnable(true);
                            if (jsonMainMenu.getJSONObject(i).has("title")) {
                                title = jsonMainMenu.getJSONObject(i).optString("title").trim();
                                mainMenu.setTitle(title);
                                footerMenu.setDisplay_name(title);
                            }
                            if (jsonMainMenu.getJSONObject(i).has("permalink")) {
                                Permalink = jsonMainMenu.getJSONObject(i).optString("permalink").trim();
                                mainMenu.setPermalink(Permalink);
                                footerMenu.setPermalink(Permalink);
                            }
                            if (jsonMainMenu.getJSONObject(i).has("id")) {
                                ID = jsonMainMenu.getJSONObject(i).optString("id").trim();
                                mainMenu.setId(ID);
                                footerMenu.setId(ID);
                            }
                            if (jsonMainMenu.getJSONObject(i).has("parent_id")) {
                                ParentId = jsonMainMenu.getJSONObject(i).optString("parent_id").trim();
                                mainMenu.setParent_id(ParentId);
                            }
                            if (jsonMainMenu.getJSONObject(i).has("link_type")) {
                                LinkType = jsonMainMenu.getJSONObject(i).optString("link_type").trim();
                                mainMenu.setLink_type(LinkType);
                                footerMenu.setLink_type(LinkType);
                            }
                            if (jsonMainMenu.getJSONObject(i).has("value")) {
                                value = jsonMainMenu.getJSONObject(i).optString("value").trim();
                                mainMenu.setValue(value);
                            }
                            if (jsonMainMenu.getJSONObject(i).has("id_seq")) {
                                id_seq = jsonMainMenu.getJSONObject(i).optString("id_seq").trim();
                                mainMenu.setId_seq(id_seq);
                                footerMenu.setId_seq(id_seq);
                            }
                            if (jsonMainMenu.getJSONObject(i).has("language_id")) {
                                language_id = jsonMainMenu.getJSONObject(i).optString("language_id").trim();
                                mainMenu.setLanguage_id(language_id);
                                footerMenu.setLanguage_id(language_id);
                            }
                            if (jsonMainMenu.getJSONObject(i).has("language_parent_id")) {
                                language_parent_id = jsonMainMenu.getJSONObject(i).optString("language_parent_id").trim();
                                mainMenu.setLanguage_parent_id(language_parent_id);
                                footerMenu.setLanguage_parent_id(language_parent_id);
                            }
                            if (jsonMainMenu.getJSONObject(i).has("category_id")) {
                                category_id = jsonMainMenu.getJSONObject(i).optString("category_id").trim();
                                mainMenu.setCategory_id(category_id);
                                footerMenu.setCategory_id(category_id);
                            }
                            if (jsonMainMenu.getJSONObject(i).has("isSubcategoryPresent")) {
                                isSubcategoryPresent = jsonMainMenu.getJSONObject(i).optString("isSubcategoryPresent").trim();
                                mainMenu.setIsSubcategoryPresent(isSubcategoryPresent);
                                footerMenu.setIsSubcategoryPresent(isSubcategoryPresent);
                            }
                            if (LinkType!=null && !LinkType.equals("")){
                                if (LinkType.equals("0")){
                                    mainMenuArrayList.add(mainMenu);
                                }
                                else{
                                    footerMenuArrayList.add(footerMenu);
                                }
                            }




                            if (jsonMainMenu.getJSONObject(i).has("child")) {

                                try {
                                    mainMenuChildArrayList= new ArrayList<>();


                                    JSONArray jsonChildNode = jsonMainMenu.getJSONObject(i).getJSONArray("child");
                                    for (int j = 0; j < jsonChildNode.length(); j++) {

                                        mainMenuChild = mainMenu.new MainMenuChild();
                                        if (jsonChildNode.getJSONObject(j).has("title")) {
                                            TitleChild = jsonChildNode.getJSONObject(j).optString("title").trim();
                                            mainMenuChild.setTitle(TitleChild);
                                        }
                                        if (jsonChildNode.getJSONObject(j).has("permalink")) {
                                            PermalinkChild = jsonChildNode.getJSONObject(j).optString("permalink").trim();
                                            mainMenuChild.setPermalink(PermalinkChild);
                                        }
                                        if (jsonChildNode.getJSONObject(j).has("id")) {
                                            IDChild = jsonChildNode.getJSONObject(j).optString("id").trim();
                                            mainMenuChild.setId(IDChild);
                                        }
                                        if (jsonChildNode.getJSONObject(j).has("parent_id")) {
                                            ParentIdChild = jsonChildNode.getJSONObject(j).optString("parent_id").trim();
                                            mainMenuChild.setParent_id(ParentIdChild);
                                        }
                                        if (jsonChildNode.getJSONObject(j).has("link_type")) {
                                            LinkTypeChild = jsonChildNode.getJSONObject(j).optString("link_type").trim();
                                            mainMenuChild.setLink_type(LinkTypeChild);
                                        }
                                        if (jsonChildNode.getJSONObject(j).has("value")) {
                                            child_value = jsonChildNode.getJSONObject(i).optString("value").trim();
                                            mainMenuChild.setValue(child_value);
                                        }
                                        if (jsonChildNode.getJSONObject(j).has("id_seq")) {
                                            child_id_seq = jsonChildNode.getJSONObject(i).optString("id_seq").trim();
                                            mainMenuChild.setId_seq(child_id_seq);
                                        }
                                        if (jsonChildNode.getJSONObject(j).has("language_id")) {
                                            child_language_id = jsonChildNode.getJSONObject(i).optString("language_id").trim();
                                            mainMenuChild.setLanguage_id(child_language_id);
                                        }
                                        if (jsonChildNode.getJSONObject(j).has("language_parent_id")) {
                                            child_language_parent_id = jsonChildNode.getJSONObject(i).optString("language_parent_id").trim();
                                            mainMenuChild.setLanguage_parent_id(child_language_parent_id);
                                        }
                                        mainMenuChildArrayList.add(mainMenuChild);


                                    }

                                    mainMenu.setMainMenuChildModel(mainMenuChildArrayList);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                        menusOutputModel.setMainMenuModel(mainMenuArrayList);
                        menusOutputModel.setFooterMenuModel(footerMenuArrayList);

                        JSONArray jsonUserMenu = myJson.optJSONArray("usermenu");
                        userMenuArrayList = new ArrayList<>();
                        try {
                            for (int i = 0; i < jsonUserMenu.length(); i++) {
                                userMenu = new MenusOutputModel().new UserMenu();
                                if (jsonUserMenu.optJSONObject(i).has("title")) {
                                    UserTitle = jsonUserMenu.optJSONObject(i).optString("title").trim();
                                    userMenu.setTitle(UserTitle);
                                }
                                if (jsonUserMenu.optJSONObject(i).has("permalink")) {
                                    UserPermalink = jsonUserMenu.optJSONObject(i).optString("permalink").trim();
                                    userMenu.setPermalink(UserPermalink);
                                }
                                if (jsonUserMenu.optJSONObject(i).has("parent_id")) {
                                    UserParentId = jsonUserMenu.optJSONObject(i).optString("parent_id").trim();
                                    userMenu.setParent_id(UserParentId);
                                }


                                if (UserPermalink.equals(str)) {
                                    try {

                                        JSONArray jsonUserChildNode = jsonUserMenu.getJSONObject(i).getJSONArray("children");
                                        int lengthJsonUserChildArr = jsonUserChildNode.length();
                                        for (int j = 0; j < lengthJsonUserChildArr; j++) {

                                            userMenuChild = userMenu.new UserMenuChild();
                                            UserTitleChild = jsonUserChildNode.optJSONObject(j).optString("title").trim();
                                            userMenuChild.setTitle(UserTitleChild);

                                            UserPermalinkChild = jsonUserChildNode.optJSONObject(j).optString("permalink").trim();
                                            userMenuChild.setPermalink(UserPermalinkChild);

                                            UserIDChild = jsonUserChildNode.optJSONObject(j).optString("id").trim();
                                            userMenuChild.setId(UserIDChild);
                                            Log.d("ANU", "Response===" + UserIDChild);
                                            UserParentIdChild = jsonUserChildNode.optJSONObject(j).optString("parent_id").trim();
                                            userMenuChild.setParent_id(UserParentIdChild);
                                            Log.d("ANU", "Response===" + UserParentIdChild);

                                            Log.d("ANU", "Response===" + UserClasChild);
                                            userMenuChildArrayList.add(userMenuChild);


                                        }

                                        userMenu.setUserMenuChildModel(userMenuChildArrayList);
                                    } catch (Exception e) {
                                    }
                                }

                                userMenuArrayList.add(userMenu);
                            }


                            menusOutputModel.setUserMenuModel(userMenuArrayList);
                        } catch (Exception e) {
                        }


              /*          footerMenuArrayList = new ArrayList<>();
                        JSONArray jsonFooterMenu = myJson.optJSONArray("footer_menu");

                        try {
                            for (int i = 0; i < jsonFooterMenu.length(); i++) {
                                footerMenu = new MenusOutputModel().new FooterMenu();
                                footerMenu.setEnable(false);

                                if (jsonFooterMenu.getJSONObject(i).has("domain")) {
                                    fdomain = jsonFooterMenu.getJSONObject(i).optString("domain").toString().trim();
                                    footerMenu.setDomain(fdomain);
                                }
                                if (jsonFooterMenu.getJSONObject(i).has("link_type")) {
                                    flink_type = jsonFooterMenu.getJSONObject(i).optString("link_type").toString().trim();
                                    footerMenu.setLink_type(flink_type);
                                }
                                if (jsonFooterMenu.getJSONObject(i).has("id")) {
                                    fid = jsonFooterMenu.getJSONObject(i).optString("id").toString().trim();
                                    footerMenu.setId(fid);
                                }
                                if (jsonFooterMenu.getJSONObject(i).has("display_name")) {
                                    fdisplay_name = jsonFooterMenu.getJSONObject(i).optString("display_name").toString().trim();
                                    footerMenu.setDisplay_name(fdisplay_name);
                                }
                                if (jsonFooterMenu.getJSONObject(i).has("permalink")) {
                                    fpermalink = jsonFooterMenu.getJSONObject(i).optString("permalink").toString().trim();
                                    footerMenu.setPermalink(fpermalink);
                                }
                                if (jsonFooterMenu.getJSONObject(i).has("url")) {
                                    furl = jsonFooterMenu.getJSONObject(i).optString("url").toString().trim();
                                    footerMenu.setUrl(furl);
                                }

                                footerMenuArrayList.add(footerMenu);

                            }

                            menusOutputModel.setFooterMenuModel(footerMenuArrayList);
                        } catch (Exception e) {
                        }*/

                    }
                    catch (Exception e) {
                    }


                } else {
                    responseStr = "0";
                    status = 0;
                    // totalItems = 0;
                    message = "";
                }
            }
        } catch (Exception e) {
            status = 0;
            // totalItems = 0;
            message = "";
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetMenusPreExecuteStarted();
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetMenusPostExecuteCompleted(menusOutputModel, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetMenusPostExecuteCompleted(menusOutputModel, status, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetMenusPostExecuteCompleted(menusOutputModel, status, message);

    }
}
