package com.example.lan_transmission_tool.controller;

import com.example.lan_transmission_tool.config.ResourceConfig;
import com.example.lan_transmission_tool.dto.FileDesc;
import lombok.AllArgsConstructor;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {

    private final ResourceConfig resourceConfig;
    private final RedissonClient redisson;

    @PostMapping
    public synchronized String upload(@RequestParam("file")MultipartFile file,
                         @RequestParam(required = false) String name) throws IOException {

        String filename = file.getOriginalFilename();
        name = StringUtils.hasLength(name)?name:filename;
        filename = filename.substring(0,filename.lastIndexOf("."))
                + System.currentTimeMillis()
                +filename.substring(filename.lastIndexOf("."),filename.length());

        File localFile = new File(resourceConfig.getFileRootPath()+filename);

        file.transferTo(localFile);

        RList<Object> transmission_tool_files = redisson.getList(resourceConfig.getResourceRedisKey());

        Integer fileCount = resourceConfig.getFileCount();
        int size = transmission_tool_files.size();
        if (size>=fileCount){
            int deleteCount = size - fileCount + 1;
            for (int i = 0; i < deleteCount; i++) {
                Object o = transmission_tool_files.get(i);
                FileDesc oldFile = (FileDesc) o;
                new File(resourceConfig.getFileRootPath()+oldFile.getFilePath().substring("/file/".length())).delete();
            }
            transmission_tool_files.trim(deleteCount,size);
        }
        transmission_tool_files.add(new FileDesc(new Date(),name,"/file/"+filename));
        return localFile.getPath();
    }

    @GetMapping("/all")
    public List<Object> getAll(){
        RList<Object> list = redisson.getList(resourceConfig.getResourceRedisKey());
        Collections.reverse(list);
        return list;
    }
}
