package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(Authentication authentication, @ModelAttribute MultipartFile fileUpload, Model model) {

        boolean success = false;
        if (fileUpload.isEmpty()) {
            return "redirect:/home?fileEmpty=true";
        }
        boolean fileNameAvailable = fileService.isFileNameAvailable(authentication.getName(), fileUpload.getOriginalFilename());
        if (fileNameAvailable) {
            success = fileService.upload(fileUpload, authentication.getName()) > 0;
        }
        if (success) {
            return "redirect:/home?fileSaved=true";
        }
        else {
            return "redirect:/home" + (!fileNameAvailable ? "?fileExists=true" : "erroroccured=true");
        }
    }

    @RequestMapping(value = "/fileaction", method = RequestMethod.POST, params = "action=download")
    public ResponseEntity<Resource> downloadFile(@ModelAttribute File fileitem, Model model) {

        File file1 = fileService.getFile(fileitem.getFileId());

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file1.getFileName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        ByteArrayResource resource = new ByteArrayResource(file1.getFileData());

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file1.getFileSize())
                .contentType(MediaType.parseMediaType(file1.getContentType()))
                .body(resource);
    }

    @RequestMapping(value = "/fileaction", method = RequestMethod.POST, params = "action=delete")
    public String deleteFile(@ModelAttribute File fileitem, Model model) {
        boolean success = fileService.deleteFile(fileitem.getFileId()) > 0;
        return "redirect:/home" + (success ? "?fileDeleted=true" : "?erroroccured=true");
    }

}
