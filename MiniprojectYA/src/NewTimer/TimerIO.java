package NewTimer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TimerIO {

    public static void main(String[] args) throws Exception {
        
        FileReader file = new FileReader("ttmmi.txt");
        BufferedReader br = new BufferedReader(file);
        String temp = "";
        String reader = "";
        
        while((temp = br.readLine()) != null) {
            reader += temp;
         }
        
        ArrayList<TimerDomain> timerList2 = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(reader);
        JsonArray jArray = element.getAsJsonArray();
        
        for(JsonElement e : jArray) {
            JsonObject jo = e.getAsJsonObject();
            
            TimerDomain td2 = new TimerDomain();
            
            td2.settodo(jo.get("todo").getAsString());
            td2.settime(jo.get("time").getAsString());
            
            timerList2.add(td2);
            
        }
        for(TimerDomain s : timerList2) {
            System.out.println(s);
        }
    
        
    }

}
