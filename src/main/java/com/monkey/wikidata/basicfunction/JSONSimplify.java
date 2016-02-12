package com.monkey.wikidata.basicfunction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONSimplify {
	public final static String language = "en";
	public String jsonString;
	public JSONObject jsonObject;
	private static JSONParser parser=new JSONParser();
	
	public JSONSimplify() {
		// TODO Auto-generated constructor stub
	}
	public JSONSimplify(String jsonString) {
		this.jsonString = jsonString;
		try {
			jsonObject = (JSONObject) parser.parse(jsonString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getID(JSONObject json) {
		return json.get("id") == null ? "" : (String) json.get("id");
	}
	
	public static String getType(JSONObject json) {
		return json.get("type") == null ? "" : (String) json.get("type");
	}
	
	//只有以P开头的才有datatype一说
	public static String getDatatype(JSONObject json) {
		return json.get("datatype") == null ? "" : (String) json.get("datatype");
	}
	
	public static String getLabel(JSONObject json) {
		if(!json.containsKey("labels")) return "";
		JSONObject labels = (JSONObject) json.get("labels");
		if(!labels.containsKey(language)) return "";
		JSONObject label = (JSONObject) labels.get(language);
		return label.get("value")==null?"":(String)label.get("value");
	}
	
	public static String getDescription(JSONObject json) {
		if(!json.containsKey("descriptions")) return "";
		JSONObject descriptions = (JSONObject) json.get("descriptions");
		if(!descriptions.containsKey(language)) return "";
		JSONObject description = (JSONObject) descriptions.get(language);
		return description.get("value")==null?"":(String)description.get("value");
	}
	
	public static String getAliase(JSONObject json) {
		StringBuffer sb = new StringBuffer();
		if(!json.containsKey("aliases")) return "";
		JSONObject aliases = (JSONObject) json.get("aliases");
		if(!aliases.containsKey(language)) return "";
		JSONArray aliase = (JSONArray) aliases.get(language);
		for(int i=0; i<aliase.size(); i++) {
			JSONObject temp = (JSONObject) aliase.get(i);
			sb.append(temp.get("value")).append(";");
		}
		return sb.toString();
	}
	
	/*
	 * JSONArray中的JSONObject的内容：我自定义的
	 * propertyID:P1855
	 * dataType:wikibase-item/url
	 * dataValueType:wikibase-entityid/string
	 * dataValue:Q8023/http://www.w3.org
	 */
	
	public static JSONObject assembleClaim(String propertyID, String dataType, String dataValueType, String dataValue) {
		JSONObject claim = new JSONObject();
		claim.put("propertyID", propertyID);
		claim.put("dataType", dataType);
		claim.put("dataValueType", dataValueType);
		claim.put("dataValue", dataValue);
		return claim;
	}
	public synchronized static JSONArray simplifyClaims(JSONObject json) {
		JSONArray array = new JSONArray();
		if(!json.containsKey("claims")) return array;	
		
		JSONObject claims = (JSONObject) json.get("claims");
		for(Object key:claims.keySet()) {
			JSONArray tempArray = (JSONArray) claims.get(key);
			for(int i=0; i<tempArray.size(); i++) {
				JSONObject p = (JSONObject) tempArray.get(i);
				JSONObject mainsnak = (JSONObject) p.get("mainsnak");
				String propertyID = (String) mainsnak.get("property");
				String dataType = mainsnak.get("datatype")==null?"":(String)mainsnak.get("datatype");
				String dataValueType = "";
				String dataValue = "";
				if(!dataType.equals("") && (dataType.equals("wikibase-item") || dataType.equals("wikibase-property"))&& mainsnak.get("datavalue")!=null) {
					JSONObject datavalue = (JSONObject) mainsnak.get("datavalue");
					JSONObject value = (JSONObject) datavalue.get("value");
					String entity_type = (String) value.get("entity-type");
					String numeric_id = String.valueOf(value.get("numeric-id"));
					if(entity_type.equals("item")) {
						dataValue = "Q"+numeric_id;
					}else if(entity_type.equals("property")) {
						dataValue = "P"+numeric_id;
					}
					dataValueType = entity_type;
				}else if(mainsnak.get("datavalue") != null) {
					JSONObject datavalue = (JSONObject) mainsnak.get("datavalue");
					dataValueType = (String) datavalue.get("type");
					dataValue = String.valueOf(datavalue.get("value"));
				}
				
				JSONObject simple = assembleClaim(propertyID, dataType, dataValueType, dataValue);
				array.add(simple);
			}
		}
		return array;
	}
	/*
	 * id  (需要)
	 * claims  (需要简化)
	 * labels  (部分需要)
	 * datatype  (需要)
	 * type  (需要)
	 * aliases  (部分需要)
	 * descriptions  (部分需要)
	 * sitelinks  (不要)
	 */
	public synchronized static String simplify(JSONObject json) {
		JSONObject newjson  = new JSONObject();
		
		String id = JSONSimplify.getID(json);
		JSONArray claims = JSONSimplify.simplifyClaims(json);
		String label = JSONSimplify.getLabel(json);
		String datatype = JSONSimplify.getDatatype(json);
		String type = JSONSimplify.getType(json);
		String aliases = JSONSimplify.getAliase(json);
		String description = JSONSimplify.getDescription(json);
		
		
		newjson.put("id", id);
		newjson.put("claims", claims);
		newjson.put("label", label);
		newjson.put("datatype", datatype);
		newjson.put("type", type);
		newjson.put("aliases", aliases);
		newjson.put("description", description);
		return newjson.toJSONString();
	}
}
