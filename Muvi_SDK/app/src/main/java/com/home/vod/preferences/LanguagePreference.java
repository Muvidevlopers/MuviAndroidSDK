package com.home.vod.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alok.acharya on 31/7/17.
 */

public class LanguagePreference {
    private static final String LANGUAGE_SHARED_PRE = "MuviLanguage";
    private SharedPreferences languageSharedPref;
    private SharedPreferences.Editor mEditor;
    private static LanguagePreference languagePreference;


    //********Notification***********
    public static String NO_NOTIFICATION = "NO_NOTIFICATION";
    public static String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";
    public static  String DEFAULT_NO_NOTIFICATION = "No notification Sent.";
    public static  String DEFAULT_NOTIFICATION_TITLE = "Notification (s)";

    public static final String NOTIFICATION = "NOTIFICATION";
    public static final String DEFAULT_NOTIFICATION = "Notification";

    /**
     * Language Constant default Key
     */
    public static String INVALID_PHONE_NUMBER = "OOPS_INVALID_PHONE_NUMBER";
    public static String DEFAULT_INVALID_PHONE_NUMBER = "Oops ! Invalid Number";
    public static String MOBILE = "MOBILE";
    public static String DEFAULT_MOBILE = "Enter Mobile Number";
    public static String DEFAULT_FIRST_NAME = "First Name";
    public static String DEFAULT_LAST_NAME = "Last Name";
    public static String FIRST_NAME = "FIRST_NAME";
    public static String LAST_NAME = "LAST_NAME";
    public static String DEFAULT_DELETE_BTN = "Delete";
    public static final String LOGIN_FACEBOOK = "LOGIN_FACEBOOK";
    public static final String DEFAULT_LOGIN_FACEBOOK = "Login with Facebook";
    public static final String DEFAULT_REGISTER_FACEBOOK = "Register with Facebook";
    public static final String SIGN_UP_WITH_EMAIL = "SIGN_UP_WITH_EMAIL";
    public static final String DEFAULT_SIGN_UP_WITH_EMAIL = "Sign Up with Email";
    public static final String REGISTER_FACEBOOK = "REGISTER_FACEBOOK";
    public static String DELETE_BTN = "DELETE_BTN";
    public static String DEFAULT_WANT_TO_DELETE = "Want to Delete";
    public static String WANT_TO_DELETE = "WANT_TO_DELETE";
    public static String MY_DOWNLOAD = "MY_DOWNLOAD";
    public static String DEFAULT_MY_DOWNLOAD = "My Download";
    public static String FREE_FOR_COUPON = "FREE_FOR_COUPON";
    public static final String TO_LOGIN = "TO_LOGIN";
    public static final String CLICK_HERE = "CLICK_HERE";
    public static final String SUBMIT_YOUR_RATING_TITLE = "SUBMIT_YOUR_RATING_TITLE";
    public static final String VIEW_LESS = "VIEW_LESS";
    public static final String IS_MYLIBRARY = "IS_MYLIBRARY";
    public static final String IS_RESTRICT_DEVICE = "IS_RESTRICT_DEVICE";
    public static final String IS_ONE_STEP_REGISTRATION = "IS_ONE_STEP_REGISTRATION";
    public static final String SELECTED_LANGUAGE_CODE = "SELECTED_LANGUAGE_CODE";
    public static final String SEARCH_PLACEHOLDER = "SEARCH_PLACEHOLDER";
    public static final String VIEW_TRAILER = "VIEW_TRAILER";
    public static final String WATCH_NOW = "WATCH_NOW";
    public static final String WATCH = "WATCH";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String GENRE = "GENRE";
    public static final String CENSOR_RATING = "CENSOR_RATING";
    public static final String CAST = "CAST";
    public static final String DIRECTOR = "DIRECTOR";
    public static final String VIEW_MORE = "VIEW_MORE";
    public static final String VIEW_ALL = "VIEW_ALL";
    public static final String FILTER_BY = "FILTER_BY";
    public static final String SORT_BY = "SORT_BY";
    public static final String FORGOT_PASSWORD = "FORGOT_PASSWORD";
    public static final String LOGIN = "LOGIN";
    public static final String CONFIRM_PASSWORD = "CONFIRM_PASSWORD";
    public static final String UPDATE_PROFILE = "UPDATE_PROFILE";
    public static final String APP_ON = "APP_ON";
    public static final String APP_SELECT_LANGUAGE = "APP_SELECT_LANGUAGE";
    public static final String PROFILE = "PROFILE";
    public static final String PURCHASE_HISTORY = "PURCHASE_HISTORY";
    public static final String LOGOUT = "LOGOUT";
    public static final String CHANGE_PASSWORD = "Change Password";
    public static final String TRANSACTION = "TRANSACTION";
    public static final String INVOICE = "INVOICE";
    public static final String TRANSACTION_DATE = "TRANSACTION_DATE";
    public static final String ORDER = "ORDER";
    public static final String AMOUNT = "AMOUNT";
    public static final String TRANSACTION_STATUS = "TRANSACTION_STATUS";
    public static final String PLAN_NAME = "PLAN_NAME";
    public static final String SEASON = "SEASON";
    public static String NEXT = "NEXT";
    public static String SELECT_PURCHASE_TYPE = "SELECT_PURCHASE_TYPE";
    public static String COMPLETE_SEASON = "COMPLETE_SEASON";
    public static final String SELECT_PLAN = "SELECT_PLAN";
    public static final String SKIP_BUTTON_TITLE = "SKIP_BUTTON_TITLE";
    public static final String PURCHASE = "PURCHASE";
    public static String ENTER_VOUCHER_CODE = "ENTER_VOUCHER_CODE";
    public static String VOUCHER_SUCCESS = "VOUCHER_SUCCESS";
    public static final String CREDIT_CARD_DETAILS = "CREDIT_CARD_DETAILS";
    public static final String CARD_WILL_CHARGE = "CARD_WILL_CHARGE";
    public static final String SAVE_THIS_CARD = "SAVE_THIS_CARD";
    public static final String USE_NEW_CARD = "USE_NEW_CARD";
    public static final String BUTTON_APPLY = "BUTTON_APPLY";
    public static final String BUTTON_RESET = "BUTTON_RESET";
    public static final String DEFAULT_BUTTON_RESET = "Reset";
    public static String CHK_OVER_18 = "CHK_OVER_18";
    public static String DEFAULT_CHK_OVER_18 = "By clicking on Register, I agree to";
    public static final String BUTTON_OK = "BUTTON_OK";
    public static final String AGREE_TERMS = "AGREE_TERMS";
    public static final String TERMS = "TERMS";
    public static final String OOPS_INVALID_EMAIL = "OOPS_INVALID_EMAIL";
    public static final String VALID_CONFIRM_PASSWORD = "VALID_CONFIRM_PASSWORD";
    public static final String PASSWORDS_DO_NOT_MATCH = "PASSWORDS_DO_NOT_MATCH";
    public static final String EMAIL_EXISTS = "EMAIL_EXISTS";
    public static final String EMAIL_DOESNOT_EXISTS = "EMAIL_DOESNOT_EXISTS";
    public static final String PASSWORD_RESET_LINK = "PASSWORD_RESET_LINK";
    public static final String YES = "YES";
    public static final String NO = "NO";
    public static final String PROFILE_UPDATED = "PROFILE_UPDATED";
    public static final String INVALID_COUPON = "INVALID_COUPON";
    public static final String DISCOUNT_ON_COUPON = "DISCOUNT_ON_COUPON";
    public static final String HOME = "HOME";
    public static String HAS_FAVORITE = "HAS_FAVORITE";
    public static String DEFAULT_HAS_FAVORITE = "0";
    public static final String CONTACT_US = "contactus";
    public static final String ACTIVATE_SUBSCRIPTION_WATCH_VIDEO = "ACTIVATE_SUBSCRIPTION_WATCH_VIDEO";
    public static final String CROSSED_MAXIMUM_LIMIT = "CROSSED_MAXIMUM_LIMIT";
    public static final String ACCESS_PERIOD_EXPIRED = "ACCESS_PERIOD_EXPIRED";
    public static final String CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY = "CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY";
    public static final String ALREADY_PURCHASE_THIS_CONTENT = "ALREADY_PURCHASE_THIS_CONTENT";
    public static final String SORT_ALPHA_A_Z = "SORT_ALPHA_A_Z";
    public static final String SORT_ALPHA_Z_A = "SORT_ALPHA_Z_A";
    public static final String SORT_LAST_UPLOADED = "SORT_LAST_UPLOADED";
    public static final String SEARCH_HINT = "SEARCH_HINT";
    public static final String GEO_BLOCKED_ALERT = "GEO_BLOCKED_ALERT";
    public static final String APP_NO_LONGER_ACTIVE = "APP_NO_LONGER_ACTIVE";
    public static final String NO_INTERNET_NO_DATA = "NO_INTERNET_NO_DATA";
    public static final String TRY_AGAIN = "TRY_AGAIN";
    public static final String SLOW_INTERNET_CONNECTION = "SLOW_INTERNET_CONNECTION";
    public static final String NO_INTERNET_CONNECTION = "NO_INTERNET_CONNECTION";
    public static final String NEW_HERE_TITLE = "NEW_HERE_TITLE";
    public static final String SIGN_UP_TITLE = "SIGN_UP_TITLE";
    public static final String NAME_HINT = "NAME_HINT";
    public static final String ALREADY_MEMBER = "ALREADY_MEMBER";
    public static final String LANGUAGE_POPUP_LOGIN = "LANGUAGE_POPUP_LOGIN";
    public static final String LANGUAGE_POPUP_LANGUAGE = "LANGUAGE_POPUP_LANGUAGE";
    public static final String OLD_PASSWORD = "OLD_PASSWORD";
    public static final String NEW_PASSWORD = "NEW_PASSWORD";
    public static final String TRANSACTION_STATUS_ACTIVE = "TRANSACTION_STATUS_ACTIVE";
    public static final String TRANSACTION_STATUS_EXPIRED = "TRANSACTION_STATUS_EXPIRED";
    public static final String TRANSACTION_DETAIL_PURCHASE_DATE = "TRANSACTION_DETAIL_PURCHASE_DATE";
    public static final String DOWNLOAD_BUTTON_TITLE = "DOWNLOAD_BUTTON_TITLE";
    public static final String CAST_CREW_BUTTON_TITLE = "CAST_CREW_BUTTON_TITLE";
    public static final String EPISODE_TITLE = "EPISODE_TITLE";
    public static final String ACTIAVTE_PLAN_TITLE = "ACTIAVTE_PLAN_TITLE";
    public static final String SELECT_OPTION_TITLE = "SELECT_OPTION_TITLE";
    public static final String CREDIT_CARD_NAME_HINT = "CREDIT_CARD_NAME_HINT";
    public static final String CREDIT_CARD_NUMBER_HINT = "CREDIT_CARD_NUMBER_HINT";
    public static final String CREDIT_CARD_CVV_HINT = "CREDIT_CARD_CVV_HINT";
    public static final String COUPON_CODE_HINT = "COUPON_CODE_HINT";
    public static final String PAYMENT_OPTIONS_TITLE = "PAYMENT_OPTIONS_TITLE";
    public static final String UPDATE_PROFILE_ALERT = "UPDATE_PROFILE_ALERT";
    public static final String ALERT = "ALERT";
    public static final String DEFAULT_ALERT = "ALERT!";
    public static final String STORY_TITLE = "STORY_TITLE";
    public static final String NO_DETAILS_AVAILABLE = "NO_DETAILS_AVAILABLE";
    public static final String SORRY = "SORRY";
    public static final String NO_VIDEO_AVAILABLE = "NO_VIDEO_AVAILABLE";
    public static final String NO_DATA = "NO_DATA";
    public static final String NO_CONTENT = "NO_CONTENT";
    public static final String VIDEO_ISSUE = "VIDEO_ISSUE";
    public static final String ERROR_IN_REGISTRATION = "ERROR_IN_REGISTRATION";
    public static final String LOGOUT_SUCCESS = "LOGOUT_SUCCESS";
    public static final String PAY_WITH_CREDIT_CARD = "PAY_WITH_CREDIT_CARD";
    public static final String ENTER_EMPTY_FIELD = "ENTER_EMPTY_FIELD";

