package cn.jbolt.common.config;
/**   
 * 常用返回信息工具类
 * @ClassName:  Msg   
 * @author: JFinal学院-小木 QQ：909854136 
 * @date:   2019年3月26日 下午2:14:29   
 */
public class Msg {
	public final static String PARAM_ERROR="参数异常";
	public final static String SUCCESS="操作成功";
	public final static String FAIL="操作失败";
	public final static String NOPERMISSION="无权访问";
	public final static String FAIL_NEED_CATEGORY="添加商品前需指定一个商品类目";
	public final static String FAIL_INIT_GOODS="新商品信息初始化失败";
	public final static String TABLE_NOT_EXIST="数据表不存在，分表尚未初始化";
	public final static String DATA_NOT_EXIST="数据记录不存在";
	public final static String DATA_SAME_NAME_EXIST="存在重名数据，请更换";
	public final static String DATA_INUSE="已经被关联使用";
	public final static String DEMO_MODE_CAN_NOT_DELETE="Demo演示模式下，不能删除此数据";
	public final static String DATA_IMPORT_FAIL="数据导入异常";
	public final static String DATA_IMPORT_FAIL_EMPTY="数据导入异常，导入文件无数据";
	public final static String NOT_ALLOWED_JBOLT_API="非法Api调用请求";
}
