package cn.jbolt.admin.wechat.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.UserApi;

import cn.hutool.extra.emoji.EmojiUtil;
import cn.jbolt._admin.user.UserService;
import cn.jbolt.admin.appdevcenter.ApplicationService;
import cn.jbolt.admin.wechat.mpinfo.WechatMpinfoService;
import cn.jbolt.admin.wechat.mpinfo.WechatMpinfoType;
import cn.jbolt.base.JBoltBaseRecordTableSeparateService;
import cn.jbolt.base.api.JBoltApiBindUserBean;
import cn.jbolt.base.api.JBoltApiRet;
import cn.jbolt.common.config.Msg;
import cn.jbolt.common.db.sql.Sql;
import cn.jbolt.common.model.Application;
import cn.jbolt.common.model.User;
import cn.jbolt.common.model.WechatMpinfo;
import cn.jbolt.common.model.WechatUser;
import cn.jbolt.common.util.ArrayUtil;
import cn.jbolt.common.util.CACHE;
import cn.jbolt.common.util.RandomUtil;
import cn.jbolt.common.util.StringUtil;
/**
 * 微信公众号粉丝用户表
 * @ClassName:  WechatUserService   
 * @author: JFinal学院-小木 QQ：909854136 
 * @date:   2019年9月28日   
 */
public class WechatUserService extends JBoltBaseRecordTableSeparateService<WechatUser>{
	private static boolean syncing=false;
	@Inject
	private WechatMpinfoService wechatMpinfoService;
	@Inject
	private ApplicationService applicationService;
	@Inject
	private UserService userService;
	@Override
	protected Class<WechatUser> mainTableModelClass() {
		return WechatUser.class;
	}
	/**
	 *  管理列表
	 * @param mpId
	 * @param pageNumber
	 * @param pageSize
	 * @param sex 
	 * @return
	 */
	public Page<Record> paginateAdminList(Object mpId, Integer pageNumber, int pageSize,String keywords, Integer sex) {
		if(notOk(mpId)) {return EMPTY_PAGE;}
		return dbTemplate(mpId, "wechat.user.paginateAdminList", Kv.by("mpId", mpId).setIfNotNull("sex", sex).setIfNotBlank("keywords",keywords)).paginate(pageNumber, pageSize);
	}
	/**
	 * 同步用户数据
	 * @param mpId
	 * @return
	 */
	public Ret sync(Object mpId) {
		Ret checkRet=checkCanSync(mpId);
		if(checkRet.isFail()) {return checkRet;}
		Kv kv=checkRet.getAs("data");
		String appId=kv.getStr("appId");
		WechatMpinfo wechatMpinfo=kv.getAs("wechatMpinfo");
		syncing=true;
		ApiConfigKit.setThreadLocalAppId(appId);
		try {
			Ret ret=syncByNextOpenId(mpId,wechatMpinfo.getType(),null);
			if(ret.isFail()) {return ret;}
			if(wechatMpinfo.getIsAuthenticated()&&wechatMpinfo.getType().intValue()!=WechatMpinfoType.XCX.getValue()&&wechatMpinfo.getType().intValue()!=WechatMpinfoType.QYWX.getValue()) {
				return syncUserInfo(mpId,wechatMpinfo.getType());
			}
			return SUCCESS;
		}finally {
			ApiConfigKit.removeThreadLocalAppId();
			syncing=false;
		}
	}
	/**
	 * 更新用户信息
	 * @param mpId
	 * @param type
	 * @return
	 */
	private Ret syncUserInfo(Object mpId, Integer type) {
		List<Record> needSyncUsers=getNeedSyncUsers(mpId,100);
		if(needSyncUsers==null||needSyncUsers.size()==0) {
			return success("同步用户信息完成");
		}
		Ret ret;
		for(Record user:needSyncUsers) {
			ret=processUserInfo(user);
			if(ret.isFail()) {
				return ret;
			}
		}
		//批量更新
		Db.batchUpdate(table(mpId), needSyncUsers, needSyncUsers.size());
		return syncUserInfo(mpId, type);
	}
	
