let AJAX_DONE = 4;	//已经向服务器发送了请求
let AJAX_OK = 200;	//服务器返回正确码
let FILE_SIZE_4M = 4*1024*1024;//文件大小4M
/**
 * ajax请求
 */
let myHttp = {
		upload:function(url,file,callback){
			let fileName = file.name;
			let fileSize = file.size;
			//如果文件大小小于4M，则直接上传
			if(fileSize<=FILE_SIZE_4M){
				//构建from表单
				let formData = new FormData();
				formData.append("file",file);
				formData.append("chunk",0);
				formData.append("chunks",1);
				//获取时间戳，拼接文件名，防止文件重名
				let timestamp = new Date().getTime();
				formData.append("fileName",timestamp+fileName);
				//使用ajax上传文件
				let xmlHttpRequest = new XMLHttpRequest();
				xmlHttpRequest.open("POST",url);
				xmlHttpRequest.send(formData);
				//请求回调
				xmlHttpRequest.onreadystatechange = function(){
					if(xmlHttpRequest.readyState==AJAX_DONE){
						if(xmlHttpRequest.status==AJAX_OK){
							callback(xmlHttpRequest.reponseText);
						}else{
							callback(undefined,xmlHttpRequest.status);
						}
					}
				}
			}else{//如果文件大于4M，则采取分片式上传，以4M为一片
				//计算文件总片数
				let chunks = Math.ceil(fileSize/FILE_SIZE_4M);
				//获取时间戳，拼接文件名，防止文件重名
				let timestamp = new Date().getTime();
				//循环对文件进行分片
				for(let i=0; i<chunks; i++){
					//计算每片文件起始和结束的位置
					let fileStart = i*FILE_SIZE_4M;
					let fileEnd = Math.min(fileSize,fileStart+FILE_SIZE_4M);
					//构建from表单
					let formData = new FormData();
					formData.append("file",file.slice(fileStart,fileEnd));
					formData.append("chunk",i);
					formData.append("chunks",chunks);
					formData.append("fileName",timestamp+fileName);
					//使用ajax上传文件
					let xmlHttpRequest = new XMLHttpRequest();
					xmlHttpRequest.open("POST",url);
					xmlHttpRequest.send(formData);
					//请求回调
					xmlHttpRequest.onreadystatechange = function(){
						if(xmlHttpRequest.readyState==AJAX_DONE){
							if(xmlHttpRequest.status==AJAX_OK){
								callback(xmlHttpRequest.reponseText);
							}else{
								callback(undefined,xmlHttpRequest.status);
							}
						}
					}
				}
			}
		}
}