package com.ibm.tivoli.cmcc.ldap;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.AttributesMapper;

public class PersonAttributeMapper implements AttributesMapper {
  
  private static Log log = LogFactory.getLog(PersonAttributeMapper.class);
  
  // LDAP Attribute -> JavaBean Property
  private static Map<String, String> attributeMapping = new HashMap<String, String>();
  static {
    attributeMapping.put("uid", "msisdn");
    attributeMapping.put("cn", "commonName");
    attributeMapping.put("sn", "lastName");
    attributeMapping.put("erhljmccbrand", "brand");
    attributeMapping.put("erhljmccstatus", "status");
    //attributeMapping.put("", "currentPoint");
    //attributeMapping.put("", "lastMonthBill");
    //attributeMapping.put("", "balance");
    attributeMapping.put("displayName", "nickname");
    //attributeMapping.put("", "dataTraffice");
    //attributeMapping.put("", "businessStatus");  
    attributeMapping.put("erhljmcc139MailStatus", "mail139Status");  
    attributeMapping.put("erhljmccFetionStatus", "fetionStatus");  
  }

  public Object mapFromAttributes(Attributes attributes) throws NamingException {
    PersonDTO contactDTO = new PersonDTO();
    String commonName = (String) attributes.get("cn").get();
    if (commonName != null)
      contactDTO.setCommonName(commonName);
    
    String lastName = (String) attributes.get("sn").get();
    if (lastName != null)
      contactDTO.setLastName(lastName);
    
    //Attribute uid = attributes.get("uid");
    //if (uid != null)
    //  contactDTO.setMsisdn((String) uid.get());
    
    // TODO Set fixed value for HLJ
    contactDTO.setProvince("200");
    
    for (String ldapAttrName: attributeMapping.keySet()) {
      Attribute attr = attributes.get(ldapAttrName);
      String propertyName = attributeMapping.get(ldapAttrName);
      if (attr != null) {
        try {
          BeanUtils.setProperty(contactDTO, propertyName, (String)attr.get());
        } catch (IllegalAccessException e) {
          log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
          log.error(e.getMessage(), e);
        }
      }
    }
    
    return contactDTO;
  }

}
