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
	
	private int memorySlider=-1;
	/**
	 *  crée le JPanel affichant le graphique
	 * @param w		largeur du panel à creer
	 * @param h		hauteur du panel à creer
	 */
	public PaneChart(int w,int h) {
		width=w;
		height=h;
		this.setPreferredSize(new Dimension(width,height));
		this.setLayout(null);
		listeners = new ArrayList<PatternListener>();
		selectionSection=new JSlider();
		ySlider=(int)(height*0.9);
		/*
		selectionSection.setBounds((int)(width*0.1),ySlider,(int)(width*0.8),height-ySlider);
		selectionSection.setOpaque(true);
		selectionSection.setBackground(Color.lightGray);
		selectionSection.addChangeListener(this);
		*/
		this.setBackground(Color.white);
		chart=new BufferedImage(ySlider,width,BufferedImage.TYPE_INT_ARGB);	
		   
		this.addMouseMotionListener(this);
		//selectionSection.setPaintTrack(false);
		
		//selectionSection.setOnMouseReleased(MouseEvent e){}
	}
	
	/**
	 * 	rafraichit les données utilisés pour generer l'image de graphique
	 * @param dataIn	les données à afficher
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
    		this.data=dataIn;
    		this.update(this.getGraphics());
    		this.refreshImage();
    		this.refreshDisplay();
	    }
		
		//stateChanged(new ChangeEvent(selectionSection));
		//selectionSection.revalidate();
		
		
	}
	
	/**
	 * 	Ajoute un listener à la liste des listeners
	 * @param toAdd	instance de PatternListener à ajouter
	 */
	public void addListener(PatternListener toAdd) {
        listeners.add(toAdd);
    }
	
	/**
	 * utilise la classe CandleStickChartView pour mettre à jour le graphique
	 * affichage réglable en nombre de candlestick affichés et intervalle à choisir
	 * @see CandleStickChartView
	 */
	private void refreshImage() {
	    if(data!=null){
		//donne image finale avec tous les calculs
		if (data.size()<=nbPatternVisible) {//si le nombre de données ne demande pas d'ajustements
			this.chart=CandleStickChartView.createChart(data,width,ySlider);
		}
		else{
			int etatSlider=selectionSection.getValue();
			int rangeSlider=selectionSection.getMaximum()-selectionSection.getMinimum();
			int startIndice=(int)((data.size()-nbPatternVisible)*((double)(etatSlider)/(double)(rangeSlider)));
						
			ArrayList<CandleStick> listToDisplay=new ArrayList<CandleStick>(data.subList(startIndice,startIndice+nbPatternVisible));
			this.chart=CandleStickChartView.createChart(listToDisplay,width,ySlider);
		}
	    }
	}
	
	/**
	 * utilise l'image raffraichie pour le rendu graphique
	 */
	public void refreshDisplay() {
		Graphics g=this.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		g.drawImage(chart, 0, 0, width,ySlider,null);
		
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
	 * @param e		démarré par curseur
	 */
	public void stateChanged(ChangeEvent e) {
	    if(e.getSource().equals(selectionSection)) {
	        this.refreshImage();
	        this.refreshDisplay();
	    }
	    else {
	        double prop=((JSlider)(e.getSource())).getValue()/100.0;
	        this.setNumberVisible(prop);
	        this.refreshImage();
            this.refreshDisplay();
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
        this.refreshDisplay();
		
	}
	public void mouseMoved(MouseEvent e) {
		if(e.getY()<ySlider && CandleStickChartView.largDivX!=0 &&e.getX()>CandleStickChartView.largLegendY && e.getX()<width) {
			int rank=(int)((double)(e.getX()-CandleStickChartView.largLegendY)/(double)(CandleStickChartView.largDivX));
			if (data.size()>rank && data.get(rank).getPatterns() != null && data.get(rank).getPatterns().size() > 0)
			{
			 	for (PatternListener listener : listeners)
			 		listener.patternHovered(data.get(rank).getPatterns().getFirst());
				//System.out.println(data.get(rank).getPatterns().getFirst());
			}	
				
		}
	}
	public void mouseDragged(MouseEvent e) {}
	
	private LinkedList<Pattern> getPatternsAtRank(ArrayList<CandleStick> data,int rank){
		LinkedList<Pattern> result=new LinkedList<Pattern>();
		final int MAX_SIZE_PATTERN=10;//on cherche dans le passé jusqu'à n evenements plus tôt
		for(int i=rank; (i>rank-MAX_SIZE_PATTERN)&&(i>=0);i--) {//protection i>=0 pour eviter sortie de tableau
			LinkedList<Pattern> listPatterns= data.get(i).getPatterns();
			for(int j=0;j<listPatterns.size();j++) {
				if(listPatterns.get(i).getPatternSize()>(rank-i)) {
					result.add(listPatterns.get(i));
				}
			}
		}
		return result;
		
	}
	
	public void setNumberVisible(double prop) {
	    if(data!=null) {this.nbPatternVisible=(int)(data.size()*prop);}
	}

	public void setSliderSelectionSection(JSlider slider) {
		this.selectionSection=slider;
	}
}