    public static final String EMAIL_PASSWORD_INVALID = "EMAIL_PASSWORD_INVALID";
    public static final String ADVANCE_PURCHASE = "ADVANCE_PURCHASE";
    public static final String FAILURE = "FAILURE";
    public static final String COUPON_CANCELLED = "COUPON_CANCELLED";
    public static final String COUPON_ALERT = "COUPON_ALERT";
    public static final String DETAILS_NOT_FOUND_ALERT = "DETAILS_NOT_FOUND_ALERT";


    public static final String UNPAID = "UNPAID";
    public static final String ERROR_IN_PAYMENT_VALIDATION = "ERROR_IN_PAYMENT_VALIDATION";
    public static final String PURCHASE_SUCCESS_ALERT = "PURCHASE_SUCCESS_ALERT";
    public static final String NO_RECORD = "NO_RECORD";

    public static final String CANCEL_BUTTON = "CANCEL_BUTTON";
    public static final String CONTINUE_BUTTON = "CONTINUE_BUTTON";

    public static final String WATCH_HISTORY = "WATCH_HISTORY";
    public static final String ADD_TO_FAV = "ADD_TO_FAV";
    public static final String ADDED_TO_FAV = "ADDED_TO_FAV";
    public static final String SIGN_OUT_WARNING = "SIGN_OUT_WARNING";
    public static final String SEARCH_ALERT = "SEARCH_ALERT";
    public static final String TEXT_EMIAL = "TEXT_EMIAL";
    public static final String TEXT_PASSWORD = "TEXT_PASSWORD";
    public static final String DOWNLOAD_CANCELED = "DOWNLOAD_CANCELED";
    public static final String DEFAULT_DOWNLOAD_CANCELED = "Download Cancelled";
    public static final String MY_FAVOURITE = "MY_FAVOURITE";
    public static final String TRANSACTION_DETAILS_ORDER_ID = "TRANSACTION_DETAILS_ORDER_ID";
    public static final String PAY_BY_PAYPAL = "PAY_BY_PAYPAL";
    public static final String BTN_PAYNOW = "BTN_PAYNOW";
    public static final String BTN_REGISTER = "BTN_REGISTER";
    public static final String GMAIL_SIGNUP = "GMAIL_SIGNUP";
    public static final String GMAIL_SIGNIN = "GMAIL_SIGNIN";
    public static final String SORT_RELEASE_DATE = "SORT_RELEASE_DATE";
    public static final String TEXT_SEARCH_PLACEHOLDER = "TEXT_SEARCH_PLACEHOLDER";
    public static final String SLOW_ISSUE_INTERNET_CONNECTION = "SLOW_ISSUE_INTERNET_CONNECTION";
    public static final String SIGN_OUT_ERROR = "SIGN_OUT_ERROR";
    public static final String BTN_SUBMIT = "BTN_SUBMIT";


