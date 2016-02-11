package com.monkey.wikidata.basicfunction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Stack;

public class JSON2File {
	private String folderLocation;
	private final String space = " ";
	private final String tab = "    ";
	private final String enter = "\r\n";
	public JSON2File(String location) {
		this.folderLocation = location;
		File file = new File(location);
		if(!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}
	
	
	public synchronized void writeEasyToSee(String filename, String json) throws Exception {
		int jsonLen = json.length();
		Stack<Character> stack = new Stack<Character>();
		int level = 0;
		File file = new File(folderLocation+"/"+filename);
		FileOutputStream fos = new FileOutputStream(file, false);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
		BufferedWriter bw = new BufferedWriter(osw);
		StringBuffer sb = new StringBuffer();
		if(!file.exists()) {
			file.createNewFile();
		}
		for(int i = 0; i < jsonLen; i++) {
			if(json.charAt(i) == '{') {
				stack.push('{');
				level = stack.size();
				sb.append('{').append(enter);
				bw.write(sb.toString());
				sb.delete(0, sb.length());
				for(int j = 0; j < level; j++) {
					sb.append(tab);
				}
			}else if(json.charAt(i) == '}') {
				stack.pop();
				level = stack.size();
				sb.append(enter);
				bw.write(sb.toString());
				sb.delete(0, sb.length());
				for(int j = 0; j < level; j++) {
					sb.append(tab);
				}
				sb.append('}').append(enter);
				bw.write(sb.toString());
				sb.delete(0, sb.length());
				for(int j = 0; j < level; j++) {
					sb.append(tab);
				}
			}else if(json.charAt(i) == ',') {
				sb.append(',').append(enter);
				bw.write(sb.toString());
				sb.delete(0, sb.length());
				for(int j = 0; j < level; j++) {
					sb.append(tab);
				}
			}else{
				sb.append(json.charAt(i));
			}
		}
		bw.close();
		osw.close();
		fos.close();
	}
	
	public synchronized void write(String name, String json) {
		try{
			File file = new File(folderLocation+"/"+name);
			FileOutputStream fos = new FileOutputStream(file, false);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(json);
			bw.close();
			osw.close();
			fos.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
