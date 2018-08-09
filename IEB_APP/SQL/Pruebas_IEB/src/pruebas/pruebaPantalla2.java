package pruebas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import FrontJava.GeneraFrame;
import FrontJava.Imagen;
import FrontJava.Rectangulo;
import config.InfoImages;
import config.InfoPantalla;

public class pruebaPantalla2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<Rectangulo>rectangulos=new Vector<Rectangulo>();
		Vector<Imagen>imagenes=new Vector<Imagen>();
		Vector<JTextField>txtFields=new Vector<JTextField>();
		Vector<JLabel>labels=new Vector<JLabel>();
		
		for(int i=0;i<3;i++){
			Rectangulo miRectangulo=new Rectangulo(i*100, i*50,100*(i+1), 50, null);
			rectangulos.add(miRectangulo);
			miRectangulo=null;
		}
		for(int i=0;i<30;i++){
			Imagen miImagen=new Imagen(InfoImages.rutaLogo(), i*20, i*20, 20, 20);
			imagenes.add(miImagen);
			miImagen=null;
		}
		for(int i=0;i<5;i++){
			for (int j = 0; j < 5; j++) {
				Imagen miImagen=new Imagen(InfoImages.rutaTorre(), i*200, j*150, 200, 150);
				imagenes.add(miImagen);
				miImagen=null;
			}
		}
		for(int i=0;i<10;i++){
			JTextField mitxt=new JTextField();
			mitxt.setBounds(120, (i+1)*40, 200, 30);
			txtFields.add(mitxt);
			mitxt=null;
		}
		for(int i=0;i<10;i++){
			JLabel lab=new JLabel("Etiqueta "+i+":");
			lab.setBounds(20, (i+1)*40, 80, 30);
			labels.add(lab);
			lab=null;
		}
		
		GeneraFrame miFrame=new GeneraFrame();
		miFrame.setRectangulos(rectangulos);
		miFrame.setImagenes(imagenes);
		miFrame.setTextFields(txtFields);
		miFrame.setLabels(labels);
		miFrame.generaFrame();
		miFrame.setVisible(true);
		miFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("El código sigue..");
	
	
	}
	
	
	class MarcoConDibujos extends JFrame{
		
		
		public MarcoConDibujos() {
			setTitle("titulo");
			setSize(InfoPantalla.anchoResolucion(),InfoPantalla.altoResolucion());
			LaminaConFiguras milamina=new LaminaConFiguras();
			milamina.setBackground(Color.lightGray);
			milamina.setBackground(null);
			add(milamina);
			LaminaConImagen miImagen=new LaminaConImagen();
			miImagen.setBackground(null);
			add(miImagen);
			EventoDeTeclado tecla=new EventoDeTeclado();
			addKeyListener(tecla);
			LaminaFoco lamfoco=new LaminaFoco();
			lamfoco.setBackground(null);
			add(lamfoco);
		}
	}
	
	class LaminaConFiguras extends JPanel{
		private Vector<Rectangulo> rectangulos=new Vector<Rectangulo>();
		
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawRect(10, 100, 980, 600);
			
			Graphics2D g2=(Graphics2D)g;
			g2.setPaint(Color.CYAN);
			Rectangle2D rectang=new Rectangle2D.Double(100, 100, 200, 150);
			//g2.draw(rectang);
			g2.fill(rectang);
			g2.setPaint(Color.RED);
			Ellipse2D elipse=new Ellipse2D.Double();
			elipse.setFrame(rectang);
			//g2.draw(elipse);
			g2.fill(elipse);
			g2.draw(new Line2D.Double(100,100,150,250));
			
		}
	}
	
	class LaminaConImagen extends JPanel{
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			File miimagen=new File(InfoImages.rutaLogo());
			
			double ancho=0;
			double alto=0;
			int nuevoAlto=0;
			double ratio=0;
			int nuevoAncho=0;
			int posIzq=0;
			int posAlt=0;
			
			try {
				imagen=ImageIO.read(miimagen);
				ancho=imagen.getWidth(this);
				alto=imagen.getHeight(this);
				nuevoAlto=InfoPantalla.altoResolucion()-80;
				ratio=nuevoAlto/alto;
				nuevoAncho=(int)(ancho*ratio);
				System.out.println("Rat: "+ratio+" ancho: "+nuevoAncho+" alto:"+nuevoAlto+" res:"+InfoPantalla.anchoResolucion());
				posIzq=InfoPantalla.anchoResolucion()-nuevoAncho-40;
				posAlt=40;
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("No se encuentra la imagen "+InfoImages.rutaLogo());
			}
			//drawImage(imagen, 400, 70, null);
			g.drawImage(imagen, posIzq,posAlt, nuevoAncho,nuevoAlto, null);
			
		}
		private Image imagen;
	}
	
	
	

}
