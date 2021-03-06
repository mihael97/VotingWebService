package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.dao.sql.SQLDAO;

/**
 * Singleton razred koji zna koga treba vratiti kao pružatelja usluge pristupa
 * podsustavu za perzistenciju podataka. Uočite da, iako je odluka ovdje
 * hardkodirana, naziv razreda koji se stvara mogli smo dinamički pročitati iz
 * konfiguracijske datoteke i dinamički učitati -- time bismo implementacije
 * mogli mijenjati bez ikakvog ponovnog kompajliranja koda.
 * 
 * @author marcupic
 *
 */
public class DAOProvider {

	/**
	 * SQL dao
	 */
	private static DAO dao = new SQLDAO();

	static {
		// procitaj d=db.properties;
		// dao=(DAO)class.forname(d.getproperty("implClassName")).newInstance();
	}

	/**
	 * Dohvat primjerka.
	 * 
	 * @return objekt koji enkapsulira pristup sloju za perzistenciju podataka.
	 */
	public static DAO getDao() {
		return dao;
	}

}