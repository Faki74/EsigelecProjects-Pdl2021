
-------------------------------------------------------------
--*********************Création des Utilisateurs*******************
--insert into user_usr values(idgenerate('joze'||'.'||'odalt','plc'),'jo', 'dalton','22/12/2018','Joueur');
insert into user_usr values(idgenerate('jean.michel','user_usr',1),'Michel','Jean',To_char(To_DATE('22/12/1985','DD/MM/YYYY'),'DD/MM/YYYY'),'Administrateur');
insert into user_usr values(idgenerate('gael.menil','user_usr',1),'Menil','Gael',To_char(To_DATE('04/12/1945','DD/MM/YYYY'),'DD/MM/YYYY'),'Administrateur');
insert into user_usr values(idgenerate('olivier.mesrine','user_usr',1),'Mesrine','Olivier',To_char(To_DATE('02/01/1968','DD/MM/YYYY'),'DD/MM/YYYY'),'Administrateur');
insert into user_usr values(idgenerate('xiu.shen','user_usr',1),'Shen','Xiu',To_char(To_DATE('14/01/1995','DD/MM/YYYY'),'DD/MM/YYYY'),'Etudiant');
insert into user_usr values(idgenerate('sunday.majrahji','user_usr',1),'Marahji','Sunday',To_char(To_DATE('08/12/1998','DD/MM/YYYY'),'DD/MM/YYYY'),'Etudiant');
insert into user_usr values(idgenerate('cyrille.attolou','user_usr',1),'Attolou','Cyrille',To_char(To_DATE('25/07/1999','DD/MM/YYYY'),'DD/MM/YYYY'),'Etudiant');
insert into user_usr values(idgenerate('olivier.oyoki','user_usr',1),'Oyoki','Olivier',To_char(To_DATE('29/08/1789','DD/MM/YYYY'),'DD/MM/YYYY'),'Administrateur');
-------------------------------------------------------------
--********************Création des horaires********************
insert into horaire_hr values(idgenerate('Horaire Accessible','horaire_hr',0),To_char(To_DATE('05:30:00','hh24:mi:ss'),'hh24:mi:ss'),To_char(To_DATE('21:30:00','hh24:mi:ss'),'hh24:mi:ss'));
insert into horaire_hr values(idgenerate('Horaire Innaccessible','horaire_hr',0),To_char(To_DATE('21:30:00','hh24:mi:ss'),'hh24:mi:ss'),To_char(To_DATE('05:30:00','hh24:mi:ss'),'hh24:mi:ss'));
insert into horaire_hr values(idgenerate('Matinee','horaire_hr',0),To_char(To_DATE('06:30:00','hh24:mi:ss'),'hh24:mi:ss'),To_char(To_DATE('12:30:00','hh24:mi:ss'),'hh24:mi:ss'));
insert into horaire_hr values(idgenerate('Apres-midi','horaire_hr',0),To_char(To_DATE('13:30:00','hh24:mi:ss'),'hh24:mi:ss'),To_char(To_DATE('19:30:00','hh24:mi:ss'),'hh24:mi:ss'));
insert into horaire_hr values(idgenerate('Journee Continue','horaire_hr',0),To_char(To_DATE('06:30:00','hh24:mi:ss'),'hh24:mi:ss'),To_char(To_DATE('15:30:00','hh24:mi:ss'),'hh24:mi:ss'));
----------------------------------------------------------------
--******************Création des types d'accès****************
insert into type_acces_acstype values(acstype_seq.nextval,'Parking');
insert into type_acces_acstype values(acstype_seq.nextval,'Acces Zone Commune');
insert into type_acces_acstype values(acstype_seq.nextval,'Sortie de secours');
insert into type_acces_acstype values(acstype_seq.nextval,'Acces Zone Privee');
---------------------------------------------------------------
--************************Affichage Tables**********************
select * from user_usr;
select * from type_acces_acstype;
select * from horaire_hr;
commit;
---------------------------------------------------------------
---------------------------------------------------------------
--*******************Création des Cartes Leo*****************
insert into cardLeo_card values(idgenerate('Leo'||CARD_SEQ.nextval,'cardLeo_card',1),'jean.michel@pdlesig',1);
insert into cardLeo_card values(idgenerate('Leo'||CARD_SEQ.nextval,'cardLeo_card',1),'gael.menil@pdlesig',1);
insert into cardLeo_card values(idgenerate('Leo'||CARD_SEQ.nextval,'cardLeo_card',1),'olivier.mesrine@pdlesig',0);
----------------------------------------------------------------
--*********************Création des Lieux*******************
insert into place_plc values(idgenerate('Amphi Charliat','place_plc',0),'Nord RC',4,'Horaire Accessible',1);
insert into place_plc values(idgenerate('Amphi Maxwell','place_plc',0),'Nord RJ',4,'Horaire Accessible',1);
insert into place_plc values(idgenerate('Direction','place_plc',0),'Ouest Niveau1',2,'Matinee',1);
insert into place_plc values(idgenerate('Local Entretien','place_plc',0),'Nord RC',2,'Journee Continue',1);
insert into place_plc values(idgenerate('Cantine','place_plc',0),'Ouest RC',2,'Journee Continue',1);
insert into place_plc values(idgenerate('Parking','place_plc',0),'Est',3,'Horaire Accessible',1);
insert into place_plc values(idgenerate('Parking Direction','place_plc',0),'Sud',3,'Horaire Accessible',1);
commit;
select * from place_plc;
--------------------------------------------------------------
--******************Création des Access*************************
--insert into acces_acs values(idgenerate(''),'desc',plc_id,acstype)
insert into acces_acs values(idgenerate('Entree Parking','acces_acs',0),'Acces Parking','Parking',1);
insert into acces_acs values(idgenerate('Entree Charliat','acces_acs',0),'Acces Amphi','Amphi Charliat',2);
insert into acces_acs values(idgenerate('Sortie Charliat','acces_acs',0),'Acces Amphi','Amphi Charliat',2);
insert into acces_acs values(idgenerate('Entree Maxwell','acces_acs',0),'Acces Amphi','Amphi Maxwell',2);
insert into acces_acs values(idgenerate('Sortie Maxwell','acces_acs',0),'Acces Amphi','Amphi Maxwell',2);
insert into acces_acs values(idgenerate('Entree-Sortie','acces_acs',0),'Acces Direction','Direction',2);
insert into acces_acs values(idgenerate('Entree-Sortie','acces_acs',0),'Acces Cantine','Cantine',2);
insert into acces_acs values(idgenerate('Entree-Sortie','acces_acs',0),'Acces Local Entretien','Local Entretien',2);
insert into acces_acs values(idgenerate('Sortie Secours','acces_acs',0),'Acces Amphi','Amphi Charliat',3);
----------------------------------------------------------------
--******************Création des Profils************************
insert into profil_pfl(pfl_id,pfl_plc_id,pfl_hr_id) values(idgenerate('Profil Administration','profil_pfl',0),'Direction','Horaire Accessible');
insert into profil_pfl(pfl_id,pfl_plc_id,pfl_hr_id) values(idgenerate('Profil Etudiant','profil_pfl',0),'Amphi Charliat','Matinee');
insert into profil_pfl(pfl_id,pfl_plc_id,pfl_hr_id) values(idgenerate('Profil Etudiant','profil_pfl',0),'Amphi Maxwell','Matinee');
insert into profil_pfl(pfl_id,pfl_plc_id,pfl_hr_id) values(idgenerate('Profil Commun','profil_pfl',0),'Parking','Horaire Accessible');
----------------------------------------------------------------
--************************Affichage Tables**********************
select * from cardLeo_card;
select * from place_plc;
select * from acces_acs;
select * from profil_pfl;
commit;
----------------------------------------------------------------
----------------------------------------------------------------
--*************Association des Profils aux Users****************
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Administration','jean.michel@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Administration','gael.menil@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Administration','olivier.mesrine@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Etudiant','xiu.shen@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Etudiant','sunday.majrahji@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Etudiant','cyrille.attolou@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Administration','olivier.oyoki@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Etudiant','xiu.shen@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Etudiant','sunday.majrahji@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Etudiant','cyrille.attolou@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Etudiant','xiu.shen@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Etudiant','sunday.majrahji@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Etudiant','cyrille.attolou@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Commun','jean.michel@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Commun','gael.menil@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Commun','olivier.mesrine@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Commun','xiu.shen@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Commun','sunday.majrahji@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Commun','cyrille.attolou@pdlesig');
insert into userprofil_usrpfl values(usrpfl_seq.nextval,'Profil Commun','olivier.oyoki@pdlesig');
----------------------------------------------------------------
--************************Affichage Tables**********************
select * from userprofil_usrpfl;
commit;
---------------------------------------------------------------*/
/*@SuppressWarnings("serial")
		public class GenBase2 extends JPanel{
			public static void main(String[] args) {
				GenBase2 panel = new GenBase2();
				JFrame frame = new JFrame();
				frame.setSize(new Dimension(1024,720));
				frame.setContentPane(panel);
				frame.pack();
				frame.setVisible(true);

			}
			public GenBase2() {
				setLayout(null);
				setPreferredSize(new Dimension(897, 539));
				setMinimumSize(new Dimension(768,576));
				setSize(this.getPreferredSize());
				JLabel lblNewLabel = new JLabel("",JLabel.CENTER);
				lblNewLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/adminBase2.png")));
				lblNewLabel.setBounds(0, 0, 847, 529);
				add(lblNewLabel);
			}
		}*/
		//String imagesDir="images/";
				//ImageIcon img=new ImageIcon(imagesDir+"adminBase.png");