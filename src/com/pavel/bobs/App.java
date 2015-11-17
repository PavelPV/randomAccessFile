package com.pavel.bobs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class App {
	
	private String fileName = "";
	
	private RandomAccessFile raFile ;
	
	public App() {		
	}
	
	public App(String file) throws FileNotFoundException {
		this.fileName = file;		
		this.raFile = new RandomAccessFile(file, "rw");
	}
	
	public void run(String marker, String newText, int mode) throws IOException {
		List<Integer> listOfIndex = this.getAllIndexOf(marker);
		switch (mode) {
		case 0 :
			for(int i = listOfIndex.size()-1; i >= 0; --i) {
				int index = listOfIndex.get(i);
				String t = this.readFrom(index);
				this.writeInto(index, t, newText + " ");
			}
			break;
		case 1 :
			for(int i = listOfIndex.size()-1; i >= 0; --i) {
				int index = listOfIndex.get(i);
				String t = this.readFrom(index + marker.length());
				this.writeInto(index, t, newText);
				if (newText.length() < marker.length()) {
					raFile.setLength(raFile.length() - (marker.length() - newText.length()));
				}
			}
			break;
		case 2 :
			for(int i = listOfIndex.size()-1; i >= 0; --i) {
				int index = listOfIndex.get(i);
				String t = this.readFrom(index + marker.length());
				this.writeInto(index + marker.length(), t, " " + newText);
			}
			break;
		default :
			return;
		}		
				
	}
	
	public void run(String marker, String newText, int index, int mode) throws IOException {
		List<Integer> listOfIndex = this.getAllIndexOf(marker);
		if (!listOfIndex.contains(index)) {
			return;
		}
		String t;
		switch (mode) {
		case 0 : 
			t = this.readFrom(index);
			this.writeInto(index, t, newText + " ");
			break;
		case 1 : 
			t = this.readFrom(index + marker.length());
			this.writeInto(index, t, newText);
			if (newText.length() < marker.length()) {
				raFile.setLength(raFile.length() - (marker.length() - newText.length()));
			}
			break;
		case 2 :
			t = this.readFrom(index + marker.length());
			this.writeInto(index + marker.length(), t, " " + newText);
			break;
		default : 
			return;
		}
	}
	
	public String readFrom(int index) throws IOException {		
		this.raFile.seek(index);
		
		StringBuilder strBytes = new StringBuilder();
		
		int tempByte = this.raFile.read();
		
		while(tempByte!=-1) {
			strBytes.append((char)tempByte);
			tempByte = this.raFile.read();
		}
		
		String temp = strBytes.toString();
		
		return temp;
	}
	
	public void writeInto(int index, String temp, String newText) throws IOException {
		this.raFile.seek(index);
		
		this.raFile.writeBytes(newText);
		
		this.raFile.writeBytes(temp);
	}
	
	public int getFirstIndexOf(String marker) throws IOException {
		this.raFile.seek(0);
		
		String temp = this.raFile.readLine();
		
		while((temp!=null)&&(!temp.contains(marker))) {
			temp = this.raFile.readLine();
		}
		
		if (temp==null) {
			return -1;
		}
		
		int lineLength = temp.length() + 2;
		
		int posAtLine = temp.indexOf(marker);
		
		int index = (int)this.raFile.getFilePointer();
		
		index = index - (lineLength- posAtLine);
		
		return index;
	}
	
	public Map<Integer, String> getAllIndexAndLinesOf(String marker) throws IOException {
		this.raFile.seek(0);
		
		Map<Integer, String> mapOfIndex = new TreeMap<Integer, String>();
		
		String temp = this.raFile.readLine();
		
		while (temp!=null) {
			while((temp!=null)&&(!temp.contains(marker))) {
				temp = this.raFile.readLine();
			}
			
			if (temp==null) {
				break;
			}
			String sub = temp;
			while(sub.contains(marker)) {
				int lineLength = sub.length() + 2;
				
				int posAtLine = sub.indexOf(marker);
				
				int index = (int)this.raFile.getFilePointer();
				
				index = index - (lineLength- posAtLine);
				
				mapOfIndex.put(index, temp);
				
				sub = sub.substring(posAtLine + marker.length());
			}
			
			temp = this.raFile.readLine();			
		}
		return mapOfIndex;
	}
	
	public List<Integer> getAllIndexOf(String marker) throws IOException {
		this.raFile.seek(0);
		
		List<Integer> listOfIndex = new ArrayList<Integer>();
		
		String temp = this.raFile.readLine();
		
		while (temp!=null) {
			while((temp!=null)&&(!temp.contains(marker))) {
				temp = this.raFile.readLine();
			}
			
			if (temp==null) {
				break;
			}
			String sub = temp;
			while(sub.contains(marker)) {
				int lineLength = sub.length() + 2;
				
				int posAtLine = sub.indexOf(marker);
				
				int index = (int)this.raFile.getFilePointer();
				
				index = index - (lineLength- posAtLine);
				
				listOfIndex.add(index);
				
				sub = sub.substring(posAtLine + marker.length());
			}
			
			temp = this.raFile.readLine();			
		}		
		return listOfIndex;
	}
	
//	public List<String> getAllLinesOf(String marker) throws IOException {
//		this.raFile.seek(0);
//		
//		List<String> listOfLines = new ArrayList<String>();
//		
//		String temp = this.raFile.readLine();
//		
//		int count = 1;
//		
//		while(temp!=null) {
//			while((temp!=null)&&(!temp.contains(marker))) {
//				temp = this.raFile.readLine();
//				count++;
//			}
//			if (temp==null) {
//				break;
//			}
//			listOfLines.add(temp);
//			temp = this.raFile.readLine();
//			count++;
//		}
//		
//		return listOfLines;
//	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String file) {
		this.fileName = file;
	}

	public RandomAccessFile getRaFile() {
		return raFile;
	}

	public void setRaFile(RandomAccessFile raFile) {
		this.raFile = raFile;
	}

	@Override
	public String toString() {
		return "App [file=" + fileName + "]";
	}

}
