package tools;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UtilityClass implements Runnable {

	private Log log = Log.getLog();
	
	public String imgUrlToLoad = "";
	public String captcha ="";
    public JFrame frame=new JFrame();

	public void displayImage() throws IOException
    {
		BufferedImage img=ImageIO.read(new URL(imgUrlToLoad));
        ImageIcon icon=new ImageIcon(img);
        frame.setLayout(new FlowLayout());
        frame.setSize(200,300);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	public void run() {
			try {
			displayImage();
			}
			catch (Exception e) {
				log.addLog(e.getMessage() );
				e.printStackTrace();
			}
		
	}


	public void saveCookie(String cookie) {
		if(cookie != "" || cookie != null) {
		
			FileWriter fileWriter = null;
			try {
				 fileWriter = new FileWriter("D:\\cookiePgu.txt");
				 fileWriter.write(cookie);
				
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

	public String readCookie() throws Exception {
		String cookie = "";
		FileReader fileReader;
		BufferedReader inBufferedReader = null;
		fileReader = new FileReader("D:\\cookiePgu.txt");
		
		inBufferedReader = new BufferedReader(fileReader);
		
		cookie = inBufferedReader.readLine();
		
		if(inBufferedReader != null)
			inBufferedReader.close();
		
		log.addLog("Old Cookie is: "+ cookie);
		return cookie;
		
		
	}


}
