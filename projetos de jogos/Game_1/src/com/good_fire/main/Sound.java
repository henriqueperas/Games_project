package com.good_fire.main;

import java.applet.Applet;
import java.applet.AudioClip;

@SuppressWarnings("deprecation")
public class Sound {

	private AudioClip clip;
	
	public static final Sound musicBackground = new Sound("/musiquinha.wav");
	public static final Sound hitEffect = new Sound("/hit.wav");
	public static final Sound hit2Effect = new Sound("/hit2.wav");
	public static final Sound blipEffect = new Sound("/Blip.wav");
	public static final Sound powerupEffect = new Sound("/powerup.wav");
	public static final Sound selectEffect = new Sound("/select.wav"); 
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e) {}
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e) {}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		}catch(Throwable e) {}
	}
}
