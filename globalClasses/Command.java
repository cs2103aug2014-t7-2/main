package globalClasses;

import globalClasses.CommandComponent.COMPONENT_TYPE;
import globalClasses.CommandComponent.FREQUENCY;

import java.util.List;

/**
 * A class for holding the command in an easy-to-manage way.
 * Note that each command type is associated with specific command component
 * types, as follows:
 *
 * ADD: NAME, CATEGORY, END, LOCATION, NOTE, START
 * 
 * All: NONE
 * 
 * CATEGORY: CATEGORY
 * 
 * CHANGE_DATE_TYPE: DATE_TYPE
 * 
 * COMPLETED: NONE
 * 
 * EDIT: NAME, CATEGORY, END, LOCATION, NOTE, START, TITLE
 * 
 * FINISH: NAME
 * 
 * HELP: NONE
 * 
 * NOTE: NAME
 * 
 * REMOVE: NAME
 * 
 * REPEAT: NAME, FREQUENCY, START, END
 * 
 * SEARCH: 	TEXT, CATEGORY, DATE, END, LOCATION, NOTE, START, TITLE, LINK, PAREN
 * 
 * SORT: START or END
 * 
 * TODAY: NONE
 * 
 * UNDO: NONE
 * 
 * @author Natalie Rawle
 *
 */

public class Command {
	private COMMAND_TYPE type;
	private List<CommandComponent> components;
	
	public enum COMMAND_TYPE {
		ADD, ALL, CATEGORY, CHANGE_DATE_TYPE, COMPLETED, EDIT, END, FINISH, 
		HELP, NOTE, REMOVE, REPEAT, SEARCH, SORT, TEXT, TODAY, UNDO,
		INVALID
	}
	
	public Command(COMMAND_TYPE commandType, 
			List<CommandComponent> commandComponents) 
					throws IllegalArgumentException {
		type = commandType;
		components = commandComponents;
		
		checkValidity();
	}
	
	public COMMAND_TYPE getType() {
		return type;
	}
	
	public List<CommandComponent> getComponents() {
		return components;
	}
	
	private void checkValidity() throws IllegalArgumentException {
		switch (type) {
			case ADD :
				checkAddComponents();
				break;
			case ALL :
				checkForNoComponents();
				break;
			case CATEGORY :
				checkCategoryComponents();
				break;
			case CHANGE_DATE_TYPE :
				checkChangeDateTypeComponents();
				break;
			case COMPLETED :
				checkForNoComponents();
				break;
			case EDIT :
				checkEditComponents();
				break;
			case FINISH :
				checkForOnlyTitleComponent();
				break;
			case HELP :
				checkForNoComponents();
				break;
			case NOTE :
				checkForOnlyTitleComponent();
				break;
			case REMOVE :
				checkForOnlyTitleComponent();
				break;
			case REPEAT :
				checkRepeatComponents();
				break;
			case SEARCH :
				checkSearchComponents();
				break;
			case SORT :
				checkSortComponents();
				break;
			case TODAY :
				checkForNoComponents();
				break;
			case UNDO :
				checkForNoComponents();
				break;
			default :
				throw new IllegalArgumentException("invalid command type");
		}
	}

	// should only have NAME, BEGIN, CATEGORY, DEADLINE, END, LOCATION, NOTE, START
	private void checkAddComponents() throws IllegalArgumentException {
		for (int i = 0; i < components.size(); ++i) {
			CommandComponent component = components.get(i);
			
			switch (component.getType()) {
				case NAME :
					break; // valid
				case CATEGORY :
					break;
				case END :
					break;
				case LOCATION :
					break;
				case NOTE :
					break;
				case START :
					break;
				default :
					throw new IllegalArgumentException("invalid subcommand");
			}
		}
		
	}

	// should only have CATEGORY
	private void checkCategoryComponents() throws IllegalArgumentException {
		checkForComponentAmount(1);
		boolean isComponentCategory = 
				checkForSpecificComponent(CommandComponent.COMPONENT_TYPE.CATEGORY);
			
			if (!isComponentCategory) {
				throw new IllegalArgumentException("invalid subcommand");
			}
	}

	// should only have DATE_TYPE
	private void checkChangeDateTypeComponents() throws IllegalArgumentException {
		checkForComponentAmount(1);
		boolean isComponentDateType = 
			checkForSpecificComponent(CommandComponent.COMPONENT_TYPE.DATE_TYPE);
		
		if (!isComponentDateType) {
			throw new IllegalArgumentException("invalid subcommand");
		}
	}

	// should only have everything in ADD plus TITLE
	private void checkEditComponents() throws IllegalArgumentException {
		for (int i = 0; i < components.size(); ++i) {
			CommandComponent component = components.get(i);
			
			switch (component.getType()) {
				case NAME :
					break; // valid
				case CATEGORY :
					break;
				case END :
					break;
				case LOCATION :
					break;
				case NOTE :
					break;
				case START :
					break;
				case TITLE :
					break;
				default :
					throw new IllegalArgumentException("invalid subcommand");
			}
		}
	}
	
