package com.coolweather.app.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

public class MyTextView extends TextView {

	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
	}

	public MyTextView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}

	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		// TODO 自动生成的方法存根
		return true;
	}
}
