package cn.jbolt._admin.demo;

import com.jfinal.aop.Inject;
import com.jfinal.core.JFinal;
import com.jfinal.upload.UploadFile;

import cn.jbolt._admin.jboltfile.JBoltFileService;
import cn.jbolt._admin.permission.CheckPermission;
import cn.jbolt._admin.permission.PermissionKey;
import cn.jbolt._admin.permission.UnCheckIfSystemAdmin;
import cn.jbolt.base.JBoltBaseController;
import cn.jbolt.common.config.JBoltUploadFolder;
/**
 * Demo演示-图片上传
 * @ClassName:  ImgUploaderDemoController   
 * @author: JFinal学院-小木 QQ：909854136 
 * @date:   2019年10月1日   
 */
@CheckPermission(PermissionKey.DEMO)
@UnCheckIfSystemAdmin
public class ImgUploaderDemoController extends JBoltBaseController {
	@Inject
	private JBoltFileService jboltFileService;
	public void index() {
		render("imguploader.html");
	}
	
	public void submit() {
		keepPara();
		setMsg("这是提交表单跳转页面，返回到本页面后的效果，输入框里带着值，图片上传组件data-value='图片地址' 就能显示！");
		render("imguploader.html");
	}
	
	/**
	 * 上传图片
	 */
	public void uploadImage(){
		//上传到今天的文件夹下
		String uploadPath=JBoltUploadFolder.todayFolder(JBoltUploadFolder.DEMO_IMAGE_UPLOADER);
		UploadFile file=getFile("file",uploadPath);
		if(notImage(file)){
			renderJsonFail("请上传图片类型文件");
			return;
		}
		renderJsonData(JFinal.me().getConstants().getBaseUploadPath()+"/"+uploadPath+"/"+file.getFileName());
	}
	
	
}
