package com.pavel.bobs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Window {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Change labels");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(250, 100, 900, 600);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		
		JLabel l_FileName = new JLabel("Name: ");
		l_FileName.setSize(100, 20);
		l_FileName.setLocation(20, 20);
		JTextField tf_FileName = new JTextField("D:/Pavlo/my files/db tables/1.txt", 20);
		tf_FileName.setSize(200, 25);
		tf_FileName.setLocation(120, 20);
		
		String[] modeArray = {"Before", "Replace", "After"};
		JComboBox cb_Mode = new JComboBox(modeArray);
		cb_Mode.setSize(71, 24);
		cb_Mode.setLocation(320, 20);
		cb_Mode.setSelectedIndex(1);
		
		JLabel l_PropFilePath = new JLabel("Properies file: ");
		l_PropFilePath.setSize(100, 20);
		l_PropFilePath.setLocation(20, 60);
		JTextField tf_PropFilePath = new JTextField(20);
		tf_PropFilePath.setSize(200, 25);
		tf_PropFilePath.setLocation(120, 60);
		
		JButton b_PropFilePath = new JButton("...");
		b_PropFilePath.setSize(70, 24);
		b_PropFilePath.setLocation(320, 60);
		
		JLabel l_Marker = new JLabel("Marker: ");
		l_Marker.setSize(100, 20);
		l_Marker.setLocation(500, 20);
		JTextField tf_Marker = new JTextField(20);
		tf_Marker.setSize(200, 25);
		tf_Marker.setLocation(600, 20);
		
		JLabel l_NewText = new JLabel("New text / №: ");
		l_NewText.setSize(100, 20);
		l_NewText.setLocation(500, 60);
		JTextField tf_NewText = new JTextField(20);
		tf_NewText.setSize(200, 25);
		tf_NewText.setLocation(600, 60);
		
		JTextField tf_SymbolIndex = new JTextField(5);
		tf_SymbolIndex.setSize(70, 25);
		tf_SymbolIndex.setLocation(800, 60);
		
		JButton b_Check = new JButton("Check");
		b_Check.setSize(70, 24);
		b_Check.setLocation(800, 20);
		
		JButton b_Confirm = new JButton("Confirm");
		b_Confirm.setSize(80, 30);
		b_Confirm.setLocation(406, 140);
		
		JLabel l_Prop = new JLabel("Properties: ");
		l_Prop.setSize(100, 20);
		l_Prop.setLocation(20, 100);
		JTextArea ta_Prop = new JTextArea();		
		JScrollPane sp_Prop = new JScrollPane(ta_Prop);
		sp_Prop.setSize(371, 410);
		sp_Prop.setLocation(20, 130);
		ta_Prop.setColumns(30);
		ta_Prop.setRows(23);
		
		JLabel l_ResultLog = new JLabel("Result log: ");
		l_ResultLog.setSize(100, 20);
		l_ResultLog.setLocation(500, 100);
		JTextArea ta_ResultLog = new JTextArea();
		JScrollPane sp_ResultLog = new JScrollPane(ta_ResultLog);
		sp_ResultLog.setSize(371, 410);
		sp_ResultLog.setLocation(500, 130);
		ta_ResultLog.setColumns(30);
		ta_ResultLog.setRows(23);
		
		panel.setLayout(null);
		panel.add(l_FileName);
		panel.add(tf_FileName);
		panel.add(cb_Mode);
		panel.add(l_PropFilePath);
		panel.add(tf_PropFilePath);
		panel.add(b_PropFilePath);
		panel.add(l_Marker);
		panel.add(tf_Marker);
		panel.add(l_NewText);
		panel.add(tf_NewText);
		panel.add(tf_SymbolIndex);
		panel.add(b_Check);
		panel.add(b_Confirm);
		panel.add(l_Prop);
		panel.add(sp_Prop, BorderLayout.CENTER);
		panel.add(l_ResultLog);
		panel.add(sp_ResultLog, BorderLayout.CENTER);
		
		b_Check.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!"".equals(tf_Marker.getText())) {
					try {
						App app = new App(tf_FileName.getText());					
						List<Integer> listOfIndex = app.getAllIndexOf(tf_Marker.getText());
						List<Integer> listOfLines = app.getAllLinesOf(tf_Marker.getText());
						String log = "";
						if (!listOfIndex.isEmpty()) {
							log = "Marker " + tf_Marker.getText() + " found at: \n";
							StringBuilder str = new StringBuilder(log);
							for(int i = 0; i < listOfIndex.size(); i++) {
								str.append("line : " + listOfLines.get(i) + " symbol №: " + listOfIndex.get(i) + "\n");
							}
							log = str.toString();
						} else {
							log = "Marker " + tf_Marker.getText() + " not found";
						}
						ta_ResultLog.setText(log);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		
		b_Confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if ((tf_Marker.getText()!=null) && (tf_NewText.getText()!=null)&&(!"".equals(tf_Marker.getText()))&&(!"".equals(tf_NewText.getText()))) {
					if ((tf_SymbolIndex.getText()!=null)&&(!"".equals(tf_SymbolIndex.getText()))) {
						try {
							App app = new App(tf_FileName.getText());
							int index = Integer.parseInt(tf_SymbolIndex.getText());
							app.run(tf_Marker.getText(), tf_NewText.getText(), index, cb_Mode.getSelectedIndex());
							String log = "Marker '" + tf_Marker.getText() + "' founded at: " + index + " were successfully changed to '" + tf_NewText.getText() + "'";
							ta_ResultLog.setText(log);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						try {
							App app = new App(tf_FileName.getText());
							List<Integer> listOfIndex = app.getAllIndexOf(tf_Marker.getText());
							app.run(tf_Marker.getText(), tf_NewText.getText(), cb_Mode.getSelectedIndex());
							String log = "";
							if (!listOfIndex.isEmpty()) {
								log = "Marker '" + tf_Marker.getText() + "' founded at: " + listOfIndex + " were successfully changed to '" + tf_NewText.getText() + "'";
							} else {
								log = "Marker '" + tf_Marker.getText() + "' not found";
							}
							ta_ResultLog.setText(log);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				else if ((ta_Prop.getText()!=null)&&(!"".equals(ta_Prop.getText()))) {
					String prop = ta_Prop.getText();
					StringTokenizer tokenizer = new StringTokenizer(prop, "\n");
					ta_ResultLog.setText("");
					
					while(tokenizer.hasMoreTokens()) {
						String temp = tokenizer.nextToken();
						try {
							App app = new App(tf_FileName.getText());
							String marker = temp.split("=")[0];
							String newText = temp.split("=")[1];
							List<Integer> listOfIndex = app.getAllIndexOf(marker);
							app.run(marker, newText, cb_Mode.getSelectedIndex());							
							String log = "";
							if (!listOfIndex.isEmpty()) {
								log = "Marker '" + marker + "' founded at: " + listOfIndex + " were successfully changed to '" + newText + "'\n";
							} else {
								log = "Marker '" + marker + "' not found\n";
							}
							ta_ResultLog.setText(ta_ResultLog.getText() + log);
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
				}
				else if ((tf_PropFilePath.getText()!=null)&&(!"".equals(tf_PropFilePath.getText()))) {
					try {
						BufferedReader reader = new BufferedReader(new FileReader(tf_PropFilePath.getText()));
						String line;
						while((line = reader.readLine())!=null) {
							App app = new App(tf_FileName.getText());
							String marker = line.split("=")[0];
							String newText = line.split("=")[1];
							List<Integer> listOfIndex = app.getAllIndexOf(marker);
							app.run(marker, newText, cb_Mode.getSelectedIndex());							
							String log = "";
							if (!listOfIndex.isEmpty()) {
								log = "Marker '" + marker + "' founded at: " + listOfIndex + " were successfully changed to '" + newText + "'\n";
							} else {
								log = "Marker '" + marker + "' not found\n";
							}
							ta_ResultLog.setText(ta_ResultLog.getText() + log);
						}
						reader.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		});
		
		b_PropFilePath.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileopen = new JFileChooser();
				int approve = fileopen.showDialog(null, "Open file");
				if (approve==JFileChooser.APPROVE_OPTION) {
					tf_PropFilePath.setText(fileopen.getSelectedFile().getAbsolutePath().replace('\\', '/'));
				}
			}
			
		});
		
//		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
