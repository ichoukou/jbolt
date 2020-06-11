package cn.jbolt.common.model;

import cn.jbolt.base.JBoltEnum;
import cn.jbolt.base.JBoltLoginState;
import cn.jbolt.common.model.base.BaseLoginLog;

/**
 * 登录日志
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class LoginLog extends BaseLoginLog<LoginLog> {
	
	public String getLoginStateName() {
		return JBoltEnum.getTextByValue(JBoltLoginState.class, getLoginState());
	}
}