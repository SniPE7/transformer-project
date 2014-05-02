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
 * <p>�ļ�����: DirectoryEntityContextMapper.java</p>
 * <p>�ļ�����: Ŀ¼ʵ������ӳ��</p>
 * <p>��Ȩ����: ��Ȩ����(C)2010</p>
 * <p>����ժҪ: ��Ҫ�������ļ������ݣ�������Ҫģ�顢�������ܵ�˵��</p>
 * <p>����˵��: �������ݵ�˵��</p>
 * <p>�������: 2012-2-16</p>
 * <p>�޸ļ�¼1:</p>
 * <pre>
 *    �޸�����:
 *    �� �� ��:
 *    �޸�����:
 * </pre>
 * <p>�޸ļ�¼2����</p>
 * @author  Wuqingming
 */
public class DirectoryEntityContextMapper implements ContextMapper {
	private static Logger logger = LoggerFactory.getLogger(DirectoryEntityContextMapper.class);
	
	//��Ҫ��װ��entity
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
					//�����ֵ�ֵ�Ͷ�ֵ��ͳһ���ն�ֵȥȡ
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
					logger.error("Ldap����ת�������쳣��", ex);
				}
			}
			entity.setDn(context.getNameInNamespace());
			return entity;
		}catch(Exception e){
			logger.error("Ldap����ת�������쳣��", e);
		}
		return null;
	}

}

