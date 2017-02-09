package xu.xuyingjie.fileserver.service;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import xu.xuyingjie.fileserver.entity.JsonResult;
import xu.xuyingjie.fileserver.utils.MyLogUtils;
/**
 * 文件上传服务
 * @author 许映杰
 *
 */
@Service
public class FileService {
	/**
	 * 上传文件
	 * @param file
	 * @param servletContext
	 * @param chunk
	 * @param chunks
	 * @return
	 */
	public JsonResult<List<String>> upload(
			String fileName,
			MultipartFile file,
			ServletContext servletContext,
			int chunk,int chunks){
		try{
			MyLogUtils.getInstance().log("文件名="+fileName+",分片总数="+chunks+",当前分片="+chunk);
			//创建临时文件储存目录
			File tempsDir = new File(servletContext.getRealPath("resources/temps/"+fileName));
			if(!tempsDir.exists()){
				tempsDir.mkdirs();
			}
			//储存为临时文件
			File tempFile = new File(tempsDir.getAbsolutePath(),fileName+".part"+chunk);
			file.transferTo(tempFile);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
