package PCK_HTML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

public class HTML {
	public HTML() {
	}

	public static Vector<String> getHTML(String ruta){
		Vector<String>v=new Vector<String>();
		URL url=null;
		URLConnection uc=null;
		InputStreamReader is=null;
		BufferedReader br=null;
		try {
			url = new URL(ruta);
		    //uc = url.openConnection();
		    uc = url.openConnection();
		    uc.connect();
		    
		    System.out.println();
		    try {
				Thread.sleep(6000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		    //uc.setConnectTimeout(5000);
		    //uc.setReadTimeout(5000);
		    is=new InputStreamReader(uc.getInputStream(),"UTF-8");
		    br=new BufferedReader(is);
		    String inputLine;
			/*URL url = new URL(ruta);
	        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
	        String inputLine;*/
		    while ((inputLine = br.readLine()) != null) {
		    	v.add(inputLine.trim());
		    }
		    br.close();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("HTML::getHTML::ERROR::No se pudo obtener el HTML"+e.toString());
		}
		return v;
	}
	
	public static Vector<String> getHTMLProxy(String ruta, String ip, int puerto){
		Vector<String>v=new Vector<String>();
		URL url=null;
		URLConnection uc=null;
		InputStreamReader is=null;
		BufferedReader br=null;
		try {
			//System.out.println("IP: '"+ip+"' Port: '"+puerto+"'");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, puerto));
			url = new URL(ruta);
		    //uc = url.openConnection();
		    uc = url.openConnection(proxy);
		    uc.connect();
		    
		    try {
				Thread.sleep(6000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		    //uc.setConnectTimeout(5000);
		    //uc.setReadTimeout(5000);
		    is=new InputStreamReader(uc.getInputStream(),"UTF-8");
		    br=new BufferedReader(is);
		    String inputLine;
			/*URL url = new URL(ruta);
	        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
	        String inputLine;*/
		    while ((inputLine = br.readLine()) != null) {
		    	v.add(inputLine.trim());
		    }
		    br.close();
		}catch (Exception e) {
			// TODO: handle exception
			String msg=e.toString();
			if(msg.contains("Unable to tunnel through proxy. Proxy returns") && v.size()==0){
				v.add("PROXY ERROR");
			}else if (msg.contains("Connection refused:") && v.size()==0) {
				v.add("PROXY ERROR");
			}else if (msg.contains("SSLHandshakeException:") && v.size()==0) {
				v.add("PROXY ERROR");
			}else if (msg.contains("ConnectException: Connection timed out") && v.size()==0) {
				//v.add("PROXY ERROR");
			}else if (msg.contains("SSLProtocolException") && v.size()==0) {
				v.add("PROXY ERROR");
			}else if (msg.contains("Connection reset") && v.size()==0) {
				v.add("PROXY ERROR");
			}else if (msg.contains("SSLException:") && v.size()==0) {
				v.add("PROXY ERROR");
			}else if(msg.contains("Authentication failure") && v.size()==0){
				v.add("PROXY ERROR");
			}else if(msg.contains("Unexpected end of file from server") && v.size()==0){
				v.add("PROXY ERROR");
			}else if(msg.contains("SSLHandshakeException") && v.size()==0){
				v.add("PROXY ERROR");
			}
			System.out.println("HTML::getHTML::ERROR::No se pudo obtener el HTML: "+e.toString());
		}
		return v;
	}
	
	public static Vector<String> getHTMLProxyProbar(String ruta, String ip, int puerto){
		Vector<String>v=new Vector<String>();
		URL url=null;
		URLConnection uc=null;
		InputStreamReader is=null;
		BufferedReader br=null;
		try {
			//System.out.println("IP: '"+ip+"' Port: '"+puerto+"'");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, puerto));
			url = new URL(ruta);
		    //uc = url.openConnection();
		    uc = url.openConnection(proxy);
		    uc.connect();
		    
		    try {
				Thread.sleep(6000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		    //uc.setConnectTimeout(5000);
		    //uc.setReadTimeout(5000);
		    is=new InputStreamReader(uc.getInputStream(),"UTF-8");
		    br=new BufferedReader(is);
		    String inputLine;
			/*URL url = new URL(ruta);
	        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
	        String inputLine;*/
		    while ((inputLine = br.readLine()) != null) {
		    	v.add(inputLine.trim());
		    }
		    br.close();
		}catch (Exception e) {
			// TODO: handle exception
			String msg=e.toString();
			if(msg.contains("Unable to tunnel through proxy. Proxy returns")){
				v.add("PROXY ERROR");
			}else if (msg.contains("Connection refused:")) {
				v.add("PROXY ERROR");
			}else if (msg.contains("SSLHandshakeException:")) {
				v.add("PROXY ERROR");
			}else if (msg.contains("ConnectException: Connection timed out")) {
				v.add("PROXY ERROR");
			}else if (msg.contains("SSLProtocolException")) {
				v.add("PROXY ERROR");
			}else if (msg.contains("Connection reset")) {
				v.add("PROXY ERROR");
			}else if (msg.contains("SSLException:")) {
				v.add("PROXY ERROR");
			}else if(msg.contains("Authentication failure")){
				v.add("PROXY ERROR");
			}
			//System.out.println("HTML::getHTML::ERROR::No se pudo obtener el HTML: "+e.toString());
		}
		return v;
	}
	
}
