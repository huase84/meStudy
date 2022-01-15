package com.meStudy.core.toolkit;

import java.sql.*;
import java.util.*;

/**
 * @Author wultn
 * @Date 16:14 2020/2/25
 * @Param
 * @return
 **/
public class SimpleJDBC {
	private Connection conn;
	private static final boolean AUTO_COMMIT = true;

	public SimpleJDBC(String driverName, String url, String userName, String password) {
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public int execute(String sql, Object... params) throws SQLException {
		Connection conn = getConnection(false);
		PreparedStatement stmt = null;
		int result = -1;
		try {
			stmt = createPreparedStatement(conn, sql, params);
			result = stmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			free(stmt);
			free(conn);
		}
		return result;
	}
	public int execute(String sql) throws SQLException {
		return execute(sql, new Object[] {});
	}
	public int executeBatch(String sql, List<Object[]> params) throws SQLException {
		Connection conn = getConnection(false);
		int result = 0;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				Object[] param = params.get(i);
				for (int j = 0; j < param.length; j++)
					stmt.setObject(j + 1, param[j]);
				stmt.addBatch();
				if (i % 1000 == 0) {
					stmt.executeBatch();
					stmt.clearBatch();
				}
			}
			stmt.executeBatch();
			conn.commit();
			result = params.size();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			free(stmt);
			free(conn);
		}
		return result;
	}
	public int executeBatch(String sql) throws SQLException {
		return executeBatch(sql, new ArrayList<Object[]>());
	}
	public ResultSet queryForResultSet(String sql, Object[] params) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = createPreparedStatement(conn, sql, params);
			return stmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(stmt);
			free(conn);
		}
		return null;
	}
	public ResultSet queryForResultSet(String sql) throws SQLException {
		return queryForResultSet(sql, new Object[] {});
	}
	public List<Map<String, Object>> queryForMap(String sql, Object... params) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = createPreparedStatement(conn, sql, params);
			rs = createResultSet(stmt);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = null;
			ResultSetMetaData rsd = rs.getMetaData();
			int columnCount = rsd.getColumnCount();
			while (rs.next()) {
				map = new HashMap<String, Object>(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					map.put(rsd.getColumnName(i), rs.getObject(i));
				}
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(rs);
			free(stmt);
			free(conn);
		}
		return null;
	}
	public List<LinkedHashMap<String, Object>> queryForLinkedHashMap(String sql) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = createPreparedStatement(conn, sql, new Object[] {});
			rs = createResultSet(stmt);
			List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
			Map<String, Object> map = null;
			ResultSetMetaData rsd = rs.getMetaData();
			int columnCount = rsd.getColumnCount();
			while (rs.next()) {
				map = new LinkedHashMap<String, Object>(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					map.put(rsd.getColumnName(i), rs.getObject(i));
				}
				list.add((LinkedHashMap<String, Object>) map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(rs);
			free(stmt);
			free(conn);
		}
		return null;
	}
	public List<Map<String, Object>> queryForMap(String sql) throws SQLException {
		return queryForMap(sql, new Object[] {});
	}
	public int queryForInt(String sql, Object[] params) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = createPreparedStatement(conn, sql, params);
			rs = createResultSet(stmt);
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(rs);
			free(stmt);
			free(conn);
		}
		return 0;
	}
	public int queryForInt(String sql) throws SQLException {
		return queryForInt(sql, new Object[] {});
	}
	public void free(Connection x) {
		if (x != null)
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public void free(Statement x) {
		if (x != null)
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public void free(PreparedStatement x) {
		if (x != null)
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public void free(ResultSet x) {
		if (x != null)
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public PreparedStatement createPreparedStatement(Connection conn, String sql, Object[] params) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql);
		for (int i = 0; i < params.length; i++)
			stmt.setObject(i + 1, params[i]);
		return stmt;
	}
	public ResultSet createResultSet(PreparedStatement stmt) throws SQLException {
		return stmt.executeQuery();
	}
	public Connection getConnection() {
		return getConnection(AUTO_COMMIT);
	}
	public Connection getConnection(boolean autoCommit) {
		try {
			Connection conn = this.conn;
			if (!autoCommit)
				conn.setAutoCommit(autoCommit);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
