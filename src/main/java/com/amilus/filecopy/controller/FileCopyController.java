package com.amilus.filecopy.controller;

import com.amilus.filecopy.service.FileCopyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/fileservice")
@Log4j2

public class FileCopyController {
    @Autowired
    private FileCopyService fileCopyService;

    @GetMapping("/copyFile/{fileName}")
    public String copyFile(@PathVariable String fileName) throws IOException {
        if (fileCopyService.copyFile(fileName)){
            return "File: "+fileName+" successfully uploaded to storage and deleted from openshift volume...";
        }
        else {
            return "Error: Uploading video file or deleting file from volume. Please, check the logs !! ";
        }
    }


    @GetMapping("/copyAllVideos")
    public String copyAllVideosToStorage() throws IOException {
       if(fileCopyService.copyAllVideos()){
           return "All .mp4 files successfully uploaded to storage and deleted from openshift volume...";
       }
       else {
           return "Error: Uploading video file or deleting file from volume. Please, check the logs !! ";
       }
    }
}
