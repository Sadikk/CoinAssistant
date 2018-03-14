package coinAssistant.ui;

import javax.swing.JFrame;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import coinAssistant.core.BinanceConnector;
import coinAssistant.core.Pattern;
import coinAssistant.core.ReflectionHelper;

public class MainWindow extends JFrame {
	private JPanel descriptionContainer;
    private JPanel interpretationContainer;
    private JPanel trendContainer;
    private JPanel graphContainer;
    private JPanel actuatorContainer;
    private JPanel mainContainer;
    private JLabel descriptionTitle;
    private JLabel interpretationTitle;
    private JLabel trendTitle;
    private JLabel actuatorTitle;
    private JLabel graphTitle;
    private JLabel timeDetection;
    private JLabel forcePattern;
    private JLabel lengthPattern;
    private JLabel comparison;
    private JLabel explanationInterpretation;
    private JLabel conclusion;
    private JLabel sumIndicator;
    private JLabel globalConclusion;
    private JLabel advice;
    private JLabel binarySetting;
    private JLabel continuousSetting;
    

	public MainWindow(String nom, int width, int height) {
	    super(nom);
	    setSize(width,height);
	    setLocation(100,25);
	    
	    descriptionContainer = new JPanel();
	    descriptionContainer.setPreferredSize(new Dimension(300, 200)); 
	    descriptionContainer.setPreferredSize(descriptionContainer.getPreferredSize());
	    descriptionContainer.setMinimumSize(descriptionContainer.getMinimumSize());
	    descriptionTitle = new JLabel("Description du pattern selectionne");
	    descriptionTitle.setBounds(75,10,300,20);
	    timeDetection = new JLabel("-temps auquel le pattern est detecte");        
	    timeDetection.setBounds(25,50,275,25);
	    forcePattern = new JLabel("-force du pattern");        
	    forcePattern.setBounds(25,75,275,25);
	    lengthPattern = new JLabel("-longueur du pattern");        
	    lengthPattern.setBounds(25,100,275,25);
	    comparison = new JLabel("-caracteristiques de comparaison des differents indicateurs");        
	    comparison.setBounds(25,125,275,25);
	    
	    descriptionContainer.add(descriptionTitle);
	    descriptionContainer.add(timeDetection);
	    descriptionContainer.add(forcePattern);
	    descriptionContainer.add(lengthPattern);
	    descriptionContainer.add(comparison);
	    
	    
	    interpretationContainer = new JPanel();
	    interpretationContainer.setPreferredSize(new Dimension(300, 200)); 
	    interpretationContainer.setPreferredSize(interpretationContainer.getPreferredSize());
	    interpretationContainer.setMinimumSize(interpretationContainer.getMinimumSize());
	    interpretationTitle = new JLabel();
	    interpretationTitle.setText("Interpretation du pattern");
	    interpretationTitle.setBounds(75,10,300,20);
	    explanationInterpretation = new JLabel("-explication de l'interpretation du pattern selectionne ou du dernier pattern");        
	    explanationInterpretation.setBounds(25,50,275,25);
	    conclusion = new JLabel("-conclusion sur la tendance soupconnee");        
	    conclusion.setBounds(25,75,275,25);
	    
	    interpretationContainer.add(interpretationTitle);
	    interpretationContainer.add(explanationInterpretation);
	    interpretationContainer.add(conclusion);
	    
	    
	    trendContainer = new JPanel();
	    trendContainer.setPreferredSize(new Dimension(300, 200)); 
	    trendContainer.setPreferredSize( trendContainer.getPreferredSize());
	    trendContainer.setMinimumSize(trendContainer.getMinimumSize());
	    trendTitle = new JLabel();
	    trendTitle.setText("Tendance globale");
	    trendTitle.setBounds(75,10,300,20);
	    sumIndicator = new JLabel("-somme de tous les indicateurs releves");        
	    sumIndicator.setBounds(25,50,275,25);
	    globalConclusion = new JLabel("-conclusion sur la tendance globale");        
	    globalConclusion.setBounds(25,75,275,25);
	    advice = new JLabel("-conseil a l'achat ou a la vente");        
	    advice.setBounds(25,100,275,25);
	    
	    trendContainer.add(trendTitle);
	    trendContainer.add(sumIndicator);
	    trendContainer.add(globalConclusion);
	    trendContainer.add(advice);
	    
	    
	    actuatorContainer = new JPanel();
	    actuatorContainer.setPreferredSize(new Dimension(450, 400));
	    actuatorTitle = new JLabel("Actionneurs de l'interface");
	    actuatorTitle.setBounds(75,10,300,20);
	    binarySetting= new JLabel("-reglages binaires : affichage d'elements on/off");        
	    binarySetting.setBounds(25,50,275,25);
	    continuousSetting = new JLabel("-reglage continu : taille de l'intervalle de temps considere etc");        
	    continuousSetting.setBounds(25,75,275,25);
	    
	    actuatorContainer.add(actuatorTitle);
	    actuatorContainer.add(binarySetting);
	    actuatorContainer.add(continuousSetting);
	    
	    
	    graphContainer = new JPanel();
	    graphContainer.setPreferredSize(new Dimension(650, 400)); 
	    graphTitle = new JLabel("Courbe du cours");
	    graphTitle.setBounds(75,10,300,20);
	    
	    graphContainer.add(graphTitle);
	  
	    mainContainer = new JPanel();
	    mainContainer.setLayout(new GridBagLayout());
	 
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0; 
	    gbc.gridheight = 5;
	    gbc.gridwidth = 6;
	    mainContainer.add(graphContainer, gbc);
	    
	    gbc.gridx = 7; 
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    gbc.gridheight = 5;
	    gbc.anchor = GridBagConstraints.CENTER;
	    mainContainer.add(actuatorContainer, gbc);
	    
	    gbc.gridx = 0; 
	    gbc.gridy = 6;
	    gbc.gridheight = 4;
	    gbc.gridwidth = 3;
	    mainContainer.add(descriptionContainer, gbc);
	    
	    gbc.gridx = 3; 
	    gbc.gridy = 6;
	    gbc.gridheight = 4;
	    gbc.gridwidth = 3;
	    mainContainer.add(interpretationContainer, gbc);
	    
	    gbc.gridx = 6; 
	    gbc.gridy = 6;
	    gbc.gridheight = 4;
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    mainContainer.add(trendContainer, gbc);
	    
	    setContentPane(mainContainer);
	    
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
    
    }

	public static void main(String[] args) { 
		MainWindow mW = new MainWindow("CoinAssistant",1200,700);
		
		//Binance connection test : ok
		//BinanceConnector connector = new BinanceConnector();
		//System.out.println(connector.getCandlesticks(""));
		
		try {
			Class[] classes = ReflectionHelper.getClasses("coinAssistant.core.candlesticks");
			//need to add
			//ReflectionHelper.getClasses("coinAssistant.core.charts.ascending");
			//ReflectionHelper.getClasses("coinAssistant.core.charts.descending");
			for (Class c : classes)
				if (Pattern.class.isAssignableFrom(c)) //pattern = superclass
					System.out.println(c.getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


