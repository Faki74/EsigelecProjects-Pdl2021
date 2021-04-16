package dao;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.*;
public class AccesDAO extends ConnectionDAO {
	/**
	 * Constructor
	 */
	public AccesDAO() {
		super();
	}
	public int add(Acces acces) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		//Connection à la base de données
		try {
			con=DriverManager.getConnection(URL, LOGIN, PASS);
			ps=con.prepareStatement("insert into acces_acs values(idgenerate(?,'acces_acs',1),?,?,?)");
			ps.setString(1, acces.getAcsDesc());
			ps.setString(2, acces.getAcsPlcId());
			ps.setInt(3, acces.getAcstype().getAcstypeId());
			returnValue=ps.executeUpdate();
		} catch (Exception e) {
			if (e.getMessage().contains("ORA-00001"))
				System.out.println("Cet identifiant d'utilisateur existe déjà. Ajout impossible !");
			else
				e.printStackTrace();
		}finally {
			// fermeture du preparedStatement et de la connexion
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ignore) {
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ignore) {
			}
		}
		return returnValue;
	}
	public int update(Acces acces) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("UPDATE acces_acs set acs_description = ?, acs_plc_id = ?, acs_acstype_id = ? WHERE acs_id = ?");
			ps.setString(1, acces.getAcsDesc());
			ps.setString(2, acces.getAcsPlcId());
			ps.setInt(3, acces.getAcstype().getAcstypeId());
			// Execution de la requete
			returnValue = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// fermeture du preparedStatement et de la connexion
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ignore) {
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ignore) {
			}
		}
		return returnValue;
	}
	public int delete(Acces acces) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("DELETE FROM acces_acs WHERE acs_id = ? ");
			ps.setString(1, acces.getAcsId());

			// Execution de la requete
			returnValue = ps.executeUpdate();

		} catch (Exception e) {
			if (e.getMessage().contains("ORA-02292"))
				System.out.println("Suppression Impossibe. Supprimer d'abord tuples associés");
			else
				e.printStackTrace();
		} finally {
			// fermeture du preparedStatement et de la connexion
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ignore) {
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ignore) {
			}
		}
		return returnValue;
	}
	public Acces get(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Acces returnValue = null;

		// connexion a la base de donnees
		try {

			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM acces_acs WHERE acs_id = ?");
			ps.setString(1, id);

			//Exécution de la requête
			rs = ps.executeQuery();
			if (rs.next()) {
				returnValue = new Acces(rs.getString("acs_id"),rs.getString("acs_desc"),rs.getString("acs_plc_id"),
						new TypeAccesDAO().get(rs.getString("acs_acstype_id")));
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du ResultSet, du PreparedStatement et de la Connexion
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ignore) {
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ignore) {
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ignore) {
			}
		}
		return returnValue;
	}

	/**
	 * Permet de recuperer tous les fournisseurs stockes dans la table fournisseur
	 * 
	 * @return une ArrayList de fournisseur
	 */
	public TableModel searchAcces(String searchKey) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TableModel returnValue = new DefaultTableModel();
		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			//JOptionPane.showMessageDialog(null, "Connection Successful!");
			ps = con.prepareStatement(" SELECT * FROM acces_acs where lower(concat(acs_id,acs_description)) like ? ");
			ps.setString(1, new String("%"+searchKey+"%").toLowerCase());
			// on execute la requete
			rs = ps.executeQuery();
			returnValue = net.proteanit.sql.DbUtils.resultSetToTableModel(rs);

		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du rs, du preparedStatement et de la connexion
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ignore) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
			}
		}
		return returnValue;
	}
	public TableModel viewResults(String tableKey) {
		TableModel tbm= super.viewResults(tableKey);
		return tbm;
	}
}

