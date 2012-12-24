package com.ibm.ncs.model.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.ncs.model.dao.TTakeEffectHistoryDao;
import com.ibm.ncs.model.dto.TTakeEffectHistory;
import com.ibm.ncs.model.dto.TTakeEffectHistoryPk;
import com.ibm.ncs.model.exceptions.TTakeEffectHistoryDaoException;

public class TTakeEffectHistoryDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TTakeEffectHistory>, TTakeEffectHistoryDao {
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
	 * @return TTakeEffectHistoryPk
	 */
	@Transactional
	public TTakeEffectHistoryPk insert(TTakeEffectHistory dto) {
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( TEID, USID, PPIID, SERVER_ID, GENERED_TIME, SRC_TYPE_FILE, ICMP_XML_FILE, SNMP_XML_FILE, ICMP_THRESHOLD, SNMP_THRESHOLD, EFFECT_TIME, EFFECT_STATUS ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )", 
				dto.getTeId(), dto.getUsid(), dto.getPpiid(), dto.getServerId(), dto.getGeneredTime(), dto.getSrcTypeFile(), dto.getIcmpXMLFile(), dto.getSnmpXMLFile(), dto.getIcmpThreshold(), dto.getSnmpThreshold(), dto.getEffectTime(), dto.getEffectStatus()
				);
		return dto.createPk();
	}

	/**
	 * Updates a single row in the T_MANUFACTURER_INFO_INIT table.
	 */
	@Transactional
	public void update(TTakeEffectHistoryPk pk, TTakeEffectHistory dto) throws TTakeEffectHistoryDaoException {
		jdbcTemplate.update("UPDATE " + getTableName() + " SET USID=?, PPIID=?, SERVER_ID=?, GENERED_TIME=?, SRC_TYPE_FILE=?, ICMP_XML_FILE=?, SNMP_XML_FILE=?, ICMP_THRESHOLD=?, SNMP_THRESHOLD=?, EFFECT_TIME=?, EFFECT_STATUS=?  WHERE TEID = ?", dto.getUsid(), dto.getPpiid(), dto.getServerId(), dto.getGeneredTime(), dto.getSrcTypeFile(), dto.getIcmpXMLFile(), dto.getSnmpXMLFile(), dto.getIcmpThreshold(), dto.getSnmpThreshold(), dto.getEffectTime(), dto.getEffectStatus(), dto.getTeId());
	}

	/**
	 * Deletes a single row in the T_MANUFACTURER_INFO_INIT table.
	 */
	@Transactional
	public void delete(TTakeEffectHistoryPk pk) throws TTakeEffectHistoryDaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE TEID = ?", pk.getTeId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TTakeEffectHistory
	 */
	public TTakeEffectHistory mapRow(ResultSet rs, int row) throws SQLException {
		TTakeEffectHistory dto = new TTakeEffectHistory();
		dto.setTeId(rs.getLong(1));
		dto.setUsid(rs.getLong(2));
		dto.setPpiid(rs.getLong(3));
		dto.setServerId(rs.getLong(4));
		dto.setGeneredTime(rs.getTimestamp(5));
		dto.setSrcTypeFile(rs.getString(6));
		dto.setIcmpXMLFile(rs.getString(7));
		dto.setSnmpXMLFile(rs.getString(8));
		dto.setIcmpThreshold(rs.getString(9));
		dto.setSnmpThreshold(rs.getString(10));
		dto.setEffectTime(rs.getTimestamp(11));
		dto.setEffectStatus(rs.getString(12));
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "T_TAKE_EFFECT_HISTORY";
	}

	/**
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the
	 * criteria ''.
	 */
	@Transactional
	public List<TTakeEffectHistory> findAll() throws TTakeEffectHistoryDaoException {
		try {
			return jdbcTemplate.query("SELECT TEID, USID, PPIID, SERVER_ID, GENERED_TIME, SRC_TYPE_FILE, ICMP_XML_FILE, SNMP_XML_FILE, ICMP_THRESHOLD, SNMP_THRESHOLD, EFFECT_TIME, EFFECT_STATUS FROM " + getTableName() + " ORDER BY SERVER_ID, GENERED_TIME", this);
		} catch (Exception e) {
			throw new TTakeEffectHistoryDaoException("Query failed", e);
		}

	}

	public TTakeEffectHistory findByTeid(long teid) throws TTakeEffectHistoryDaoException {
		try {
			List<TTakeEffectHistory> list = jdbcTemplate.query("SELECT TEID, USID, PPIID, SERVER_ID, GENERED_TIME, SRC_TYPE_FILE, ICMP_XML_FILE, SNMP_XML_FILE, ICMP_THRESHOLD, SNMP_THRESHOLD, EFFECT_TIME, EFFECT_STATUS FROM " + getTableName() + " WHERE TEID = ?", this, teid);
			return list.size() == 0 ? null : list.get(0);
		} catch (Exception e) {
			throw new TTakeEffectHistoryDaoException("Query failed", e);
		}
  }

	public TTakeEffectHistory findLastItemByServerIdAndReleaseInfo(long serverId, long ppiid) throws TTakeEffectHistoryDaoException {
		try {
			List<TTakeEffectHistory> list = jdbcTemplate.query("select * from (SELECT TEID, USID, PPIID, SERVER_ID, GENERED_TIME, SRC_TYPE_FILE, ICMP_XML_FILE, SNMP_XML_FILE, ICMP_THRESHOLD, SNMP_THRESHOLD, EFFECT_TIME, EFFECT_STATUS FROM " + getTableName() + " WHERE server_id = ? and ppiid = ? order by GENERED_TIME desc) where rownum=1", this, serverId, ppiid);
			return list.size() == 0 ? null : list.get(0);
		} catch (Exception e) {
			throw new TTakeEffectHistoryDaoException("Query failed", e);
		}
  }

	public List<TTakeEffectHistory> findBranchHistoryByPpiid(long parseLong) throws TTakeEffectHistoryDaoException {
		try {
			List<TTakeEffectHistory> list = jdbcTemplate.query("select * from (SELECT TEID, USID, PPIID, SERVER_ID, GENERED_TIME, SRC_TYPE_FILE, ICMP_XML_FILE, SNMP_XML_FILE, ICMP_THRESHOLD, SNMP_THRESHOLD, EFFECT_TIME, EFFECT_STATUS FROM T_SERVER_NODE sn left join T_TAKE_EFFECT_HISTORY teh on sn.server WHERE ppiid = ? order by GENERED_TIME desc) where rownum=1", this, serverId, ppiid);
			return list;
		} catch (Exception e) {
			throw new TTakeEffectHistoryDaoException("Query failed", e);
		}
  }

}
