package cn.jbolt._admin.dictionary;

import com.jfinal.aop.Inject;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;

import cn.jbolt.base.JBoltBaseService;
import cn.jbolt.common.config.Msg;
import cn.jbolt.common.model.DictionaryType;
import cn.jbolt.common.model.SystemLog;

/**
 * 字典类型Service
 * @ClassName:  DictionaryTypeService   
 * @author: JFinal学院-小木 QQ：909854136 
 * @date:   2019年11月9日   
 */
public class DictionaryTypeService extends JBoltBaseService<DictionaryType> {
	private DictionaryType dao = new DictionaryType().dao();
	@Inject
	private DictionaryService dictionaryService;
	@Override
	protected DictionaryType dao() {
		return dao;
	}
	/**
	 * 新增 保存
	 * @param userId
	 * @param type
	 * @return
	 */
	public Ret save(Integer userId,DictionaryType type) {
		if(type==null||isOk(type.getId())||notOk(type.getName())||notOk(type.getModeLevel())||notOk(type.getTypeKey())){
			return fail(Msg.PARAM_ERROR);
		}
		if(existsName(type.getName())){
			return fail("数据分类["+type.getName()+"]已经存在");
		}
		if(exists("type_key",type.getTypeKey())){
			return fail("数据分类标识Key["+type.getTypeKey()+"]已经存在");
		}
		boolean success=type.save();
		if(success){
			//添加日志
			addSaveSystemLog(type.getId(), userId, SystemLog.TARGETTYPE_DICTIONARY_TYPE,type.getName());
		}
		return ret(success);
	}
	/**
	 * 更新
	 * @param userId
	 * @param type
	 * @return
	 */
	public Ret update(Integer userId,DictionaryType type) {
		if(type==null||notOk(type.getId())||notOk(type.getName())||notOk(type.getModeLevel())||notOk(type.getTypeKey())){
			return fail(Msg.PARAM_ERROR);
		}
		DictionaryType db=findById(type.getId());
		if(db==null) {
			return fail(Msg.DATA_NOT_EXIST);
		}
		if(existsName(type.getName(), type.getId())){
			return fail("数据分类["+type.getName()+"]已经存在");
		}
		if(exists("type_key",type.getTypeKey(), type.getId())){
			return fail("数据分类标识Key["+type.getTypeKey()+"]已经存在");
		}
		boolean success=type.update();
		if(success){
			if(db.getTypeKey().equals(type.getTypeKey())==false) {
				//如果修改了typeKey就要去更新一下字典数据表的绑定了
				dictionaryService.updateTypeKey(type.getId(),type.getTypeKey());
			}
			addUpdateSystemLog(type.getId(), userId, SystemLog.TARGETTYPE_DICTIONARY_TYPE,type.getName());
		}
		return ret(success);
	}
	
	/**
	 * 删除字典分类
	 * @param userId
	 * @param id
	 * @return
	 */
	public Ret delete(Integer userId, Integer id) {
		Ret ret=deleteById(id, true);
		if(ret.isOk()){
			DictionaryType type=ret.getAs("data");
			addDeleteSystemLog(id, userId, SystemLog.TARGETTYPE_DICTIONARY_TYPE,type.getName());
		}
		return ret;
	}
	/**
	 * 通过typeKey获取字典分类
	 * @param typeKey
	 * @return
	 */
	public DictionaryType findByTypeKey(String typeKey) {
		return findFirst(Kv.by("type_key", typeKey));
	}
	
	/**
	 * 检测是否被使用
	 */
	@Override
	public String checkInUse(DictionaryType dictionaryType) {
		boolean inUse=dictionaryService.checkTypeInUse(dictionaryType.getId());
		return inUse?"此类型下已经存在数据项!":null;
	}

}
