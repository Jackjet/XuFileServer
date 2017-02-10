window.onload = function(){
	
}

let upload = function(){
	//获取文件以及文件名称，大小信息
	let file = inputFile.files[0];
	
	myHttp.upload("file/upload",file,function(result){
		console.log("上传中");
		console.log(result);
	},function(result){
		console.log("完成");
		console.log(result);
	},function(result){
		console.log("错误信息");
	});
}