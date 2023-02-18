package org.example.entity;

public class Room {
	private final String name;
	private final String id;
	
	public Room(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
}
