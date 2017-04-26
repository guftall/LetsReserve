import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.Encoder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.jws.soap.SOAPBinding.Use;
import javax.lang.model.util.Elements;
import javax.net.ssl.HttpsURLConnection;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.Document;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import tools.Ghaza;
import tools.GhazaList;
import tools.MyException;
import tools.User;
import tools.UtilityClass;

public class ReqClass {

	
	private UtilityClass utilityClass = new UtilityClass();
	private GhazaList ghazaList = new GhazaList();
	
	private String selfUrl = "http://self.pgu.ac.ir/";

	private User user;
	
	private final String Host = "self.pgu.ac.ir";
	
	private static String __VIEWSTATE = "";
	private static String __EVENTVALIDATION = "";
	private String __EVENTTARGET = "";
	public static String Cookie = "";
	private String CaptchaControl1 = "";
	
	//private String Origin = "http://self.pgu.ac.ir";
	//private String Referer = "http://self.pgu.ac.ir/login.aspx";
	
	
	public ReqClass(User user, boolean makeNewCookie){
		this.user = user;
		try {
			if(makeNewCookie)
				getNewCookieIfNotValid();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public String getTxtusername() {
		return this.user.getTxtUsername();
	}

	public String getTxtpassword() {
		return this.user.getTxtPassword();
	}

	private void sendGetToSelf() throws Exception {
		
		URL obj = new URL(selfUrl);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
		conn.setRequestMethod("GET");

		setRequestHeaders(conn);
		
		conn.setRequestProperty("Host", "self.pgu.ac.ir");
		
		int responseCode = conn.getResponseCode();
		System.out.println("Response Code(GET - self.pgu.ac.ir) is: "+ responseCode);
		
		if(responseCode == 200) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "UTF-8"));
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			//conn.disconnect();
			
			String captchaUrl = response.toString().substring(response.toString().indexOf(
					"CaptchaImage"), response.toString().indexOf("CaptchaImage")+ 59);
			
			
			// Get image
			utilityClass.imgUrlToLoad = selfUrl+ captchaUrl;
			new Thread(utilityClass).start();
			
			setViewStateAndEValidationToFields(response.toString());
			

			this.CaptchaControl1 = getImageCaptcha();
			
			utilityClass.frame.setVisible(false);
			utilityClass.frame.dispose();
			
		}
		
	}
	
	private void sendPostToSelfPgu() throws Exception {
	    
	    StringBuilder tokenUri=new StringBuilder("");

        tokenUri.append("__LASTFOCUS=");
        tokenUri.append(URLEncoder.encode("","UTF-8"));

        tokenUri.append("&__EVENTTARGET=");
        tokenUri.append(URLEncoder.encode(__EVENTTARGET,"UTF-8"));

        tokenUri.append("&__EVENTARGUMENT=");
        tokenUri.append(URLEncoder.encode("","UTF-8"));
        

        tokenUri.append("&__VIEWSTATE=");
        tokenUri.append(URLEncoder.encode(__VIEWSTATE,"UTF-8"));
        
        tokenUri.append("&__VIEWSTATEENCRYPTED=");
        tokenUri.append(URLEncoder.encode("","UTF-8"));

        tokenUri.append("&__EVENTVALIDATION=");
        tokenUri.append(URLEncoder.encode(__EVENTVALIDATION,"UTF-8"));
	    
        tokenUri.append("&txtusername=");
        tokenUri.append(URLEncoder.encode("15481","UTF-8"));
        
        tokenUri.append("&txtpassword=");
        tokenUri.append(URLEncoder.encode("17633","UTF-8"));

	    tokenUri.append("&CaptchaControl1=");
        tokenUri.append(URLEncoder.encode(CaptchaControl1,"UTF-8"));

        tokenUri.append("&btnlogin=");
        tokenUri.append(URLEncoder.encode("ظˆط±ظˆط¯","UTF-8"));

        /*
        if(Cookie != "") {

            tokenUri.append("&Cookie=");
            tokenUri.append(URLEncoder.encode("Nos","UTF-8"));
        }
 */
        String pguSite = "http://self.pgu.ac.ir/Login.aspx";
		URL url = new URL(pguSite);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		
		setRequestHeaders(con);

		con.setRequestProperty("Cache-Control", "max-age=0");
		con.setRequestProperty("Connection", "keep-alive");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		con.setRequestProperty("Host", "self.pgu.ac.ir");
		con.setRequestProperty("Origin", "http://self.pgu.ac.ir");
		con.setRequestProperty("Referer", "http://self.pgu.ac.ir/login.aspx");
		con.setRequestProperty("Upgrade-Insecure-Requests", "1");
		
		con.setFixedLengthStreamingMode(tokenUri.toString().getBytes("UTF-8").length);
 
		con.setDoOutput(true);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream(),"UTF-8");
		
		outputStreamWriter.write(tokenUri.toString());
        outputStreamWriter.flush();
        con.setInstanceFollowRedirects(false);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		
		int responseCode = con.getResponseCode();
		

		System.out.println("Response Code(POST - /Login.aspx) : " + responseCode);
			if(con.getHeaderField("Set-Cookie") != null)
			{
				Cookie = con.getHeaderField("Set-Cookie").substring(0,42);
			}
			else {
				System.out.println("server don send any cookie - SendPostToSelf()");
				try {
					String inputLine;
					StringBuffer response = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
						}
					
					System.out.println(response.toString());
					
					}
					catch (Exception e) {
						e.printStackTrace();
					}
			}
			
			if(in != null)
				in.close();
			if(con != null)
				con.disconnect();
		

	}

	
	public void nextWeekBtn() throws Exception {
		
		getNewCookieIfNotValid();
		SendGetToReserve();
		
		String urlAddre = this.selfUrl + "Reserve.aspx";
		URL obj = new URL(urlAddre);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
		setRequestHeaders(conn);
		
		conn.setRequestMethod("POST");

		conn.setRequestProperty("Origin", "http://self.pgu.ac.ir");
		conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Referer", "http://self.pgu.ac.ir/reserve.aspx");

		conn.setRequestProperty("Cookie", Cookie);
		
		
		StringBuilder tokenUri = setFormDataForPostReserve();
		
		tokenUri.append("&__EVENTTARGET=");
		tokenUri.append(URLEncoder.encode("btnnextweek1", "UTF-8"));
		
		
		conn.setFixedLengthStreamingMode(tokenUri.toString().getBytes("UTF-8").length);
		
		

		conn.setDoOutput(true);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
		conn.setInstanceFollowRedirects(false);
		outputStreamWriter.write(tokenUri.toString());
        outputStreamWriter.flush();

        int responseCode = conn.getResponseCode();
		
        
        //conn.setInstanceFollowRedirects(false);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        

		System.out.println("Response Code(POST - NextWeek) : " + responseCode);
		
		
		try {
				String inputLine;
				StringBuffer response = new StringBuffer();
		
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
		
				in.close();
				setViewStateAndEValidationToFields(response.toString());
				
				
				submitNextWeekFormTest01(response.toString());
				
				
			}catch (Exception e) {
			
			}
				
	}
	
	
	private String getImageCaptcha() {

        String test1= JOptionPane.showInputDialog("Please input Image Numbers: ");

		return test1;
		
	}
	
	private String SendGetToReserve() throws Exception {
		

		URL obj = new URL(selfUrl + "Reserve.aspx");
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
		conn.setRequestMethod("GET");
		
		setRequestHeaders(conn);
		
		conn.setRequestProperty("Referer", "http://self.pgu.ac.ir/");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.8,fa;q=0.6,la;q=0.4");
		
		conn.setRequestProperty("Cookie", Cookie);
		
		int responseCode = conn.getResponseCode();
		System.out.println("Response Code(GET - /Reserve.aspx) is: "+ responseCode);
		
		if(responseCode == 200) {
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
			
			setViewStateAndEValidationToFields(response.toString());

			try {
			org.jsoup.nodes.Document doc = Jsoup.parse(response.toString());
			Element lblMessage = doc.getElementById("LbMsg");
			System.out.println(lblMessage.text());
			}
			catch (Exception e) {
				throw new MyException("Cookie is not Valid");
			}

			getSelectedGhazas(Jsoup.parse(response.toString()));
			return response.toString();
		}

		return "Error. Response code is : "+ responseCode;
		
	}

	public void TaideReserveGhaza() throws Exception {
		
		getNewCookieIfNotValid();
		SendGetToReserve();


        StringBuilder tokenUri = setFormDataForPostReserve();
        

        tokenUri.append("&__EVENTTARGET=");
        tokenUri.append(URLEncoder.encode("","UTF-8"));
        tokenUri.append("&btn_saveKharid=");
        tokenUri.append(URLEncoder.encode("تائید", "UTF-8"));
        
	    
        String pguSite = "http://self.pgu.ac.ir/Reserve.aspx";
		URL url = new URL(pguSite);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");

		con.setRequestProperty("Origin", "http://self.pgu.ac.ir");
		con.setRequestProperty("Upgrade-Insecure-Requests", "1");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Referer", "http://self.pgu.ac.ir/reserve.aspx");

		con.setRequestProperty("Cookie", Cookie);
		
		

		String dataTest = tokenUri.toString();
		con.setFixedLengthStreamingMode(dataTest.getBytes("UTF-8").length);
		System.out.println(dataTest);
		con.setDoOutput(true);
		
		
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream(),"UTF-8");
		
		outputStreamWriter.write(dataTest.toString());
        con.setInstanceFollowRedirects(false);
        outputStreamWriter.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		
		int responseCode = con.getResponseCode();
		

		System.out.println("Response Code(POST - /Reserve.aspx) : " + responseCode);
		
		try {
				String inputLine;
				StringBuffer response1 = new StringBuffer();
				
			while ((inputLine = in.readLine()) != null) {
				response1.append(inputLine);
			}
	
				in.close();
				org.jsoup.nodes.Document doc = Jsoup.parse(response1.toString());
				System.out.println(response1.toString());
				System.out.println(doc.getElementById("LbMsg").text());
		
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		
	}

	private void submitNextWeekFormTest01(String html) throws Exception {

        StringBuilder tokenUri = setFormDataForPostReserve();

        tokenUri.append("&__EVENTTARGET=");
        tokenUri.append(URLEncoder.encode("","UTF-8"));
        tokenUri.append("&btn_saveKharid=");
        tokenUri.append(URLEncoder.encode("تائید", "UTF-8"));

        tokenUri.setCharAt(tokenUri.indexOf("txtn_numGhaza1")+15, '0');
        tokenUri.setCharAt(tokenUri.indexOf("txtn_numGhaza2")+15, '0');
        tokenUri.setCharAt(tokenUri.indexOf("txtn_numGhaza3")+15, '0');
        tokenUri.setCharAt(tokenUri.indexOf("txtn_numGhaza4")+15, '0');
        
        
        String pguSite = "http://self.pgu.ac.ir/Reserve.aspx";
		URL url = new URL(pguSite);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");

		con.setRequestProperty("Origin", "http://self.pgu.ac.ir");
		con.setRequestProperty("Upgrade-Insecure-Requests", "1");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Referer", "http://self.pgu.ac.ir/reserve.aspx");

		con.setRequestProperty("Cookie", Cookie);
		
		
		
		con.setFixedLengthStreamingMode(tokenUri.toString().getBytes("UTF-8").length);
 
		con.setDoOutput(true);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream(),"UTF-8");
		
		outputStreamWriter.write(tokenUri.toString());
        con.setInstanceFollowRedirects(false);
        outputStreamWriter.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		
		int responseCode = con.getResponseCode();
		

		System.out.println("Response Code(POST - /Reserve.aspx NextWeek) : " + responseCode);
		
		try {
				String inputLine;
				StringBuffer response1 = new StringBuffer();
				
			while ((inputLine = in.readLine()) != null) {
				response1.append(inputLine);
			}
	
				in.close();
				org.jsoup.nodes.Document doc = Jsoup.parse(response1.toString());
				System.out.println(response1.toString());
				System.out.println(doc.getElementById("LbMsg").text());
		
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		
   
	}
	
	private void saveCookie() {
		if(Cookie != "" || Cookie != null) {
		
			FileWriter fileWriter = null;
			try {
				 fileWriter = new FileWriter("D:\\cookiePgu.txt");
				 fileWriter.write(Cookie);
				
			}catch (Exception e) {

			}finally {
				if(fileWriter != null)
					try {
						fileWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		
	}

	private void readCookie() throws Exception {
		FileReader fileReader;
		BufferedReader inBufferedReader = null;
		try {
			fileReader = new FileReader("D:\\cookiePgu.txt");
			
			inBufferedReader = new BufferedReader(fileReader);
			
			Cookie = inBufferedReader.readLine();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(inBufferedReader != null)
				inBufferedReader.close();
		}
		
		System.out.println("Cookie is: "+ Cookie);
		
	}

	private void getNewCookieIfNotValid() throws Exception {
		
		readCookie();
		if(!CookieIsValid()) {
			// TODO gozashtane ! dar IF bala
			sendGetToSelf();
			sendPostToSelfPgu();
			System.out.println("New Cookie is: "+ Cookie);
			saveCookie();
		}
		
	}
	
	private boolean CookieIsValid() throws Exception {
		try {
			String getRes =
			SendGetToReserve();
			if(getRes.startsWith("Error")) {
				System.out.println(getRes);
				return false;
			}
		}catch (MyException e) {
			if(e.msg.startsWith("Cookie"));
			return false;
		}
		
		
		return true;
	}

	
	private void getSelectedGhazas(org.jsoup.nodes.Document document) throws Exception {
		
		while(ghazaList.getAllGhazas().size() > 0)
			ghazaList.getAllGhazas().remove(0);
		
		org.jsoup.select.Elements elements = document.getElementsByTag("input");
		System.out.println(elements.size());
		for(Element element : elements) {
			if(element.attr("name").startsWith("Hid") || element.attr("name").startsWith("Edit") ||
					element.attr("name").contains("Ghaza"))
			{
				Ghaza ghaza = new Ghaza();

				
				ghaza.nameId = element.attr("name");
				
				ghaza.value = element.val();
				ghaza.description = "#bug1";
				
				if(!element.attr("disabled").startsWith("disab")  || ghaza.nameId.startsWith("Ghaza"))
					ghaza.enable = true;
				
				ghazaList.addGhaza(ghaza);
			}
			
		}
		System.out.println(ghazaList.getAllGhazas().size());
	}
	
	private void setViewStateAndEValidationToFields(String string) throws Exception {

		org.jsoup.nodes.Document doc = Jsoup.parse(string);
		// Set __VIEWSTATE
		__VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
		

		__EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION").val();
	}

	private void setRequestHeaders(HttpURLConnection connection) {

		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
		connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8,fa;q=0.6,la;q=0.4");
	}

	private StringBuilder setFormDataForPostReserve() throws Exception {
		
		StringBuilder rEStringBuilder = new StringBuilder();


		rEStringBuilder.append("__EVENTARGUMENT=");
		rEStringBuilder.append(URLEncoder.encode("","UTF-8"));
        

		rEStringBuilder.append("&__VIEWSTATE=");
		rEStringBuilder.append(URLEncoder.encode(__VIEWSTATE,"UTF-8"));
        
		rEStringBuilder.append("&__VIEWSTATEENCRYPTED=");
		rEStringBuilder.append(URLEncoder.encode("","UTF-8"));

		rEStringBuilder.append("&__EVENTVALIDATION=");
		rEStringBuilder.append(URLEncoder.encode(__EVENTVALIDATION,"UTF-8"));
	    
		rEStringBuilder.append("&");

        for(Ghaza ghaza : ghazaList.getAllGhazas()) {
        	//if(ghaza.enable)
        		rEStringBuilder.append(ghaza.nameId+ "="+ ghaza.value+ "&");
        }

        rEStringBuilder.append("RD_Self=1");
        rEStringBuilder.append("&Self=");
        rEStringBuilder.append(URLEncoder.encode("1", "UTF-8"));
		
		return rEStringBuilder;
	}
	
	
	
	
}