    public static final String TRANASCTION_DETAIL = "TRANASCTION_DETAIL";
    public static final String SIGN_OUT_ALERT = "SIGN_OUT_ALERT";

    public static final String GOOGLE_FCM_TOKEN = "GOOGLE_FCM_TOKEN";
    public static final String ENTER_REGISTER_FIELDS_DATA = "ENTER_REGISTER_FIELDS_DATA";

    public static final String MY_LIBRARY = "MY_LIBRARY";
    public static final String ABOUT_US = "ABOUT_US";
    public static final String FILL_FORM_BELOW = "FILL_FORM_BELOW";
    public static final String MESSAGE = "MESSAGE";
    public static final String SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE = "SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE";
    public static final String LOGIN_STATUS_MESSAGE = "LOGIN_STATUS_MESSAGE";
    public static final String DEREGISTER = "DEREGISTER";
    public static final String MANAGE_DEVICE = "MANAGE_DEVICE";
    public static final String YOUR_DEVICE = "YOUR_DEVICE";
    public static final String ANDROID_VERSION = "OS_VERSION";
    public static final String DEFAULT_ANDROID_VERSION = "Android Version";
    public static final String DEFAULT_DEREGISTER = "Deregister";
    public static final String DEFAULT_MANAGE_DEVICE = "Manage Device";
    public static final String DEFAULT_YOUR_DEVICE = "Your Devices";

