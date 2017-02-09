package xu.xuyingjie.fileserver.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import xu.xuyingjie.fileserver.entity.JsonResult;
import xu.xuyingjie.fileserver.service.FileService;
import xu.xuyingjie.fileserver.utils.MyLogUtils;
/**
 * 文件上传控制器
 * @author 许映杰
 *
 */
@Controller
public class FileController implements ServletContextAware{
	private ServletContext servletContext;	//用于获取项目路径，来创建储存文件路径
	@Resource
	private FileService fileService;
	/**
	 * 上传文件
	 * @param file		//上传的文件
	 * @param chunk		//分片文件当前的分片
	 * @param chunks	//分片文件的总片数
	 * @return
	 */
	@RequestMapping(value="file/upload",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult<String> upload(
			@RequestParam(value="fileName") String fileName,
			@RequestParam(value="file") MultipartFile file,
			@RequestParam(value="chunk") int chunk,
			@RequestParam(value="chunks") int chunks){
		
		MyLogUtils.getInstance().log("开始上传文件");
		return fileService.upload(fileName,file,servletContext,chunk,chunks);
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}
	
	
}
