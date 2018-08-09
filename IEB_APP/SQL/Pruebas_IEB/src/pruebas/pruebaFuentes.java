package pruebas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import config.InfoPantalla;

public class pruebaFuentes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		frame fr=new frame();
		
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
}

class frame extends JFrame{
	public frame(){
		
		
		setTitle("titulo");
		setSize(InfoPantalla.anchoResolucion(),InfoPantalla.altoResolucion());
		panel pan=new panel();
		add(pan);
		
	}
	
	
	
}

class panel extends JPanel{
	Vector<JLabel>etiquetas=new Vector<>();
	public void paintComponent(Graphics g){
		int incremX=110;
		int xCount=10;
		int incremY=30;
		int w=100;
		int h=20;
		int x=10;
		int y=10;
		
		int cont=0;
		
		super.paintComponent(g);
		String[] fontNames=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(int i=0;i<fontNames.length;i++){
			if (cont==xCount){
				y=10;
				x+=incremX;
			}else{
				y+=incremY;
			}
			Font fuente =new Font(fontNames[i],Font.BOLD|Font.ITALIC,14);
			JLabel lab=new JLabel("Este es el tipo "+fontNames[i]);
			lab.setFont(fuente);
			lab.setBounds(x, y, w, h);
			etiquetas.add(lab);
		}
		for(int i=0;i<etiquetas.size();i++){
			add(etiquetas.get(i));
		}
		
		
	}
}
