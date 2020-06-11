package cn.jbolt.common.model;

import cn.jbolt.base.JBoltAutoCache;
import cn.jbolt.base.UnProcessBoolean;
import cn.jbolt.common.model.base.BaseUserConfig;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
@UnProcessBoolean
@JBoltAutoCache(idCache = false,keyCache = true,column = "config_key",bindColumn = "user_id")
public class UserConfig extends BaseUserConfig<UserConfig> {
	public static final String TYPE_STRING="string";
	public static final String TYPE_INT="int";
	public static final String TYPE_DECIMAL="decimal";
	public static final String TYPE_BOOLEAN="boolean";
}
