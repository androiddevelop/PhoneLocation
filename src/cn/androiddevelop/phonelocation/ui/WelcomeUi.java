package cn.androiddevelop.phonelocation.ui;

import cn.androiddevelop.phonelocation.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * 无用界面，3.1以后为了安全起见，必须有Activity才能接受广播，故将该activity启动后关闭
 * 
 * @author Yuedong Li
 * 
 */
public class WelcomeUi extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.welcome);
		this.finish();
	}

}
