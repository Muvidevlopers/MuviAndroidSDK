/**
 * SDK initialization, platform and device information classes.
 */

package com.home.apisdk;

import java.util.Date;

/**
 * Created by MUVI on 1/18/2017.
 * Class contains all the APIs end points
 */

public class APIUrlConstant {

    public static String BASE_URl;
    /**
     * endpoint to initialize SDK API
     */
    public static String INITIALIZATION_URL = "initialiseSdk";
    /**
     * endpoint to get Menu List API
     */
    public static String MENU_LIST_URL = "getMenuList";
    /**
     * endpoint to get Genre List API
     */
    public static String GENRE_LIST_URL = "getGenreList";
    /**
     * endpoint to get Profile Details API
     */
    public static String GET_PROFILE_DETAILS_URL = "getProfileDetails";
    /**
     * endpoint to Update User Profile API
     */
    public static String UPDATE_PROFILE_URL = "updateUserProfile";
    /**
     * endpoint to get Logout API
     */
    public static String LOGOUT_URL = "logout";
    /**
     * endpoint to get Video Details API
     */
    public static String VIDEO_DETAILS_URL = "getVideoDetails";

    /**
     * endpoint to get Watch History Details API
     */
    public static String WATCH_HISTORY = "watchHistory";
    /**
     * endpoint to getMonitization Details API
     */
    public static String GET_MONITIZATION_DETAILS = "GetMonetizationDetails";

    public static String getGetMonitizationDetails() {
        return BASE_URl + GET_MONITIZATION_DETAILS;
    }

    /**
     * endpoint to get Content List API
     */
    public static String GET_CONTENT_LIST_URL = "getContentList";
    /**
     * endpoint to get Forgot Password API
     */
    public static String FORGOT_PASSWORD_URL = "forgotPassword";
    /**
     * endpoint to get Login API
     */
    public static String LOGIN_URL = "login";
    /**
     * endpoint to get Register User API
     */
    public static String REGISTER_URL = "registerUser";
    /**
     * endpoint to get Episode Details API
     */
    public static String GET_EPISODE_DETAILS_URL = "episodeDetails";
    /**
     * endpoint to get Search Data API
     */
    public static String SEARCH_DATA_URL = "searchData";
    /**
     * endpoint to get Content Details API
     */
    public static String CONTENT_DETAILS_URL = "getContentDetails";


    //New Api Added

    /**
     * endpoint to get Studio PLan Lists API
     */
    public static String SUBSCRIPTION_PLAN_LISTS = "getStudioPlanLists";
    /**
     * endpoint to get Home Page API
     */
   // public static String HOMEPAGE_URL = "homePage";