    public static String DEFAULT_VIDEO_ISSUE = "There's a problem with a video or Internet connection";
    public static final String DEFAULT_SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE = "Logout process has been successfully completed. Now you are authorized to activity_login.";
    public static final String DEFAULT_LOGIN_STATUS_MESSAGE = "You are no longer logged in . Please log in again.";

    public static final String DEFAULT_TRANSACTION_STATUS_ACTIVE = "Transcation status active";
    public static final String DEFAULT_IS_ONE_STEP_REGISTRATION = "0";
    public static String DEFAULT_FREE_FOR_COUPON = "You can watch the video at free of cost.";
    //ADD LATER FOR PURCHASE AND TRANSACTION DETAILS
    public static final String DEFAULT_NEED_LOGIN_TO_REVIEW = "You need to Login to add your review.";
    public static final String NEED_LOGIN_TO_REVIEW = "NEED_LOGIN_TO_REVIEW";
    public static final String DEFAULT_ENTER_REVIEW_HERE = "Enter your Review here...max 50 characters";
    public static final String ENTER_REVIEW_HERE = "ENTER_REVIEW_HERE";
    public static final String DEFAULT_BTN_POST_REVIEW = "Post Review";
    public static final String BTN_POST_REVIEW = "BTN_POST_REVIEW";
    public static final String DEFAULT_VIEW_LESS = "View Less";
    public static final String DEFAULT_SUBMIT_YOUR_RATING_TITLE = "Submit Your Rating";
    public static final String PLAN_ID = "PLAN_ID";
    public static final String DEFAULT_PLAN_ID = "0";
    public static final String NO_PDF = "NO_PDF";
    public static final String DEFAULT_NO_PDF = "PDF Not Available.";
    public static final String DOWNLOAD_INTERRUPTED = "DOWNLOAD_INTERRUPTED";
    public static final String DEFAULT_DOWNLOAD_INTERRUPTED = "Download Interrupted.";
    public static final String DOWNLOAD_COMPLETED = "DOWNLOAD_COMPLETED";
    public static final String DEFAULT_DOWNLOAD_COMPLETED = "Download Completed.";
    public static final String TRANSACTION_TITLE = "TRANSACTION_TITLE";
    public static final String DEFAULT_TRANSACTION_TITLE = "Transaction";
    public static final String FREE = "FREE";
    public static final String DEFAULT_FREE = "FREE";
    public static final String SUBSCRIPTION_COMPLETED = "SUBSCRIPTION_COMPLETED";
    public static final String DEFAULT_SUBSCRIPTION_COMPLETED = "Your subscription process completed successfully.";
    public static final String CVV_ALERT = "CVV_ALERT";
    public static final String DEFAULT_CVV_ALERT = "Please enter your CVV.";
    public static final String DEFAULT_CANCEL_BUTTON = "Cancel";
    public static final String DEFAULT_INVOICE = "Invoice";
    public static final String TRANSACTION_ORDER_ID = "TRANSACTION_ORDER_ID";
    public static final String DEFAULT_TRANSACTION_ORDER_ID = "Order Id";
    public static final String DEFAULT_TRANSACTION_DETAIL_PURCHASE_DATE = "Purchase Date";
    public static final String DEFAULT_TRANSACTION_DETAILS_TITLE = "Transaction Details";
    public static final String DEFAULT_TRANSACTION_DATE = "Transaction Date";
    public static final String DEFAULT_ORDER = "Order";
    public static final String DEFAULT_AMOUNT = "Amount";
    public static final String DEFAULT_TRANSACTION_STATUS = "Transaction Status";
    public static final String DEFAULT_PLAN_NAME = "Plan Name";
    public static final String DEFAULT_TRANASCTION_DETAIL = "Transaction Details";
    public static final String DEFAULT_DOWNLOAD_BUTTON_TITLE = "DOWNLOAD";
    public static final String DEFAULT_ACTIAVTE_PLAN_TITLE = "Activate Plan";
    public static final String DEFAULT_SAVE_THIS_CARD = "Save this card for faster checkouts";
    public static final String BUTTON_PAY_NOW = "BUTTON_PAY_NOW";
    public static final String DEFAULT_CARD_WILL_CHARGE = "Your card will be charged now :";
    public static final String DEFAULT_CREDIT_CARD_NAME_HINT = "Enter your Name on Card";
    public static final String DEFAULT_CREDIT_CARD_NUMBER_HINT = "Enter your Card Number";
    public static final String DEFAULT_COUPON_CANCELLED = "Applied coupon is cancelled.";
    public static final String DEFAULT_CREDIT_CARD_DETAILS = "Credit Card Details";
    public static final String DEFAULT_BUTTON_PAY_NOW = "Pay Now";
    public static final String DEFAULT_SELECT_PLAN = "Select Your Plan";
    public static final String DEFAULT_SKIP_BUTTON_TITLE = "Skip";
    public static final String DEFAULT_CONTINUE_BUTTON = "Continue";
    public static final String DEFAULT_USE_NEW_CARD = "Use New Card";
    public static final String DEFAULT_DISCOUNT_ON_COUPON = "Awesome, You just saved";
    public static final String DEFAULT_INVALID_COUPON = "Invalid Coupon!";
    public static final String DEFAULT_ERROR_IN_PAYMENT_VALIDATION = "Error in payment validation";
    public static final String ERROR_IN_SUBSCRIPTION = "ERROR_IN_SUBSCRIPTION";
    public static final String DEFAULT_ERROR_IN_SUBSCRIPTION = "Error in Subscription";
    public static final String ERROR_TRANSACTION_PROCESS = "ERROR_TRANSACTION_PROCESS";
    public static final String DEFAULT_ERROR_TRANSACTION_PROCESS = "Error in Transaction Process";
    public static final String DEFAULT_PURCHASE_SUCCESS_ALERT = "You have successfully purchased the content.";
    public static final String DEFAULT_COUPON_CODE_HINT = "Enter Coupon Code";
    public static final String DEFAULT_WATCH_HISTORY = "Watch History";

