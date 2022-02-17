package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * retorna um vendedor por Id
	 */
	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			// iniciar o prepareStatement / fazer uma consulta SQL
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department " 
					+ "ON seller.DepartmentId = department.Id " 
					+ "WHERE seller.Id = ?");
			
			// configurar o ? da busca no banco de dados
			st.setInt(1, id);
			rs = st.executeQuery();
			
			// testar se veio um resultado (rs.next)
			if (rs.next()) {
				// instanciar os objetos e setar os dados
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			// fechar os recursos
			DB.closeStatement(st);
			DB.closeResultSet(rs);
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
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			// iniciar o prepareStatement / fazer uma consulta SQL
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department " 
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			// configurar o ? da busca no banco de dados
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			
			// testar se veio um resultado (rs.next)
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				// testar se um department existe ou não existe
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				// instanciar o objeto e setar os dados
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			// fechar os recursos
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
