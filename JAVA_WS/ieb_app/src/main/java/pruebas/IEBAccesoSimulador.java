package pruebas;

import java.util.HashMap;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlImageInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.Iterator;

import FilesAndFolders.FicherosCLS;
import config.InfoIEB;


public class IEBAccesoSimulador {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*for (int i=0;i<1;i++) {
			accede(String.valueOf(i));
		}*/
		try {
			prueba80();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("main Exception");
		}
		
		
	}
	
	public static void prueba() {
		try {
			System.out.println("Empezando...");
			String loginFormUrl="https://ieb-simulador.com/cgi-bin/acceso.cgi";
			
			
			Connection.Response loginForm = Jsoup.connect(loginFormUrl)
					.userAgent("Mozilla/5.0")
	                .method(Connection.Method.GET)
	                .execute();
			//System.out.println(loginForm.body());
			
			String prueba="https://ieb-simulador.com/cgi-bin/acceso.cgi?usuario=1PRUEBA&password=1PRUEBA";
			
			//Document document = Jsoup.connect(loginFormUrl)
			Document document = Jsoup.connect(prueba)
	                //.data("cookieexists", "false")
					.userAgent("Mozilla/5.0")
	                /*.data("usuario", "1PRUEBA")
	                .data("password", "1PRUEBA")*/
	                //.cookies(loginForm.cookies())
	                .post();
	        System.out.println(document);
			
	       
	        FormElement form = document.select("[name=form]").forms().get(0);
	        Connection post = form.submit();
	        
	        
	        System.out.println(post.toString());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPCION");
		}
	}
	
	public static void pruba2() throws Exception {
		
		
		final WebClient webClient = new WebClient();
		final HtmlPage page1 = webClient.getPage("http://ieb-simulador.com/cgi-bin/acceso.cgi");
		final HtmlForm form = page1.getFormByName("form");
		System.out.println(form.toString());
		final HtmlTextInput textField = form.getInputByName("usuario");
		textField.setValueAttribute("1PRUEBA");
		final HtmlPasswordInput textField2 = form.getInputByName("password");
		textField2.setValueAttribute("1PRUEBA");
		
		
		int cuenta=form.getChildElementCount();
		
		form.setActionAttribute("acceso.cgi");
		
		System.out.println("onClick: "+form.getOnClickAttribute());
		System.out.println("Tiene "+cuenta+" elementos.");
		for(int i=0;i<cuenta;i++) {
			System.out.println("Elemento "+i);
			try {
				HtmlElement div=(HtmlElement) form.getChildElements().iterator().next();
				System.out.println(div.asText()+":::"+div.getOnClickAttribute());
				int cuenta2=div.getChildElementCount();
				for(int j=0;j<cuenta2;j++) {
					System.out.println("Elemento ["+i+"]["+j+"]");
					HtmlElement div2=(HtmlElement) div.getChildElements().iterator().next();
					try {
						System.out.println(div2.asText()+":::"+div.getOnClickAttribute());
					}catch (Exception e) {
						// TODO: handle exception
						System.out.println("PETE Elemento ["+i+"]["+j+"]");
					}
				}
				HtmlPage page2=div.click();
				System.out.println(page2.toString());
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("PETE "+i);
			}
		}
		
		/*
		//final HtmlSubmitInput  button =(HtmlSubmitInput) form.getInputsByName("image1").get(0);
		System.out.println(form.getInputsByName("image1").listIterator(0).toString());
		final HtmlIm imagen=(HtmlImageInput) form.getInputsByName("image1");

		
		//final HtmlPage page2 = button.click();
		//final HtmlPage page2 =(HtmlPage) imagen.click();
		final HtmlPage page2=((HtmlSubmitInput) form.getInputsByName("image1")).click();
		*/
		


		
		
		
		
	}
	
	
	public static void prueba80() {
		try {
			final String USER_AGENT = "\"Mozilla/5.0";  
			
			String url = "https://ieb-simulador.com/";
			Document doc=Jsoup.connect(url).get();
			
			System.out.println("La web es:"+url);
			Thread.sleep(300);
			System.out.println(doc.toString());
			
			
			//String url = "http://ieb-simulador.com/cgi-bin/acceso.cgi";
			String urlPost = "https://ieb-simulador.com/";
			
			HashMap<String, String> cookies = new HashMap<String, String>(); 
			HashMap<String, String>formData=new HashMap<String, String>();
			formData.put("usuario", "Beneiitte");
			formData.put("password", "Beneiitte");
			
			Connection.Response loginForm = Jsoup.connect(url).method(Connection.Method.GET).userAgent(USER_AGENT).execute(); 
			cookies.putAll(loginForm.cookies());
			if(cookies.size()>0) {
				for(int i=0;i<cookies.size();i++) {
					System.out.println("cookie "+i+" - "+cookies.get(i).toString());
				}
			}
			System.out.println("Respuesta ok");
			Thread.sleep(500);
			System.out.println(loginForm.toString());

			Document loginDoc = loginForm.parse(); // this is the document that contains response html
			Elements els=loginDoc.getAllElements();
			boolean procesado=false;
			String src="";
			int pos=-1;
			if (els.size()>0) {
				for(int i=0;i<els.size();i++) {
					System.out.println("Elemento: "+els.get(i).tagName()+"\n\""+els.get(i).toString()+"\"\n\n");
					if(procesado==false && els.get(i).nodeName().equals("frame")) {
						procesado=true;
						System.out.println(i+" - "+els.get(i).attr("src"));
						src=els.get(i).attr("src");
						pos=i;
					}
				}
			}
			Connection.Response formulario=Jsoup.connect(src)  
			         .cookies(cookies)  
			         //.data(formData)  
			         //.method(Connection.Method.POST)  
			         .userAgent(USER_AGENT)  
			         .execute(); 
			cookies.putAll(formulario.cookies());
			if(cookies.size()>0) {
				for(int i=0;i<cookies.size();i++) {
					System.out.println("cookie "+i+" - "+cookies.get(i).toString());
				}
			}
			System.out.println("Respuesta 2 ok");
		
			
			loginDoc = formulario.parse(); // this is the document that contains response html
			els=loginDoc.getAllElements();
			procesado=false;
			src="";
			pos=-1;
			FormElement form=null;
			if (els.size()>0) {
				for(int i=0;i<els.size();i++) {
					System.out.println("Elemento: "+els.get(i).nodeName()/*+"\n\""+els.get(i).toString()+"\"\n\n"*/);
					if(procesado==false && els.get(i).nodeName().equals("form")) {
						procesado=true;
						System.out.println(i+" - "+els.get(i).toString());
						form=(FormElement) els.get(i);
						pos=i;
					}
				}
			}
			
			System.out.println(form.toString());
			
			Elements elementos=form.getAllElements();
			for(int i=0;i<elementos.size();i++) {
				
				//System.out.println(i+" - "+elementos.get(i).tagName()+" - "+elementos.get(i).toString()+"\n");
				if(elementos.get(i).toString().contains("\"usuario\"") && elementos.get(i).tagName().equals("td")) {
					System.out.println("USUARIO: "+i+" - "+form.getAllElements().get(i).toString());
					form.getAllElements().get(i).append("1PRUEBA");
					System.out.println("USUARIO: "+i+" - "+form.getAllElements().get(i).toString());
				}else if (elementos.get(i).toString().contains("\"password\"") && elementos.get(i).tagName().equals("td")) {
					System.out.println("PASSWORD: "+i+" - "+form.getAllElements().get(i).toString());
					form.getAllElements().get(i).append("1PRUEBA");
					System.out.println("PASSWORD: "+i+" - "+form.getAllElements().get(i).toString());
				}
			}	
			
			Connection.Request respuesta=form.submit().userAgent(USER_AGENT).cookies(cookies).request();
			loginDoc = respuesta.;
			
			System.out.println("\n\n\n");
			System.out.println(loginDoc.toString());
			
			
			
		
			 
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("PETA!!!!!");
			System.out.println(e.toString());
		}
			
	}
	
	public static void accede(String id) {
		try {
			System.out.println("Empezando...");
			final String USER_AGENT = "\"Mozilla/5.0 (Windows NT\" +\n" +  
			         "          \" 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2\"";  
			 String loginFormUrl = "https://github.com/login";  
			 String loginActionUrl = "https://github.com/session";  
			 String username = "joelmin93@gmail.com";  
			 String password = "XXXX";  
			String link="https://ieb-simulador.com/cgi-bin/graficos_precios.cgi?id_usuario=109396&id_grupo=1176&id_apartado=&profesor=&id_simulacion=152&sesionID=id782635489164529591863564212542&runmode=descarga";
			HashMap<String, String> cookies = new HashMap<String, String>(); 
			HashMap<String, String>formData=new HashMap<String, String>();
			formData.put("id_zona", "0");
			formData.put("id_mercado", "12");
			formData.put("id_activo", "7");
			
			Connection.Response loginForm = Jsoup.connect(link).method(Connection.Method.GET).userAgent(USER_AGENT).execute();  
			 Document loginDoc = loginForm.parse(); // this is the document that contains response html
			 cookies.putAll(loginForm.cookies()); // save the cookies, this will be passed on to next request  
			
			 Connection.Response homePage = Jsoup.connect(loginActionUrl)  
			         .cookies(cookies)  
			         .data(formData)  
			         .method(Connection.Method.POST)  
			         .userAgent(USER_AGENT)  
			         .execute();   
			 Document doc = homePage.parse();
			//Document doc = Jsoup.connect("https://ieb-simulador.com/cgi-bin/acceso.cgi")//.get()
			//Document doc = Jsoup.connect("https://ieb-simulador.com/descarga_historicos/historico_"+id+".csv").get();
			//Document doc = Jsoup.connect(link).get();
					/*.data("usuario", "1PRUEBA")
					.data("password", "1PRUEBA")*/
					// and other hidden fields which are being passed in post request.
					/*.userAgent("Chrome")
					.post();*/
					String ruta = InfoIEB.rutaExport()+"\\PruebaHTML.html";
					System.out.println(doc.html());
					FicherosCLS.writeLine(ruta, doc.html());
					
					//Element el=doc.selectFirst("body");
					//el=el.selectFirst("body");
					//System.out.println(el.toString());
					
		}catch (Exception e) {
			// TODO: handle exception
			
			System.out.println(e.toString());
		}
		
	}
}
