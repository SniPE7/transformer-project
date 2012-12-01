package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.IpAddrRangeCheckParentDao;
import com.ibm.ncs.model.dto.IpAddrRangeCheckParent;
import com.ibm.ncs.model.exceptions.IpAddrRangeCheckParentDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class IpAddrRangeCheckParentDaoImpl extends AbstractDAO implements ParameterizedRowMapper<IpAddrRangeCheckParent>, IpAddrRangeCheckParentDao
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
	 * @throws IpAddrRangeCheckParentDaoException
	 * @return List<IpAddrRangeCheckParent>
	 */
	public List<IpAddrRangeCheckParent> execute(long gid) throws IpAddrRangeCheckParentDaoException
	{
		try {
			return jdbcTemplate.query("select IPDECODE_MIN, IPDECODE_MAX from t_list_ip where GID in (select SUPID from T_grp_net where GID=?)", this,gid);
		}
		catch (Exception e) {
			throw new IpAddrRangeCheckParentDaoException("Query failed", e);
		}
		
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return IpAddrRangeCheckParent
	 */
	public IpAddrRangeCheckParent mapRow(ResultSet rs, int row) throws SQLException
	{
		IpAddrRangeCheckParent dto = new IpAddrRangeCheckParent();
		dto.setIpdecodeMin( rs.getLong( 1 ) );
		dto.setIpdecodeMax( rs.getLong( 2 ) );
		return dto;
	}

}
