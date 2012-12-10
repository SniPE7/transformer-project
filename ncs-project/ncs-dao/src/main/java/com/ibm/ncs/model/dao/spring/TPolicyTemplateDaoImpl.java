package com.ibm.ncs.model.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.ncs.model.dao.TPolicyTemplateDao;
import com.ibm.ncs.model.dto.PolicyTemplate;
import com.ibm.ncs.model.exceptions.TPolicyTemplateDaoException;

public class TPolicyTemplateDaoImpl extends AbstractDAO implements ParameterizedRowMapper<PolicyTemplate>, TPolicyTemplateDao {

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

	public TPolicyTemplateDaoImpl() {
		super();
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "T_POLICY_TEMPLATE";
	}

	@Transactional
	public void insert(PolicyTemplate dto) throws TPolicyTemplateDaoException {
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PTID, MPNAME, STATUS, CATEGORY, DESCRIPTION ) VALUES ( ?, ?, ?, ?, ? )", dto.getPtid(), dto.getMpname(), dto.getStatus(), dto.getCategory(), dto.getDescription());
	}

	@Transactional
	public void update(long ptid, PolicyTemplate dto) throws TPolicyTemplateDaoException {
		jdbcTemplate.update("UPDATE " + getTableName() + " SET MPNAME = ?, STATUS = ?, CATEGORY = ?, DESCRIPTION = ? WHERE PTID = ?", dto.getMpname(), dto.getStatus(), dto.getCategory(), dto.getDescription(), ptid);
	}

	@Transactional
	public void delete(long ptid) throws TPolicyTemplateDaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PTID = ?", ptid);
	}

	public PolicyTemplate findById(String ptid) throws TPolicyTemplateDaoException {
		try {
			List<PolicyTemplate> items = jdbcTemplate.query("SELECT PTID, MPNAME, STATUS, CATEGORY, DESCRIPTION FROM " + getTableName() + " WHERE PTID=?", this, ptid);
			if (items != null && items.size() > 0) {
				return items.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new TPolicyTemplateDaoException("Query failed", e);
		}
	}

	public PolicyTemplate findByMpname(String mpname) throws TPolicyTemplateDaoException {
		try {
			List<PolicyTemplate> items = jdbcTemplate.query("SELECT PTID, MPNAME, STATUS, CATEGORY, DESCRIPTION FROM " + getTableName() + " WHERE MPNAME=?", this, mpname);
			if (items != null && items.size() > 0) {
				return items.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new TPolicyTemplateDaoException("Query failed", e);
		}
  }

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.simple.ParameterizedRowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public PolicyTemplate mapRow(ResultSet rs, int arg1) throws SQLException {
		PolicyTemplate dto = new PolicyTemplate();
		dto.setPtid(rs.getLong(1));
		dto.setMpname(rs.getString(2));
		dto.setStatus(rs.getString(3));
		dto.setCategory(rs.getLong(4));
		dto.setDescription(rs.getString(5));
		return dto;
	}

}
