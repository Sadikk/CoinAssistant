package coinAssistant.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class MainWindow extends JFrame {
	private JPanel conteneur1;
    private JPanel conteneur2;
    private JPanel conteneur3;
    private JPanel conteneur4;
    private JPanel conteneurPrincipal;
    private JLabel etiquette1;
    private JLabel etiquette2;
    private JLabel etiquette3;
    private JLabel etiquette4;
    private JLabel champs11;
    private JLabel champs12;
    private JLabel champs13;
    private JLabel champs14;
    private JLabel champs21;
    private JLabel champs22;
    private JLabel champs31;
    private JLabel champs32;
    private JLabel champs33;
    

public MainWindow(String nom, int width, int height) {
    super(nom);
	setLayout(null);
    setSize(width,height);
    setLocation(100,25);
    
    etiquette1 = new JLabel();
    etiquette1.setText("Description du pattern séléctionné");
    etiquette1.setBounds(75,10,300,20);
    champs11 = new JLabel("-temps auquel le pattern est détecté");        
    champs11.setBounds(25,50,275,25);
    champs12 = new JLabel("-force du pattern");        
    champs12.setBounds(25,75,275,25);
    champs13 = new JLabel("-longueur du pattern");        
    champs13.setBounds(25,100,275,25);
    champs14 = new JLabel("-caractéristiques de comparaison des différents indicateurs");        
    champs14.setBounds(25,125,275,25);
    
    conteneur1 = new JPanel();
    conteneur1.setLayout(null);
    conteneur1.add(etiquette1);
    conteneur1.add(champs11);
    conteneur1.add(champs12);
    conteneur1.add(champs13);
    conteneur1.add(champs14);
    conteneur1.setBounds(15,450,300,220);
    
    etiquette2 = new JLabel();
    etiquette2.setText("Interprétation du pattern");
    etiquette2.setBounds(75,10,300,20);
    champs21 = new JLabel("-explication de l'interprétation du pattern sélectionné ou du dernier pattern");        
    champs21.setBounds(25,50,275,25);
    champs22 = new JLabel("-conclusion sur la tendance soupçonnée");        
    champs22.setBounds(25,75,275,25);
    
    conteneur2 = new JPanel();
    conteneur2.setLayout(null);
    conteneur2.add(etiquette2);
    conteneur2.add(champs21);
    conteneur2.add(champs22);
    conteneur2.setBounds(400,450,300,220);
    
    etiquette3 = new JLabel();
    etiquette3.setText("Tendance globale");
    etiquette3.setBounds(75,10,300,20);
    champs31 = new JLabel("-somme de tous les indicateurs relevés");        
    champs31.setBounds(25,50,275,25);
    champs32 = new JLabel("-conclusion sur la tendance globale");        
    champs32.setBounds(25,75,275,25);
    champs33 = new JLabel("-conseil à l'achat où à la vente");        
    champs33.setBounds(25,100,275,25);
    
    conteneur3 = new JPanel();
    conteneur3.setLayout(null);
    conteneur3.add(etiquette3);
    conteneur3.add(champs31);
    conteneur3.add(champs32);
    conteneur3.add(champs33);
    conteneur3.setBounds(800,450,300,220);
    
    etiquette4 = new JLabel();
    etiquette4.setText("Actionneurs de l'interface");
    etiquette4.setBounds(75,10,300,20);
    
    conteneur4 = new JPanel();
    conteneur4.setLayout(null);
    conteneur4.add(etiquette4);
    conteneur4.setBounds(800,50,300,220);
   
    conteneurPrincipal = new JPanel();
    conteneurPrincipal.setLayout(null);
    conteneurPrincipal.add(conteneur2);
    conteneurPrincipal.add(conteneur1);
    conteneurPrincipal.add(conteneur3);
    conteneurPrincipal.add(conteneur4);
    setContentPane(conteneurPrincipal);
    
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    
    }
public static void main(String[] args) { 
	MainWindow mW = new MainWindow("CoinAssistant",1200,700);
	}

}


