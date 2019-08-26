package NewTimer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CalendarWrite {
    void writeFile(HashMap<String, ArrayList<Schedule>> mapList) throws IOException {
        try {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter("car.txt"));
            
            JsonArray jArray = new JsonArray();
            for (String s : mapList.keySet()) {
                JsonObject jObject = new JsonObject();
                
                jObject.addProperty(s, mapList.get(s).toString());
                jArray.add(jObject);
            }
            
            Gson g = new Gson();
                                   
            bWriter.write(g.toJson(mapList));
            bWriter.close();

            System.out.println("Schedule Data Wrting has done");
        }catch (Exception e) {
            
        }
    }
}
