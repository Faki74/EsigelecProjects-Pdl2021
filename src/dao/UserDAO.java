package dao;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.stream.Stream;

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
	/*
	 * CARD SECTION
	 */
	public int addCard(Card card) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		//Connection à la base de données
		try {
			con=DriverManager.getConnection(URL, LOGIN, PASS);
			ps=con.prepareStatement("insert into cardLeo_card values(idgenerate('Leo'||CARD_SEQ.nextval,'cardLeo_card',1),?,1)");
			ps.setString(1, card.getCardUsrId());
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
	public int updateCard(Card card) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("UPDATE cardLeo_card set card_usr_status = ? WHERE card_id = ?");
			ps.setInt(1, card.getCardStatus());
			ps.setString(2, card.getCardId());

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
	public int deleteCard(Card card) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;

		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("DELETE FROM cardleo_card WHERE card_id = ?");
			ps.setString(1, card.getCardId());

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
	/*
	 * USER SECTION
	 */
	public String addUser(User user) {
		Connection con = null;
		PreparedStatement ps = null;
		String returnValue=null;
		try{
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps=con.prepareStatement("begin dbms_output.enable(); end;");
			try {
				// First, we have to enable the DBMS_OUTPUT. Otherwise,
				// all calls to DBMS_OUTPUT made on our connection won't
				// have any effect.
				ps.executeUpdate();
				String sqry = "Declare\r\n"
						+ "usrId VARCHAR2(38) :=idgenerate('"+user.getUsrPrenom()+"."+user.getUsrNom()+"','user_usr',1);\r\n"
						+ "begin\r\n"
						+ "insert into user_usr values(usrId,'"+user.getUsrNom()+"','"+user.getUsrPrenom()+"',"
						+ "To_char(To_DATE('"+user.getUsrBirthday()+"','DD/MM/YYYY'),'DD/MM/YYYY'),'"+user.getUsrFonction()+"');\r\n"
						+ "dbms_output.put_line(usrId);\r\n"
						+ "end;\r\n";
				// Now, this is the actually interesting procedure call
				ps.executeUpdate(sqry);
				// After we're done with our call(s), we can proceed to
				// fetch the SERVEROUTPUT explicitly, using
				// DBMS_OUTPUT.GET_LINES
				try (CallableStatement call = con.prepareCall(
						"declare "
								+ "  num integer := 1000;"
								+ "begin "
								+ "  dbms_output.get_lines(?, num);"
								+ "end;"
						)) {
					call.registerOutParameter(1, Types.ARRAY,
							"DBMSOUTPUT_LINESARRAY");
					call.execute();

					Array array = null;
					try {
						array = call.getArray(1);
						//Stream.of((Object[]) array.getArray())
						//    .forEach(System.out::println);
						returnValue=((String[]) array.getArray())[0];
					}
					finally {
						if (array != null)
							array.free();
					}
				}
			}

			// Don't forget to disable DBMS_OUTPUT for the remaining use
			// of the connection.
			finally {
				ps.executeUpdate("begin dbms_output.disable(); end;");
			}
		}catch (Exception e) {
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
	public String updateUser(User user, User oldUser) {
		Connection con = null;
		PreparedStatement ps = null;
		String returnValue = null;
		try{
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps=con.prepareStatement("begin dbms_output.enable(); end;");
			try {
				// First, we have to enable the DBMS_OUTPUT. Otherwise,
				// all calls to DBMS_OUTPUT made on our connection won't
				// have any effect.
				ps.executeUpdate();
				String sqry = "DECLARE\r\n"
						+ "v_count NUMBER(5); usrId VARCHAR2(38);\r\n"
						+ "BEGIN\r\n"
						+ "SELECT count(*) into v_count from user_usr where lower(usr_id) LIKE '"+new String(user.getUsrPrenom()+"."+user.getUsrNom()).toLowerCase()+"%';\r\n"
						+ "usrId := '"+user.getUsrPrenom()+"."+user.getUsrNom()+"'||v_count||'@pdlesig';\r\n"
						+ "UPDATE user_usr SET usr_id = '"+user.getUsrPrenom()+"."+user.getUsrNom()+"'||v_count||'@pdlesig', usr_nom = '"+user.getUsrNom()+"', \r\n"
						+ "usr_prenom = '"+user.getUsrPrenom()+"', usr_birthday = To_char(To_DATE('"+user.getUsrBirthday()+"','DD/MM/YYYY'),'DD/MM/YYYY'),\r\n"
						+ "usr_fonction = '"+user.getUsrFonction()+"' WHERE usr_id = '"+oldUser.getUsrId()+"';\r\n"
						+ "DBMS_OUTPUT.PUT_LINE(usrId);\r\n"
						+ "END;";
						
						
						
						/*"Declare\r\n"
						+ "v_count NUMBER(5); usrId VARCHAR2(38);\r\n"
						+ "begin\r\n"
						+ "Select count(*) into v_count from user_usr where lower(usr_id) like '%"+new String(user.getUsrPrenom()+"."+user.getUsrNom()).toLowerCase()+"%';\r\n"
						+ "usrId := '"+user.getUsrPrenom()+"."+user.getUsrNom()+"'||v_count||'@pdlesig';"
						+ "UPDATE user_usr set usr_id = '"+user.getUsrPrenom()+"."+user.getUsrNom()+"+'||v_count||'@pdlesig' "
						+ "set usr_nom = "+user.getUsrNom()+", usr_prenom = "+user.getUsrPrenom()+", "
								+ "usr_birthday = "+user.getUsrBirthday()+", usr_fonction = "+user.getUsrFonction()+" WHERE usr_id = "+oldUser.getUsrId()+";\r\n"
						+ "DBMS_output.put_line(usrId);\r\n"
						+ "end;\r\n";
				/*ps.setString(1, new String("%"+user.getUsrPrenom()+"."+user.getUsrNom()+"%").toLowerCase());
				ps.setString(2, user.getUsrPrenom()+"."+user.getUsrNom());
				ps.setString(3, user.getUsrPrenom()+"."+user.getUsrNom());
				ps.setString(4, user.getUsrNom());
				ps.setString(5, user.getUsrPrenom());
				ps.setString(6, user.getUsrBirthday());
				ps.setString(7, user.getUsrFonction());
				ps.setString(8, oldUser.getUsrId());*/

				// Now, this is the actually interesting procedure call
				ps.executeUpdate(sqry);
				// After we're done with our call(s), we can proceed to
				// fetch the SERVEROUTPUT explicitly, using
				// DBMS_OUTPUT.GET_LINES
				try (CallableStatement call = con.prepareCall(
						"declare "
								+ "  num integer := 1000;"
								+ "begin "
								+ "  dbms_output.get_lines(?, num);"
								+ "end;"
						)) {
					call.registerOutParameter(1, Types.ARRAY,
							"DBMSOUTPUT_LINESARRAY");
					call.execute();

					Array array = null;
					try {
						array = call.getArray(1);
						//Stream.of((Object[]) array.getArray())
						//    .forEach(System.out::println);
						returnValue=((String[]) array.getArray())[0];
					}
					finally {
						if (array != null)
							array.free();
					}
				}
			}

			// Don't forget to disable DBMS_OUTPUT for the remaining use
			// of the connection.
			finally {
				ps.executeUpdate("begin dbms_output.disable(); end;");
				if (ps != null) {
					ps.close();
				}
			}
		}catch (Exception e) {
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
	public int deleteUser(User user) {
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
	public User getUser(String id) {
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
	/*
	 * USERPROFIL SECTION
	 */
	public int addUserProfil(String profil,User user) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;
		//Connection à la base de données
		try {
			con=DriverManager.getConnection(URL, LOGIN, PASS);
			ps=con.prepareStatement("insert into userprofil_usrpfl values(usrpfl_seq.nextval,?,?)");
			ps.setString(1, profil);
			ps.setString(2, user.getUsrId());
			returnValue=ps.executeUpdate();
		} catch (Exception e) {
			if (e.getMessage().contains("ORA-00001"))
				System.out.println("Cet identifiant de fournisseur existe déjà. Ajout impossible !");
			else
				e.printStackTrace();
		}
		return returnValue;
	}
	public int deleteUserProfil(String profil,User user) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;

		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("DELETE FROM userprofil_usrpfl WHERE usrpfl_pfl_id = ? and usrpfl_usr_id = ?");
			ps.setString(1, profil);
			ps.setString(2, user.getUsrId());
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
	public ResultSet getUserProfilRSet(String id) {
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
				ps = con.prepareStatement("SELECT DISTINCT * FROM userprofil_usrpfl WHERE lower(concat(usrpfl_usr_id,usrpfl_pfl_id)) like ? ORDER BY usrpfl_usr_id");
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
	public ArrayList<User> getUserProfil(String id) {
		//Must send an integer ID
		ResultSet rs = getUserProfilRSet(id);
		User currentUsr = null;
		ArrayList<User> returnValue = new ArrayList<User>();
		try {
			if(rs.next()) {
				currentUsr=new UserDAO().getUser(rs.getString("usrpfl_usr_id"));
				currentUsr.getUsrProfil().add(new ProfilDAO().get(rs.getString("usrpfl_pfl_id")));
			}
			while(rs.next()) {
				if(currentUsr.getUsrId().equalsIgnoreCase(rs.getString("usrpfl_usr_id"))) {
					returnValue.add(currentUsr);
					currentUsr=new UserDAO().getUser(rs.getString("usrpfl_usr_id"));
					currentUsr.getUsrProfil().add(new ProfilDAO().get(rs.getString("usrpfl_pfl_id")));
				}
				else {
					currentUsr.getUsrProfil().add(new ProfilDAO().get(rs.getString("usrpfl_pfl_id")));
				}
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
	/**
	 * <p><br>Lister les Profils associés à un utilisateur : searchKey = userId<br>
	 * <br>Lister les Profils disponibles : searchKey = *<br><p>
	 * @param searchKey
	 * @return Liste des profils
	 */
	public ArrayList<String> getNames(String searchKey){
		//indiquer "" pour tout afficher
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> returnValue = new ArrayList<String>();
		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			if(searchKey.equals("*")) {
				ps = con.prepareStatement("SELECT DISTINCT pfl_id FROM profil_pfl ORDER BY pfl_id");
			}else {
				ps = con.prepareStatement("SELECT DISTINCT pfl_id FROM ((profil_pfl INNER JOIN userprofil_usrpfl on pfl_id=usrpfl_pfl_id)"
						+ " INNER JOIN user_usr on usrpfl_usr_id=usr_id) WHERE usrpfl_usr_id = ? ORDER BY pfl_id");
				ps.setString(1, searchKey);
			}
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
	 * Permet de recuperer tous les résultats de la table USERPROFIL
	 * 
	 * @return une 
	 */
	public TableModel searchUserProfil(String searchKey) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TableModel returnValue = new DefaultTableModel();
		// connexion a la base de donnees
		try {
			rs=getUserProfilRSet(searchKey);
			returnValue = net.proteanit.sql.DbUtils.resultSetToTableModel(rs);

		} catch (Exception ee) {
			ee.printStackTrace();
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
	public static void main(String[] args) {
		/*for(String mot : new UserDAO().getNames("jean.michel@pdlesig"))
			System.out.println(mot);*/
		//System.out.println(new UserDAO().addUser(new User("", "alao", "mourad", "12/05/2000", "Prof")));
		System.out.println(new UserDAO().updateUser(new User("", "laos", "ourad", "12/05/2000", "Prof"),new User("mourad.alao0@pdlesig", "alao", "mourad", "12/05/2000", "Prof")));
		//new UserDAO().addUser();
	}
}

