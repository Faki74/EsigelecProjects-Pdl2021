package dao;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.*;
import gui.AdminFrame;
public class UserDAO extends ConnectionDAO {
	/**
	 * Constructor
	 */
	public UserDAO() {
		super();
	}
	public int add(User user) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		//Connection à la base de données
		try {
			con=DriverManager.getConnection(URL, LOGIN, PASS);
			ps=con.prepareStatement("insert into user_usr values(idgenerate(?,'user_usr',1),?,?,To_char(To_DATE(?,'DD/MM/YYYY'),'DD/MM/YYYY'),?)");
			ps.setString(1, user.getUsrPrenom()+"."+user.getUsrNom());
			ps.setString(2, user.getUsrNom());
			ps.setString(3, user.getUsrPrenom());
			ps.setString(4, user.getUsrBirthday());
			ps.setString(5, user.getUsrFonction());
			returnValue=ps.executeUpdate();
		} catch (Exception e) {
			if (e.getMessage().contains("ORA-00001"))
				System.out.println("Cet identifiant de fournisseur existe déjà. Ajout impossible !");
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
	public int update(User user) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("UPDATE user_usr set usr_nom = ?, usr_prenom = ?, usr_birthday = ?, usr_fonction = ? WHERE usr_id = ?");
			ps.setString(1, user.getUsrNom());
			ps.setString(2, user.getUsrPrenom());
			ps.setString(3, user.getUsrBirthday());
			ps.setString(4, user.getUsrFonction());
			ps.setString(5, user.getUsrId());

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
	public int delete(User user) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("DELETE FROM user_usr WHERE usr_id = ? ");
			ps.setString(1, user.getUsrId());

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
	public User get(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User returnValue = null;

		// connexion a la base de donnees
		try {

			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM user_usr WHERE usr_id = ?");
			ps.setString(1, id);
			
			//Exécution de la requête
			rs = ps.executeQuery();
			if (rs.next()) {
				returnValue = new User(rs.getString("usr_id"),
									       rs.getString("usr_nom"),
									       rs.getString("usr_prenom"),
									       rs.getString("usr_birthday"),
									       rs.getString("usr_fonction"));
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
	public TableModel searchUser(String searchKey) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TableModel returnValue = new DefaultTableModel();
		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			//JOptionPane.showMessageDialog(null, "Connection Successful!");
			ps = con.prepareStatement(" SELECT * FROM user_usr where lower(concat(concat(usr_id,usr_nom),usr_prenom)) like ? ");
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
