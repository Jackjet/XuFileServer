package xu.xuyingjie.fileserver.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xu.xuyingjie.fileserver.service.FileService;
import xu.xuyingjie.fileserver.utils.MyLogUtils;
/**
 * 测试控制器
 * @author 许映杰
 *
 */
@Controller
public class TestController{
	@Resource
	private FileService fileService;
	/**
	 * 测试
	 */
	@RequestMapping(value="test",method=RequestMethod.GET)
	@ResponseBody
	public String test(){
		MyLogUtils.getInstance().log("测试");
		return "测试成功";
	}
}
