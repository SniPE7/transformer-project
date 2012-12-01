package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.IpAddrRangeCheckDao;
import com.ibm.ncs.model.dto.IpAddrRangeCheck;
import com.ibm.ncs.model.dto.IpAddrRangeCheckParent;
import com.ibm.ncs.model.exceptions.IpAddrRangeCheckDaoException;
import com.ibm.ncs.model.exceptions.IpAddrRangeCheckParentDaoException;

import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class IpAddrRangeCheckDaoImpl extends AbstractDAO implements ParameterizedRowMapper<IpAddrRangeCheck>, IpAddrRangeCheckDao
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
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> execute(long gid) throws IpAddrRangeCheckDaoException
	{
		try {
			return jdbcTemplate.query("select IPDECODE_MIN, IPDECODE_MAX from t_list_ip where GID in (select SUPID from T_grp_net where GID=?)", this,gid);
		}
		catch (Exception e) {
			throw new IpAddrRangeCheckDaoException("Query failed", e);
		}
		
	}

	
	/**
	 * Method 'findParentIpRange'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> findParentIpRange(long gid) throws IpAddrRangeCheckDaoException
	{
		try {
			return jdbcTemplate.query("select IPDECODE_MIN, IPDECODE_MAX from t_list_ip where GID in (select SUPID from T_grp_net where GID=?)", this,gid);
		}
		catch (Exception e) {
			throw new IpAddrRangeCheckDaoException("Query failed", e);
		}
		
	}
	
	/**
	 * Method 'findParentIpRange'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> findParentIpRangeBySupid(long gid,long supid) throws IpAddrRangeCheckDaoException
	{
		try {
			return jdbcTemplate.query("select IPDECODE_MIN, IPDECODE_MAX from t_list_ip where gid=?", this,supid);
		}
		catch (Exception e) {
			throw new IpAddrRangeCheckDaoException("Query failed", e);
		}
		
	}
	
	/**
	 * Method 'findSiblingIpRange'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> findSiblingIpRange(long gid) throws IpAddrRangeCheckDaoException
	{
		try {
			return jdbcTemplate.query("select a.IPDECODE_MIN,a.IPDECODE_MAX from t_list_ip a  where a.GID in  (select GID from T_GRP_NET b where b.SUPID=( select SUPID from T_GRP_NET c where c.GID=? ) and b.UNMALLOCEDIPSETFLAG<>1 and b.GID <>?)", this,gid,gid);
		}
		catch (Exception e) {
			throw new IpAddrRangeCheckDaoException("Query failed", e);
		}
		
	}
	
	/**
	 * Method 'findSiblingIpRange'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> findSiblingIpRange(long gid, long supid) throws IpAddrRangeCheckDaoException
	{
		try {
			return jdbcTemplate.query("select a.IPDECODE_MIN,a.IPDECODE_MAX from t_list_ip a  where a.GID in  (select GID from T_GRP_NET b where b.SUPID=? and b.UNMALLOCEDIPSETFLAG<>1 and b.GID <>?)", this,supid,gid);
		}
		catch (Exception e) {
			throw new IpAddrRangeCheckDaoException("Query failed", e);
		}
		
	}
	
	
	/**
	 * Method 'findSubIpRange'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> findSubIpRange(long gid) throws IpAddrRangeCheckDaoException
	{
		try {
			return jdbcTemplate.query("select a.IPDECODE_MIN,a.IPDECODE_MAX from t_list_ip a  where a.GID in  (select b.GID from t_grp_net b  where b.SUPID=?  and UNMALLOCEDIPSETFLAG<>1 )  ", this,gid);
		}
		catch (Exception e) {
			throw new IpAddrRangeCheckDaoException("Query failed", e);
		}
		
	}
	
	/**
	 * Method 'findInRange'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> findInRange(long gid) throws IpAddrRangeCheckDaoException
	{
		try {
			return jdbcTemplate.query("select a.IPDECODE_MIN,a.IPDECODE_MAX from t_list_ip a  where a.GID in  (select b.GID from t_grp_net b  where b.GID=?  and UNMALLOCEDIPSETFLAG<>1 )  ", this,gid);
		}
		catch (Exception e) {
			throw new IpAddrRangeCheckDaoException("Query failed", e);
		}
		
	}
	
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return IpAddrRangeCheck
	 */
	public IpAddrRangeCheck mapRow(ResultSet rs, int row) throws SQLException
	{
		IpAddrRangeCheck dto = new IpAddrRangeCheck();
		dto.setIpdecodeMin( rs.getLong( 1 ) );
		dto.setIpdecodeMax( rs.getLong( 2 ) );
		return dto;
	}

}