	/**
	 * 调用接口 去获取用户信息
	 * @param user
	 */
	private Ret processUserInfo(Record user) {
		ApiResult apiResult=UserApi.getUserInfo(user.getStr("open_id"));
		if(apiResult.isSucceed()==false) {return fail(apiResult.getErrorMsg());}
		
		Integer subscribe=apiResult.getInt("subscribe");
		if(isOk(subscribe)&&subscribe.intValue()==1) {
			String userNickName=user.getStr("nickname");
			//判断如果传进来的user 没有nickname或者有但是是之前自动生成的那种 需要再次设置一下
			if(notOk(userNickName)||(userNickName.indexOf("用户_")!=-1&&userNickName.equals("用户_")==false)) {
				String nickName=apiResult.getStr("nickname");
				if(EmojiUtil.containsEmoji(nickName)) {
					nickName=EmojiUtil.toHtml(nickName);
				}else {
					nickName=StringUtil.filterEmoji(nickName);
				}
				if(StrKit.isBlank(nickName)) {
					nickName="用户_"+user.get("id");
				}
				user.set("nickname",nickName);
			}
			user.set("language", apiResult.getStr("language"));
			user.set("country", apiResult.getStr("country"));
			user.set("province", apiResult.getStr("province"));
			user.set("city", apiResult.getStr("city"));
			user.set("sex", apiResult.getInt("sex"));
			user.set("union_id", apiResult.getStr("unionid"));
			user.set("remark", apiResult.getStr("remark"));
			user.set("group_id", apiResult.getInt("groupid"));
			user.set("subscribe_scene", apiResult.getStr("subscribe_scene"));
			user.set("qr_scene", apiResult.getInt("qr_scene"));
			user.set("qr_scene_str", apiResult.getStr("qr_scene_str"));
			user.set("head_img_url", apiResult.getStr("headimgurl"));
			Long subscribeTime=apiResult.getLong("subscribe_time");
			user.set("subscribe_time", new Date(subscribeTime*1000L));
			}
		
		return SUCCESS;
	}
	/**
	 * 获取需要同步的数据
	 * @param mpId
	 * @param count
	 * @return
	 */
	private List<Record> getNeedSyncUsers(Object mpId, int count) {
		Sql sql=sql(mpId).select("id","open_id").isNull("nickname").firstPage(count);
		return Db.find(sql.toSql());
	}
	/**
	 * 更新同步
	 * @param mpId
	 * @param mpType
	 * @param nextOpenId
	 * @return
	 */
	private Ret syncByNextOpenId(Object mpId,Integer mpType, String nextOpenId) {
		ApiResult apiResult=null;
		try {
			apiResult=UserApi.getFollowers(nextOpenId);
			if(apiResult.isSucceed()==false) {
				syncing=true;
				return fail(apiResult.getErrorMsg());
			}
		} catch (RuntimeException e) {
			syncing=true;
			return fail(e.getMessage());
		}
		
		Integer total=apiResult.getInt("total");
		if(notOk(total)) {
			syncing=true;
			return fail("这个公众平台用户总数为0");
		}
		Integer count=apiResult.getInt("count");
		nextOpenId=apiResult.getStr("next_openid");
		if(notOk(count)||notOk(nextOpenId)) {
			syncing=true;
			return success("同步完成");
		}
		Object data=apiResult.get("data");
		JSONObject jsonObject=JSON.parseObject(data.toString());
		JSONArray array=jsonObject.getJSONArray("openid");
		if(array!=null&&array.size()>0) {
			String openId;
			List<Record> records=new ArrayList<Record>();
			for(int i=0;i<count;i++) {
				openId=array.getString(i);
				boolean exist=exists(mpId, "open_id", openId);
				if(exist) {
					continue;
				}
				records.add(genRecordByOpenId(mpId,mpType,openId));
			}
			int recordSize=records.size();
			if(recordSize>0) {
				Db.batchSave(table(mpId), records, recordSize);
			}
		}
		//递归调用
		return syncByNextOpenId(mpId,mpType,nextOpenId);
	}
	 
