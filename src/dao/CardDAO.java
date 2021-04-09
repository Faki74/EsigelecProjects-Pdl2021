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
public class CardDAO extends ConnectionDAO {
	/**
	 * Constructor
	 */
	public CardDAO() {
		super();
	}
	public int add(Card card) {
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
		}
		return returnValue;
	}
	public int update(Card card) {
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
		} 
		return returnValue;
	}
	public int delete(Card card) {
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
			ps = con.prepareStatement("SELECT * FROM cardleo_card WHERE lower(concat(card_usr_id,card_id)) like ?");
			ps.setString(1, new String("%"+id+"%").toLowerCase() );

			//Exécution de la requête
			rs = ps.executeQuery();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return rs;
	}
	public Card get(String id) {
		ResultSet rs = getRSet(id);
		Card returnValue = null;
		try {
			while (rs.next()) {
				returnValue = new Card(rs.getString("card_id"),
						rs.getString("card_usr_id"),
						rs.getInt("card_status"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * Permet de recuperer tous les fournisseurs stockes dans la table fournisseur
	 * 
	 * @return une ArrayList de fournisseur
	 */
	public TableModel searchCard(String searchKey) {
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
