package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.PredefmibPolMapDao;
import com.ibm.ncs.model.dto.PredefmibPolMap;
import com.ibm.ncs.model.dto.PredefmibPolMapPk;
import com.ibm.ncs.model.exceptions.PredefmibPolMapDaoException;
import com.ibm.ncs.model.exceptions.TLinepolMapDaoException;

import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class PredefmibPolMapDaoImpl extends AbstractDAO implements ParameterizedRowMapper<PredefmibPolMap>, PredefmibPolMapDao
{
	protected SimpleJdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * Method 'setDataSource'
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return PredefmibPolMapPk
	 */
	@Transactional
	public PredefmibPolMapPk insert(PredefmibPolMap dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PDMID, MPID, PPID, MCODE, FLAG ) VALUES ( ?, ?, ?, ?, ? )",dto.getPdmid(),dto.isMpidNull()?null:dto.getMpid(),dto.isPpidNull()?null:dto.getPpid(),dto.isMcodeNull()?null:dto.getMcode(),dto.isFlagNull()?null:dto.getFlag());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the PREDEFMIB_POL_MAP table.
	 */
	@Transactional
	public void update(PredefmibPolMapPk pk, PredefmibPolMap dto) throws PredefmibPolMapDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET PDMID = ?, MPID = ?, PPID = ?, MCODE = ?, FLAG = ? WHERE PDMID = ?",dto.getPdmid(),dto.getMpid(),dto.getPpid(),dto.getMcode(),dto.getFlag(),pk.getPdmid());
	}

	/** 
	 * Deletes a single row in the PREDEFMIB_POL_MAP table.
	 */
	@Transactional
	public void delete(PredefmibPolMapPk pk) throws PredefmibPolMapDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PDMID = ?",pk.getPdmid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return PredefmibPolMap
	 */
	public PredefmibPolMap mapRow(ResultSet rs, int row) throws SQLException
	{
		PredefmibPolMap dto = new PredefmibPolMap();
		dto.setPdmid( rs.getLong( 1 ) );
		dto.setMpid( rs.getLong( 2 ) );
		if (rs.wasNull()) {
			dto.setMpidNull( true );
		}
		
		dto.setPpid( rs.getLong( 3 ) );
		if (rs.wasNull()) {
			dto.setPpidNull( true );
		}
		
		dto.setMcode( rs.getLong( 4 ) );
		if (rs.wasNull()) {
			dto.setMcodeNull( true );
		}
		
		dto.setFlag( rs.getInt( 5 ) );
		if (rs.wasNull()) {
			dto.setFlagNull( true );
		}
		
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "PREDEFMIB_POL_MAP";
	}

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'PDMID = :pdmid'.
	 */
	@Transactional
	public PredefmibPolMap findByPrimaryKey(long pdmid) throws PredefmibPolMapDaoException
	{
		try {
			List<PredefmibPolMap> list = jdbcTemplate.query("SELECT PDMID, MPID, PPID, MCODE, FLAG FROM " + getTableName() + " WHERE PDMID = ?", this,pdmid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new PredefmibPolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria ''.
	 */
	@Transactional
	public List<PredefmibPolMap> findAll() throws PredefmibPolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MPID, PPID, MCODE, FLAG FROM " + getTableName() + " ORDER BY PDMID", this);
		}
		catch (Exception e) {
			throw new PredefmibPolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'PDMID = :pdmid'.
	 */
	@Transactional
	public List<PredefmibPolMap> findByPredefmibInfo(long pdmid) throws PredefmibPolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MPID, PPID, MCODE, FLAG FROM " + getTableName() + " WHERE PDMID = ?", this,pdmid);
		}
		catch (Exception e) {
			throw new PredefmibPolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'PDMID = :pdmid'.
	 */
	@Transactional
	public List<PredefmibPolMap> findWherePdmidEquals(long pdmid) throws PredefmibPolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MPID, PPID, MCODE, FLAG FROM " + getTableName() + " WHERE PDMID = ? ORDER BY PDMID", this,pdmid);
		}
		catch (Exception e) {
			throw new PredefmibPolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'MPID = :mpid'.
	 */
	@Transactional
	public List<PredefmibPolMap> findWhereMpidEquals(long mpid) throws PredefmibPolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MPID, PPID, MCODE, FLAG FROM " + getTableName() + " WHERE MPID = ? ORDER BY MPID", this,mpid);
		}
		catch (Exception e) {
			throw new PredefmibPolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'PPID = :ppid'.
	 */
	@Transactional
	public List<PredefmibPolMap> findWherePpidEquals(long ppid) throws PredefmibPolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MPID, PPID, MCODE, FLAG FROM " + getTableName() + " WHERE PPID = ? ORDER BY PPID", this,ppid);
		}
		catch (Exception e) {
			throw new PredefmibPolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'MCODE = :mcode'.
	 */
	@Transactional
	public List<PredefmibPolMap> findWhereMcodeEquals(long mcode) throws PredefmibPolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MPID, PPID, MCODE, FLAG FROM " + getTableName() + " WHERE MCODE = ? ORDER BY MCODE", this,mcode);
		}
		catch (Exception e) {
			throw new PredefmibPolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'FLAG = :flag'.
	 */
	@Transactional
	public List<PredefmibPolMap> findWhereFlagEquals(int flag) throws PredefmibPolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MPID, PPID, MCODE, FLAG FROM " + getTableName() + " WHERE FLAG = ? ORDER BY FLAG", this,flag);
		}
		catch (Exception e) {
			throw new PredefmibPolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the PREDEFMIB_POL_MAP table that matches the specified primary-key value.
	 */
	public PredefmibPolMap findByPrimaryKey(PredefmibPolMapPk pk) throws PredefmibPolMapDaoException
	{
		return findByPrimaryKey( pk.getPdmid() );
	}

	/** 
	 * Deletes  rows in the PREDEFMIB_POL_MAP table BUT not exist in the table PREDEFMIB_INFO.
	 */
	@Transactional
	public void removeNoUseData() throws PredefmibPolMapDaoException{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PDMID NOT IN (select pdmid from PREDEFMIB_INFO)");
	}
}
