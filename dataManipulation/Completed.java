package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import userInterface.CommandType.COMMAND_TYPE;
import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;

public class Completed extends Command {

	public Completed(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.COMPLETED, commandComponents);
	}

	@Override
	public String execute() {
		List<Task> allTasks = getAllCompletedTasks();
		String stringTasks = getStringOfAllTasks(allTasks);
		return stringTasks;
	}

	private List<Task> getAllCompletedTasks() {
		List<Task> completed = new ArrayList<Task>();
		TotalTaskList totalList = TotalTaskList.getInstance();
		List<Task> allTasks = totalList.getList();
		
		for (int i = 0; i < allTasks.size(); ++i) {
			Task currentTask = allTasks.get(i);
			if (currentTask.isCompleted()) {
				completed.add(currentTask);
			}
		}
		
		return completed;
	}

	private String getStringOfAllTasks(List<Task> list) {
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(list);
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

}
