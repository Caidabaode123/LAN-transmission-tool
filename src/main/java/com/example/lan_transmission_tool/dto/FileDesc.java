package com.example.lan_transmission_tool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDesc {
    private Date addTime;
    private String fileName;
    private String filePath;
}
