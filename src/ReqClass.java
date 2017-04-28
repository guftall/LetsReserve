
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;

import tools.GhazaList;
import tools.Log;
import tools.MyException;
import tools.User;
import tools.UtilityClass;

public class ReqClass {

	
	private UtilityClass utilityClass = new UtilityClass();
	private GhazaList ghazaList = new GhazaList();
	private Log log = Log.getLog();
	

	private User user;
	
	
	private String __VIEWSTATE = "";
	private String __EVENTVALIDATION = "";
	private String __EVENTTARGET = "";
	public String Cookie = "";
	private String CaptchaControl1 = "";
	
	private String weekTarikhShanbe = "";
	
	//private String Origin = "http://self.pgu.ac.ir";
	//private String Referer = "http://self.pgu.ac.ir/login.aspx";
	
	
	public ReqClass(User user){
		this.user = user;
	}
	
	
	
	
	
	
	
	

	public void LoginToSelf() throws Exception {

		getNewCookieIfNotValid();
	}
	
	private void loadCaptchaImage(String imgUrl) {

		utilityClass.imgUrlToLoad = imgUrl;
		new Thread(utilityClass).start();
		

		this.CaptchaControl1 = JOptionPane.showInputDialog("Please input Image Numbers: ");
		
		utilityClass.frame.setVisible(false);
		utilityClass.frame.dispose();
	}
	
	
	public void showNextWeekGhazas() throws Exception {
		
		getNewCookieIfNotValid();
		//SendGetToReserve();
		String targetUrl = "http://self.pgu.ac.ir/Reserve.aspx";
		
		URL url = new URL(targetUrl);

		__EVENTTARGET = "btnnextweek1";
		String postData = getAllFormDataForSubmitReserve(sendGet(url),false);
		//sendPost(url,postData);
		org.jsoup.nodes.Document htmlRes = Jsoup.parse(sendPost(url,postData));
		if(htmlRes.getElementById("LbMsg") != null){
			String[] splittedDate =
			htmlRes.getElementById("D1").text().split("/");
			
			weekTarikhShanbe = splittedDate[2] + splittedDate[1] + splittedDate[0];
			log.addLog(weekTarikhShanbe);
			ghazaList.setGhazasPersianName(weekTarikhShanbe);
			log.enablePrinting = true;
			log.addLog(ghazaList.getAllGhazasPersionName());
			log.enablePrinting = false;
			
		}
				
				
	}
	
	private void getNewCookieIfNotValid() throws Exception {
		
		this.Cookie = utilityClass.readCookie();
		if(!CookieIsValid()) {
			org.jsoup.nodes.Document response = sendGet(new URL("http://self.pgu.ac.ir/login.aspx"));
			String captchaUrl = response.getElementById("Panel1").child(3).child(0).child(0).attr("src").toString();
			

			// Get image
			loadCaptchaImage("http://self.pgu.ac.ir/"+ captchaUrl);
			

			
	        
			sendPost(new URL("http://self.pgu.ac.ir/login.aspx"), getAllFormDataForLogin());
			log.addLog("New Cookie is: "+ Cookie);
			utilityClass.saveCookie(Cookie);
		}
		
	}
	
	private boolean CookieIsValid() throws Exception {
		try {
			org.jsoup.nodes.Document getRes  = sendGet(new URL("http://self.pgu.ac.ir/ChangePass.aspx"));
			if(getRes.getElementById("lblmessage") == null) {
				return true;
			}
			else {
				log.addLog(getRes.toString());
				return false;
			}
		}catch (MyException e) {
			if(e.msg.startsWith("Cookie"));
			return false;
		}
	}

	
	private void setViewStateAndEValidationToFields(org.jsoup.nodes.Document doc) throws Exception {

		// Set __VIEWSTATE
		__VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
		
		if(doc.getElementById("__EVENTVALIDATION") != null)
			__EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION").val();
	}

