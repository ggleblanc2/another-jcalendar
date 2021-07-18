package com.ggl.jcalendar.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>
 * <code>JCalendar</code> is a Swing <code>JDialog</code> that displays the
 * month as a calendar for the calendar date. The day of the calendar date is
 * highlighted. This dialog is usually created in the
 * <code>ActionListener</code> of a <code>JButton</code>
 * </p>
 * 
 * <p>
 * The user selected date is retrieved as a <code>LocalDate</code>. If the user
 * cancels the <code>JCalendar</code>, the selected date is retrieved as
 * <code>null</code>.
 * </p>
 * 
 * <p>
 * The <code>JCalendar</code> has several optional parameters. You may:
 * <ul>
 * <li>Change the panel background color.</li>
 * <li>Change the panel foreground color.</li>
 * <li>Change the calendar day highlight color.</li>
 * <li>Change the font.</li>
 * <li>Change the month names, to accommodate other languages.</li>
 * <li>Change the start day of the week. The default is the
 * <code> DayOfWeek SUNDAY</code>.</li>
 * <li>Change the three letter day of the week names, to accommodate other
 * languages and other start days of the week.</li>
 * <li>Exclude week days from being selected.</li>
 * <li>Set the earliest valid date for selection.</li>
 * <li>Set the latest valid date for selection.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Here's the simplest example of creating a <code>JCalendar</code>.
 * 
 * <pre>
 * <code>
 *    JCalendar calendar = new JCalendar(frame, LocalDate.now(), 
 *            "Transaction Date");
 *    calendar.start();
 *    LocalDate selectedDate = calendar.getSelectedDate();
 *    if (selectedDate != null) {
 *        ...
 *    }
 * </code>
 * </pre>
 * 
 * Optional parameter method calls would go between the <code>JCalendar</code>
 * instantiation and the <code>start</code> method call.
 * </p>
 * 
 * <p>
 * Here's one example of a "dark" <code>JCalendar</code>:
 * 
 * <pre>
 * <code>
 *    JCalendar calendar = new JCalendar(frame, LocalDate.now(), 
 *            "Transaction Date");
 *    calendar.setPanelBackGroundColor(Color.BLACK);
 *    calendar.setPanelForegroundColor(Color.WHITE);
 *    calendar.setDayHighlightColor(Color.RED);
 *    calendar.start();
 *    LocalDate selectedDate = calendar.getSelectedDate();
 *    if (selectedDate != null) {
 *        ...
 *    }
 * </code>
 * </pre>
 * </p>
 * 
 * @author Gilbert G. Le Blanc - Created 16 July 2021
 * 
 * @see java.awt.Color
 * @see java.awt.event.ActionListener
 * @see java.time.DayOfWeek
 * @see java.time.LocalDate
 * @see javax.swing.JButton
 * @see javax.swing.JDialog
 * @see javax.swing.JFrame
 * 
 */
