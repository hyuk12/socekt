package org.example.entity;

import java.util.ArrayList;
import java.util.List;

import org.example.ServerThread;

import com.google.gson.annotations.Expose;

import lombok.Getter;

@Getter
public class Room {
    private String kingName;
    private String roomName;
    private List<ServerThread> users = new ArrayList<>();

    
    public Room(String kingName, String roomName) {
        this.kingName = kingName;
        this.roomName = roomName;
        this.users = new ArrayList<>();

    }
}
