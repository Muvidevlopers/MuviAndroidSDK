package player.activity;

import android.content.Context;

import player.utils.Util;


/**
 * Created by MUVI on 10-05-2017.
 */

public class TranslatedLanguage {
    Context context;

    public TranslatedLanguage(Context context) {
        this.context=context;
    }

    public String getNoInternetConnection(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION);
    }
    public String getViewMore(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.VIEW_MORE, Util.DEFAULT_VIEW_MORE);
    }
    public String getViewTrailer(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.VIEW_TRAILER, Util.DEFAULT_VIEW_TRAILER);
    }
    public String getPlanId(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.PLAN_ID, Util.DEFAULT_PLAN_ID);
    }
    public String getNoContent(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.NO_CONTENT, Util.DEFAULT_NO_CONTENT);
    }
    public String getNoData(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.NO_DATA, Util.DEFAULT_NO_DATA);
    }
    public String getNoVideoAvailabel(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE);
    }
    public String getSorry(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SORRY, Util.DEFAULT_SORRY);
    }
    public String getButtonOk(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK);
    }
    public String getSelectedLanguageCode(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE);
    }
    public String getSlowInternetConnection(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION);
    }
    public String getSeason(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SEASON, Util.DEFAULT_SEASON);
    }
    public String getNoDetailsAvailabel(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.NO_DETAILS_AVAILABLE, Util.DEFAULT_NO_DETAILS_AVAILABLE);
    }
    public String getCastCrewButtonTitle(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CAST_CREW_BUTTON_TITLE, Util.DEFAULT_CAST_CREW_BUTTON_TITLE);
    }
    public String getContentNotAvailabelInCountry(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY, Util.DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY);
    }
    public String getLanguagePopupLogin(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.LANGUAGE_POPUP_LOGIN, Util.DEFAULT_LANGUAGE_POPUP_LOGIN);
    }
    public String getbtnRegister(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.BTN_REGISTER, Util.DEFAULT_BTN_REGISTER);
    }
    public String getProfile(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.PROFILE, Util.DEFAULT_PROFILE);
    }
    public String getPurchaseHistory(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.PURCHASE_HISTORY, Util.DEFAULT_PURCHASE_HISTORY);
    }
    public String getLogout(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.LOGOUT, Util.DEFAULT_LOGOUT);
    }
    public String getSignoutWarning(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SIGN_OUT_WARNING, Util.DEFAULT_SIGN_OUT_WARNING);
    }
    public String getYes(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.YES, Util.DEFAULT_YES);
    }
    public String getNo(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.NO, Util.DEFAULT_NO);
    }
    public String getSignoutError(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR);
    }
    public String getLogoutSuccess(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.LOGOUT_SUCCESS, Util.DEFAULT_LOGOUT_SUCCESS);
    }
    public String getAppSelectLanguage(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.APP_SELECT_LANGUAGE, Util.DEFAULT_APP_SELECT_LANGUAGE);
    }
    public String getButtonApply(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.BUTTON_APPLY, Util.DEFAULT_BUTTON_APPLY);
    }
    public String getGeoBlockAlert(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.GEO_BLOCKED_ALERT, Util.DEFAULT_GEO_BLOCKED_ALERT);
    }
    public String getFilterBy(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.FILTER_BY, Util.DEFAULT_FILTER_BY);
    }
    public String getSortBy(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SORT_BY, Util.DEFAULT_SORT_BY);
    }
    public String getSortLastuploaded(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SORT_LAST_UPLOADED, Util.DEFAULT_SORT_LAST_UPLOADED);
    }
    public String getSortReleaseDate(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SORT_RELEASE_DATE, Util.DEFAULT_SORT_RELEASE_DATE);
    }
    public String getSortAlphaA_Z(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SORT_ALPHA_A_Z, Util.DEFAULT_SORT_ALPHA_A_Z);
    }
    public String getSortAlphaZ_A(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SORT_ALPHA_Z_A, Util.DEFAULT_SORT_ALPHA_Z_A);
    }
    public String getGoogleFcmToken(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.GOOGLE_FCM_TOKEN, Util.DEFAULT_GOOGLE_FCM_TOKEN);
    }
    public String getNoInternetNoData(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.NO_INTERNET_NO_DATA, Util.DEFAULT_NO_INTERNET_NO_DATA);
    }
    public String getLanguagePopupLanguage(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.LANGUAGE_POPUP_LANGUAGE, Util.DEFAULT_LANGUAGE_POPUP_LANGUAGE);
    }
    public String getMyLibrary(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.MY_LIBRARY, Util.DEFAULT_MY_LIBRARY);
    }
    public String getHome(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.HOME, Util.DEFAULT_HOME);
    }
    public String getForgotPass(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.FORGOT_PASSWORD, Util.DEFAULT_FORGOT_PASSWORD);
    }
    public String getNewHereTitle(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.NEW_HERE_TITLE, Util.DEFAULT_NEW_HERE_TITLE);
    }
    public String getSignupTitle(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SIGN_UP_TITLE, Util.DEFAULT_SIGN_UP_TITLE);
    }
    public String getLogin(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.LOGIN, Util.DEFAULT_LOGIN);
    }
    public String getOopsInvalidEmail(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.OOPS_INVALID_EMAIL, Util.DEFAULT_OOPS_INVALID_EMAIL);
    }
    public String getRegisterFieldData(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.ENTER_REGISTER_FIELDS_DATA, Util.DEFAULT_ENTER_REGISTER_FIELDS_DATA);
    }
    public String getDetailsNotFound(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.DETAILS_NOT_FOUND_ALERT, Util.DEFAULT_DETAILS_NOT_FOUND_ALERT);
    }
    public String getTextEmail(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.TEXT_EMIAL, Util.DEFAULT_TEXT_EMIAL);
    }
    public String getTextPassword(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.TEXT_PASSWORD, Util.DEFAULT_TEXT_PASSWORD);
    }
    public String getEmailPasswordInvalid(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.EMAIL_PASSWORD_INVALID, Util.DEFAULT_TEXT_PASSWORD);
    }
    public String getCancelButton(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CANCEL_BUTTON, Util.DEAFULT_CANCEL_BUTTON);
    }
    public String getLogoutSuccessMsg(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE, Util.SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE);
    }
    public String getTryAgain(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.TRY_AGAIN, Util.DEFAULT_TRY_AGAIN);
    }
    public String getAdvancePurchase(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.ADVANCE_PURCHASE, Util.DEFAULT_ADVANCE_PURCHASE);
    }
    public String getCastCrwebtnTitle(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CAST_CREW_BUTTON_TITLE, Util.DEFAULT_CAST_CREW_BUTTON_TITLE);
    }
    public String getAlraedyMember(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.ALREADY_MEMBER, Util.DEFAULT_ALREADY_MEMBER);
    }
    public String getErrorInRegistration(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.ERROR_IN_REGISTRATION, Util.DEFAULT_ERROR_IN_REGISTRATION);
    }
    public String getFailure(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.FAILURE, Util.DEFAULT_FAILURE);
    }
    public String getEmailExist(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.EMAIL_EXISTS, Util.DEFAULT_EMAIL_EXISTS);
    }
    public String getNameHint(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.NAME_HINT, Util.DEFAULT_NAME_HINT);
    }
    public String getConfirmpass(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CONFIRM_PASSWORD, Util.DEFAULT_CONFIRM_PASSWORD);
    }
    public String getPassDonotMatch(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.PASSWORDS_DO_NOT_MATCH, Util.DEFAULT_PASSWORDS_DO_NOT_MATCH);
    }
    public String getBtnSubmit(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.BTN_SUBMIT, Util.DEFAULT_BTN_SUBMIT);
    }
    public String getEmailDoesnotExit(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.EMAIL_DOESNOT_EXISTS, Util.DEFAULT_EMAIL_DOESNOT_EXISTS);
    }
    public String getPassResetLink(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.PASSWORD_RESET_LINK, Util.DEFAULT_PASSWORD_RESET_LINK);
    }
    public String getFillFormBelow(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.FILL_FORM_BELOW, Util.DEFAULT_FILL_FORM_BELOW);
    }
    public String getMessage(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.MESSAGE, Util.DEFAULT_MESSAGE);
    }
    public String getEpisodeTitle(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.EPISODE_TITLE, Util.DEFAULT_EPISODE_TITLE);
    }
    public String getLoginStatusMsg(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.LOGIN_STATUS_MESSAGE, Util.DEFAULT_LOGIN_STATUS_MESSAGE);
    }
    public String getResumeMsg(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.RESUME_MESSAGE, Util.DEFAULT_RESUME_MESSAGE);
    }
    public String getContinuebtn(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CONTINUE_BUTTON, Util.DEAFULT_CONTINUE_BUTTON);
    }
    public String getCancelBtn(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CANCEL_BUTTON, Util.DEAFULT_CANCEL_BUTTON);
    }
    public String getCreditCardDetail(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CREDIT_CARD_DETAILS, Util.DEFAULT_CREDIT_CARD_DETAILS);
    }
    public String getBtnPayNow(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.BUTTON_PAY_NOW, Util.DEFAULT_BUTTON_PAY_NOW);
    }
    public String getCardWillCharge(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CARD_WILL_CHARGE, Util.DEFAULT_CARD_WILL_CHARGE);
    }
    public String getCreditcardNameHint(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CREDIT_CARD_NAME_HINT, Util.DEFAULT_CREDIT_CARD_NAME_HINT);
    }
    public String getCreditcardNumberHint(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CREDIT_CARD_NUMBER_HINT, Util.DEFAULT_CREDIT_CARD_NUMBER_HINT);
    }
    public String getCvvAlert(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CVV_ALERT, Util.DEFAULT_CVV_ALERT);
    }
    public String getErrorInSubscription(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.ERROR_IN_SUBSCRIPTION, Util.DEFAULT_ERROR_IN_SUBSCRIPTION);
    }
    public String getSubscriptionCompleted(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SUBSCRIPTION_COMPLETED, Util.DEFAULT_SUBSCRIPTION_COMPLETED);
    }
    public String getPurchase(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.PURCHASE, Util.DEFAULT_PURCHASE);
    }
    public String getSaveThisCard(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData("  "+ Util.SAVE_THIS_CARD,"  "+ Util.DEFAULT_SAVE_THIS_CARD);
    }
    public String getCoupancodehint(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.COUPON_CODE_HINT, Util.DEFAULT_COUPON_CODE_HINT);
    }
    public String getCoupanCancel(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.COUPON_CANCELLED, Util.DEFAULT_COUPON_CODE_HINT);
    }
    public String getUseNewCard(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.USE_NEW_CARD, Util.DEFAULT_USE_NEW_CARD);
    }
    public String getInvalidCoupan(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.INVALID_COUPON, Util.DEFAULT_INVALID_COUPON);
    }
    public String getDiscountonCoupan(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.DISCOUNT_ON_COUPON, Util.DEFAULT_DISCOUNT_ON_COUPON);
    }
    public String getActivateSubscriptionWatchVideo(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.ACTIVATE_SUBSCRIPTION_WATCH_VIDEO, Util.DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO);
    }
    public String getCrossMaxLimit(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CROSSED_MAXIMUM_LIMIT, Util.DEFAULT_CROSSED_MAXIMUM_LIMIT);
    }
    public String getErrorInPaymentValidation(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.ERROR_IN_PAYMENT_VALIDATION, Util.DEFAULT_ERROR_IN_PAYMENT_VALIDATION);
    }
    public String getPurchaseSuccessAlert(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.PURCHASE_SUCCESS_ALERT, Util.DEFAULT_PURCHASE_SUCCESS_ALERT);
    }
    public String getOldPassword(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.OLD_PASSWORD, Util.DEFAULT_OLD_PASSWORD);
    }
    public String getNewPassword(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.NEW_PASSWORD, Util.DEFAULT_NEW_PASSWORD);
    }
    public String getChangePassword(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.CHANGE_PASSWORD, Util.DEFAULT_CHANGE_PASSWORD);
    }
    public String getupdateProfile(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.UPDATE_PROFILE, Util.DEFAULT_UPDATE_PROFILE);
    }
    public String getupdateProfileAlert(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.UPDATE_PROFILE_ALERT, Util.DEFAULT_UPDATE_PROFILE_ALERT);
    }
    public String getProfileUpdate(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.PROFILE_UPDATED, Util.DEFAULT_PROFILE_UPDATED);
    }
    public String getSearchPlaceHolder(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SEARCH_PLACEHOLDER, Util.DEFAULT_SEARCH_PLACEHOLDER);
    }
    public String getTextSearchPlaceHolder(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.TEXT_SEARCH_PLACEHOLDER, Util.DEFAULT_TEXT_SEARCH_PLACEHOLDER);
    }
    public String getSearchAlert(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SEARCH_ALERT, Util.DEFAULT_SEARCH_ALERT);
    }
    public String getSelectPlan(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.SELECT_PLAN, Util.DEFAULT_SELECT_PLAN);
    }
    public String getActivatePlanTitle(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.ACTIAVTE_PLAN_TITLE, Util.DEFAULT_ACTIAVTE_PLAN_TITLE);
    }
    public String getTransactionDate(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.TRANSACTION_DATE, Util.DEFAULT_TRANSACTION_DATE);
    }
    public String getOrder(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.ORDER, Util.DEFAULT_ORDER);
    }
    public String getAmount(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.AMOUNT, Util.DEFAULT_AMOUNT);
    }
    public String getInvoice(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.INVOICE, Util.DEFAULT_INVOICE);
    }
    public String getTransactionStatus(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.TRANSACTION_STATUS, Util.DEFAULT_TRANSACTION_STATUS);
    }
    public String getPlanName(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.PLAN_NAME, Util.DEFAULT_PLAN_NAME);
    }
    public String getTransactionDetail(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.TRANASCTION_DETAIL, Util.DEFAULT_TRANASCTION_DETAIL);
    }
    public String getDownloadBtnTitle(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.DOWNLOAD_BUTTON_TITLE, Util.DOWNLOAD_BUTTON_TITLE);
    }
    public String getNoPdf(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.NO_PDF, Util.DEFAULT_NO_PDF);
    }
    public String getDownloadInterrupted(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.DOWNLOAD_INTERRUPTED, Util.DEFAULT_DOWNLOAD_INTERRUPTED);
    }
    public String getDownloadCompleted(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.DOWNLOAD_COMPLETED, Util.DEFAULT_DOWNLOAD_COMPLETED);
    }
    public String getIsMyLibrary(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.IS_MYLIBRARY, Util.DEFAULT_IS_MYLIBRARY);
    }
    public String getAppOn(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.APP_ON, Util.DEFAULT_APP_ON);
    }
    public String getTranscationTitle(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.TRANSACTION_TITLE, Util.DEFAULT_TRANSACTION_TITLE);
    }
    public String getTranscationOrderId(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.TRANSACTION_ORDER_ID, Util.DEFAULT_TRANSACTION_ORDER_ID);
    }
    public String getTranscationDetailPurchaseDate(){

        SaveData saveData=new SaveData(context, Util.LANGUAGE_SHARED_PRE);
        return saveData.getData(Util.TRANSACTION_DETAIL_PURCHASE_DATE, Util.DEFAULT_TRANSACTION_DETAIL_PURCHASE_DATE);
    }
}