    public static String DEFAULT_TERMS = "terms";
    public static String DEFAULT_AGREE_TERMS = "By Clicking on Register,I agree to";
    public static final String DEFAULT_TO_LOGIN = "to Login.";
    public static final String DEFAULT_CLICK_HERE = "Click here";
    public static final String DEFAULT_IS_MYLIBRARY = "0";
    public static final String DEFAULT_IS_RESTRICT_DEVICE = "0";
    public static final String DEFAULT_UPDATE_PROFILE = "Update Profile";
    public static final String DEFAULT_APP_ON = "On";
    public static final String DEFAULT_APP_SELECT_LANGUAGE = "Select Language";
    public static final String DEFAULT_DETAILS_NOT_FOUND_ALERT = "Failed to find details.";
    public static final String DEFAULT_MY_FAVOURITE = "My Favourite";
    public static final String DEFAULT_GOOGLE_FCM_TOKEN = "0";

    //ADD LATER FOR PURCHASE AND TRANSACTION DETAILS

    public static String SAVE = "SAVE";
    public static String DEFAULT_SAVE = "Save";

    public static String SAVE_OFFLINE_VIDEO = "SAVE_OFFLINE_VIDEO";
    public static String DEFAULT_SAVE_OFFLINE_VIDEO = "Download Options";
    public static String SEND = "SEND";
    public static String DEFAULT_SEND = "Send";
    public static String CONFIRM_DELETE_MESSAGE = "Send";
    public static String DEFAULT_CONFIRM_DELETE_MESSAGE = "Are you sure to Delete?";


