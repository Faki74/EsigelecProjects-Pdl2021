package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class WelcomeFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtBhhkbkbkuh;
	private JTextField idField;
	private JPasswordField pwdField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeFrame frame = new WelcomeFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WelcomeFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addStuffToFrame();
		
		
	}
	private void addStuffToFrame() {

		//awtPanel.add(new JLabel("YEEEEESSS"));
		//awtPanel.add(button);
		BasePanel backPane = new BasePanel();
		setBounds(100, 100, 800, 512);setSize(backPane.getSize());
		getLayeredPane().add(backPane,JLayeredPane.DEFAULT_LAYER);

		JPanel awtPanel = new JPanel() {
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
		awtPanel.setBackground(new Color(0,0,0,1)); 

		OvalButton connectBtn = new OvalButton("Connect",1,2,new Color(191,144,0,150),new Color(191,144,0,80),new Color(253,184,155,80),new Color(253,184,155,80));
		connectBtn.setOpaque(false);connectBtn.setBorderThickness(0);
		connectBtn.setFocusPainted(false);
		connectBtn.setBorderPainted(false);
		connectBtn.setContentAreaFilled(false);


		awtPanel.setSize(backPane.getSize());

		getLayeredPane().add(awtPanel,JLayeredPane.PALETTE_LAYER);

		//JButton button = new JButton("New button");

		idField = new JTextField();
		idField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(idField.getText().equalsIgnoreCase("Identifiant")) {
					idField.setText("");
					idField.setHorizontalAlignment(SwingConstants.LEADING);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(idField.getText().equals("")) {
					idField.setText("Identifiant");
					idField.setHorizontalAlignment(SwingConstants.CENTER);
				}
			}
		});
		idField.setHorizontalAlignment(SwingConstants.CENTER);
		idField.setFont(new Font("Consolas", Font.PLAIN, 20));
		idField.setText("Identifiant");
		idField.setForeground(new Color(105, 105, 105));
		idField.setOpaque(false);
		idField.setColumns(10);
		
		pwdField = new JPasswordField("Mot de Passe");
		pwdField.setForeground(new Color(105, 105, 105));
		pwdField.setFont(new Font("Consolas", Font.PLAIN, 20));
		pwdField.setHorizontalAlignment(SwingConstants.CENTER);
		pwdField.setEchoChar((char)0);
		pwdField.setOpaque(false);

		JCheckBox mdpShowBox = new JCheckBox("Show");
		mdpShowBox.setOpaque(false);
		mdpShowBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mdpShowBox.isSelected()) {
					pwdField.setEchoChar((char)0);
					mdpShowBox.setText("Hide");
				}
				else if(!new String(pwdField.getPassword()).equalsIgnoreCase("Mot de Passe")){
					pwdField.setEchoChar('*');
					mdpShowBox.setText("Show");
				}
			}
		});
		pwdField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if( new String(pwdField.getPassword()).equalsIgnoreCase("Mot de Passe")) {
					pwdField.setText("");pwdField.setHorizontalAlignment(SwingConstants.LEADING);
					if(!mdpShowBox.isSelected())
						pwdField.setEchoChar('*');
				}

			}
			@Override
			public void focusLost(FocusEvent e) {
				if( new String(pwdField.getPassword()).equalsIgnoreCase("")) {
					pwdField.setText("Mot de Passe");pwdField.setEchoChar((char)0);
					pwdField.setHorizontalAlignment(SwingConstants.CENTER);
						
				}
			}});
		JLabel authLabel = new JLabel("Authentification");
		authLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		authLabel.setForeground(new Color(105, 105, 105));
		authLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("");

		GroupLayout gl_awtPanel = new GroupLayout(awtPanel);
		gl_awtPanel.setHorizontalGroup(
			gl_awtPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_awtPanel.createSequentialGroup()
					.addGap(352)
					.addComponent(connectBtn, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(373, Short.MAX_VALUE))
				.addGroup(gl_awtPanel.createSequentialGroup()
					.addGap(229)
					.addGroup(gl_awtPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_awtPanel.createSequentialGroup()
							.addComponent(pwdField, GroupLayout.PREFERRED_SIZE, 407, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(mdpShowBox, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
							.addGap(139))
						.addGroup(gl_awtPanel.createSequentialGroup()
							.addComponent(idField, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
							.addGap(246))))
				.addGroup(gl_awtPanel.createSequentialGroup()
					.addGap(288)
					.addComponent(authLabel, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
					.addGap(308))
				.addGroup(gl_awtPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnNewRadioButton)
					.addContainerGap(859, Short.MAX_VALUE))
		);
		gl_awtPanel.setVerticalGroup(
			gl_awtPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_awtPanel.createSequentialGroup()
					.addGroup(gl_awtPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_awtPanel.createSequentialGroup()
							.addGap(30)
							.addComponent(authLabel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addGap(42)
							.addComponent(idField, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
							.addGap(40)
							.addGroup(gl_awtPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(pwdField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
								.addComponent(mdpShowBox))
							.addGap(40)
							.addComponent(connectBtn, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_awtPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(rdbtnNewRadioButton)))
					.addContainerGap(183, Short.MAX_VALUE))
		);
		awtPanel.setLayout(gl_awtPanel);
		
		}
	}