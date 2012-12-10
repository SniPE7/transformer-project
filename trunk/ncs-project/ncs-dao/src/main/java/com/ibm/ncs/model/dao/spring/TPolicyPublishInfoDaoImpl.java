/**
 * 
 */
package com.ibm.ncs.model.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.ncs.model.dao.PolicyPublishInfo;
import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.model.exceptions.TPolicyBaseDaoException;
import com.ibm.ncs.model.exceptions.TPolicyPublishInfoDaoException;

/**
 * @author zhaodonglu
 * 
 */
public class TPolicyPublishInfoDaoImpl extends AbstractDAO implements ParameterizedRowMapper<PolicyPublishInfo>, TPolicyPublishInfoDao {

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
	 * 
	 */
	public TPolicyPublishInfoDaoImpl() {
		super();
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "T_POLICY_PUBLISH_INFO";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ncs.model.dao.TPolicyPublishInfoDao#getNewVersion4Draft()
	 */
	public String getNewVersion4Draft() {
		return Integer.toString(this.jdbcTemplate.queryForInt("SELECT MAX(VERSION) FROM "  + getTableName()) + 1);
	}
	
	public PolicyPublishInfo getDraftVersion() throws TPolicyPublishInfoDaoException {
		try {
			List<PolicyPublishInfo> items = jdbcTemplate.query("SELECT PPIID, VERSION, VERSION_TAG, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME FROM " + getTableName() + " WHERE PUBLISH_TIME is null ORDER BY VERSION DESC", this);
			if (items != null && items.size() > 0) {
				return items.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new TPolicyPublishInfoDaoException("Query failed", e);
		}
	}

	public PolicyPublishInfo getReleasedVersion() throws TPolicyPublishInfoDaoException {
		try {
			List<PolicyPublishInfo> items = jdbcTemplate.query("SELECT PPIID, VERSION, VERSION_TAG, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME FROM " + getTableName() + " WHERE PUBLISH_TIME is not null ORDER BY PUBLISH_TIME DESC", this);
			if (items != null && items.size() > 0) {
				return items.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new TPolicyPublishInfoDaoException("Query failed", e);
		}
  }

	public List<PolicyPublishInfo> getHistoryVersions() throws TPolicyPublishInfoDaoException {
		List<PolicyPublishInfo> items = this.getAllHistoryAndReleasedVersion();
		if (items != null && items.size() > 0) {
			 items.remove(0);
		}
	  return items;
  }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.ncs.model.dao.TPolicyPublishInfoDao#getAllHistoryAndReleasedVersion
	 * ()
	 */
	public List<PolicyPublishInfo> getAllHistoryAndReleasedVersion() throws TPolicyPublishInfoDaoException {
		try {
			return jdbcTemplate.query("SELECT PPIID, VERSION, VERSION_TAG, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME FROM " + getTableName() + " WHERE PUBLISH_TIME is not null ORDER BY PUBLISH_TIME DESC",
			    this);
		} catch (Exception e) {
			throw new TPolicyPublishInfoDaoException("Query failed", e);
		}
	}

	public PolicyPublishInfo mapRow(ResultSet rs, int arg1) throws SQLException {
		PolicyPublishInfo dto = new PolicyPublishInfo();
		dto.setPpiid(rs.getLong(1));
		dto.setVersion(rs.getString(2));
		dto.setVersionTag(rs.getString(3));
		dto.setDescription(rs.getString(4));
		dto.setPublishTime(rs.getTime(5));
		dto.setCreateTime(rs.getTime(6));
		dto.setUpdateTime(rs.getTime(7));
		return dto;
	}

	@Transactional
	public void insert(PolicyPublishInfo dto) throws TPolicyPublishInfoDaoException {
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PPIID, VERSION, VERSION_TAG, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME ) VALUES ( ?, ?, ?, ?, ?, ?, ? )", dto.getPpiid(), dto.getVersion(), dto.getVersionTag(),
		    dto.getDescription(), dto.getPublishTime(), dto.getCreateTime(), dto.getUpdateTime());
	}

	/**
	 * Updates a single row in the T_POLICY_BASE table.
	 */
	@Transactional
	public void update(long ppiid, PolicyPublishInfo dto) throws TPolicyBaseDaoException {
		jdbcTemplate.update("UPDATE " + getTableName() + " SET VERSION_TAG = ?, DESCRIPTION = ?, PUBLISH_TIME = ?, UPDATE_TIME = ? WHERE PPIID = ?", dto.getVersionTag(), dto.getDescription(), dto.getPublishTime(), dto.getUpdateTime(), dto.getPpiid());
	}

	/**
	 * Deletes a single row in the T_POLICY_BASE table.
	 */
	@Transactional
	public void delete(long ppiid) throws TPolicyBaseDaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PPIID = ?", ppiid);
	}

	public PolicyPublishInfo findById(String ppiid) throws TPolicyPublishInfoDaoException {
		try {
			List<PolicyPublishInfo> items = jdbcTemplate.query("SELECT PPIID, VERSION, VERSION_TAG, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME FROM " + getTableName() + " WHERE PPIID=?", this, ppiid);
			if (items != null && items.size() > 0) {
				return items.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new TPolicyPublishInfoDaoException("Query failed", e);
		}
  }

	@Transactional
	public void copyAllPolicyTemplateVer(long srcPpiid, long destPpiid) throws TPolicyPublishInfoDaoException {
		try {
			String sql = 
					"insert into T_POLICY_TEMPLATE_VER(PTVID, PT_VERSION, PTID, PPIID, STATUS, DESCRIPTION) " +
				  "select  " +
			    "NM_SEQ.NEXTVAL,  " +
			    "TO_CHAR(TO_NUMBER(PT_VERSION) + 1),  " +
			    "ptv.PTID,  " +
			    "?,  " +
			    "'D',  " +
			    "PT.DESCRIPTION " +
			  "from  " +
			    "T_POLICY_TEMPLATE_VER ptv left join T_POLICY_TEMPLATE pt on ptv.ptid=pt.ptid  " +
			  "where ppiid=? ";
			int ret = jdbcTemplate.update(sql, destPpiid, srcPpiid);
		} catch (Exception e) {
			throw new TPolicyPublishInfoDaoException("Query failed", e);
		}
  }

}
