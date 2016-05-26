package com.coolweather.app.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coolweather.app.R;
import com.coolweather.app.model.Data;
import com.coolweather.app.model.Forecast;
import com.coolweather.app.model.MyPagerAdapter;
import com.coolweather.app.model.YesterdayInfo;

//import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MoreReportActivity extends FragmentActivity implements
		OnClickListener {
	/**
	 * 初始化成员变量
	 */
	private List<View> viewList;
	private List<String> tabList;
	private ViewPager viewPager;
	private PagerTabStrip tab;
	private Handler handler;
	private String url;
	private SharedPreferences prefs;
	private Editor editor;
	private SimpleDateFormat sdf;
	private MyPagerAdapter adapter;
	private TextView page1_tv_type, page1_tv_low, page1_tv_high, page1_tv_wind;
	private TextView page2_tv_type, page2_tv_low, page2_tv_high, page2_tv_wind;
	private TextView page2_tv_ganmao;
	private TextView page3_tv_type, page3_tv_low, page3_tv_high, page3_tv_wind;
	private TextView page4_tv_type, page4_tv_low, page4_tv_high, page4_tv_wind;
	private TextView page5_tv_type, page5_tv_low, page5_tv_high, page5_tv_wind;
	private TextView page6_tv_type, page6_tv_low, page6_tv_high, page6_tv_wind;
	private LayoutInflater inflater;
	private LayoutAnimationController lac;

	private String weatherCode;
	private Button btn_home, btn_quit;
	private TextView tv_cityName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_report_layout);
		/**
		 * 实例化成员变量
		 */
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		editor = prefs.edit();
		sdf = new SimpleDateFormat("yyyy年M月d日  E", Locale.CHINA);
		Intent intent = getIntent();
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		tabList = new ArrayList<String>();
		tab = (PagerTabStrip) findViewById(R.id.tab);
		tab.setTabIndicatorColor(Color.BLUE);
		tab.setTextColor(Color.YELLOW);
		tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
		tab.setDrawFullUnderline(false);
		tab.setTabIndicatorColor(Color.BLUE);
		btn_home = (Button) findViewById(R.id.more_switch_city);
		btn_quit = (Button) findViewById(R.id.more_quit);
		tv_cityName = (TextView) findViewById(R.id.more_city_name);
		lac = new LayoutAnimationController(AnimationUtils.loadAnimation(
				MoreReportActivity.this, R.anim.anim_zoom_in));
		lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
		lac.setInterpolator(new OvershootInterpolator());

		btn_home.setOnClickListener(this);
		btn_quit.setOnClickListener(this);
		inflater = getLayoutInflater();
		View view1 = inflater.inflate(R.layout.page1, null);
		View view2 = inflater.inflate(R.layout.page2, null);
		View view3 = inflater.inflate(R.layout.page3, null);
		View view4 = inflater.inflate(R.layout.page4, null);
		View view5 = inflater.inflate(R.layout.page5, null);
		View view6 = inflater.inflate(R.layout.page6, null);
		viewList = new ArrayList<View>();
		viewList.add(0, view1);
		viewList.add(1, view2);
		viewList.add(2, view3);
		viewList.add(3, view4);
		viewList.add(4, view5);
		viewList.add(5, view6);

		page1_tv_wind = (TextView) view1.findViewById(R.id.page1_wind);
		page1_tv_high = (TextView) view1.findViewById(R.id.page1_temphigh);
		page1_tv_low = (TextView) view1.findViewById(R.id.page1_templow);
		page1_tv_type = (TextView) view1.findViewById(R.id.page1_weather_type);
		// 给weather_info_layout_page1设置动画效果
		RelativeLayout weather_info_layout_page1 = (RelativeLayout) view1
				.findViewById(R.id.weather_info_layout_page1);
		weather_info_layout_page1.setLayoutAnimation(lac);
		weather_info_layout_page1.startLayoutAnimation();

		page2_tv_wind = (TextView) view2.findViewById(R.id.page2_wind);
		page2_tv_high = (TextView) view2.findViewById(R.id.page2_temphigh);
		page2_tv_low = (TextView) view2.findViewById(R.id.page2_templow);
		page2_tv_type = (TextView) view2.findViewById(R.id.page2_weather_type);
		page2_tv_ganmao = (TextView) view2.findViewById(R.id.page2_ganmao);
		// 给weather_info_layout_page2设置动画效果
		RelativeLayout weather_info_layout_page2 = (RelativeLayout) view2
				.findViewById(R.id.weather_info_layout_page2);
		weather_info_layout_page2.setLayoutAnimation(lac);
		weather_info_layout_page2.startLayoutAnimation();

		page3_tv_wind = (TextView) view3.findViewById(R.id.page3_wind);
		page3_tv_high = (TextView) view3.findViewById(R.id.page3_temphigh);
		page3_tv_low = (TextView) view3.findViewById(R.id.page3_templow);
		page3_tv_type = (TextView) view3.findViewById(R.id.page3_weather_type);
		// 给weather_info_layout_page3设置动画效果
		RelativeLayout weather_info_layout_page3 = (RelativeLayout) view3
				.findViewById(R.id.weather_info_layout_page3);
		weather_info_layout_page3.setLayoutAnimation(lac);
		weather_info_layout_page3.startLayoutAnimation();

		page4_tv_wind = (TextView) view4.findViewById(R.id.page4_wind);
		page4_tv_high = (TextView) view4.findViewById(R.id.page4_temphigh);
		page4_tv_low = (TextView) view4.findViewById(R.id.page4_templow);
		page4_tv_type = (TextView) view4.findViewById(R.id.page4_weather_type);
		// 给weather_info_layout_page4设置动画效果
		RelativeLayout weather_info_layout_page4 = (RelativeLayout) view4
				.findViewById(R.id.weather_info_layout_page4);
		weather_info_layout_page4.setLayoutAnimation(lac);
		weather_info_layout_page4.startLayoutAnimation();

		page5_tv_wind = (TextView) view5.findViewById(R.id.page5_wind);
		page5_tv_high = (TextView) view5.findViewById(R.id.page5_temphigh);
		page5_tv_low = (TextView) view5.findViewById(R.id.page5_templow);
		page5_tv_type = (TextView) view5.findViewById(R.id.page5_weather_type);
		// 给weather_info_layout_page5设置动画效果
		RelativeLayout weather_info_layout_page5 = (RelativeLayout) view5
				.findViewById(R.id.weather_info_layout_page5);
		weather_info_layout_page5.setLayoutAnimation(lac);
		weather_info_layout_page5.startLayoutAnimation();

		page6_tv_wind = (TextView) view6.findViewById(R.id.page6_wind);
		page6_tv_high = (TextView) view6.findViewById(R.id.page6_temphigh);
		page6_tv_low = (TextView) view6.findViewById(R.id.page6_templow);
		page6_tv_type = (TextView) view6.findViewById(R.id.page6_weather_type);
		// 给weather_info_layout_page6设置动画效果
		RelativeLayout weather_info_layout_page6 = (RelativeLayout) view6
				.findViewById(R.id.weather_info_layout_page6);
		weather_info_layout_page6.setLayoutAnimation(lac);
		weather_info_layout_page6.startLayoutAnimation();

		/**
		 * 新开一个子线程，用于发送HttpURL请求，从而获得Json字符串
		 */
		new Thread() {
			public void run() {

				weatherCode = prefs.getString("weather_code", "");
				url = "http://wthrcdn.etouch.cn/weather_mini?citykey="
						+ weatherCode;
				String result = "";
				try {
					String line = "";
					URL httpUrl = new URL(url);
					HttpURLConnection conn = (HttpURLConnection) httpUrl
							.openConnection();

					InputStreamReader isr = new InputStreamReader(
							conn.getInputStream(), "utf-8");
					BufferedReader br = new BufferedReader(isr);
					while ((line = br.readLine()) != null) {
						result += line;
					}
					isr.close();
					br.close();
					conn.disconnect();

					Message msg = handler.obtainMessage();
					Bundle data = new Bundle();
					data.putString("result", result);
					msg.setData(data);

					handler.sendMessage(msg);

				} catch (MalformedURLException e) { // TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IOException e) { // TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			};
		}.start();

		/**
		 * 接收子线程传过来的Json字符串，并调用parseJsonString()方法，解析Json字符串到SharedPreferences
		 */
		handler = new Handler() {
			public void handleMessage(Message msg) {
				Bundle data = msg.getData();
				String jsonString = data.getString("result");
				Log.i("info", jsonString);
				parseJsonString(jsonString);
				tabList.add(0, prefs.getString("yesterdayDate", "1"));
				tabList.add(1, prefs.getString("forecast_Date0", "3"));
				tabList.add(2, prefs.getString("forecast_Date1", "4"));
				tabList.add(3, prefs.getString("forecast_Date2", "5"));
				tabList.add(4, prefs.getString("forecast_Date3", "6"));
				tabList.add(5, prefs.getString("forecast_Date4", "7"));

				tv_cityName.setText(prefs.getString("todayCity", ""));
				page1_tv_wind.setText(prefs.getString("yesterdayFx", "yes")
						+ prefs.getString("yesterdayFl", ""));
				page1_tv_high.setText(prefs.getString("yesterdayHigh", ""));
				page1_tv_low.setText(prefs.getString("yesterdayLow", ""));
				page1_tv_type.setText(prefs.getString("yesterdayType", ""));

				page2_tv_wind.setText(prefs
						.getString("forecast_Fengxiang0", "")
						+ prefs.getString("forecast_Fengli0", ""));
				page2_tv_high.setText(prefs.getString("forecast_High0", ""));
				page2_tv_low.setText(prefs.getString("forecast_Low0", ""));
				page2_tv_type.setText(prefs.getString("weather_desp", ""));
				page2_tv_ganmao.setText(prefs.getString("todayGanmao", ""));

				page3_tv_wind.setText(prefs
						.getString("forecast_Fengxiang1", "")
						+ prefs.getString("forecast_Fengli1", ""));
				page3_tv_high.setText(prefs.getString("forecast_High1", ""));
				page3_tv_low.setText(prefs.getString("forecast_Low1", ""));
				page3_tv_type.setText(prefs.getString("forecast_Type1", ""));

				page4_tv_wind.setText(prefs
						.getString("forecast_Fengxiang2", "")
						+ prefs.getString("forecast_Fengli2", ""));
				page4_tv_high.setText(prefs.getString("forecast_High2", ""));
				page4_tv_low.setText(prefs.getString("forecast_Low2", ""));
				page4_tv_type.setText(prefs.getString("forecast_Type2", ""));

				page5_tv_wind.setText(prefs
						.getString("forecast_Fengxiang3", "")
						+ prefs.getString("forecast_Fengli3", ""));
				page5_tv_high.setText(prefs.getString("forecast_High3", ""));
				page5_tv_low.setText(prefs.getString("forecast_Low3", ""));
				page5_tv_type.setText(prefs.getString("forecast_Type3", ""));

				page6_tv_wind.setText(prefs
						.getString("forecast_Fengxiang4", "")
						+ prefs.getString("forecast_Fengli4", ""));
				page6_tv_high.setText(prefs.getString("forecast_High4", ""));
				page6_tv_low.setText(prefs.getString("forecast_Low4", ""));
				page6_tv_type.setText(prefs.getString("forecast_Type4", ""));

				/**
				 * 给viewPager设置适配器
				 */
				adapter = new MyPagerAdapter(viewList, tabList);
				viewPager.setAdapter(adapter);
			};
		};

	}

	/**
	 * 自定义一个方法，解析Json字符串到SharedPrefrence
	 * 
	 * @param str
	 */
	public void parseJsonString(String str) {

		try {

			JSONObject jsonObject1 = new JSONObject(str);
			if (jsonObject1 != null) {
				String desc = jsonObject1.getString("desc");
				if (desc.equals("OK")) {
					JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
					if (jsonObject2 != null) {
						Data today = new Data();
						today.setWendu(jsonObject2.getString("wendu"));
						today.setGanmao(jsonObject2.getString("ganmao"));
						today.setCity(jsonObject2.getString("city"));
						String todayWendu = today.getWendu();
						String todayGanmao = today.getGanmao();
						String todayCity = today.getCity();

						editor.putString("todayWendu", todayWendu);
						editor.putString("todayGanmao", todayGanmao);
						editor.putString("todayCity", todayCity);
						editor.commit();
						JSONObject jsonObject3 = jsonObject2
								.getJSONObject("yesterday");

						if (jsonObject3 != null) {
							YesterdayInfo yesterday = new YesterdayInfo();
							yesterday.setDate(jsonObject3.getString("date"));
							yesterday.setFl(jsonObject3.getString("fl"));
							yesterday.setFx(jsonObject3.getString("fx"));
							yesterday.setHigh(jsonObject3.getString("high"));
							yesterday.setLow(jsonObject3.getString("low"));
							yesterday.setType(jsonObject3.getString("type"));

							String yesterdayDate = yesterday.getDate();
							String yesterdayFl = yesterday.getFl();
							String yesterdayFx = yesterday.getFx();
							String yesterdayHigh = yesterday.getHigh();
							String yesterdayLow = yesterday.getLow();
							String yesterdayType = yesterday.getType();

							editor.putString("yesterdayDate", yesterdayDate);
							editor.putString("yesterdayFl", yesterdayFl);
							editor.putString("yesterdayFx", yesterdayFx);
							editor.putString("yesterdayHigh", yesterdayHigh);
							editor.putString("yesterdayLow", yesterdayLow);
							editor.putString("yesterdayType", yesterdayType);
							editor.commit();

						}
						JSONArray jsonArray1 = jsonObject2
								.getJSONArray("forecast");
						if (jsonArray1 != null) {
							ArrayList<Forecast> forecastList = new ArrayList<Forecast>();
							ArrayList<String> fengxiangList = new ArrayList<String>();
							ArrayList<String> fengliList = new ArrayList<String>();
							ArrayList<String> typeList = new ArrayList<String>();
							ArrayList<String> lowList = new ArrayList<String>();
							ArrayList<String> highList = new ArrayList<String>();
							ArrayList<String> dateList = new ArrayList<String>();
							for (int i = 0; i < jsonArray1.length(); i++) {
								Forecast forecast = new Forecast();

								forecast.setFengxiang(jsonArray1.getJSONObject(
										i).getString("fengxiang"));
								forecast.setFengli(jsonArray1.getJSONObject(i)
										.getString("fengli"));
								forecast.setType(jsonArray1.getJSONObject(i)
										.getString("type"));
								forecast.setLow(jsonArray1.getJSONObject(i)
										.getString("low"));
								forecast.setHigh(jsonArray1.getJSONObject(i)
										.getString("high"));
								forecast.setDate(jsonArray1.getJSONObject(i)
										.getString("date"));

								fengxiangList.add(i, forecast.getFengxiang());
								fengliList.add(i, forecast.getFengli());
								typeList.add(i, forecast.getType());
								lowList.add(i, forecast.getLow());
								highList.add(i, forecast.getHigh());
								dateList.add(i, forecast.getDate());

								editor.putString("forecast_Fengxiang" + i,
										fengxiangList.get(i).toString());
								editor.putString("forecast_Fengli" + i,
										fengliList.get(i).toString());
								editor.putString("forecast_Type" + i, typeList
										.get(i).toString());
								editor.putString("forecast_Low" + i, lowList
										.get(i).toString());
								editor.putString("forecast_High" + i, highList
										.get(i).toString());
								editor.putString("forecast_Date" + i, dateList
										.get(i).toString());
								editor.commit();
								forecastList.add(forecast);
							}

						}

					}

				}

			}

		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	/**
	 * 为两个按钮设置点击事件
	 */
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.more_switch_city:
			Intent intent1 = new Intent(this, WeatherActivity.class);
			startActivity(intent1);
			finish();
			break;
		case R.id.more_quit:
			System.exit(0);
			break;
		}

	}

	/**
	 * 设置物理返回键
	 */
	@Override
	public void onBackPressed() {
		// TODO 自动生成的方法存根
		finish();
	}

}
