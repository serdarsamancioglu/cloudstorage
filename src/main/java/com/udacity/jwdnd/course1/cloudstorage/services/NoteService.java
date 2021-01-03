package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public int insertNote(String username, Note note) {
        note.setUserId(userMapper.getUserId(username));
        return noteMapper.insertNote(note);
    }

    public List<Note> getNotes(String username) {
        return noteMapper.getNotes(userMapper.getUserId(username));
    }

    public int updateNote(Note note) {
        return noteMapper.update(note);
    }

    public int deleteNote(Note note) {
        return noteMapper.delete(note);
    }
}
