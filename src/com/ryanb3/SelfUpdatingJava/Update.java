package com.ryanb3.SelfUpdatingJava;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Update {

	public Update(String URL, String programName, String indexName, String currentIndex)
			throws MalformedURLException, IOException {
		String newIndex = checkIndex(URL + indexName);
		if (JOptionPane.showConfirmDialog(null, "Would you like to check for an update?") == 0 && !newIndex.equals(currentIndex)) {
			try {
				String newName = programName + " " + newIndex + ".jar";
				downloadJar(programName, newName, URL);
				launchJar(newName);
				deleteOldJar(programName, currentIndex);
				System.exit(0);
			} catch(IOException ex) {
				
			}
		} else {
			return;
		}
	}
	
	public void deleteOldJar(String programName, String oldIndex) {
		File file = new File(programName + " " + oldIndex + ".jar");
		file.delete();
	}
	
	public void launchJar(String jarName) throws IOException {
		Process ps=Runtime.getRuntime().exec(new String[]{"java", "-jar", jarName});			
	}
	
	public void downloadJar(String programName, String jarName, String URL) throws IOException {
		try (ReadableByteChannel in = Channels.newChannel(new URL(URL + programName + ".jar").openStream());
			FileChannel out = new FileOutputStream(jarName).getChannel()) {
			out.transferFrom(in, 0, Long.MAX_VALUE);
		}
	}

	public String dealWithSpaces(String toProcess) {
		String[] array = toProcess.split(" ");
		String toReturn = "";
		for (int i = 0; i < array.length; i++) {
			toReturn += array[i];
			if (i != array.length - 1) {
				toReturn += new String("\\ ");
			}
		}
		return toReturn;
	}

	public String checkIndex(String URL) {
		String content = null;
		URLConnection connection = null;
		try {
			connection = new URL(URL).openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			content = scanner.next();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return content;

	}

}
