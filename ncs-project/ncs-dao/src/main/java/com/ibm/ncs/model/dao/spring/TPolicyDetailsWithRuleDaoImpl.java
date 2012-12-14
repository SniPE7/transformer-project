package com.ibm.ncs.model.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.ncs.model.dao.TPolicyDetailsWithRuleDao;
import com.ibm.ncs.model.dto.TPolicyDetailsWithRule;
import com.ibm.ncs.model.dto.TPolicyDetailsWithRulePk;
import com.ibm.ncs.model.exceptions.TPolicyDetailsWithRuleDaoException;

public class TPolicyDetailsWithRuleDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TPolicyDetailsWithRule>, TPolicyDetailsWithRuleDao {
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
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TPolicyDetailsWithRulePk
	 */
	@Transactional
	public TPolicyDetailsWithRulePk insert(TPolicyDetailsWithRule dto) {
		jdbcTemplate
		    .update(
		        "INSERT INTO "
		            + getTableName()
		            + " ( PTVID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",
		        dto.getPtvid(), dto.getModid(), dto.getEveid(), dto.isPollNull() ? null : dto.getPoll(), dto.getValue1(), dto.isSeverity1Null() ? null : dto.getSeverity1(),
		        dto.getFilterA(), dto.getValue2(), dto.isSeverity2Null() ? null : dto.getSeverity2(), dto.getFilterB(), dto.isSeverityANull() ? null : dto.getSeverityA(),
		        dto.isSeverityBNull() ? null : dto.getSeverityB(), dto.getOidgroup(), dto.getOgflag(), dto.getValue1Low(), dto.getValue2Low(),
		        dto.isV1lSeverity1Null() ? null : dto.getV1lSeverity1(), dto.isV1lSeverityANull() ? null : dto.getV1lSeverityA(),
		        dto.isV2lSeverity2Null() ? null : dto.getV2lSeverity2(), dto.isV2lSeverityBNull() ? null : dto.getV2lSeverityB(), dto.getComparetype());
		return dto.createPk();
	}

	/**
	 * Updates a single row in the T_POLICY_EVENT_RULE table.
	 */
	@Transactional
	public void update(TPolicyDetailsWithRulePk pk, TPolicyDetailsWithRule dto) throws TPolicyDetailsWithRuleDaoException {
		jdbcTemplate
		    .update(
		        "UPDATE "
		            + getTableName()
		            + " SET PTVID = ?, MODID = ?, EVEID = ?, POLL = ?, VALUE_1 = ?, SEVERITY_1 = ?, FILTER_A = ?, VALUE_2 = ?, SEVERITY_2 = ?, FILTER_B = ?, SEVERITY_A = ?, SEVERITY_B = ?, OIDGROUP = ?, OGFLAG = ?, VALUE_1_LOW = ?, VALUE_2_LOW = ?, V1L_SEVERITY_1 = ?, V1L_SEVERITY_A = ?, V2L_SEVERITY_2 = ?, V2L_SEVERITY_B = ?, COMPARETYPE = ? WHERE PTVID = ? AND MODID = ? AND EVEID = ?",
		        dto.getPtvid(), dto.getModid(), dto.getEveid(), dto.isPollNull() ? null : dto.getPoll(), dto.getValue1(), dto.isSeverity1Null() ? null : dto.getSeverity1(),
		        dto.getFilterA(), dto.getValue2(), dto.isSeverity2Null() ? null : dto.getSeverity2(), dto.getFilterB(), dto.isSeverityANull() ? null : dto.getSeverityA(),
		        dto.isSeverityBNull() ? null : dto.getSeverityB(), dto.getOidgroup(), dto.getOgflag(), dto.getValue1Low(), dto.getValue2Low(),
		        dto.isV1lSeverity1Null() ? null : dto.getV1lSeverity1(), dto.isV1lSeverityANull() ? null : dto.getV1lSeverityA(),
		        dto.isV2lSeverity2Null() ? null : dto.getV2lSeverity2(), dto.isV2lSeverityBNull() ? null : dto.getV2lSeverityB(), dto.getComparetype(), pk.getPtvid(), pk.getModid(),
		        pk.getEveid());
	}

	/**
	 * Deletes a multi rows in the T_POLICY_EVENT_RULE table.
	 */
	@Transactional
	public void delete(long ptvid) throws TPolicyDetailsWithRuleDaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PTVID = ? ", ptvid);
	}

	/**
	 * Deletes a single row in the T_POLICY_EVENT_RULE table.
	 */
	@Transactional
	public void delete(TPolicyDetailsWithRulePk pk) throws TPolicyDetailsWithRuleDaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PTVID = ? AND MODID = ? AND EVEID = ?", pk.getPtvid(), pk.getModid(), pk.getEveid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TPolicyDetailsWithRule
	 */
	public TPolicyDetailsWithRule mapRow(ResultSet rs, int row) throws SQLException {
		TPolicyDetailsWithRule dto = new TPolicyDetailsWithRule();
		dto.setPtvid(rs.getLong(1));
		dto.setModid(rs.getLong(2));
		dto.setEveid(rs.getLong(3));
		dto.setPoll(rs.getLong(4));
		if (rs.wasNull()) {
			dto.setPollNull(true);
		}

		dto.setValue1(rs.getString(5));
		dto.setSeverity1(rs.getLong(6));
		if (rs.wasNull()) {
			dto.setSeverity1Null(true);
		}

		dto.setFilterA(rs.getString(7));
		dto.setValue2(rs.getString(8));
		dto.setSeverity2(rs.getLong(9));
		if (rs.wasNull()) {
			dto.setSeverity2Null(true);
		}

		dto.setFilterB(rs.getString(10));
		dto.setSeverityA(rs.getLong(11));
		if (rs.wasNull()) {
			dto.setSeverityANull(true);
		}

		dto.setSeverityB(rs.getLong(12));
		if (rs.wasNull()) {
			dto.setSeverityBNull(true);
		}

		dto.setOidgroup(rs.getString(13));
		dto.setOgflag(rs.getString(14));
		dto.setValue1Low(rs.getString(15));
		dto.setValue2Low(rs.getString(16));
		dto.setV1lSeverity1(rs.getLong(17));
		if (rs.wasNull()) {
			dto.setV1lSeverity1Null(true);
		}

		dto.setV1lSeverityA(rs.getLong(18));
		if (rs.wasNull()) {
			dto.setV1lSeverityANull(true);
		}

		dto.setV2lSeverity2(rs.getLong(19));
		if (rs.wasNull()) {
			dto.setV2lSeverity2Null(true);
		}

		dto.setV2lSeverityB(rs.getLong(20));
		if (rs.wasNull()) {
			dto.setV2lSeverityBNull(true);
		}

		dto.setComparetype(rs.getString(21));
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "T_POLICY_EVENT_RULE";
	}

}
