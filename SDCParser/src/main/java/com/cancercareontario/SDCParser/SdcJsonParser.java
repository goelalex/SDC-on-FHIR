package com.cancercareontario.SDCParser;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SdcJsonParser {
	public static void main(String argv[]) {
		 JSONParser parser = new JSONParser();
		 try {
			 Object obj = parser.parse(new FileReader("C:\\PERSONAL\\SDC_on_FHIR\\PKG_Lung_Surgery_withBloodtransfusionAnswerJSON.json"));
			 JSONObject jsonObject =  (JSONObject) obj;
			 System.out.println(jsonObject.toJSONString());
			 Set keySet = jsonObject.keySet();
			 Iterator it = keySet.iterator();
			 while(it.hasNext()) {
				 System.out.println(it.next());
			 }
		 }catch(Exception exception) {
			 exception.printStackTrace();
		 }
	}
}
