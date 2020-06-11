package cn.jbolt.common.util;

import com.jfinal.upload.UploadFile;

import cn.jbolt.base.para.IJBoltParaValidator;
import cn.jbolt.base.para.JBoltParaValidator;
/**
 * cache层使用的参数校验器
 * @ClassName:  CacheParaValidator   
 * @author: JFinal学院-小木 QQ：909854136 
 * @date:   2019年12月5日   
 */
public class CacheParaValidator implements IJBoltParaValidator {

	@Override
	public boolean isOk(Object param) {
		return JBoltParaValidator.isOk(param);
	}
	@Override
	public boolean notOk(Object param) {
		return JBoltParaValidator.notOk(param);
	}
	@Override
	public boolean isOk(Object[] param) {
		return JBoltParaValidator.isOk(param);
	}
	@Override
	public boolean notOk(Object[] param) {
		return JBoltParaValidator.notOk(param);
	}
	@Override
	public boolean notImage(UploadFile file) {
		return false;
	}
	@Override
	public boolean isImage(UploadFile file) {
		return false;
	}
	@Override
	public boolean isImage(String contentType) {
		return false;
	}
	@Override
	public boolean notImage(String contentType) {
		return false;
	}
	@Override
	public boolean notExcel(UploadFile file) {
		return false;
	}
	@Override
	public boolean isExcel(UploadFile file) {
		return false;
	}

}
