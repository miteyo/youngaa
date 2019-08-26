package NewTimer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CalendarRead {
    void readFile(HashMap<String, ArrayList<Schedule>> mapList, ArrayList<Schedule> scheduleList) throws IOException {
        // 출력용 JsonArray의 ArrayList
        ArrayList<JsonArray> JArr = new ArrayList<>();

        try {
            // 파일을 읽어옴
            FileReader fr = new FileReader("car.txt");
            BufferedReader br = new BufferedReader(fr);

            String jsonString = new String();
            String temp = "";

            // 읽을 수 없을 때까지 json 파일을 불러와서 jsonString에 기록
            while ((temp = br.readLine()) != null) {
                jsonString += temp;
            }

            // Json 파일에서 불러온 값을 파싱하여 jObject에 넘겨줌
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(jsonString);
            JsonObject jObject = element.getAsJsonObject();

            // key를 넣어서 jObject에 쓰여진 각각의 key에 해당하는 ArrayList를 분리
            for (String key : jObject.keySet()) {
                JArr.add(jObject.get(key).getAsJsonArray());
                for (JsonArray jsa : JArr) {
                    scheduleList = new ArrayList<>();
                    for (JsonElement e2 : jsa) {
                        JsonObject j = e2.getAsJsonObject();
                        Schedule s = new Schedule();

                        s.scheduleStart = j.get("scheduleStart").getAsString();
                        s.scheduleEnd = j.get("scheduleEnd").getAsString();
                        s.scheduleContent = j.get("scheduleContent").getAsString();
                        s.scheduleStartInt = j.get("scheduleStartInt").getAsInt();
                        s.scheduleEndInt = j.get("scheduleEndInt").getAsInt();

                        scheduleList.add(s);

                    }
                    mapList.put(key, scheduleList);
                }
            }

//            // 테스트용 출력
//            for (String key : mapList.keySet()) {
//                for (int i = 0; i < mapList.size(); i++) {
//                    System.out.println(i);
//                    System.out.println("Key: " + key);
//                    System.out.println("Start: " + mapList.get(key).get(i).scheduleStart);
//                    System.out.println("End: " + mapList.get(key).get(i).scheduleEnd);
//                    System.out.println("Content: " + mapList.get(key).get(i).scheduleContent);
//                    System.out.println("=======================");
//                }
//            }
        } catch (Exception e) {

        }

    }
}
