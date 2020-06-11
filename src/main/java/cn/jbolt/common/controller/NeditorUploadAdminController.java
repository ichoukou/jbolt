package cn.jbolt.common.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.NoUrlPara;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;

import cn.jbolt._admin.jboltfile.JBoltFileService;
import cn.jbolt._admin.permission.UnCheck;
import cn.jbolt.base.JBoltBaseController;
import cn.jbolt.common.config.JBoltUploadFolder;
import cn.jbolt.common.util.RealUrlUtil;
/**
 * 给系统NEditor组件提供的默认上传路径
 * @ClassName:  NeditorUploadAdminController   
 * @author: JFinal学院-小木 QQ：909854136 
 * @date:   2019年9月1日   
 */
public class NeditorUploadAdminController extends JBoltBaseController {
	@Inject
	private JBoltFileService jboltFileService;
	@UnCheck
	@Before(NoUrlPara.class)
	public void index() {
		renderNull();
	}
	@UnCheck
	public void wordimg(){
		String todayFolder=JBoltUploadFolder.todayFolder();
		String uploadPath=JBoltUploadFolder.EDITOR_NEDITOR_WORD_IMAGE+"/"+todayFolder;
		UploadFile file=null;
		try {
			file= getFile("file", uploadPath);
		} catch (RuntimeException e) {
			renderJsonFail("上传图片尺寸太大");
			return;
		}
		if (file != null && file.getFile() != null&&file.getFile().exists()) {
			if(notImage(file.getContentType())){
				renderJsonFail("请上传图片类型文件");
				return;
			}
			renderJson(jboltFileService.saveImageFile(getSessionAdminUserId(),file,uploadPath));
		}
	}
	@UnCheck
	public void image(){
		String todayFolder=JBoltUploadFolder.todayFolder();
		String uploadPath=JBoltUploadFolder.EDITOR_NEDITOR_IMAGE+"/"+todayFolder;
		UploadFile file=null;
		try {
			file= getFile("file", uploadPath);
		} catch (RuntimeException e) {
			return;
		}
		if (file != null && file.getFile() != null&&file.getFile().exists()) {
			String originalName = file.getOriginalFileName();
			String path = JFinal.me().getConstants().getBaseUploadPath() + "/" + uploadPath + "/"
					+ file.getFileName();
			String webUrl=RealUrlUtil.getImage(path);
			//保存图片类文件
			Ret saveRet=jboltFileService.saveImageFile(getSessionAdminUserId(),file,uploadPath);
			if(saveRet.isOk()) {
				renderJson(Ret.by("code",200)
						.set("state", "SUCCESS")
						.set("url", webUrl)
						.set("original",originalName));
			}
			
		}
		
		
	}
        @UnCheck
        public void video(){
        	String todayFolder=JBoltUploadFolder.todayFolder();
        	String uploadPath=JBoltUploadFolder.EDITOR_NEDITOR_VIDEO+"/"+todayFolder;
            UploadFile file=null;
            try {
            	file= getFile("file", uploadPath);
			} catch (RuntimeException e) {
				return;
			}
    		if (file != null && file.getFile() != null&&file.getFile().exists()) {
                String originalName = file.getOriginalFileName();
                String path = JFinal.me().getConstants().getBaseUploadPath() +"/"+uploadPath+ "/"
                		+ file.getFileName();
                String webUrl=RealUrlUtil.get(path);
                //保存视频文件
                Ret saveRet=jboltFileService.saveVideoFile(getSessionAdminUserId(),file,uploadPath);
                if(saveRet.isOk()) {
                	renderJson(Ret.by("code",200)
                    		.set("state", "SUCCESS")  //下面这几个都是必须返回给ueditor的数据
                    		.set("url", webUrl)  //文件上传后的路径
                            .set("original",originalName));
                }
    		}
            
 
    }
}
