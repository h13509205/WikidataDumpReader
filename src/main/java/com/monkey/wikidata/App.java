package com.monkey.wikidata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.monkey.wikidata.basicfunction.JSON2File;
import com.monkey.wikidata.basicfunction.JSONQualify;
import com.monkey.wikidata.basicfunction.JSONSimplify;
import com.monkey.wikidata.basicfunction.ReadWriteFileWithEncode;

/**
 * Main function
 *
 */
/*
 * id  (需要)
 * claims  (需要)
 * labels  (部分需要)
 * datatype  (需要)
 * type  (需要)
 * aliases  (部分需要)
 * descriptions  (部分需要)
 * sitelinks  (不要)
 */
public class App 
{
	private JSON2File j = new JSON2File("D:/wikidata/");
	private JSON2File jTest = new JSON2File("D:/wikidata/test");
	private JSON2File jTest2 = new JSON2File("D:/wikidata/test2");
	private JSON2File modifyEasy2See = new JSON2File("D:/wikidata/easytosee");
	
    public static void main( String[] args ) throws Exception
    {
    	App a = new App();
    	a.simplifyJSONs();
    	//a.writeJSONWithoutModify2();
    	//a.test();
    	//a.writeJSONModifiedEasy2See();
    }
    
    public void simplifyJSONs(){
    	try{
    		File p = new File("D:/wikidata/wikidatasimplep.txt");
    		p.delete();
    		p.createNewFile();
    		BufferedWriter writerp = new BufferedWriter(new OutputStreamWriter(  
                    new FileOutputStream(p), "UTF-8"));
    		File q = new File("D:/wikidata/wikidatasimpleq.txt");
    		q.delete();
    		q.createNewFile();
    		BufferedWriter writerq = new BufferedWriter(new OutputStreamWriter(  
                    new FileOutputStream(q), "UTF-8"));
    		File count = new File("D:/wikidata/wikidatacount.txt");
    		count.delete();
    		count.createNewFile();
    		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(  
                    new FileOutputStream(count), "UTF-8"));
    		
    		File file = new File("D:/wikidatadump/wikidata.json");
    		FileInputStream fis = new FileInputStream(file);
    		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
    		BufferedReader br = new BufferedReader(isr);
    		String s = br.readLine();
    		JSONParser parser=new JSONParser();
    		
    		while((s = br.readLine())!=null) {
    			if(JSONQualify.isQualified(s)) {
    				JSONObject jsonObj = (JSONObject) parser.parse(JSONQualify.qualify(s));
    				String id = JSONSimplify.getID(jsonObj);
    				String simple = JSONSimplify.simplify(jsonObj);
    				//System.out.println(id);
    				writer.write(id+"\r\n"); 
    				if(id.charAt(0) == 'P'){
    					writerp.write(simple+"\r\n");
    				}else if(id.charAt(0) == 'Q'){
    					writerq.write(simple+"\r\n");
    				}
    				
    			}
    		}
    		br.close();
    		isr.close();
    		fis.close();
    		
    		writerp.close();
    		writerq.close();
    		writer.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void writeJSONWithoutModify() {
    	try{
    		File file = new File("D:/wikidatadump/wikidata.json");
    		FileInputStream fis = new FileInputStream(file);
    		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
    		BufferedReader br = new BufferedReader(isr);
    		JSONParser parser=new JSONParser();
    		
    		String s = br.readLine();
    		for(int i=0;i<1000;i++) {
    			s = br.readLine();
    			JSONObject jsonObj = (JSONObject) parser.parse(JSONQualify.qualify(s));
    			String id = JSONSimplify.getID(jsonObj);
    			String perfix = id.substring(0, 1);
    			jTest.writeEasyToSee(perfix+"/"+id+".json", s);
    			System.out.println(id);
    		}
    		
    		br.close();
    		isr.close();
    		fis.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void writeJSONWithoutModify2() {
    	try{
    		File file = new File("D:/wikidatadump/wikidata.json");
    		FileInputStream fis = new FileInputStream(file);
    		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
    		BufferedReader br = new BufferedReader(isr);
    		JSONParser parser=new JSONParser();
    		
    		String s = br.readLine();
    		for(int i=0;i<1000;i++) {
    			s = br.readLine();
    			JSONObject jsonObj = (JSONObject) parser.parse(JSONQualify.qualify(s));
    			String id = JSONSimplify.getID(jsonObj);
    			String perfix = id.substring(0, 1);
    			jTest2.write(perfix+"/"+id+".json", s);
    			System.out.println(id);
    		}
    		
    		br.close();
    		isr.close();
    		fis.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void writeJSONModifiedEasy2See() {
    	try{
    		File file = new File("D:/wikidatadump/wikidata.json");
    		FileInputStream fis = new FileInputStream(file);
    		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
    		BufferedReader br = new BufferedReader(isr);
    		JSONParser parser=new JSONParser();
    		
    		String s = br.readLine();
    		for(int i=0;i<1000;i++) {
    			s = br.readLine();
    			JSONObject jsonObj = (JSONObject) parser.parse(JSONQualify.qualify(s));
    			String id = JSONSimplify.getID(jsonObj);
    			String perfix = id.substring(0, 1);
    			System.out.println(id);
    			System.out.println();
    			modifyEasy2See.writeEasyToSee(perfix+"/"+id+".json", JSONSimplify.simplify(jsonObj));
    		}
    		
    		br.close();
    		isr.close();
    		fis.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void test() {
    	try{
    		File file = new File("D:/wikidata/test2/P/P16.json");
    		FileInputStream fis = new FileInputStream(file);
    		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
    		BufferedReader br = new BufferedReader(isr);
    		JSONParser parser=new JSONParser();
    		
    		String s = br.readLine();
    		JSONObject jsonObj = (JSONObject) parser.parse(JSONQualify.qualify(s));
//    		System.out.println("aliase:  "+JSONSimplify.getAliase(jsonObj));
//    		System.out.println("datatype:  "+JSONSimplify.getDatatype(jsonObj));
//    		System.out.println("description:  "+JSONSimplify.getDescription(jsonObj));
//    		System.out.println("id:  "+JSONSimplify.getID(jsonObj));
//    		System.out.println("label:  "+JSONSimplify.getLabel(jsonObj));
//    		System.out.println("type:  "+JSONSimplify.getType(jsonObj));
//    		System.out.println("claims:  "+JSONSimplify.simplifyClaims(jsonObj).toJSONString());
    		System.out.println(JSONSimplify.simplify(jsonObj));
    		br.close();
    		isr.close();
    		fis.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
