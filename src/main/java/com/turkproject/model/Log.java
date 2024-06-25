package com.turkproject.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@Table(name = "Logs")
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String ipAddress;
    private Date date;

    public Log(String username, String ipAddress, Date date) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.date = date;
    }
}