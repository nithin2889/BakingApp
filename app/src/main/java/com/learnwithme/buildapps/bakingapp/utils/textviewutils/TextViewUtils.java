package com.learnwithme.buildapps.bakingapp.utils.textviewutils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

/**
 * Created by Nithin on 26/06/2017.
 */

public class TextViewUtils {
    // Src: https://stackoverflow.com/questions/10979821/
    public static void setTextWithSpan(TextView textView, String fullText, String styledText,
                                       StyleSpan style) {
        SpannableStringBuilder sb = new SpannableStringBuilder(fullText);
        int start = fullText.indexOf(styledText);
        int end = start + styledText.length();
        sb.setSpan(style, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(sb);

        /*final SpannableStringBuilder sbs = new SpannableStringBuilder(fullText);
        // Span to make text bold
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        //Span to make text italic
        final StyleSpan iss = new StyleSpan(android.graphics.Typeface.ITALIC);
        // make first 4 characters Bold
        sbs.setSpan(bss, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make last 2 characters Italic
        sbs.setSpan(iss, 4, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        textView.setText(sbs);*/
    }
}