package cn.jbolt.common.model.base;
import cn.jbolt.base.JBoltBaseModel;
import cn.jbolt.common.gen.JBoltField;

/**
 * jbolt版本升级
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseJboltVersion<M extends BaseJboltVersion<M>> extends JBoltBaseModel<M>{

	/**
	 * 主键ID
	 */
	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	/**
	 * 主键ID
	 */
	@JBoltField(name="id" ,columnName="id",type="Integer", remark="主键ID")
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 版本号
	 */
	public M setVersion(java.lang.String version) {
		set("version", version);
		return (M)this;
	}
	
	/**
	 * 版本号
	 */
	@JBoltField(name="version" ,columnName="version",type="String", remark="版本号")
	public java.lang.String getVersion() {
		return getStr("version");
	}

	/**
	 * 发布时间
	 */
	public M setPublishTime(java.util.Date publishTime) {
		set("publish_time", publishTime);
		return (M)this;
	}
	
	/**
	 * 发布时间
	 */
	@JBoltField(name="publishTime" ,columnName="publish_time",type="Date", remark="发布时间")
	public java.util.Date getPublishTime() {
		return getDate("publish_time");
	}

	/**
	 * 是否最新版
	 */
	public M setIsNew(java.lang.Boolean isNew) {
		set("is_new", isNew);
		return (M)this;
	}
	
	/**
	 * 是否最新版
	 */
	@JBoltField(name="isNew" ,columnName="is_new",type="Boolean", remark="是否最新版")
	public java.lang.Boolean getIsNew() {
		return getBoolean("is_new");
	}

	/**
	 * 创建时间
	 */
	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	/**
	 * 创建时间
	 */
	@JBoltField(name="createTime" ,columnName="create_time",type="Date", remark="创建时间")
	public java.util.Date getCreateTime() {
		return getDate("create_time");
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

}