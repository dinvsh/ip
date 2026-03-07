package steve;

/**
 * Centralised store for all user-facing strings used by the Steve chatbot.
 * Contains constants for status messages, prompts, and error messages.
 * This class is not instantiable. All members are static constants.
 */
public class Messages {
    private Messages() {}

    /** Greeting shown when Steve starts. */
    public static final String WELCOME = "waddup im steve";
    /** Prompt shown after the greeting. */
    public static final String PROMPT = "whatchu want from me?";
    /** Message shown when the user exits. */
    public static final String GOODBYE = "see you next time dawg";
    /** Header shown before listing all tasks. */
    public static final String LIST_HEADER = " here's your stuff cuh:";
    /** Confirmation shown after a task is added. */
    public static final String TASK_ADDED = "gotchu i added this task:";
    /** Format string showing the updated task count after an addition. */
    public static final String TASK_COUNT = "now you got %d tasks in the list";
    /** Confirmation shown after marking a task as done. */
    public static final String MARKED = "aight i marked it done:";
    /** Confirmation shown after unmarking a task. */
    public static final String UNMARKED = "ok i unmarked it:";
    /** Confirmation shown after deleting a task. */
    public static final String TASK_REMOVED = "aight i removed this task:";
    /** Header shown before listing search results. */
    public static final String FIND_HEADER = " here are the matching tasks in your list cuh:";
    /** Horizontal line used to visually separate output blocks. */
    public static final String HORIZONTAL_LINE = "____________________________________________________________";

    /** Prefix prepended to all error messages. */
    public static final String ERR_SYSTEM_PREFIX = "uh oh: ";
    /** Error shown when a non-numeric task index is supplied. */
    public static final String ERR_INVALID_NUMBER = "that aint a number bro";
    /** Error shown when the task index is out of range. */
    public static final String ERR_TASK_NOT_FOUND = "that task number does not exist cuh";
    /** Error format shown when no index is given for mark/unmark/delete. */
    public static final String ERR_MISSING_INDEX = "you gotta tell me which one to %s cuh";
    /** Error shown when a task description is empty. */
    public static final String ERR_EMPTY_DESC = "bro u gotta say what u wanna do";
    /** Error shown when /by is missing from a deadline command. */
    public static final String ERR_MISSING_BY = "bro where's the /by?";
    /** Error shown when /from or /to is missing from an event command. */
    public static final String ERR_MISSING_FROM_TO = "bro where's the /from and /to?";
    /** Error shown when trying to mark an already-done task. */
    public static final String ERR_ALREADY_MARKED = "bro its already marked done, u good?";
    /** Error shown when trying to unmark a task that isn't marked. */
    public static final String ERR_NOT_MARKED = "bro it aint even marked yet, how u gonna unmark it?";
    /** Error shown when the command is not recognised. */
    public static final String ERR_UNKNOWN_CMD = "idk what that means cuh";
    /** Error shown when a date is missing from deadline/event input. */
    public static final String ERR_MISSING_DATE = "bro u gotta tell me when";
    /** Error shown when /from appears after /to in an event command. */
    public static final String ERR_START_AFTER_END = "bro u gotta put /from before /to";
    /** Error shown when the start date is missing from an event command. */
    public static final String ERR_MISSING_START_DATE = "bro u gotta tell me when it starts";
    /** Error shown when the end date is missing from an event command. */
    public static final String ERR_MISSING_END_DATE = "bro u gotta tell me when it ends";
    /** Error shown when no keyword is given to the find command. */
    public static final String ERR_EMPTY_FIND = "bro u gotta tell me what to find";
    /** Error shown when a date cannot be parsed as yyyy-MM-dd. */
    public static final String ERR_BAD_DATE_FORMAT = "bro those dates ain't right. use yyyy-mm-dd format cuh.";
}