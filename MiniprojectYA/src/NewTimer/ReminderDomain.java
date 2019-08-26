package NewTimer;

import java.util.ArrayList;

import com.google.gson.Gson;

public class ReminderDomain {
	String priority;
	String todo;
	Boolean checkValue;
	int day;
	public ReminderDomain() {
		this.checkValue = false;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	public Boolean getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(Boolean checkValue) {
		this.checkValue = checkValue;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	public String toString() {
		Gson gSon = new Gson();
		return gSon.toJson(this);
	}
	
}
