package cn.jbolt.common.model.base;
import cn.jbolt.base.JBoltBaseModel;
import cn.jbolt.common.gen.JBoltField;

/**
 * 货架元素_轮播图
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseShelfCarousel<M extends BaseShelfCarousel<M>> extends JBoltBaseModel<M>{

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
	 * 图片地址
	 */
	public M setImgurl(java.lang.String imgurl) {
		set("imgurl", imgurl);
		return (M)this;
	}
	
	/**
	 * 图片地址
	 */
	@JBoltField(name="imgurl" ,columnName="imgurl",type="String", remark="图片地址")
	public java.lang.String getImgurl() {
		return getStr("imgurl");
	}

	/**
	 * 打开模式 内置资源地址，网页地址
	 */
	public M setOpentype(java.lang.Integer opentype) {
		set("openType", opentype);
		return (M)this;
	}
	
	/**
	 * 打开模式 内置资源地址，网页地址
	 */
	@JBoltField(name="opentype" ,columnName="openType",type="Integer", remark="打开模式 内置资源地址，网页地址")
	public java.lang.Integer getOpentype() {
		return getInt("openType");
	}

	/**
	 * 打开地址
	 */
	public M setOpenUrl(java.lang.String openUrl) {
		set("open_url", openUrl);
		return (M)this;
	}
	
	/**
	 * 打开地址
	 */
	@JBoltField(name="openUrl" ,columnName="open_url",type="String", remark="打开地址")
	public java.lang.String getOpenUrl() {
		return getStr("open_url");
	}

	/**
	 * 货架ID
	 */
	public M setShelfId(java.lang.Integer shelfId) {
		set("shelf_id", shelfId);
		return (M)this;
	}
	
	/**
	 * 货架ID
	 */
	@JBoltField(name="shelfId" ,columnName="shelf_id",type="Integer", remark="货架ID")
	public java.lang.Integer getShelfId() {
		return getInt("shelf_id");
	}

	/**
	 * 货架元素ID
	 */
	public M setShelfElementId(java.lang.Integer shelfElementId) {
		set("shelf_element_id", shelfElementId);
		return (M)this;
	}
	
	/**
	 * 货架元素ID
	 */
	@JBoltField(name="shelfElementId" ,columnName="shelf_element_id",type="Integer", remark="货架元素ID")
	public java.lang.Integer getShelfElementId() {
		return getInt("shelf_element_id");
	}

	/**
	 * 排序
	 */
	public M setSortRank(java.lang.Integer sortRank) {
		set("sort_rank", sortRank);
		return (M)this;
	}
	
	/**
	 * 排序
	 */
	@JBoltField(name="sortRank" ,columnName="sort_rank",type="Integer", remark="排序")
	public java.lang.Integer getSortRank() {
		return getInt("sort_rank");
	}

}
