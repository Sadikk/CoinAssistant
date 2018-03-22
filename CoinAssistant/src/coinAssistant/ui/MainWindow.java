package coinAssistant.ui;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import coinAssistant.core.BinanceConnector;
import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;
import coinAssistant.core.ReflectionHelper;

public class MainWindow extends JFrame implements ItemListener, PatternListener{
	
	//dimensionnement de la fen�tre
	int sizeXinit=1800;
	int sizeYinit=1200;
	int sizeX;
	int sizeY;
	double ratio;//final/init
	
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
    private JLabel patternName;
    private JLabel patternDescription;
    private JLabel lengthPattern;
    private JLabel patternThumb;
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
	    
	    Toolkit t = Toolkit.getDefaultToolkit();
		this.sizeX = (int) (t.getScreenSize().width * 0.8);
		this.sizeY = (int)(sizeX/((double)(sizeXinit)/(sizeYinit)));
		this.ratio=(double)(sizeX)/(double)(sizeXinit);
	    
		
	    setSize(sizeX,sizeY);
	    setResizable(false);
	    setLocation(toRelative(100),toRelative(25));
	    //setMinimumSize(new Dimension(1668, 947));
	    //setMaximumSize(new Dimension(1668, 947));
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
	    descriptionContainer.setPreferredSize(new Dimension(toRelative(500), toRelative(300))); 
	    descriptionContainer.setBorder(BorderFactory.createTitledBorder("Description du pattern selectionne"));
	    
	    patternName = new JLabel("<html>-Nom du pattern</html>");        
	    patternDescription = new JLabel("<html>-Description du pattern</html>");        
	    lengthPattern = new JLabel("<html>-Longueur du pattern</html>");        
	    patternThumb = new JLabel("<html>-Image modèle</html>");        
	    
	    descriptionContainer.add(patternName);
	    descriptionContainer.add(patternDescription);
	    descriptionContainer.add(lengthPattern);
	    descriptionContainer.add(patternThumb);
	    
	    
	    
	    interpretationContainer = Box.createVerticalBox();
	    interpretationContainer.setPreferredSize(new Dimension(toRelative(500), toRelative(300))); 
	    interpretationContainer.setBorder(BorderFactory.createTitledBorder("Interpretation du pattern"));
	    explanationInterpretation = new JLabel("<html>-explication de l'interpretation du pattern selectionne ou du dernier pattern</html>");  
	    conclusion = new JLabel("<html>-conclusion sur la tendance soupconnee</html>");        
	    
	    interpretationContainer.add(explanationInterpretation);
	    interpretationContainer.add(conclusion);
	    
	    
	    trendContainer = Box.createVerticalBox();
	    trendContainer.setPreferredSize(new Dimension(toRelative(500), toRelative(300)));
	    trendContainer.setBorder(BorderFactory.createTitledBorder("Tendance Globale"));
	    sumIndicator = new JLabel("<html>-somme de tous les indicateurs releves");        
	    globalConclusion = new JLabel("<html>-conclusion sur la tendance globale");        
	    advice = new JLabel("<html>-conseil a l'achat ou a la vente");        
	    
	    trendContainer.add(sumIndicator);
	    trendContainer.add(globalConclusion);
	    trendContainer.add(advice);
	    
	    
	    actuatorContainer = Box.createVerticalBox();
	    actuatorContainer.setPreferredSize(new Dimension(toRelative(650), toRelative(500)));
	    actuatorContainer.setBorder(BorderFactory.createTitledBorder("Actionneurs"));
	    

	    final DefaultComboBoxModel model = new DefaultComboBoxModel(_binance.getSymbols().toArray());
	    JComboBox symbolBox = new JComboBox(model);
	    symbolBox.setPreferredSize(new Dimension(toRelative(200), toRelative(50)));
	    symbolBox.setMaximumSize(new Dimension(toRelative(200), toRelative(50)));
	    symbolBox.addItemListener((ItemListener) this);
	    
	    binarySetting= new JLabel("<html>Paire de cryptomonnaies a analyser : </html>");        
	    continuousSetting = new JLabel("<html>-reglage continu : taille de l'intervalle de temps considere etc</html>");        
	    
	    actuatorContainer.add(binarySetting);
	    actuatorContainer.add(symbolBox);
	    actuatorContainer.add(continuousSetting);
	    
	   
	    
	    
	    graphContainer = new PaneChart(toRelative(900), toRelative(600));
	    graphContainer.setPreferredSize(new Dimension(toRelative(900),toRelative( 600)));
	    graphContainer.setMinimumSize(graphContainer.getPreferredSize());
	    graphContainer.setBorder(BorderFactory.createTitledBorder("Courbe du cours"));
	    graphContainer.addListener(this);
	    
	  
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
	    pack();
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
    
    }
	
	private int toRelative(int valeur) {
		return (int)(valeur*ratio);
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
	
	/* *  * 
	 * @see coinAssistant.ui.PatternListener#patternHovered(coinAssistant.core.Pattern)
	 */
	public void patternHovered(Pattern pattern)
	{
		patternName.setText("<html> Nom : " + pattern.getName() + "</html>");
		lengthPattern.setText("<html> Taille : " + pattern.getPatternSize() + "</html>");
		patternDescription.setText("<html> Description : " + pattern.getDescription() + "</html>");
		try {
			System.out.println(pattern.getClass().getSimpleName()); //not working
			patternThumb.setIcon(new ImageIcon(ImageIO.read(
			        getClass().getResource("/resources/" + pattern.getClass().getSimpleName() + ".png"))));
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exception = sw.toString();
			JOptionPane.showMessageDialog(null, 
                    "Une erreur est survenue lors de l'affichage d'une image de pattern. Error : " + e.getMessage() + "-- Stacktrace :" + exception, 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
		}
	}
	

	public static void main(String[] args) { 
		MainWindow mW = new MainWindow();
		System.out.println(mW.getSize());
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


