package cn.jbolt.common.model;

import cn.jbolt.base.JBoltAutoCache;
import cn.jbolt.common.model.base.BasePermission;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
@JBoltAutoCache(keyCache = true,column = "permission_key")
public class Permission extends BasePermission<Permission>{
	
	public static final int LEVEL_1=1;
	public static final int LEVEL_2=2;
	public static final int LEVEL_3=3;
	public static final int LEVEL_4=4;
	public static final int LEVEL_5=5;
	
	
	public static final int OPEN_TYPE_DEFAULT=1;
	public static final int OPEN_TYPE_IFRAME=2;
	public static final int OPEN_TYPE_DIALOG=3;
	public static final int OPEN_TYPE_JBOLT_LAYER=4;
}