    public static final String DEFAULT_TRY_AGAIN = "Try Again !";
    public static final String DEFAULT_FILL_FORM_BELOW = "Fill the form below.";
    public static final String DEFAULT_MESSAGE = "Message";

    public static final String DEFAULT_MY_LIBRARY = "My Library";
    public static final String DEFAULT_SELECTED_LANGUAGE_CODE = "en";
    public static final String DEFAULT_HOME = "Home";
    public static final String DEFAULT_ENTER_REGISTER_FIELDS_DATA = "Fill the empty field(s)";
    public static final String DEFAULT_TERMS_AND_CONDITIONS = "Termes & Conditions";
    public static final String DEFAULT_ABOUT_US = "About Us";
    public static final String DEFAULT_CONTACT_US = "Contact us";

    public static final String DEFAULT_SEARCH_PLACEHOLDER = "Search";
    public static final String DEFAULT_VIEW_TRAILER = "View Trailer";
    public static final String DEFAULT_VIEW_MORE = "View More";
    public static final String DEFAULT_VIEW_ALL = "View All";
    public static final String DEFAULT_FILTER_BY = "Filter By";
    public static final String DEFAULT_SORT_BY = "Sort By";
    public static final String DEFAULT_FORGOT_PASSWORD = "Forgot Password?";
    public static final String DEFAULT_LOGIN = "Login";
    public static final String DEFAULT_CONFIRM_PASSWORD = "Confirm Password";
    public static final String DEFAULT_PROFILE = "Profile";
    public static final String DEFAULT_PURCHASE_HISTORY = "Purchase History";
    public static final String DEFAULT_LOGOUT = "Logout";
    public static final String DEFAULT_CHANGE_PASSWORD = "Change Password";
    public static final String DEFAULT_SEASON = "Season";
    public static String DEFAULT_NEXT = "Next";
    public static String DEFAULT_COMPLETE_SEASON = "Complete Season";
    public static String DEFAULT_SELECT_PURCHASE_TYPE = "Select Purchase Type";

    public static final String DEFAULT_PURCHASE = "Purchase";
    public static String DEFAULT_ENTER_VOUCHER_CODE = "Enter Voucher Code";
    public static String DEFAULT_VOUCHER_SUCCESS = "Voucher Applied  Successfully.";
    public static final String DEFAULT_BUTTON_APPLY = "Apply";
    public static final String DEFAULT_BUTTON_OK = "Ok";
    public static final String DEFAULT_OOPS_INVALID_EMAIL = "Oops! Invalid email.";
    public static final String DEFAULT_PASSWORDS_DO_NOT_MATCH = "Passwords do not match";
    public static final String DEFAULT_EMAIL_EXISTS = "Email already exists";
    public static final String DEFAULT_EMAIL_DOESNOT_EXISTS = "Email does not exist. Please enter correct email.";
    public static final String DEFAULT_PASSWORD_RESET_LINK = "Password Reset link has been emailed to your registered email ID. Please check your email to reset password.";
    public static final String DEFAULT_YES = "Yes";
    public static final String DEFAULT_NO = "No";
    public static final String DEFAULT_PROFILE_UPDATED = "Profile updated successfully.";

    public static final String DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO = "You are not authorised to view this content. Please activate";
    public static final String DEFAULT_CROSSED_MAXIMUM_LIMIT = "Sorry, you have exceeded the maximum number of views for this content.";
    public static final String DEFAULT_ACCESS_PERIOD_EXPIRED = "The watch period for the previous purchase has expired, please continue to purchase again.";
    public static final String DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY = "This content is not available to stream in your country";
    public static final String DEFAULT_ALREADY_PURCHASE_THIS_CONTENT = "Sorry, you have already purchased this content earlier.";
    public static final String DEFAULT_SORT_ALPHA_A_Z = "Alphabetic A-Z";
    public static final String DEFAULT_SORT_ALPHA_Z_A = "Alphabetic Z-A";
    public static final String DEFAULT_SORT_LAST_UPLOADED = "Last Uploaded";
    public static final String DEFAULT_GEO_BLOCKED_ALERT = "Sorry, this app is not available in your country.";
    public static final String DEFAULT_APP_NO_LONGER_ACTIVE = "Sorry, This app is no longer active";
    public static final String DEFAULT_NO_INTERNET_NO_DATA = "No Internet Connection / No Data";
    public static final String DEFAULT_SLOW_INTERNET_CONNECTION = "Slow Internet Connection";
    public static final String DEFAULT_NO_INTERNET_CONNECTION = "No Internet Connection";
    public static final String DEFAULT_NEW_HERE_TITLE = "New here ?";
    public static final String DEFAULT_SIGN_UP_TITLE = "Sign Up";

