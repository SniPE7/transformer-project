package com.ibm.ncs.model.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ibm.ncs.model.dao.PolDetailDspDao;
import com.ibm.ncs.model.dto.PolDetailDsp;
import com.ibm.ncs.model.exceptions.PolDetailDspDaoException;

public class PolDetailDspDaoImpl extends AbstractDAO implements ParameterizedRowMapper<PolDetailDsp>, PolDetailDspDao {
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
	 * @param mpid
	 * @param modid
	 * @param general
	 * @param ecode
	 * @param mcode
	 * @param mname
	 * @throws PolDetailDspDaoException
	 * @return List<PolDetailDsp>
	 */
	public List<PolDetailDsp> execute(long mpid, long modid, long general, long ecode, long mcode, java.lang.String mname) throws PolDetailDspDaoException {
		try {
			return jdbcTemplate
			    .query(
			        "select * from  ( select a.MPID ,	a.MODID ,	a.EVEID ,	POLL, 	VALUE_1, 	 SEVERITY_1, 	FILter_A, 	VALUE_2, 	SEVERITY_2 ,	FILter_B, 	 SEVERITY_A ,	SEVERITY_B, 	OIDGROUP ,	OGFlag ,	VALUE_1_LOW ,	VALUE_2_LOW,  V1L_SEVERITY_1, 	V1L_SEVERITY_A, 	V2L_SEVERITY_2 ,	V2L_SEVERITY_B, CompareType, b.major,b.ecode,b.general ,c.mname, c.mcode, NULL as ptvid, NULL as VALUE_1_RULE, NULL as VALUE_2_RULE, NULL as VALUE_1_LOW_RULE, NULL as VALUE_2_LOW_RULE from t_policy_details a left join t_event_type_init b on a.modid=b.modid and a.eveid=b.eveid  left join t_module_info_init c on a.modid=c.modid) x  where mpid=? and modid=? and general=? and ecode=? and mcode=? and mname=? ",
			        this, mpid, modid, general, ecode, mcode, mname);
		} catch (Exception e) {
			throw new PolDetailDspDaoException("Query failed", e);
		}

	}

	/**
	 * Method 'findByMpid'
	 * 
	 * @param mpid
	 * @param mcode
	 * @throws PolDetailDspDaoException
	 * @return List<PolDetailDsp>
	 */
	public List<PolDetailDsp> findByMpid(long mpid) throws PolDetailDspDaoException {
		try {
			return jdbcTemplate
			    .query(
			        "select * from  ( select a.MPID ,	a.MODID ,	a.EVEID ,	POLL, 	VALUE_1, 	 SEVERITY_1, 	FILter_A, 	VALUE_2, 	SEVERITY_2 ,	FILter_B, 	 SEVERITY_A ,	SEVERITY_B, 	OIDGROUP ,	OGFlag ,	VALUE_1_LOW ,	VALUE_2_LOW,  V1L_SEVERITY_1, 	V1L_SEVERITY_A, 	V2L_SEVERITY_2 ,	V2L_SEVERITY_B, CompareType, b.major,b.ecode,b.general ,c.mname, c.mcode, NULL as ptvid, NULL as VALUE_1_RULE, NULL as VALUE_2_RULE, NULL as VALUE_1_LOW_RULE, NULL as VALUE_2_LOW_RULE from t_policy_details a left join t_event_type_init b on a.modid=b.modid and a.eveid=b.eveid  left join t_module_info_init c on a.modid=c.modid) x  where mpid=? ",
			        this, mpid);
		} catch (Exception e) {
			throw new PolDetailDspDaoException("Query failed", e);
		}

	}

	/**
	 * Method 'findByPtvid'
	 * 
	 * @param ptvid
	 * @throws PolDetailDspDaoException
	 * @return List<PolDetailDsp>
	 */
	public List<PolDetailDsp> findByPtvid(long ptvid) throws PolDetailDspDaoException {
		try {
			return jdbcTemplate
			    .query(
			        "select * from  ( select NULL ,	a.MODID ,	a.EVEID ,	POLL, 	VALUE_1, 	 SEVERITY_1, 	FILter_A, 	VALUE_2, 	SEVERITY_2 ,	FILter_B, 	 SEVERITY_A ,	SEVERITY_B, 	OIDGROUP ,	OGFlag ,	VALUE_1_LOW ,	VALUE_2_LOW,  V1L_SEVERITY_1, 	V1L_SEVERITY_A, 	V2L_SEVERITY_2 ,	V2L_SEVERITY_B, CompareType, b.major,b.ecode,b.general ,c.mname, c.mcode, a.PTVID, a.VALUE_1_RULE, a.VALUE_2_RULE, a.VALUE_1_LOW_RULE, a.VALUE_2_LOW_RULE  from T_POLICY_EVENT_RULE a left join t_event_type_init b on a.modid=b.modid and a.eveid=b.eveid  left join t_module_info_init c on a.modid=c.modid) x  where ptvid=? ",
			        this, ptvid);
		} catch (Exception e) {
			throw new PolDetailDspDaoException("Query failed", e);
		}

	}

