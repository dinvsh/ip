package steve;

public class Messages {
    private Messages() {}

    public static final String WELCOME = "waddup im steve";
    public static final String PROMPT = "whatchu want from me?";
    public static final String GOODBYE = "see you next time dawg";
    public static final String LIST_HEADER = " here's your stuff cuh:";
    public static final String TASK_ADDED = "gotchu i added this task:";
    public static final String TASK_COUNT = "now you got %d tasks in the list";
    public static final String MARKED = "aight i marked it done:";
    public static final String UNMARKED = "ok i unmarked it:";
    public static final String HORIZONTAL_LINE = "____________________________________________________________";

    public static final String ERR_SYSTEM_PREFIX = "uh oh: ";
    public static final String ERR_INVALID_NUMBER = "that aint a number bro";
    public static final String ERR_TASK_NOT_FOUND = "that task number does not exist cuh";
    public static final String ERR_MISSING_INDEX = "you gotta tell me which one to %s cuh";
    public static final String ERR_EMPTY_DESC = "bro u gotta say what u wanna do";
    public static final String ERR_MISSING_BY = "bro where's the /by?";
    public static final String ERR_MISSING_FROM_TO = "bro where's the /from and /to?";
    public static final String ERR_ALREADY_MARKED = "bro its already marked done, u good?";
    public static final String ERR_NOT_MARKED = "bro it aint even marked yet, how u gonna unmark it?";
    public static final String ERR_UNKNOWN_CMD = "idk what that means cuh";

    public static final String ERR_MISSING_DATE = "bro u gotta tell me when";
    public static final String ERR_START_AFTER_END = "bro u gotta put /from before /to";
    public static final String ERR_MISSING_START_DATE = "bro u gotta tell me when it starts";
    public static final String ERR_MISSING_END_DATE = "bro u gotta tell me when it ends";
}
