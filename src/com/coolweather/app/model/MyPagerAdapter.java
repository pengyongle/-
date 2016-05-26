package com.coolweather.app.model;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter {

	private List<View> viewList;
	private List<String> tabList;

	public MyPagerAdapter(List<View> viewList, List<String> tabList) {
		this.viewList = viewList;
		this.tabList = tabList;
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO 自动生成的方法存根
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		container.addView(viewList.get(position));

		return viewList.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO 自动生成的方法存根
		container.removeView(viewList.get(position));
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO 自动生成的方法存根
		return tabList.get(position);
	}

}
