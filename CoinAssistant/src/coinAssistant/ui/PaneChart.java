package coinAssistant.ui; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import coinAssistant.core.CandleStick;
public class PaneChart extends JPanel implements ChangeListener{
	
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
	 * 	raffraichit les données utilisés pour generer l'image de graphique
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
	 * change le rendu graphique au mouvement du curseur
	 * @param e		démarré par curseur
	 */
	public void stateChanged(ChangeEvent e) {
		this.refreshImage();
		this.refreshDisplay();
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
}
