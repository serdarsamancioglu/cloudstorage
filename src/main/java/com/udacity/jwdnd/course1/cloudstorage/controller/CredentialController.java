package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/credential", method = RequestMethod.POST, params = "action=save")
    public String saveCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {

        boolean success;
        if (credential.getCredentialId() != null && credential.getCredentialId() > 0) {
            success = credentialService.update(credential) > 0;
        }
        else {
            success = credentialService.insert(credential, authentication.getName()) > 0;
        }
        return "redirect:/home" + (success ? "?credentialSaved=true" : "?erroroccured=true");
    }

    @RequestMapping(value = "/credential", method = RequestMethod.POST, params = "action=delete")
    public String deleteCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {

        boolean success = false;
        if (credential.getCredentialId() != null && credential.getCredentialId() > 0) {
            success = credentialService.delete(credential) > 0;
        }
        return "redirect:/home" + (success ? "?credentialDeleted=true" : "?erroroccured=true");
    }


}
