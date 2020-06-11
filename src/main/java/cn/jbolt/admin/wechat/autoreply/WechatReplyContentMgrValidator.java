package cn.jbolt.admin.wechat.autoreply;

import com.jfinal.core.Controller;

import cn.hutool.core.util.ArrayUtil;
import cn.jbolt.base.para.JBoltValidator;
import cn.jbolt.common.config.Msg;
/**
 * 自动回复规则中的回复内容管理
 * @ClassName:  WechatReplyContentMgrValidator   
 * @author: JFinal学院-小木 QQ：909854136 
 * @date:   2019年6月22日   
 */
public class WechatReplyContentMgrValidator extends JBoltValidator {
	private static final String[] actionNames= new String[]{"edit","delete","up","down","toggleEnable"};

	@Override
	protected void validate(Controller c) {
		validateJBoltInteger(0, Msg.PARAM_ERROR+":微信公众平台autoReplyId");
		String actionName=getActionMethodName();
		if(ArrayUtil.contains(actionNames, actionName)) {
			validateJBoltInteger(1,  Msg.PARAM_ERROR+":数据ID");
		}
	}


}
