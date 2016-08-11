package controller;

import java.util.ArrayList;

public class Team {
	
	private String name;
	private ArrayList<User> users;
	
	
	public Team(String name) {
		super();
		this.name = name;
		this.users = new ArrayList<User>();
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public void addUser(User user) {
		try {
			this.users.add(user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
