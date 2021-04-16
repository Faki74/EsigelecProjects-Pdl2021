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
public class ProfilDAO extends ConnectionDAO {
	/**
	 * Constructor
	 */
	public ProfilDAO() {
		super();
	}
	public int add(Profil profil) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		//Connection à la base de données
		try {
			con=DriverManager.getConnection(URL, LOGIN, PASS);
			ps=con.prepareStatement("insert into profil_pfl values(?,?,?,?)");
			ps.setString(1, profil.getPflId());
			ps.setString(2, profil.getPflDesc());
			ps.setString(3, profil.getPflPlace().getPlcId());
			ps.setString(4, profil.getPflHoraire().getHrId());
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
	public int update(Profil oldProfil,Profil newProfil) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("UPDATE profil_pfl set pfl_id = ?,pfl_description = ?,pfl_plc_id = ?,pfl_hr_id =? WHERE pfl_id = ?,pfl_plc_id = ?,pfl_hr_id = ?");
			ps.setString(1, newProfil.getPflId());
			ps.setString(2, newProfil.getPflDesc());
			ps.setString(3, newProfil.getPflPlace().getPlcId());
			ps.setString(4, newProfil.getPflHoraire().getHrId());
			ps.setString(5, oldProfil.getPflId());
			ps.setString(6, oldProfil.getPflPlace().getPlcId());
			ps.setString(7, oldProfil.getPflHoraire().getHrId());
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
	public int delete(Profil profil) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;

		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("DELETE FROM profil_pfl WHERE pfl_id = ?,pfl_plc_id = ?,pfl_hr_id = ?");
			ps.setString(1, profil.getPflId());
			ps.setString(2, profil.getPflPlace().getPlcId());
			ps.setString(3, profil.getPflHoraire().getHrId());
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
	/**
	 * Get results from table PROFIL
	 * @param id
	 * @return
	 */
	public ResultSet getRSet(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Profil returnValue = null;
		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM profil_pfl WHERE lower(concat(pfl_id,concat(pfl_plc_id,pfl_hr_id))) like ?");
			ps.setString(1, new String("%"+id+"%").toLowerCase());
			//Exécution de la requête
			rs = ps.executeQuery();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return rs;
	}
	/*
	 * Attention JUSTE POUR LES TESTS, A NORMALISER
	 */
	/**
	 * Get a tuple from a given id 
	 * @param id
	 * @return
	 */
	public ArrayList<Profil> get(String id) {
		ResultSet rs = getRSet(id);
		ArrayList<Profil> returnValue = null;
		try {
			returnValue = new ArrayList<Profil>();
			while (rs.next()) {
				returnValue.add(new Profil(rs.getString("pfl_id"),rs.getString("pfl_description"),
						new PlaceDAO().get(rs.getString("pfl_plc_id")),
						new HoraireDAO().get(rs.getString("pfl_hr_id"))));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}
	/**
	 * Permet de recuperer tous les profils dans la table PROFIL
	 * 
	 * @return une 
	 */
	public TableModel searchProfil(String searchKey) {
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
