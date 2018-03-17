package coinAssistant.ui;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import coinAssistant.core.BinanceConnector;
import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;
import coinAssistant.core.ReflectionHelper;

public class MainWindow extends JFrame implements ItemListener{
	private Box descriptionContainer;
    private Box interpretationContainer;
    private Box trendContainer;
    private PaneChart graphContainer;
    private Box actuatorContainer;
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
    
    private BinanceConnector _binance;
    private LinkedList<Pattern> _patterns;
    
	public MainWindow() {
	    super("CoinAssistant");
	    setSize(1200,700);
	    setLocation(100,25);
	    setMinimumSize(new Dimension(650, 400));
	    
	    _binance = new BinanceConnector();
	    _patterns = new LinkedList<Pattern>();
	    Class[] classes;
		try {
			classes = ReflectionHelper.getClasses("coinAssistant.core.candlesticks");
			for (Class c : classes) {
				if (Pattern.class.isAssignableFrom(c)) //pattern = superclass
				{
					

						try {
							_patterns.add((Pattern)c.newInstance());
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    descriptionContainer = Box.createVerticalBox();
	    descriptionContainer.setPreferredSize(new Dimension(300, 200)); 
	    descriptionContainer.setBorder(BorderFactory.createTitledBorder("Description du pattern selectionne"));
	    
	    timeDetection = new JLabel("<html>-temps auquel le pattern est detecte</html>");        
	    forcePattern = new JLabel("<html>-force du pattern</html>");        
	    lengthPattern = new JLabel("<html>-longueur du pattern</html>");        
	    comparison = new JLabel("<html>-caracteristiques de comparaison des differents indicateurs</html>");        
	    
	    descriptionContainer.add(timeDetection);
	    descriptionContainer.add(forcePattern);
	    descriptionContainer.add(lengthPattern);
	    descriptionContainer.add(comparison);
	    
	    
	    
	    interpretationContainer = Box.createVerticalBox();
	    interpretationContainer.setPreferredSize(new Dimension(300, 200)); 
	    interpretationContainer.setBorder(BorderFactory.createTitledBorder("Interpretation du pattern"));
	    explanationInterpretation = new JLabel("<html>-explication de l'interpretation du pattern selectionne ou du dernier pattern</html>");  
	    conclusion = new JLabel("<html>-conclusion sur la tendance soupconnee</html>");        
	    
	    interpretationContainer.add(explanationInterpretation);
	    interpretationContainer.add(conclusion);
	    
	    
	    trendContainer = Box.createVerticalBox();
	    trendContainer.setPreferredSize(new Dimension(300, 200));
	    trendContainer.setBorder(BorderFactory.createTitledBorder("Tendance Globale"));
	    sumIndicator = new JLabel("<html>-somme de tous les indicateurs releves");        
	    globalConclusion = new JLabel("<html>-conclusion sur la tendance globale");        
	    advice = new JLabel("<html>-conseil a l'achat ou a la vente");        
	    
	    trendContainer.add(sumIndicator);
	    trendContainer.add(globalConclusion);
	    trendContainer.add(advice);
	    
	    
	    actuatorContainer = Box.createVerticalBox();
	    actuatorContainer.setPreferredSize(new Dimension(450, 400));
	    actuatorContainer.setBorder(BorderFactory.createTitledBorder("Actionneurs"));
	    

	    final DefaultComboBoxModel model = new DefaultComboBoxModel(_binance.getSymbols().toArray());
	    JComboBox symbolBox = new JComboBox(model);
	    symbolBox.setPreferredSize(new Dimension(200, 50));
	    symbolBox.setMaximumSize(new Dimension(200, 50));
	    symbolBox.addItemListener((ItemListener) this);
	    
	    binarySetting= new JLabel("<html>Paire de cryptomonnaies à analyser : </html>");        
	    continuousSetting = new JLabel("<html>-reglage continu : taille de l'intervalle de temps considere etc</html>");        
	    
	    actuatorContainer.add(binarySetting);
	    actuatorContainer.add(symbolBox);
	    actuatorContainer.add(continuousSetting);
	    
	   
	    
	    
	    graphContainer = new PaneChart(650, 400);
	    graphContainer.setPreferredSize(new Dimension(650, 400));
	    graphContainer.setMinimumSize(graphContainer.getPreferredSize());
	    graphContainer.setBorder(BorderFactory.createTitledBorder("Courbe du cours"));
	    
	  
	    mainContainer = new JPanel();
	    mainContainer.setLayout(new GridBagLayout());
	 
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0; 
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridheight = 5;
	    gbc.gridwidth = 6;
	    mainContainer.add(graphContainer, gbc);
	    
	    gbc.gridx = 7; 
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    gbc.gridheight = 5;
	    gbc.anchor = GridBagConstraints.CENTER;
	    mainContainer.add(actuatorContainer, gbc);
	    
	    gbc.gridx = 0; 
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridy = 6;
	    gbc.gridheight = 4;
	    gbc.gridwidth = 3;
	    mainContainer.add(descriptionContainer, gbc);
	    
	    gbc.gridx = 3; 
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridy = 6;
	    gbc.gridheight = 4;
	    gbc.gridwidth = 3;
	    mainContainer.add(interpretationContainer, gbc);
	    
	    gbc.gridx = 6; 
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridy = 6;
	    gbc.gridheight = 4;
	    gbc.gridwidth = 3;
	  //  gbc.gridwidth = GridBagConstraints.REMAINDER;
	    mainContainer.add(trendContainer, gbc);
	    
	    setContentPane(mainContainer);
	    
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
    
    }
	
	public void displayData(ArrayList<CandleStick> data)
	{
		for (Pattern p : _patterns)
			p.applyPattern(data);
		this.graphContainer.setData(data);
	}
	
	public void itemStateChanged(ItemEvent event) {
       if (event.getStateChange() == ItemEvent.SELECTED) {
          Object item = event.getItem();
          displayData(new ArrayList<CandleStick>(_binance.getCandlesticks(item.toString(), null)));
       }
    }      
	

	public static void main(String[] args) { 
		MainWindow mW = new MainWindow();
		
		//Binance connection test : ok
		/*BinanceConnector connector = new BinanceConnector();
		mW.displayData(new ArrayList<CandleStick>(connector.getCandlesticks("")));*/
		
		/*try {
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
		}*/
	}
	
	
}


