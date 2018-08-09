package pruebas;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sun.java2d.pipe.DrawImage;
import config.InfoImages;
import config.InfoSQL;
import FrontJava.Imagen;

public class pantalla2Panel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame fr=new JFrame();
		fr.setBounds(0, 0, 1000, 800);
		Panel1 pan1=new Panel1();
		Panel2 pan2=new Panel2();
		fr.add(pan1);
		fr.add(pan2);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
	}
	
	

}

class Panel1 extends JPanel{
	public Panel1() {
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Imagen im=new Imagen(InfoImages.rutaImagenes()+"/Torre.jpg", 0, 0, 1000, 800);
		Image IM=im.getImagen();
		g.drawImage(IM, 0, 0, 1000, 800, null);
		setBounds(0, 0, 1000, 800);
		
	}
	
}

class Panel2 extends JPanel{
	public Panel2() {
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Imagen im=new Imagen(InfoImages.rutaImagenes()+"/Torre.jpg", 0, 0, 1000, 800);
		Image IM=im.getImagen();
		g.drawImage(IM, 0, 0, 200, 200, null);
		setBounds(0, 0, 1000, 800);
		
	}
	
}
