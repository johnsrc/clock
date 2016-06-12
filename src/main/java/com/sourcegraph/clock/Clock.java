package com.sourcegraph.clock;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public final class Clock implements ActionListener {

	private javax.swing.Timer timer;
	private JLabel timeLabel = new JLabel();
	private JFrame frame;
	private String[] timezones = {"America/Los_Angeles", "America/Lima", "Africa/Johannesburg", "Europe/Berlin", "Europe/Kiev", "Asia/Dubai"};
	private String currTimezone = timezones[0];
	private List<JButton> timezoneToggles = new ArrayList<JButton>();
	
	public Clock() {
		for (String tz : timezones) {
			JButton button = new JButton(tz);
			button.addActionListener(this);
			timezoneToggles.add(button);
		}
		
		frame = new JFrame(frameTitle());
		
		createClock();
		initializeTimer();
	}
	
	// This method is called every second, or when a new timezone
	// is toggled. It should update the timeLabel with the time
	// for the currently selected timezone, and update the frame title.
	public void actionPerformed(ActionEvent ae) {
		setCurrTimezone(ae);
		frame.setTitle(frameTitle());

		timeLabel.setText("12:00:00"); // TODO
	}
	
	public void setCurrTimezone(ActionEvent ae) {
		for (int i = 0; i < timezones.length; i++) {
			if (timezoneToggles.get(i) == ae.getSource()) {
				currTimezone = timezones[i];
				return;
			}
		}
	}
	
	public String frameTitle() {
		return "Clock (" + currTimezone + ")";
	}

	public void createClock() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		timeLabel.setFont(new Font("Arial", Font.ITALIC, 25));
		JPanel panel = new JPanel();
		panel.add(timeLabel);
		for (JButton button : timezoneToggles) {
			panel.add(button);
		}
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));

		frame.getContentPane().add(panel);

		frame.setResizable(true);
		frame.setBounds(500, 500, 500, 100);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void initializeTimer() {
		if (timer == null) {
			timer = new javax.swing.Timer(1000, this);
			timer.setInitialDelay(0);
			timer.start();
		} else if (!timer.isRunning()) {
			timer.restart();
		}
	}

	public static void main(String args[]) {
		try {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new Clock();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}