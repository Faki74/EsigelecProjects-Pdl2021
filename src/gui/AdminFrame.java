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
import javax.swing.DefaultComboBoxModel;
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

import javax.swing.border.BevelBorder;
import javax.swing.JCheckBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

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
	private JTextField usrPflIdField;
	private JTextField cardIdField;
	private ArrayList<String> usrPflList = null;
	private JComboBox<String> usrPflSelectBox = null;
	private JComboBox<String> usrPflSelectBox_1 = null;
	private static CardLayout cl_managePanel = new CardLayout();
	private boolean newUser=true, newProfil=true, newCard=true;
	private User currentUser;
	private Card currentCard;
	private Profil currentProfil;
	private Object[] usrPflCBoxList = null;
	private JTable searchTable;
	private JPanel managePanel;
	private OvalButton usrBtnCreate;
	private OvalButton usrBtnModifier;
	private OvalButton usrBtnSupprimer;
	private JPanel usrPane;
	private JPanel profilPane;
	private JPanel cardPane;
	private JPanel searchManager;
	private OvalButton usrPflBtnAssocier;
	private OvalButton usrPflBtnSupprimer;
	private OvalButton cardBtnAssocier;
	private OvalButton cardBtnSupprimer;
	private JLabel usrPaneLabel_1;
	private OvalButton usrPflBtnAssocier_1;
	private JLabel cardStatusLabel;
	private JCheckBox cardStatusCBox;

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
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

		managePanel = new JPanel() {
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
		managePanel.setLayout(cl_managePanel);

		JPanel userManager = new JPanel();
		userManager.setOpaque(false);
		managePanel.add(userManager, "userManager");

		usrPane = new JPanel();
		usrPane.setBorder(UIManager.getBorder("Spinner.border"));
		usrPane.setOpaque(false);

		profilPane = new JPanel();
		profilPane.setBorder(UIManager.getBorder("Spinner.border"));
		profilPane.setOpaque(false);

		cardPane = new JPanel();
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
		cardIdField.setEditable(false);
		cardIdField.setText("Id Carte");
		cardIdField.setOpaque(false);
		cardIdField.setHorizontalAlignment(SwingConstants.CENTER);
		cardIdField.setForeground(SystemColor.controlDkShadow);
		cardIdField.setFont(new Font("Consolas", Font.PLAIN, 25));
		cardIdField.setColumns(10);

		cardStatusLabel = new JLabel("Active");
		cardStatusLabel.setBorder(UIManager.getBorder("TextField.border"));
		cardStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cardStatusLabel.setForeground(SystemColor.controlDkShadow);
		cardStatusLabel.setFont(new Font("Consolas", Font.PLAIN, 25));

		cardStatusCBox = new JCheckBox("");
		cardStatusCBox.setAlignmentY(Component.TOP_ALIGNMENT);
		cardStatusCBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		cardStatusCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cardStatusCBox.isSelected()) 
					cardStatusLabel.setText("Active");
				else 
					cardStatusLabel.setText("Bloqu�e");
				new CardDAO().update(currentUser.getUsrId(),(cardStatusCBox.isSelected()?1:0));
			}
		});
		cardStatusCBox.setSelected(true);
		cardStatusCBox.setForeground(new Color(105, 105, 105));
		cardStatusCBox.setFont(new Font("Consolas", Font.BOLD, 20));
		cardStatusCBox.setBorder(UIManager.getBorder("TextField.border"));


		cardBtnAssocier = new OvalButton("Associer", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));

		cardBtnAssocier.setOpaque(false);
		cardBtnAssocier.setFocusPainted(false);
		cardBtnAssocier.setBorderPainted(false);
		cardBtnAssocier.setContentAreaFilled(false);
		cardBtnAssocier.setForeground(new Color(105, 105, 105));
		cardBtnAssocier.setFont(new Font("Tahoma", Font.BOLD, 20));
		cardBtnAssocier.setForeground(SystemColor.controlDkShadow);
		cardBtnAssocier.setFont(new Font("Tahoma", Font.BOLD, 20));

		cardBtnSupprimer = new OvalButton("Supprimer", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));
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

		JLabel usrPflPaneLabel = new JLabel("Profil");
		usrPflPaneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usrPflPaneLabel.setForeground(SystemColor.controlDkShadow);
		usrPflPaneLabel.setFont(new Font("Tahoma", Font.BOLD, 25));

		usrPaneLabel_1 = new JLabel("Aucun Profil Associ\u00E9");
		usrPaneLabel_1.setBackground(new Color(255, 0, 0));
		usrPaneLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		usrPaneLabel_1.setForeground(Color.RED);
		usrPaneLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));

		usrPflBtnAssocier_1 = new OvalButton("Associer Profil", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));
		usrPflBtnAssocier_1.setOpaque(false);
		usrPflBtnAssocier_1.setFocusPainted(false);
		usrPflBtnAssocier_1.setBorderPainted(false);
		usrPflBtnAssocier_1.setContentAreaFilled(false);
		usrPflBtnAssocier_1.setForeground(new Color(105, 105, 105));
		usrPflBtnAssocier_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		usrPflBtnAssocier_1.setForeground(SystemColor.controlDkShadow);
		usrPflBtnAssocier_1.setFont(new Font("Tahoma", Font.BOLD, 20));


		GroupLayout gl_noPflPanel = new GroupLayout(noPflPanel);
		gl_noPflPanel.setHorizontalGroup(
				gl_noPflPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_noPflPanel.createSequentialGroup()
						.addGap(15)
						.addComponent(usrPaneLabel_1, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
						.addGap(15))
				.addGroup(gl_noPflPanel.createSequentialGroup()
						.addGap(25)
						.addComponent(usrPflBtnAssocier_1, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
						.addGap(31))
				.addGroup(gl_noPflPanel.createSequentialGroup()
						.addGap(77)
						.addComponent(usrPflPaneLabel, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
						.addGap(80))
				);
		gl_noPflPanel.setVerticalGroup(
				gl_noPflPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_noPflPanel.createSequentialGroup()
						.addGap(53)
						.addComponent(usrPflPaneLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addGap(43)
						.addComponent(usrPaneLabel_1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
						.addComponent(usrPflBtnAssocier_1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
						.addGap(65))
				);
		noPflPanel.setLayout(gl_noPflPanel);

		JPanel pflPanel = new JPanel();
		pflPanel.setOpaque(false);
		profilPane.add(pflPanel, "pflPanel");

		JLabel usrPflPaneLabel_2 = new JLabel("Profils");
		usrPflPaneLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		usrPflPaneLabel_2.setForeground(SystemColor.controlDkShadow);
		usrPflPaneLabel_2.setFont(new Font("Tahoma", Font.BOLD, 25));

		usrPflSelectBox = new JComboBox();
		usrPflSelectBox.setForeground(new Color(105, 105, 105));
		usrPflSelectBox.setOpaque(false);
		usrPflSelectBox.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JSeparator separator = new JSeparator();

		usrPflIdField = new JTextField();
		usrPflIdField.setEditable(false);
		usrPflIdField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(usrPflIdField.getText().equalsIgnoreCase("Id Profil"))
					usrPflIdField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrPflIdField.getText().equals(""))
					usrFonctionField.setText("Id Profil");
			}
		});
		usrPflIdField.setText("Id Profil");
		usrPflIdField.setOpaque(false);
		usrPflIdField.setHorizontalAlignment(SwingConstants.CENTER);
		usrPflIdField.setForeground(SystemColor.controlDkShadow);
		usrPflIdField.setFont(new Font("Consolas", Font.PLAIN, 20));
		usrPflIdField.setColumns(10);
		usrPflSelectBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(usrPflSelectBox.getSelectedItem().toString()!=null) {
					ArrayList<String> pflList = new ArrayList<String>();
					int pflCount = usrPflSelectBox_1.getItemCount();
					System.out.println(pflCount);
					for(int i=0;i<pflCount;i++)
						pflList.add(usrPflSelectBox_1.getItemAt(i));
					if(pflList.contains(usrPflSelectBox.getSelectedItem().toString())) {
						usrPflBtnAssocier.setEnabled(false);
						usrPflBtnSupprimer.setEnabled(true);
					}else {
						usrPflIdField.setText(usrPflSelectBox.getSelectedItem().toString());
						usrPflBtnAssocier.setEnabled(true);
						usrPflBtnSupprimer.setEnabled(false);
					}
				}

				//pflCount.add();
				//
			}
		});

		usrPflBtnAssocier = new OvalButton("Associer", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));
		usrPflBtnAssocier.setOpaque(false);
		usrPflBtnAssocier.setFocusPainted(false);
		usrPflBtnAssocier.setBorderPainted(false);
		usrPflBtnAssocier.setContentAreaFilled(false);
		usrPflBtnAssocier.setForeground(new Color(105, 105, 105));
		usrPflBtnAssocier.setFont(new Font("Tahoma", Font.BOLD, 20));
		usrPflBtnAssocier.setForeground(SystemColor.controlDkShadow);
		usrPflBtnAssocier.setFont(new Font("Tahoma", Font.BOLD, 20));

		usrPflBtnSupprimer = new OvalButton("Supprimer", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));
		usrPflBtnSupprimer.setOpaque(false);
		usrPflBtnSupprimer.setFocusPainted(false);
		usrPflBtnSupprimer.setBorderPainted(false);
		usrPflBtnSupprimer.setContentAreaFilled(false);
		usrPflBtnSupprimer.setForeground(new Color(105, 105, 105));
		usrPflBtnSupprimer.setFont(new Font("Tahoma", Font.BOLD, 20));
		usrPflBtnSupprimer.setForeground(SystemColor.controlDkShadow);
		usrPflBtnSupprimer.setFont(new Font("Tahoma", Font.BOLD, 20));

		usrPflSelectBox_1 = new JComboBox();
		usrPflSelectBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(usrPflSelectBox_1.getItemCount()!=0) {
					usrPflIdField.setText(usrPflSelectBox_1.getSelectedItem().toString());
					usrPflBtnAssocier.setEnabled(false);
					usrPflBtnSupprimer.setEnabled(true);
				}else
					newUsrPflFields();
			}
		});
		usrPflSelectBox_1.setOpaque(false);
		usrPflSelectBox_1.setForeground(SystemColor.controlDkShadow);
		usrPflSelectBox_1.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel usrPflPaneLabel_2_1 = new JLabel("Profils Associ\u00E9s");
		usrPflPaneLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		usrPflPaneLabel_2_1.setForeground(SystemColor.controlDkShadow);
		usrPflPaneLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 25));

		JSeparator separator_1_1 = new JSeparator();

		GroupLayout gl_pflPanel = new GroupLayout(pflPanel);
		gl_pflPanel.setHorizontalGroup(
				gl_pflPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addGap(88)
						.addComponent(usrPflPaneLabel_2, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
						.addGap(82))
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(usrPflSelectBox, 0, 293, Short.MAX_VALUE)
						.addContainerGap())
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addGap(63)
						.addGroup(gl_pflPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(usrPflBtnSupprimer, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
								.addComponent(usrPflBtnAssocier, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
						.addGap(78))
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addGap(32)
						.addComponent(usrPflPaneLabel_2_1, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
						.addGap(30))
				.addGroup(Alignment.TRAILING, gl_pflPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_pflPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(separator_1_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
								.addGroup(gl_pflPanel.createSequentialGroup()
										.addComponent(usrPflIdField, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
										.addGap(1)
										.addComponent(separator, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
						.addContainerGap())
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(usrPflSelectBox_1, 0, 279, Short.MAX_VALUE)
						.addContainerGap())
				);
		gl_pflPanel.setVerticalGroup(
				gl_pflPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pflPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(usrPflPaneLabel_2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(usrPflSelectBox, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addGap(1)
						.addGroup(gl_pflPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator_1_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(usrPflPaneLabel_2_1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addGap(8)
						.addComponent(usrPflSelectBox_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(usrPflIdField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(60)
						.addComponent(usrPflBtnAssocier, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(usrPflBtnSupprimer, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
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

		OvalButton usrSBtn = new OvalButton("Nouvelle Personne ", 1, 2, new Color(191, 144, 0, 150), new Color(191, 144, 0, 80), new Color(253, 184, 155, 80), new Color(253, 184, 155, 80));
		usrSBtn.setOpaque(false);
		usrSBtn.setFocusPainted(false);
		usrSBtn.setBorderPainted(false);
		usrSBtn.setContentAreaFilled(false);
		usrSBtn.setForeground(new Color(105, 105, 105));
		usrSBtn.setFont(new Font("Tahoma", Font.BOLD, 20));

		JSeparator separator_1 = new JSeparator();

		JComboBox usrSCBox = new JComboBox(new String[] {"Lister les Personnes","Lister les Cartes","Lister les Profils associ�s"});
		usrSCBox.setForeground(new Color(105, 105, 105));
		usrSCBox.setFont(new Font("Consolas", Font.PLAIN, 25));
		GroupLayout gl_usrSearch = new GroupLayout(usrSearch);
		gl_usrSearch.setHorizontalGroup(
				gl_usrSearch.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_usrSearch.createSequentialGroup()
						.addGap(126)
						.addGroup(gl_usrSearch.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_usrSearch.createSequentialGroup()
										.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 631, GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
								.addGroup(gl_usrSearch.createSequentialGroup()
										.addGroup(gl_usrSearch.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_usrSearch.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(usrSBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(28)
														.addComponent(usrSCBox, 0, 445, Short.MAX_VALUE))
												.addComponent(usrSField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE))
										.addGap(123))))
				);
		gl_usrSearch.setVerticalGroup(
				gl_usrSearch.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_usrSearch.createSequentialGroup()
						.addContainerGap(2, Short.MAX_VALUE)
						.addGroup(gl_usrSearch.createParallelGroup(Alignment.BASELINE)
								.addComponent(usrSCBox, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(usrSBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(usrSField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(1))
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
		usrIdField.setEditable(false);

		usrIdField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(usrIdField.getText().equalsIgnoreCase("Id"))
					usrIdField.setText("");
				usrIdField.setFont(new Font("Consolas", Font.PLAIN, 15));
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrIdField.getText().equals("")) {
					usrIdField.setText("Id");
					usrIdField.setFont(new Font("Consolas", Font.PLAIN, 25));
				}
			}
		});
		usrIdField.setForeground(new Color(105, 105, 105));
		usrIdField.setText("Id");
		usrIdField.setFont(new Font("Consolas", Font.PLAIN, 25));
		usrIdField.setOpaque(false);
		usrIdField.setHorizontalAlignment(SwingConstants.CENTER);
		usrIdField.setColumns(10);

		usrPrenomField = new JTextField();
		usrPrenomField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(usrPrenomField.getText().equalsIgnoreCase("Prenom")) {
					usrPrenomField.setText("");
				}
				usrPrenomField.setFont(new Font("Consolas", Font.PLAIN, 15));
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrPrenomField.getText().equals("")) {
					usrPrenomField.setText("Prenom");
					usrPrenomField.setFont(new Font("Consolas", Font.PLAIN, 25));
				}
			}
		});
		usrPrenomField.setText("Prenom");
		usrPrenomField.setOpaque(false);
		usrPrenomField.setHorizontalAlignment(SwingConstants.CENTER);
		usrPrenomField.setForeground(SystemColor.controlDkShadow);
		usrPrenomField.setFont(new Font("Consolas", Font.PLAIN, 25));
		usrPrenomField.setColumns(10);

		usrNomField = new JTextField();
		usrNomField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(usrNomField.getText().equalsIgnoreCase("Nom"))
					usrNomField.setText("");
				usrNomField.setFont(new Font("Consolas", Font.PLAIN, 15));
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrNomField.getText().equals("")) {
					usrNomField.setText("Nom");
					usrNomField.setFont(new Font("Consolas", Font.PLAIN, 25));
				}
			}
		});
		usrNomField.setText("Nom");
		usrNomField.setOpaque(false);
		usrNomField.setHorizontalAlignment(SwingConstants.CENTER);
		usrNomField.setForeground(SystemColor.controlDkShadow);
		usrNomField.setFont(new Font("Consolas", Font.PLAIN, 25));
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
				usrDateField.setFont(new Font("Consolas", Font.PLAIN, 15));
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrDateField.getText().equals("")) {
					usrDateField.setText("Date de naissance");
					usrDateField.setFont(new Font("Consolas", Font.PLAIN, 25));
				}
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
				usrFonctionField.setFont(new Font("Consolas", Font.PLAIN, 15));
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(usrFonctionField.getText().equals("")) {
					usrFonctionField.setText("Fonction");
					usrFonctionField.setFont(new Font("Consolas", Font.PLAIN, 25));
				}
			}
		});
		usrFonctionField.setText("Fonction");
		usrFonctionField.setOpaque(false);
		usrFonctionField.setHorizontalAlignment(SwingConstants.CENTER);
		usrFonctionField.setForeground(SystemColor.controlDkShadow);
		usrFonctionField.setFont(new Font("Consolas", Font.PLAIN, 25));
		usrFonctionField.setColumns(10);

		usrBtnCreate = new OvalButton("Cr\u00E9er",1,2,new Color(191,144,0,150),new Color(191,144,0,80),new Color(253,184,155,80),new Color(253,184,155,80));

		usrBtnModifier = new OvalButton("Modifier",1,2,new Color(191,144,0,150),new Color(191,144,0,80),new Color(253,184,155,80),new Color(253,184,155,80));

		usrBtnSupprimer = new OvalButton("Supprimer",1,2,new Color(191,144,0,150),new Color(191,144,0,80),new Color(253,184,155,80),new Color(253,184,155,80));


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
		usrBtnCreate.setEnabled(true);
		usrBtnModifier.setEnabled(false);
		usrBtnSupprimer.setEnabled(false);
		usrPflBtnAssocier.setEnabled(true);
		usrPflBtnSupprimer.setEnabled(false);
		cardBtnAssocier.setEnabled(true);
		cardBtnSupprimer.setEnabled(false);

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
		usrPflCBoxList=new UserDAO().getNames("*").toArray();
		if (usrPflCBoxList.length!=0) {
			newProfil=false;
			usrPflSelectBox.setModel(new DefaultComboBoxModel( Arrays.copyOf(usrPflCBoxList, usrPflCBoxList.length,String[].class)));
			usrPflIdField.setText(usrPflSelectBox.getSelectedItem().toString());
		}else {
			newProfil=true;
			( (CardLayout) profilPane.getLayout() ).show(profilPane, "noPflPanel");
			usrPflBtnAssocier_1.setText("Cr�er Profil");
			usrPaneLabel_1.setText("Aucun Profil Disponible");
		}

		System.out.println(usrDateField.getText());
		JPanel placeManager = new JPanel();
		placeManager.setOpaque(false);
		managePanel.add(placeManager, "placeManager");

		searchManager = new JPanel();
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

		/*
		 * Panel linking
		 */
		usrBtnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(newUser) {
					//usrIdField.setEditable(false);
					usrBtnCreate.setEnabled(true);
					usrBtnModifier.setEnabled(false);
					usrBtnSupprimer.setEnabled(false);
					newUser=false;
					/*Verification validit� des champs*/
					if( !usrNomField.getText().equalsIgnoreCase("Nom") &&
							!usrPrenomField.getText().equalsIgnoreCase("Prenom") &&
							!usrFonctionField.getText().equalsIgnoreCase("Fonction")) {
						currentUser=new User("",usrNomField.getText(),usrPrenomField.getText(),usrDateField.getText(),usrFonctionField.getText());
						currentUser.setUsrId(new UserDAO().addUser(currentUser));
						System.out.println(currentUser.getUsrId());
						if(operationMessage(currentUser.getUsrId())){
							usrIdField.setFont(new Font("Consolas", Font.PLAIN, 15));
							usrIdField.setText(currentUser.getUsrId());
							usrBtnCreate.setEnabled(false);
							usrBtnModifier.setEnabled(true);
							usrBtnSupprimer.setEnabled(true);
						}
						/*managePanel.removeAll(); managePanel.add(searchManager);
						resultLabel.setText();
						managePanel.repaint(); managePanel.revalidate();
						TableModel tbm = new UserDAO().viewResults("user_usr");
						searchTable.setModel(tbm);*/
					}else {
						JOptionPane.showMessageDialog(null,"Champs invalides");
					}
					newUser=false;
				}
				else {
					//new UserDAO().add(new User("",usrNomField.getText(),usrPrenomField.getText(),usrDateField.getText(),usrFonctionField.getText()));
					usrBtnCreate.setEnabled(false);
					usrBtnModifier.setEnabled(true);
					usrBtnSupprimer.setEnabled(true);
					newUser=true;
				}
			}
		});
		usrBtnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newUsrId=new UserDAO().updateUser(new User(usrIdField.getText(),usrNomField.getText(),usrPrenomField.getText(),usrDateField.getText(),usrFonctionField.getText()),currentUser);
				if(operationMessage(newUsrId)) {
					currentUser=new UserDAO().getUser(newUsrId);
					usrIdField.setText(currentUser.getUsrId());
					usrPrenomField.setText(currentUser.getUsrPrenom());
					usrNomField.setText(currentUser.getUsrNom());
					usrDateField.setText(currentUser.getUsrBirthday());
					usrFonctionField.setText(currentUser.getUsrFonction());
				}
			}
		});
		usrBtnSupprimer.addActionListener(new ActionListener() {
			@SuppressWarnings({ })
			public void actionPerformed(ActionEvent e) {
				if(operationMessage(new UserDAO().deleteUser(new User(usrIdField.getText(),usrNomField.getText(),usrPrenomField.getText(),usrDateField.getText(),usrFonctionField.getText())))) {
					newUserFields();
					/*( (CardLayout) managePanel.getLayout() ).show(managePanel, "searchPanel");
					manageCard.show(managePanel,"searchManager");
					managePanel.repaint();managePanel.validate();
					TableModel tbm = new UserDAO().searchUser("");
					searchTable.setModel(tbm);*/
				}
			}
		});

		usrPflBtnAssocier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(operationMessage(new UserDAO().addUserProfil(usrPflIdField.getText(), currentUser))) {
					usrPflBtnAssocier.setEnabled(false);
					usrPflBtnSupprimer.setEnabled(true);
					usrPflSelectBox_1.addItem(usrPflIdField.getText());
					System.out.println(usrPflSelectBox_1.getItemAt(0));
				}
			}
		});
		usrPflBtnSupprimer.addActionListener(new ActionListener() {
			@SuppressWarnings({  })
			public void actionPerformed(ActionEvent e) {
				if(operationMessage(new UserDAO().deleteUserProfil(usrPflIdField.getText(), currentUser))) {
					if(usrPflSelectBox_1.getItemCount()>1) {
						usrPflSelectBox_1.removeItem(usrPflIdField.getText());
						usrPflIdField.setText(usrPflSelectBox_1.getSelectedItem().toString());
					}else {
						usrPflIdField.setText(usrPflSelectBox.getSelectedItem().toString());
						newUsrPflFields();
					}
				}
			}
		});
		usrPflBtnAssocier_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!newUser) {
					( (CardLayout) profilPane.getLayout() ).show(profilPane, "pflPanel");
				}else {
					JOptionPane.showMessageDialog(managePanel, "Selectionnez un profil au pr�alable");
				}
			}
		});
		cardBtnAssocier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(operationMessage(new CardDAO().add(new Card("", currentUser.getUsrId(), cardStatusCBox.isSelected()?1:0)))) {
					currentUser.setUsrCard(currentCard=new CardDAO().get(currentUser.getUsrId()));
					cardIdField.setFont(new Font("Consolas", Font.PLAIN, 15));
					cardIdField.setText(currentUser.getUsrCard().getCardId());
					cardBtnAssocier.setEnabled(false);
					cardBtnSupprimer.setEnabled(true);
				}
			}
		});
		cardBtnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(operationMessage(new CardDAO().delete(currentCard))) {
					newCardFields();
				}
			}
		});
		cardBtnAssocier_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!newUser) {
					( (CardLayout) cardPane.getLayout() ).show(cardPane, "cardPanel");
				}else {
					JOptionPane.showMessageDialog(managePanel, "Selectionnez un profil au pr�alable");
				}
			}
		});
		usrSBtn.addActionListener(new ActionListener() {
			@SuppressWarnings({  })
			public void actionPerformed(ActionEvent e) {
				newUserFields();
				/*if(!usrSField.getText().equalsIgnoreCase("")) {
					//managePanel.removeAll();
					( (CardLayout) managePanel.getLayout() ).show(managePanel, "searchPanel");
					//manageCard.show(managePanel,"searchManager");
					managePanel.repaint();managePanel.validate();
					TableModel tbm = new UserDAO().searchUser(usrSField.getText());
					searchTable.setModel(tbm);
				}*/
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



		/**
		 * Doit rester en fin
		 */
		usrSCBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				switch(usrSCBox.getSelectedItem().toString()){
				case "Lister les Personnes":{
					( (CardLayout) managePanel.getLayout() ).show(managePanel, "searchManager");
					TableModel tbm = new UserDAO().searchUser("");
					searchTable.setModel(tbm);
					break;
				}
				case "Lister les Cartes":{
					( (CardLayout) managePanel.getLayout() ).show(managePanel, "searchManager");
					TableModel tbm = new CardDAO().searchCard("");
					searchTable.setModel(tbm);
					break;
				}
				case "Lister les Profils associ�s":{
					( (CardLayout) managePanel.getLayout() ).show(managePanel, "searchManager");
					TableModel tbm = new UserDAO().searchUserProfil("");
					searchTable.setModel(tbm);
					break;
				}
				}
			}
		});
		searchTable.addMouseListener(new MouseAdapter() {
			@SuppressWarnings({  })
			@Override
			public void mouseClicked(MouseEvent e) {
				int ligne = searchTable.getSelectedRow();
				String idKey = searchTable.getModel().getValueAt(ligne,0).toString();
				String idName = searchTable.getColumnName(0).toLowerCase();
				System.out.println(idName);
				//userManager
				if(idName.equals("usr_id") || idName.equals("card_id") || idName.equals("usrpfl_id")){
					newUserFields();
					if(idName.equals("usr_id"))
						idKey = searchTable.getModel().getValueAt(ligne,0).toString();
					else if(idName.equals("card_id"))
						idKey = searchTable.getModel().getValueAt(ligne,1).toString();
					else if(idName.equals("usrpfl_id"))
						idKey = searchTable.getModel().getValueAt(ligne,2).toString();
					usrBtnCreate.setEnabled(false);
					usrBtnModifier.setEnabled(true);
					usrBtnSupprimer.setEnabled(true);
					newUser=false;
					currentUser= new UserDAO().getUser(idKey);
					( (CardLayout) managePanel.getLayout() ).show(managePanel, "userManager");
					( (CardLayout) profilPane.getLayout() ).show(profilPane, "noPflPanel");
					//Filling User fields
					usrIdField.setText(currentUser.getUsrId());usrIdField.setFont(new Font("Consolas", Font.PLAIN, 15));
					usrPrenomField.setText(currentUser.getUsrPrenom());usrPrenomField.setFont(new Font("Consolas", Font.PLAIN, 15));
					usrNomField.setText(currentUser.getUsrNom());usrNomField.setFont(new Font("Consolas", Font.PLAIN, 15));
					usrDateField.setText(currentUser.getUsrBirthday());usrDateField.setFont(new Font("Consolas", Font.PLAIN, 15));
					usrFonctionField.setText(currentUser.getUsrFonction());usrFonctionField.setFont(new Font("Consolas", Font.PLAIN, 15));
					//Filling Card fields
					currentCard= new CardDAO().get(currentUser.getUsrId());
					if(currentCard!=null) {
						( (CardLayout) cardPane.getLayout() ).show(cardPane, "cardPanel");
						//System.out.println(currentCard.getCardId());
						cardStatusCBox.setSelected(1==currentCard.getCardStatus()?true:false);
						cardIdField.setText(currentCard.getCardId());cardIdField.setFont(new Font("Consolas", Font.PLAIN, 15));
						cardBtnAssocier.setEnabled(false);
						cardBtnSupprimer.setEnabled(true);
					}else {
						( (CardLayout) cardPane.getLayout() ).show(cardPane, "noCardPanel");
						cardBtnAssocier.setEnabled(true);
						cardBtnSupprimer.setEnabled(false);
					}
					//Filling Profil fields
					//getting the associated profiles
					Object[] dummy = new UserDAO().getNames("*").toArray();
					usrPflCBoxList=new UserDAO().getNames(currentUser.getUsrId()).toArray();
					if (dummy.length!=0) {
						newProfil=false;
						usrPflSelectBox.setModel(new DefaultComboBoxModel( Arrays.copyOf(dummy, dummy.length,String[].class)));
					}else {
						newProfil=true;
						( (CardLayout) profilPane.getLayout() ).show(profilPane, "noPflPanel");
						usrPflBtnAssocier_1.setText("Cr�er Profil");
						usrPaneLabel_1.setText("Aucun Profil Disponible");
					}
					if(usrPflCBoxList.length!=0) {
						( (CardLayout) profilPane.getLayout() ).show(profilPane, "pflPanel");
						usrPflSelectBox_1.setModel(new DefaultComboBoxModel<String>( Arrays.copyOf(usrPflCBoxList, usrPflCBoxList.length,String[].class)));
						usrPflIdField.setText((String)usrPflSelectBox_1.getSelectedItem());
						usrPflBtnAssocier.setEnabled(false);
						usrPflBtnSupprimer.setEnabled(true);
					}else {
						( (CardLayout) profilPane.getLayout() ).show(profilPane, "noPflPanel");
						usrPflIdField.setText((String)usrPflSelectBox.getSelectedItem());
						usrPflBtnAssocier.setEnabled(true);
						usrPflBtnSupprimer.setEnabled(false);
					}
				}

				//pflNameField.setText(currentProfil.getPflDesc());
				/*if(currentProfilList.size()!=0) {
					currentProfil=new ProfilDAO().get(pflSelectBox.getSelectedItem().toString());
				}else {
					JOptionPane.showMessageDialog(null, "Aucun profil disponible\n Cr�ez un Nouveau profil");
				}*/
				//( (CardLayout) managePanel.getLayout() ).show(managePanel, "userManager");
				//}
			}
		});
		searchManager.setLayout(gl_searchManager);
	}
	public boolean operationMessage(int opResult) {
		if(opResult!=0) {
			JOptionPane.showMessageDialog(this, "Op�ration Effectu�e");
			return true;
		}else {
			JOptionPane.showMessageDialog(this, "Op�ration Non Effectu�e","Erreur",JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	public boolean operationMessage(Object opResult) {
		if(opResult!=null) {
			JOptionPane.showMessageDialog(this, "Op�ration Effectu�e");
			return true;
		}else {
			JOptionPane.showMessageDialog(this, "Op�ration Non Effectu�e","Erreur",JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	public void newUserFields() {
		//reseting to default values
		usrIdField.setText("Id");usrIdField.setFont(new Font("Consolas", Font.PLAIN, 20));
		usrPrenomField.setText("Prenom");usrPrenomField.setFont(new Font("Consolas", Font.PLAIN, 20));
		usrNomField.setText("Nom");usrNomField.setFont(new Font("Consolas", Font.PLAIN, 20));
		usrDateField.setText("Date de Naissance");usrDateField.setFont(new Font("Consolas", Font.PLAIN, 20));
		usrFonctionField.setText("Fonction");usrFonctionField.setFont(new Font("Consolas", Font.PLAIN, 20));
		usrBtnCreate.setEnabled(true);
		usrBtnModifier.setEnabled(false);
		usrBtnSupprimer.setEnabled(false);
		newCardFields();newUsrPflFields();
		currentUser=null;newUser=true;
	}
	public void newCardFields() {
		cardIdField.setText("Id carte");cardIdField.setFont(new Font("Consolas", Font.PLAIN, 20));
		cardStatusLabel.setText("Active");
		cardStatusCBox.setSelected(true);
		cardBtnAssocier.setEnabled(true);
		cardBtnSupprimer.setEnabled(false);
		( (CardLayout) cardPane.getLayout() ).show(cardPane, "noCardPanel");
		newCard=true;currentCard=null;
	}
	public void newUsrPflFields() {
		Object[] dummy = new UserDAO().getNames("*").toArray();
		usrPflSelectBox_1.removeAllItems();
		System.out.println("____________il y a"+usrPflSelectBox_1.getItemCount());
		if (dummy.length!=0) {
			newProfil=false;
			usrPflSelectBox.setModel(new DefaultComboBoxModel( Arrays.copyOf(dummy, dummy.length,String[].class)));
		}else {
			newProfil=true;
			( (CardLayout) profilPane.getLayout() ).show(profilPane, "noPflPanel");
			usrPflBtnAssocier_1.setText("Cr�er Profil");
			usrPaneLabel_1.setText("Aucun Profil Disponible");
		}
		( (CardLayout) profilPane.getLayout() ).show(profilPane, "noPflPanel");
		usrPflIdField.setText((String)usrPflSelectBox.getSelectedItem());
		usrPflBtnAssocier.setEnabled(true);
		usrPflBtnSupprimer.setEnabled(false);
	}
}
@SuppressWarnings("serial")
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