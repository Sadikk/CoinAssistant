package coinAssistant.ui; 
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public abstract class CandleStickChartView {  
	static BufferedImage current;
	static int width=500;
	static int height=width/2; 
	
	//variables pour le map des valeurs:
	static final double SIZE_BORDER=10.0; //en %
	static int largDivX=0;
	static double rapportY=0;  
	static double vMin=0;
	static double vMax=0;
	static int largCandle=0;
	
	/**
	 * crée une image representant les données en entrée, aux dimensions choisies
	 * @param data		données en entrée
	 * @param w			largeur de l'image à creer
	 * @param h			hauteur de l'image à creer
	 * @return le graphique créé
	 */
	public static BufferedImage createChart(ArrayList<CandleStick> data, int w, int h) {
		current=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		current.getGraphics().setColor(Color.white);
		current.getGraphics().fillRect(0, 0, width, height);
		
		width=w;
		height=h;
		return createChart(current,data);
	}
	
	/**
	 * crée une image representant les données en entrées, superposées au graphique
	 * @param c 		image utilisée pour l'affichage
	 * @param data 		données en entrée
	 * @return le graphique créé
	 */
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
		//on aère en laissant 10% de marge en bas et en haut
		double gap=vMax-vMin;
		vMax+=gap*(SIZE_BORDER/100.0);
		vMin-=gap*(SIZE_BORDER/100.0);
				
		rapportY=(double)(height)/(vMax-vMin);
		
		//showPatternV1(c,data);
		
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
		showPatternV2(current,data);
		return current;	
	}
	
	/**
	 * met en valeur les patterns reconnus en surlignant avec transparence le fond
	 * s'utilise avant l'affichage des candlesticks pour ne pas les affecter
	 * @param c			l'image sur laquelle superposer les calques
	 * @param data		les données associées
	 */
	public static void showPatternV1(BufferedImage c,ArrayList<CandleStick> data) {
		Graphics g=c.getGraphics();
		
		for (int i=0;i<data.size();i++) {
			CandleStick candle=data.get(i);			
			if(candle.patterns.size()!=0) {
				int taillePattern=candle.patterns.get(0).getPatternSize();
				if(candle.patterns.size()==1) {
					Color toFade=candle.patterns.get(0).getColor();
					Color faded=new Color(toFade.getRed(),toFade.getGreen(),toFade.getBlue(),175);
					g.setColor(faded);
				}
				else {
					g.setColor(new Color(255,255,0,175));
				}
				g.fillRect(i*largDivX, 0, taillePattern*largDivX, height);
			}
		}
		
	}
	
	/**
	 * met en valeur les patterns reconnus avec des barres sous le graphique
	 * s'utilise à n'importe quel moment, les barres sont à priori dans la marge sans valeur en bas du graphique
	 * @param c			l'image sur laquelle superposer les marques
	 * @param data		les données associées
	 */
	public static void showPatternV2(BufferedImage c,ArrayList<CandleStick> data) {
		Graphics2D g=(Graphics2D)c.getGraphics();
		BasicStroke line = new BasicStroke(4.0f);
		g.setStroke(line);
		final int MAX_PATTERN_SIMULTANE=5;
		boolean [][] pris=new boolean[data.size()][MAX_PATTERN_SIMULTANE];
		
		for (int i=0;i<data.size();i++) {
			CandleStick candle=data.get(i);
			if (candle.getPatterns() != null)
			{
				for(Pattern p :candle.getPatterns()) {
					if(p.getPatternSize()<data.size()-i) {//protège la methode d'input invalides avec pattern reconnus en dehors de la liste
						g.setColor(p.getColor());
						int rang=firstRangeAvailable(pris[i]);
						int heightLine=(int)(height*(1.0-rang*0.02));
						g.drawLine((int)(i+0.10)*(largDivX),heightLine,(int)(i+p.getPatternSize()-0.10)*largDivX,heightLine);//0.1 pour espacer les signaux
						for(int j=0;j<p.getPatternSize();j++) {
							pris[i+j][rang]=true;
						}
					}
				}
			}
		}
	}
	private static int firstRangeAvailable(boolean [] tab) {
		for(int i=0;i<tab.length;i++) {
			if(!tab[i]) {return i;}
		}
		System.out.println("Capacite d'evenements simultanés insuffisante");
		return -1;
	}
}
