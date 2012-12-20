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
		return Integer.toString(this.jdbcTemplate.queryForInt("SELECT MAX(TO_NUMBER(VERSION)) FROM "  + getTableName()) + 1);
	}
	
	public PolicyPublishInfo getDraftVersion() throws TPolicyPublishInfoDaoException {
		try {
			List<PolicyPublishInfo> items = jdbcTemplate.query("SELECT PPIID, VERSION, VERSION_TAG, STATUS, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME FROM " + getTableName() + " WHERE PUBLISH_TIME is null ORDER BY VERSION DESC", this);
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
			List<PolicyPublishInfo> items = jdbcTemplate.query("SELECT PPIID, VERSION, VERSION_TAG, STATUS, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME FROM " + getTableName() + " WHERE PUBLISH_TIME is not null ORDER BY PUBLISH_TIME DESC", this);
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
			return jdbcTemplate.query("SELECT PPIID, VERSION, VERSION_TAG, STATUS, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME FROM " + getTableName() + " WHERE PUBLISH_TIME is not null ORDER BY PUBLISH_TIME DESC",
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
		dto.setStatus(rs.getString(4));
		dto.setDescription(rs.getString(5));
		dto.setPublishTime(rs.getTime(6));
		dto.setCreateTime(rs.getTime(7));
		dto.setUpdateTime(rs.getTime(8));
		return dto;
	}

	@Transactional
	public void insert(PolicyPublishInfo dto) throws TPolicyPublishInfoDaoException {
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PPIID, VERSION, VERSION_TAG, STATUS, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )", dto.getPpiid(), dto.getVersion(), dto.getVersionTag(), dto.getStatus(),
		    dto.getDescription(), dto.getPublishTime(), dto.getCreateTime(), dto.getUpdateTime());
	}

	/**
	 * Updates a single row in the T_POLICY_BASE table.
	 */
	@Transactional
	public void update(long ppiid, PolicyPublishInfo dto) throws TPolicyPublishInfoDaoException {
		jdbcTemplate.update("UPDATE " + getTableName() + " SET VERSION_TAG = ?, STATUS=?, DESCRIPTION = ?, PUBLISH_TIME = ?, UPDATE_TIME = ? WHERE PPIID = ?", dto.getVersionTag(), dto.getStatus(), dto.getDescription(), dto.getPublishTime(), dto.getUpdateTime(), dto.getPpiid());
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
			List<PolicyPublishInfo> items = jdbcTemplate.query("SELECT PPIID, VERSION, VERSION_TAG, STATUS, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME FROM " + getTableName() + " WHERE PPIID=?", this, ppiid);
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
		if (srcPpiid == destPpiid) {
			 throw new TPolicyPublishInfoDaoException(String.format("Src[%s] PPIID equal Dest[%s] PPIID!", srcPpiid, destPpiid));
		}
		try {
			String copyPTVsql = 
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
			int ret = jdbcTemplate.update(copyPTVsql, destPpiid, srcPpiid);
			
			String copyPTScope = "insert into T_POLICY_TEMPLATE_SCOPE(PTVID, DTID, MRID) " +
					" select  " +
					"   (select ptvid from T_POLICY_TEMPLATE_VER dptv where dptv.ppiid=? and dptv.ptid=ptv.ptid), " +
					"   pts.dtid, " +
					"   pts.mrid " +
					" from " +
					"   T_POLICY_TEMPLATE_SCOPE pts inner join T_POLICY_TEMPLATE_VER ptv on ptv.ptvid=pts.ptvid " +
					"                               inner join T_POLICY_PUBLISH_INFO ppi on ptv.ppiid=ppi.ppiid " +
					" where " +
					"   ptv.ppiid=?"; 
			ret = jdbcTemplate.update(copyPTScope, destPpiid, srcPpiid);
			
			String copyRule = "insert into T_POLICY_EVENT_RULE(PTVID, " +
					"   MODID, " +
					"   EVEID, " +
					"   POLL, " +
					"   VALUE_1, " +
					"   VALUE_1_RULE, " +
					"   SEVERITY_1, " +
					"   FILTER_A, " +
					"   VALUE_2, " +
					"   VALUE_2_RULE, " +
					"   SEVERITY_2, " +
					"   FILTER_B, " +
					"   SEVERITY_A, " +
					"   SEVERITY_B, " +
					"   OIDGROUP, " +
					"   OGFLAG, " +
					"   VALUE_1_LOW, " +
					"   VALUE_1_LOW_RULE, " +
					"   VALUE_2_LOW, " +
					"   VALUE_2_LOW_RULE, " +
					"   V1L_SEVERITY_1, " +
					"   V1L_SEVERITY_A, " +
					"   V2L_SEVERITY_2, " +
					"   V2L_SEVERITY_B, " +
					"   COMPARETYPE) " +
					" select  " +
					"   (select ptvid from T_POLICY_TEMPLATE_VER dptv where dptv.ppiid=? and dptv.ptid=ptv.ptid), " +
					"   per.MODID, " +
					"   per.EVEID, " +
					"   per.POLL, " +
					"   per.VALUE_1, " +
					"   per.VALUE_1_RULE, " +
					"   per.SEVERITY_1, " +
					"   per.FILTER_A, " +
					"   per.VALUE_2, " +
					"   per.VALUE_2_RULE, " +
					"   per.SEVERITY_2, " +
					"   per.FILTER_B, " +
					"   per.SEVERITY_A, " +
					"   per.SEVERITY_B, " +
					"   per.OIDGROUP, " +
					"   per.OGFLAG, " +
					"   per.VALUE_1_LOW, " +
					"   per.VALUE_1_LOW_RULE, " +
					"   per.VALUE_2_LOW, " +
					"   per.VALUE_2_LOW_RULE, " +
					"   per.V1L_SEVERITY_1, " +
					"   per.V1L_SEVERITY_A, " +
					"   per.V2L_SEVERITY_2, " +
					"   per.V2L_SEVERITY_B, " +
					"   per.COMPARETYPE " +
					" from " +
					"   T_POLICY_EVENT_RULE per inner join T_POLICY_TEMPLATE_VER ptv on per.ptvid=ptv.ptvid " +
					"                               inner join T_POLICY_PUBLISH_INFO ppi on ppi.ppiid=ptv.ppiid " +
					" where " +
					"   ptv.ppiid=? ";
			ret = jdbcTemplate.update(copyRule, destPpiid, srcPpiid);
		} catch (Exception e) {
			throw new TPolicyPublishInfoDaoException("Query failed", e);
		}
  }

	/**
	 * Updates a single row in the T_POLICY_BASE table.
	 */
	@Transactional
	public void release(long toPpiid, PolicyPublishInfo toDto) throws TPolicyPublishInfoDaoException {
		jdbcTemplate.update("UPDATE " + getTableName() + " SET VERSION_TAG = ?, STATUS=?, DESCRIPTION = ?, PUBLISH_TIME = ?, UPDATE_TIME = ? WHERE PPIID = ?", toDto.getVersionTag(), toDto.getStatus(), toDto.getDescription(), toDto.getPublishTime(), toDto.getUpdateTime(), toDto.getPpiid());
		PolicyPublishInfo releasedPPI = this.getReleasedVersion();
		if (releasedPPI == null) {
			System.out.println("Could not find last release PolicyTemplate Set.");
			return;
		}
		long fromPpiid = releasedPPI.getPpiid();
		{
			// 更新策略集
			// update t_policy_base pb set ptvid=(select to_ptvid from v_policy_tplt_ver_change where from_ppiid=10001 and to_ppiid=257235009 and from_ptvid=pb.ptvid) where ptvid > 0
			String sql = "update t_policy_base pb set ptvid=(select to_ptvid from v_policy_tplt_ver_change where from_ppiid=? and to_ppiid=? and from_ptvid=pb.ptvid) where ptvid > 0";
			jdbcTemplate.update(sql, fromPpiid, toPpiid);
		}
		{
			//-- 添加
			//--  添加策略定义
			//insert into t_policy_base(mpid, ptvid, mpname, category, description)
			// select to_ptvid, to_ptvid, mpname, category, description
			// from v_policy_tplt_ver_change
			// where from_ppiid=10001 and to_ppiid=257235009 and to_ptvid not in (select ptvid from t_policy_base where ptvid>0)
			//;  
			//--  添加策略详细信息
			//insert into t_policy_details
	    //		               ( MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE)
			//select distinct PTVID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE
			//from
	    //		  t_policy_event_rule
			//where
	    //		  ptvid in ( select to_ptvid from v_policy_tplt_ver_change where from_ppiid=10001 and to_ppiid=257235009 and to_ptvid not in (select ptvid from t_policy_base where ptvid>0) )
			//;
			String sql = "insert into t_policy_base(mpid, ptvid, mpname, category, description) " +
					" select to_ptvid, to_ptvid, mpname, category, description " +
					" from v_policy_tplt_ver_change " +
					" where from_ppiid=? and to_ppiid=? and to_ptvid not in (select ptvid from t_policy_base where ptvid>0) ";
			jdbcTemplate.update(sql, fromPpiid, toPpiid);
			
			sql = "insert into t_policy_details " +
					"               ( MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE) " +
					" select distinct PTVID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE " +
					"  from " +
					"    t_policy_event_rule " +
					"  where " +
					"    ptvid in ( select to_ptvid from v_policy_tplt_ver_change where from_ppiid=? and to_ppiid=? and to_ptvid not in (select ptvid from t_policy_base where ptvid>0) ) ";
			jdbcTemplate.update(sql, fromPpiid, toPpiid);
		}
		{
			//-- 删除多余的
			//delete from t_devpol_map where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select to_ptvid from v_policy_tplt_ver_change where from_ppiid=10001 and to_ppiid=257235009));
			//delete from t_linepol_map where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select to_ptvid from v_policy_tplt_ver_change where from_ppiid=10001 and to_ppiid=257235009));
			//delete from predefmib_pol_map  where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select to_ptvid from v_policy_tplt_ver_change where from_ppiid=10001 and to_ppiid=257235009));
			//delete from t_policy_base where ptvid > 0 and ptvid not in (select to_ptvid from v_policy_tplt_ver_change where from_ppiid=10001 and to_ppiid=257235009);
			jdbcTemplate.update("delete from t_devpol_map where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select to_ptvid from v_policy_tplt_ver_change where from_ppiid=? and to_ppiid=?))", fromPpiid, toPpiid);
			jdbcTemplate.update("delete from t_linepol_map where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select to_ptvid from v_policy_tplt_ver_change where from_ppiid=? and to_ppiid=?))", fromPpiid, toPpiid);
			jdbcTemplate.update("delete from predefmib_pol_map  where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select to_ptvid from v_policy_tplt_ver_change where from_ppiid=? and to_ppiid=?))", fromPpiid, toPpiid);
			jdbcTemplate.update("delete from t_policy_base where ptvid > 0 and ptvid not in (select to_ptvid from v_policy_tplt_ver_change where from_ppiid=? and to_ppiid=?)", fromPpiid, toPpiid);
		}
	}

}