    public static final String DEFAULT_NAME_HINT = "Enter your Name";
    public static final String DEFAULT_ALREADY_MEMBER = "Already Member";
    public static final String DEFAULT_LANGUAGE_POPUP_LOGIN = "Log in";
    public static final String DEFAULT_LANGUAGE_POPUP_LANGUAGE = "Language";
    public static final String DEFAULT_OLD_PASSWORD = "New Password";
    public static final String DEFAULT_NEW_PASSWORD = "Confirm Password";
    public static final String DEFAULT_CAST_CREW_BUTTON_TITLE = "Cast and Crew";
    public static final String DEFAULT_EPISODE_TITLE = "All Episodes";

    public static final String DEFAULT_UPDATE_PROFILE_ALERT = "We could not be able to update your profile. Please try again.";
    public static final String DEFAULT_NO_DETAILS_AVAILABLE = "No details available";
    public static final String DEFAULT_SORRY = "Sorry !";
    public static final String DEFAULT_NO_VIDEO_AVAILABLE = "There's some error. Please try again !";
    public static final String DEFAULT_NO_DATA = "No Data";
    public static final String DEFAULT_NO_CONTENT = "There's no matching content found.";
    public static final String DEFAULT_ERROR_IN_REGISTRATION = "Error in registration";
    public static final String DEFAULT_LOGOUT_SUCCESS = "Logout Success";
    public static final String DEFAULT_ENTER_EMPTY_FIELD = "Fill the empty field(s)";

    public static final String DEFAULT_EMAIL_PASSWORD_INVALID = "Email or Password is invalid!";
    public static final String DEFAULT_ADVANCE_PURCHASE = "Advance Purchase";
    public static final String DEFAULT_FAILURE = "Failure !";
    public static final String DEFAULT_NO_RECORD = "No record found!!!";
    public static final String DEFAULT_SIGN_OUT_WARNING = "Are you sure you want to sign out ?";
    public static final String DEFAULT_SEARCH_ALERT = "Enter some text to search ...";
    public static final String DEFAULT_TEXT_EMIAL = "Enter your Email Address";
    public static final String DEFAULT_TEXT_PASSWORD = "Enter your Password";
    public static final String DEFAULT_BTN_REGISTER = "Register";
    public static final String DEFAULT_GMAIL_SIGNUP = "Register with Google";
    public static final String DEFAULT_GMAIL_SIGNIN = "Login with Google";
    public static final String DEFAULT_SORT_RELEASE_DATE = "Release Date";
    public static final String DEFAULT_TEXT_SEARCH_PLACEHOLDER = "Search";
    public static final String DEFAULT_SIGN_OUT_ERROR = "Sorry, we can not be able to log out. Try again!.";
    public static final String DEFAULT_BTN_SUBMIT = "Submit";
    public static final String DEAFULT_CANCEL_BUTTON = "Cancel";
    public static final String DEAFULT_CONTINUE_BUTTON = "Continue";

    public static String IS_STREAMING_RESTRICTION = "IS_STREAMING_RESTRICTION";
    public static String DEFAULT_IS_IS_STREAMING_RESTRICTION = "0";

