package cn.androiddevelop.phonelocation.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络操作，可以获取网页源代码，下载网络文件
 * 
 * @author Yuedong Li
 * 
 */
public class UrlUtil {

	/**
	 * 得到网络地址对应的字符串，也即得到网页源代码
	 * 
	 * @param address
	 *            网址
	 * @return 网页源代码
	 * @throws IOException
	 *             IO异常
	 */
	public static String getString(String address) throws IOException {
//		System.out.println("address=" + address);
		URL url = new URL(address);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		BufferedReader buff = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line = buff.readLine();
		buff.close();
		return line;
	}

}
