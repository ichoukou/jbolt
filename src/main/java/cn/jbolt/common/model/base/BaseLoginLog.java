package cn.jbolt.common.model.base;
import cn.jbolt.base.JBoltBaseModel;
import cn.jbolt.common.gen.JBoltField;

/**
 * 登录日志
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseLoginLog<M extends BaseLoginLog<M>> extends JBoltBaseModel<M>{

	/**
	 * ID
	 */
	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	/**
	 * ID
	 */
	@JBoltField(name="id" ,columnName="id",type="Integer", remark="ID")
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 用户ID
	 */
	public M setUserId(java.lang.Integer userId) {
		set("user_id", userId);
		return (M)this;
	}
	
	/**
	 * 用户ID
	 */
	@JBoltField(name="userId" ,columnName="user_id",type="Integer", remark="用户ID")
	public java.lang.Integer getUserId() {
		return getInt("user_id");
	}

	/**
	 * 用户名
	 */
	public M setUsername(java.lang.String username) {
		set("username", username);
		return (M)this;
	}
	
	/**
	 * 用户名
	 */
	@JBoltField(name="username" ,columnName="username",type="String", remark="用户名")
	public java.lang.String getUsername() {
		return getStr("username");
	}

	/**
	 * IP地址
	 */
	public M setLoginIp(java.lang.String loginIp) {
		set("login_ip", loginIp);
		return (M)this;
	}
	
	/**
	 * IP地址
	 */
	@JBoltField(name="loginIp" ,columnName="login_ip",type="String", remark="IP地址")
	public java.lang.String getLoginIp() {
		return getStr("login_ip");
	}

	/**
	 * 登录时间
	 */
	public M setLoginTime(java.util.Date loginTime) {
		set("login_time", loginTime);
		return (M)this;
	}
	
	/**
	 * 登录时间
	 */
	@JBoltField(name="loginTime" ,columnName="login_time",type="Date", remark="登录时间")
	public java.util.Date getLoginTime() {
		return getDate("login_time");
	}

	/**
	 * 登录状态
	 */
	public M setLoginState(java.lang.Integer loginState) {
		set("login_state", loginState);
		return (M)this;
	}
	
	/**
	 * 登录状态
	 */
	@JBoltField(name="loginState" ,columnName="login_state",type="Integer", remark="登录状态")
	public java.lang.Integer getLoginState() {
		return getInt("login_state");
	}

	/**
	 * 是否是浏览器访问
	 */
	public M setIsBrowser(java.lang.Boolean isBrowser) {
		set("is_browser", isBrowser);
		return (M)this;
	}
	
	/**
	 * 是否是浏览器访问
	 */
	@JBoltField(name="isBrowser" ,columnName="is_browser",type="Boolean", remark="是否是浏览器访问")
	public java.lang.Boolean getIsBrowser() {
		return getBoolean("is_browser");
	}

	/**
	 * 浏览器版本号
	 */
	public M setBrowserVersion(java.lang.String browserVersion) {
		set("browser_version", browserVersion);
		return (M)this;
	}
	
	/**
	 * 浏览器版本号
	 */
	@JBoltField(name="browserVersion" ,columnName="browser_version",type="String", remark="浏览器版本号")
	public java.lang.String getBrowserVersion() {
		return getStr("browser_version");
	}

	/**
	 * 浏览器
	 */
	public M setBrowserName(java.lang.String browserName) {
		set("browser_name", browserName);
		return (M)this;
	}
	
	/**
	 * 浏览器
	 */
	@JBoltField(name="browserName" ,columnName="browser_name",type="String", remark="浏览器")
	public java.lang.String getBrowserName() {
		return getStr("browser_name");
	}

	/**
	 * 操作系统
	 */
	public M setOsName(java.lang.String osName) {
		set("os_name", osName);
		return (M)this;
	}
	
	/**
	 * 操作系统
	 */
	@JBoltField(name="osName" ,columnName="os_name",type="String", remark="操作系统")
	public java.lang.String getOsName() {
		return getStr("os_name");
	}

	/**
	 * 登录城市
	 */
	public M setLoginCity(java.lang.String loginCity) {
		set("login_city", loginCity);
		return (M)this;
	}
	
	/**
	 * 登录城市
	 */
	@JBoltField(name="loginCity" ,columnName="login_city",type="String", remark="登录城市")
	public java.lang.String getLoginCity() {
		return getStr("login_city");
	}

	/**
	 * 登录位置详情
	 */
	public M setLoginAddress(java.lang.String loginAddress) {
		set("login_address", loginAddress);
		return (M)this;
	}
	
	/**
	 * 登录位置详情
	 */
	@JBoltField(name="loginAddress" ,columnName="login_address",type="String", remark="登录位置详情")
	public java.lang.String getLoginAddress() {
		return getStr("login_address");
	}

	/**
	 * 登录城市代码
	 */
	public M setLoginCityCode(java.lang.String loginCityCode) {
		set("login_city_code", loginCityCode);
		return (M)this;
	}
	
	/**
	 * 登录城市代码
	 */
	@JBoltField(name="loginCityCode" ,columnName="login_city_code",type="String", remark="登录城市代码")
	public java.lang.String getLoginCityCode() {
		return getStr("login_city_code");
	}

	/**
	 * 登录省份
	 */
	public M setLoginProvince(java.lang.String loginProvince) {
		set("login_province", loginProvince);
		return (M)this;
	}
	
	/**
	 * 登录省份
	 */
	@JBoltField(name="loginProvince" ,columnName="login_province",type="String", remark="登录省份")
	public java.lang.String getLoginProvince() {
		return getStr("login_province");
	}

	/**
	 * 是否移动端
	 */
	public M setIsMobile(java.lang.Boolean isMobile) {
		set("is_mobile", isMobile);
		return (M)this;
	}
	
	/**
	 * 是否移动端
	 */
	@JBoltField(name="isMobile" ,columnName="is_mobile",type="Boolean", remark="是否移动端")
	public java.lang.Boolean getIsMobile() {
		return getBoolean("is_mobile");
	}

	/**
	 * 平台名称
	 */
	public M setOsPlatformName(java.lang.String osPlatformName) {
		set("os_platform_name", osPlatformName);
		return (M)this;
	}
	
	/**
	 * 平台名称
	 */
	@JBoltField(name="osPlatformName" ,columnName="os_platform_name",type="String", remark="平台名称")
	public java.lang.String getOsPlatformName() {
		return getStr("os_platform_name");
	}

	/**
	 * 浏览器引擎名
	 */
	public M setBrowserEngineName(java.lang.String browserEngineName) {
		set("browser_engine_name", browserEngineName);
		return (M)this;
	}
	
	/**
	 * 浏览器引擎名
	 */
	@JBoltField(name="browserEngineName" ,columnName="browser_engine_name",type="String", remark="浏览器引擎名")
	public java.lang.String getBrowserEngineName() {
		return getStr("browser_engine_name");
	}

	/**
	 * 浏览器引擎版本
	 */
	public M setBrowserEngineVersion(java.lang.String browserEngineVersion) {
		set("browser_engine_version", browserEngineVersion);
		return (M)this;
	}
	
	/**
	 * 浏览器引擎版本
	 */
	@JBoltField(name="browserEngineVersion" ,columnName="browser_engine_version",type="String", remark="浏览器引擎版本")
	public java.lang.String getBrowserEngineVersion() {
		return getStr("browser_engine_version");
	}

	/**
	 * 登录地横坐标
	 */
	public M setLoginAddressLatitude(java.lang.String loginAddressLatitude) {
		set("login_address_latitude", loginAddressLatitude);
		return (M)this;
	}
	
	/**
	 * 登录地横坐标
	 */
	@JBoltField(name="loginAddressLatitude" ,columnName="login_address_latitude",type="String", remark="登录地横坐标")
	public java.lang.String getLoginAddressLatitude() {
		return getStr("login_address_latitude");
	}

	/**
	 * 登录地纵坐标
	 */
	public M setLoginAddressLongitude(java.lang.String loginAddressLongitude) {
		set("login_address_longitude", loginAddressLongitude);
		return (M)this;
	}
	
	/**
	 * 登录地纵坐标
	 */
	@JBoltField(name="loginAddressLongitude" ,columnName="login_address_longitude",type="String", remark="登录地纵坐标")
	public java.lang.String getLoginAddressLongitude() {
		return getStr("login_address_longitude");
	}

	/**
	 * 是否为异地异常登录
	 */
	public M setIsRemoteLogin(java.lang.Boolean isRemoteLogin) {
		set("is_remote_login", isRemoteLogin);
		return (M)this;
	}
	
	/**
	 * 是否为异地异常登录
	 */
	@JBoltField(name="isRemoteLogin" ,columnName="is_remote_login",type="Boolean", remark="是否为异地异常登录")
	public java.lang.Boolean getIsRemoteLogin() {
		return getBoolean("is_remote_login");
	}

	/**
	 * 记录创建时间
	 */
	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	/**
	 * 记录创建时间
	 */
	@JBoltField(name="createTime" ,columnName="create_time",type="Date", remark="记录创建时间")
	public java.util.Date getCreateTime() {
		return getDate("create_time");
	}

}
