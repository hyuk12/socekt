package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import org.example.ServerThread;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Getter

public class Room {
    private String kingName;
    private String roomName;
    private List<ServerThread> users = new ArrayList<>();

    public Room(String kingName, String roomName, List<ServerThread> users) {
        this.kingName = kingName;
        this.roomName = roomName;
        this.users = users;

    }
}
