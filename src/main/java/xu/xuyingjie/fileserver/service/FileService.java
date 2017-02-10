package xu.xuyingjie.fileserver.service;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import xu.xuyingjie.fileserver.entity.JsonResult;
import xu.xuyingjie.fileserver.entity.UploadStatus;
import xu.xuyingjie.fileserver.utils.MyFileUtils;
import xu.xuyingjie.fileserver.utils.MyLogUtils;
/**
 * 文件上传服务
 * @author 许映杰
 *
 */
@Service
public class FileService {
	@Value("#{configProperties['BASE_URL']}")
	private String BASE_URL;
	/**
	 * 上传文件
	 * @param file
	 * @param servletContext
	 * @param chunk
	 * @param chunks
	 * @return
	 */
	public JsonResult<UploadStatus> upload(
			String fileName,
			MultipartFile file,
			ServletContext servletContext,
			int chunk,int chunks){
		JsonResult<UploadStatus> jsonResult = new JsonResult<>();
		try{
			MyLogUtils.getInstance().log("文件名="+fileName+",分片总数="+chunks+",当前分片索引="+chunk);
			//创建临时文件储存目录
			File tempsDir = new File(servletContext.getRealPath("resources/temps/"+fileName));
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
						servletContext.getRealPath("resources/files"), fileName, chunks);
				//返回文件地址
				uploadStatus.setStatus("success");
				uploadStatus.setResult(BASE_URL+"/resources/files/"+fileName);
				jsonResult.setData(uploadStatus);
			}else{
				uploadStatus.setStatus("uploading");
				jsonResult.setData(uploadStatus);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jsonResult;
	}
}
