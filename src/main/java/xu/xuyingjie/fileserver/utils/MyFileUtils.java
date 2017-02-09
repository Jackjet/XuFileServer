package xu.xuyingjie.fileserver.utils;

import java.util.concurrent.ConcurrentHashMap;
/**
 * 分片文件处理工具类
 * @author 许映杰
 *
 */
public class MyFileUtils {
	//记录已经上传成功的分片文件数量，线程安全
	private static ConcurrentHashMap<String,Integer> successChunksCount = new ConcurrentHashMap<>();
	private static MyFileUtils instance = new MyFileUtils();
	
	public static MyFileUtils getInstance(){
		return instance;
	}
	
	/**
	 * 每上传成功一个文件的分片，就在文件名对应的值加1
	 * 对比分片总数，如果上传成功分片数等于分片总数则调用合并文件方法
	 * @param fileName
	 * @param chunks
	 * @return
	 */
	public int successChunks(String fileName,int chunks){
		int chunksNow = successChunksCount.getOrDefault(fileName, 0);
		chunksNow ++;
		if(chunks==chunksNow){
			successChunksCount.remove(fileName);
		}else{
			successChunksCount.put(fileName, chunksNow);
		}
		return chunksNow;
	}
}
