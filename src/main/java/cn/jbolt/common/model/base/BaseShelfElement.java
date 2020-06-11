package cn.jbolt.common.model.base;
import cn.jbolt.base.JBoltBaseModel;
import cn.jbolt.common.gen.JBoltField;

/**
 * 货架从上到下的元素
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseShelfElement<M extends BaseShelfElement<M>> extends JBoltBaseModel<M>{

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
	 * 类型 轮播 楼层 分类 活动等
	 */
	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}
	
	/**
	 * 类型 轮播 楼层 分类 活动等
	 */
	@JBoltField(name="type" ,columnName="type",type="Integer", remark="类型 轮播 楼层 分类 活动等")
	public java.lang.Integer getType() {
		return getInt("type");
	}

	/**
	 * 宽度
	 */
	public M setWidth(java.lang.String width) {
		set("width", width);
		return (M)this;
	}
	
	/**
	 * 宽度
	 */
	@JBoltField(name="width" ,columnName="width",type="String", remark="宽度")
	public java.lang.String getWidth() {
		return getStr("width");
	}

	/**
	 * 高度
	 */
	public M setHeight(java.lang.String height) {
		set("height", height);
		return (M)this;
	}
	
	/**
	 * 高度
	 */
	@JBoltField(name="height" ,columnName="height",type="String", remark="高度")
	public java.lang.String getHeight() {
		return getStr("height");
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