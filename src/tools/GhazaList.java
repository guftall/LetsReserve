package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GhazaList {
	

	
	
	private Ghaza[] ghazas = new Ghaza[105];
	
	
	public GhazaList() {
		InitializeGhazasNameId();
	}
	
	
	public String getAllGhazasPersionName() {
		StringBuilder stringBuilder = new StringBuilder();
		for(Ghaza ghaza : ghazas) {
			if(ghaza.nameId.startsWith("Ghaza"))
				stringBuilder.append(ghaza.nameId+ "="+ ghaza.description);
		}
		
		return stringBuilder.toString();
	}

	public void setGhazasPersianName(String weekShanbeDate) throws Exception {
		
		if(weekShanbeDate.length() < 6)
			return;
		
		HttpURLConnection conn =(HttpURLConnection) new URL("http://self.pgu.ac.ir/Ghaza.aspx?date="+ weekShanbeDate).openConnection();
		
		conn.setRequestMethod("GET");
		conn.getResponseCode();
		
		BufferedReader in = new BufferedReader(
				new InputStreamReader(conn.getInputStream(), "UTF-8"));
		
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		if(in != null)
			in.close();
		conn.disconnect();
		
		
		Document getSelectGhazaAspxResponse = Jsoup.parse(response.toString());
		
		Elements elements = getSelectGhazaAspxResponse.getElementsByTag("ol");
		for(org.jsoup.nodes.Element element : elements) {
			
			for(int i=0; i<105; i++) {
				if(ghazas[i].nameId.startsWith("Ghaza")) {
					String s1 = element.attr("id").substring(12);
					String s2 =(ghazas[i].nameId.substring(5, 6) +(Integer.parseInt(ghazas[i].nameId.substring(6, 7))-1)).toString();
					if(s1.startsWith(s2))
						ghazas[i].description = element.child(0).text();
						
				}
			}
		}
	}
	
	public void setGhazasValue(Document getReserveResponse) {
		
		Elements elements = getReserveResponse.getElementsByTag("input");

		for(Element element : elements) {
			String nameId = element.attr("name");
			for(int i=0; i<105; i++) {
				if(nameId == ghazas[i].nameId) {
					
					ghazas[i].value = element.val();
					
					if(!element.attr("disabled").startsWith("disab"))
						ghazas[i].enable = true;
					}
			}
			
		}
	}
	
	
	public String getGhazasFieldForPostReserve(boolean allFields) {
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<ghazas.length; i++) {
			if(allFields) {
				builder.append(ghazas[i].nameId+ "="+ ghazas[i].value+ "&");
			}else {
				if(ghazas[i].enable || ghazas[i].nameId.startsWith("Ghaza"))
					builder.append(ghazas[i].nameId+ "="+ ghazas[i].value+ "&");
			}
		}
		
		return builder.toString();
	}
	

	private void InitializeGhazasNameId() {

		for(int i=0; i<105; i++)
			ghazas[i] = new Ghaza();
		
		String[] NSC = {"N", "S", "C"};
		int index = 0;
		
		for(int i=0; i<3; i++) {
			for(int j=1; j<=7; j++) {
				ghazas[index].nameId = "Ghaza"+ NSC[i]+ j;
				++index;
			}
		}
		
		for(int i=1; i<7; i++) {
			ghazas[index].nameId = "Hid"+ i;
			++index;
		}
		
		for(int i=1; i<=7; i++) {
			ghazas[index].nameId = "HidS"+ i;
			++index;
		}
		
		for(int i=1; i<=7; i++) {
			ghazas[index].nameId = "HidN"+ i;
			++index;
		}
		
		for(int i=1; i<=7; i++) {
			ghazas[index].nameId = "HidC"+ i;
			++index;
		}
		
		for(int i=1; i<=7; i++) {
			ghazas[index].nameId = "HidSN"+ i;
			++index;
		}
		
		for(int i=1; i<=7; i++) {
			ghazas[index].nameId = "HidCN"+ i;
			++index;
		}
		
		
		for(int i=1; i<=7; i++) {
			ghazas[index].nameId = "EditS"+ i;
			++index;
		}
		
		for(int i=1; i<=7; i++) {
			ghazas[index].nameId = "EditN"+ i;
			++index;
		}
		
		for(int i=1; i<=7; i++) {
			ghazas[index].nameId = "EditC"+ i;
			++index;
		}
		
		for(int i=1; i<=7; i++) {
			ghazas[index].nameId = "txts_numGhaza"+ i;
			++index;
		}
		
		for(int i=1; i<=7; i++) {
			ghazas[index].nameId = "txtn_numGhaza1"+ i;
			++index;
		}
		
		for(int i=1; i<=7; i++) {
			ghazas[index].nameId = "txtc_numGhaza1"+ i;
			++index;
		}
		
		
		
	}


}