	/**
	 * Method 'findByMpidMcode'
	 * 
	 * @param mpid
	 * @param mcode
	 * @throws PolDetailDspDaoException
	 * @return List<PolDetailDsp>
	 */
	public List<PolDetailDsp> findByMpidMcode(long mpid, long mcode) throws PolDetailDspDaoException {
		try {
			return jdbcTemplate
			    .query(
			        "select * from  ( select a.MPID ,	a.MODID ,	a.EVEID ,	POLL, 	VALUE_1, 	 SEVERITY_1, 	FILter_A, 	VALUE_2, 	SEVERITY_2 ,	FILter_B, 	 SEVERITY_A ,	SEVERITY_B, 	OIDGROUP ,	OGFlag ,	VALUE_1_LOW ,	VALUE_2_LOW,  V1L_SEVERITY_1, 	V1L_SEVERITY_A, 	V2L_SEVERITY_2 ,	V2L_SEVERITY_B, CompareType,  b.major,b.ecode,b.general ,c.mname, c.mcode, NULL as ptvid, NULL as VALUE_1_RULE, NULL as VALUE_2_RULE, NULL as VALUE_1_LOW_RULE, NULL as VALUE_2_LOW_RULE from t_policy_details a left join t_event_type_init b on a.modid=b.modid and a.eveid=b.eveid  left join t_module_info_init c on a.modid=c.modid) x  where mpid=? and mcode=?",
			        this, mpid, mcode);
		} catch (Exception e) {
			throw new PolDetailDspDaoException("Query failed", e);
		}

	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return PolDetailDsp
	 */
	public PolDetailDsp mapRow(ResultSet rs, int row) throws SQLException {
		PolDetailDsp dto = new PolDetailDsp();
		dto.setMpid(rs.getLong(1));
		if (rs.wasNull())
			dto.setMpidNull(true);
		dto.setModid(rs.getLong(2));
		if (rs.wasNull())
			dto.setModidNull(true);
		dto.setEveid(rs.getLong(3));
		if (rs.wasNull())
			dto.setEveidNull(true);
		dto.setPoll(rs.getLong(4));
		if (rs.wasNull())
			dto.setPollNull(true);
		dto.setValue1(rs.getString(5));
		dto.setSeverity1(rs.getLong(6));
		if (rs.wasNull())
			dto.setSeverity1Null(true);
		dto.setFilterA(rs.getString(7));
		dto.setValue2(rs.getString(8));
		dto.setSeverity2(rs.getLong(9));
		if (rs.wasNull())
			dto.setSeverity2Null(true);
		dto.setFilterB(rs.getString(10));
		dto.setSeverityA(rs.getLong(11));
		if (rs.wasNull())
			dto.setSeverityANull(true);
		dto.setSeverityB(rs.getLong(12));
		if (rs.wasNull())
			dto.setSeverityBNull(true);
		dto.setOidgroup(rs.getString(13));
		dto.setOgflag(rs.getString(14));
		dto.setValue1low(rs.getString(15));
		dto.setValue2low(rs.getString(16));
		dto.setV1lseverity1(rs.getLong(17));
		if (rs.wasNull())
			dto.setV1lseverity1Null(true);
		dto.setV1lseverityA(rs.getLong(18));
		if (rs.wasNull())
			dto.setV1lseverityANull(true);
		dto.setV2lseverity2(rs.getLong(19));
		if (rs.wasNull())
			dto.setV2lseverity2Null(true);
		dto.setV2lseverityB(rs.getLong(20));
		if (rs.wasNull())
			dto.setV2lseverityBNull(true);
		dto.setCompareType(rs.getString(21));
		dto.setMajor(rs.getString(22));
		dto.setEcode(rs.getLong(23));
		dto.setGeneral(rs.getLong(24));
		dto.setMname(rs.getString(25));
		dto.setMcode(rs.getLong(26));
		
		dto.setPtvid(rs.getLong(27));
		dto.setValue1Rule(rs.getString(28));
		dto.setValue2Rule(rs.getString(29));
		dto.setValue1LowRule(rs.getString(30));
		dto.setValue2LowRule(rs.getString(31));
		return dto;
	}

}
