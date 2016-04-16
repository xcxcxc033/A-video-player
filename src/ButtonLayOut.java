import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

public class ButtonLayOut {
	
	public JButton initButton(JLabel mainLabel, int x, int y , String iconString){

		//mainLabel.setPreferredSize(new Dimension(300, 60));
		ImageIcon icon = new ImageIcon(iconString);
		final ImageIcon new_icon = ChangeImgSize(icon, 60, 60);
		
		//ImageIcon imaPause = new ImageIcon("pause.png");
		//final ImageIcon new_imaPause = ChangeImgSize(imaPause, 60, 60);
		
		//ImageIcon imgStop = new ImageIcon("stop.png");
		//final ImageIcon new_imgStop = ChangeImgSize(imgStop, 60, 60);
		
		//ImageIcon imgRePlay = new ImageIcon("replay.png");
		//final ImageIcon new_imgRePlay = ChangeImgSize(imgRePlay, 60, 60);
		
		
		
		
		
		//JButton left = new JButton(new_imgRePlay);	
		//final JButton mid = new JButton(new_imaStart);
		JButton btn = new JButton(new_icon);
		/*
		 * 
		mid.addActionListener(new ActionListener() {
			boolean is_pause = false;
	           @Override
	           public void actionPerformed(ActionEvent e) {
	        	   // start button was clicked 
	        	   if(!is_pause){
	        		   mid.setIcon(new_imaPause);
	        		   
	        		   is_pause = true;
	        	   }else{
	        		   mid.setIcon(new_imaStart);
	        		   is_pause = false;
	        	   }
	        	
	        	 
	        	  }
	       });*/
		
		
		//left.setPreferredSize(new Dimension(150, 60));
		//mid.setPreferredSize(new Dimension(200, 60));
		btn.setPreferredSize(new Dimension(150, 60));

	    //left.setVisible(true);
	    //mid.setVisible(true);
		btn.setVisible(true);

	    //mainLabel.setBorder(new LineBorder(Color.green, 0));
	    //mainLabel.setLayout(new BorderLayout());
	   
	    //mainLabel.add(left, BorderLayout.WEST);
	    //mainLabel.add(mid, BorderLayout.CENTER);
	    //mainLabel.add(right, BorderLayout.EAST);
		
		return btn;
	}
	
	
	
	public static ImageIcon ChangeImgSize(ImageIcon img, int w, int h){
		Image image = img.getImage(); 
		Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH);
		img = new ImageIcon(newimg); 

	    return img;
	}


}
