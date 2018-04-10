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
	static double largDivX=0;
	static double rapportY=0;  
	static double vMin=0;
	static double vMax=0;
	static int largCandle=0;	
	static int largLegendY=0;
	static private boolean rapportYFrozen=false;
	
	/**
	 * crée une image representant les données en entrée, aux dimensions choisies
	 * @param data		données en entrée
	 * @param w			largeur de l'image à creer
	 * @param h			hauteur de l'image à creer
	 * @return le graphique créé
	 */
	public static BufferedImage createChart(ArrayList<CandleStick> data, int w, int h) {
		current=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics g=current.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
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
		largLegendY=(int)(0.1*width);
		width-=largLegendY;
		largDivX=(double)(width)/(double)(data.size());
		largCandle=(int)(largDivX*0.45);
		if(!rapportYFrozen) {//toutes les adaptations de l'echelle aux données, desactivable depuis la fenetre
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
		}
		
		//showPatternV1(c,data);
		
		//on trace les barres
		Graphics g=current.getGraphics();
		for(int i=0;i<data.size();i++) {
			CandleStick candle=data.get(i);
			//tracé de la ligne min-max
			int abscisse=(int)((i+0.5)*largDivX)+largLegendY;
			g.setColor(Color.black);
			g.drawLine(abscisse, mapValueYtoGraph(candle.getLow()), abscisse, mapValueYtoGraph(candle.getHigh()));

			//tracé des boites
			
			//on regarde si motif croissant ou decroissant
			double min=candle.getMinBody();
			double max=candle.getMaxBody();
			if(candle.isAscend()) {g.setColor(new Color(140,193,118));}
			else {g.setColor(new Color(184,44,12));}
			//boites
			g.fillRect(abscisse-largCandle,mapValueYtoGraph(max),largCandle*2,(int)((max-min)*rapportY));
			//contour de la boite
			g.setColor(Color.black);
			g.drawRect(abscisse-largCandle,mapValueYtoGraph(max),largCandle*2,(int)((max-min)*rapportY));
		}
		showPatternBox(current,data);
		addLegend(current,rapportY,vMin,vMax);
		return current;	
	}
	public static int mapValueYtoGraph(double value) {
		return height-(int)((value-vMin)*rapportY);
	}
	
	public static void addLegend(BufferedImage c, double rapportY,double vMin,double vMax) {
		Graphics g=c.getGraphics();
		final int NB_GRAD=8;
		double pas=(vMax-vMin)/(double)NB_GRAD;
		int order=calculOrder(vMin);
		for(int i=0;i<NB_GRAD;i++) {
			int hBox=c.getHeight()-(int)(rapportY*(i*pas));
			g.setColor(Color.white);
			g.fillRect(8, hBox-15, 30, 15);
			g.setColor(Color.black);
			g.drawRect(8, hBox-15, 30, 15);
			double valToDisplay=Math.round((vMin+i*pas)*100.0/Math.pow(10, order))/100.0;
			//System.out.println(debugToDisplay+" "+Math.pow(10, order)+" "+order+" "+vMin);
			String toDisplay=Double.toString(valToDisplay);
			g.drawString(toDisplay,10,hBox);
		}
		g.drawString("X10^("+order+")",10,20);
		
	}
	
	private static int calculOrder(double vMin) {
		int ordre=0;
		while(vMin/(double)Math.pow(10, ordre)<1 || vMin/(double)Math.pow(10, ordre)>10) {
			if((vMin/(double)Math.pow(10, ordre))<1) {
				ordre--;
			}
			else {ordre++;}
		}
		return ordre;
	}
	
	/**
	 * met en valeur les patterns reconnus en surlignant avec transparence le fond
	 * s'utilise avant l'affichage des candlesticks pour ne pas les affecter
	 * @param c			l'image sur laquelle superposer les calques
	 * @param data		les données associées
	 */
	public static void showPatternOver(BufferedImage c,ArrayList<CandleStick> data) {
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
				g.fillRect((int)(i*largDivX)+largLegendY, 0, (int)(taillePattern*largDivX), height);
			}
		}
		
	}
	
	/**
	 * met en valeur les patterns reconnus avec des barres sous le graphique
	 * s'utilise à n'importe quel moment, les barres sont à priori dans la marge sans valeur en bas du graphique
	 * @param c			l'image sur laquelle superposer les marques
	 * @param data		les données associées
	 */
	public static void showPatternLines(BufferedImage c,ArrayList<CandleStick> data) {
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
						g.drawLine((int)((i+0.10)*(largDivX))+largLegendY,heightLine,(int)((i+p.getPatternSize()-0.10)*largDivX)+largLegendY,heightLine);//0.1 pour espacer les signaux
						for(int j=0;j<p.getPatternSize();j++) {
							pris[i+j][rang]=true;
						}
					}
				}
			}
		}
	}
	
	/**
	 * met en valeur les patterns reconnus avec des box sous autour du graph
	 * @param c			l'image sur laquelle superposer les marques
	 * @param data		les données associées
	 */
	public static void showPatternBox(BufferedImage c,ArrayList<CandleStick> data) {
		Graphics2D g=(Graphics2D)c.getGraphics();
		BasicStroke line = new BasicStroke(2.0f);
		g.setStroke(line);
		
		for (int i=0;i<data.size();i++) {
			CandleStick candle=data.get(i);
			if (candle.getPatterns() != null)
			{
				for(Pattern p :candle.getPatterns()) {
					if(p.getPatternSize()<data.size()-i) {//protège la methode d'input invalides avec pattern reconnus en dehors de la liste
						g.setColor(p.getColor());
						
						//on cherche les positions exactes des rectangles à dessiner
						double vMinLocal=candle.getLow();
						double vMaxLocal=candle.getHigh();
						for(int r=i;r<i+p.getPatternSize();r++){
							vMinLocal=Math.min(vMinLocal, data.get(r).getLow());
							vMaxLocal=Math.max(vMaxLocal, data.get(r).getHigh());
						}
						//on aère un peu le graphe:
						vMinLocal-=(int)((vMaxLocal-vMinLocal)*0.2);
						vMaxLocal+=(int)((vMaxLocal-vMinLocal)*0.2);
						g.drawRect((int)(largDivX*i)+largLegendY, mapValueYtoGraph(vMaxLocal), (int)(largDivX*p.getPatternSize()),(int)((vMaxLocal-vMinLocal)*rapportY));
					}
				}
			}
		}
	}
	
	/**
	 * methode outil pour la methode showPatternLines, donne le premier rang dispo pour l'affichage d'une ligne
	 * @see showPatternLines
	 * @param tab : le tableau des disponibilités
	 * @return  le rang recherché
	 */
	private static int firstRangeAvailable(boolean [] tab) {
		for(int i=0;i<tab.length;i++) {
			if(!tab[i]) {return i;}
		}
		System.out.println("Capacite d'evenements simultanés insuffisante");
		return -1;
	}
	
	public static void setRapportYFrozen(boolean state) {
		rapportYFrozen=state;
	}
}

