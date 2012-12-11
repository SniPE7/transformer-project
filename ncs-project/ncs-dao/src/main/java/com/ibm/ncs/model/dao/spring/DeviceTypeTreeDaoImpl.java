package com.ibm.ncs.model.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.exceptions.DeviceTypeTreeDaoException;

public class DeviceTypeTreeDaoImpl extends AbstractDAO implements ParameterizedRowMapper<DeviceTypeTree>, DeviceTypeTreeDao {
	protected SimpleJdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * Method 'setDataSource'
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/**
	 * Method 'execute'
	 * 
	 * @param mrName
	 * @param cateName
	 * @param subCategory
	 * @throws DeviceTypeTreeDaoException
	 * @return List<DeviceTypeTree>
	 */
	public List<DeviceTypeTree> execute(java.lang.String mrName, java.lang.String cateName, java.lang.String subCategory) throws DeviceTypeTreeDaoException {
		try {
			return jdbcTemplate
			    .query(
			        "select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a  on a.mrid=c.mrid  where mrname=? and    a.name=?  and    a.subCategory=?",
			        this, mrName, cateName, subCategory);
		} catch (Exception e) {
			throw new DeviceTypeTreeDaoException("Query failed", e);
		}

	}

	/**
	 * Method 'listDeviceTypeTree'; mrid,category,mrname,name,subcategory
	 * 
	 * @throws DeviceTypeTreeDaoException
	 * @return List<DeviceTypeTree>
	 */
	public List<DeviceTypeTree> listDeviceTypeTree() throws DeviceTypeTreeDaoException {
		try {
			return jdbcTemplate.query("select distinct mrid,dtid,category,mrname,name,subcategory ,'','','','' from("
			    +
			    // "select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory from t_manufacturer_info_init c left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a on a.mrid=c.mrid order by c.mrName)",
					// this);
			    "select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  "
			    + "left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a  on a.mrid=c.mrid  )", this);
		} catch (Exception e) {
			throw new DeviceTypeTreeDaoException("Query failed", e);
		}

	}

	/**
	 * Method 'listDeviceTypeTree';mrid,category,mrname,name,subcategory , model,
	 * objectid
	 * 
	 * @throws DeviceTypeTreeDaoException
	 * @return List<DeviceTypeTree>
	 */
	public List<DeviceTypeTree> listDeviceTypeTreeWithModelOid() throws DeviceTypeTreeDaoException {
		try {
			return jdbcTemplate.query("select distinct mrid,dtid,category,mrname,name,subcategory , model, objectid,'','' from("
			    +
			    // "select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory from t_manufacturer_info_init c left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a on a.mrid=c.mrid order by c.mrName)",
					// this);
			    "select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  "
			    + "left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a  on a.mrid=c.mrid  )", this);
		} catch (Exception e) {
			throw new DeviceTypeTreeDaoException("Query failed", e);
		}

	}

	/**
	 * Method 'execute'
	 * 
	 * @throws DeviceTypeTreeDaoException
	 * @return List<DeviceTypeTree>
	 */
	public List<DeviceTypeTree> findDeviceTypeByManufacture(String mrName) throws DeviceTypeTreeDaoException {
		try {
			// return
			// jdbcTemplate.query("select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory from t_manufacturer_info_init c "
			// +
			// "left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id ) a on a.mrid=c.mrid "
			// +
			// " where mrname='"+mrname+"'"
			// , this);
			return jdbcTemplate.query("select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  "
			    + " join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a  " + "on a.mrid=c.mrid  " + "where mrname=? ", this, mrName);
		} catch (Exception e) {
			throw new DeviceTypeTreeDaoException("Query failed", e);
		}

	}

	public List<DeviceTypeTree> findDeviceTypeByWhere(String mrName, String cateName) throws DeviceTypeTreeDaoException {
		try {
			// return
			// jdbcTemplate.query("select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory from t_manufacturer_info_init c "
			// +
			// "left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id ) a on a.mrid=c.mrid "
			// +
			// " where  mrname=' "+mrname +"' and a.name='"+category
			// +"' orderby mrname"
			// , this);
			return jdbcTemplate.query("select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  "
			    + " join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a  " + "on a.mrid=c.mrid  " + "where mrname=? and    a.name=? ", this, mrName,
			    cateName);
		} catch (Exception e) {
			throw new DeviceTypeTreeDaoException("Query failed", e);
		}

	}

	public List<DeviceTypeTree> findDeviceTypeByWhere(String mrName, String cateName, String subCategory) throws DeviceTypeTreeDaoException {
		try {
			// return
			// jdbcTemplate.query("select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory from t_manufacturer_info_init c "
			// +
			// "left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id ) a on a.mrid=c.mrid "
			// +
			// " where mrname='"+mrname+"' and a.name='"+category+"' and a.subcategory='"+subcategory+"'"
			// , this);
			return jdbcTemplate.query("select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  "
			    + " join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a  " + "on a.mrid=c.mrid  "
			    + "where mrname=? and    a.name=?  and    a.subCategory=?", this, mrName, cateName, subCategory);
		} catch (Exception e) {
			throw new DeviceTypeTreeDaoException("Query failed", e);
		}

	}

