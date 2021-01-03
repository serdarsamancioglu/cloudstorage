package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotes(int userId);

    @Insert("INSERT INTO NOTES (userid, notetitle, notedescription) values " +
            "(#{userId}, #{noteTitle}, #{noteDescription})")
    @Options(keyProperty = "noteId", useGeneratedKeys = true)
    int insertNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} where noteid = #{noteId}")
    int update(Note note);

    @Update("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int delete(Note note);
}