package com.ibm.siam.am.idp.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;

import com.ibm.siam.am.idp.ldap.entity.DirectoryEntity;

/**
 * <p>文件名称: DirectoryEntityContextMapper.java</p>
 * <p>文件描述: 目录实体属性映射</p>
 * <p>版权所有: 版权所有(C)2010</p>
 * <p>内容摘要: 简要描述本文件的内容，包括主要模块、函数及能的说明</p>
 * <p>其他说明: 其它内容的说明</p>
 * <p>完成日期: 2012-2-16</p>
 * <p>修改记录1:</p>
 * <pre>
 *    修改日期:
 *    修 改 人:
 *    修改内容:
 * </pre>
 * <p>修改记录2：…</p>
 * @author  Wuqingming
 */
public class DirectoryEntityContextMapper implements ContextMapper {
	private static Logger logger = LoggerFactory.getLogger(DirectoryEntityContextMapper.class);
	
	//需要封装的entity
	@SuppressWarnings("rawtypes")
	private Class entityClass;
	
	public DirectoryEntityContextMapper(Class<?> entityClass) {
		super();
		this.entityClass = entityClass;
	}

	@SuppressWarnings("rawtypes")
	public Object mapFromContext(Object ctx) {
		try{
			DirectoryEntity entity = (DirectoryEntity) entityClass.newInstance();
			if(ctx == null) {
				return entity;
			} 
			
			DirContextAdapter context = (DirContextAdapter) ctx;
			Attributes attrs = context.getAttributes();
			NamingEnumeration results = attrs.getAll();
			
			while(results.hasMoreElements()) {
				try {
					Attribute attr = (Attribute) results.nextElement();
					String name = attr.getID().toLowerCase();
					//不区分单值和多值，统一按照多值去取
					/*List<Object> buteValues = new ArrayList<Object>();
					NamingEnumeration repeatEnumer = attr.getAll();
					while (repeatEnumer.hasMoreElements()) {
						Object value = repeatEnumer.nextElement();
						buteValues.add(value);
					}
					entity.setValue(name, buteValues);*/
					NamingEnumeration repeatEnumer = attr.getAll();
					while (repeatEnumer.hasMoreElements()) {
						Object value = repeatEnumer.nextElement();
						entity.addValue(name, value, false);
					}
				} catch(Exception ex) {
					logger.error("Ldap属性转换出现异常：", ex);
				}
			}
			entity.setDn(context.getNameInNamespace());
			return entity;
		}catch(Exception e){
			logger.error("Ldap对象转换出现异常：", e);
		}
		return null;
	}

}

