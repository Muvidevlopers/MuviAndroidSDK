package com.home.vod.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.home.vod.R;
import com.home.vod.preferences.LanguagePreference;

import static com.home.vod.preferences.LanguagePreference.DEFAULT_VIEW_LESS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VIEW_MORE;
import static com.home.vod.preferences.LanguagePreference.VIEW_LESS;
import static com.home.vod.preferences.LanguagePreference.VIEW_MORE;

/**
 * Created by user on 5/2/2016.
 */
public class ResizableCustomView {
    public static Context mcontext;
    public static boolean isViewMore = false;


    public static void doResizeTextView(Context context, final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {
        mcontext = context;

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }

//        LogUtil.showLog("sanjay:--------","story data"+tv.getText().toString());

        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);



                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
               else if ((tv.getLineCount() <= maxLine && isViewMore == false) || (tv.getLineCount() < maxLine)){
                    LogUtil.showLog("sanjay:--------","story data else if called");
                    tv.setText(tv.getText());
             }

                else {
                    isViewMore = true;
                    if (maxLine > 0 && tv.getLineCount() >= maxLine) {

                        LogUtil.showLog("sanjay:--------","story data maxline "+tv.getLineCount());
                        LogUtil.showLog("BKS","expandtext="+expandText.length());

                        int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);

                        String text = null;
                        try {
                            text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                        } catch (StringIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                        viewMore), TextView.BufferType.SPANNABLE);
                    } else {

                        LogUtil.showLog("sanjay:--------","story data maxline ==========="+tv.getLineCount());
                        int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                        String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                        tv.setText(text);
                        LogUtil.showLog("sanjay:--------","story data maxline ===========text"+text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                        viewMore), TextView.BufferType.SPANNABLE);
                    }
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new MySpannable(false) {

                @Override
                public void onClick(View widget) {

                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        doResizeTextView(mcontext,tv, -1, LanguagePreference.getLanguagePreference(mcontext).getTextofLanguage(VIEW_LESS,DEFAULT_VIEW_LESS), false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        doResizeTextView(mcontext,tv, 2, LanguagePreference.getLanguagePreference(mcontext).getTextofLanguage(VIEW_MORE,DEFAULT_VIEW_MORE), true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }
    public static  class MySpannable extends ClickableSpan{
        private boolean isUnderline = false;

        /**
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderline);
            ds.setColor(mcontext.getResources().getColor(R.color.button_background));

        }
        @Override
        public void onClick(View widget) {

        }
    }
}