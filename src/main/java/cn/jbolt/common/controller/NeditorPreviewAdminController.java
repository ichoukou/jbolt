package cn.jbolt.common.controller;

import cn.jbolt._admin.permission.UnCheck;
import cn.jbolt.base.JBoltBaseController;
/**
 * Neditor Preview
 * @ClassName:  NeditorPreviewAdminController   
 * @author: JFinal学院-小木 QQ：909854136 
 * @date:   2020年3月20日   
 */
@UnCheck
public class NeditorPreviewAdminController extends JBoltBaseController {
	
	public void index() {
		render("/assets/plugins/neditor/dialogs/preview/preview.html");
	}
	
}
