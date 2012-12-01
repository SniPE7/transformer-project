package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.IpAddrRangeCheckSonDao;
import com.ibm.ncs.model.dto.IpAddrRangeCheckSon;
import com.ibm.ncs.model.exceptions.IpAddrRangeCheckSonDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class IpAddrRangeCheckSonDaoImpl extends AbstractDAO implements ParameterizedRowMapper<IpAddrRangeCheckSon>, IpAddrRangeCheckSonDao
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
	 * @throws IpAddrRangeCheckSonDaoException
	 * @return List<IpAddrRangeCheckSon>
	 */
	public List<IpAddrRangeCheckSon> execute(long gid) throws IpAddrRangeCheckSonDaoException
	{
		try {
			return jdbcTemplate.query("select a.IPDECODE_MIN,a.IPDECODE_MAX from t_list_ip a  where a.GID in  (select b.GID from t_grp_net b  where b.SUPID=?  and UNMALLOCEDIPSETFLAG<>1 )  ", this,gid);
		}
		catch (Exception e) {
			throw new IpAddrRangeCheckSonDaoException("Query failed", e);
		}
		
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return IpAddrRangeCheckSon
	 */
	public IpAddrRangeCheckSon mapRow(ResultSet rs, int row) throws SQLException
	{
		IpAddrRangeCheckSon dto = new IpAddrRangeCheckSon();
		dto.setIpdecodeMin( rs.getLong( 1 ) );
		dto.setIpdecodeMax( rs.getLong( 2 ) );
		return dto;
	}

}
