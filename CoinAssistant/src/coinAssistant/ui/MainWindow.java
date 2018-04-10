package coinAssistant.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;

import coinAssistant.core.BinanceConnector;
import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;
import coinAssistant.core.ReflectionHelper;
import coinAssistant.core.TraderSimulator;

public class MainWindow extends JFrame implements ItemListener, PatternListener{
	
	//dimensionnement de la fenï¿½tre
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
    private JLabel simulationResult;
    private JLabel binarySetting;
    private JLabel continuousSetting;
    private JComboBox symbolBox;
    
    private TraderSimulator trader;
    private JSlider nbPatternOnScreen;
    private JSlider selectionSection;
    private JRadioButton box,lines,highlight;
    private JCheckBox freezeRapportY;
    private BinanceConnector _binance;
    private LinkedList<Pattern> _patterns;
    
	public MainWindow() {
	    super("CoinAssistant");
	    
	    Toolkit t = Toolkit.getDefaultToolkit();
		this.sizeX = (int) (t.getScreenSize().width * 0.8);
		this.sizeY = (int)(sizeX/((double)(sizeXinit)/(sizeYinit)));
		this.ratio=(double)(sizeX)/(double)(sizeXinit);
	    
		Font font = new Font("Calibri", Font.PLAIN, toRelative(20));
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
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					
					
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
	    simulationResult = new JLabel("<html> gains en suivant les conseils de l'outil : </html>");
	    simulationResult.setFont(font);
	    
	    trendContainer.add(sumIndicator);
	    trendContainer.add(globalConclusion);   
	    trendContainer.add(simulationResult);
	    
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
	    nbPatternOnScreen.setInverted(true);
	    
	    //initialisation du slider permettant de choisir la fenêtre temporelle voulue
	    selectionSection=new JSlider(0,100);
	    selectionSection.setPreferredSize(new Dimension(toRelative(700),toRelative(100)));
	    selectionSection.setMaximumSize(selectionSection.getPreferredSize());
	    selectionSection.addChangeListener(graphContainer);
	    selectionSection.setPaintTicks(true);
		selectionSection.setPaintLabels(true);
		
		//permet de figer les paramêtres d'echelle du chart affiché
		freezeRapportY=new JCheckBox("Bloquer la variation d'echelle",false);
		freezeRapportY.setPreferredSize(new Dimension(toRelative(500),toRelative(100)));
		freezeRapportY.setMaximumSize(freezeRapportY.getPreferredSize());
		freezeRapportY.addItemListener(graphContainer);
		
		//permet de choisir le mode de mise en valeur des patterns
		JLabel labelRadioButton=new JLabel("mode de mise en valeur des patterns");
		labelRadioButton.setFont(font);
		box=new JRadioButton("boites",true);
		lines=new JRadioButton("lignes");
		highlight=new JRadioButton("surligné");
		ButtonGroup bg=new ButtonGroup();
		bg.add(box);
		bg.add(lines);
		bg.add(highlight);
		//ecouteurs entrainant directement le changement dans la classe cible
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {CandleStickChartView.typeShowPattern=ShowPatternStyle.BOX;}
		});
		lines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {CandleStickChartView.typeShowPattern=ShowPatternStyle.LINES;}
		});
		highlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {CandleStickChartView.typeShowPattern=ShowPatternStyle.HIGHLIGHT;}
		});
		
		
	    actuatorContainer.add(nbPatternOnScreen);
	    actuatorContainer.add(selectionSection);
	    actuatorContainer.add(freezeRapportY);
	    actuatorContainer.add(labelRadioButton);
	    actuatorContainer.add(box);
	    actuatorContainer.add(lines);
	    actuatorContainer.add(highlight);
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
		globalConclusion.setText("Tendance globale : " +  (sum > 0 ? "Haussiere" : "Baissiere"));
		this.graphContainer.setData(data);
		
		trader=new TraderSimulator(data);
		double result=trader.simulateOnAll();
		result=(double)(Math.round(result*100))/100;
		simulationResult.setText("Gains en suivant les conseils de l'outil :"+ result+"%");
	}
	
	
	public void itemStateChanged(ItemEvent event) {
		//lorsqu'une nouvelle cryptomonnaie est selectionnee, on modifie le graphique
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
		patternName.setText("<html> Nom : " + (pattern == null ? " - " : pattern.getName()) + " </html>");
		lengthPattern.setText("<html> Taille : " + (pattern == null ? " - " : pattern.getPatternSize()) + " </html>");
		patternDescription.setText("<html> Description : " + (pattern == null ? " - " :  pattern.getDescription()) + " </html>");
		explanationInterpretation.setText("<html> Explication : " + (pattern == null ? " - " :  pattern.getInterpretationText()) + " </html>");
		
		
		if (pattern== null)
			patternThumb.setIcon(null);
		else
		{
			try {
				InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(pattern.getClass().getSimpleName() + ".png");
				if (input == null)
					patternThumb.setIcon(null);
				patternThumb.setIcon(new ImageIcon(new ImageIcon(ImageIO.read(input)).getImage().getScaledInstance(toRelative(300), toRelative(300), Image.SCALE_SMOOTH)));
			} catch (IOException e) {
				
			}
		}
		
	}
	
	public void loadFirstPair()
	{
		symbolBox.setSelectedItem("ETHBTC");
	}
	

	public static void main(String[] args) { 
		
        SwingUtilities.invokeLater ( new Runnable ()
        {
            public void run ()
            {
                WebLookAndFeel.install ();
                MainWindow mW = new MainWindow();
                mW.loadFirstPair();
            }
        } );
	}
	
	
}


