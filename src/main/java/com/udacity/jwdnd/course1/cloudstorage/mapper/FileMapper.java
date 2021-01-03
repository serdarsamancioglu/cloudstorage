package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid=#{userId}")
    List<File> getFiles(int userId);

    @Select("SELECT Count(*) FROM FILES WHERE userid=#{userId} and fileName=#{fileName}")
    int checkFileName(int userId, String fileName);

    @Select("SELECT * FROM FILES WHERE fileid=#{fileId}")
    File getFile(int fileId);

    @Insert("INSERT INTO FILES (userid, filename, contenttype, filesize, filedata) VALUES " +
            "(#{userId}, #{fileName}, #{contentType}, #{fileSize}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileid=#{id}")
    int delete(int id);
}