	/**
	 * Method 'findDeviceTypeByObjectID'
	 * 
	 * @throws DeviceTypeTreeDaoException
	 * @return List<DeviceTypeTree>
	 */
	public List<DeviceTypeTree> findDeviceTypeByObjectID(String objectid) throws DeviceTypeTreeDaoException {
		try {
			// return
			// jdbcTemplate.query("select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory from t_manufacturer_info_init c "
			// +
			// "left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id ) a on a.mrid=c.mrid "
			// +
			// " where mrname='"+mrname+"'"
			// , this);
			// System.out.println("select distinct mrid,0,category,mrname,name,subcategory , model, objectid,'','' from ("
			// +
			// "select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  "
			// +
			// "left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a  "
			// +
			// "on a.mrid=c.mrid  " +
			// " ) ss"+
			// "where objectid=? "+objectid);
			return jdbcTemplate.query("select distinct mrid,dtid,category,mrname,name,subcategory , model, objectid,'','' from ("
			    + "select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  "
			    + "left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a  " + "on a.mrid=c.mrid  " + " ) ss " + "where objectid=? ", this,
			    objectid);
		} catch (Exception e) {
			throw new DeviceTypeTreeDaoException("Query failed", e);
		}

	}

	/**
	 * Method 'findDeviceTypeByDeviceID'
	 * 
	 * @throws DeviceTypeTreeDaoException
	 * @return List<DeviceTypeTree>
	 */
	public List<DeviceTypeTree> findDeviceTypeByDeviceID(long devid) throws DeviceTypeTreeDaoException {
		try {
			// return
			// jdbcTemplate.query("select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory from t_manufacturer_info_init c "
			// +
			// "left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id ) a on a.mrid=c.mrid "
			// +
			// " where mrname='"+mrname+"'"
			// , this);
			return jdbcTemplate.query("select distinct mrid,dtid,category,mrname,name,subcategory , model, objectid,devid,'' from ("
			    + "select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  "
			    + "left join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a  " + "on a.mrid=c.mrid  " + " ) ss" + "where devidid=? ", this, devid);
		} catch (Exception e) {
			throw new DeviceTypeTreeDaoException("Query failed", e);
		}
	}

	public List<DeviceTypeTree> findDeviceTypeByWhereClause(String whereClause) throws DeviceTypeTreeDaoException {
		try {
			return jdbcTemplate.query("select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  "
			    + " join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id) a  " + "on a.mrid=c.mrid  " + whereClause + " order by c.mrname, a.model", this);
		} catch (Exception e) {
			throw new DeviceTypeTreeDaoException("Query failed", e);
		}

	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return DeviceTypeTree
	 */
	public DeviceTypeTree mapRow(ResultSet rs, int row) throws SQLException {
		DeviceTypeTree dto = new DeviceTypeTree();
		dto.setMrid(rs.getLong(1));
		dto.setDtid(rs.getLong(2));
		dto.setCategory(rs.getLong(3));
		dto.setMrName(rs.getString(4));
		dto.setCateName(rs.getString(5));
		dto.setSubCategory(rs.getString(6));
		dto.setModel(rs.getString(7));
		dto.setObjectid(rs.getString(8));
		dto.setLogo(rs.getString(9));
		dto.setDescription(rs.getString(10));
		return dto;
	}

	public List<DeviceTypeTree> searchByMulti(String mrName, String cateName, String subcate, String model, String objectid, String descr) throws DeviceTypeTreeDaoException {
		StringBuffer sbbase = new StringBuffer(
		    "select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  "
		        + " join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id)  a " + "on a.mrid=c.mrid  ");

		StringBuffer sb = addCondition(sbbase, "c.mrname", mrName);
		sb = addCondition(sb, "a.name", cateName);
		sb = addCondition(sb, "a.subCategory", subcate);
		sb = addCondition(sb, "a.model", model);
		sb = addCondition(sb, "a.objectid", objectid);
		sb = addCondition(sb, "a.description", descr);

		System.out.println("sb is " + sb.toString());

		try {
			return jdbcTemplate.query(sb.toString(), this);
		} catch (Exception e) {
			throw new DeviceTypeTreeDaoException("Query failed", e);
		}
	}

	private StringBuffer addCondition(StringBuffer sb, String colName, String val) {
		if (val != null && !val.equals("")) {
			if (sb.indexOf("where", 240) == -1) {
				sb.append(" where " + "Upper(" + colName + ")" + " like " + "'%" + val.toUpperCase() + "%'");
			} else {
				sb.append(" and " + "Upper(" + colName + ")" + " like " + "'%" + val.toUpperCase() + "%'");
			}
		}
		return sb;
	}

	public DeviceTypeTree findByDtid(long dtid) throws DeviceTypeTreeDaoException {
		List<DeviceTypeTree> list = null;
		try {
			list = jdbcTemplate.query("select c.mrid,a.dtid,a.category,c.mrname,a.name,a.subcategory , a.model,a.objectid,a.logo,a.description from t_manufacturer_info_init c  "
			    + " join (select * from t_device_type_init d,t_category_map_init b where d.category=b.id)  a " + "on a.mrid=c.mrid  and dtid=?", this, dtid);
			return list.size() == 0 ? null : list.get(0);
		} catch (Exception e) {
			throw new DeviceTypeTreeDaoException("Query failed", e);
		}

	}

}
