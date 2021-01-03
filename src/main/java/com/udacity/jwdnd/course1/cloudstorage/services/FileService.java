package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private UserMapper userMapper;
    private FileMapper fileMapper;

    public FileService(UserMapper userMapper, FileMapper fileMapper) {
        this.userMapper = userMapper;
        this.fileMapper = fileMapper;
    }

    public int upload(MultipartFile multipartFile, String username) {
        File file = new File();

        try {
            file.setContentType(multipartFile.getContentType());
            file.setFileData(multipartFile.getBytes());
            file.setFileName(multipartFile.getOriginalFilename());
            file.setFileSize(multipartFile.getSize());
            file.setUserId(userMapper.getUserId(username));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileMapper.insert(file);
    }

    public List<File> getFiles(String username) {
        return fileMapper.getFiles(userMapper.getUserId(username));
    }

    public boolean isFileNameAvailable(String username, String fileName) {
        return fileMapper.checkFileName(userMapper.getUserId(username), fileName) == 0;
    }

    public File getFile(int id) {
        return fileMapper.getFile(id);
    }

    public int deleteFile(int id) {
        return fileMapper.delete(id);
    }
}
