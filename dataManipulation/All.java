package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.Task;

public class All extends Command {

	public All(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("all", commandComponents);
	}

	@Override
	public String execute() {
		List<Task> allTasks = getAllUncompletedTasks();
		String stringTasks = getStringOfAllTasks(allTasks);
		return stringTasks;
	}

	private List<Task> getAllUncompletedTasks() {
		List<Task> uncompleted = new ArrayList<Task>();
		TotalTaskList totalList = TotalTaskList.getInstance();
		List<Task> allTasks = totalList.getList();
		
		for (int i = 0; i < uncompleted.size(); ++i) {
			Task currentTask = allTasks.get(i);
			if (!currentTask.isCompleted()) {
				uncompleted.add(currentTask);
			}
		}
		
		return uncompleted;
	}

	private String getStringOfAllTasks(List<Task> list) {
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(list);
	}

	@Override
	protected void checkValidity() {
		checkForNoComponents();
	}

}
