package cn.androiddevelop.phonelocation.util;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import cn.androiddevelop.phonelocation.config.Config;

/**
 * 手机号码归属地
 * 
 * @author Yuedong Li
 * 
 */
public class PhoneNumber {

	public static String getLocation(String phoneNumber) throws IOException,
	JSONException {
		String location = "未知号码";
		if (phoneNumber.length() == 11) {
			String address = Config.QUERY_ADDRESS+ phoneNumber ;
			String webSource = UrlUtil.getString(address);
			JSONObject json = new JSONObject(webSource);
			json = json.getJSONObject("data");
			String province = json.getString("province");
			String city = json.getString("city");
			if (!province.equals(city)) {
				city = province + city;
			}
			location = city.trim() + " "
					+ json.getString("sp");
			location = location.trim();
		}
		return location;
	}
}