    public static String STOP_SAVING_THIS_VIDEO = "STOP_SAVING_THIS_VIDEO";
    public static String DEFAULT_STOP_SAVING_THIS_VIDEO = "Stop saving this video";
    public static String YOUR_VIDEO_WONT_BE_SAVED = "YOUR_VIDEO_WONT_BE_SAVED";
    public static String DEFAULT_YOUR_VIDEO_WONT_BE_SAVED = "Your video can not be saved";
    public static String BTN_KEEP = "BTN_KEEP";
    public static String DEFAULT_BTN_KEEP = "Keep";
    public static String BTN_DISCARD = "BTN_DISCARD";
    public static String DEFAULT_BTN_DISCARD = "Discard";
    public static String WANT_TO_DOWNLOAD = "WANT_TO_DOWNLOAD";
    public static String DEFAULT_WANT_TO_DOWNLOAD = "Want to Download";
    public static String DOWNLOAD_CANCELLED = "DOWNLOAD_CANCELLED";
    public static String DEFAULT_DOWNLOAD_CANCELLED = "Download Cancelled";
    public static String DEFAULT_BTN_NEXT = "Next";
    public static String BTN_NEXT = "BTN_NEXT";
    public static String DEFAULT_CREDIT_CARD_CVV_HINT = "CVV";
    public static String DEFAULT_VOUCHER_CODE = "Voucher Code";
    public static String DEFAULT_WATCH_NOW = "Watch Now";
    public static String DEFAULT_PAYMENT_OPTIONS_TITLE = "Payment Options";
    public static String VOUCHER_BLANK_MESSAGE = "VOUCHER_BLANK_MESSAGE";
    public static String DEFAULT_VOUCHER_BLANK_MESSAGE = "Please Enter Your Voucher Code";
    public static String DETAILS_TITLE = "DETAILS_TITLE";
    public static String DEFAULT_DETAILS_TITLE = "Details Title";
    public static String BENEFIT_TITLE = "BENEFIT_TITLE";
    public static String DEFAULT_BENEFIT_TITLE = "Benefits";
    public static String DIFFICULTY_TITLE = "DIFFICULTY_TITLE";
    public static String DEFAULT_DIFFICULTY_TITLE = "Difficulty";
    public static String DURATION_TITLE = "DURATION_TITLE";
    public static String DEFAULT_DURATION_TITLE = " Duration";
    public static String RESUME_MESSAGE = "RESUME_MESSAGE";
    public static String DEFAULT_RESUME_MESSAGE = "Continue watching where you left?";
    public static String DEFAULT_ADD_A_REVIEW = "Add a Review";
    public static String DEFAULT_REVIEWS = "Reviews";
    public static String ADD_A_REVIEW = "ADD_A_REVIEW";
    public static String REVIEWS = "REVIEWS";
    public static String NO_PURCHASE_HISTORY = "NO_PURCHASE_HISTORY";
    public static String DEFAULT_NO_PURCHASE_HISTORY = "No Purchase History";
    public static String FILMOGRAPHY = "FILMOGRAPHY";
    public static String DEFAULT_FILMOGRAPHY = "filmography";
    public static String ERROR_IN_DATA_FETCHING = "ERROR_IN_DATA_FETCHING";
    public static String DEFAULT_ERROR_IN_DATA_FETCHING = "Error in data fetching. Please try again";
    public static String ENTER_YOUR_MESSAGE = "ENTER_YOUR_MESSAGE";
    public static String DEFAULT_ENTER_YOUR_MESSAGE = "Enter Your Message";
    public static String DEFAULT_VALID_CONFIRM_PASSWORD = "Please enter confirm password";
    public static String WANT_DOWNLOAD_CANCEL = "WANT_DOWNLOAD_CANCEL";
    public static String DEFAULT_WANT_DOWNLOAD_CANCEL = "Do you want to cancel the download ?";
    public static String DOWNLOAD_CANCEL = "DOWNLOAD_CANCEL";
    public static String DEFAULT_DOWNLOAD_CANCEL = "Download Cancelled";
    public static String NO_DOWNLOADED_VIDEOS = "NO_DOWNLOADED_VIDEOS";
    public static String DEFAULT_NO_DOWNLOADED_VIDEOS = "No downloaded video(s) available";
    public static String DOWNLOADED_ACCESS_EXPIRED = "DOWNLOADED_ACCESS_EXPIRED";
    public static String DEFAULT_DOWNLOADED_ACCESS_EXPIRED = "You don't have access to play this video";
    public static String RESUME = "RESUME";
    public static String DEFAULT_RESUME = "Resume";
    public static String NO_RESULT_FOUND_REFINE_YOUR_SEARCH = "NO_RESULT_FOUND_REFINE_YOUR_SEARCH";
    public static String DEFAULT_NO_RESULT_FOUND_REFINE_YOUR_SEARCH = "No result found. Please refine your search.";
    public static String NO_DEVICE_AVAILABE = "NO_DEVICE_AVAILABE";
    public static String DEFAULT_NO_DEVICE_AVAILABE = "No devices available for this user.";
    public static String REMOVE_DEVICE_SUCCESS = "REMOVE_DEVICE_SUCCESS";
    public static String DEFAULT_REMOVE_DEVICE_SUCCESS = "Remove device request successful.";





    /**
     * @param mContext
     */

    private LanguagePreference(Context mContext) {
        languageSharedPref = mContext.getSharedPreferences(LANGUAGE_SHARED_PRE, Context.MODE_PRIVATE);
        mEditor = languageSharedPref.edit();
    }

    public static LanguagePreference getLanguagePreference(Context mContext) {
        if (languagePreference == null) {
            return new LanguagePreference(mContext);
        }
        return languagePreference;
    }


    public void setLanguageSharedPrefernce(String Key, String Value) {
        mEditor.putString(Key, Value);
        mEditor.commit();
    }

    public String getTextofLanguage(String tempKey, String defaultValue) {
        return languageSharedPref.getString(tempKey, defaultValue);
    }

}
