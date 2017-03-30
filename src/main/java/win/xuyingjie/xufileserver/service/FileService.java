package win.xuyingjie.xufileserver.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import win.xuyingjie.xufileserver.entity.JsonResult;
import win.xuyingjie.xufileserver.entity.UploadStatus;
import win.xuyingjie.xufileserver.utils.MyFileUtils;
import win.xuyingjie.xufileserver.utils.MyLogUtils;

/**
 * 文件上传服务
 * @author 许映杰
 *
 */
@Service
public class FileService {
	@Value("${config.fileUrl}")
	private String fileUrl;
	@Value("${config.fileSavePath}")
	private String fileSavePath;
	/**
	 * 上传文件
	 * @param file
	 * @param servletContext
	 * @param chunk
	 * @param chunks
	 * @return
	 */
	public JsonResult<UploadStatus> upload(	String fileName,MultipartFile file,int chunk,int chunks){
		JsonResult<UploadStatus> jsonResult = new JsonResult<>();
		try{
			MyLogUtils.getInstance().log("文件名="+fileName+",分片总数="+chunks+",当前分片索引="+chunk);
			//创建临时文件储存目录
			File tempsDir = new File(fileSavePath+"temps/"+fileName);
			if(!tempsDir.exists()){
				tempsDir.mkdirs();
			}
			//储存为临时文件
			File tempFile = new File(tempsDir.getAbsolutePath(),fileName+".part"+chunk);
			file.transferTo(tempFile);
			//检查分片是否下载完成
			int successChunks = MyFileUtils.getInstance().successChunks(fileName, chunks);
			//构建返回状态信息
			UploadStatus uploadStatus = new UploadStatus();
			if(successChunks==chunks){
				//分片下载完成后合并分片为文件
				MyFileUtils.getInstance().mergeChunks(tempsDir.getAbsolutePath(),
						fileSavePath+"files", fileName, chunks);
				//返回文件地址
				uploadStatus.setStatus("success");
				uploadStatus.setResult(fileUrl+"files/"+fileName);
				jsonResult.setData(uploadStatus);
			}else{
				uploadStatus.setStatus("uploading");
				jsonResult.setData(uploadStatus);
			}
		}catch (Exception e) {
			// TODO: handle exception
			jsonResult.setErrcode(JsonResult.SERVER_ERR);
			jsonResult.setErrmsg(JsonResult.SERVER_ERR_MSG);
			e.printStackTrace();
		}
		return jsonResult;
	}
}
