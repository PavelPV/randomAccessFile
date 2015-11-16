package com.pavel.bobs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LogWindow extends JFrame implements ActionListener {
	
	private JScrollPane sc_Log;
	
	public LogWindow() {
		super("Log");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(220, 100, 950, 650);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public LogWindow(List<Object[]> log, String fileName, int mode) {
		super("Log");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(220, 100, 950, 650);
		this.setResizable(false);
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
//		sc_Log = new JScrollPane(this.getContentPane());
		for(int i = 0; i < log.size(); i++) {
			Object[] o = log.get(i);
			JPanel panel = new JPanel();
//			panel.setLayout(new FlowLayout());
			JButton button = new JButton("Change");
			panel.setSize(900, 20);
			panel.add(new JCheckBox("", (boolean)o[0]), BorderLayout.WEST);
			panel.add(new JLabel(o[1].toString()));
			panel.add(new JLabel(o[2].toString()));
			panel.add(new JLabel(o[3].toString()));
			panel.add(new JLabel(o[4].toString()));
			panel.add(button, BorderLayout.EAST);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					try {
						App app = new App(fileName);
						int index = Integer.parseInt(o[4].toString());
						app.run(o[1].toString(), o[2].toString(), index, mode);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			});
			this.add(panel);
		}
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public static void main(String[] args) {
		LogWindow logW = new LogWindow();
	}
}
