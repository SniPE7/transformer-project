package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dto.ModuleEventType;
import com.ibm.ncs.model.exceptions.ModuleEventTypeDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ModuleEventTypeDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ModuleEventType>, ModuleEventTypeDao
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
	 * Method 'execute'
	 * 
	 * @param eveid
	 * @param general
	 * @param ecode
	 * @throws ModuleEventTypeDaoException
	 * @return List<ModuleEventType>
	 */
	public List<ModuleEventType> execute(long eveid, long general, long ecode) throws ModuleEventTypeDaoException
	{
		try {
			return jdbcTemplate.query(" select  mname, mcode,  ETID, MODID, 	EVEID, ESTID, 	EVEOTHERNAME, ECODE ,GENERAL ,MAJOR, 	MINOR, OTHER, DESCRIPTION, USEflag   from  ( select b.mname, b.mcode,  ETID, a.MODID, 	EVEID, ESTID, 	EVEOTHERNAME, ECODE ,GENERAL ,MAJOR, 	MINOR, OTHER, a.DESCRIPTION, USEflag  from t_event_type_init a, t_module_info_init b where a.modid=b.modid and (a.general=0 or a.general=-1) and b.mcode=2) x  where eveid=? and general=? and ecode=?", this,eveid,general,ecode);
		}
		catch (Exception e) {
			throw new ModuleEventTypeDaoException("Query failed", e);
		}
		
	}

	
	/**
	 * Method 'findModuleEventTypeByEveidGeneralEcode'
	 * 
	 * @param eveid = event id
	 * @param general : general = -1 (private) ; general =0 (public)
	 * @param ecode : ecode = 1 (device); ecode= 6(line/port;ecode=7 ( icmp ??)
	 * @throws ModuleEventTypeDaoException
	 * @return List<ModuleEventType>
	 */
	public List<ModuleEventType> findModuleEventTypeByEveidGeneralEcode(long eveid,  long ecode) throws ModuleEventTypeDaoException
	{
		try {
			return jdbcTemplate.query(" select  mname, mcode,  ETID, MODID, 	EVEID, ESTID, 	EVEOTHERNAME, ECODE ,GENERAL ,MAJOR, 	MINOR, OTHER, DESCRIPTION, USEflag   from  ( select b.mname, b.mcode,  ETID, a.MODID, 	EVEID, ESTID, 	EVEOTHERNAME, ECODE ,GENERAL ,MAJOR, 	MINOR, OTHER, a.DESCRIPTION, USEflag  from t_event_type_init a, t_module_info_init b where a.modid=b.modid and (a.general=0 or a.general=-1) and b.mcode=2) x  where eveid=?  and ecode=?", this,eveid,ecode);
		}
		catch (Exception e) {
			throw new ModuleEventTypeDaoException("Query failed", e);
		}
		
	}
	
	
	/**
	 * Method 'findModuleEventTypeByEveidGeneralEcode'
	 * 
	 * 
	 * @param general : general = -1 (private) ; general =0 (public)
	 * @param ecode : ecode = 1 (device); ecode= 6(line/port;ecode=7 ( icmp ??)
	 * @throws ModuleEventTypeDaoException
	 * @return List<ModuleEventType>
	 */
	public List<ModuleEventType> findModuleEventTypeByGeneralEcode(  long ecode) throws ModuleEventTypeDaoException
	{
		try {
			return jdbcTemplate.query(" select  MODID, mname, mcode,EVEID,  ETID,  	 ESTID, 	EVEOTHERNAME, ECODE ,GENERAL ,MAJOR, 	MINOR, OTHER, DESCRIPTION, USEflag   from  ( select b.mname, b.mcode,  ETID, a.MODID, 	EVEID, ESTID, 	EVEOTHERNAME, ECODE ,GENERAL ,MAJOR, 	MINOR, OTHER, a.DESCRIPTION, USEflag  from t_event_type_init a, t_module_info_init b where a.modid=b.modid and (a.general=0 or a.general=-1) and b.mcode=2) x  where   ecode=?", this,ecode);
		}
		catch (Exception e) {
			throw new ModuleEventTypeDaoException("Query failed", e);
		}
		
	}
	
	
	public List<ModuleEventType> findModuleEventTypeByEcode(  long ecode) throws ModuleEventTypeDaoException
	{
//		System.out.println("dao invoked-------ecode:"+ecode);
		try {
			return jdbcTemplate.query(" select  MODID, mname, mcode,EVEID,  ETID,  	 ESTID, 	EVEOTHERNAME, ECODE ,GENERAL ,MAJOR, 	MINOR, OTHER, DESCRIPTION, USEflag   from  ( select b.mname, b.mcode,  ETID, a.MODID, 	EVEID, ESTID, 	EVEOTHERNAME, ECODE ,GENERAL ,MAJOR, 	MINOR, OTHER, a.DESCRIPTION, USEflag  from t_event_type_init a, t_module_info_init b where a.modid=b.modid  and b.mcode=4) x  where   ecode=?", this,ecode);
		}
		catch (Exception e) {
			throw new ModuleEventTypeDaoException("Query failed", e);
		}
		
	}
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ModuleEventType
	 */
	public ModuleEventType mapRow(ResultSet rs, int row) throws SQLException
	{
		ModuleEventType dto = new ModuleEventType();
		dto.setModid( rs.getLong( 1 ) );
		dto.setMname( rs.getString( 2 ) );
		dto.setMcode( rs.getLong( 3 ) );
		dto.setEveid( rs.getLong( 4 ) );
		dto.setEtid( rs.getLong( 5 ) );
		dto.setEstid( rs.getLong( 6 ) );
		dto.setEveothername( rs.getString( 7 ) );
		dto.setEcode( rs.getLong( 8 ) );
		dto.setGeneral( rs.getLong( 9 ) );
		dto.setMajor( rs.getString( 10 ) );
		dto.setMinor( rs.getString( 11 ) );
		dto.setOther( rs.getString( 12 ) );
		dto.setDescription( rs.getString( 13 ) );
		dto.setUseflag( rs.getString( 14 ) );
		return dto;
	}

}