	private void setRequestHeaders(HttpURLConnection connection) {

		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
		connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8,fa;q=0.6,la;q=0.4");
	}

	
	private String sendPost(URL url, String allFormData) throws Exception {
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		
		if(Cookie != "")
			conn.setRequestProperty("Cookie", Cookie);

		conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
		//setRequestHeaders(conn);
		
		conn.setFixedLengthStreamingMode(allFormData.toString().getBytes("UTF-8").length);
		 
		conn.setDoOutput(true);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
		
		outputStreamWriter.write(allFormData.toString());
        outputStreamWriter.flush();
        conn.setInstanceFollowRedirects(false);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		
		int responseCode = conn.getResponseCode();
		log.addLog("Post Response Code("+ url.getHost()+ ") is : "+ responseCode);
		
		
		if(conn.getHeaderField("Set-Cookie") != null)
		{
			Cookie = conn.getHeaderField("Set-Cookie").substring(0,42);
			log.addLog("Cookie changed to : "+ Cookie);
			utilityClass.saveCookie(Cookie);
		}
		
		
		String txt1;
		String html ="";
		
		while((txt1 = in.readLine()) != null) {
			html += txt1;
		}
		
		if(responseCode == 200)
			setViewStateAndEValidationToFields(Jsoup.parse(html));
		
		if(in != null)
			in.close();
		conn.disconnect();
		
		return html;
		
	}

	private org.jsoup.nodes.Document sendGet(URL url) throws Exception {
		
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		if(Cookie != "")
			conn.setRequestProperty("Cookie", Cookie);
		//setRequestHeaders(conn);

		int responseCode = conn.getResponseCode();

		log.addLog("GET Response Code("+ url.getHost()+ ") is: "+ responseCode);
		
		//if(responseCode == 200) {
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
		
		 
		org.jsoup.nodes.Document doc = Jsoup.parse(response.toString());
		setViewStateAndEValidationToFields(doc);
		//setViewStateAndEValidationToFields(response.toString());
		//weekTarikhShanbe = Jsoup.parse(response.toString()).getElementById("D1").text();
	
		
		return doc;
		//}
		
		
	}
	
	private int sendGet(String targetUrl) throws Exception {
		
		URL url = new URL(targetUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		if(Cookie != "")
			conn.setRequestProperty("Cookie", Cookie);
		//setRequestHeaders(conn);

		return conn.getResponseCode();
	}

	private String getAllFormDataForLogin() throws Exception {

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
        tokenUri.append(URLEncoder.encode(user.getTxtUsername(),"UTF-8"));
        
        tokenUri.append("&txtpassword=");
        tokenUri.append(URLEncoder.encode(user.getTxtPassword(),"UTF-8"));

	    tokenUri.append("&CaptchaControl1=");
        tokenUri.append(URLEncoder.encode(CaptchaControl1,"UTF-8"));

        tokenUri.append("&btnlogin=");
        tokenUri.append(URLEncoder.encode("تآیید","UTF-8"));
        
        return tokenUri.toString();
	}


	private String getAllFormDataForSubmitReserve(org.jsoup.nodes.Document reserveGetResponse, boolean setBtn_Taeid) throws Exception {
		StringBuilder tokenUri=new StringBuilder("");

        tokenUri.append("__EVENTTARGET=");
        tokenUri.append(URLEncoder.encode(__EVENTTARGET,"UTF-8"));

        tokenUri.append("&__EVENTARGUMENT=");
        tokenUri.append(URLEncoder.encode("","UTF-8"));

        tokenUri.append("&__VIEWSTATE=");
        tokenUri.append(URLEncoder.encode(__VIEWSTATE,"UTF-8"));
        
        tokenUri.append("&__VIEWSTATEENCRYPTED=");
        tokenUri.append(URLEncoder.encode("","UTF-8"));

        tokenUri.append("&__EVENTVALIDATION=");
        tokenUri.append(URLEncoder.encode(__EVENTVALIDATION,"UTF-8"));
        
		
		
		tokenUri.append("&");
		// TODO It Can Be False to return just 'enabled Ghazas' + 'Ghaza[SNC][1-7]'
		tokenUri.append(ghazaList.getGhazasFieldForPostReserve(true));
        tokenUri.append("RD_Self=");
        tokenUri.append(URLEncoder.encode("1","UTF-8"));
        tokenUri.append("&Self=");
        tokenUri.append(URLEncoder.encode("1","UTF-8"));
        if(setBtn_Taeid) {
	        tokenUri.append("&btn_saveKharid=");
	        tokenUri.append(URLEncoder.encode("تائید","UTF-8"));
        }
        
        return tokenUri.toString();
	}
	
		
	}
	
