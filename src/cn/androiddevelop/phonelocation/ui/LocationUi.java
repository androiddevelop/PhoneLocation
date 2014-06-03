package cn.androiddevelop.phonelocation.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.androiddevelop.phonelocation.R;
import cn.androiddevelop.phonelocation.component.MyService;

public class LocationUi extends LinearLayout implements OnTouchListener {
	private TextView locationInfo;
	public static int x = 0;
	public static int y = 0;
	private int xxStart, xxEnd; // 拖动位置
	private int yyStart, yyEnd; // 拖动位置

	public LocationUi(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.location, this);
		locationInfo = (TextView) findViewById(R.id.location);
		locationInfo.setOnTouchListener(this);
	}

	/**
	 * 设置归属地信息
	 * 
	 * @param text
	 */
	public void setText(String text) {
		locationInfo.setText(text);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			xxStart = (int) event.getX();
			yyStart = (int) event.getY();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			xxEnd = (int) event.getX();
			yyEnd = (int) event.getY();
//			System.out.println("------->xxEnd=" + xxEnd + " yyEnd=" + yyEnd);
			int offsetX = xxEnd - xxStart;
			int offsetY = yyEnd - yyStart;
			if (offsetX * offsetX + offsetY * offsetY > 1600) {
				x = x + offsetX;
				y = y + offsetY;
				xxStart = xxEnd;
				yyStart = yyEnd;
				MyService.refreshLocation();
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
		}
		return true;
	}

}
