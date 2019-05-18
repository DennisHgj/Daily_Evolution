package com.example.daliyevolution.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * CashierinputFliter
 * confirm the cash input format
 * @author Guanjie Huang
 * @ID u6532079
 */
public class CashierInputFilter implements InputFilter{
    Pattern mPattern;
    //Input MAX
    private static final int MAX_VALUE = Integer.MAX_VALUE;
    //Pointer_length
    private static final int POINTER_LENGTH = 2;

    private static final String POINTER = ".";

    private static final String ZERO = "0";

    public CashierInputFilter() {
        mPattern = Pattern.compile("([0-9]|\\.)*");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();
        //test bottoms
        if (TextUtils.isEmpty(sourceText)) {
            return "";
        }
        Matcher matcher = mPattern.matcher(source);
        if(destText.contains(POINTER)) {
            if (!matcher.matches()) {
                return "";
            } else {
                if (POINTER.equals(source)) {  //only one point permit
                    return "";
                }
            }
            //Verify the decimal point precision and ensure that only two digits can be entered after the decimal point
            int index = destText.indexOf(POINTER);
            int length = destText.length() - index;
            //If the length is greater than 2, and the newly inserted character is after the decimal point
            if (length > POINTER_LENGTH && index<dstart) {
                //return null
                return "";
            }
        } else {
            //If you do not enter a decimal point, you can only enter a decimal point and a number, but you cannot enter a decimal point in the first place.
            if (!matcher.matches()) {
                return "";
            } else {
                if ((POINTER.equals(source)) && TextUtils.isEmpty(destText)) {
                    return "";
                }
            }
        }
        //Verify the size of the input amount
        double sumText = Double.parseDouble(destText + sourceText);
        if (sumText > MAX_VALUE) {
            return dest.subSequence(dstart, dend);
        }
        return dest.subSequence(dstart, dend) + sourceText;
    }
}