	// should only have TEXT, CATEGORY, DATE, END, LOCATION, NOTE, START, 
	// TITLE, LINK, PAREN
	private void checkSearchComponents() throws IllegalArgumentException {
		for (int i = 0; i < components.size(); ++i) {
			CommandComponent component = components.get(i);
			
			switch (component.getType()) {
				case LINK :	 // valid
					break;
				case TITLE :
					break;
				case CATEGORY :
					break;
				case DATE :
					break;
				case END :
					break;
				case LOCATION :
					break;
				case NOTE :
					break;
				case PAREN :
					break;
				case START :
					break;
				case TEXT:
					break;
				default :
					throw new IllegalArgumentException("invalid subcommand");
			}
		}
	}

	// should only have TITLE
	private void checkForOnlyTitleComponent() throws IllegalArgumentException {
		checkForComponentAmount(1);
		boolean hasTitleComponent =
				checkForSpecificComponent(CommandComponent.COMPONENT_TYPE.NAME);
		
		if (!hasTitleComponent) {
			throw new IllegalArgumentException("invalid subcommand");
		}
	}

	// should only have TITLE, FREQUENCY or TITLE, FREQUENCY, START, END (if repeated just once)
	private void checkRepeatComponents() throws IllegalArgumentException {
		try {
			checkRepeatTwoComponents();
			checkFrequencyIsNotOnly();
		} catch (IllegalArgumentException e) {
			checkRepeatFourComponents();
			checkFrequencyIsOnly();
		}
		
	}

	private void checkFrequencyIsNotOnly() {
		for (int i = 0; i < components.size(); ++i) {
			CommandComponent component = components.get(i);
			
			if (component.getType() == COMPONENT_TYPE.FREQUENCY) {
				if (component.getContents().equalsIgnoreCase(FREQUENCY.ONCE.toString())) {
					throw new IllegalArgumentException("too few subcommands");
				}
			}
		}
	}

	private void checkFrequencyIsOnly() {
		for (int i = 0; i < components.size(); ++i) {
			CommandComponent component = components.get(i);
			
			if (component.getType() == COMPONENT_TYPE.FREQUENCY) {
				if (!component.getContents().equalsIgnoreCase(FREQUENCY.ONCE.toString())) {
					throw new IllegalArgumentException("too many subcommands");
				}
			}
		}
	}

	private void checkRepeatFourComponents() {
		checkForComponentAmount(4);
		
		for (int i = 0; i < components.size(); ++i) {
			CommandComponent component = components.get(i);
			
			switch (component.getType()) {
				case FREQUENCY :	 // valid
					break;
				case NAME :
					break;
				case START :
					break;
				case END :
					break;
				default :
					throw new IllegalArgumentException("invalid subcommand");
			}
		}
	}

	private void checkRepeatTwoComponents() {
		checkForComponentAmount(2);
		
		for (int i = 0; i < components.size(); ++i) {
			CommandComponent component = components.get(i);
			
			switch (component.getType()) {
				case FREQUENCY :	 // valid
					break;
				case NAME :
					break;
				default :
					throw new IllegalArgumentException("invalid subcommand");
			}
		}
	}

	// Should only have START or END
	private void checkSortComponents() throws IllegalArgumentException {
		checkForComponentAmount(1);
		
		boolean hasStart = 
				checkForSpecificComponent(CommandComponent.COMPONENT_TYPE.START);
		boolean hasEnd = 
				checkForSpecificComponent(CommandComponent.COMPONENT_TYPE.END);
		
		if (!hasStart && !hasEnd) {
			throw new IllegalArgumentException("invalid subcommand");
		}
	}

	private void checkForNoComponents() throws IllegalArgumentException {
		if (!components.isEmpty()) {
			throw new IllegalArgumentException("too many subcommands");
		}
	}
	
	private boolean checkForSpecificComponent(COMPONENT_TYPE givenType) {
		for (int i = 0; i < components.size(); ++i) {
			CommandComponent currentComponent = components.get(i);
			
			if (currentComponent.getType() == givenType) {
				return true;
			}
		}
		
		return false;
	}
	
	private void checkForComponentAmount(int amount) throws IllegalArgumentException {
		if (components.size() > amount) {
			throw new IllegalArgumentException("too many subcommands");
		} else if (components.size() < amount) {
			throw new IllegalArgumentException("not enough information");
		}
	}
	
	@Override
	public String toString() {
		String formattedType = "Command Type: " + type;
		String newLine = "\n";
		String fullMessage = new String(formattedType + newLine);
		
		for (int i = 0; i < components.size(); ++i) {
			fullMessage = fullMessage + components.get(i).toString() + newLine;
		}
		
		return fullMessage;
	}
}