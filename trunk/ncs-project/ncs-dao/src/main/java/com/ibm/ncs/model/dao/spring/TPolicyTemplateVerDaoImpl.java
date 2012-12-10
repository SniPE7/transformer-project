package com.ibm.ncs.model.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.ncs.model.dao.TPolicyTemplateVerDao;
import com.ibm.ncs.model.dto.PolicyTemplateVer;
import com.ibm.ncs.model.exceptions.TPolicyTemplateVerDaoException;

public class TPolicyTemplateVerDaoImpl extends AbstractDAO implements ParameterizedRowMapper<PolicyTemplateVer>, TPolicyTemplateVerDao {

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

	public TPolicyTemplateVerDaoImpl() {
		super();
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "T_POLICY_TEMPLATE_VER";
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.simple.ParameterizedRowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public PolicyTemplateVer mapRow(ResultSet rs, int arg1) throws SQLException {
		PolicyTemplateVer dto = new PolicyTemplateVer();
		dto.setPtvid(rs.getLong(1));
		dto.setPtVersion(rs.getString(2));
		dto.setPtid(rs.getLong(3));
		dto.setPpiid(rs.getLong(4));
		dto.setStatus(rs.getString(5));
		dto.setDescription(rs.getString(6));
		return dto;
	}

	@Transactional
	public void insert(PolicyTemplateVer dto) throws TPolicyTemplateVerDaoException {
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PTVID, PT_VERSION, PTID, PPIID, STATUS, DESCRIPTION ) VALUES ( ?, ?, ?, ?, ?, ? )", dto.getPtvid(), dto.getPtVersion(), dto.getPtid(), dto.getPpiid(), dto.getStatus(), dto.getDescription());
	}

	@Transactional
	public void update(long ptvid, PolicyTemplateVer dto) throws TPolicyTemplateVerDaoException {
		jdbcTemplate.update("UPDATE " + getTableName() + " SET PT_VERSION = ?, PTID=?, PPIID=?, STATUS = ?, DESCRIPTION = ? WHERE PTVID = ?", dto.getPtVersion(), dto.getPtid(), dto.getPpiid(), dto.getStatus(), dto.getDescription(), ptvid);
	}

	@Transactional
	public void delete(long ptvid) throws TPolicyTemplateVerDaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PTVID = ?", ptvid);
	}

	public PolicyTemplateVer findById(String ptvid) throws TPolicyTemplateVerDaoException {
		try {
			List<PolicyTemplateVer> items = jdbcTemplate.query("SELECT PTVID, PT_VERSION, PTID, PPIID, STATUS, DESCRIPTION FROM " + getTableName() + " WHERE PTVID=?", this, ptvid);
			if (items != null && items.size() > 0) {
				return items.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new TPolicyTemplateVerDaoException("Query failed", e);
		}
	}

	public List<PolicyTemplateVer> findByPublishInfoId(String ppiid) throws TPolicyTemplateVerDaoException {
		try {
			List<PolicyTemplateVer> items = jdbcTemplate.query("SELECT ptv.PTVID, ptv.PT_VERSION, ptv.PTID, ptv.PPIID, ptv.STATUS, ptv.DESCRIPTION from " + 
																												 " T_POLICY_TEMPLATE_VER ptv inner join T_POLICY_PUBLISH_INFO ppi on PTV.PPIID=PPI.PPIID " + 
                                                         "                           inner join T_POLICY_TEMPLATE pt on PTV.PTID=PT.PTID " + 
                                                         "where PTV.PPIID=? order by pt.mpname asc", this, ppiid);
			return items;
		} catch (Exception e) {
			throw new TPolicyTemplateVerDaoException("Query failed", e);
		}
  }

}
