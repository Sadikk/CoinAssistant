package coinAssistant.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;

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
    private JLabel sumIndicator;
    private JLabel globalConclusion;
    private JLabel binarySetting;
    private JLabel continuousSetting;
    private JComboBox symbolBox;
    
    private JSlider nbPatternOnScreen;
    private JSlider selectionSection;
    private JCheckBox freezeRapportY;
    private BinanceConnector _binance;
    private LinkedList<Pattern> _patterns;
    
	public MainWindow() {
	    super("CoinAssistant");
	    
	    Toolkit t = Toolkit.getDefaultToolkit();
		this.sizeX = (int) (t.getScreenSize().width * 0.8);
		this.sizeY = (int)(sizeX/((double)(sizeXinit)/(sizeYinit)));
		this.ratio=(double)(sizeX)/(double)(sizeXinit);
	    
		Font font = new Font("Calibri", Font.PLAIN, toRelative(18));
	    setSize(sizeX,sizeY);
	    setResizable(false);
	    setLocation(toRelative(100),toRelative(25));
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
	    
	    patternName = new JLabel("<html>Nom du pattern</html>");   
	    patternName.setFont(font);
	    patternDescription = new JLabel("<html>Description du pattern</html>");   
	    patternDescription.setFont(font);
	    lengthPattern = new JLabel("<html>Longueur du pattern</html>");        
	    lengthPattern.setFont(font);
	    patternThumb = new JLabel();        
	    
	    descriptionContainer.add(patternName);
	    descriptionContainer.add(patternDescription);
	    descriptionContainer.add(lengthPattern);
	    descriptionContainer.add(patternThumb);
	    
	    
	    
	    interpretationContainer = Box.createVerticalBox();
	    interpretationContainer.setPreferredSize(new Dimension(toRelative(500), toRelative(300))); 
	    interpretationContainer.setBorder(BorderFactory.createTitledBorder("Interpretation du pattern"));
	    explanationInterpretation = new JLabel("<html>Interpretation</html>"); 
	    explanationInterpretation.setFont(font);
	    
	    interpretationContainer.add(explanationInterpretation);
	    
	    
	    trendContainer = Box.createVerticalBox();
	    trendContainer.setPreferredSize(new Dimension(toRelative(500), toRelative(300)));
	    trendContainer.setBorder(BorderFactory.createTitledBorder("Tendance Globale"));
	    sumIndicator = new JLabel("<html>Somme des pattern : </html>");        
	    sumIndicator.setFont(font);
	    globalConclusion = new JLabel("<html>conclusion sur la tendance globale</html>"); 
	    globalConclusion.setFont(font);
	    
	    trendContainer.add(sumIndicator);
	    trendContainer.add(globalConclusion);   
	    
	    
	    actuatorContainer = Box.createVerticalBox();
	    actuatorContainer.setPreferredSize(new Dimension(toRelative(650), toRelative(500)));
	    actuatorContainer.setBorder(BorderFactory.createTitledBorder("Actionneurs"));
	    

	    final DefaultComboBoxModel model = new DefaultComboBoxModel(_binance.getSymbols().toArray());
	    symbolBox = new JComboBox(model);
	    symbolBox.setPreferredSize(new Dimension(toRelative(200), toRelative(50)));
	    symbolBox.setMaximumSize(new Dimension(toRelative(200), toRelative(50)));
	    symbolBox.addItemListener((ItemListener) this);
	    symbolBox.setSelectedIndex(0);
	    symbolBox.revalidate();
	    
	    binarySetting= new JLabel("<html>Paire de cryptomonnaies a analyser : </html>");        
	    continuousSetting = new JLabel("<html>Zoom (echelle X)</html>");        
	    
	    actuatorContainer.add(binarySetting);
	    actuatorContainer.add(symbolBox);
	    actuatorContainer.add(continuousSetting);
	       
	    graphContainer = new PaneChart(toRelative(1140), toRelative(600));
	    graphContainer.setPreferredSize(new Dimension(toRelative(1140),toRelative(600)));
	    graphContainer.setMinimumSize(graphContainer.getPreferredSize());
	    graphContainer.setBorder(BorderFactory.createTitledBorder("Courbe du cours"));
	    graphContainer.addListener(this);
	    
	    //initialisation du slider permettant de choisir le nombre de patterns visibles
	    nbPatternOnScreen=new JSlider(0,100);
	    nbPatternOnScreen.setPreferredSize(new Dimension(toRelative(700),toRelative(50)));
	    nbPatternOnScreen.setMaximumSize(nbPatternOnScreen.getPreferredSize());
	    nbPatternOnScreen.addChangeListener(graphContainer);
	    actuatorContainer.add(nbPatternOnScreen);
	    
	    //initialisation du slider permettant de choisir la fen�tre temporelle voulue
	    selectionSection=new JSlider(0,100);
	    selectionSection.setPreferredSize(new Dimension(toRelative(700),toRelative(100)));
	    selectionSection.setMaximumSize(selectionSection.getPreferredSize());
	    selectionSection.addChangeListener(graphContainer);
	    selectionSection.setPaintTicks(true);
		selectionSection.setPaintLabels(true);
		
		freezeRapportY=new JCheckBox("Bloquer la variation d'echelle",false);
		freezeRapportY.setPreferredSize(new Dimension(toRelative(500),toRelative(100)));
		freezeRapportY.setMaximumSize(freezeRapportY.getPreferredSize());
		freezeRapportY.addItemListener(graphContainer);
		
	    actuatorContainer.add(nbPatternOnScreen);
	    actuatorContainer.add(selectionSection);
	    actuatorContainer.add(freezeRapportY);
	    graphContainer.setSliderSelectionSection(selectionSection);
	    
	  
	    mainContainer = new JPanel();
	    mainContainer.setLayout(new GridBagLayout());
	 
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0; 
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridheight = 5;
	    gbc.gridwidth = 6;
	    mainContainer.add(graphContainer, gbc);
	    
	    gbc.gridx = 0; 
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridy = 6;
	    gbc.gridwidth = 3;
	    gbc.gridheight = 4;
	    mainContainer.add(actuatorContainer, gbc);
	    
	    gbc.gridx = 7; 
	    gbc.gridy = 0; 
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    gbc.gridheight = 5;
	    gbc.anchor = GridBagConstraints.CENTER;
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
	    System.out.println(System.getProperty("user.dir"));
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
		double sum = 0.0;
		for (Pattern p : _patterns) {
			p.applyPattern(data);
		}
		for (CandleStick candle : data)
			for (Pattern p : candle.getPatterns())
				sum += p.getInterpretation();
		sumIndicator.setText("<html>Somme des pattern : " + Math.round(sum) + "</html>");
		globalConclusion.setText("Tendance globale : " +  (sum > 0 ? "Haussière" : "Baissière"));
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
		explanationInterpretation.setText("<html> Explication : " + pattern.getInterpretationText() + "</html>");
		
		try {
			InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(pattern.getClass().getSimpleName() + ".png");
			patternThumb.setIcon(new ImageIcon(new ImageIcon(ImageIO.read(input)).getImage().getScaledInstance(toRelative(300), toRelative(300), Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exception = sw.toString();
			JOptionPane.showMessageDialog(this, 
                    "Une erreur est survenue lors de l'affichage d'une image de pattern. Error : " + e.getMessage() + "-- Stacktrace :" + exception, 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void loadFirstPair()
	{
		//symbolBox.fireItemStateChanged(new ItemEvent(symbolBox,0,"GTOBTC",0));
		displayData(new ArrayList<CandleStick>(_binance.getCandlesticks("GTOBTC", null)));
		graphContainer.refreshDisplay();
	}
	

	public static void main(String[] args) { 
		
        SwingUtilities.invokeLater ( new Runnable ()
        {
            public void run ()
            {
                // Install WebLaF as application L&F
                WebLookAndFeel.install ();
                MainWindow mW = new MainWindow();
                mW.loadFirstPair();
        		System.out.println(mW.getSize());
            }
        } );
	}
	
	
}


