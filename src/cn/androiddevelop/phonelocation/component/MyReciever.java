package cn.androiddevelop.phonelocation.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import cn.androiddevelop.phonelocation.util.NetworkStatus;

/**
 * 监听电话广播
 * 
 * @author Yuedong Li
 * 
 */
public class MyReciever extends BroadcastReceiver {
	private String phoneNumber = "";
	private TelephonyManager tm;
	private Context context;
	private static boolean isOutcalling = false;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;

		if (!NetworkStatus.isNetworkAvailable(context))
			return;

		if (intent.getAction() == Intent.ACTION_NEW_OUTGOING_CALL) {
			phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			phoneNumber = numberTrim(phoneNumber);
			System.out.println("phoneNumber0:" + phoneNumber);
			if (phoneNumber != null) {
				isOutcalling = true;
				startService(context, phoneNumber, true);
			}
		}
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

	}

	PhoneStateListener listener = new PhoneStateListener() {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				// System.out.println("IDLE");
				if (isOutcalling)
					isOutcalling = false;
				else
					startService(context, phoneNumber, false);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				isOutcalling = false;
				phoneNumber = incomingNumber;
				startService(context, incomingNumber, true);
				break;
			}
		}
	};

	/**
	 * 启动服务
	 * 
	 * @param context
	 * @param phoneNumber
	 */
	private void startService(Context context, String phoneNumber,
			boolean operation) {
		phoneNumber = numberTrim(phoneNumber);
		Log.i("phoneNumber",  phoneNumber);
		if (phoneNumber.length() == 11) {
			Intent intent = new Intent();
			intent.putExtra("phoneNumber", numberTrim(phoneNumber));
			intent.putExtra("showFlag", operation);
			intent.setAction("myService");
			context.startService(intent);
		}
	}

	/**
	 * 电话号码处理
	 * 
	 * @return
	 */
	private String numberTrim(String phoneNumber) {
		if (phoneNumber != null && phoneNumber.startsWith("+86"))
			phoneNumber = phoneNumber.substring(3);
		return phoneNumber;
	}
}
