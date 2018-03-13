package coinAssistant.ui;

import javax.swing.JFrame;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import coinAssistant.core.BinanceConnector;

public class MainWindow extends JFrame {
	private JPanel descriptionContainer;
    private JPanel interpretationContainer;
    private JPanel trendContainer;
    private JPanel graphContainer;
    private JPanel actuatorContainer;
    private JPanel mainContainer;
   /* private JLabel etiquette1;
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
    private JLabel champs33;*/
    

	public MainWindow(String nom, int width, int height) {
	    super(nom);
	    setSize(width,height);
	    setLocation(100,25);
	    
	    descriptionContainer = new JPanel();
	    descriptionContainer.setPreferredSize(new Dimension(300, 200)); 
	    descriptionContainer.setPreferredSize(descriptionContainer.getPreferredSize());
	    descriptionContainer.setMinimumSize(descriptionContainer.getMinimumSize());
	    
	    interpretationContainer = new JPanel();
	    interpretationContainer.setPreferredSize(new Dimension(300, 200)); 
	    interpretationContainer.setPreferredSize(interpretationContainer.getPreferredSize());
	    interpretationContainer.setMinimumSize(interpretationContainer.getMinimumSize());
	    
	    trendContainer = new JPanel();
	    trendContainer.setPreferredSize(new Dimension(300, 200)); 
	    trendContainer.setPreferredSize( trendContainer.getPreferredSize());
	    trendContainer.setMinimumSize(trendContainer.getMinimumSize());
	    
	    actuatorContainer = new JPanel();
	    actuatorContainer.setPreferredSize(new Dimension(450, 400)); 
	    
	    graphContainer = new JPanel();
	    graphContainer.setPreferredSize(new Dimension(650, 400)); 
	  
	    mainContainer = new JPanel();
	    mainContainer.setLayout(new GridBagLayout());
	 
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = gbc.gridy = 0; 
	    
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.weightx = 1;
	    gbc.weighty = 1;
	    
	    mainContainer.add(descriptionContainer, gbc);
	    
	    setContentPane(mainContainer);
	    
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    
	    
	    
	    /*etiquette1 = new JLabel();
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
	    
	
	    etiquette2 = new JLabel();
	    etiquette2.setText("Interprétation du pattern");
	    etiquette2.setBounds(75,10,300,20);
	    champs21 = new JLabel("-explication de l'interprétation du pattern sélectionné ou du dernier pattern");        
	    champs21.setBounds(25,50,275,25);
	    champs22 = new JLabel("-conclusion sur la tendance soupçonnée");        
	    champs22.setBounds(25,75,275,25);
	    
	    etiquette3 = new JLabel();
	    etiquette3.setText("Tendance globale");
	    etiquette3.setBounds(75,10,300,20);
	    champs31 = new JLabel("-somme de tous les indicateurs relevés");        
	    champs31.setBounds(25,50,275,25);
	    champs32 = new JLabel("-conclusion sur la tendance globale");        
	    champs32.setBounds(25,75,275,25);
	    champs33 = new JLabel("-conseil à l'achat où à la vente");        
	    champs33.setBounds(25,100,275,25);
	    
	    etiquette4 = new JLabel();
	    etiquette4.setText("Actionneurs de l'interface");
	    etiquette4.setBounds(75,10,300,20);*/
    
    }

	public static void main(String[] args) { 
		MainWindow mW = new MainWindow("CoinAssistant",1200,700);
		BinanceConnector connector = new BinanceConnector();
		System.out.println(connector.getCandlesticks(""));
	}
}


