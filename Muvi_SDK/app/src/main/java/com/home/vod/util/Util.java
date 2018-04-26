package com.home.vod.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.home.apisdk.apiModel.APVModel;
import com.home.apisdk.apiModel.CurrencyModel;
import com.home.apisdk.apiModel.PPVModel;
import com.home.vod.BuildConfig;
import com.home.vod.QueueDataProvider;
import com.home.vod.R;
import com.home.vod.activity.LoginActivity;
import com.home.vod.activity.MainActivity;
import com.home.vod.activity.RegisterActivity;
import com.home.vod.activity.ShowWithEpisodesActivity;
import com.home.vod.activity.SplashScreen;
import com.home.vod.expandedcontrols.ExpandedControlsActivity;
import com.home.vod.model.DataModel;
import com.home.vod.model.LanguageModel;
import com.home.vod.preferences.LanguagePreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.home.vod.preferences.LanguagePreference.*;
import static com.home.vod.preferences.LanguagePreference.ALREADY_PURCHASE_THIS_CONTENT;
import static com.home.vod.preferences.LanguagePreference.APP_ON;
import static com.home.vod.preferences.LanguagePreference.APP_SELECT_LANGUAGE;
import static com.home.vod.preferences.LanguagePreference.BTN_SUBMIT;
import static com.home.vod.preferences.LanguagePreference.CARD_WILL_CHARGE;
import static com.home.vod.preferences.LanguagePreference.CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.CROSSED_MAXIMUM_LIMIT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PURCHASE;
import static com.home.vod.preferences.LanguagePreference.FILL_FORM_BELOW;
import static com.home.vod.preferences.LanguagePreference.GEO_BLOCKED_ALERT;
import static com.home.vod.preferences.LanguagePreference.LOGIN_STATUS_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.MESSAGE;
import static com.home.vod.preferences.LanguagePreference.NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.PURCHASE;
import static com.home.vod.preferences.LanguagePreference.PURCHASE_SUCCESS_ALERT;
import static com.home.vod.preferences.LanguagePreference.SEARCH_HINT;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_ALERT;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.SLOW_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.SLOW_ISSUE_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.SORT_BY;
import static com.home.vod.preferences.LanguagePreference.STORY_TITLE;
import static com.home.vod.preferences.LanguagePreference.TERMS;
import static com.home.vod.preferences.LanguagePreference.TRANASCTION_DETAIL;
import static com.home.vod.preferences.LanguagePreference.TRANSACTION;
import static com.home.vod.preferences.LanguagePreference.TRANSACTION_DATE;
import static com.home.vod.preferences.LanguagePreference.TRANSACTION_STATUS;
import static com.home.vod.preferences.LanguagePreference.TRY_AGAIN;
import static com.home.vod.preferences.LanguagePreference.UNPAID;
import static com.home.vod.preferences.LanguagePreference.UPDATE_PROFILE;
import static com.home.vod.preferences.LanguagePreference.UPDATE_PROFILE_ALERT;
import static com.home.vod.preferences.LanguagePreference.USE_NEW_CARD;
import static com.home.vod.preferences.LanguagePreference.VIDEO_ISSUE;
import static com.home.vod.preferences.LanguagePreference.VIEW_MORE;
import static com.home.vod.preferences.LanguagePreference.VIEW_TRAILER;
import static com.home.vod.preferences.LanguagePreference.WATCH;
import static com.home.vod.preferences.LanguagePreference.WATCH_NOW;
import static com.home.vod.preferences.LanguagePreference.YES;

/**
 * Created by User on 24-07-2015.
 */
public class Util {

    public static MediaInfo mSendingMedia;
    public static boolean drawer_line_visibility = true;
    public static boolean itemclicked = false;
    public static String DEFAULT_IS_ONE_STEP_REGISTRATION = "0";
    public static final String UpdateGoogleid = "UpdateGoogleid";

    public static int main_menu_list_size = -2;
    public static PPVModel ppvModel = null;
    public static APVModel apvModel = null;
    public static CurrencyModel currencyModel = null;
    public static DataModel dataModel = null;
    public static ArrayList<LanguageModel> languageModel = null;

    public static boolean goToLibraryplayer = false;
    public static boolean my_library_visibility = false;

    public static ArrayList<Integer> image_orentiation = new ArrayList<>();

    public static String GOOGLE_FCM_TOKEN = "GOOGLE_FCM_TOKEN";
    public static String DEFAULT_GOOGLE_FCM_TOKEN = "0";
    public static boolean favorite_clicked = false;

    public static int check_for_subscription = 0;

    public static String selected_season_id = "0";
    public static String selected_episode_id = "0";

    public static String VideoResolution = "Auto";
    public static String DefaultSubtitle = "Off";
    public static boolean player_description = true;
    public static boolean landscape = true;

    public static boolean hide_pause = false;
    public static boolean call_finish_at_onUserLeaveHint = true;
    public static int ERROR_CODE_EXPIRED_AUTHTOKEN = 410;

    //public static String Dwonload_pdf_rootUrl = "https://www.muvi.com/docs/";

    public static String pdf_url=BuildConfig.SERVICE_BASE_PATH;
    public static String  final_pdf_url=pdf_url.substring(0,pdf_url.lastIndexOf("rest"+""+'/'));
    public static String Dwonload_pdf_rootUrl = final_pdf_url +""+ "docs/";

    public static boolean app_is_in_player_context = false;
    public static ArrayList<String> drawer_collapse_expand_imageview = new ArrayList<>();
    public static int image_compressed = 3;
    public static boolean hideBcakIcon = false;

   /*public static boolean checkNetwork(Context context) {
      ConnectivityManager cm =
              (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
      boolean isConnected = activeNetwork != null &&
              activeNetwork.isConnectedOrConnecting();
      if (isConnected == false) {
         return false;
      }
      return true;
   }*/


    //Array Contains a string
    public static boolean containsString(String[] list, String str) {
        return Arrays.asList(list).contains(str);
    }


