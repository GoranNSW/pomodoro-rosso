package controller;

import java.util.List;

public class Team {
	
	private String name;
	private List<User> users;
	
	
	public Team(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	public boolean deleteUser(String email) {
		boolean isFound = false;
		for (User user : this.users) {
			if (user.getEmail().equals(email)) {
				isFound = this.users.remove(user);
			}
		}
		return isFound; //TODO - if found show success, otherwise show not found 
	}

	
}
