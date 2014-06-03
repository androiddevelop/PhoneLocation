package cn.androiddevelop.phonelocation.component;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import cn.androiddevelop.phonelocation.ui.LocationUi;
import cn.androiddevelop.phonelocation.util.PhoneNumber;

/**
 * 联网查询手机号码信息
 * 
 * @author Yuedong Li
 * 
 */
public class MyService extends Service {
	private static WindowManager windowManager;
	private String locationMsg;
	private static LocationUi location;
	private static WindowManager.LayoutParams params;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		final String phoneNumber = intent.getStringExtra("phoneNumber");
		final boolean showFlag = intent.getBooleanExtra("showFlag", false);

		if (phoneNumber.length() == 11) {
			if (!showFlag) {
				if (windowManager != null) {
					windowManager.removeView(location);
				}
				windowManager = null;
				location = null;
				this.stopSelf();
			} else {
				if (windowManager == null) {
					windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
					params = new WindowManager.LayoutParams();
					params.type = LayoutParams.TYPE_PHONE;
					params.format = PixelFormat.RGBA_8888;
					params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
							| LayoutParams.FLAG_NOT_FOCUSABLE;
					params.gravity = Gravity.CENTER;
					params.y = -150;
					location = new LocationUi(this);
					Thread thread = new Thread() {
						public void run() {
							try {
								locationMsg = PhoneNumber
										.getLocation(phoneNumber);
							} catch (Exception e) {
								e.printStackTrace();
								locationMsg = "未知号码";
							}
						}
					};
					thread.start();
					try {
						thread.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					calcLength(locationMsg);
					int height = location.getMeasuredHeight();
					int width = location.getMeasuredWidth();

					// 须指定宽度高度信息
					params.width = width;
					params.height = height;

					params.x = LocationUi.x;
					params.y = LocationUi.y;

					windowManager.addView(location, params);
				}
			}

		}

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 计算悬浮窗的大小
	 * 
	 * @param locationMsg
	 */
	private void calcLength(String locationMsg) {
		location.setText(locationMsg);
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		location.measure(w, h);
	}

	/**
	 * 更新位置
	 */
	public static void refreshLocation() {
		params.x = LocationUi.x;
		params.y = LocationUi.y;
		windowManager.updateViewLayout(location, params);
	}
}
