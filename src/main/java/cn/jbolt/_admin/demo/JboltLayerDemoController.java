package cn.jbolt._admin.demo;

import com.jfinal.core.JFinal;
import com.jfinal.upload.UploadFile;

import cn.jbolt._admin.permission.CheckPermission;
import cn.jbolt._admin.permission.PermissionKey;
import cn.jbolt._admin.permission.UnCheckIfSystemAdmin;
import cn.jbolt.base.JBoltBaseController;
/**
 * Demo演示-JboltLayer组件页面
 * @ClassName:  JboltLayerDemoController   
 * @author: JFinal学院-小木 QQ：909854136 
 * @date:   2019年10月1日   
 */
@CheckPermission(PermissionKey.DEMO)
@UnCheckIfSystemAdmin
public class JboltLayerDemoController extends JBoltBaseController {
	public void index() {
		render("jboltlayer.html");
	}
	
	public void content() {
		render("jboltlayercontent.html");
	}
	
	public void uploadImg(){
		UploadFile file=getFile("file","demo/jboltlayer");
		if(notImage(file)){
			renderJsonFail("请上传图片类型文件");
			return;
		}
		renderJsonData(JFinal.me().getConstants().getBaseUploadPath()+"/demo/jboltlayer/"+file.getFileName());
	}
	
	
	public void demo() {
		render("jboltlayerdemo.html");
	}
	
}
