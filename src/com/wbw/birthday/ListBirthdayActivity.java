package com.wbw.birthday;

import java.util.ArrayList;
import java.util.List;

import com.wbw.birthday.calender.LunarCalendar;
import com.wbw.birthday.info.BirthdayInfo;
import com.wbw.birthday.widget.ListBirthdayListAdapter;








import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListBirthdayActivity  extends Activity{
	private LunarCalendar lcCalendar = null;
	ArrayList<String> scheduleDate = new ArrayList<String>();
	String scheduleDay ;  //这一天的阳历
	String scheduleYear;
    String scheduleMonth;
    String week;
    String chinese_year;
    private String dateInfo_yangli,dateInfo_yinli;
    private List<BirthdayInfo> getlist ;
    private BaseAdapter adapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listbirthday);
		Bundle bundle = getIntent().getExtras();
		scheduleDate = bundle.getStringArrayList("info");
		scheduleYear =  scheduleDate.get(0);
		scheduleMonth =  scheduleDate.get(1);
		scheduleDay =  scheduleDate.get(2);
		week =  scheduleDate.get(3);
		chinese_year = scheduleDate.get(4);
		dateInfo_yangli=scheduleYear+"年"+scheduleMonth+"月"+scheduleDay+"日";
       	//添加农历信息	
		dateInfo_yinli = getLunarDay(Integer.parseInt(scheduleYear),
 				Integer.parseInt(scheduleMonth), Integer.parseInt(scheduleDay));
		
		findAllViews();
		setStrings();
		createAction();
	}
	
	private ImageView iv_return,iv_add;
	private TextView tv_title;
	private TextView tv_dayandmonth,tv_dayofweek,tv_lunaryear,tv_list_title;
	private ListView lv_list;
	private void findAllViews(){
		iv_return = (ImageView) findViewById(R.id.iv_return);
		iv_add = (ImageView) findViewById(R.id.iv_add);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_dayandmonth = (TextView) findViewById(R.id.tv_dayandmonth);
		tv_dayofweek = (TextView) findViewById(R.id.tv_dayofweek);
		tv_lunaryear = (TextView) findViewById(R.id.tv_lunaryear);
		tv_list_title = (TextView) findViewById(R.id.tv_list_title);
		lv_list = (ListView) findViewById(R.id.lv_list);
	}
	
	private void setStrings(){
		tv_title.setText(dateInfo_yangli);
		tv_dayandmonth.setText(dateInfo_yinli);
		tv_lunaryear.setText(chinese_year);
		tv_dayofweek.setText(week);
		adapter = new ListBirthdayListAdapter(ListBirthdayActivity.this,getlist);
		lv_list.setAdapter(adapter);
	}
	

	@Override
	protected void onResume() {		
		
		getlist = lcCalendar.matchScheduleDateList(Integer.parseInt(scheduleYear),
 				Integer.parseInt(scheduleMonth), Integer.parseInt(scheduleDay));
		if(getlist.size()<=0){
			tv_list_title.setText(R.string.noanpai);
		}else{
			tv_list_title.setText(R.string.anpai);
			
		}
		adapter = new ListBirthdayListAdapter(ListBirthdayActivity.this,getlist);
		lv_list.setAdapter(adapter);
		super.onResume();
	}
	
	private void createAction(){
		iv_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
				ListBirthdayActivity.this.finish();
			}
		});
		
		iv_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  Intent intent = new Intent(ListBirthdayActivity.this,AddBirthdayActivity.class);
                  Bundle b = new Bundle();
                  b.putStringArrayList("info",scheduleDate);
                  intent.putExtras(b);
                  ListBirthdayActivity.this.startActivity(intent);
			}
		});
	}
	
	/**
	 * 根据日期的年月日返回阴历日期
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public String getLunarDay(int year, int month, int day) {
		lcCalendar=new LunarCalendar();
		String lunar = lcCalendar.getLunarDate(year, month, day);
		return lunar;
	}
	
	

}