    public static String HOMEPAGE_URL = "getAppHomePage";
    /**
     * endpoint to get Featured Content Details API
     */
   // public static String GET_FEATURE_CONTENT_URL = "getFeaturedContent";
    public static String GET_FEATURE_CONTENT_URL = "getAPPFeaturedContent";
    /**
     * endpoint to get Image For Download API
     */
    public static String GET_IMAGE_FOR_DOWNLOAD_URL = "GetImageForDownload";
    /**
     * endpoint to get Country Details API
     */
    public static String CHECK_GEO_BLOCK_URL = "checkGeoBlock";
    /**
     * endpoint to get Registration Enable details API
     */
    public static String IS_REGISTRATIONENABLED_URL = "isRegistrationEnabled";
    /**
     * endpoint to get Static Page Details API
     */
    public static String GETSTATICPAGES_URL = "getStaticPagedetails";
    /**
     * endpoint to get Contact Us API
     */
    public static String CONTACT_US_URL = "contactUs";
    /**
     * endpoint to get Celebrity Details API
     */
    public static String GET_CELIBRITY_URL = "getCelibrity";
    /**
     * endpoint to get Purchase History Details API
     */
    public static String PURCHASE_HISTORY_URL = "PurchaseHistory";
    /**
     * endpoint to get Transaction Details API
     */
    public static String TRANSACTION_URL = "Transaction";
    /**
     * endpoint to get Invoice Pdf Details API
     */
    public static String GET_INVOICE_PDF_URL = "GetInvoicePDF";
    /**
     * endpoint to Delete Invoice Pdf Details API
     */
    public static String DELETE_INVOICE_PDF_URL = "DeleteInvoicePath";
    /**
     * endpoint to get Monitization Details API
     */
    public static String GET_MONETIZATION_DETAILS_URL = "GetMonetizationDetails";
    /**
     * endpoint to get Social Auth Details API
     */
    public static String SOCIALAUTH_URL = "socialAuth";
    /**
     * endpoint to get Validate Voucher Details API
     */
    public static String VALIDATE_VOUCHER_URL = "ValidateVoucher";
    /**
     * endpoint to get Voucher Plan Details API
     */
    public static String GET_VOUCHER_PLAN_URL = "GetVoucherPlan";
    /**
     * endpoint to get Voucher Subscription Details API
     */
    public static String VOUCHER_SUBSCRIPTION_URL = "VoucherSubscription";
    /**
     * endpoint to get My Library Details API
     */
    public static String MYLIBRARY_URL = "MyLibrary";
    /**
     * endpoint to get Register User Payment Details API
     */
    public static String REGISTER_USER_PAYMENT_URL = "registerUserPayment";
    /**
     * endpoint to get Auth User Payment Information Details API
     */
    public static String AUTH_USER_PAYMENT_INFO_URL = "authUserPaymentInfo";
    /**
     * endpoint to get Card List For PPV Details API
     */
    public static String GET_CARD_LIST_FOR_PPV_URL = "getCardsListForPPV";
    /**
     * endpoint to get Language Translation Details API
     */
    public static String LanguageTranslation = "textTranslation";
    /**
     * endpoint to Check Device Details API
     */
    public static String CheckDevice = "CheckDevice";
    /**
     * endpoint to Logout All Details API
     */
    public static String LogoutAll = "LogoutAll";
    /**
     * endpoint to get Facebook User Status Details API
     */
    public static String fbUserExistsUrl = "getFbUserStatus";
    /**
     * endpoint to get About Us Details API
     */
    public static String AboutUs = "getStaticPagedetails";

    /**
     * endpoint to get Gmail Registration Details API
     */
    public static String GmailRegUrl = "socialAuth";

    public static String getGmailRegUrl() {
        return BASE_URl + GmailRegUrl;
    }

    /**
     * endpoint to get View Favorite Details API
     */
    public static String ViewFavorite = "ViewFavourite";
    /**
     * endpoint to get Add to Favorite Details API
     */
    public static String AddtoFavlist = "AddtoFavlist";
    /**
     * endpoint to get Delete Favorite List Details API
     */
    public static String DeleteFavList = "DeleteFavList";
    /**
     * endpoint to get Menu Details API
     */
    public static String GetMenusUrl = "GetMenus";
    /**
     * endpoint to get Update Google Id Details API
     */
    public static String UpdateGoogleid = "UpdateGoogleid";
    /**
     * endpoint to get Cast Details API
     */
    public static String GetCastDetails = "getCastDetail";
    /**
     * endpoint to get Content Rating View Details API
     */
    public static final String ViewContentRating = "ViewContentRating";
    /**
     * endpoint to get Content Rating Add Details API
     */
    public static final String AddContentRating = "AddContentRating";
    /**
     * endpoint to get PPV Payment Details API
     */
    public static final String addSubscriptionUrl = "ppvpayment";
    /**
     * endpoint to get Manage Device Details API
     */
    public static final String ManageDevices = "ManageDevices";
    /**
     * endpoint to get Validate Coupon Code Details API
     */
    public static String VALIDATE_COUPON_CODE_URL = "validateCouponCode";
    /**
     * endpoint to get Update Buffer Logs Details API
     */
    public static final String updateBufferLogUrl = "updateBufferLogs";
    /**
     * endpoint to get Video Buffer Logs Details API
     */
    public static String VIDEO_BUFFER_LOGS_URL = "bufferLogs";
    /**
     * endpoint to get Validate User For Content Details API
     */
    public static String VALIDATE_USER_FOR_CONTENT_URL = "isContentAuthorized";
    /**
     * endpoint to get IP Address Details API
     */
    public static String IP_ADDRESS_URL = "https://api.ipify.org/?format=json";
    /**
     * endpoint to get Language LIst Details API
     */
    public static String GET_LANGUAGE_LIST_URL = "getLanguageList";
    /**
     * endpoint to get Video Logs Details API
     */

