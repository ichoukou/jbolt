package cn.jbolt.common.model.base;
import cn.jbolt.base.JBoltBaseModel;
import cn.jbolt.common.gen.JBoltField;

/**
 * sku详情
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSkuItem<M extends BaseSkuItem<M>> extends JBoltBaseModel<M>{

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
	 * SKU ID
	 */
	public M setSkuId(java.lang.Integer skuId) {
		set("sku_id", skuId);
		return (M)this;
	}
	
	/**
	 * SKU ID
	 */
	@JBoltField(name="skuId" ,columnName="sku_id",type="Integer", remark="SKU ID")
	public java.lang.Integer getSkuId() {
		return getInt("sku_id");
	}

	/**
	 * 商品 ID
	 */
	public M setGoodsId(java.lang.Integer goodsId) {
		set("goods_id", goodsId);
		return (M)this;
	}
	
	/**
	 * 商品 ID
	 */
	@JBoltField(name="goodsId" ,columnName="goods_id",type="Integer", remark="商品 ID")
	public java.lang.Integer getGoodsId() {
		return getInt("goods_id");
	}

	/**
	 * 商品分类
	 */
	public M setTypeId(java.lang.Integer typeId) {
		set("type_id", typeId);
		return (M)this;
	}
	
	/**
	 * 商品分类
	 */
	@JBoltField(name="typeId" ,columnName="type_id",type="Integer", remark="商品分类")
	public java.lang.Integer getTypeId() {
		return getInt("type_id");
	}

	/**
	 * 规格 ID
	 */
	public M setSpecId(java.lang.Integer specId) {
		set("spec_id", specId);
		return (M)this;
	}
	
	/**
	 * 规格 ID
	 */
	@JBoltField(name="specId" ,columnName="spec_id",type="Integer", remark="规格 ID")
	public java.lang.Integer getSpecId() {
		return getInt("spec_id");
	}

	/**
	 * 规格项 ID
	 */
	public M setSpecItemId(java.lang.Integer specItemId) {
		set("spec_item_id", specItemId);
		return (M)this;
	}
	
	/**
	 * 规格项 ID
	 */
	@JBoltField(name="specItemId" ,columnName="spec_item_id",type="Integer", remark="规格项 ID")
	public java.lang.Integer getSpecItemId() {
		return getInt("spec_item_id");
	}

}
