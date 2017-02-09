package xu.xuyingjie.fileserver.service;

import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xu.xuyingjie.fileserver.entity.JsonResult;
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
			MultipartFile file,
			ServletContext servletContext,
			int chunk,int chunks){
		return null;
	}
}
