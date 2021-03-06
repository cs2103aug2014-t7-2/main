package dataManipulation;

//@author A0126720N

import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.ActionException.ErrorLocation;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.NoResultException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;
import fileIo.FileIo;

public class Finish extends Command {

	public Finish(List<Subcommand> commandComponents) 
			throws BadCommandException, BadSubcommandException, 
			BadSubcommandArgException {
		super(COMMAND_TYPE.FINISH, commandComponents);
		boolean hasDateSubcommand = hasSubcommandType(Subcommand.TYPE.DATE);
		if (hasDateSubcommand) {
			parseDateToStartAndEnd();
		}
	}
	
	@Override
	public String undo() throws Exception {
		Unfinish reverse = new Unfinish(subcommands);
		return reverse.literalUnfinish();
	}

	@Override
	public String execute() throws Exception {
		Task markedTask = markAsCompleted();
		ezCMessages messages = ezCMessages.getInstance();
		String stringTask = messages.getFinishMessage(markedTask);
		FileIo IoStream = FileIo.getInstance();
		IoStream.rewriteFile();
		return stringTask;
	}
	
	public Task markAsCompleted() throws Exception {
		TotalTaskList masterList = TotalTaskList.getInstance();
		List<Task> taskToBeMarked = ExactMatchSearcher.literalSearch(subcommands, masterList.getNotCompleted());
		if (taskToBeMarked.size() > 1) {
			throw new ActionException(taskToBeMarked, ErrorLocation.FINISH, subcommands);
		} else if (taskToBeMarked.size() == 0) {
			throw new Exception("no match found");
		}
		Task toComplete = taskToBeMarked.get(0);
		finishTask(toComplete);
		
		return toComplete;
	}
	
	private void finishTask(Task toComplete) throws Exception {
		TotalTaskList masterList = TotalTaskList.getInstance();
		List<Task> toSearchThrough = masterList.getNotCompleted();
		boolean wasSuccess = toSearchThrough.remove(toComplete);
		if (!wasSuccess) {
			toSearchThrough = masterList.getOverdue();
			wasSuccess = toSearchThrough.remove(toComplete);
			if (!wasSuccess) {
				throw new Exception("no match found");
			}
		}
		
		toComplete.setComplete();
		masterList.addCompleted(toComplete);
	}

	public void addEditedTask(Task oldTask, Task newTask) {
		TotalTaskList list = TotalTaskList.getInstance();
		FileIo IoStream = FileIo.getInstance();
		
		list.removeNotCompleted(oldTask);
		list.addCompleted(newTask);
		IoStream.rewriteFile();
	}
	

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

	public String literalFinish() throws BadCommandException, 
		BadSubcommandException, BadSubcommandArgException, Exception {
		TotalTaskList list = TotalTaskList.getInstance();
		List<Task> completed = list.getCompleted();
		Task perfectMatch = findLiteralMatch(subcommands, completed);

		ezCMessages messages = ezCMessages.getInstance();
		return messages.getFinishMessage(perfectMatch);
	}
	
	private Task findLiteralMatch(List<Subcommand> subcommands, List<Task> list) 
			throws BadCommandException, 
			BadSubcommandException, BadSubcommandArgException, Exception {

		Task match = (new Add(subcommands)).buildTask(subcommands);
		TotalTaskList totalList = TotalTaskList.getInstance();
		List<Task> currentTasks = totalList.getList();

		for (int i = 0; i < currentTasks.size(); ++i) {
			Task current = currentTasks.get(i);
			if (current.isEqualTask(match)) {
				current.setComplete();
				currentTasks.remove(current);
				totalList.addCompleted(current);
				return current;
			}
		}
		
		currentTasks = TotalTaskList.getInstance().getOverdue();
		for (int i = 0; i < currentTasks.size(); ++i) {
			Task current = currentTasks.get(i);
			if (current.isEqualTask(match)) {
				current.setComplete();
				currentTasks.remove(current);
				totalList.addCompleted(current);
				return current;
			}
		}
		
		throw new NoResultException("The task that you are trying to finish cannot be found.");
	}

}
