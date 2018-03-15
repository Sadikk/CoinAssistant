package coinAssistant.ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import coinAssistant.core.*;

public abstract class CandleStickChartView {  
	static BufferedImage current;
	static int width=500;
	static int height=width/2; 
	
	//variables pour le map des valeurs:
	static int largDivX=0;
	static double rapportY=0; 
	static double vMin=0;
	static double vMax=0;
	static int largCandle=0;
	//retourne une representation graphique d'une liste d'evenements
	//sans image d'entrée
	public static BufferedImage createChart(ArrayList<CandleStick> data, int w, int h) {
		current=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		current.getGraphics().setColor(Color.white);
		current.getGraphics().fillRect(0, 0, width, height);
		
		width=w;
		height=h;
		return createChart(current,data);
	}
	
	// avec image d'entrée, pour superposer des graphes par exemple
	public static BufferedImage createChart(BufferedImage c, ArrayList<CandleStick> data) {
		current=c;
		//dimensionnement adapté à la taille des données: définir l'echelle x
		largDivX=width/data.size();
		largCandle=(int)(largDivX*0.1);
		//on cherche le min/max de la série pour définir l'echelle y
		vMin=data.get(0).getLow();
		vMax=data.get(0).getHigh();
		for(CandleStick candle:data) {
			if(candle.getLow()<vMin) {vMin=candle.getLow();}
			if(candle.getHigh()>vMax) {vMax=candle.getHigh();}
		}
		rapportY=(double)(height)/(vMax-vMin);
		
		//on trace les barres
		Graphics g=current.getGraphics();
		for(int i=0;i<data.size();i++) {
			CandleStick candle=data.get(i);
			//tracé de la ligne min-max
			int abscisse=(int)((i+0.5)*largDivX);
			g.setColor(Color.black);
			g.drawLine(abscisse, height-(int)((candle.getLow()-vMin)*rapportY), abscisse, height-(int)((candle.getHigh()-vMin)*rapportY));
			//barre du haut
			g.drawLine(abscisse-largCandle, height-(int)((candle.getHigh()-vMin)*rapportY), abscisse+largCandle, height-(int)((candle.getHigh()-vMin)*rapportY));
			//barre du bas
			g.drawLine(abscisse-largCandle, height-(int)((candle.getLow()-vMin)*rapportY), abscisse+largCandle,height-(int)((candle.getLow()-vMin)*rapportY));
			
			//tracé des boites
			
			//on regarde si motif croissant ou decroissant
			double min=candle.getMinBody();
			double max=candle.getMaxBody();
			if(candle.isAscend()) {g.setColor(Color.green);}
			else {g.setColor(Color.red);}
			//boites
			g.fillRect(abscisse-largCandle,height-(int)((max-vMin)*rapportY),largCandle*2,(int)((max-min)*rapportY));
			//contour de la boite
			g.setColor(Color.black);
			g.drawRect(abscisse-largCandle,height-(int)((max-vMin)*rapportY),largCandle*2,(int)((max-min)*rapportY));
		}
		return current;	
	}
	
}
