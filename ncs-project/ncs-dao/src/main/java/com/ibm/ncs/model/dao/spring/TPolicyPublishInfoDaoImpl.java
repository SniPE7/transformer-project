/**
 * 
 */
package com.ibm.ncs.model.dao.spring;

import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	
	private static Log log = LogFactory.getLog(TPolicyPublishInfoDaoImpl.class);

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
			List<PolicyPublishInfo> items = jdbcTemplate.query("SELECT PPIID, VERSION, VERSION_TAG, STATUS, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME FROM " + getTableName() + " WHERE PUBLISH_TIME is null and (status is null or status='D') ORDER BY VERSION DESC", this);
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
			List<PolicyPublishInfo> items = jdbcTemplate.query("SELECT PPIID, VERSION, VERSION_TAG, STATUS, DESCRIPTION, PUBLISH_TIME, CREATE_TIME, UPDATE_TIME FROM " + getTableName() + " WHERE PUBLISH_TIME is not null and status='R' ORDER BY PUBLISH_TIME DESC", this);
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

	public PolicyPublishInfo getAppliedVersion() throws TPolicyPublishInfoDaoException {
		try {
			List<PolicyPublishInfo> items = jdbcTemplate.query("select distinct ppi.* from t_policy_base pb inner join t_policy_template_ver ptv on ptv.ptvid=pb.ptvid inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid", this);
			if (items != null && items.size() > 0) {
				return items.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new TPolicyPublishInfoDaoException("Query failed", e);
		}
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
		dto.setPublishTime(rs.getTimestamp(6));
		dto.setCreateTime(rs.getTimestamp(7));
		dto.setUpdateTime(rs.getTimestamp(8));
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
		System.out.println(String.format("发布策略集: %s", toDto));

		jdbcTemplate.update("UPDATE " + getTableName() + " SET STATUS='H'");
		toDto.setStatus("R");
		jdbcTemplate.update("UPDATE " + getTableName() + " SET VERSION_TAG = ?, STATUS=?, DESCRIPTION = ?, PUBLISH_TIME = ?, UPDATE_TIME = ? WHERE PPIID = ?", toDto.getVersionTag(), toDto.getStatus(), toDto.getDescription(), toDto.getPublishTime(), toDto.getUpdateTime(), toDto.getPpiid());
		
	}
	/**
	 * Updates a single row in the T_POLICY_BASE table.
	 */
	@Transactional
	public void upgrade(Writer writer) throws TPolicyPublishInfoDaoException {
		PolicyPublishInfo releasedPPI = this.getReleasedVersion();
		if (releasedPPI == null) {
			throw new TPolicyPublishInfoDaoException("没有发布的策略集, 无需升级!");
		}
		PolicyPublishInfo branchPPI = this.getReleasedVersion();
		if (branchPPI == null) {
			//throw new TPolicyPublishInfoDaoException("本地从未应用过策略, 需要先进行迁移!");
		}

		PrintWriter out = new PrintWriter(writer);
		int total = 0;
		{
			// 更新策略集
			String sql = "update t_policy_base pb set ptvid=(select to_ptvid from v_policy_tplt_ver_change where to_ppiid=(select ppiid from v_current_released_ppiid) and from_ppiid=(select ppiid from v_branch_current_ppiid) and from_ptvid=pb.ptvid) where ptvid > 0 and (select to_ptvid from v_policy_tplt_ver_change where to_ppiid=(select ppiid from v_current_released_ppiid) and from_ppiid=(select ppiid from v_branch_current_ppiid) and from_ptvid=pb.ptvid) is not null";
			total = jdbcTemplate.update(sql);
			log.info(String.format("升级策略数量(修改原有策略): %s个", total));
			out.println(String.format("升级策略数量(修改原有策略): %s个", total));
      
			sql = "delete from t_policy_details pd where (mpid,modid,eveid) not in (select (select distinct mpid from t_policy_base where ptvid=per.ptvid), modid,eveid from t_policy_event_rule per where ptvid in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid))) " +
					"and mpid in (select mpid from t_policy_base where ptvid>0)";
			total = jdbcTemplate.update(sql);
			log.info(String.format("升级策略数量(删除多余性能指标): %s个", total));
			out.println(String.format("升级策略数量(删除多余性能指标): %s个", total));
			
			sql = "insert into t_policy_details " +
					"               ( MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE) " +
					"select distinct pb.mpid,  " +
					"MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE " +
					"from " +
					"  t_policy_event_rule per inner join t_policy_base pb on pb.ptvid = per.ptvid " +
					"where " +
					"  (pb.mpid, modid, eveid) not in (select mpid, modid, eveid from t_policy_details where mpid in (select mpid from t_policy_base where ptvid>0))";
			total = jdbcTemplate.update(sql);
			log.info(String.format("升级策略数量(添加新增性能指标): %s个", total));
			out.println(String.format("升级策略数量(添加新增性能指标): %s个", total));
			
			sql = "update " +
					"  t_policy_details pd " +
					"  set  " +
					"    POLL=(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		VALUE_1=(select distinct VALUE_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		SEVERITY_1=(select distinct SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		FILTER_A=(select distinct FILTER_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		VALUE_2=(select distinct VALUE_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		SEVERITY_2=(select distinct SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		FILTER_B=(select distinct FILTER_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		SEVERITY_A=(select distinct SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		SEVERITY_B=(select distinct SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		OIDGROUP=(select distinct OIDGROUP from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		OGFLAG=(select distinct OGFLAG from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		VALUE_1_LOW=(select distinct VALUE_1_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		VALUE_2_LOW=(select distinct VALUE_2_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		V1L_SEVERITY_1=(select distinct V1L_SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		V1L_SEVERITY_A=(select distinct V1L_SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		V2L_SEVERITY_2=(select distinct V2L_SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		V2L_SEVERITY_B=(select distinct V2L_SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		COMPARETYPE=(select distinct COMPARETYPE from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"  where  " +
					"  (modid, eveid) in (select modid, eveid from t_policy_details where mpid in (select mpid from t_policy_base where ptvid>0)) " +
					"  and ( " +
					"    POLL<>(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or VALUE_1<>(select distinct VALUE_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or SEVERITY_1<>(select distinct SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or FILTER_A<>(select distinct FILTER_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or VALUE_2<>(select distinct VALUE_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or SEVERITY_2<>(select distinct SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or FILTER_B<>(select distinct FILTER_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or SEVERITY_A<>(select distinct SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or SEVERITY_B<>(select distinct SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or OIDGROUP<>(select distinct OIDGROUP from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or OGFLAG<>(select distinct OGFLAG from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or VALUE_1_LOW<>(select distinct VALUE_1_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or VALUE_2_LOW<>(select distinct VALUE_2_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or V1L_SEVERITY_1<>(select distinct V1L_SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or V1L_SEVERITY_A<>(select distinct V1L_SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or V2L_SEVERITY_2<>(select distinct V2L_SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or V2L_SEVERITY_B<>(select distinct V2L_SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or COMPARETYPE<>(select distinct COMPARETYPE from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		) ";
			total = jdbcTemplate.update(sql);
			log.info(String.format("升级策略数量(变更性能指标): %s个", total));
			out.println(String.format("升级策略数量(变更性能指标): %s个", total));
			
			// 删除不在设备类型范围内的设备策略应用关系
			sql = "delete from t_devpol_map dm where (dm.mpid, dm.devid) not in (select mpid, devid from V_MP_DEVICE_SCOPE) and (select ptvid from t_policy_base p where p.mpid=dm.mpid) > 0";
			total = jdbcTemplate.update(sql);
			log.info(String.format("升级策略数量(删除不在设备类型范围内的设备映射): %s个", total));
			out.println(String.format("升级策略数量(删除不在设备类型范围内的设备映射): %s个", total));

			// 删除不在设备类型范围内的端口策略应用关系
			sql = "delete from t_linepol_map lm where (lm.mpid, lm.ptid) not in (select mpid, p.ptid from V_MP_DEVICE_SCOPE mds inner join t_port_info p on p.devid=mds.devid) and (select ptvid from t_policy_base p where p.mpid=lm.mpid) > 0";
			total = jdbcTemplate.update(sql);
			log.info(String.format("升级策略数量(删除不在设备类型范围内的端口映射): %s个", total));
			out.println(String.format("升级策略数量(删除不在设备类型范围内的端口映射): %s个", total));
			
			// 删除不在设备类型范围内的MIB策略应用关系
			sql = "delete from PREDEFMIB_POL_MAP pm where (pm.mpid, pm.pdmid) not in (select mpid, p.pdmid from V_MP_DEVICE_SCOPE mds inner join PREDEFMIB_INFO p on p.devid=mds.devid) and (select ptvid from t_policy_base p where p.mpid=pm.mpid) > 0";
			total = jdbcTemplate.update(sql);
			log.info(String.format("升级策略数量(删除不在设备类型范围内的MIB Index映射): %s个", total));
			out.println(String.format("升级策略数量(删除不在设备类型范围内的MIB Index映射): %s个", total));
		}
		
		{
			//-- 添加
			//--  添加策略详细信息
			String sql = "insert into t_policy_details " +
					"               ( MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE) " +
					"select PTVID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE " +
					"from t_policy_event_rule " +
					"where (PTVID, MODID, EVEID) in ( " +
					"select distinct PTVID, MODID, EVEID " +
					"from " +
					"  t_policy_event_rule " +
					"where " +
					"  ptvid in ( select ptv.ptvid from t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid	where ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0) ) " +
					") and (PTVID, MODID, EVEID) not in (select distinct MPID, MODID, EVEID from t_policy_details) ";
			total = jdbcTemplate.update(sql);		
			log.info(String.format("新添加性能指标: %s", total));
			out.println(String.format("新添加性能指标: %s", total));

			// 添加策略定义
			sql = "insert into t_policy_base(mpid, ptvid, mpname, category, description) " +
					"	select " +
					"	  distinct ptv.ptvid, ptv.ptvid, pt.mpname, pt.category, pt.description " +
					"	from " +
					"	 t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid " +
					"	                           inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid " +
					"	where ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0) ";
			total = jdbcTemplate.update(sql);
			log.info(String.format("新添加策略: %s", total));
			out.println(String.format("新添加策略: %s", total));
		}
		{
			//-- 删除多余的
			total = jdbcTemplate.update("delete from t_devpol_map where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)))");
			total = jdbcTemplate.update("delete from t_linepol_map where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)))");
			total = jdbcTemplate.update("delete from predefmib_pol_map  where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)))");
			total = jdbcTemplate.update("delete from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid))");
			log.info(String.format("删除策略: %s", total));
			out.println(String.format("删除策略: %s", total));
		}
	}

	@Transactional
  public void migrate(Writer writer) throws TPolicyPublishInfoDaoException {
		PolicyPublishInfo releasedPPI = this.getReleasedVersion();
		if (releasedPPI == null) {
			throw new TPolicyPublishInfoDaoException("没有发布的策略集, 无需迁移!");
		}
		PolicyPublishInfo branchPPI = this.getAppliedVersion();
		if (branchPPI != null) {
			throw new TPolicyPublishInfoDaoException("本地已经应用过策略模板, 无需迁移!");
		}

		PrintWriter out = new PrintWriter(writer);
		int total = 0;
		{
			// 更新策略集
			String sql = "update t_policy_base pb set ptvid=(select ptvid from t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid inner join T_POLICY_PUBLISH_INFO ppi on ppi.ppiid=ptv.ppiid where ptv.ppiid=(select ppiid from v_current_released_ppiid) and pb.mpname=pt.mpname and pb.category=pt.category) where ptvid > 0";
			total = jdbcTemplate.update(sql);
			log.info(String.format("迁移策略数量(修改原有策略): %s个", total));
			out.println(String.format("迁移策略数量(修改原有策略): %s个", total));

			sql = "delete from t_policy_details pd where (mpid,modid,eveid) not in (select (select distinct mpid from t_policy_base where ptvid=per.ptvid), modid,eveid from t_policy_event_rule per where ptvid in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid))) " +
					"and mpid in (select mpid from t_policy_base where ptvid>0) ";
			total = jdbcTemplate.update(sql);
			log.info(String.format("迁移策略数量(删除多余性能指标): %s个", total));
			out.println(String.format("迁移策略数量(删除多余性能指标): %s个", total));

			sql = "insert into t_policy_details " +
					"               ( MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE) " +
					"select distinct pb.mpid,  " +
					"MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE " +
					"from " +
					"  t_policy_event_rule per inner join t_policy_base pb on pb.ptvid = per.ptvid " +
					"where " +
					"  (modid, eveid) not in (select modid, eveid from t_policy_details where mpid in (select mpid from t_policy_base where ptvid>0)) ";
			total = jdbcTemplate.update(sql);
			log.info(String.format("迁移策略数量(添加新增性能指标): %s个", total));
			out.println(String.format("迁移策略数量(添加新增性能指标): %s个", total));
			
			sql = "update " +
					"  t_policy_details pd " +
					"  set  " +
					"    POLL=(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		VALUE_1=(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		SEVERITY_1=(select distinct SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		FILTER_A=(select distinct FILTER_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		VALUE_2=(select distinct VALUE_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		SEVERITY_2=(select distinct SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		FILTER_B=(select distinct FILTER_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		SEVERITY_A=(select distinct SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		SEVERITY_B=(select distinct SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		OIDGROUP=(select distinct OIDGROUP from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		OGFLAG=(select distinct OGFLAG from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		VALUE_1_LOW=(select distinct VALUE_1_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		VALUE_2_LOW=(select distinct VALUE_2_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		V1L_SEVERITY_1=(select distinct V1L_SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		V1L_SEVERITY_A=(select distinct V1L_SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		V2L_SEVERITY_2=(select distinct V2L_SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		V2L_SEVERITY_B=(select distinct V2L_SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid), " +
					"		COMPARETYPE=(select distinct COMPARETYPE from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"  where  " +
					"  (modid, eveid) in (select modid, eveid from t_policy_details where mpid in (select mpid from t_policy_base where ptvid>0)) " +
					"  and ( " +
					"    POLL<>(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or VALUE_1<>(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or SEVERITY_1<>(select distinct SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or FILTER_A<>(select distinct FILTER_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or VALUE_2<>(select distinct VALUE_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or SEVERITY_2<>(select distinct SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or FILTER_B<>(select distinct FILTER_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or SEVERITY_A<>(select distinct SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or SEVERITY_B<>(select distinct SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or OIDGROUP<>(select distinct OIDGROUP from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or OGFLAG<>(select distinct OGFLAG from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or VALUE_1_LOW<>(select distinct VALUE_1_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or VALUE_2_LOW<>(select distinct VALUE_2_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or V1L_SEVERITY_1<>(select distinct V1L_SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or V1L_SEVERITY_A<>(select distinct V1L_SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or V2L_SEVERITY_2<>(select distinct V2L_SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or V2L_SEVERITY_B<>(select distinct V2L_SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		or COMPARETYPE<>(select distinct COMPARETYPE from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid) " +
					"		) ";
			total = jdbcTemplate.update(sql);
			log.info(String.format("迁移策略数量(修改性能指标): %s个", total));
			out.println(String.format("迁移策略数量(修改性能指标): %s个", total));
		}
		{
			//-- 添加
			//--  添加策略详细信息
			String sql = "insert into t_policy_details " +
					"               ( MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE) " +
					"select PTVID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE " +
					"from t_policy_event_rule " +
					"where (PTVID, MODID, EVEID) in ( " +
					"select distinct PTVID, MODID, EVEID " +
					"from " +
					"  t_policy_event_rule " +
					"where " +
					"  ptvid in ( select ptv.ptvid from t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid	where ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0) ) " +
					") and (PTVID, MODID, EVEID) not in (select distinct MPID, MODID, EVEID from t_policy_details) ";
			total = jdbcTemplate.update(sql);		
			log.info(String.format("新添加性能指标: %s", total));
			out.println(String.format("新添加性能指标: %s", total));

			// 添加策略定义
			sql = "insert into t_policy_base(mpid, ptvid, mpname, category, description) " +
					"	select " +
					"	  distinct ptv.ptvid, ptv.ptvid, pt.mpname, pt.category, pt.description " +
					"	from " +
					"	 t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid " +
					"	                           inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid " +
					"	where ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0) ";
			total = jdbcTemplate.update(sql);
			log.info(String.format("新添加策略: %s", total));
			out.println(String.format("新添加策略: %s", total));
		}
		{
			//-- 删除多余的
			total = jdbcTemplate.update("delete from t_devpol_map where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)))");
			total = jdbcTemplate.update("delete from t_linepol_map where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)))");
			total = jdbcTemplate.update("delete from predefmib_pol_map  where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)))");
			total = jdbcTemplate.update("delete from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid))");
			log.info(String.format("删除策略: %s", total));
			out.println(String.format("删除策略: %s", total));
		}
  }

}


