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
import tools.User;
import tools.UtilityClass;

public class ReqClass {

	private UtilityClass utilityClass = new UtilityClass();
	
	private String url = "http://self.pgu.ac.ir/";
	
	private String txtusername = "";
	private String txtpassword = "";
	
	private final String Host = "self.pgu.ac.ir";

	private final String Accept_Language = "en-US,en;q=0.8,fa;q=0.6,la;q=0.4";
	private static String __VIEWSTATE = "";
	private static String __EVENTVALIDATION = "";
	private String __EVENTTARGET = "";
	public static String Cookie = "";
	private String CaptchaControl1 = "";
	private String btnlogin = "ورود";
	private String Origin = "http://self.pgu.ac.ir";
	private String Referer = "http://self.pgu.ac.ir/login.aspx";
	
	


	
	public void nextWeekBtn() throws Exception {
		
		String getResponse = SendGetToReserve();
		String urlAddre = this.url + "Reserve.aspx";
		URL obj = new URL(urlAddre);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
		setRequestHeaders(conn);
		
		conn.setRequestMethod("POST");

		conn.setRequestProperty("Origin", "http://self.pgu.ac.ir");
		conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Referer", "http://self.pgu.ac.ir/reserve.aspx");

		conn.setRequestProperty("Cookie", this.Cookie);
		
		
		
		StringBuilder tokenUri = setFormDataForPostReserve(getSelectedGhazas(Jsoup.parse(getResponse)));
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
	
	
	public ReqClass(User user, boolean makeNewCookie) throws Exception {
		this.txtusername = user.getTxtUsername();
		this.txtpassword = user.getTxtPassword();
		
		if(makeNewCookie)
			getNewCookie();
	}
	


	public String getTxtusername() {
		return txtusername;
	}

	public String getTxtpassword() {
		return txtpassword;
	}


	private void sendGetToSelf() throws Exception {
		
		URL obj = new URL(url);
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
			utilityClass.imgUrlToLoad =url+ captchaUrl;
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
        tokenUri.append(URLEncoder.encode("ورود","UTF-8"));

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
		con.setRequestProperty("Origin", Origin);
		con.setRequestProperty("Referer", Referer);
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
				this.Cookie = con.getHeaderField("Set-Cookie").substring(0,42);
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

	private String getImageCaptcha() {

        String test1= JOptionPane.showInputDialog("Please input Image Numbers: ");

		return test1;
		
	}
	
	private String SendGetToReserve() throws Exception {
		

		URL obj = new URL(url + "Reserve.aspx");
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
		conn.setRequestMethod("GET");
		
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		conn.setRequestProperty("Referer", "http://self.pgu.ac.ir/");
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.8,fa;q=0.6,la;q=0.4");
		
		conn.setRequestProperty("Cookie", this.Cookie);
		
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

			org.jsoup.nodes.Document doc = Jsoup.parse(response.toString());
			Element lblMessage = doc.getElementById("LbMsg");
			System.out.println(lblMessage.text());

			return response.toString();
		}

		return "No Response";
		
	}

	public void TaideReserveGhaza() throws Exception {
		
		String response = SendGetToReserve();


        StringBuilder tokenUri = setFormDataForPostReserve(getSelectedGhazas(Jsoup.parse(response)));
        

        tokenUri.append("__EVENTTARGET=");
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

		con.setRequestProperty("Cookie", this.Cookie);
		
		
		
		con.setFixedLengthStreamingMode(tokenUri.toString().getBytes("UTF-8").length);
 
		con.setDoOutput(true);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream(),"UTF-8");
		
		outputStreamWriter.write(tokenUri.toString());
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
				// TODO: handle exception
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		
	}

	public void submitNextWeekFormTest01(String html) throws Exception {

        StringBuilder tokenUri = setFormDataForPostReserve(getSelectedGhazas(Jsoup.parse(html)));

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

		con.setRequestProperty("Cookie", this.Cookie);
		
		
		
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
				// TODO: handle exception
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		
   
	}
	
	
	public void saveCookie() {
		if(Cookie != "" || Cookie != null) {
		
			FileWriter fileWriter = null;
			try {
				 fileWriter = new FileWriter("D:\\cookiePgu.txt");
				 fileWriter.write(this.Cookie);
				
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


	public void readCookie() throws Exception {
		// TODO Auto-generated method stub
		FileReader fileReader;
		try {
			fileReader = new FileReader("D:\\cookiePgu.txt");
			

			BufferedReader inBufferedReader = new BufferedReader(fileReader);
			
			this.Cookie = inBufferedReader.readLine();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Cookie is: "+ this.Cookie);
		
	}


	public void getNewCookie() throws Exception {
		sendGetToSelf();
		sendPostToSelfPgu();
		System.out.println("New Cookie is: "+ this.Cookie);
	}

	
	public static ArrayList<Ghaza> getSelectedGhazas(org.jsoup.nodes.Document document) {
		
		org.jsoup.select.Elements elements = document.getElementsByTag("input");
		
		ArrayList<Ghaza> resGhazas = new ArrayList<Ghaza>();
		
		for(Element element : elements) {
			if(element.attr("name").startsWith("Hid") || element.attr("name").startsWith("Edit") ||
					element.attr("name").startsWith("Ghaza") || element.attr("name").startsWith("txt"))
			if(!element.attr("disabled").startsWith("disab")) {
				Ghaza ghaza = new Ghaza();
				
				ghaza.nameId = element.attr("name");
				
				ghaza.value = element.val();
				ghaza.description = "#bug1";
				resGhazas.add(new Ghaza());
			}
			
		}

		return resGhazas;
	}
	

	

	private void setViewStateAndEValidationToFields(String string) throws Exception {

		org.jsoup.nodes.Document doc = Jsoup.parse(string);
		// Set __VIEWSTATE
		__VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
		

		__EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION").val();
	}

	
	public void setRequestHeaders(HttpURLConnection connection) {

		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
		connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8,fa;q=0.6,la;q=0.4");
	}

	private StringBuilder setFormDataForPostReserve(ArrayList<Ghaza> selectedGhazas) throws Exception {
		StringBuilder rEStringBuilder = new StringBuilder();


		rEStringBuilder.append("&__EVENTARGUMENT=");
		rEStringBuilder.append(URLEncoder.encode("","UTF-8"));
        

		rEStringBuilder.append("&__VIEWSTATE=");
		rEStringBuilder.append(URLEncoder.encode(__VIEWSTATE,"UTF-8"));
        
		rEStringBuilder.append("&__VIEWSTATEENCRYPTED=");
		rEStringBuilder.append(URLEncoder.encode("","UTF-8"));

		rEStringBuilder.append("&__EVENTVALIDATION=");
		rEStringBuilder.append(URLEncoder.encode(__EVENTVALIDATION,"UTF-8"));
	    
		rEStringBuilder.append("&");

        for(Ghaza ghza : selectedGhazas) {
        	rEStringBuilder.append(ghza.nameId+ "="+ ghza.value+ "&");
        }

        rEStringBuilder.append("RD_Self=1");
        rEStringBuilder.append("&Self=");
        rEStringBuilder.append(URLEncoder.encode("1", "UTF-8"));
		
		return rEStringBuilder;
	}
	
}
