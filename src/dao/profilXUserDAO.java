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
public class profilXUserDAO extends ConnectionDAO {
	/**
	 * Constructor
	 */
	public profilXUserDAO() {
		super();
	}
	public int add(profilXuser pXu) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		//Connection à la base de données
		try {
			con=DriverManager.getConnection(URL, LOGIN, PASS);
			ps=con.prepareStatement("insert into userprofil_usrpfl values(usrpfl_seq.nextval,?,?)");
			ps.setString(1, pXu.getProfil().getPflId());
			ps.setString(2, pXu.getUser().getUsrId());
			returnValue=ps.executeUpdate();
		} catch (Exception e) {
			if (e.getMessage().contains("ORA-00001"))
				System.out.println("Cet identifiant de fournisseur existe déjà. Ajout impossible !");
			else
				e.printStackTrace();
		}
		return returnValue;
	}
	public int delete(profilXuser pXu) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;

		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("DELETE FROM userprofil_usrpfl WHERE usrpfl_id = ?");
			ps.setInt(1, pXu.getUsrpflId());

			// Execution de la requete
			returnValue = ps.executeUpdate();

		} catch (Exception e) {
			if (e.getMessage().contains("ORA-02292"))
				System.out.println("Suppression Impossibe. Supprimer d'abord tuples associés");
			else
				e.printStackTrace();
		} 
		return returnValue;
	}
	public ResultSet getRSet(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Card returnValue = null;

		// connexion a la base de donnees
		try {

			con = DriverManager.getConnection(URL, LOGIN, PASS);
			if(id.matches("^[0-9]+$")) {
				ps = con.prepareStatement("SELECT * FROM userprofil_usrpfl WHERE usrpfl_id = ?");
				ps.setInt(1, Integer.parseInt(id) );
			}else {
				ps = con.prepareStatement("SELECT * FROM userprofil_usrpfl WHERE lower(concat(usrpfl_usr_id,usrpfl_pfl_id)) like ?");
				ps.setString(1, new String("%"+id+"%").toLowerCase() );
			}

			//Exécution de la requête
			rs = ps.executeQuery();
		} catch (Exception ee) {
			ee.printStackTrace();
		} 
		/*finally {
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
		}*/
		return rs;
	}
	public ArrayList<profilXuser> get(String id) {
		//Must send an integer ID
		ResultSet rs = getRSet(id);
		ArrayList<profilXuser> returnValue = new ArrayList<profilXuser>();
		try {
			while(rs.next()) {
				returnValue.add(new profilXuser(rs.getInt("usrpfl_id"),
						new ProfilDAO().get(rs.getString("usrpfl_pfl_id")),
						new UserDAO().get(rs.getString("usrpfl_usr_id"))));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		// fermeture du ResultSet, du PreparedStatement et de la Connexion
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception ignore) {
		}
	}
		return returnValue;
	}

	public ArrayList<String> getNames(String searchKey){
		//indiquer "" pour tout afficher
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> returnValue = new ArrayList<String>();
		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT DISTINCT pfl_id FROM ((profil_pfl INNER JOIN userprofil_usrpfl on pfl_id=usrpfl_pfl_id)"
					+ " INNER JOIN user_usr on usrpfl_usr_id=usr_id) WHERE usrpfl_usr_id like ?");
			ps.setString(1, "%"+searchKey+"%");
			// on execute la requete
			rs = ps.executeQuery();
			// on parcourt les lignes du resultat
			while (rs.next()) {
				returnValue.add(rs.getString("pfl_id"));
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		finally {
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
	public TableModel searchUserProfil(String searchKey) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TableModel returnValue = new DefaultTableModel();
		// connexion a la base de donnees
		try {
			rs=getRSet(searchKey);
			returnValue = net.proteanit.sql.DbUtils.resultSetToTableModel(rs);

		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return returnValue;
	}
	public TableModel viewResults(String tableKey) {
		TableModel tbm= super.viewResults(tableKey);
		return tbm;
	}


}