    public static String VIDEO_LOGS_URL = "VideoLogNew";
    /**
     * endpoint to get Remove Device Details API
     */
    public static final String RemoveDevice = "RemoveDevice";
    /**
     * endpoint to get Check If User Is Logged In Details API
     */
    public static final String CheckIfUserLoggedIn = "CheckIfUserLoggedIn";

    /**
     * endpoint to get all the menu lists including sub menus
     */

    public static final String GetAppMenu = "getAppMenu";

    /**
     * endpoint to get all the Sub Category lists including
     */

    public static final String GetSubCategoryList = "getSubCategoryList";

    /* endpoint to get Notification Lists Details API*/
    public static String notificationlists = "NotificationLists";


    /* endpoint to get Read All Notification Details API*/
    public static String readallnotification = "ReadAllNotification";
    /* endpoint to get Notification Count Details API*/
    public static String notificationcount = "GetNoOfUnreadNotification";



    public static String getGetSubCategoryList() {
        return GetSubCategoryList;
    }



    public static String getGetAppMenu() {
        return BASE_URl + GetAppMenu;
    }


    public static String getMenuListUrl() {
        return BASE_URl + MENU_LIST_URL;
    }

    public static String getRemoveDevice() {
        return BASE_URl + RemoveDevice;
    }

    public static String getCheckIfUserLoggedIn() {
        return BASE_URl + CheckIfUserLoggedIn;
    }


    public static String getGetCastDetails() {
        return BASE_URl + GetCastDetails;
    }


    public static String getUpdateGoogleid() {
        return BASE_URl + UpdateGoogleid;
    }


    public static String getGetMenusUrl() {
        return BASE_URl + GetMenusUrl;
    }

    public static String getViewFavorite() {
        return BASE_URl + ViewFavorite;
    }

    public static String getAddtoFavlist() {
        return BASE_URl + AddtoFavlist;
    }

    public static String getDeleteFavList() {
        return BASE_URl + DeleteFavList;
    }

    public static String getAddContentRating() {
        return BASE_URl + AddContentRating;
    }

    public static String getViewContentRating() {
        return BASE_URl + ViewContentRating;
    }

    public static String getAddSubscriptionUrl() {
        return BASE_URl + addSubscriptionUrl;
    }

    public static String getManageDevices() {
        return BASE_URl + ManageDevices;
    }

    public static String getAboutUs() {
        return BASE_URl + AboutUs;
    }

    public static String getWatchHistory() {
        return BASE_URl + WATCH_HISTORY;
    }
    public static String getFbUserExistsUrl() {
        return BASE_URl + fbUserExistsUrl;
    }


    public static String getLogoutAll() {
        return BASE_URl + LogoutAll;
    }

    public static String getCheckDevice() {
        return BASE_URl + CheckDevice;
    }

    public static String getLanguageTranslation() {
        return BASE_URl + LanguageTranslation;
    }


    public static String getInitializationUrl() {
        return BASE_URl + INITIALIZATION_URL;
    }

    public static String getGenreListUrl() {
        return BASE_URl + GENRE_LIST_URL;
    }

    public static String getGetProfileDetailsUrl() {
        return BASE_URl + GET_PROFILE_DETAILS_URL;
    }

    public static String getUpdateProfileUrl() {
        return BASE_URl + UPDATE_PROFILE_URL;
    }

    public static String getLogoutUrl() {
        return BASE_URl + LOGOUT_URL;
    }

    public static String getVideoDetailsUrl() {
        return BASE_URl + VIDEO_DETAILS_URL;
    }

    public static String getGetContentListUrl() {
        return BASE_URl + GET_CONTENT_LIST_URL;
    }

    public static String getForgotPasswordUrl() {
        return BASE_URl + FORGOT_PASSWORD_URL;
    }

    public static String getLoginUrl() {
        return BASE_URl + LOGIN_URL;
    }

    public static String getRegisterUrl() {
        return BASE_URl + REGISTER_URL;
    }

