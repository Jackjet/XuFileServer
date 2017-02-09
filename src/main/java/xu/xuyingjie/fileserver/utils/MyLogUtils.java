package xu.xuyingjie.fileserver.utils;

import java.text.SimpleDateFormat;
/**
 * 打印日志类
 * @author 许映杰
 *
 */
public class MyLogUtils {
	private static MyLogUtils instance = new MyLogUtils();
	public static MyLogUtils getInstance(){
		return instance;
	}
	/**
	 * 打印日志(包含应用名和时间)
	 * @param msg
	 */
	public void log(String msg){
		String strTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis());
		System.out.println("文件服务器:"+strTime+"=>"+msg);
	}
}