   /*public static String rootUrl() {
      //String rootUrl = "https://sonydadc.muvi.com/rest/";
//        String rootUrl = "http://muvistudio.edocent.com/rest/";
      String rootUrl = "https://www.muvi.com/rest/";
//        String rootUrl = "https://www.idogic.com/rest/";
      return rootUrl;

   }*/


    public static int isDouble(String str) {
        Double d = Double.parseDouble(str);
        int i = d.intValue();
        return i;
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {

            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            e.printStackTrace();
            outputDate = "";
        }
        return outputDate;

    }

    public static long calculateDays(Date dateEarly, Date dateLater) {
        return (dateLater.getTime() - dateEarly.getTime()) / (24 * 60 * 60 * 1000);
    }

    //Email Validation for activity_login
    public static boolean isValidMail(String email2) {
        boolean check;
        Pattern p;
        Matcher m;
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        p = Pattern.compile(EMAIL_STRING);
        m = p.matcher(email2);
        check = m.matches();
        if (!check) {
        }
        return check;
    }

    public static boolean isValidPhone(String phone) {

        if ((phone.length() >= 10) && (phone.length() <= 15)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isConfirmPassword(String password, String confirmPassword) {
        Pattern pattern = Pattern.compile(password, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(confirmPassword);

        return matcher.matches();
    }

    public static boolean containsIgnoreCase(List<Integer> list, int soughtFor) {
        for (Integer current : list) {
            if (current == soughtFor) {
                return true;
            }
        }
        return false;
    }




  /* public static SharedPreferences getLanguageSharedPrefernce(Context context) {
      SharedPreferences languageSharedPref = context.getSharedPreferences(Util.LANGUAGE_SHARED_PRE, 0); // 0 - for private mode
      return languageSharedPref;
   }

   public static void setLanguageSharedPrefernce(Context context, String Key, String Value) {
      SharedPreferences languageSharedPref = context.getSharedPreferences(Util.LANGUAGE_SHARED_PRE, 0); // 0 - for private mode
      SharedPreferences.Editor editor = languageSharedPref.edit();
      editor.putString(Key, Value);
      editor.commit();
   }*/

   /*public static String getTextofLanguage(Context context, String tempKey, String defaultValue) {
      SharedPreferences sp = Util.getLanguageSharedPrefernce(context);
      String str = sp.getString(tempKey, defaultValue);
      return str;
   }
*/


    /*****************
     * chromecvast*-------------------------------------
     */
    public static void showQueuePopup(final Context context, View view, final MediaInfo mediaInfo) {
        CastSession castSession =
                CastContext.getSharedInstance(context).getSessionManager().getCurrentCastSession();
        if (castSession == null || !castSession.isConnected()) {
            return;
        }
        final RemoteMediaClient remoteMediaClient = castSession.getRemoteMediaClient();
        if (remoteMediaClient == null) {
            return;
        }
        final QueueDataProvider provider = QueueDataProvider.getInstance(context);
        PopupMenu popup = new PopupMenu(context, view);
        popup.getMenuInflater().inflate(
                provider.isQueueDetached() || provider.getCount() == 0
                        ? R.menu.detached_popup_add_to_queue
                        : R.menu.popup_add_to_queue, popup.getMenu());
        PopupMenu.OnMenuItemClickListener clickListener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                QueueDataProvider provider = QueueDataProvider.getInstance(context);
                MediaQueueItem queueItem = new MediaQueueItem.Builder(mediaInfo).setAutoplay(
                        true).setPreloadTime(Constant.PRELOAD_TIME_S).build();
                MediaQueueItem[] newItemArray = new MediaQueueItem[]{queueItem};
                String toastMessage = null;
                if (provider.isQueueDetached() && provider.getCount() > 0) {
                    if ((menuItem.getItemId() == R.id.action_play_now)) {
                        MediaQueueItem[] items = Util
                                .rebuildQueueAndAppend(provider.getItems(), queueItem);
                        remoteMediaClient.queueLoad(items, provider.getCount(),
                                MediaStatus.REPEAT_MODE_REPEAT_OFF, null);
                    } else {
                        return false;
                    }
                } else {
                    if (provider.getCount() == 0) {
                        remoteMediaClient.queueLoad(newItemArray, 0,
                                MediaStatus.REPEAT_MODE_REPEAT_OFF, null);
                    } else {
                        int currentId = provider.getCurrentItemId();
                        if (menuItem.getItemId() == R.id.action_play_now) {
                            remoteMediaClient.queueInsertAndPlayItem(queueItem, currentId, null);
                        } else {
                            return false;
                        }
                    }
                }
                if (menuItem.getItemId() == R.id.action_play_now) {
                    Intent intent = new Intent(context, ExpandedControlsActivity.class);
                    context.startActivity(intent);
                }
                if (!TextUtils.isEmpty(toastMessage)) {
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        };
        popup.setOnMenuItemClickListener(clickListener);
        popup.show();
    }

    public static MediaQueueItem[] rebuildQueue(List<MediaQueueItem> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        MediaQueueItem[] rebuiltQueue = new MediaQueueItem[items.size()];
        for (int i = 0; i < items.size(); i++) {
            rebuiltQueue[i] = rebuildQueueItem(items.get(i));
        }

        return rebuiltQueue;
    }

    public static MediaQueueItem[] rebuildQueueAndAppend(List<MediaQueueItem> items,
                                                         MediaQueueItem currentItem) {
        if (items == null || items.isEmpty()) {
            return new MediaQueueItem[]{currentItem};
        }
        MediaQueueItem[] rebuiltQueue = new MediaQueueItem[items.size() + 1];
        for (int i = 0; i < items.size(); i++) {
            rebuiltQueue[i] = rebuildQueueItem(items.get(i));
        }
        rebuiltQueue[items.size()] = currentItem;

        return rebuiltQueue;
    }

    public static MediaQueueItem rebuildQueueItem(MediaQueueItem item) {
        return new MediaQueueItem.Builder(item).clearItemId().build();
    }

    /**
     * Method to convert hr to sec.
     *
     * @param time
     * @return
     */
    public static int HoursToSecond(String time) {

        StringTokenizer tokens = new StringTokenizer(time, ":");
        int hour = Integer.parseInt(tokens.nextToken()) * 3600;
        int minute = Integer.parseInt(tokens.nextToken()) * 60;
        int second = Integer.parseInt(tokens.nextToken()) + hour + minute;
        return second;
    }


    /**
     * Method to get display size of device.
     *
     * @param context
     * @return
     */
    public static Point getDisplaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        return new Point(width, height);
    }

    /**
     * Method to check portrait orientation.
     *
     * @param context
     * @return
     */
    public static boolean isOrientationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    public static String getAppVersionName(Context context) {
        String versionString = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0 /* basic info */);
            versionString = info.versionName;
        } catch (Exception e) {
            // do nothing
        }
        return versionString;
    }


