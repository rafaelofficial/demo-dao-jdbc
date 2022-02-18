package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Department obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			// preparar a consulta sql
			st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
			
			// configurar placeholder
			st.setInt(1, id);
			
			// executa query
			rs = st.executeQuery();
			
			// testar se veio um resultado (rs.next)
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
	
	// instaciar objeto Seller
		private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
			Seller obj = new Seller();
			obj.setId(rs.getInt("Id"));
			obj.setName(rs.getString("Name"));
			obj.setEmail(rs.getString("Email"));
			obj.setBirthDate(rs.getDate("BirthDate"));
			obj.setBaseSalary(rs.getDouble("BaseSalary"));
			obj.setDepartment(dep);
			return obj;
		}

		// instanciar objeto Department
		private Department instantiateDepartment(ResultSet rs) throws SQLException {
			Department dep = new Department();
			dep.setId(rs.getInt("Id"));
			dep.setName(rs.getString("Name"));
			return dep;
		}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
