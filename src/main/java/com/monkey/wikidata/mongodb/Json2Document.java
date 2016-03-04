package com.monkey.wikidata.mongodb;

import org.bson.Document;
import org.json.simple.JSONObject;

public class Json2Document {
	public static Document parse(JSONObject json) {
		Document doc = new Document();
		for(Object key : json.keySet()) {
			doc.put((String) key, json.get(key));
		}
		return doc;
	}
}
