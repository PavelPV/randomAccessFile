package com.pavel.bobs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LogWindow extends JFrame {
	
	private JScrollPane sc_Log;
	private JButton b_ChangeAll;

	
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
		b_ChangeAll = new JButton("Change All");
		this.add(b_ChangeAll);		
//		sc_Log = new JScrollPane(this.getContentPane());
		for(int i = 0; i < log.size(); i++) {
			Object[] o = log.get(i);
			int ind = i;
			JPanel panel = new JPanel();
			if (o[0] instanceof Boolean) {
	//			panel.setLayout(new FlowLayout());
				JButton button = new JButton("Change");
				JCheckBox checkbox = new JCheckBox("", (boolean)o[0]);
				panel.setSize(900, 20);
				panel.add(checkbox, BorderLayout.WEST);
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
							button.setEnabled(false);
							checkbox.setEnabled(false);
							
							int diff = o[1].toString().length() - o[2].toString().length();
							for(int j = ind; j < log.size();j++) {
								if ((int)log.get(j)[4]>(int)o[4]) {
									log.get(j)[4] = (int)(log.get(j)[4]) - diff;
								}
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
				});
				checkbox.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (checkbox.isSelected()) {
							o[0] = true;
						} else {
							o[0] = false;
						}
					}
					
				});
				
			} else {
				panel.add(new JLabel(o[0].toString()));
			}
			this.add(panel);
		}
		b_ChangeAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				log.sort(new Comparator<Object[]>() {

					@Override
					public int compare(Object[] arg0, Object[] arg1) {
						return (int)arg0[4] - (int)arg1[4];
					}
					
				});
				for(int i = log.size() - 1; i >= 0; --i) {
					try {
						Object[] prop = log.get(i);
						if (prop[0] instanceof Boolean) {
							if ((boolean)prop[0]==true) {
								App app = new App(fileName);
								int index = (int) prop[4];
								app.run(prop[1].toString(), prop[2].toString(), index, mode);
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		});
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		LogWindow logW = new LogWindow();
	}
}
