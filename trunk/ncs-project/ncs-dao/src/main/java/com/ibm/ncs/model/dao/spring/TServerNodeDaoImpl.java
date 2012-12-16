package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TServerNodeDao;
import com.ibm.ncs.model.dto.TServerNode;
import com.ibm.ncs.model.dto.TServerNodePk;
import com.ibm.ncs.model.exceptions.TServerNodeDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TServerNodeDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TServerNode>, TServerNodeDao {
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
	 * @return TServerNodePk
	 */
	@Transactional
	public TServerNodePk insert(TServerNode dto) {
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( SERVER_ID,SERVER_CODE,NODE_TYPE,SERVER_NAME,DESCRIPTION,SERVICE_ENDPOINT ) VALUES ( ?, ?, ?, ?, ?, ? )", dto.getServerID(), dto.getServerCode(), dto.getNodeType(), dto.getServerName(), dto.getDescription(), dto.getServiceEndpointInfo());
		return dto.createPk();
	}

	/**
	 * Updates a single row in the T_MANUFACTURER_INFO_INIT table.
	 */
	@Transactional
	public void update(TServerNodePk pk, TServerNode dto) throws TServerNodeDaoException {
		jdbcTemplate.update("UPDATE " + getTableName() + " SET SERVER_CODE = ?, NODE_TYPE = ?, SERVER_NAME=?, DESCRIPTION = ?, SERVICE_ENDPOINT=?  WHERE SERVER_ID = ?", dto.getServerCode(), dto.getNodeType(), dto.getServerName(), dto.getDescription(), dto.getServiceEndpointInfo(), pk.getServerID());
	}

	/**
	 * Deletes a single row in the T_MANUFACTURER_INFO_INIT table.
	 */
	@Transactional
	public void delete(TServerNodePk pk) throws TServerNodeDaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE SERVER_ID = ?", pk.getServerID());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TServerNode
	 */
	public TServerNode mapRow(ResultSet rs, int row) throws SQLException {
		TServerNode dto = new TServerNode();
		dto.setServerID(rs.getLong(1));
		dto.setServerCode(rs.getString(2));
		dto.setNodeType(rs.getString(3));
		dto.setServerName(rs.getString(4));
		dto.setDescription(rs.getString(5));
		dto.setServiceEndpointInfo(rs.getString(6));
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName() {
		return "T_SERVER_NODE";
	}

	/**
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the
	 * criteria ''.
	 */
	@Transactional
	public List<TServerNode> findAll() throws TServerNodeDaoException {
		try {
			return jdbcTemplate.query("SELECT SERVER_ID,SERVER_CODE,NODE_TYPE,SERVER_NAME,DESCRIPTION,SERVICE_ENDPOINT FROM " + getTableName() + " ORDER BY NODE_TYPE, UPPER(SERVER_CODE)", this);
		} catch (Exception e) {
			throw new TServerNodeDaoException("Query failed", e);
		}

	}

	public TServerNode findByServerId(long serverId) throws TServerNodeDaoException {
		try {
			List<TServerNode> list = jdbcTemplate.query("SELECT SERVER_ID,SERVER_CODE,NODE_TYPE,SERVER_NAME,DESCRIPTION,SERVICE_ENDPOINT FROM " + getTableName() + " WHERE SERVER_ID = ?", this, serverId);
			return list.size() == 0 ? null : list.get(0);
		} catch (Exception e) {
			throw new TServerNodeDaoException("Query failed", e);
		}
	}

	public TServerNode findByServerCode(long serverCode) throws TServerNodeDaoException {
		try {
			List<TServerNode> list = jdbcTemplate.query("SELECT SERVER_ID,SERVER_CODE,NODE_TYPE,SERVER_NAME,DESCRIPTION,SERVICE_ENDPOINT FROM " + getTableName() + " WHERE SERVER_CODE = ?", this, serverCode);
			return list.size() == 0 ? null : list.get(0);
		} catch (Exception e) {
			throw new TServerNodeDaoException("Query failed", e);
		}
	}

}
