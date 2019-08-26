package NewTimer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ReminderView {// 출력

    void reading(ArrayList<ReminderDomain> reminderList) throws IOException {
        try {
            FileReader fr = new FileReader("test2.txt");
            BufferedReader br = new BufferedReader(fr);
            
            String jsonString = new String();
            String temp = "";
            
            while ((temp = br.readLine()) != null) {
                jsonString += temp;
            }
            
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(jsonString);
            JsonArray jArray = element.getAsJsonArray();
            
            for (JsonElement e : jArray) {
                JsonObject jObject = e.getAsJsonObject();
                ReminderDomain rd = new ReminderDomain();
                rd.setTodo(jObject.get("todo").getAsString());
                rd.setPriority(jObject.get("priority").getAsString());
                rd.setCheckValue(jObject.get("checkValue").getAsBoolean());
                rd.setDay(jObject.get("day").getAsInt());
                
                reminderList.add(rd);
            }
//            for (ReminderDomain s : reminderList) {
//                System.out.println(s);
//            }
//            
        }catch (Exception e) {
        }
        
    }
}
