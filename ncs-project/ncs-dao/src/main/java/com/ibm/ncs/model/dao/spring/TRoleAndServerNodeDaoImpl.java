package com.ibm.ncs.model.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ibm.ncs.model.dao.TRoleAndServerNodeDao;
import com.ibm.ncs.model.dto.TRoleAndServerNode;
import com.ibm.ncs.model.dto.TServerNode;
import com.ibm.ncs.model.exceptions.TUserDaoException;

public class TRoleAndServerNodeDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TRoleAndServerNode>, TRoleAndServerNodeDao {
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
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TUser
	 */
	public TRoleAndServerNode mapRow(ResultSet rs, int row) throws SQLException {
		TRoleAndServerNode dto = new TRoleAndServerNode();
		dto.setRoleName(rs.getString(1));
		TServerNode tsd = new TServerNode();
		tsd.setServerID(rs.getLong(2));
		tsd.setServerCode(rs.getString(3));
		tsd.setNodeType(rs.getString(4));
		tsd.setServerName(rs.getString(5));
		tsd.setDescription(rs.getString(6));
		tsd.setServiceEndpointInfo(rs.getString(7));
		dto.setTargetServerNode(tsd );
		return dto;
	}

	public List<TRoleAndServerNode> findByUsername(String username) throws TUserDaoException {
		try {
			List<TRoleAndServerNode> list = jdbcTemplate.query("select " + 
					" r.role_name, " + 
					" sn.server_id, " + 
					" sn.server_code, " + 
					" sn.node_type, " + 
					" sn.server_name, " + 
					" sn.description, " + 
					" sn.service_endpoint " + 
					"from " + 
					" t_user_role_map urm inner join t_role r on r.role_id=urm.role_id " + 
					"                         inner join t_user u on u.usid=urm.usid " + 
					"                         left join t_role_managed_node rmn on urm.role_id=rmn.role_id " + 
					"                         left join t_server_node sn on sn.server_id=rmn.server_id " + 
					"where " + 
					" u.uname=? ", this, username);
			return list;
		} catch (Exception e) {
			throw new TUserDaoException("Query failed", e);
		}
	}
}
