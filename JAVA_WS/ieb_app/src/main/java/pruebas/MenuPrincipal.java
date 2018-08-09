package pruebas;

import java.awt.Color;
import java.awt.Font;
import java.awt.color.ColorSpace;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import FrontJava.Formas;
import FrontJava.GeneraFrame;
import FrontJava.Imagen;
import FrontJava.Rectangulo;
import config.InfoImages;
import config.InfoPantalla;

public class MenuPrincipal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				//Vector<Rectangulo>rectangulos=new Vector<Rectangulo>();
				Vector<Imagen>imagenes=new Vector<Imagen>();
				Vector<JButton>botones=new Vector<>();
				//Vector<JTextField>txtFields=new Vector<JTextField>();
				//Vector<JLabel>labels=new Vector<JLabel>();
				/*
				for(int i=0;i<3;i++){
					Rectangulo miRectangulo=new Rectangulo(i*100, i*50,100*(i+1), 50, null);
					rectangulos.add(miRectangulo);
					miRectangulo=null;
				}*/
				Imagen imagen1=new Imagen(InfoImages.rutaTorre(), 0,0,
										InfoPantalla.anchoResolucion(),
										InfoPantalla.altoResolucion());
				Imagen imagen2=new Imagen(InfoImages.rutaLogo(),50,50,250,240);
				imagenes.add(imagen1);
				imagenes.add(imagen2);
				imagen1=null;
				imagen2=null;
				
				JButton boton=new JButton("Boton 1");
				Font fuenteBoton=Formas.getFuenteTipoBoton();
				//fuenteBoton.;
				//fuenteBoton.ITALIC=1;
				boton.setFont(fuenteBoton);
				
				boton.setBounds(50, 400, 180, 90);
				boton.setBackground(Color.LIGHT_GRAY);
				botones.add(boton);
				
				
				
				/*
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
				*/
				GeneraFrame miFrame=new GeneraFrame();
				//miFrame.setRectangulos(rectangulos);
				miFrame.setImagenes(imagenes);
				miFrame.setBotones(botones);
				//miFrame.setTextFields(txtFields);
				//miFrame.setLabels(labels);
				miFrame.generaFrame();
				miFrame.setVisible(true);
				miFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				System.out.println("El código sigue..");
			
	}

}
