package com.ggl.jcalendar;

import javax.swing.SwingUtilities;

import com.ggl.jcalendar.view.JCalendarFrame;

public class JCalendarTest implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new JCalendarTest());
	}

	@Override
	public void run() {
		new JCalendarFrame();
	}

}
