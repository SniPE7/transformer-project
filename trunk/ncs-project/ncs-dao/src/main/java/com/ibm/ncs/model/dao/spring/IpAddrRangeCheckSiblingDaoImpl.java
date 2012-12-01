package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.IpAddrRangeCheckSiblingDao;
import com.ibm.ncs.model.dto.IpAddrRangeCheckSibling;
import com.ibm.ncs.model.exceptions.IpAddrRangeCheckSiblingDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class IpAddrRangeCheckSiblingDaoImpl extends AbstractDAO implements ParameterizedRowMapper<IpAddrRangeCheckSibling>, IpAddrRangeCheckSiblingDao
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
	 * @throws IpAddrRangeCheckSiblingDaoException
	 * @return List<IpAddrRangeCheckSibling>
	 */
	public List<IpAddrRangeCheckSibling> execute(long gid) throws IpAddrRangeCheckSiblingDaoException
	{
		try {
			return jdbcTemplate.query("select a.IPDECODE_MIN,a.IPDECODE_MAX from t_list_ip a  where a.GID in  (select GID from T_GRP_NET b where b.SUPID=( select SUPID from T_GRP_NET c where c.GID=? ) and b.UNMALLOCEDIPSETFLAG<>1 and b.GID <>?)", this,gid,gid);
		}
		catch (Exception e) {
			throw new IpAddrRangeCheckSiblingDaoException("Query failed", e);
		}
		
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return IpAddrRangeCheckSibling
	 */
	public IpAddrRangeCheckSibling mapRow(ResultSet rs, int row) throws SQLException
	{
		IpAddrRangeCheckSibling dto = new IpAddrRangeCheckSibling();
		dto.setIpdecodeMin( rs.getLong( 1 ) );
		dto.setIpdecodeMax( rs.getLong( 2 ) );
		return dto;
	}

}
