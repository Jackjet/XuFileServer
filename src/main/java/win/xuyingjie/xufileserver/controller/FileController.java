package win.xuyingjie.xufileserver.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import win.xuyingjie.xufileserver.entity.JsonResult;
import win.xuyingjie.xufileserver.entity.UploadStatus;
import win.xuyingjie.xufileserver.service.FileService;
import win.xuyingjie.xufileserver.utils.MyLogUtils;

/**
 * 文件上传控制器
 * @author 许映杰
 *
 */
@RestController
public class FileController{
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
	public JsonResult<UploadStatus> upload(
			@RequestParam(value="fileName") String fileName,
			@RequestParam(value="file") MultipartFile file,
			@RequestParam(value="chunk") int chunk,
			@RequestParam(value="chunks") int chunks){
		
		MyLogUtils.getInstance().log("开始上传文件");
		return fileService.upload(fileName,file,chunk,chunks);
	}
}
