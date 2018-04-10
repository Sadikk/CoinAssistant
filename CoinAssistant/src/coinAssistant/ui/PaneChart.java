package coinAssistant.ui; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import coinAssistant.core.BinanceConnector;
import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;
public class PaneChart extends JPanel implements ChangeListener,MouseMotionListener, ItemListener{
	
	int width;
	int height;
	
	ArrayList<CandleStick> data;
	JSlider selectionSection;
	BufferedImage chart;
	private int ySlider;
	
	private int nbPatternVisible=50;
	private List<PatternListener> listeners;
	
	private long timeEvent=0;
	
	
	
	private int firstRankDisplayed=0;
	/**
	 *  cr�e le JPanel affichant le graphique
	 * @param w		largeur du panel � creer
	 * @param h		hauteur du panel � creer
	 */
	public PaneChart(int w,int h) {
		width=w;
		height=h;
		this.setPreferredSize(new Dimension(width,height));
		this.setLayout(null);
		listeners = new ArrayList<PatternListener>();
		selectionSection=new JSlider();
		ySlider=(int)(height*0.9);
		this.setBackground(Color.white);
		chart=new BufferedImage(ySlider,width,BufferedImage.TYPE_INT_ARGB);	
		   
		this.addMouseMotionListener(this);
	}
	
	/**
	 * 	rafraichit les donn�es utilis�s pour generer l'image de graphique
	 * @param dataIn	les donn�es � afficher
	 */
	public void setData(ArrayList<CandleStick> dataIn ) {
	    if(dataIn!=null) {
    	    selectionSection.setValue(100);
    	    int interval=BinanceConnector.getIntervalInMin();
    	    selectionSection.setMaximum(dataIn.size()*interval);
    	    int nbSpan=5;
    	    int bigSpan=(int)(interval*(double)(dataIn.size())/(double)(nbSpan));
    	    selectionSection.setMajorTickSpacing(bigSpan);
    	    selectionSection.setLabelTable(setLabelForLegend(bigSpan,nbSpan));
    	    selectionSection.setValue(selectionSection.getMaximum()-10);
    		this.data=dataIn;
    		this.update(this.getGraphics());
    		this.refreshImage();
    		repaint();
	    }
	}
	
	/**
	 * 	Ajoute un listener � la liste des listeners
	 * @param toAdd	instance de PatternListener � ajouter
	 */
	public void addListener(PatternListener toAdd) {
        listeners.add(toAdd);
    }
	
	/**
	 * utilise la classe CandleStickChartView pour mettre � jour le graphique
	 * affichage r�glable en nombre de candlestick affich�s et intervalle � choisir
	 * @see CandleStickChartView
	 */
	private void refreshImage() {
	    if(data!=null){
		//donne image finale avec tous les calculs
		if (data.size()<=nbPatternVisible) {//si le nombre de donn�es ne demande pas d'ajustements
			this.chart=CandleStickChartView.createChart(data,width,ySlider);
		}
		else{
			int etatSlider=selectionSection.getValue();
			int rangeSlider=selectionSection.getMaximum()-selectionSection.getMinimum();
			int startIndice=(int)((data.size()-nbPatternVisible)*((double)(etatSlider)/(double)(rangeSlider)));
			firstRankDisplayed=startIndice;
			ArrayList<CandleStick> listToDisplay=new ArrayList<CandleStick>(data.subList(startIndice,startIndice+nbPatternVisible));
			this.chart=CandleStickChartView.createChart(listToDisplay,width,ySlider);
		}
	    }
	}
	
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (chart != null) {
        	g.setColor(Color.white);
    		g.fillRect(0, 0, width, height);
    		g.drawImage(chart, 0, 0,null);
        }
    }
	
	private Dictionary<Integer,JLabel> setLabelForLegend(int span, int nbLabel) {
	    Dictionary<Integer,JLabel> labels=new Hashtable<Integer,JLabel>();
	    SimpleDateFormat h = new SimpleDateFormat ("HH");
	    SimpleDateFormat m=new SimpleDateFormat("mm");
	    Date currentTime = new Date();
	    
	    int currentH = Integer.valueOf(h.format(currentTime));
	    int currentM=Integer.valueOf(m.format(currentTime));
	    int max=selectionSection.getMaximum();
	    for(int i=0;i<nbLabel;i++) {
	        int newH=-1;
	        int newM=-1;
	        int nbMinLate=-i*span+max;
	        System.out.println(nbMinLate);
	        if(currentM-nbMinLate>=0) {
	            newH=currentH;
	            newM=currentM;
	        }
	        else {
	            int nbHour=-1+(currentM-nbMinLate)/60;
	            newH=currentH+nbHour;
	            newM=currentM-(nbMinLate+nbHour*60);
	            
	        }
	        String s=Integer.toString(newH)+":"+Integer.toString(newM);
	        labels.put(i*span, new JLabel(s));
	        
	    }
	    return labels;
	}
	
	/**
	 * change le rendu graphique au mouvement du curseur
	 * @param e		d�marr� par curseur
	 */
	public void stateChanged(ChangeEvent e) {
		if(System.currentTimeMillis()-timeEvent>100) {
			timeEvent=System.currentTimeMillis();
		    if(e.getSource().equals(selectionSection)) {
		        this.refreshImage();
		        repaint();
		    }
		    else {
		        double prop=((JSlider)(e.getSource())).getValue()/100.0;
		        this.setNumberVisible(prop);
		        this.refreshImage();
	            repaint();
		    }
		}
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED) {
			CandleStickChartView.setRapportYFrozen(true);
		}
		else {
			CandleStickChartView.setRapportYFrozen(false);
		}
		this.refreshImage();
	    repaint();
		
	}
	
	
	public void mouseMoved(MouseEvent e) {
		//lorsqu'on passe la souris sur un pattern, afficher les informations correspondantes
		if(e.getY()<ySlider && CandleStickChartView.largDivX!=0 &&e.getX()>CandleStickChartView.largLegendY && e.getX()<width) {
			int rank=firstRankDisplayed+(int)((double)(e.getX()-CandleStickChartView.largLegendY)/(double)(CandleStickChartView.largDivX));
			if (data.size()>rank)
			{
			 	for (PatternListener listener : listeners)
			 		listener.patternHovered(data.get(rank).getPatterns().size() == 0 ? null : data.get(rank).getPatterns().getFirst());
			}	
				
		}
	}
	public void mouseDragged(MouseEvent e) {}
	
	@Override
    public Dimension getPreferredSize() {
        if (isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        return new Dimension(width, height);
    }
	
	public void setNumberVisible(double prop) {
	    if(data!=null) {this.nbPatternVisible=(int)(data.size()*prop);}
	}

	public void setSliderSelectionSection(JSlider slider) {
		this.selectionSection=slider;
	}
}
