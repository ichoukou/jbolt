package cn.jbolt.admin.mall.goods;

import java.util.List;

import com.jfinal.kit.Kv;

import cn.jbolt.base.JBoltBaseService;
import cn.jbolt.common.model.GoodsElementContent;

/**
 * 商品的元素单图文字元素类content管理service   
 * @ClassName:  GoodsElementContentService   
 * @author: JFinal学院-小木 QQ：909854136 
 * @date:   2019年3月23日 下午12:47:51   
 */
public class GoodsElementContentService extends JBoltBaseService<GoodsElementContent> {
	private GoodsElementContent dao = new GoodsElementContent().dao();
	@Override
	protected GoodsElementContent dao() {
		return dao;
	}
	
	/**
	 *  获取一个商品关联的所有elementContent
	 * @param goodsId
	 * @return
	 */
	public List<GoodsElementContent> getGoodsElementContents(Integer goodsId) {
		return getCommonList(Kv.by("goods_id", goodsId));
	}

	@Override
	public String checkInUse(GoodsElementContent m) {
		// TODO Auto-generated method stub
		return null;
	}

}
