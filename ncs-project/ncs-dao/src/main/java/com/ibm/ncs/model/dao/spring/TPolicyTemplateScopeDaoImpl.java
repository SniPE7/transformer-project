package com.ibm.ncs.model.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.ncs.model.dao.TPolicyTemplateScopeDao;
import com.ibm.ncs.model.dto.PolicyTemplateScope;
import com.ibm.ncs.model.exceptions.TPolicyTemplateScopeDaoException;

public class TPolicyTemplateScopeDaoImpl extends AbstractDAO implements ParameterizedRowMapper<PolicyTemplateScope>, TPolicyTemplateScopeDao {

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

	public TPolicyTemplateScopeDaoImpl() {
		super();
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "T_POLICY_TEMPLATE_SCOPE";
	}

	@Transactional
	public void insert(PolicyTemplateScope dto) throws TPolicyTemplateScopeDaoException {
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PTVID, DTID, MRID ) VALUES ( ?, ?, ? )", dto.getPtvid(), (dto.getDtid() == 0?null:dto.getDtid()), (dto.getMrid() == 0?null:dto.getMrid()));
	}

	@Transactional
	public void update(PolicyTemplateScope dto) throws TPolicyTemplateScopeDaoException {
		jdbcTemplate.update("UPDATE " + getTableName() + " SET PTVID = ?, DTID = ?, MRID = ? WHERE PTVID=? AND DTID=? AND MRID=?", dto.getPtvid(), dto.getDtid(), dto.getMrid(), dto.getPtvid(), dto.getDtid(), dto.getMrid());
	}

	@Transactional
	public void delete(PolicyTemplateScope dto) throws TPolicyTemplateScopeDaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PTVID=? AND DTID=? AND MRID=?", dto.getPtvid(), dto.getDtid(), dto.getMrid());
	}

	public PolicyTemplateScope findByIDs(long ptvid, long dtid, long mrid) throws TPolicyTemplateScopeDaoException {
		try {
			List<PolicyTemplateScope> items = jdbcTemplate.query("SELECT PTVID, DTID, MRID FROM " + getTableName() + " WHERE PTVID=? AND DTID=? AND MRID=?", this, ptvid, dtid, mrid);
			if (items != null && items.size() > 0) {
				return items.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new TPolicyTemplateScopeDaoException("Query failed", e);
		}
  }

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.simple.ParameterizedRowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public PolicyTemplateScope mapRow(ResultSet rs, int arg1) throws SQLException {
		PolicyTemplateScope dto = new PolicyTemplateScope();
		dto.setPtvid(rs.getLong(1));
		dto.setDtid(rs.getLong(2));
		dto.setMrid(rs.getLong(3));
		return dto;
	}

	public List<PolicyTemplateScope> findByPtvd(String ptvid) throws TPolicyTemplateScopeDaoException {
		try {
			List<PolicyTemplateScope> items = jdbcTemplate.query("SELECT PTVID, DTID, MRID FROM T_POLICY_TEMPLATE_SCOPE  WHERE PTVID=?", this, ptvid);
			return items;
		} catch (Exception e) {
			throw new TPolicyTemplateScopeDaoException("Query failed", e);
		}
  }

	public void deleteAllByPtvid(long ptvid) throws TPolicyTemplateScopeDaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PTVID=? ", ptvid);
  }
}
