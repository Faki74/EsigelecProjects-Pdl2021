package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.MaskFormatter;

import dao.CardDAO;
import dao.ProfilDAO;
import dao.UserDAO;
import dao.profilXUserDAO;
import model.Card;
import model.Profil;
import model.User;
import model.profilXuser;

import javax.swing.border.BevelBorder;
import javax.swing.JCheckBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtBhhkbkbkuh;
	private JTextField usrIdField;
	private JTextField usrPrenomField;
	private JTextField usrNomField;
	private JFormattedTextField usrDateField;
	private JTextField usrFonctionField;
	private JTextField usrSField;
	private JTextField pflIdField;
	private JTextField pflNameField;
	private JTextField cardIdField;
	private static CardLayout manageCard = new CardLayout();
	private boolean newUser=true, newProfil, newCard;
	private User currentUser;
	private Card currentCard;
	private Profil currentProfil;
	private ArrayList<profilXuser> currentProfilList = null;
	private ArrayList<String> currentCBoxList = null;
	private JTable searchTable;
	//add 14 37 for frame extension from backPane

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame frame = new AdminFrame();
					frame.getContentPane().setSize(new Dimension(BasePanel.normalDim.width+14,BasePanel.normalDim.height +37));
					frame.setVisible(true);
					System.out.println( frame.getContentPane().getSize()+" "+frame.getSize());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * 
		 */
		BasePanel backPane = new BasePanel();
		setBounds(100, 100, backPane.getSize().width+14,backPane.getSize().height+37);
		System.out.println(backPane.getSize());
		getLayeredPane().add(backPane,JLayeredPane.DEFAULT_LAYER);

		JPanel managePanel = new JPanel() {
			{
				setOpaque(false);
			}
			public void paintComponent(Graphics g) {
				g.setColor(getBackground());
				Rectangle r = g.getClipBounds();
				g.fillRect(r.x, r.y, r.width, r.height);
				super.paintComponent(g);
			}
		};
		managePanel.setBackground(new Color(0,0,0,1));


		managePanel.setSize(backPane.getSize());

		getLayeredPane().add(managePanel,JLayeredPane.PALETTE_LAYER);
		//managePanel.setLayout(new CardLayout(0, 0));
		managePanel.setLayout(manageCard);

		JPanel userManager = new JPanel();
		userManager.setOpaque(false);
		managePanel.add(userManager, "userManager");

		JPanel usrPane = new JPanel();
		usrPane.setBorder(UIManager.getBorder("Spinner.border"));
		usrPane.setOpaque(false);

		JPanel profilPane = new JPanel();
		profilPane.setBorder(UIManager.getBorder("Spinner.border"));
		profilPane.setOpaque(false);

		JPanel cardPane = new JPanel();
		cardPane.setBorder(UIManager.getBorder("Spinner.border"));
		cardPane.setOpaque(false);

		JPanel usrSearchPanel = new JPanel();
		usrSearchPanel.setBorder(UIManager.getBorder("Spinner.border"));
		usrSearchPanel.setOpaque(false);
		GroupLayout gl_userManager = new GroupLayout(userManager);
		gl_userManager.setHorizontalGroup(
				gl_userManager.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_userManager.createSequentialGroup()
						.addComponent(usrPane, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
						.addGap(1)
						.addComponent(profilPane, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
						.addGap(1)
						.addComponent(cardPane, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
				.addComponent(usrSearchPanel, GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
				);
		gl_userManager.setVerticalGroup(
				gl_userManager.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_userManager.createSequentialGroup()
						.addGroup(gl_userManager.createParallelGroup(Alignment.LEADING)
								.addComponent(cardPane, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
								.addComponent(profilPane, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
								.addComponent(usrPane, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE))
						.addGap(1)
						.addComponent(usrSearchPanel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
				);
		cardPane.setLayout(new CardLayout(0, 0));

		JPanel noCardPanel = new JPanel();
		noCardPanel.setOpaque(false);
		cardPane.add(noCardPanel, "noCardPanel");

		JLabel cardPaneLabel = new JLabel("L\u00E9o Card");
		cardPaneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cardPaneLabel.setForeground(SystemColor.controlDkShadow);
		cardPaneLabel.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel cardPaneLabel_1 = new JLabel("Aucune Carte Associ\u00E9e");
		cardPaneLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		cardPaneLabel_1.setForeground(Color.RED);
		cardPaneLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		cardPaneLabel_1.setBackground(Color.RED);

		OvalButton cardBtnAssocier_1 = new OvalButton("Associer Carte", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));
		cardBtnAssocier_1.setOpaque(false);
		cardBtnAssocier_1.setFocusPainted(false);
		cardBtnAssocier_1.setBorderPainted(false);
		cardBtnAssocier_1.setContentAreaFilled(false);
		cardBtnAssocier_1.setForeground(new Color(105, 105, 105));
		cardBtnAssocier_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		cardBtnAssocier_1.setForeground(SystemColor.controlDkShadow);
		cardBtnAssocier_1.setFont(new Font("Tahoma", Font.BOLD, 20));

		GroupLayout gl_noCardPanel = new GroupLayout(noCardPanel);
		gl_noCardPanel.setHorizontalGroup(
				gl_noCardPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_noCardPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_noCardPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_noCardPanel.createSequentialGroup()
										.addComponent(cardPaneLabel, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
										.addGap(59))
								.addGroup(gl_noCardPanel.createSequentialGroup()
										.addComponent(cardBtnAssocier_1, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
										.addContainerGap())
								.addGroup(Alignment.TRAILING, gl_noCardPanel.createSequentialGroup()
										.addComponent(cardPaneLabel_1, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
										.addContainerGap())))
				);
		gl_noCardPanel.setVerticalGroup(
				gl_noCardPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_noCardPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(cardPaneLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addGap(84)
						.addComponent(cardPaneLabel_1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
						.addComponent(cardBtnAssocier_1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
						.addGap(65))
				);
		noCardPanel.setLayout(gl_noCardPanel);

		JPanel cardPanel = new JPanel();
		cardPanel.setOpaque(false);
		cardPane.add(cardPanel, "cardPanel");

		JLabel cardPaneLabel_2 = new JLabel("L\u00E9o Card");
		cardPaneLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		cardPaneLabel_2.setForeground(SystemColor.controlDkShadow);
		cardPaneLabel_2.setFont(new Font("Tahoma", Font.BOLD, 25));

		cardIdField = new JTextField();
		cardIdField.setText("Id Carte");
		cardIdField.setOpaque(false);
		cardIdField.setHorizontalAlignment(SwingConstants.CENTER);
		cardIdField.setForeground(SystemColor.controlDkShadow);
		cardIdField.setFont(new Font("Consolas", Font.PLAIN, 25));
		cardIdField.setColumns(10);

		JLabel cardStatusLabel = new JLabel("Active");
		cardStatusLabel.setBorder(UIManager.getBorder("TextField.border"));
		cardStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cardStatusLabel.setForeground(SystemColor.controlDkShadow);
		cardStatusLabel.setFont(new Font("Consolas", Font.PLAIN, 25));

		JCheckBox cardStatusCBox = new JCheckBox("");
		cardStatusCBox.setAlignmentY(Component.TOP_ALIGNMENT);
		cardStatusCBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		cardStatusCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cardStatusCBox.isSelected()) 
					cardStatusLabel.setText("Active");
				else 
					cardStatusLabel.setText("Bloquée");
			}
		});
		cardStatusCBox.setSelected(true);
		cardStatusCBox.setForeground(new Color(105, 105, 105));
		cardStatusCBox.setFont(new Font("Consolas", Font.BOLD, 20));
		cardStatusCBox.setBorder(UIManager.getBorder("TextField.border"));


		OvalButton cardBtnAssocier = new OvalButton("Associer", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));
		cardBtnAssocier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cardBtnAssocier.setOpaque(false);
		cardBtnAssocier.setFocusPainted(false);
		cardBtnAssocier.setBorderPainted(false);
		cardBtnAssocier.setContentAreaFilled(false);
		cardBtnAssocier.setForeground(new Color(105, 105, 105));
		cardBtnAssocier.setFont(new Font("Tahoma", Font.BOLD, 20));
		cardBtnAssocier.setForeground(SystemColor.controlDkShadow);
		cardBtnAssocier.setFont(new Font("Tahoma", Font.BOLD, 20));

		OvalButton cardBtnSupprimer = new OvalButton("Supprimer", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));
		cardBtnSupprimer.setOpaque(false);
		cardBtnSupprimer.setFocusPainted(false);
		cardBtnSupprimer.setBorderPainted(false);
		cardBtnSupprimer.setContentAreaFilled(false);
		cardBtnSupprimer.setForeground(new Color(105, 105, 105));
		cardBtnSupprimer.setFont(new Font("Tahoma", Font.BOLD, 20));
		cardBtnSupprimer.setForeground(SystemColor.controlDkShadow);
		cardBtnSupprimer.setFont(new Font("Tahoma", Font.BOLD, 20));
		GroupLayout gl_cardPanel = new GroupLayout(cardPanel);
		gl_cardPanel.setHorizontalGroup(
				gl_cardPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_cardPanel.createSequentialGroup()
						.addGap(76)
						.addComponent(cardPaneLabel_2, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
						.addGap(55))
				.addGroup(gl_cardPanel.createSequentialGroup()
						.addGap(21)
						.addGroup(gl_cardPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_cardPanel.createSequentialGroup()
										.addComponent(cardStatusLabel, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
										.addGap(18)
										.addComponent(cardStatusCBox, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
								.addComponent(cardIdField, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
						.addGap(21))
				.addGroup(gl_cardPanel.createSequentialGroup()
						.addGap(43)
						.addGroup(gl_cardPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(cardBtnSupprimer, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
								.addComponent(cardBtnAssocier, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
						.addGap(59))
				);
		gl_cardPanel.setVerticalGroup(
				gl_cardPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_cardPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(cardPaneLabel_2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addGap(67)
						.addComponent(cardIdField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(gl_cardPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(cardStatusCBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cardStatusLabel, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
						.addComponent(cardBtnAssocier, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(cardBtnSupprimer, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(42))
				);
		cardPanel.setLayout(gl_cardPanel);
		profilPane.setLayout(new CardLayout(0, 0));

		JPanel noPflPanel = new JPanel();
		noPflPanel.setOpaque(false);
		profilPane.add(noPflPanel, "noPflPanel");

		JLabel pflPaneLabel = new JLabel("Profil");
		pflPaneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pflPaneLabel.setForeground(SystemColor.controlDkShadow);
		pflPaneLabel.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel usrPaneLabel_1 = new JLabel("Aucun Profil Associ\u00E9");
		usrPaneLabel_1.setBackground(new Color(255, 0, 0));
		usrPaneLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		usrPaneLabel_1.setForeground(Color.RED);
		usrPaneLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));

		OvalButton pflBtnAssocier_1 = new OvalButton("Associer Profil", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));
		pflBtnAssocier_1.setOpaque(false);
		pflBtnAssocier_1.setFocusPainted(false);
		pflBtnAssocier_1.setBorderPainted(false);
		pflBtnAssocier_1.setContentAreaFilled(false);
		pflBtnAssocier_1.setForeground(new Color(105, 105, 105));
		pflBtnAssocier_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		pflBtnAssocier_1.setForeground(SystemColor.controlDkShadow);
		pflBtnAssocier_1.setFont(new Font("Tahoma", Font.BOLD, 20));

		GroupLayout gl_noPflPanel = new GroupLayout(noPflPanel);
		gl_noPflPanel.setHorizontalGroup(
				gl_noPflPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_noPflPanel.createSequentialGroup()
						.addGap(37)
						.addComponent(usrPaneLabel_1, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
						.addGap(46))
				.addGroup(gl_noPflPanel.createSequentialGroup()
						.addGap(25)
						.addComponent(pflBtnAssocier_1, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
						.addGap(31))
				.addGroup(gl_noPflPanel.createSequentialGroup()
						.addGap(77)
						.addComponent(pflPaneLabel, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
						.addGap(80))
				);
		gl_noPflPanel.setVerticalGroup(
				gl_noPflPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_noPflPanel.createSequentialGroup()
						.addGap(53)
						.addComponent(pflPaneLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addGap(43)
						.addComponent(usrPaneLabel_1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
						.addComponent(pflBtnAssocier_1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
						.addGap(65))
				);
		noPflPanel.setLayout(gl_noPflPanel);

		JPanel pflPanel = new JPanel();
		pflPanel.setOpaque(false);
		profilPane.add(pflPanel, "pflPanel");

		JLabel pflPaneLabel_2 = new JLabel("Profil");
		pflPaneLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		pflPaneLabel_2.setForeground(SystemColor.controlDkShadow);
		pflPaneLabel_2.setFont(new Font("Tahoma", Font.BOLD, 25));

		JComboBox pflSelectBox = new JComboBox();
		pflSelectBox.setForeground(new Color(105, 105, 105));
		pflSelectBox.setOpaque(false);
		pflSelectBox.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JSeparator separator = new JSeparator();

		pflIdField = new JTextField();
		pflIdField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(pflIdField.getText().equalsIgnoreCase("Id Profil"))
					pflIdField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(pflIdField.getText().equals(""))
					usrFonctionField.setText("Id Profil");
			}
		});
		pflIdField.setText("Id Profil");
		pflIdField.setOpaque(false);
		pflIdField.setHorizontalAlignment(SwingConstants.CENTER);
		pflIdField.setForeground(SystemColor.controlDkShadow);
		pflIdField.setFont(new Font("Consolas", Font.PLAIN, 25));
		pflIdField.setColumns(10);

		pflNameField = new JTextField();
		pflNameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(pflNameField.getText().equalsIgnoreCase("Nom du Profil"))
					pflNameField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(pflNameField.getText().equals(""))
					pflNameField.setText("Nom du Profil");
			}
		});
		pflNameField.setText("Nom du Profil");
		pflNameField.setOpaque(false);
		pflNameField.setHorizontalAlignment(SwingConstants.CENTER);
		pflNameField.setForeground(SystemColor.controlDkShadow);
		pflNameField.setFont(new Font("Consolas", Font.PLAIN, 25));
		pflNameField.setColumns(10);

		OvalButton pflBtnAssocier = new OvalButton("Associer", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));
		pflBtnAssocier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pflBtnAssocier.setOpaque(false);
		pflBtnAssocier.setFocusPainted(false);
		pflBtnAssocier.setBorderPainted(false);
		pflBtnAssocier.setContentAreaFilled(false);
		pflBtnAssocier.setForeground(new Color(105, 105, 105));
		pflBtnAssocier.setFont(new Font("Tahoma", Font.BOLD, 20));
		pflBtnAssocier.setForeground(SystemColor.controlDkShadow);
		pflBtnAssocier.setFont(new Font("Tahoma", Font.BOLD, 20));

		OvalButton pflBtnSupprimer = new OvalButton("Supprimer", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));
		pflBtnSupprimer.setOpaque(false);
		pflBtnSupprimer.setFocusPainted(false);
		pflBtnSupprimer.setBorderPainted(false);
		pflBtnSupprimer.setContentAreaFilled(false);
		pflBtnSupprimer.setForeground(new Color(105, 105, 105));
		pflBtnSupprimer.setFont(new Font("Tahoma", Font.BOLD, 20));
		pflBtnSupprimer.setForeground(SystemColor.controlDkShadow);
		pflBtnSupprimer.setFont(new Font("Tahoma", Font.BOLD, 20));

		GroupLayout gl_pflPanel = new GroupLayout(pflPanel);
		gl_pflPanel.setHorizontalGroup(
				gl_pflPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addGap(88)
						.addComponent(pflPaneLabel_2, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
						.addGap(82))
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_pflPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pflPanel.createSequentialGroup()
										.addGap(1)
										.addComponent(separator, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
								.addComponent(pflIdField, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
						.addContainerGap())
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(pflSelectBox, 0, 279, Short.MAX_VALUE)
						.addContainerGap())
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(pflNameField, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addGap(63)
						.addGroup(gl_pflPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(pflBtnSupprimer, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(pflBtnAssocier, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
						.addGap(78))
				);
		gl_pflPanel.setVerticalGroup(
				gl_pflPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(pflPaneLabel_2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(pflSelectBox, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addGap(28)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
						.addGap(31)
						.addComponent(pflIdField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(pflNameField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
						.addComponent(pflBtnAssocier, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(pflBtnSupprimer, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(45))
				);
		pflPanel.setLayout(gl_pflPanel);
		usrSearchPanel.setLayout(new CardLayout(0, 0));

		JPanel usrSearch = new JPanel();
		usrSearchPanel.add(usrSearch, "usrSearch");

		usrSField = new JTextField();
		usrSField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(usrSField.getText().equalsIgnoreCase("Rechercher"))
					usrSField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrSField.getText().equals(""))
					usrSField.setText("Rechercher");
			}
		});
		usrSField.setText("Rechercher");
		usrSField.setOpaque(false);
		usrSField.setHorizontalAlignment(SwingConstants.CENTER);
		usrSField.setForeground(SystemColor.controlDkShadow);
		usrSField.setFont(new Font("Consolas", Font.PLAIN, 25));
		usrSField.setColumns(10);

		OvalButton usrSBtn = new OvalButton("Rechercher", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));


		usrSBtn.setOpaque(false);
		usrSBtn.setFocusPainted(false);
		usrSBtn.setBorderPainted(false);
		usrSBtn.setContentAreaFilled(false);
		usrSBtn.setForeground(new Color(105, 105, 105));
		usrSBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		GroupLayout gl_usrSearch = new GroupLayout(usrSearch);
		gl_usrSearch.setHorizontalGroup(
				gl_usrSearch.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_usrSearch.createSequentialGroup()
						.addGap(59)
						.addComponent(usrSBtn, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(usrSField, GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
						.addGap(31))
				);
		gl_usrSearch.setVerticalGroup(
				gl_usrSearch.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_usrSearch.createSequentialGroup()
						.addGap(31)
						.addGroup(gl_usrSearch.createParallelGroup(Alignment.LEADING)
								.addComponent(usrSBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(usrSField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(18, Short.MAX_VALUE))
				);
		usrSearch.setLayout(gl_usrSearch);

		JPanel profilSearch = new JPanel();
		usrSearchPanel.add(profilSearch, "profilSearch");

		JPanel cardSearch = new JPanel();
		usrSearchPanel.add(cardSearch, "cardSearch");

		JLabel usrPaneLabel = new JLabel("Personne");
		usrPaneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usrPaneLabel.setForeground(new Color(105, 105, 105));
		usrPaneLabel.setFont(new Font("Tahoma", Font.BOLD, 25));

		usrIdField = new JTextField();

		usrIdField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(usrIdField.getText().equalsIgnoreCase("Id"))
					usrIdField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrIdField.getText().equals(""))
					usrIdField.setText("Id");
			}
		});
		usrIdField.setForeground(new Color(105, 105, 105));
		usrIdField.setText("Id");
		usrIdField.setFont(new Font("Consolas", Font.PLAIN, 20));
		usrIdField.setOpaque(false);
		usrIdField.setHorizontalAlignment(SwingConstants.CENTER);
		usrIdField.setColumns(10);

		usrPrenomField = new JTextField();
		usrPrenomField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(usrPrenomField.getText().equalsIgnoreCase("Prenom"))
					usrPrenomField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrPrenomField.getText().equals(""))
					usrPrenomField.setText("Prenom");
			}
		});
		usrPrenomField.setText("Prenom");
		usrPrenomField.setOpaque(false);
		usrPrenomField.setHorizontalAlignment(SwingConstants.CENTER);
		usrPrenomField.setForeground(SystemColor.controlDkShadow);
		usrPrenomField.setFont(new Font("Consolas", Font.PLAIN, 20));
		usrPrenomField.setColumns(10);

		usrNomField = new JTextField();
		usrNomField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(usrNomField.getText().equalsIgnoreCase("Nom"))
					usrNomField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrNomField.getText().equals(""))
					usrNomField.setText("Nom");
			}
		});
		usrNomField.setText("Nom");
		usrNomField.setOpaque(false);
		usrNomField.setHorizontalAlignment(SwingConstants.CENTER);
		usrNomField.setForeground(SystemColor.controlDkShadow);
		usrNomField.setFont(new Font("Consolas", Font.PLAIN, 20));
		usrNomField.setColumns(10);

		usrDateField = new JFormattedTextField();
		usrDateField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		usrDateField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(usrDateField.getText().equalsIgnoreCase("Date de naissance")) {
					try {
						MaskFormatter dateMask = new MaskFormatter("##/##/####");
						dateMask.setPlaceholder("01/01/1970");
						//dateMask.setOverwriteMode(true);
						dateMask.install(usrDateField);
					} catch (ParseException ex) {}
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrDateField.getText().equals(""))
					usrDateField.setText("Date de naissance");
			}
		});
		usrDateField.setText("Date de naissance");
		usrDateField.setOpaque(false);
		usrDateField.setHorizontalAlignment(SwingConstants.CENTER);
		usrDateField.setForeground(SystemColor.controlDkShadow);
		usrDateField.setFont(new Font("Consolas", Font.PLAIN, 25));
		usrDateField.setColumns(10);

		usrFonctionField = new JTextField();
		usrFonctionField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(usrFonctionField.getText().equalsIgnoreCase("Fonction"))
					usrFonctionField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrFonctionField.getText().equals(""))
					usrFonctionField.setText("Fonction");
			}
		});
		usrFonctionField.setText("Fonction");
		usrFonctionField.setOpaque(false);
		usrFonctionField.setHorizontalAlignment(SwingConstants.CENTER);
		usrFonctionField.setForeground(SystemColor.controlDkShadow);
		usrFonctionField.setFont(new Font("Consolas", Font.PLAIN, 20));
		usrFonctionField.setColumns(10);

		OvalButton usrBtnCreate = new OvalButton("Cr\u00E9er",1,2,new Color(191,144,0,150),new Color(191,144,0,80),new Color(253,184,155,80),new Color(253,184,155,80));
		OvalButton usrBtnModifier = new OvalButton("Modifier",1,2,new Color(191,144,0,150),new Color(191,144,0,80),new Color(253,184,155,80),new Color(253,184,155,80));
		usrBtnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(new UserDAO().update(new User(usrIdField.getText(),usrNomField.getText(),usrPrenomField.getText(),usrDateField.getText(),usrFonctionField.getText())) !=0) {
					JOptionPane.showMessageDialog(null, "Mise à jour Effectuée");
				}
			}
		});
		OvalButton usrBtnSupprimer = new OvalButton("Supprimer",1,2,new Color(191,144,0,150),new Color(191,144,0,80),new Color(253,184,155,80),new Color(253,184,155,80));
		usrBtnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(new UserDAO().update(new User(usrIdField.getText(),usrNomField.getText(),usrPrenomField.getText(),usrDateField.getText(),usrFonctionField.getText())) !=0) {
					JOptionPane.showMessageDialog(null, "Personne Supprimée");
					( (CardLayout) managePanel.getLayout() ).show(managePanel, "searchPanel");
					//manageCard.show(managePanel,"searchManager");
					managePanel.repaint();managePanel.validate();
					TableModel tbm = new UserDAO().searchUser("");
					searchTable.setModel(tbm);
				}

			}
		});

		usrBtnCreate.setOpaque(false);
		usrBtnCreate.setFocusPainted(false);
		usrBtnCreate.setBorderPainted(false);
		usrBtnCreate.setContentAreaFilled(false);
		usrBtnCreate.setForeground(new Color(105, 105, 105));
		usrBtnCreate.setFont(new Font("Tahoma", Font.BOLD, 20));


		usrBtnModifier.setOpaque(false);
		usrBtnModifier.setFocusPainted(false);
		usrBtnModifier.setBorderPainted(false);
		usrBtnModifier.setContentAreaFilled(false);
		usrBtnModifier.setForeground(new Color(105, 105, 105));
		usrBtnModifier.setFont(new Font("Tahoma", Font.BOLD, 20));
		usrBtnModifier.setForeground(SystemColor.controlDkShadow);
		usrBtnModifier.setFont(new Font("Tahoma", Font.BOLD, 20));


		usrBtnSupprimer.setOpaque(false);
		usrBtnSupprimer.setFocusPainted(false);
		usrBtnSupprimer.setBorderPainted(false);
		usrBtnSupprimer.setContentAreaFilled(false);
		usrBtnSupprimer.setForeground(SystemColor.controlDkShadow);
		usrBtnSupprimer.setFont(new Font("Tahoma", Font.BOLD, 20));

		JRadioButton rdbtnNewRadioButton = new JRadioButton("");
		GroupLayout gl_usrPane = new GroupLayout(usrPane);
		gl_usrPane.setHorizontalGroup(
				gl_usrPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_usrPane.createSequentialGroup()
						.addComponent(rdbtnNewRadioButton)
						.addGap(60)
						.addComponent(usrPaneLabel, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
						.addGap(97))
				.addGroup(gl_usrPane.createSequentialGroup()
						.addGap(21)
						.addGroup(gl_usrPane.createParallelGroup(Alignment.LEADING)
								.addComponent(usrFonctionField, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_usrPane.createParallelGroup(Alignment.LEADING)
										.addComponent(usrDateField, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_usrPane.createParallelGroup(Alignment.LEADING)
												.addComponent(usrNomField, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_usrPane.createParallelGroup(Alignment.LEADING)
														.addComponent(usrPrenomField, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)
														.addComponent(usrIdField, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)))))
						.addGap(27))
				.addGroup(gl_usrPane.createSequentialGroup()
						.addGap(65)
						.addGroup(gl_usrPane.createParallelGroup(Alignment.LEADING)
								.addComponent(usrBtnSupprimer, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
								.addComponent(usrBtnModifier, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
								.addComponent(usrBtnCreate, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
						.addGap(84))
				);
		gl_usrPane.setVerticalGroup(
				gl_usrPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_usrPane.createSequentialGroup()
						.addGroup(gl_usrPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_usrPane.createSequentialGroup()
										.addContainerGap()
										.addComponent(usrPaneLabel)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(usrIdField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(usrPrenomField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(usrNomField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(usrDateField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(usrFonctionField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
										.addComponent(usrBtnCreate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(usrBtnModifier, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(usrBtnSupprimer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(rdbtnNewRadioButton))
						.addContainerGap())
				);
		usrPane.setLayout(gl_usrPane);
		userManager.setLayout(gl_userManager);
		System.out.println(usrDateField.getText());
		JPanel placeManager = new JPanel();
		placeManager.setOpaque(false);
		managePanel.add(placeManager, "placeManager");

		JPanel searchManager = new JPanel();
		searchManager.setOpaque(false);
		managePanel.add(searchManager, "searchManager");

		JLabel resultLabel = new JLabel("R\u00E9sultats");
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setForeground(SystemColor.controlDkShadow);
		resultLabel.setFont(new Font("Tahoma", Font.BOLD, 25));

		JSeparator separator_2 = new JSeparator();

		JPanel resultManager = new JPanel();
		resultManager.setOpaque(false);

		JButton btnNewButton = new JButton("New button");

		GroupLayout gl_searchManager = new GroupLayout(searchManager);
		gl_searchManager.setHorizontalGroup(
				gl_searchManager.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchManager.createSequentialGroup()
						.addGroup(gl_searchManager.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_searchManager.createSequentialGroup()
										.addGap(370)
										.addComponent(resultLabel, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_searchManager.createSequentialGroup()
										.addGap(50)
										.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 787, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_searchManager.createSequentialGroup()
										.addGap(22)
										.addComponent(resultManager, GroupLayout.PREFERRED_SIZE, 842, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_searchManager.createSequentialGroup()
										.addGap(76)
										.addComponent(btnNewButton)))
						.addContainerGap(22, Short.MAX_VALUE))
				);
		gl_searchManager.setVerticalGroup(
				gl_searchManager.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchManager.createSequentialGroup()
						.addGap(10)
						.addComponent(resultLabel)
						.addGap(10)
						.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(resultManager, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
						.addGap(56)
						.addComponent(btnNewButton)
						.addGap(80))
				);
		resultManager.setLayout(new CardLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		resultManager.add(scrollPane, "scrollPane");

		searchTable = new JTable();
		searchTable.setOpaque(false);
		searchTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null},
				},
				new String[] {
						"New column", "New column", "New column", "New column", "New column"
				}
				));
		scrollPane.setViewportView(searchTable);

		/*
		 * OPERATIONS DELICATES A EFFECTUER
		 */


		searchTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				newUser=false;
				int ligne = searchTable.getSelectedRow();
				String idKey = searchTable.getModel().getValueAt(ligne,0).toString();
				String idName = searchTable.getColumnName(0);
				//userManager
				//if(idName.equalsIgnoreCase("usr_id")){
				currentUser= new UserDAO().get(idKey);
				( (CardLayout) managePanel.getLayout() ).show(managePanel, "userManager");
				( (CardLayout) profilPane.getLayout() ).show(profilPane, "noPflPanel");
				//Filling User fields
				usrIdField.setText(currentUser.getUsrId());
				usrPrenomField.setText(currentUser.getUsrPrenom());
				usrNomField.setText(currentUser.getUsrNom());
				usrDateField.setText(currentUser.getUsrBirthday());
				usrFonctionField.setText(currentUser.getUsrFonction());
				//Filling Card fields
				currentCard= new CardDAO().get(currentUser.getUsrId());
				System.out.println(currentCard.getCardId());
				if(currentCard!=null) {
					( (CardLayout) cardPane.getLayout() ).show(cardPane, "cardPanel");
					//System.out.println(currentCard.getCardId());
					cardStatusCBox.setSelected(1==currentCard.getCardStatus()?true:false);
					cardIdField.setText(currentCard.getCardId());
				}else {
					( (CardLayout) cardPane.getLayout() ).show(cardPane, "noCardPanel");
				}
				//Filling Profil fields
				currentProfilList=(new profilXUserDAO()).get(currentUser.getUsrId());
				if(currentProfilList.size()!=0) {
					( (CardLayout) profilPane.getLayout() ).show(profilPane, "pflPanel");
					currentCBoxList= new profilXUserDAO().getNames(currentUser.getUsrId());
					if(currentCBoxList.size()!=0) {
						System.out.println("Pf natif"); newProfil=false;
						for(String profil : currentCBoxList) {
							pflSelectBox.addItem(profil);
							System.out.println(profil);
						}
					}else {
						newProfil=true;
						currentProfilList=new profilXUserDAO().get("");
						currentCBoxList= new profilXUserDAO().getNames("");
						System.out.println("Pf total");
						for(String profil : currentCBoxList) {
							pflSelectBox.addItem(profil);
							System.out.println(profil);
						}
					}
					System.out.println(pflSelectBox.getSelectedItem().toString());
					currentProfil=new ProfilDAO().get(pflSelectBox.getSelectedItem().toString());
					//currentProfil=currentProfilList.get(0).getProfil().getPflId();
					System.out.println(currentProfil.getPflId());
					pflIdField.setText(currentProfil.getPflId());
					pflNameField.setText(currentProfil.getPflDesc());
				}else {
					( (CardLayout) profilPane.getLayout() ).show(profilPane, "noPflPanel");
				}
				
				//pflIdField.setText(currentProfil.getPflId());
				//pflNameField.setText(currentProfil.getPflDesc());
				/*if(currentProfilList.size()!=0) {
					currentProfil=new ProfilDAO().get(pflSelectBox.getSelectedItem().toString());
				}else {
					JOptionPane.showMessageDialog(null, "Aucun profil disponible\n Créez un Nouveau profil");
				}*/
				//( (CardLayout) managePanel.getLayout() ).show(managePanel, "userManager");
				//}
			}
		});
		searchManager.setLayout(gl_searchManager);
		/*
		 * 


		 */

		/*
		 * Panel linking
		 */
		usrBtnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(newUser) {
					usrIdField.setEditable(false);
					usrBtnModifier.setEnabled(false);
					usrBtnSupprimer.setEnabled(false);
					newUser=false;
					/*Verification validité des champs*/
					if( !usrNomField.getText().equalsIgnoreCase("Nom") &&
							!usrPrenomField.getText().equalsIgnoreCase("Prenom") &&
							!usrFonctionField.getText().equalsIgnoreCase("Fonction")) {
						new UserDAO().add(new User("",usrNomField.getText(),usrPrenomField.getText(),usrDateField.getText(),usrFonctionField.getText()));
						managePanel.removeAll(); managePanel.add(searchManager);
						//resultLabel.setText();
						managePanel.repaint(); managePanel.revalidate();
						TableModel tbm = new UserDAO().viewResults("user_usr");
						searchTable.setModel(tbm);
					}else {
						JOptionPane.showMessageDialog(null,"Champs invalides");
					}
				}
				else {
					//new UserDAO().add(new User("",usrNomField.getText(),usrPrenomField.getText(),usrDateField.getText(),usrFonctionField.getText()));
					usrBtnModifier.setEnabled(true);
					usrBtnSupprimer.setEnabled(true);
					newUser=true;
				}
				System.out.println(usrDateField.getText());
			}
		});
		/*pflSelectBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentProfilList.size()!=0) {
					currentProfil=new ProfilDAO().get(pflSelectBox.getSelectedItem().toString());
					currentProfil=new ProfilDAO().get(pflSelectBox.getSelectedItem().toString());
					pflIdField.setText(currentProfil.getPflId());
					pflNameField.setText(currentProfil.getPflDesc());
				}
			}
		});*/
		pflBtnAssocier_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!newUser) {
					( (CardLayout) profilPane.getLayout() ).show(profilPane, "pflPanel");
				}else {
					JOptionPane.showMessageDialog(null, "Selectionnez un profil au préalable");
				}
			}
		});
		cardBtnAssocier_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				 * 
				 * 
				 * 
				 * 
				 */
				if(!newUser) {
					( (CardLayout) cardPane.getLayout() ).show(cardPane, "cardPanel");
				}else {
					JOptionPane.showMessageDialog(null, "Selectionnez un profil au préalable");
				}
			}
		});
		usrSBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!usrSField.getText().equalsIgnoreCase("")) {
					//managePanel.removeAll();
					( (CardLayout) managePanel.getLayout() ).show(managePanel, "searchPanel");
					//manageCard.show(managePanel,"searchManager");
					managePanel.repaint();managePanel.validate();
					TableModel tbm = new UserDAO().searchUser(usrSField.getText());
					searchTable.setModel(tbm);
				}
			}
		});
		usrSField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!usrSField.getText().equalsIgnoreCase("") && e.getKeyCode() == KeyEvent.VK_ENTER){
					( (CardLayout) managePanel.getLayout() ).show(managePanel, "searchManager");
					//manageCard.show(managePanel,"searchManager");
					TableModel tbm = new UserDAO().searchUser(usrSField.getText());
					searchTable.setModel(tbm);
				}
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				( (CardLayout) managePanel.getLayout() ).show(managePanel, "placeManager");
				//manageCard.show(managePanel,"searchManager");
				getLayeredPane().repaint(); 
				getLayeredPane().revalidate();
			}
		});

	}
}
class TransparentPanel extends JPanel {
	{
		setOpaque(false);
	}
	public TransparentPanel() {
		// TODO Auto-generated constructor stub
		super();
	}
	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		Rectangle r = g.getClipBounds();
		g.fillRect(r.x, r.y, r.width, r.height);
		super.paintComponent(g);
	}
}