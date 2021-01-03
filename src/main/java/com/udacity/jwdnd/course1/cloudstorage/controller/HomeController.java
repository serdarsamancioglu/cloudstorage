package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private FileService fileService;

    @Autowired
    private CredentialService credentialService;

    public HomeController(NoteService noteService, FileService fileService) {
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String home(@ModelAttribute Note note, Authentication authentication, Model model) {
        List<File> files = fileService.getFiles(authentication.getName());
        model.addAttribute("files", fileService.getFiles(authentication.getName()));
        List<Note> notes = noteService.getNotes(authentication.getName());
        model.addAttribute("notes", notes);
        List<Credential> credentials = credentialService.getCredentials(authentication.getName());
        model.addAttribute("credentials", credentials);
        model.addAttribute("credential", new Credential());
        model.addAttribute("note", new Note());
        return "home";
    }

    @PostMapping(params = "action=save")
    public String note(Authentication authentication, @ModelAttribute Note note, Model model) {

        boolean success = false;
        if (note.getNoteId() > 0) {
            //update
            success = noteService.updateNote(note) > 0;
        }
        else {
            success = noteService.insertNote(authentication.getName(), note) > 0;
        }

        return "redirect:/home" + (success ? "?noteSaved=true" : "?erroroccured=true");
    }

    @PostMapping(params = "action=delete")
    public String deleteNote(Authentication authentication, @ModelAttribute Note note, Model model) {

        boolean success = noteService.deleteNote(note) > 0;
        model.addAttribute("notes", noteService.getNotes(authentication.getName()));

        return "redirect:/home" + (success ? "?noteDeleted=true" : "?erroroccured=true");
    }
}
