package pruebas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import config.InfoImages;
import config.InfoPantalla;




public class pruebaPantalla {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MarcoConDibujos mimarco=new MarcoConDibujos();
		mimarco.setVisible(true);
		//mimarco.set
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

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

class EventoDeTeclado implements KeyListener{

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int codigo=arg0.getKeyCode();
		if (codigo==82){
			System.out.println("Es una 'r'");
		}else{
			System.out.println(codigo);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

class LaminaFoco extends JPanel{
	JTextField cuadro1;
	JTextField cuadro2;
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setLayout(null);
		cuadro1=new JTextField();
		cuadro2=new JTextField();
		cuadro1.setBounds(100, 10, 150, 40);
		cuadro2.setBounds(50, 20, 300, 20);
		add(cuadro1);
		add(cuadro2);
	}
}