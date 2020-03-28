package com.example.farmfresh;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSrc;
        public PasswordCharSequence(CharSequence source) {
            //Store the character sequence
            mSrc = source;
        }
        public char charAt(int index) {
            //Replace characters with '*'
            return '*';
        }
        public int length() {
            //Default return
            return mSrc.length();
        }
        public CharSequence subSequence(int start, int end) {
            return mSrc.subSequence(start, end);
        }
    }
};