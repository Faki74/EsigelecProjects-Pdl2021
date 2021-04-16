package dao;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.*;

public class PlaceDAO extends ConnectionDAO{
	/**
	 * Constructor
	 */
	public PlaceDAO() {
		super();
	}
	public int add(Place place) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		//Connection à la base de données
		try {
			con=DriverManager.getConnection(URL, LOGIN, PASS);
			ps=con.prepareStatement("insert into place_plc values(idgenerate(?,'Place_plc',1),?,?,?,?)");
			ps.setString(1, place.getPlcLocation());
			ps.setInt(2, place.getPlcNbAcces());
			ps.setString(3, place.getPlcHoraire().getHrId());
			ps.setInt(4, place.getPlcStatus());
			returnValue=ps.executeUpdate();
		} catch (Exception e) {
			if (e.getMessage().contains("ORA-00001"))
				System.out.println("Cet identifiant de lieu existe déjà. Ajout impossible !");
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
	public int update(Place place) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("UPDATE place_plc set plc_location = ?, plc_nbacces = ?, plc_hr_id=?, plc_status = ? WHERE plc_id = ?");
			ps.setString(1, place.getPlcLocation());
			ps.setInt(2, place.getPlcNbAcces());
			ps.setString(3, place.getPlcHoraire().getHrId());
			ps.setInt(4, place.getPlcStatus());
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
	public int delete(Place place) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("DELETE FROM place_plc WHERE plc_id = ? ");
			ps.setString(1, place.getPlcId());

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
	 * 
	 * @param id
	 * @return
	 */
	public Place get(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Place returnValue = null;

		// connexion a la base de donnees
		try {

			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM place_plc WHERE plc_id = ?");
			ps.setString(1, id);

			//Exécution de la requête
			rs = ps.executeQuery();
			if (rs.next()) {
				returnValue = new Place(rs.getString("plc_id"),
						rs.getString("plc_location"),
						rs.getInt("plc_nbacces"),
						new HoraireDAO().get(rs.getString("plc_hr_id")),
						rs.getInt("plc_status"));
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
	 * 
	 * @param id
	 * @return
	 */
	public int getAcces(Place place) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnValue = 0;

		// connexion a la base de donnees
		try {

			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT acces_acs.* FROM place_plc INNER JOIN acces_acs on plc_id=acs_plc_id WHERE plc_id = ?");
			ps.setString(1, place.getPlcId());

			//Exécution de la requête
			rs = ps.executeQuery();
			while(rs.next()) {
				place.setPlcAcces(new Acces(rs.getString("acs_id"),rs.getString("acs_desc"),rs.getString("acs_plc_id"),
						new TypeAccesDAO().get(rs.getString("acs_acstype_id"))));
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
	 * Permet de recuperer tous les lieux de la table lieu
	 * 
	 * @return une 
	 */
	public TableModel searchPlace(String searchKey) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TableModel returnValue = new DefaultTableModel();
		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			//JOptionPane.showMessageDialog(null, "Connection Successful!");
			ps = con.prepareStatement(" SELECT * FROM place_plc where lower(concat(plc_id,plc_location)) like ? ");
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