	/**
	 * 保存一个不重复的OPENID 进入数据库
	 * @param mpId
	 * @param mpType
	 * @param openId
	 */
	private Record genRecordByOpenId(Object mpId,Integer mpType, String openId) {
		WechatUser wechatUser=new WechatUser();
		wechatUser.setEnable(true);
		wechatUser.setIsChecked(false);
		wechatUser.setOpenId(openId);
		wechatUser.setSubscibe(true);
		wechatUser.setSource(mpType);
		wechatUser.set("mp_id", mpId);
		return wechatUser.toRecord();
	}
	/**
	 * 同步一个用户数据
	 * @param userId
	 * @param mpId
	 * @param id
	 * @return
	 */
	public Ret syncOneUserInfo(Integer userId, Object mpId, Integer id) {
		Ret checkRet=checkCanSync(mpId);
		if(checkRet.isFail()) {return checkRet;}
		Record user=findById(mpId, id);
		if(user==null) {return fail(Msg.DATA_NOT_EXIST);}
		Kv kv=checkRet.getAs("data");
		String appId=kv.getStr("appId");
		syncing=true;
		ApiConfigKit.setThreadLocalAppId(appId);
		try {
			Ret syncUserInfo=processUserInfo(user);
			if(syncUserInfo.isFail()) {return syncUserInfo;}
			Ret updateRet=update(mpId, user);
			return updateRet;
		}finally {
			ApiConfigKit.removeThreadLocalAppId();
			syncing=false;
		}
	}
	/**
	 * 检测是否可以同步
	 * @param mpId
	 * @return
	 */
	private Ret checkCanSync(Object mpId) {
		if(syncing) {return fail("已经在同步了，请耐心等待...");}
		if(notOk(mpId)) {return fail(Msg.PARAM_ERROR);}
		boolean exist=tableExist(mpId);
		if(exist==false) {
			return fail(Msg.TABLE_NOT_EXIST);
		}
		WechatMpinfo wechatMpinfo=wechatMpinfoService.findById(mpId);
		if(wechatMpinfo==null){
			return fail("微信公众平台信息不存在");
		}
		
		String appId=CACHE.me.getWechatConfigAppId(mpId);
		if(StrKit.isBlank(appId)){
			return fail(wechatMpinfo.getName()+"基础配置不正确!");
		}
		boolean canSync=(wechatMpinfo.getIsAuthenticated()&&wechatMpinfo.getType().intValue()!=WechatMpinfoType.XCX.getValue()&&wechatMpinfo.getType().intValue()!=WechatMpinfoType.QYWX.getValue());
		if(!canSync) {return fail("此公众平台无调用API权限");}
		return success(Kv.by("appId", appId).set("wechatMpinfo",wechatMpinfo), Msg.SUCCESS);
	}
	/**
	 * 切换Enable状态
	 * @param userId
	 * @param mpId
	 * @param id
	 * @return
	 */
	public Ret toggleEnable(Integer userId, Object mpId, Integer id) {
		if(notOk(mpId)) {return fail(Msg.PARAM_ERROR);}
		boolean exist=tableExist(mpId);
		if(exist==false) {
			return fail(Msg.TABLE_NOT_EXIST);
		}
		WechatMpinfo wechatMpinfo=wechatMpinfoService.findById(mpId);
		if(wechatMpinfo==null){
			return fail("微信公众平台信息不存在");
		}
		String appId=CACHE.me.getWechatConfigAppId(mpId);
		if(StrKit.isBlank(appId)){
			return fail(wechatMpinfo.getName()+"基础配置不正确!");
		}
		Ret ret=toggleBoolean(mpId, id, "enable");
		if(ret.isOk()) {
			Record userRecord=ret.getAs("data");
			//添加日志
			CACHE.me.removeApiWechatUserByMpOpenId(mpId, userRecord.getStr("open_id"));
			return successWithData(userRecord.getBoolean("enable"));
		}
		return ret;
	}
	/** 
	 * 同步关注者的用户信息到微信User表
	 * @param appId
	 * @param openId
	 * @return
	 */
	public Ret syncSubscribeUserInfo(String appId, String openId) {
		Object mpId=CACHE.me.getWechatMpidByAppId(appId);
		if(notOk(mpId)) {
			return fail("appId为："+appId+"的公众号信息获取失败");
		}
		WechatMpinfo wechatMpinfo=wechatMpinfoService.findById(mpId);
		if(wechatMpinfo==null) {
			return fail("微信公众平台信息不存在");
		}
	
		//根据openId去找人 找到就更新 找不到就新增
		Record wechatUser=getByOpenId(mpId,openId);
		if(wechatUser!=null) {
			//说明之前用户信息已经同步到数据库里保存了 这次更新
			Ret ret=processUserInfo(wechatUser);
			if(ret.isOk()) {
				update(mpId, wechatUser);
			}
		}else {
			//说明是个全新用户，存起来
			saveOneNewWechatUserInfo(mpId,wechatMpinfo.getType(),openId);
		}
		return SUCCESS;
		
	}
	/**
	 * 更新用户信息
	 * @param mpId
	 * @param apiResult
	 */
	private void updateOneNewWechatUserInfo(Object mpId,WechatUser user,ApiResult apiResult) {
		Integer subscribe=apiResult.getInt("subscribe");
		if(isOk(subscribe)&&subscribe.intValue()==1) {
			String userNickName=user.getNickname();
			//判断如果传进来的user 没有nickname或者有但是是之前自动生成的那种 需要再次设置一下
			if(notOk(userNickName)||(userNickName.indexOf("用户_")!=-1&&userNickName.equals("用户_")==false)) {
				String nickName=apiResult.getStr("nickname");
				if(EmojiUtil.containsEmoji(nickName)) {
					nickName=EmojiUtil.toHtml(nickName);
				}else {
					nickName=StringUtil.filterEmoji(nickName);
				}
				if(StrKit.isBlank(nickName)) {
					nickName="用户_"+user.getId();
				}
				user.setNickname(nickName);
			}
			
			processUserInfoByApi(user, apiResult);
			Record record=user.toRecord();
			update(mpId, record);
			 
		}
		
		
	}
	/**
	 * 填充更新必要字段
	 * @param user
	 * @param apiResult
	 */
	private void processUserInfoByApi(WechatUser user, ApiResult apiResult) {
		user.setLanguage(apiResult.getStr("language"));
		user.setCountry(apiResult.getStr("country"));
		user.setProvince(apiResult.getStr("province"));
		user.setCity(apiResult.getStr("city"));
		user.setSex(apiResult.getInt("sex"));
		user.setUnionId(apiResult.getStr("unionid"));
		user.setRemark(apiResult.getStr("remark"));
		user.setGroupId(apiResult.getInt("groupid"));
		user.setSubscribeScene(apiResult.getStr("subscribe_scene"));
		user.setQrScene(apiResult.getInt("qr_scene"));
		user.setQrSceneStr(apiResult.getStr("qr_scene_str"));
		user.setHeadImgUrl(apiResult.getStr("headimgurl"));
		Long subscribeTime=apiResult.getLong("subscribe_time");
		user.setSubscribeTime(new Date(subscribeTime*1000L));
	}
	/**
	 * 保存一个新的关注用户信息
	 * @param mpId
	 * @param apiResult
	 */
	private void saveOneNewWechatUserInfo(Object mpId,int mpType,String openId) {
		Record record=genRecordByOpenId(mpId, mpType, openId);
		Ret ret=save(mpId, record);
		if(ret.isOk()) {
			Ret processRet=processUserInfo(record);
			if(processRet.isOk()) {
				update(mpId, record);
			}
		}
		
	}
	/**
	 * 根据openId获取用户
	 * @param mpId 
	 * @param openId
	 * @return
	 */
	public Record getByOpenId(Object mpId, String openId) {
		return findFirst(mpId, Kv.by("open_id", openId));
	}
	/**
	 * 根据openId获取用户 api专用
	 * @param mpId 
	 * @param openId
	 * @return
	 */
	public Record getByOpenIdForApi(Object mpId, String openId) {
		return findFirst(selectSql(mpId).select("id,nickname,open_id,union_id,sex,subscibe,head_img_url,enable,session_key,mp_id,bind_user,phone,weixin").eq("open_id", openId).first());
	}
	/**
	 * 根据openId获取用户
	 * @param mpId 
	 * @param openId
	 * @return
	 */
	public WechatUser getWechatUserByOpenId(Object mpId, String openId) {
		Record record=getByOpenId(mpId, openId);
		if(record==null) {return null;}
		return new WechatUser()._setAttrs(record.getColumns());
	}
	/**
	 * 根据openId获取用户 api专用
	 * @param mpId 
	 * @param openId
	 * @return
	 */
	public WechatUser getApiWechatUserByOpenId(Object mpId, String openId) {
		Record record=getByOpenIdForApi(mpId, openId);
		if(record==null) {return null;}
		return new WechatUser()._setAttrs(record.getColumns());
	}
	/**
	 * 微信小程序wx.login
	 * @param mpId
	 * @param openId
	 * @param unionId
	 * @param sessionKey
	 * @return
	 */
	public WechatUser saveWxaUser(Object mpId,String openId,String unionId, String sessionKey) {
		Date now=new Date();
		WechatUser wechatUser=new WechatUser();
		wechatUser.setEnable(true);
		wechatUser.setIsChecked(false);
		wechatUser.setOpenId(openId);
		wechatUser.setSource(WechatMpinfoType.XCX.getValue());
		wechatUser.set("mp_id", mpId);
		wechatUser.setUnionId(unionId);
		wechatUser.setSessionKey(sessionKey); 
		wechatUser.setNickname("用户_"+RandomUtil.randomLowWithNumber(6));
		wechatUser.setSubscibe(true);
		wechatUser.setSubscribeTime(now);
		wechatUser.setFirstLoginTime(now);
		wechatUser.setLastLoginTime(now);
		Record record=wechatUser.toRecord();
		boolean success=Db.save(table(mpId), record);
		if(!success) {
			return null;
		}
		wechatUser.setId(record.getInt("id"));
		return wechatUser;
	}
	/**
	 * 微信小程序授权拿到用户解密数据后更新数据库
	 * @param mpId
	 * @param wechatAppId
	 * @param userId
	 * @param userInfoResult
	 * @return
	 */
	public Ret updateWxaUserInfoByDecrypt(Object mpId, String wechatAppId,Object userId, ApiResult userInfoResult) {
		WechatUser wechatUser=findByIdToWechatUser(mpId, userId);
		if(wechatUser==null) {
			return fail(String.format("微信小程序用户信息更新时,找不到指定的用户信息数据[%s-%s]", mpId,userId));
		}
		String openId=userInfoResult.getStr("openId");
		if(openId.equals(wechatUser.getOpenId())==false) {
			return fail(String.format("微信小程序用户信息更新时,openId不一致[%s-%s]", mpId,userId));
		}
		String unionId=userInfoResult.getStr("unionId");
		if(StrKit.notBlank(unionId)) {
			wechatUser.setUnionId(unionId);
		}
		String oldOpenId=wechatUser.getOpenId();
		wechatUser.setOpenId(openId);
		wechatUser.setNickname(userInfoResult.getStr("nickName"));
		wechatUser.setSex(userInfoResult.getInt("gender"));
		wechatUser.setCountry(userInfoResult.get("country"));
		wechatUser.setProvince(userInfoResult.get("province"));
		wechatUser.setCity(userInfoResult.get("city"));
		wechatUser.setHeadImgUrl(userInfoResult.get("avatarUrl"));
		wechatUser.setLanguage(userInfoResult.getStr("language"));
		Date now=new Date();
		wechatUser.setUpdateTime(now);
		if(notOk(wechatUser.getFirstAuthTime())) {
			//如果没设置首次注册授权时间 就设置一下
			wechatUser.setFirstAuthTime(now);
			wechatUser.setLastAuthTime(now);
		}
		boolean success=Db.update(table(mpId), wechatUser.toRecord());
		if(success) {
			if(StrKit.notBlank(oldOpenId)) {
				//清掉缓存
				CACHE.me.removeApiWechatUserByMpOpenId(mpId, oldOpenId);
			}
		}
		return success?SUCCESS:fail(String.format("更新微信小程序用户授权信息失败[%s:%s]",mpId,wechatUser.getId()));
	}
	/**
	 * 微信小程序手机号授权拿到用户解密数据后更新数据库
	 * @param mpId
	 * @param wechatAppId
	 * @param userId
	 * @param userInfoResult
	 * @return
	 */
	public Ret updateWxaPhoneNumberByDecrypt(Object mpId, String wechatAppId,Object userId, ApiResult userInfoResult) {
		Map<String, String> warterMark= userInfoResult.get("watermark");
		//判断水印
		if(warterMark==null) {
			return fail(String.format("微信小程序用户手机号信息更新时,未获取到appId[%s-%s]", mpId,userId));
		}
		//判断水印里的APPID
		String waterMarkAppId=warterMark.get("appid");
		if(wechatAppId.equals(waterMarkAppId)==false) {
			return fail(String.format("微信小程序用户手机号信息更新时,appId不匹配[%s-%s]", mpId,userId));
		}
		//判断微信用户
		WechatUser wechatUser=findByIdToWechatUser(mpId, userId);
		if(wechatUser==null) {
			return fail(String.format("微信小程序用户手机号信息更新时,找不到指定的用户信息数据[%s-%s]", mpId,userId));
		}
		
		wechatUser.setPhone(userInfoResult.getStr("purePhoneNumber"));
		wechatUser.setPhoneCountryCode(userInfoResult.getStr("countryCode"));
		Date now=new Date();
		wechatUser.setUpdateTime(now);
		if(notOk(wechatUser.getFirstAuthTime())) {
			//如果没设置首次注册授权时间 就设置一下
			wechatUser.setFirstAuthTime(now);
			wechatUser.setLastAuthTime(now);
		}
		boolean success=Db.update(table(mpId), wechatUser.toRecord());
		if(success) {
			if(StrKit.notBlank(wechatUser.getOpenId())) {
				//清掉缓存
				CACHE.me.removeApiWechatUserByMpOpenId(mpId, wechatUser.getOpenId());
			}
		}
		return success?successWithData(Kv.by("phoneNumber", wechatUser.getPhone()).set("countryCode",wechatUser.getPhoneCountryCode())):fail(String.format("更新微信小程序用户手机号授权信息失败[%s:%s]",mpId,wechatUser.getId()));
	}
	/**
	 * 更新用户wx小程序登录信息
	 * @param mpId
	 * @param userId
	 * @param openId
	 * @param unionId
	 * @param sessionKey
	 * @return
	 */
	public Ret updateWxaUserLoginInfo(Object mpId, WechatUser user, String openId, String unionId,
			String sessionKey) {
		String oldOpenId=user.getOpenId();
		user.setOpenId(openId);
		user.setUnionId(unionId);
		user.setSessionKey(sessionKey);
		Date now=new Date();
		user.setUpdateTime(now);
		user.setLastLoginTime(now);
		boolean success=Db.update(table(mpId), user.toRecord());
		if(success) {
			if(StrKit.notBlank(oldOpenId)) {
				//清掉缓存
				CACHE.me.removeApiWechatUserByMpOpenId(mpId, oldOpenId);
			}
		}
		return success?SUCCESS:fail(String.format("更新微信小程序用户登录信息失败[%s:%s]",mpId,user.getId()));
	}
	/**
	 * 微信用户绑定其他用户
	 * @param application
	 * @param mpId
	 * @param apiUserId
	 * @param type
	 * @param userName
	 * @param password
	 * @return
	 */
	public JBoltApiRet bindOtherUer(Application application,Object mpId, Object apiUserId, Integer type, String userName, String password) {
		WechatUser wechatUser=findByIdToWechatUser(mpId,apiUserId);
		JBoltApiRet ret=null;
		switch (type.intValue()) {
		case JBoltApiBindUserBean.TYPE_SYSTEM_USER:
			ret=bindSystemUser(application,wechatUser,userName,password);
			break;
		}
		if(ret==null) {
			ret=JBoltApiRet.WECHAT_XCX_BINDUSER_ERROR(application);
		}
		return ret;
	}
	/**
	 * 绑定系统用户
	 * @param application
	 * @param wechatUser
	 * @param userName
	 * @param password
	 * @return
	 */
	private JBoltApiRet bindSystemUser(Application application,WechatUser wechatUser, String userName, String password) {
		User user=userService.getUser(userName, password);
		if(user==null) {
			return JBoltApiRet.WECHAT_XCX_BINDUSER_NO_USER(application,"系统用户");
		}
		String bindUser=wechatUser.getBindUser();
		String newBindUser=JBoltApiBindUserBean.TYPE_SYSTEM_USER+"_"+user.getId();
		boolean needUpdate=false;
		if(notOk(bindUser)) {
			//如果没有 就直接赋值
			bindUser=newBindUser;
			needUpdate=true;
		}else {
			//如果相等或者已经绑定 直接success就好了
			if(checkExistBindUserInfo(bindUser,newBindUser)) {
				return JBoltApiRet.API_SUCCESS;
			}
			if((bindUser.startsWith(JBoltApiBindUserBean.TYPE_SYSTEM_USER+"_")||bindUser.indexOf(","+JBoltApiBindUserBean.TYPE_SYSTEM_USER+"_")!=-1)) {
				//判断是不是已经存在这个类型的用户绑定 同一个类型下
				return JBoltApiRet.WECHAT_XCX_BINDUSER_SAMETYPE_EXIST(application,"系统用户");
			}else {
				bindUser=bindUser+","+newBindUser;
				needUpdate=true;
			}
			
		}
		if(needUpdate) {
			wechatUser.setBindUser(bindUser);
			Ret ret=update(wechatUser.getMpId(),wechatUser.toRecord());
			if(ret.isFail()) {
				return JBoltApiRet.WECHAT_XCX_BINDUSER_UPDATE_ERROR(application,"系统用户");
			}
		}
		return JBoltApiRet.API_SUCCESS;
		
	}
	/**
	 * 检测是否存在相同数据
	 * @param bindUser
	 * @param newBindUser
	 * @return
	 */
	private boolean checkExistBindUserInfo(String bindUser, String newBindUser) {
		if(newBindUser.equals(bindUser)) {return true;}//相等
		if(bindUser.indexOf(",")==-1) {return false;}
		String[] arr=ArrayUtil.from(bindUser, ",");
		return ArrayUtil.contains(arr, newBindUser);
	}
	
	



	 
}
