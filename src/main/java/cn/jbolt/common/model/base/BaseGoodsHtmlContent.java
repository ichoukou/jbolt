package cn.jbolt.common.model.base;
import cn.jbolt.base.JBoltBaseModel;
import cn.jbolt.common.gen.JBoltField;

/**
 * 商品关联的html富文本详情
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGoodsHtmlContent<M extends BaseGoodsHtmlContent<M>> extends JBoltBaseModel<M>{

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
	 * 商品ID
	 */
	public M setGoodsId(java.lang.Integer goodsId) {
		set("goods_id", goodsId);
		return (M)this;
	}
	
	/**
	 * 商品ID
	 */
	@JBoltField(name="goodsId" ,columnName="goods_id",type="Integer", remark="商品ID")
	public java.lang.Integer getGoodsId() {
		return getInt("goods_id");
	}

	/**
	 * 详情内容
	 */
	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}
	
	/**
	 * 详情内容
	 */
	@JBoltField(name="content" ,columnName="content",type="String", remark="详情内容")
	public java.lang.String getContent() {
		return getStr("content");
	}

	/**
	 * SKUID
	 */
	public M setSkuId(java.lang.Integer skuId) {
		set("sku_id", skuId);
		return (M)this;
	}
	
	/**
	 * SKUID
	 */
	@JBoltField(name="skuId" ,columnName="sku_id",type="Integer", remark="SKUID")
	public java.lang.Integer getSkuId() {
		return getInt("sku_id");
	}

	/**
	 * 更新人
	 */
	public M setUpdateUserId(java.lang.Integer updateUserId) {
		set("update_user_id", updateUserId);
		return (M)this;
	}
	
	/**
	 * 更新人
	 */
	@JBoltField(name="updateUserId" ,columnName="update_user_id",type="Integer", remark="更新人")
	public java.lang.Integer getUpdateUserId() {
		return getInt("update_user_id");
	}

	/**
	 * 更新时间
	 */
	public M setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
		return (M)this;
	}
	
	/**
	 * 更新时间
	 */
	@JBoltField(name="updateTime" ,columnName="update_time",type="Date", remark="更新时间")
	public java.util.Date getUpdateTime() {
		return getDate("update_time");
	}

}
