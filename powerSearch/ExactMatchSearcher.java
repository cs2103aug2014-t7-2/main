package powerSearch;

import dataEncapsulation.Task;
import dataManipulation.Subcommand;
import dataManipulation.TotalTaskList;

import java.util.ArrayList;
import java.util.List;

public class NearMatchSearcher{
	public static List<Task> taskList;
	public static List<Task> nearSearch(Subcommand key, List<Task> list) throws Exception {
		taskList = list;
		List<Task> answer = new ArrayList<Task>();
		switch (key.getType()){
		case TITLE:
			answer.add(nearSearchName(key.getContents()));
			return answer;
		case CATEGORY:
			return nearSearchCategory(key.getContents());
		case LOCATION:
			return nearSearchLocation(key.getContents());
		case NOTE:
			return nearSearchNote(key.getContents());
		default:
			break;
		}
		return answer;
	}
	
	private static Task nearSearchName(String key) throws Exception {
		int length1, length2, diff;
		length2 = key.length();
		for (int i = 0; i < taskList.size(); ++i) {
			length1 = taskList.get(i).getName().length();
			diff = Math.abs(length2 - length1);
			if (diff <= 2) {
				return taskList.get(i);
			}
		}
		throw new Exception("no matches found");
	}
	
	private static List<Task> nearSearchCategory(String key) throws Exception {
		List<Task> answer = new ArrayList<Task>();
		int length1, length2, diff;
		length2 = key.length();
		for (int i = 0; i < taskList.size(); ++i) {
			length1 = taskList.get(i).getCategory().length();
			diff = Math.abs(length2 - length1);
			if (diff <= 2) {
				answer.add(taskList.get(i));
			}
		}
		if(answer.isEmpty()){
		throw new Exception("no matches found");
		}
		else
			return answer;
	}
	
	private static List<Task> nearSearchNote(String key) throws Exception {
		List<Task> answer = new ArrayList<Task>();
		int length1, length2, diff;
		length2 = key.length();
		for (int i = 0; i < taskList.size(); ++i) {
			length1 = taskList.get(i).getNote().length();
			diff = Math.abs(length2 - length1);
			if (diff <= 2) {
				answer.add(taskList.get(i));
			}
		}
		if(answer.isEmpty()){
		throw new Exception("no matches found");
		}
		else
			return answer;
	}
	
	private static List<Task> nearSearchLocation(String key) throws Exception {
		List<Task> answer = new ArrayList<Task>();
		int length1, length2, diff;
		length2 = key.length();
		for (int i = 0; i < taskList.size(); ++i) {
			length1 = taskList.get(i).getLocation().length();
			diff = Math.abs(length2 - length1);
			if (diff <= 2) {
				answer.add(taskList.get(i));
			}
		}
		if(answer.isEmpty()){
		throw new Exception("no matches found");
		}
		else
			return answer;
	}
	
	public static boolean isTaskDuplicate(Task taskToCheck) {
		for(Task t : TotalTaskList.getInstance().getList()) {
			if(t.getName().toLowerCase().equals(taskToCheck.getName().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
}
