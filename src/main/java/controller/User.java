package controller;

public class User {

	private String email;
	private String name;
	private int pomodoroTime;
	private int pomodoroCount;
	private boolean pomodoroPause;
	private int breakTime;
	
	//Pomodoro time constants
	private static int MINUTES = 60*1000;
	private static int POMODORO_TIME = 25 * MINUTES; 
	private static int LONG_BREAK = 15 * MINUTES; 
	private static int SHORT_BREAK = 5 * MINUTES; 
		
	public User(String email, String name) {
		super();
		this.email = email;
		this.name = name;
		this.pomodoroTime = POMODORO_TIME;
		this.pomodoroCount = 0;
		this.pomodoroPause = false;
		this.breakTime = 0;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPomodoroTime() {
		return pomodoroTime;
	}	
	
	public void setPomodoroTime(int pomodoroTime) {
		this.pomodoroTime = pomodoroTime * MINUTES;
	}

	public void resetPomodoroTime() {
		this.pomodoroTime = POMODORO_TIME;
		//TODO - notify team members
	}
	
	public int getPomodoroCount() {
		return pomodoroCount;
	}

	public void setPomodoroCount(int pomodoroCount) {
		this.pomodoroCount = pomodoroCount;
	}

	public boolean isPomodoroPause() {
		return pomodoroPause;
	}

	public void setPomodoroPause(boolean pomodoroPause) {
		this.pomodoroPause = pomodoroPause;
		// TODO - notify team members
	}

	public int getBreakTime() {
		return breakTime;
	}
	
	public void setBreakTime(int breakTime) {
		this.breakTime = breakTime * MINUTES;
	}

	public void setShortBreak() {
		this.breakTime = SHORT_BREAK;
	}
	
	public void setLongBreak() {
		this.breakTime = LONG_BREAK;
	}
	
	
}