    public static String getGetEpisodeDetailsUrl() {
        return BASE_URl + GET_EPISODE_DETAILS_URL;
    }

    public static String getSearchDataUrl() {
        return BASE_URl + SEARCH_DATA_URL;
    }

    public static String getContentDetailsUrl() {
        return BASE_URl + CONTENT_DETAILS_URL;
    }

    public static String getSubscriptionPlanLists() {
        return BASE_URl + SUBSCRIPTION_PLAN_LISTS;
    }

    public static String getHomepageUrl() {
        return BASE_URl + HOMEPAGE_URL;
    }

    public static String getGetFeatureContentUrl() {
        return BASE_URl + GET_FEATURE_CONTENT_URL;
    }

    public static String getGetImageForDownloadUrl() {
        return BASE_URl + GET_IMAGE_FOR_DOWNLOAD_URL;
    }

    public static String getCheckGeoBlockUrl() {
        return BASE_URl + CHECK_GEO_BLOCK_URL;
    }

    public static String getIsRegistrationenabledUrl() {
        return BASE_URl + IS_REGISTRATIONENABLED_URL;
    }

    public static String getGetstaticpagesUrl() {
        return BASE_URl + GETSTATICPAGES_URL;
    }

    public static String getContactUsUrl() {
        return BASE_URl + CONTACT_US_URL;
    }

    public static String getGetCelibrityUrl() {
        return BASE_URl + GET_CELIBRITY_URL;
    }

    public static String getPurchaseHistoryUrl() {
        return BASE_URl + PURCHASE_HISTORY_URL;
    }

    public static String getTransactionUrl() {
        return BASE_URl + TRANSACTION_URL;
    }

    public static String getGetInvoicePdfUrl() {
        return BASE_URl + GET_INVOICE_PDF_URL;
    }

    public static String getDeleteInvoicePdfUrl() {
        return BASE_URl + DELETE_INVOICE_PDF_URL;
    }

    public static String getGetMonetizationDetailsUrl() {
        return BASE_URl + GET_MONETIZATION_DETAILS_URL;
    }

    public static String getSocialauthUrl() {
        return BASE_URl + SOCIALAUTH_URL;
    }

    public static String getValidateVoucherUrl() {
        return BASE_URl + VALIDATE_VOUCHER_URL;
    }

    public static String getGetVoucherPlanUrl() {
        return BASE_URl + GET_VOUCHER_PLAN_URL;
    }

    public static String getVoucherSubscriptionUrl() {
        return BASE_URl + VOUCHER_SUBSCRIPTION_URL;
    }

    public static String getMylibraryUrl() {
        return BASE_URl + MYLIBRARY_URL;
    }

    public static String getRegisterUserPaymentUrl() {
        return BASE_URl + REGISTER_USER_PAYMENT_URL;
    }

    public static String getAuthUserPaymentInfoUrl() {
        return BASE_URl + AUTH_USER_PAYMENT_INFO_URL;
    }

    public static String getGetCardListForPpvUrl() {
        return BASE_URl + GET_CARD_LIST_FOR_PPV_URL;
    }

    public static String getValidateCouponCodeUrl() {
        return BASE_URl + VALIDATE_COUPON_CODE_URL;
    }

    public static String getIpAddressUrl() {
        return IP_ADDRESS_URL;
    }

    public static String getGetLanguageListUrl() {
        return BASE_URl + GET_LANGUAGE_LIST_URL;
    }

    public static String getVideoLogsUrl() {
        return BASE_URl + VIDEO_LOGS_URL;
    }

    public static String getVideoBufferLogsUrl() {
        return BASE_URl + VIDEO_BUFFER_LOGS_URL;
    }

    public static String getValidateUserForContentUrl() {
        return BASE_URl + VALIDATE_USER_FOR_CONTENT_URL;
    }


    public static String getUpdateBufferLogUrl() {
        return BASE_URl + updateBufferLogUrl;
    }
    public static String getNotificationcount() {
        return BASE_URl + notificationcount;
    }
    public static String getNotificationLists() {
        return BASE_URl + notificationlists;
    }
    public static String getReadallnotification() {
        return BASE_URl + readallnotification;
    }

}
