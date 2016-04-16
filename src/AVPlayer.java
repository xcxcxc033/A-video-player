
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class AVPlayer {

	JFrame frame;
	JLabel lbIm1;
	JLabel lbIm2;
	BufferedImage img;
	BufferedImage[] bufferedImgs;
	private int width = 480;
	private int height = 270;
    private Integer[] locks;
    private int current = 0; 
    private int loadFrame = 100;
    private int loadedFrame = -1;
    private Object currentLock = new Object();
    private final int intervalTime = 66;//66;
	

	public void initialize(String[] args){
		int width = 480;
		int height = 270;

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		InputStream is;
		try {
			File file = new File(args[0]);
			
			is = new FileInputStream(file);

			//long len = file.length();
			long len = width*height*3;
			bufferedImgs = new BufferedImage[(int)(file.length()/len)];
            locks = new Integer[bufferedImgs.length];
            for(int i = 0; i!= locks.length; i++){
                locks[i] = new Integer(i);
            }
			byte[] bytes = new byte[(int)len];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
			}


			int ind = 0;
			for(int y = 0; y < height; y++){

				for(int x = 0; x < width; x++){

					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+height*width];
					byte b = bytes[ind+height*width*2]; 

					int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					img.setRGB(x,y,pix);
					ind++;
				}
			}
			// Use labels to display the images
			
			
			frame = new JFrame();
			GridBagLayout gLayout = new GridBagLayout();
			frame.getContentPane().setLayout(gLayout);
			
			

			JLabel lbText1 = new JLabel("Video: " + args[0]);
			lbText1.setHorizontalAlignment(SwingConstants.LEFT);
			JLabel lbText2 = new JLabel("Audio: " + args[1]);
			lbText2.setHorizontalAlignment(SwingConstants.LEFT);
			lbIm1 = new JLabel(new ImageIcon(img));
			JLabel button_start = new JLabel();
			JLabel last = new JLabel("last");
//			JLabel button_stop = new JLabel("btn_stop");


			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.CENTER;
			c.weightx = 0.5;
			c.gridx = 0;
			c.gridy = 0;
			frame.getContentPane().add(lbText1, c);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.CENTER;
			c.weightx = 0.5;
			c.gridx = 0;
			c.gridy = 1;
			frame.getContentPane().add(lbText2, c);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 2;
			frame.getContentPane().add(lbIm1, c);
			
			/** button
			*/

			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.gridx = 0;
			c.gridy = 3;
			frame.getContentPane().add(button_start, c);
			
			

			frame.pack();
			frame.setVisible(true);
			
			frame.setSize(500, 450);
			
			ButtonLayOut btn = new ButtonLayOut();
			btn.initButton_start(button_start);
//			btn.initButton_pause(button_pause);
//			btn.initButton_stop(button_stop);
			
//			java.util.List<BufferedImage> frames = allFrames(args[0]);
//				for(int i = 0; i!= frames.size(); i++){
//					BufferedImage img = frames.get(i);
//					lbIm1.setIcon(new ImageIcon(img));
//					Thread.sleep(66);
//				}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void updateFrame(){
		System.out.print(100000000);
		try{
		for(int i = 0; i!= bufferedImgs.length; i++){
			System.gc();
			synchronized (currentLock) {
				current = i;
			}
			
			BufferedImage img = bufferedImgs[i];
			while(img == null){
                synchronized(locks[i]){
                    img = bufferedImgs[i];
                }
                bufferedImgs[i] = null;
                Thread.sleep(1);
//				System.out.println(img);
			}
			lbIm1.setIcon(new ImageIcon(img));
			
//			System.out.println(i);
			Thread.sleep(intervalTime);
		}}
		catch(InterruptedException e){
			
		}
	}
	
	public void allFrames(String filename){
		
		try {
			File file = new File(filename);
			InputStream is = new FileInputStream(file);
			allFrames(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void allFrames(InputStream is){
		
		try{
			while(true){
//				System.out.println(current);
				int temp_current = 0;
				synchronized (currentLock) {
					temp_current = current;
				}
				
				int temp_loadedFrame = loadedFrame;
				for(int i = temp_loadedFrame+1; i < bufferedImgs.length && i < loadFrame + temp_current; i++){
	                synchronized(locks[i]){
	                	if(bufferedImgs[i] == null){
	                		bufferedImgs[i] = readNextFrame(is);
	                		loadedFrame = i;
	                	}
	                }
//					System.out.println(i);
				}
				if(temp_current + loadedFrame/2 < temp_loadedFrame){
					Thread.sleep(1);
				}
				
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			
		}
	}
		
	public BufferedImage readNextFrame(InputStream is) throws IOException {
		
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			//long len = file.length();
			long len = width*height*3;
			byte[] bytes = new byte[(int)len];
			
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
			}


			int ind = 0;
			for(int y = 0; y < height; y++){

				for(int x = 0; x < width; x++){

					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+height*width];
					byte b = bytes[ind+height*width*2]; 

					int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					img.setRGB(x,y,pix);
					ind++;
				}
			}
			
			return img;
			
	}
	
	public void playWAV(String filename){
		// opens the inputStream
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		// initializes the playSound Object
		PlaySound playSound = new PlaySound(inputStream);

		// plays the sound
		try {
			playSound.play();
		} catch (PlayWaveException e) {
			e.printStackTrace();
			return;
		}
	}

	public static void main(final String[] args) {
		if (args.length < 2) {
		    System.err.println("usage: java -jar AVPlayer.jar [RGB file] [WAV file]");
		    return;
		}
		
		final AVPlayer ren = new AVPlayer();
		ren.initialize(args);
		Thread imgs = new Thread() {
			public void run(){
				ren.updateFrame();
			}
		};
		
		
		Thread read = new Thread(){
			public void run(){
				ren.allFrames(args[0]);
			}
		};
		read.start();
		imgs.start();
		ren.playWAV(args[1]);
	}

}