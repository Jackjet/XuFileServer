# XuFileServer
文件，视频，图片服务器，上传后返回文件地址，支持大文件分片上传
项目支持文本，二进制文件上传，支持大文件分片上传

## 项目编译打包版本下载:
[编译打包版下载](https://github.com/xu0ying0jie/XuFileServer/blob/master/dest/20170210/dest.zip)

## 使用教程
1.部署dest.zip里面后台war包到tomcat等服务器上
2.运行tomcat后war包会自动解压
3.修改tomcat里面项目XuFileServer/WEB-INF/classes/xu/xuyingjie/fileserver目录下的config.properties配置文件改为你自己的域名端口号
4.重启tomcat

后台rest api：
	url:file/upload
	method:POST
	参数:
		@RequestParam(value="fileName") String fileName, //文件名
		@RequestParam(value="file") MultipartFile file,	//分片文件
		@RequestParam(value="chunk") int chunk,	//当前分片索引
		@RequestParam(value="chunks") int chunks	//总分片数
	文件名最好有拼接随机部分，防止文件重名
	分片文件最好为4M或以下
	分片索引从0开始
	
## 前端JS库使用教程
本项目提供了一个前端的JS封装
dest.zip文件解压里面有一个xuJsLib.js文件
使用方法为
	//从input标签中获取文件对象
	//调用xuJsLib.js里面的myHttp.upload(url,file,uploading,success,error)
	//参数：
		url:文件服务器上传api地址
		file:从input标签获取的文件对象
		uploading:上传中的回调
		success:上传完成回调
		error:上传出错回调
	
	例子：	
	let file = inputFile.files[0];	//inputFile是前端input标签的ID
	myHttp.upload("file/upload",file,function(result){
		console.log("上传中");
		console.log(result);
	},function(result){
		console.log("完成");
		console.log(result);//返回文件的url
	},function(result){
		console.log("错误信息");
	});