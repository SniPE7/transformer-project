package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.IcmpThresholdsDao;
import com.ibm.ncs.model.dto.IcmpThresholds;
import com.ibm.ncs.model.dto.IcmpThresholdsPk;
import com.ibm.ncs.model.exceptions.IcmpThresholdsDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class IcmpThresholdsDaoImpl extends AbstractDAO implements ParameterizedRowMapper<IcmpThresholds>, IcmpThresholdsDao {
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
	 * @return IcmpThresholdsPk
	 */
	@Transactional
	public IcmpThresholdsPk insert(IcmpThresholds dto) {
		jdbcTemplate.update("INSERT INTO " + getTableName()
		    + " ( IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",
		    dto.getIpaddress(), dto.getBtime(), dto.getEtime(), dto.getThreshold(), dto.getComparetype(), dto.getSeverity1(), dto.getSeverity2(),
		    dto.isFilterflag1Null() ? null : dto.getFilterflag1(), dto.isFilterflag2Null() ? null : dto.getFilterflag2(), dto.getVarlist(), dto.getSummarycn());
		return dto.createPk();
	}

	/**
	 * Updates a single row in the ICMP_THRESHOLDS table.
	 */
	@Transactional
	public void update(IcmpThresholdsPk pk, IcmpThresholds dto) throws IcmpThresholdsDaoException {
		jdbcTemplate
		    .update(
		        "UPDATE "
		            + getTableName()
		            + " SET IPADDRESS = ?, BTIME = ?, ETIME = ?, THRESHOLD = ?, COMPARETYPE = ?, SEVERITY1 = ?, SEVERITY2 = ?, FILTERFLAG1 = ?, FILTERFLAG2 = ?, VARLIST = ?, SUMMARYCN = ? WHERE IPADDRESS = ?",
		        dto.getIpaddress(), dto.getBtime(), dto.getEtime(), dto.getThreshold(), dto.getComparetype(), dto.getSeverity1(), dto.getSeverity2(), dto.getFilterflag1(),
		        dto.getFilterflag2(), dto.getVarlist(), dto.getSummarycn(), pk.getIpaddress());
	}

	/**
	 * Deletes a single row in the ICMP_THRESHOLDS table.
	 */
	@Transactional
	public void delete(IcmpThresholdsPk pk) throws IcmpThresholdsDaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE IPADDRESS = ?", pk.getIpaddress());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return IcmpThresholds
	 */
	public IcmpThresholds mapRow(ResultSet rs, int row) throws SQLException {
		IcmpThresholds dto = new IcmpThresholds();
		dto.setIpaddress(rs.getString(1));
		dto.setBtime(rs.getString(2));
		dto.setEtime(rs.getString(3));
		dto.setThreshold(rs.getString(4));
		dto.setComparetype(rs.getString(5));
		dto.setSeverity1(rs.getString(6));
		dto.setSeverity2(rs.getString(7));
		dto.setFilterflag1(rs.getLong(8));
		if (rs.wasNull()) {
			dto.setFilterflag1Null(true);
		}

		dto.setFilterflag2(rs.getLong(9));
		if (rs.wasNull()) {
			dto.setFilterflag2Null(true);
		}

		dto.setVarlist(rs.getString(10));
		dto.setSummarycn(rs.getString(11));
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "ICMP_THRESHOLDS";
	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'IPADDRESS = :ipaddress'.
	 */
	@Transactional
	public IcmpThresholds findByPrimaryKey(String ipaddress) throws IcmpThresholdsDaoException {
		try {
			List<IcmpThresholds> list = jdbcTemplate.query(
			    "SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			        + " WHERE IPADDRESS = ?", this, ipaddress);
			return list.size() == 0 ? null : list.get(0);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria ''.
	 */
	@Transactional
	public List<IcmpThresholds> findAll() throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " ORDER BY IPADDRESS", this);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'IPADDRESS = :ipaddress'.
	 */
	@Transactional
	public List<IcmpThresholds> findWhereIpaddressEquals(String ipaddress) throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " WHERE IPADDRESS = ? ORDER BY IPADDRESS", this, ipaddress);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'BTIME = :btime'.
	 */
	@Transactional
	public List<IcmpThresholds> findWhereBtimeEquals(String btime) throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " WHERE BTIME = ? ORDER BY BTIME", this, btime);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'ETIME = :etime'.
	 */
	@Transactional
	public List<IcmpThresholds> findWhereEtimeEquals(String etime) throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " WHERE ETIME = ? ORDER BY ETIME", this, etime);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'THRESHOLD = :threshold'.
	 */
	@Transactional
	public List<IcmpThresholds> findWhereThresholdEquals(String threshold) throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " WHERE THRESHOLD = ? ORDER BY THRESHOLD", this, threshold);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'COMPARETYPE = :comparetype'.
	 */
	@Transactional
	public List<IcmpThresholds> findWhereComparetypeEquals(String comparetype) throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " WHERE COMPARETYPE = ? ORDER BY COMPARETYPE", this, comparetype);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'SEVERITY1 = :severity1'.
	 */
	@Transactional
	public List<IcmpThresholds> findWhereSeverity1Equals(String severity1) throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " WHERE SEVERITY1 = ? ORDER BY SEVERITY1", this, severity1);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'SEVERITY2 = :severity2'.
	 */
	@Transactional
	public List<IcmpThresholds> findWhereSeverity2Equals(String severity2) throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " WHERE SEVERITY2 = ? ORDER BY SEVERITY2", this, severity2);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'FILTERFLAG1 = :filterflag1'.
	 */
	@Transactional
	public List<IcmpThresholds> findWhereFilterflag1Equals(long filterflag1) throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " WHERE FILTERFLAG1 = ? ORDER BY FILTERFLAG1", this, filterflag1);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'FILTERFLAG2 = :filterflag2'.
	 */
	@Transactional
	public List<IcmpThresholds> findWhereFilterflag2Equals(long filterflag2) throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " WHERE FILTERFLAG2 = ? ORDER BY FILTERFLAG2", this, filterflag2);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'VARLIST = :varlist'.
	 */
	@Transactional
	public List<IcmpThresholds> findWhereVarlistEquals(String varlist) throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " WHERE VARLIST = ? ORDER BY VARLIST", this, varlist);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria
	 * 'SUMMARYCN = :summarycn'.
	 */
	@Transactional
	public List<IcmpThresholds> findWhereSummarycnEquals(String summarycn) throws IcmpThresholdsDaoException {
		try {
			return jdbcTemplate.query("SELECT IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN FROM " + getTableName()
			    + " WHERE SUMMARYCN = ? ORDER BY SUMMARYCN", this, summarycn);
		} catch (Exception e) {
			throw new IcmpThresholdsDaoException("Query failed", e);
		}

	}

	/**
	 * Returns the rows from the ICMP_THRESHOLDS table that matches the specified
	 * primary-key value.
	 */
	public IcmpThresholds findByPrimaryKey(IcmpThresholdsPk pk) throws IcmpThresholdsDaoException {
		return findByPrimaryKey(pk.getIpaddress());
	}

	public void delete(IcmpThresholds dto) {
		jdbcTemplate.update("Delete from " + getTableName() + " where ipaddress=?  ", dto.getIpaddress());
	}

	public void update(IcmpThresholds dto) {
		jdbcTemplate.update("Update " + getTableName()
		    + " set   BTIME=?, ETIME=?, THRESHOLD=?, COMPARETYPE=?, SEVERITY1=?, SEVERITY2=?,  FILTERFLAG1=?,FILTERFLAG2=?,VARLIST=?,SUMMARYCN=?  " + " where ipaddress=? ",
		    dto.getBtime(), dto.getEtime(), dto.getThreshold(), dto.getComparetype(), dto.getSeverity1(), dto.getSeverity2(), dto.getFilterflag1(), dto.getFilterflag2(),
		    dto.getVarlist(), dto.getSummarycn(), dto.getIpaddress());

	}

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return IcmpThresholdsPk
	 */
	@Transactional
	public int insertEffect() {
		int ret = -1;
		String s1 = "'可Ping通|Ping不通'";
		String s2 = "'丢包率正常|丢包率超阀值'";
		String s3 = "'响应时间未超阀值|响应时间超阀值'";
		String sqldev = "select devip ipaddress, btime, etime, "
		    + " '0'||(case when value_1_low is not null and trim(length(value_1_low))<>0 then '|'||value_1 end)||(case  when value_2_low is not null and trim(length(value_2_low))<>0 then '|'||value_2 end) threshold,"
		    + " 'value '||comparetype||' threshold' comparetype, "
		    + " v1l_severity_1||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||severity_1 end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||severity_2 end) severity1, "
		    + " v1l_severity_A||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||severity_A end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||severity_B end) severity2, "
		    + " filter_A filterflag1,filter_B filterflag2, "
		    + " 'var0'||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||value_1_low end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||value_2_low end) varlist, "
		    + " " + s1 + "||(case when value_1_low is not null and trim(length(value_1_low))<>0  and value_1_low='var1' then '|'||" + s2
		    + " when value_1_low is not null and trim(length(value_1_low))<>0  and value_1_low='var2' then '|'||" + s3 + " end)"
		    + "||(case when value_2_low is not null and trim(length(value_2_low))<>0  and value_2_low='var1' then '|'||" + s2
		    + " when value_2_low is not null and trim(length(value_2_low))<>0  and value_2_low='var2' then '|'||" + s3 + " end) summaryCN " + " from v_icmp_dev_thresholds";
		String sqlport = "select ifip ipaddress, btime, etime, "
		    + " '0'||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||value_1 end)||(case  when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||value_2 end) threshold,"
		    + " 'value '||comparetype||' threshold' comparetype, "
		    + " v1l_severity_1||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||severity_1 end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||severity_2 end) severity1, "
		    + " v1l_severity_A||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||severity_A end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||severity_B end) severity2, "
		    + " filter_A filterflag1,filter_B filterflag2, "
		    + " 'var0'||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||value_1_low end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||value_2_low end) varlist, "
		    + " " + s1 + "||(case when value_1_low is not null and trim(length(value_1_low))<>0  and value_1_low='var1' then '|'||" + s2
		    + " when value_1_low is not null and trim(length(value_1_low))<>0  and value_1_low='var2' then '|'||" + s3 + " end)"
		    + "||(case when value_2_low is not null and trim(length(value_2_low))<>0  and value_2_low='var1' then '|'||" + s2
		    + " when value_2_low is not null and trim(length(value_2_low))<>0  and value_2_low='var2' then '|'||" + s3 + " end) summaryCN "
		    + " from v_icmp_port_thresholds where (ifip is not null or trim(ifip) <>'')" + " and ifip not in ( select devip from v_icmp_dev_thresholds )";
		/*
		 * String sqlpdm = "select oidname ipaddress, btime, etime, " +
		 * " '0'||(case when value_1_low is not null then '|'||value_1 end)||(case  when value_2_low is not null then '|'||value_2 end) threshold,"
		 * +
		 * " comparetype, severity_1 severity1,severity_2 severity2, filter_A filterflag1,filter_B filterflag2, "
		 * +
		 * " 'var0'||(case when value_1_low is not null then '|'||value_1_low end)||(case when value_2_low is not null then '|'||value_2_low end) varlist, "
		 * + " "+s1+
		 * "||(case when value_1_low is not null and value_1_low='var1' then '|'||"
		 * +s2
		 * +" when value_1_low is not null and value_1_low='var2' then '|'||"+s3+" end)"
		 * +
		 * "||(case when value_2_low is not null and value_2_low='var1' then '|'||"
		 * +s2+" when value_2_low is not null and value_2_low='var2' then '|'||"+s2+
		 * " end) summaryCN " +
		 * " from v_icmp_pdm_thresholds where oidname is not null or trim(oidname) <>''"
		 * ;
		 */
		// System.out.print(sqldev);
		// System.out.print(sqlport);
		try {
			ret = jdbcTemplate.update("INSERT INTO " + getTableName() + "( " + sqldev + " )");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		try {
			ret += jdbcTemplate.update("INSERT INTO " + getTableName() + "( " + sqlport + " )");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		/* need to confirm if icmp ping is needed ??? */
		/*
		 * try { ret += jdbcTemplate.update("INSERT INTO " + getTableName()
		 * +"("+sqlpdm+")" ,this); } catch (DataAccessException e) {
		 * e.printStackTrace(); }
		 */
		return ret;
	}

	public int deleteAll() {
		int ret = -1;
		ret = jdbcTemplate.update("DELETE FROM " + getTableName());
		return ret;
	}

}
