package tools;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class UtilityClass implements Runnable {

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
				System.out.println(e.getMessage() );
				e.printStackTrace();
			}
		
	}



}
