package PCK_HTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import config.LogGlobal;

public class JSoupCon {
	
	public static Document getLink(String link) {
		Document doc=null;
		try {
			doc = Jsoup.connect(link).get();
					  /*.data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();*/
			//System.out.println(doc.title());			
		} catch (Exception e) {
			// TODO: handle exception
			String msg="JSoupCon::getLink::ERROR::"+link+" - "+e.toString();
			LogGlobal.WriteError(msg);
		}
		return doc;
	}
}
