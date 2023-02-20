package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.ServerThread;

import java.net.Socket;
import java.util.List;

@Getter
@AllArgsConstructor
public class Room {
    private String kingName;
    private String roomName;
    private int socketPort;

}
