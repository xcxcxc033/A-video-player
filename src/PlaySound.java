//package org.wikijava.sound.playWave;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.DataLine.Info;

/**
 * 
 * <Replace this with a short description of the class.>
 * 
 * @author Giulio
 */
public class PlaySound {

    private InputStream waveStream;
    int readBytes = 0;

    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb

    /**
     * CONSTRUCTOR
     */
    public PlaySound(InputStream waveStream) {
	//this.waveStream = waveStream;
	this.waveStream = new BufferedInputStream(waveStream);
    }

   // byte[] bytes = IOUtils.toByteArray(waveStream);

    //Peter
    
    SourceDataLine dataLine = null;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    

    
    byte[] bytes = out.toByteArray();
    
    
    //Peter
    
   // Clip clip = AudioSystem.getClip(); //cx
    
	public void play() throws PlayWaveException {

	AudioInputStream audioInputStream = null;
	try {
	    audioInputStream = AudioSystem.getAudioInputStream(this.waveStream);
	} catch (UnsupportedAudioFileException e1) {
	    throw new PlayWaveException(e1);
	} catch (IOException e1) {
	    throw new PlayWaveException(e1);
	}

	// Obtain the information about the AudioInputStream
	AudioFormat audioFormat = audioInputStream.getFormat();
	Info info = new Info(SourceDataLine.class, audioFormat);

	// opens the audio channel
	//SourceDataLine dataLine = null; //Peter Delete
	try {
	    dataLine = (SourceDataLine) AudioSystem.getLine(info);
	    dataLine.open(audioFormat, this.EXTERNAL_BUFFER_SIZE);
	} catch (LineUnavailableException e1) {
	    throw new PlayWaveException(e1);
	}
	

	// Starts the music :P
	dataLine.start();
	
	byte[] audioBuffer = new byte[this.EXTERNAL_BUFFER_SIZE];
//	int framesize = audioBuffer.length;
//	byte[] packet = new byte[framesize];
	try {
		readBytes = audioInputStream.read(audioBuffer, 0, audioBuffer.length);
		//int numBytesRead = audioInputStream.read(packet, 0, framesize);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println(this.EXTERNAL_BUFFER_SIZE/15/60/5);
	
	for(int i = 0; i < readBytes; i++){
		audioBuffer[i] = (byte)(-1 * audioBuffer[i]);
		System.out.println(audioBuffer[i]);
	}
		

	
	
	
	

//	try {
////	    while (readBytes != -1) {
////		readBytes = audioInputStream.read(audioBuffer, 0,
////			audioBuffer.length);
////		if (readBytes >= 0){
////		    dataLine.write(audioBuffer, 0, readBytes);
////		}
////	    }
//	} catch (IOException e1) {
//	    throw new PlayWaveException(e1);
//	} finally {
//	    // plays what's left and and closes the audioChannel
//		//peter
//	    dataLine.drain();
//	    dataLine.close();
//		//peter
//	}
	
	
	
	
	

	
//	for(int i = 0; i < audioBuffer.length; i++){
//		System.out.println(audioBuffer[i]);
//	}

    }
	
	//peter
		public void Stop(){
			dataLine.stop();
			readBytes = 0;
			System.out.println("stop" + readBytes);
		}
		
		public void Resume(){
			dataLine.start();
			readBytes = 0;
			System.out.println("resume" + readBytes);
		}
		//peter
	
}