public class JCalendar extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private Color panelBackGroundColor;
	private Color panelForegroundColor;
	private Color dayHighlightColor;
	
	private DayOfWeek startDayOfWeek;
	
	private DayOfWeek[] daysToExclude;
	
	private Font font;
	
	private JButton[] dayButton;
	
	private JLabel dateLabel;
	
	private JFrame frame;
	
	private LocalDate calendarDate;
	private LocalDate earliestDate;
	private LocalDate latestDate;
	private LocalDate selectedDate;
	
	private String[] dayNames;
	private String[] monthNames;

	/**
	 * This constructor creates the default <code>JCalendar</code>.
	 * 
	 * @param frame        - The <code>JFrame</code> for which this
	 *                     <code>JCalendar JDialog</code> is meant.
	 * @param calendarDate - The date for which a calendar of the month and year is
	 *                     displayed. The day of the month is highlighted.
	 * @param title        - The title of the <code>JDialog</code> which describes
	 *                     what type of date the user selects.
	 */
	public JCalendar(JFrame frame, LocalDate calendarDate, String title) {
		super(frame, title, true);
		
		this.frame = frame;
		this.calendarDate = calendarDate;
		
		this.monthNames = new String[] { "January", "February", "March", 
				"April", "May", "June", "July", "August",
				"September", "October", "November", "December" };
		this.dayNames = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		this.startDayOfWeek = DayOfWeek.SUNDAY;
		this.daysToExclude = new DayOfWeek[0];
		this.panelBackGroundColor = Color.WHITE;
		this.dayHighlightColor = Color.YELLOW;
		this.panelForegroundColor = Color.BLUE;
		this.font = getFont().deriveFont(Font.BOLD);
		this.selectedDate = null;
		this.earliestDate = null;
		this.latestDate = null;
	}
	
	/**
	 * This method creates the <code>JCalendar</code> and displays the
	 * <code>JDialog</code>.
	 */
	public void start() {
		add(createMainPanel(calendarDate), BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(frame);
		setVisible(true);
	}
	
	private JPanel createMainPanel(LocalDate calendarDate) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(panelBackGroundColor);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(createTitlePanel(calendarDate), gbc);
		
		gbc.gridy++;
		panel.add(createCalendarPanel(calendarDate), gbc);
		
		return panel;
	}
	
	private JPanel createTitlePanel(LocalDate calendarDate) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(panelBackGroundColor);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		JButton previousYearButton = new JButton("<<");
		previousYearButton.setBackground(panelBackGroundColor);
		previousYearButton.setForeground(panelForegroundColor);
		previousYearButton.setFont(font);
		panel.add(previousYearButton, gbc);
		
		previousYearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LocalDate tempDate = JCalendar.this.calendarDate.minusYears(1L);
				if (earliestDate == null) {
					updateCalendar(tempDate);
				} else if (isAfter(earliestDate, tempDate)) {
					updateCalendar(tempDate);
				}
			}
		});
		
		gbc.gridx++;
		JButton previousMonthButton = new JButton("<");
		previousMonthButton.setBackground(panelBackGroundColor);
		previousMonthButton.setForeground(panelForegroundColor);
		previousMonthButton.setFont(font);
		panel.add(previousMonthButton, gbc);
		
		previousMonthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LocalDate tempDate = JCalendar.this.calendarDate.minusMonths(1L);
				if (earliestDate == null) {
					updateCalendar(tempDate);
				} else if (isAfter(earliestDate, tempDate)) {
					updateCalendar(tempDate);
				}
			}
		});
		
		gbc.gridx++;
		gbc.weightx = 1.0;
		dateLabel = new JLabel();
		updateTitleDate(calendarDate);
		dateLabel.setBackground(panelBackGroundColor);
		dateLabel.setForeground(panelForegroundColor);
		dateLabel.setFont(font);
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(dateLabel, gbc);
		
		gbc.gridx++;
		gbc.weightx = 0.0;
		JButton nextMonthButton = new JButton(">");
		nextMonthButton.setBackground(panelBackGroundColor);
		nextMonthButton.setForeground(panelForegroundColor);
		nextMonthButton.setFont(font);
		panel.add(nextMonthButton, gbc);
		
		nextMonthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LocalDate tempDate = JCalendar.this.calendarDate.plusMonths(1L);
				if (latestDate == null) {
					updateCalendar(tempDate);
				} else if (isBefore(tempDate, latestDate)) {
					updateCalendar(tempDate);
				}
			}
		});
		
		gbc.gridx++;
		JButton nextYearButton = new JButton(">>");
		nextYearButton.setBackground(panelBackGroundColor);
		nextYearButton.setForeground(panelForegroundColor);
		nextYearButton.setFont(font);
		panel.add(nextYearButton, gbc);
		
		nextYearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LocalDate tempDate = JCalendar.this.calendarDate.plusYears(1L);
				if (latestDate == null) {
					updateCalendar(tempDate);
				} else if (isBefore(tempDate, latestDate)) {
					updateCalendar(tempDate);
				}
			}
		});
		
		return panel;
	}
	
	private boolean isAfter(LocalDate earliestDate, LocalDate calendarDate) {
		int earliestMonth = earliestDate.getMonthValue();
		int earliestYear = earliestDate.getYear();
		LocalDate testDate = LocalDate.of(earliestYear, earliestMonth, 1);
		return !testDate.isAfter(calendarDate);
	}
	
	private boolean isBefore(LocalDate calendarDate, LocalDate latestDate) {
		int latestMonth = latestDate.getMonthValue();
		int latestYear = latestDate.getYear();
		LocalDate testDate = LocalDate.of(latestYear, latestMonth, 1);
		testDate = testDate.plusMonths(1L);
		return !testDate.isBefore(calendarDate);
	}
	
	private void updateCalendar(LocalDate tempDate) {
		calendarDate = tempDate;
		updateTitleDate(calendarDate);
		updateCalendarDays(calendarDate);
	}
	
	private JPanel createCalendarPanel(LocalDate calendarDate) {
		JPanel panel = new JPanel(new GridLayout(0, 7));
		panel.setBackground(panelBackGroundColor);
		
		for (int index = 0; index < dayNames.length; index++) {
			JLabel label = new JLabel(dayNames[index]);
			label.setBackground(panelBackGroundColor);
			label.setForeground(panelForegroundColor);
			label.setFont(font);
			label.setHorizontalAlignment(JLabel.CENTER);
			panel.add(label);
		}
		
		dayButton = new JButton[42];
		for (int index = 0; index < dayButton.length; index++) {
			dayButton[index] = new JButton();
			dayButton[index].setFont(font);
			panel.add(dayButton[index]);
			
			dayButton[index].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					JButton button = (JButton) event.getSource();
					int dayOfMonth = Integer.valueOf(button.getText());
					JCalendar.this.selectedDate = LocalDate.of(
							JCalendar.this.calendarDate.getYear(), 
							JCalendar.this.calendarDate.getMonth(), dayOfMonth);
					JCalendar.this.dispose();
				}
			});
		}
		
		updateCalendarDays(calendarDate);
		
		return panel;
	}
	
	private void updateTitleDate(LocalDate calendarDate) {
		int month = calendarDate.getMonthValue() - 1;
		int year = calendarDate.getYear();
		String dateString = monthNames[month] + " " + year;
		dateLabel.setText(dateString);
	}
	
	private void updateCalendarDays(LocalDate calendarDate) {
		LocalDate startDate = LocalDate.of(calendarDate.getYear(), calendarDate.getMonth(), 1);
		int dayOfWeek = startDayOfWeek.getValue() - 1;
		long daysToSubtract = (long) startDate.getDayOfWeek().getValue();
		if (dayOfWeek <= daysToSubtract) {
			startDate = startDate.minusDays(daysToSubtract - dayOfWeek - 1);
		} else {
			startDate = startDate.minusDays(daysToSubtract - dayOfWeek + 6);
		}
		
		for (int index = 0; index < dayButton.length; index++) {
			dayButton[index].setBackground(panelBackGroundColor);
			dayButton[index].setForeground(panelForegroundColor);
			dayButton[index].setEnabled(false);
			dayButton[index].setText(" ");
			if ((earliestDate != null) && startDate.isBefore(earliestDate)) {

			} else if ((latestDate != null) && startDate.isAfter(latestDate)) {

			} else if (startDate.getYear() == calendarDate.getYear() 
					&& startDate.getMonth().equals(calendarDate.getMonth())) {
				int day = startDate.getDayOfMonth();
				dayButton[index].setText(Integer.toString(day));
				if (isIncludedDay(startDate)) {
					dayButton[index].setEnabled(true);
				}
				if (startDate.getDayOfMonth() == calendarDate.getDayOfMonth()) {
					dayButton[index].setBackground(dayHighlightColor);
				}
			}
			startDate = startDate.plusDays(1L);
		}
	}
	
	private boolean isIncludedDay(LocalDate startDate) {
		DayOfWeek startDay = startDate.getDayOfWeek();
		for (DayOfWeek day : daysToExclude) {
			if (startDay.equals(day)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method returns the selected date.
	 * 
	 * @return Selected date or <code>null</code> if <code>JDialog</code> is
	 *         cancelled.
	 */
	public LocalDate getSelectedDate() {
		return selectedDate;
	}

	/**
	 * This method sets the font.
	 * 
	 * @param - The <code>Font</code> used to display the <code>JCalendar</code>.
	 */
	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * This method sets the days to exclude from selection.
	 * 
	 * @param daysToExclude - One or more <code>DayOfWeek</code> values to exclude
	 *                      from selection.
	 */
	public void setDaysToExclude(DayOfWeek... daysToExclude) {
		this.daysToExclude = daysToExclude;
	}

	/**
	 * This method sets the <code>JCalendar</code> panel background color.
	 * 
	 * @param panelBackGroundColor - Panel background color.
	 */
	public void setPanelBackGroundColor(Color panelBackGroundColor) {
		this.panelBackGroundColor = panelBackGroundColor;
	}

	/**
	 * This method sets the <code>JCalendar</code> panel foreground color.
	 * 
	 * @param panelForeGroundColor - Panel foreground color.
	 */
	public void setPanelForegroundColor(Color panelForegroundColor) {
		this.panelForegroundColor = panelForegroundColor;
	}

	/**
	 * This method sets the <code>JCalendar</code> day highlight color. Setting this
	 * color the same as the panel background color effectively removes the day
	 * highlighting.
	 * 
	 * @param dayHighlightColor - Panel background color to highlight the day of the
	 *                          calendar date.
	 */
	public void setDayHighlightColor(Color dayHighlightColor) {
		this.dayHighlightColor = dayHighlightColor;
	}

	/**
	 * This method allows for the day names to be in a different language, or allows
	 * for the calendar week to start on any <code>DayOfWeek</code>.
	 * 
	 * @param startDayOfWeek - <code>DayOfWeek</code> that starts the week.
	 * @param dayNames       - String array of three character weekday names.
	 */
	public void setDayNames(DayOfWeek startDayOfWeek, String[] dayNames) {
		this.startDayOfWeek = startDayOfWeek;
		if (dayNames.length == 7) {
			this.dayNames = dayNames;
		} else {
			String text = "There must be 7 three letter day names.  You have "
					+ "provided " + dayNames.length;
			throw new InvalidParameterException(text);
		}
	}

	/**
	 * This method allows for the month names to be in a different language.
	 * 
	 * @param monthNames - String array of the 12 month names, starting with
	 *                   January.
	 */
	public void setMonthNames(String[] monthNames) {
		if (monthNames.length == 12) {
			this.monthNames = monthNames;
		} else {
			String text = "There must be 12 month names.  You have "
					+ "provided " + monthNames.length;
			throw new InvalidParameterException(text);
		}
	}

	/**
	 * This method sets the earliest date that the <code>JCalendar</code> will show.
	 * Useful for time sensitive dates like transaction dates.
	 * 
	 * @param earliestDate - Earliest date that can be selected. Must be less than
	 *                     or equal to the calendar date specified in the
	 *                     constructor.
	 */
	public void setEarliestDate(LocalDate earliestDate) {
		if (earliestDate.isBefore(calendarDate) || earliestDate.isEqual(calendarDate)) {
			this.earliestDate = earliestDate;
		} else {
			String text = "Earliest date " + earliestDate + " must come "
					+ "before the calendar date " + calendarDate;
			throw new InvalidParameterException(text);
		}
	}

	/**
	 * This method sets the latest date that the <code>JCalendar</code> will show.
	 * Useful for time sensitive dates like subscription dates.
	 * 
	 * @param latestDate - Latest date that can be selected. Must be greater than or
	 *                   equal to the calendar date specified in the constructor.
	 */
	public void setLatestDate(LocalDate latestDate) {
		if (latestDate.isAfter(calendarDate) || latestDate.isEqual(calendarDate)) {
			this.latestDate = latestDate;
		} else {
			String text = "Latest date " + latestDate + " must come "
					+ "after the calendar date " + calendarDate;
			throw new InvalidParameterException(text);
		}
	}
	
}
