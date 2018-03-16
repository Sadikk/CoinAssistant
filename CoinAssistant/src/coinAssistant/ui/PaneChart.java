package coinAssistant.ui; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;
public class PaneChart extends JPanel implements ChangeListener,MouseMotionListener{
	
	int width;
	int height;
	
	ArrayList<CandleStick> data;
	JSlider selectionSection;
	BufferedImage chart;
	int ySlider;
	int nbPatternVisible=20;
	
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
		
		selectionSection=new JSlider();
		ySlider=(int)(height*0.9);
		selectionSection.setBounds((int)(width*0.1),ySlider,(int)(width*0.8),height-ySlider);
		selectionSection.setOpaque(true);
		selectionSection.setBackground(Color.white);
		selectionSection.addChangeListener(this);
		this.add(selectionSection);
		
		this.setBackground(Color.white);
		chart=new BufferedImage(ySlider,width,BufferedImage.TYPE_INT_ARGB);		
		//debug   
		//this.addMouseListener(this);
		//
	}
	
	/**
	 * 	rafraichit les données utilisés pour generer l'image de graphique
	 * @param dataIn	les données à afficher
	 */
	public void setData(ArrayList<CandleStick> dataIn ) {
		this.data=dataIn;
		this.update(this.getGraphics());
		this.refreshImage();
		this.refreshDisplay();
		
		stateChanged(new ChangeEvent(selectionSection));
		/**
		 * a enlever, l'affichage du chart avant de bouger le curseur, donc je le force ici
		 */
	}
	
	/**
	 * utilise la classe CandleStickChartView pour mettre à jour le graphique
	 * affichage réglable en nombre de candlestick affichés et intervalle à choisir
	 * @see CandleStickChartView
	 */
	private void refreshImage() {
		//donne image finale avec tous les calculs
		if(data.size()<=nbPatternVisible) {//si le nombre de données ne demande pas d'ajustements
			this.chart=CandleStickChartView.createChart(data,width,ySlider);
		}
		else {
			int etatSlider=selectionSection.getValue();
			int rangeSlider=selectionSection.getMaximum()-selectionSection.getMinimum();
			int startIndice=(int)((data.size()-nbPatternVisible)*((double)(etatSlider)/(double)(rangeSlider)));
						
			ArrayList<CandleStick> listToDisplay=new ArrayList<CandleStick>(data.subList(startIndice,startIndice+nbPatternVisible));
			this.chart=CandleStickChartView.createChart(listToDisplay,width,ySlider);
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
	
	/**
	 * change le rendu graphique au mouvement du curseur
	 * @param e		démarré par curseur
	 */
	public void stateChanged(ChangeEvent e) {
		this.refreshImage();
		this.refreshDisplay();
	}
	
	public void mouseMoved(MouseEvent e) {
		if(e.getY()<ySlider) {
			int rank=(int)((double)(e.getX())/(double)(CandleStickChartView.largDivX));
			
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
}
