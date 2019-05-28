package com.rong360.example;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "====";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(String s, Context context, AttributeSet attributeSet) {
                return null;
            }

            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);

                Log.i(TAG, "name = " + name);
                int n = attrs.getAttributeCount();
                for (int i = 0; i < n; i++) {
                    //Log.i(TAG, attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
                }
                if("ModuleWrapperView".equals(name)){
                    view = new Button(context, attrs);
                }
                return view;
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_example);
    }
}