    /**
     * Show toast message
     *
     * @param mContext
     * @param message
     */
    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }


    /**
     * @param mContext Method to show no video available alert.
     * @auther alok
     */
    public static void showNoDataAlert(Context mContext) {
        LanguagePreference languagePreference = LanguagePreference.getLanguagePreference(mContext);
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle);
        dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_VIDEO_AVAILABLE, DEFAULT_NO_VIDEO_AVAILABLE));
        dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        dlgAlert.create().show();
    }

    public static void showActivateSubscriptionWatchVideoAleart(final Activity mContext, String showMsg) {
        LanguagePreference languagePreference = LanguagePreference.getLanguagePreference(mContext);
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle);
                  /*  if (userMessage!=null && !userMessage.equalsIgnoreCase("")){
                        dlgAlert.setMessage(userMessage);
                    }else{
                        dlgAlert.setMessage(Util.getTextofLanguage(ShowWithEpisodesActivity.this,Util.ACTIVATE_SUBSCRIPTION_WATCH_VIDEO,Util.DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO));

                    }*/
        dlgAlert.setMessage(showMsg);

        dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent in = new Intent(mContext, MainActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        if (mContext instanceof LoginActivity) {
                            mContext.startActivity(in);
                            ((LoginActivity) mContext).onBackPressed();
                        }
                        if (mContext instanceof RegisterActivity) {
                            mContext.startActivity(in);
                            ((RegisterActivity) mContext).onBackPressed();
                        }

                    }
                });
        dlgAlert.create().show();
    }


    public static void printMD5Key(Context mContext) {

        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(),  // replace with your unique package name
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                LogUtil.showLog("MUVIshkey:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                String s = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                LogUtil.showLog("MUVIshkey:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    /**
     * Parse language key and store in prefernces.
     *
     * @param languagePreference
     * @param jsonResponse
     * @param default_Language
     * @throws JSONException
     */

    public static void parseLanguage(LanguagePreference languagePreference, String jsonResponse, String default_Language) throws JSONException {
        JSONObject json = new JSONObject(jsonResponse);
        setTranslationLanguageToPref(languagePreference, GEO_BLOCKED_ALERT, DEFAULT_GEO_BLOCKED_ALERT, "", json);
        setTranslationLanguageToPref(languagePreference, GMAIL_SIGNIN, DEFAULT_GMAIL_SIGNIN, "google_signin", json);
        setTranslationLanguageToPref(languagePreference, GMAIL_SIGNUP, DEFAULT_GMAIL_SIGNUP, "google_signup", json);
        setTranslationLanguageToPref(languagePreference, VIEW_LESS, DEFAULT_VIEW_LESS, "view_less", json);
        setTranslationLanguageToPref(languagePreference, ALREADY_MEMBER, DEFAULT_ALREADY_MEMBER, "already_member", json);
        setTranslationLanguageToPref(languagePreference, SUBSCRIPTION_COMPLETED, DEFAULT_SUBSCRIPTION_COMPLETED, "subscription_completed", json);

        setTranslationLanguageToPref(languagePreference, ACTIAVTE_PLAN_TITLE, DEFAULT_ACTIAVTE_PLAN_TITLE, "activate_plan_title", json);
        setTranslationLanguageToPref(languagePreference, APP_NO_LONGER_ACTIVE, DEFAULT_APP_NO_LONGER_ACTIVE, "app_expired", json);

        setTranslationLanguageToPref(languagePreference, TRANSACTION_STATUS_ACTIVE, "", "transaction_status_active", json);
        setTranslationLanguageToPref(languagePreference, ADD_TO_FAV, "", "add_to_fav", json);
        setTranslationLanguageToPref(languagePreference, ADDED_TO_FAV, "", "added_to_fav", json);
        setTranslationLanguageToPref(languagePreference, ENTER_EMPTY_FIELD, DEFAULT_ENTER_EMPTY_FIELD, "enter_register_fields_data", json);

        setTranslationLanguageToPref(languagePreference, ADVANCE_PURCHASE, DEFAULT_ADVANCE_PURCHASE, "advance_purchase", json);
        setTranslationLanguageToPref(languagePreference, ALERT, DEFAULT_ALERT, "alert", json);
        //setTranslationLanguageToPref(languagePreference, GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN, "google_fcm_token", json);

        setTranslationLanguageToPref(languagePreference, EPISODE_TITLE, DEFAULT_EPISODE_TITLE, "episodes_title", json);
        //setTranslationLanguageToPref(languagePreference, GMAIL_SIGNIN, DEFAULT_GMAIL_SIGNIN, "gmail_signin", json);
        setTranslationLanguageToPref(languagePreference, SORT_ALPHA_A_Z, DEFAULT_SORT_ALPHA_A_Z, "sort_alpha_a_z", json);
        setTranslationLanguageToPref(languagePreference, SORT_ALPHA_Z_A, DEFAULT_SORT_ALPHA_Z_A, "sort_alpha_z_a", json);
        setTranslationLanguageToPref(languagePreference, LOGIN_FACEBOOK, DEFAULT_LOGIN_FACEBOOK, "login_facebook", json);
        setTranslationLanguageToPref(languagePreference, REGISTER_FACEBOOK, DEFAULT_REGISTER_FACEBOOK, "register_facebook", json);
        setTranslationLanguageToPref(languagePreference, AMOUNT, DEFAULT_AMOUNT, "amount", json);
        setTranslationLanguageToPref(languagePreference, COUPON_CANCELLED, DEFAULT_COUPON_CANCELLED, "coupon_cancelled", json);
        setTranslationLanguageToPref(languagePreference, BUTTON_APPLY, DEFAULT_BUTTON_APPLY, "btn_apply", json);
        setTranslationLanguageToPref(languagePreference, CHK_OVER_18, DEFAULT_CHK_OVER_18, "chk_over_18", json);

        setTranslationLanguageToPref(languagePreference, SIGN_OUT_WARNING, DEFAULT_SIGN_OUT_WARNING, "sign_out_warning", json);
        setTranslationLanguageToPref(languagePreference, DISCOUNT_ON_COUPON, DEFAULT_DISCOUNT_ON_COUPON, "discount_on_coupon", json);
        setTranslationLanguageToPref(languagePreference, MY_LIBRARY, DEFAULT_MY_LIBRARY, "my_library", json);
        setTranslationLanguageToPref(languagePreference, CREDIT_CARD_CVV_HINT, DEFAULT_CREDIT_CARD_CVV_HINT, "credit_card_cvv_hint", json);
        setTranslationLanguageToPref(languagePreference, CAST, "", "cast", json);
        setTranslationLanguageToPref(languagePreference, CAST_CREW_BUTTON_TITLE, DEFAULT_CAST_CREW_BUTTON_TITLE, "cast_crew_button_title", json);
        setTranslationLanguageToPref(languagePreference, CENSOR_RATING, "", "censor_rating", json);

        if (json.optString("change_password").trim() == null || json.optString("change_password").trim().equals("")) {
            setTranslationLanguageToPref(languagePreference, CHANGE_PASSWORD, DEFAULT_CHANGE_PASSWORD, "change_password", json);
        } else {
            setTranslationLanguageToPref(languagePreference, CHANGE_PASSWORD, DEFAULT_CHANGE_PASSWORD, "change_password", json);
        }
        setTranslationLanguageToPref(languagePreference, ACCESS_PERIOD_EXPIRED, DEFAULT_ACCESS_PERIOD_EXPIRED, "access_period_expired", json);
        setTranslationLanguageToPref(languagePreference, CANCEL_BUTTON, DEFAULT_CANCEL_BUTTON, "btn_cancel", json);
        setTranslationLanguageToPref(languagePreference, RESUME_MESSAGE, DEFAULT_RESUME_MESSAGE, "resume_watching", json);
        setTranslationLanguageToPref(languagePreference, CONTINUE_BUTTON, DEFAULT_CONTINUE_BUTTON, "continue", json);
        setTranslationLanguageToPref(languagePreference, CONTACT_US, DEFAULT_CONTACT_US, "contact_us", json);


        setTranslationLanguageToPref(languagePreference, ENTER_VOUCHER_CODE, DEFAULT_ENTER_VOUCHER_CODE, "enter_voucher_code", json);
        setTranslationLanguageToPref(languagePreference, CONFIRM_PASSWORD, DEFAULT_CONFIRM_PASSWORD, "confirm_password", json);
        setTranslationLanguageToPref(languagePreference, CREDIT_CARD_DETAILS, DEFAULT_CREDIT_CARD_DETAILS, "credit_card_detail", json);
        setTranslationLanguageToPref(languagePreference, DIRECTOR, "", "director", json);
        setTranslationLanguageToPref(languagePreference, DOWNLOAD_BUTTON_TITLE, DEFAULT_DOWNLOAD_BUTTON_TITLE, "download_button_title", json);
        setTranslationLanguageToPref(languagePreference, DESCRIPTION, "", "description", json);
        setTranslationLanguageToPref(languagePreference, HOME, DEFAULT_HOME, "home", json);

        setTranslationLanguageToPref(languagePreference, EMAIL_EXISTS, DEFAULT_EMAIL_EXISTS, "email_exists", json);
        setTranslationLanguageToPref(languagePreference, RESUME, DEFAULT_RESUME, "resume", json);
        setTranslationLanguageToPref(languagePreference, EMAIL_DOESNOT_EXISTS, DEFAULT_EMAIL_DOESNOT_EXISTS, "email_does_not_exist", json);
        setTranslationLanguageToPref(languagePreference, EMAIL_PASSWORD_INVALID, DEFAULT_EMAIL_PASSWORD_INVALID, "email_password_invalid", json);
        setTranslationLanguageToPref(languagePreference, COUPON_CODE_HINT, DEFAULT_COUPON_CODE_HINT, "coupon_code_hint", json);
        setTranslationLanguageToPref(languagePreference, SEARCH_ALERT, DEFAULT_SEARCH_ALERT, "search_alert", json);

        setTranslationLanguageToPref(languagePreference, CREDIT_CARD_NUMBER_HINT, DEFAULT_CREDIT_CARD_NUMBER_HINT, "credit_card_number_hint", json);
        setTranslationLanguageToPref(languagePreference, TEXT_EMIAL, DEFAULT_TEXT_EMIAL, "text_email", json);
        setTranslationLanguageToPref(languagePreference, NAME_HINT, DEFAULT_NAME_HINT, "name_hint", json);
        setTranslationLanguageToPref(languagePreference, CREDIT_CARD_NAME_HINT, DEFAULT_CREDIT_CARD_NAME_HINT, "credit_card_name_hint", json);
        setTranslationLanguageToPref(languagePreference, TEXT_PASSWORD, DEFAULT_TEXT_PASSWORD, "text_password", json);

        setTranslationLanguageToPref(languagePreference, ERROR_IN_PAYMENT_VALIDATION, DEFAULT_ERROR_IN_PAYMENT_VALIDATION, "error_in_payment_validation", json);
        setTranslationLanguageToPref(languagePreference, ERROR_IN_SUBSCRIPTION, DEFAULT_ERROR_IN_SUBSCRIPTION, "error_in_subscription", json);
        setTranslationLanguageToPref(languagePreference, ERROR_TRANSACTION_PROCESS, DEFAULT_ERROR_TRANSACTION_PROCESS, "error_transc_process", json);
        setTranslationLanguageToPref(languagePreference, ERROR_IN_REGISTRATION, DEFAULT_ERROR_IN_REGISTRATION, "error_in_registration", json);
        setTranslationLanguageToPref(languagePreference, TRANSACTION_STATUS_EXPIRED, "", "transaction_status_expired", json);
        setTranslationLanguageToPref(languagePreference, DETAILS_NOT_FOUND_ALERT, DEFAULT_DETAILS_NOT_FOUND_ALERT, "details_not_found_alert", json);

        setTranslationLanguageToPref(languagePreference, FAILURE, DEFAULT_FAILURE, "failure", json);
        setTranslationLanguageToPref(languagePreference, FILTER_BY, DEFAULT_FILTER_BY, "filter_by", json);
        setTranslationLanguageToPref(languagePreference, FORGOT_PASSWORD, DEFAULT_FORGOT_PASSWORD, "forgot_password", json);
        setTranslationLanguageToPref(languagePreference, GENRE, "", "genre", json);

        setTranslationLanguageToPref(languagePreference, AGREE_TERMS, DEFAULT_AGREE_TERMS, "agree_terms", json);
        setTranslationLanguageToPref(languagePreference, ENTER_REVIEW_HERE, DEFAULT_ENTER_REVIEW_HERE, "enter_review_here", json);
        setTranslationLanguageToPref(languagePreference, TO_LOGIN, DEFAULT_TO_LOGIN, "to_login", json);
        setTranslationLanguageToPref(languagePreference, CLICK_HERE, DEFAULT_CLICK_HERE, "click_here", json);
        setTranslationLanguageToPref(languagePreference, NEED_LOGIN_TO_REVIEW, DEFAULT_NEED_LOGIN_TO_REVIEW, "need_to_login", json);
        setTranslationLanguageToPref(languagePreference, INVALID_COUPON, DEFAULT_INVALID_COUPON, "invalid_coupon", json);
        setTranslationLanguageToPref(languagePreference, INVOICE, DEFAULT_INVOICE, "invoice", json);
        setTranslationLanguageToPref(languagePreference, LANGUAGE_POPUP_LANGUAGE, DEFAULT_LANGUAGE_POPUP_LANGUAGE, "language_popup_language", json);
        setTranslationLanguageToPref(languagePreference, SORT_LAST_UPLOADED, DEFAULT_SORT_LAST_UPLOADED, "sort_last_uploaded", json);

        setTranslationLanguageToPref(languagePreference, LANGUAGE_POPUP_LOGIN, DEFAULT_LANGUAGE_POPUP_LOGIN, "language_popup_login", json);
        setTranslationLanguageToPref(languagePreference, LOGIN, DEFAULT_LOGIN, "login", json);
        setTranslationLanguageToPref(languagePreference, NO_RESULT_FOUND_REFINE_YOUR_SEARCH, DEFAULT_NO_RESULT_FOUND_REFINE_YOUR_SEARCH, "no_result_found_refine_your_search", json);
        setTranslationLanguageToPref(languagePreference, FIRST_NAME, DEFAULT_FIRST_NAME, "first_name", json);
        setTranslationLanguageToPref(languagePreference, LAST_NAME, DEFAULT_LAST_NAME, "last_name", json);
        setTranslationLanguageToPref(languagePreference, LOGOUT, DEFAULT_LOGOUT, "logout", json);
        setTranslationLanguageToPref(languagePreference, LOGOUT_SUCCESS, DEFAULT_LOGOUT_SUCCESS, "logout_success", json);
        setTranslationLanguageToPref(languagePreference, MY_FAVOURITE, DEFAULT_MY_FAVOURITE, "my_favourite", json);
        setTranslationLanguageToPref(languagePreference, DOWNLOAD_CANCELED, DEFAULT_DOWNLOAD_CANCELED, "download_cancelled", json);

        setTranslationLanguageToPref(languagePreference, NEW_PASSWORD, DEFAULT_NEW_PASSWORD, "new_password", json);
        setTranslationLanguageToPref(languagePreference, NEW_HERE_TITLE, DEFAULT_NEW_HERE_TITLE, "new_here_title", json);
        setTranslationLanguageToPref(languagePreference, NO, DEFAULT_NO, "no", json);
        setTranslationLanguageToPref(languagePreference, NO_DATA, DEFAULT_NO_DATA, "no_data", json);
        setTranslationLanguageToPref(languagePreference, NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION, "no_internet_connection", json);
        setTranslationLanguageToPref(languagePreference, ENTER_REGISTER_FIELDS_DATA, DEFAULT_ENTER_REGISTER_FIELDS_DATA, "enter_register_fields_data", json);

        setTranslationLanguageToPref(languagePreference, NO_INTERNET_NO_DATA, DEFAULT_NO_INTERNET_NO_DATA, "no_internet_no_data", json);
        setTranslationLanguageToPref(languagePreference, NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE, "no_details_available", json);
        setTranslationLanguageToPref(languagePreference, BUTTON_OK, DEFAULT_BUTTON_OK, "btn_ok", json);
        setTranslationLanguageToPref(languagePreference, OLD_PASSWORD, DEFAULT_OLD_PASSWORD, "old_password", json);
        setTranslationLanguageToPref(languagePreference, OOPS_INVALID_EMAIL, DEFAULT_OOPS_INVALID_EMAIL, "oops_invalid_email", json);

        setTranslationLanguageToPref(languagePreference, ORDER, DEFAULT_ORDER, "order", json);
        setTranslationLanguageToPref(languagePreference, TRANSACTION_DETAILS_ORDER_ID, "", "transaction_detail_order_id", json);
        setTranslationLanguageToPref(languagePreference, PASSWORD_RESET_LINK, DEFAULT_PASSWORD_RESET_LINK, "password_reset_link", json);
        setTranslationLanguageToPref(languagePreference, PASSWORDS_DO_NOT_MATCH, DEFAULT_PASSWORDS_DO_NOT_MATCH, "password_donot_match", json);
        setTranslationLanguageToPref(languagePreference, PAY_BY_PAYPAL, "", "pay_by_paypal", json);

        setTranslationLanguageToPref(languagePreference, BTN_PAYNOW, "", "btn_paynow", json);
        setTranslationLanguageToPref(languagePreference, PAY_WITH_CREDIT_CARD, "", "pay_with_credit_card", json);
        setTranslationLanguageToPref(languagePreference, PAYMENT_OPTIONS_TITLE, DEFAULT_PAYMENT_OPTIONS_TITLE, "payment_options_title", json);
        setTranslationLanguageToPref(languagePreference, PLAN_NAME, DEFAULT_PLAN_NAME, "plan_name", json);
        setTranslationLanguageToPref(languagePreference, PLAN_NAME, DEFAULT_PLAN_NAME, "plan_name", json);
        setTranslationLanguageToPref(languagePreference, ACTIVATE_SUBSCRIPTION_WATCH_VIDEO, DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO, "activate_subscription_watch_video", json);

        setTranslationLanguageToPref(languagePreference, COUPON_ALERT, "", "coupon_alert", json);
        setTranslationLanguageToPref(languagePreference, FREE_FOR_COUPON, DEFAULT_FREE_FOR_COUPON, "free_for_coupon", json);
        setTranslationLanguageToPref(languagePreference, VALID_CONFIRM_PASSWORD, "", "valid_confirm_password", json);
        setTranslationLanguageToPref(languagePreference, BTN_DISCARD, DEFAULT_BTN_DISCARD, "btn_discard", json);
        setTranslationLanguageToPref(languagePreference, BTN_KEEP, DEFAULT_BTN_KEEP, "btn_keep", json);
        setTranslationLanguageToPref(languagePreference, DOWNLOAD_CANCEL, DEFAULT_DOWNLOAD_CANCEL, "download_cancelled", json);
        setTranslationLanguageToPref(languagePreference, WANT_DOWNLOAD_CANCEL, DEFAULT_WANT_DOWNLOAD_CANCEL, "want_download_cancel", json);
        setTranslationLanguageToPref(languagePreference, TRANSACTION_ORDER_ID, DEFAULT_TRANSACTION_ORDER_ID, "transaction_detail_order_id", json);
        setTranslationLanguageToPref(languagePreference, ADD_A_REVIEW, DEFAULT_ADD_A_REVIEW, "add_a_review", json);
        setTranslationLanguageToPref(languagePreference, REVIEWS, DEFAULT_REVIEWS, "reviews", json);
        setTranslationLanguageToPref(languagePreference, VALID_CONFIRM_PASSWORD, DEFAULT_VALID_CONFIRM_PASSWORD, "valid_confirm_password", json);
        setTranslationLanguageToPref(languagePreference, PROFILE, DEFAULT_PROFILE, "profile", json);
        setTranslationLanguageToPref(languagePreference, BUTTON_RESET, DEFAULT_BUTTON_RESET, "btn_reset", json);
        setTranslationLanguageToPref(languagePreference, PROFILE_UPDATED, DEFAULT_PROFILE_UPDATED, "profile_updated", json);


        setTranslationLanguageToPref(languagePreference, PURCHASE, DEFAULT_PURCHASE, "purchase", json);
        setTranslationLanguageToPref(languagePreference, TRANSACTION_DETAIL_PURCHASE_DATE, DEFAULT_TRANSACTION_DETAIL_PURCHASE_DATE, "transaction_detail_purchase_date", json);
        setTranslationLanguageToPref(languagePreference, PURCHASE_HISTORY, DEFAULT_PURCHASE_HISTORY, "purchase_history", json);
        setTranslationLanguageToPref(languagePreference, NO_PURCHASE_HISTORY, DEFAULT_NO_PURCHASE_HISTORY, "no_purchase_history", json);
        setTranslationLanguageToPref(languagePreference, MY_DOWNLOAD, DEFAULT_MY_DOWNLOAD, "my_download", json);
        setTranslationLanguageToPref(languagePreference, BTN_REGISTER, DEFAULT_BTN_REGISTER, "btn_register", json);
        setTranslationLanguageToPref(languagePreference, SORT_RELEASE_DATE, DEFAULT_SORT_RELEASE_DATE, "sort_release_date", json);

        setTranslationLanguageToPref(languagePreference, SAVE_THIS_CARD, DEFAULT_SAVE_THIS_CARD, "save_this_card", json);
        setTranslationLanguageToPref(languagePreference, TEXT_SEARCH_PLACEHOLDER, DEFAULT_TEXT_SEARCH_PLACEHOLDER, "text_search_placeholder", json);
        setTranslationLanguageToPref(languagePreference, SEASON, DEFAULT_SEASON, "season", json);
        setTranslationLanguageToPref(languagePreference, SELECT_OPTION_TITLE, "", "select_option_title", json);
        setTranslationLanguageToPref(languagePreference, SELECT_PLAN, DEFAULT_SELECT_PLAN, "select_plan", json);
        setTranslationLanguageToPref(languagePreference, NO_DOWNLOADED_VIDEOS, DEFAULT_NO_DOWNLOADED_VIDEOS, "no_downloaded_videos_available", json);

        setTranslationLanguageToPref(languagePreference, SIGN_UP_TITLE, DEFAULT_SIGN_UP_TITLE, "signup_title", json);
        setTranslationLanguageToPref(languagePreference, DOWNLOAD_COMPLETED, DEFAULT_SIGN_UP_TITLE, "download_completed", json);
        setTranslationLanguageToPref(languagePreference, SLOW_INTERNET_CONNECTION, DEFAULT_SLOW_INTERNET_CONNECTION, "slow_internet_connection", json);
        setTranslationLanguageToPref(languagePreference, SLOW_ISSUE_INTERNET_CONNECTION, "", "slow_issue_internet_connection", json);
        setTranslationLanguageToPref(languagePreference, SORRY, DEFAULT_SORRY, "sorry", json);
        setTranslationLanguageToPref(languagePreference, DOWNLOADED_ACCESS_EXPIRED, DEFAULT_DOWNLOADED_ACCESS_EXPIRED, "downloaded_access_expired", json);
        setTranslationLanguageToPref(languagePreference, GEO_BLOCKED_ALERT, DEFAULT_GEO_BLOCKED_ALERT, "geo_blocked_alert", json);
        setTranslationLanguageToPref(languagePreference, ERROR_IN_DATA_FETCHING, DEFAULT_ERROR_IN_DATA_FETCHING, "error_data_fetching", json);

        setTranslationLanguageToPref(languagePreference, SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR, "sign_out_error", json);
        setTranslationLanguageToPref(languagePreference, ALREADY_PURCHASE_THIS_CONTENT, DEFAULT_ALREADY_PURCHASE_THIS_CONTENT, "already_purchase_this_content", json);
        setTranslationLanguageToPref(languagePreference, CROSSED_MAXIMUM_LIMIT, DEFAULT_CROSSED_MAXIMUM_LIMIT, "crossed_max_limit_of_watching", json);
        setTranslationLanguageToPref(languagePreference, SORT_BY, DEFAULT_SORT_BY, "sort_by", json);
        setTranslationLanguageToPref(languagePreference, STORY_TITLE, "", "story_title", json);

        setTranslationLanguageToPref(languagePreference, BTN_SUBMIT, DEFAULT_BTN_SUBMIT, "btn_submit", json);
        setTranslationLanguageToPref(languagePreference, TRANSACTION_STATUS, DEFAULT_TRANSACTION_STATUS, "transaction_success", json);
        setTranslationLanguageToPref(languagePreference, VIDEO_ISSUE, DEFAULT_VIDEO_ISSUE, "video_issue", json);
        setTranslationLanguageToPref(languagePreference, NO_CONTENT, DEFAULT_NO_CONTENT, "no_content", json);
        setTranslationLanguageToPref(languagePreference, NO_VIDEO_AVAILABLE, DEFAULT_NO_VIDEO_AVAILABLE, "no_video_available", json);

        setTranslationLanguageToPref(languagePreference, CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY, DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY, "content_not_available_in_your_country", json);
        setTranslationLanguageToPref(languagePreference, TRANSACTION_DATE, DEFAULT_TRANSACTION_DATE, "transaction_date", json);
        setTranslationLanguageToPref(languagePreference, TRANASCTION_DETAIL, DEFAULT_TRANASCTION_DETAIL, "transaction_detail", json);
        setTranslationLanguageToPref(languagePreference, TRANSACTION_STATUS, DEFAULT_TRANSACTION_STATUS, "transaction_status", json);
        setTranslationLanguageToPref(languagePreference, TRANSACTION, DEFAULT_TRANSACTION_TITLE, "transaction", json);

        setTranslationLanguageToPref(languagePreference, TRY_AGAIN, DEFAULT_TRY_AGAIN, "try_again", json);
        setTranslationLanguageToPref(languagePreference, UNPAID, "", "unpaid", json);
        setTranslationLanguageToPref(languagePreference, USE_NEW_CARD, DEFAULT_USE_NEW_CARD, "use_new_card", json);
        setTranslationLanguageToPref(languagePreference, VIEW_MORE, DEFAULT_VIEW_MORE, "view_more", json);
        setTranslationLanguageToPref(languagePreference, VIEW_ALL, DEFAULT_VIEW_ALL, "viewall", json);
        setTranslationLanguageToPref(languagePreference, VIEW_TRAILER, DEFAULT_VIEW_TRAILER, "view_trailer", json);

        setTranslationLanguageToPref(languagePreference, WATCH, "", "watch", json);
        setTranslationLanguageToPref(languagePreference, WATCH_NOW, DEFAULT_WATCH_NOW, "watch_now", json);
        setTranslationLanguageToPref(languagePreference, SIGN_OUT_ALERT, "", "sign_out_alert", json);
        setTranslationLanguageToPref(languagePreference, UPDATE_PROFILE_ALERT, DEFAULT_UPDATE_PROFILE_ALERT, "update_profile_alert", json);
        setTranslationLanguageToPref(languagePreference, YES, DEFAULT_YES, "yes", json);

        setTranslationLanguageToPref(languagePreference, PURCHASE_SUCCESS_ALERT, DEFAULT_PURCHASE_SUCCESS_ALERT, "purchase_success_alert", json);
        setTranslationLanguageToPref(languagePreference, CARD_WILL_CHARGE, DEFAULT_CARD_WILL_CHARGE, "card_will_charge", json);
        setTranslationLanguageToPref(languagePreference, SEARCH_HINT, "", "search_hint", json);
        setTranslationLanguageToPref(languagePreference, TERMS, DEFAULT_TERMS, "terms", json);
        setTranslationLanguageToPref(languagePreference, UPDATE_PROFILE, DEFAULT_UPDATE_PROFILE, "btn_update_profile", json);

        setTranslationLanguageToPref(languagePreference, APP_ON, DEFAULT_APP_ON, "app_on", json);
        setTranslationLanguageToPref(languagePreference, APP_SELECT_LANGUAGE, DEFAULT_APP_SELECT_LANGUAGE, "app_select_language", json);
        setTranslationLanguageToPref(languagePreference, FILL_FORM_BELOW, DEFAULT_FILL_FORM_BELOW, "Fill_form_below", json);


        setTranslationLanguageToPref(languagePreference, DETAILS_TITLE, DEFAULT_DETAILS_TITLE, "details_title", json);
        setTranslationLanguageToPref(languagePreference, BENEFIT_TITLE, DEFAULT_BENEFIT_TITLE, "benefit_title", json);
        setTranslationLanguageToPref(languagePreference, DIFFICULTY_TITLE, DEFAULT_DIFFICULTY_TITLE, "difficulty_title", json);
        setTranslationLanguageToPref(languagePreference, DURATION_TITLE, DEFAULT_DURATION_TITLE, "duration_title", json);

        setTranslationLanguageToPref(languagePreference, SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE, DEFAULT_SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE, "simultaneous_logout_message", json);
        setTranslationLanguageToPref(languagePreference, LOGIN_STATUS_MESSAGE, DEFAULT_LOGIN_STATUS_MESSAGE, "login_status_message", json);
        setTranslationLanguageToPref(languagePreference, FILL_FORM_BELOW, DEFAULT_FILL_FORM_BELOW, "fill_form_below", json);
        setTranslationLanguageToPref(languagePreference, MESSAGE, DEFAULT_MESSAGE, "text_message", json);
        setTranslationLanguageToPref(languagePreference, MANAGE_DEVICE, DEFAULT_MANAGE_DEVICE, "manage_device", json);

        setTranslationLanguageToPref(languagePreference, YOUR_DEVICE, DEFAULT_YOUR_DEVICE, "your_device", json);
        setTranslationLanguageToPref(languagePreference, DEREGISTER, DEFAULT_DEREGISTER, "deregister", json);
        setTranslationLanguageToPref(languagePreference, FILMOGRAPHY, DEFAULT_FILMOGRAPHY, "filmography", json);
        setTranslationLanguageToPref(languagePreference, ENTER_YOUR_MESSAGE, DEFAULT_ENTER_YOUR_MESSAGE, "text_message_placeholder", json);
        setTranslationLanguageToPref(languagePreference, NO_DEVICE_AVAILABE, DEFAULT_NO_DEVICE_AVAILABE, "no_devices_available", json);
        setTranslationLanguageToPref(languagePreference, REMOVE_DEVICE_SUCCESS, DEFAULT_REMOVE_DEVICE_SUCCESS, "remove_device_request_succ", json);

        setTranslationLanguageToPref(languagePreference, SAVE, DEFAULT_SAVE, "btn_save", json);
        setTranslationLanguageToPref(languagePreference, SAVE_OFFLINE_VIDEO, DEFAULT_SAVE_OFFLINE_VIDEO, "save_offline_video", json);
        setTranslationLanguageToPref(languagePreference, DELETE_BTN, DEFAULT_DELETE_BTN, "delete_btn", json);
        setTranslationLanguageToPref(languagePreference, SEND, DEFAULT_SEND, "btn_send", json);
        setTranslationLanguageToPref(languagePreference, CONFIRM_DELETE_MESSAGE, DEFAULT_CONFIRM_DELETE_MESSAGE, "confirm_delete_message", json);
        setTranslationLanguageToPref(languagePreference, WATCH_HISTORY, DEFAULT_WATCH_HISTORY, "watch_history", json);
        languagePreference.setLanguageSharedPrefernce(SELECTED_LANGUAGE_CODE, default_Language);

    }

    /**
     * This method is used to set translation language from json keyword.
     * @param languagePreference
     * @param lanngKey
     * @param langValue
     * @param jsonKey
     * @param jsonObject
     */
    private static void setTranslationLanguageToPref(LanguagePreference languagePreference, String lanngKey,
                                                     String langValue, String jsonKey, JSONObject jsonObject) {
        if (jsonObject.has(jsonKey) && !(jsonObject.optString(jsonKey).trim()).equals(""))
            languagePreference.setLanguageSharedPrefernce(lanngKey, jsonObject.optString(jsonKey).trim());
        else
            languagePreference.setLanguageSharedPrefernce(lanngKey, langValue);

    }


    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static int getDPI(Context _context) {

        int screen_Info=0;

        int density = _context.getResources().getDisplayMetrics().densityDpi;
        float density1 = _context.getResources().getDisplayMetrics().density;

        Activity act = (Activity) _context;
        Display display = act.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float dpHeight = outMetrics.heightPixels / density1;
        float dpWidth = outMetrics.widthPixels / density1;
        LogUtil.showLog("Login", "height-" + dpHeight + ",Width:-" + dpWidth);
        switch (density) {
            case DisplayMetrics.DENSITY_LOW: {
                LogUtil.showLog("Login", "LDPI height-" + dpHeight + ",Width:-" + dpWidth);
                screen_Info=DisplayMetrics.DENSITY_LOW;
            }
            break;
            case DisplayMetrics.DENSITY_MEDIUM: {
                LogUtil.showLog("Login", "MDPI height-" + dpHeight + ",Width:-" + dpWidth);
                screen_Info=DisplayMetrics.DENSITY_MEDIUM;

            }
            break;
            case DisplayMetrics.DENSITY_HIGH: {
                LogUtil.showLog("Login", "HDPI height-" + dpHeight + ",Width:-" + dpWidth);
                screen_Info=DisplayMetrics.DENSITY_HIGH;

            }
            break;
            case DisplayMetrics.DENSITY_XHIGH: {
                LogUtil.showLog("Login", "XHDPI height-" + dpHeight + ",Width:-" + dpWidth);
                screen_Info=DisplayMetrics.DENSITY_XHIGH;

            }
            break;
            case DisplayMetrics.DENSITY_XXHIGH: {
                LogUtil.showLog("Login", "XXHDPI height-" + dpHeight + ",Width:-" + dpWidth);
                screen_Info=DisplayMetrics.DENSITY_XXHIGH;

            }
            break;
            case DisplayMetrics.DENSITY_XXXHIGH: {
                LogUtil.showLog("Login", "XXXHDPI height-" + dpHeight + ",Width:-" + dpWidth);
                screen_Info=DisplayMetrics.DENSITY_XXXHIGH;

            }
            break;
            case DisplayMetrics.DENSITY_TV: {
                LogUtil.showLog("Login", "TVDPI height-" + dpHeight + ",Width:-" + dpWidth);
                screen_Info=DisplayMetrics.DENSITY_TV;

            }
            break;
        }
        return screen_Info;
    }


    public static boolean getStreamingRestriction(LanguagePreference languagePreference) {
        return languagePreference.getTextofLanguage(IS_STREAMING_RESTRICTION, DEFAULT_IS_IS_STREAMING_RESTRICTION).equals("1");
    }

    public static void hideKeyboard(Context context) {
        Activity act = (Activity) context;
        InputMethodManager inputManager = (InputMethodManager)
                act.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * This method will return a string format text which comes form API end , if it contains any html contnet.
     *
     * @param input
     * @return
     */
    public static String getTextViewTextFromApi(String input) {

        return "" + (input.replace("\\r\\n", "<br>").replace("\\n", "<br>").replace("\\", ""));

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         float reqWidth, float reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, float reqWidth, float reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawable badge;
        // Reuse drawable if possible

        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }



}

