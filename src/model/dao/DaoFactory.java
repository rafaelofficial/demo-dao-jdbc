package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	/**
	 * @return instancia um novo SellerDao
	 */
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC();
	}
}
