package coinAssistant.ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import coinAssistant.core.*;

public abstract class CSChartView {
	static BufferedImage current;
	static final int TAILLE_X=1000;
	static final int TAILLE_Y=TAILLE_X/2;
	
	//variables pour le map des valeurs:
	static int largDivX=0;
	static double rapportY=0;
	static double vMin=0;
	static double vMax=0;
	static int largCandle=0;
	//retourne une representation graphique d'une liste d'evenements
	//sans image d'entrée
	public static BufferedImage createChart(ArrayList<CandleStick> data) {
		current=new BufferedImage(TAILLE_X,TAILLE_Y,BufferedImage.TYPE_INT_ARGB);
		current.getGraphics().setColor(Color.white);
		current.getGraphics().fillRect(0, 0, TAILLE_X, TAILLE_Y);
		return createChart(current,data);
	}
	
	// avec image d'entrée, pour superposer des graphes par exemple
	public static BufferedImage createChart(BufferedImage c, ArrayList<CandleStick> data) {
		current=c;
		//dimensionnement adapté à la taille des données: définir l'echelle x
		largDivX=TAILLE_X/data.size();
		largCandle=(int)(largDivX*0.1);
		//on cherche le min/max de la série pour définir l'echelle y
		vMin=data.get(0).mini;
		vMax=data.get(0).maxi;
		for(CandleStick candle:data) {
			if(candle.mini<vMin) {vMin=candle.mini;}
			if(candle.maxi>vMax) {vMax=candle.maxi;}
		}
		rapportY=(double)(TAILLE_Y)/(vMax-vMin);
		System.out.println(rapportY);
		
		//on trace les barres
		Graphics g=current.getGraphics();
		for(int i=0;i<data.size();i++) {
			CandleStick candle=data.get(i);
			//tracé de la ligne min-max
			int abscisse=(int)((i+0.5)*largDivX);
			g.setColor(Color.black);
			g.drawLine(abscisse, TAILLE_Y-(int)((candle.mini-vMin)*rapportY), abscisse, TAILLE_Y-(int)((candle.maxi-vMin)*rapportY));
			//barre du haut
			g.drawLine(abscisse-largCandle, TAILLE_Y-(int)((candle.maxi-vMin)*rapportY), abscisse+largCandle, TAILLE_Y-(int)((candle.maxi-vMin)*rapportY));
			//barre du bas
			g.drawLine(abscisse-largCandle, TAILLE_Y-(int)((candle.mini-vMin)*rapportY), abscisse+largCandle,TAILLE_Y-(int)((candle.mini-vMin)*rapportY));
			
			//tracé des boites
			
			//on regarde si motif croissant ou decroissant
			double min=0;
			double max=0;
			if(candle.start>candle.end) {
				g.setColor(Color.green);
				min=candle.end;
				max=candle.start;
			}
			else {
				g.setColor(Color.red);
				min=candle.start;
				max=candle.end;
			}
			
			//boites
			g.fillRect(abscisse-largCandle,TAILLE_Y-(int)((max-vMin)*rapportY),largCandle*2,(int)((max-min)*rapportY));
			//contour de la boite
			g.setColor(Color.black);
			g.drawRect(abscisse-largCandle,TAILLE_Y-(int)((max-vMin)*rapportY),largCandle*2,(int)((max-min)*rapportY));
		}
		return current;	
	}
	
}
