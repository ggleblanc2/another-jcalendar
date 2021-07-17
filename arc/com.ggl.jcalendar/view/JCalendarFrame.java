package com.ggl.jcalendar.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JCalendarFrame {
	
	private final JFrame frame;
	
	private DateButtonField birthDateButtonField;
	private DateButtonField subscriptionDateButtonField;
	private DateButtonField transactionDateButtonField;
	
	public JCalendarFrame() {
		frame = new JFrame("JCalendar Test GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = createMainPanel();
		frame.add(mainPanel, BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		
		System.out.println(frame.getSize());
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1.0;
		gbc.gridy = 0;
		
		LocalDate currentDate = LocalDate.now();
		LocalDate birthDate = LocalDate.now().minusYears(21L);
		
		birthDateButtonField = new DateButtonField(panel, gbc,
				"Birth Date:", birthDate);
		panel = birthDateButtonField.getPanel();
		birthDateButtonField.getCalendarButton()
				.addActionListener(new BirthDateListener());
		
		subscriptionDateButtonField = new DateButtonField(panel, gbc,
				"Subscription Date:", currentDate);
		panel = subscriptionDateButtonField.getPanel();
		String df = updateDateField(currentDate);
		String dl = updateDateLabel(currentDate);
		subscriptionDateButtonField.getDateField().setText(df);
		subscriptionDateButtonField.getDateLabel().setText(dl);
		subscriptionDateButtonField.getCalendarButton()
				.addActionListener(new SubscriptionDateListener());
		
		transactionDateButtonField = new DateButtonField(panel, gbc,
				"Transaction Date:", currentDate);
		panel = transactionDateButtonField.getPanel();
		df = updateDateField(currentDate);
		dl = updateDateLabel(currentDate);
		transactionDateButtonField.getDateField().setText(df);
		transactionDateButtonField.getDateLabel().setText(dl);
		transactionDateButtonField.getCalendarButton()
				.addActionListener(new TransactionDateListener());
		
		Dimension d = panel.getPreferredSize();
		panel.setPreferredSize(new Dimension(500, d.height));
		
		return panel;
	}
	
	public String updateDateField(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
		String dateString = date.format(formatter);
		return dateString;
	}
	
	public String updateDateLabel(LocalDate date) {
		StringBuilder builder = new StringBuilder();
		builder.append("The date selected is ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
		String dateString = date.format(formatter);
		builder.append(dateString);
		builder.append(".");
		return builder.toString();
	}
	
	public class DateButtonField {
		
		private final JButton calendarButton;
		
		private final JLabel dateLabel;
		
		private final JPanel panel;
		
		private final JTextField dateField;
		
		public DateButtonField(JPanel panel, GridBagConstraints gbc, 
				String labelText, LocalDate currentDate) {
			this.panel = panel;
			
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy++;
			JLabel label = new JLabel(labelText);
			panel.add(label, gbc);
			
			gbc.gridx++;
			gbc.weightx = 0.0;
			dateField = new JTextField(6);
			panel.add(dateField, gbc);
			
			gbc.gridx++;
			calendarButton = new JButton();
			Dimension d = dateField.getPreferredSize();
			calendarButton.setPreferredSize(new Dimension(d.height, d.height));
			panel.add(calendarButton, gbc);
			
			gbc.gridwidth = 3;
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.weightx = 1.0;
			dateLabel = new JLabel(" ");
			panel.add(dateLabel, gbc);
		}

		public JButton getCalendarButton() {
			return calendarButton;
		}

		public JLabel getDateLabel() {
			return dateLabel;
		}

		public JPanel getPanel() {
			return panel;
		}

		public JTextField getDateField() {
			return dateField;
		}
		
	}
	
	public class BirthDateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JCalendar calendar = new JCalendar(frame, 
					LocalDate.now().minusYears(65L), 
					"Birth Date");
			calendar.setPanelBackGroundColor(new Color(0xA52A2A));
			calendar.setPanelForegroundColor(new Color(0xC0C0C0));
			calendar.setDayHighlightColor(new Color(0x808000));
			calendar.setFont(frame.getFont().deriveFont(Font.BOLD, 24f));
			calendar.setLatestDate(LocalDate.now().minusYears(21L));
			calendar.start();
			LocalDate selectedDate = calendar.getSelectedDate();
			if (selectedDate != null) {
				String df = updateDateField(selectedDate);
				String dl = updateDateLabel(selectedDate);
				birthDateButtonField.getDateField().setText(df);
				birthDateButtonField.getDateLabel().setText(dl);
			}
		}
		
	}
	
	public class SubscriptionDateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JCalendar calendar = new JCalendar(frame, LocalDate.now(), 
					"Subscription Date");
			calendar.setPanelBackGroundColor(Color.BLACK);
			calendar.setPanelForegroundColor(Color.WHITE);
			calendar.setDayHighlightColor(Color.RED);
			calendar.setEarliestDate(LocalDate.now());
			calendar.setLatestDate(LocalDate.now().plusYears(1L));
			calendar.start();
			LocalDate selectedDate = calendar.getSelectedDate();
			if (selectedDate != null) {
				String df = updateDateField(selectedDate);
				String dl = updateDateLabel(selectedDate);
				subscriptionDateButtonField.getDateField().setText(df);
				subscriptionDateButtonField.getDateLabel().setText(dl);
			}
		}
		
	}
	
	public class TransactionDateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JCalendar calendar = new JCalendar(frame, LocalDate.now(), 
					"Transaction Date");
			calendar.setEarliestDate(LocalDate.now().minusMonths(3L));
			calendar.setLatestDate(LocalDate.now().plusMonths(1L));
			calendar.setDaysToExclude(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
			calendar.start();
			LocalDate selectedDate = calendar.getSelectedDate();
			if (selectedDate != null) {
				String df = updateDateField(selectedDate);
				String dl = updateDateLabel(selectedDate);
				transactionDateButtonField.getDateField().setText(df);
				transactionDateButtonField.getDateLabel().setText(dl);
			}
		}
		
	}

}
