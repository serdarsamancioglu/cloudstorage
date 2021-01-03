package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "logout")
    private WebElement logout;

    @FindBy(id = "inputNoteTitle")
    private WebElement inputNoteTitle;

    @FindBy(id = "note-description")
    private WebElement inputNoteDesc;

    @FindBy(id = "btnSaveNote")
    private WebElement saveNote;

    @FindBy(id = "newNote")
    private WebElement newNote;

    @FindBy(id = "noteTitle")
    private WebElement itemNoteTitle;

    @FindBy(id = "noteDescription")
    private WebElement itemNoteDesc;

    @FindBy(id = "editNote")
    private WebElement editNoteButton;

    @FindBy(id = "deleteNote")
    private WebElement deleteNoteButton;

    @FindBy(id = "credential-url")
    private WebElement inputCredentialUrl;

    @FindBy(id = "credential-username")
    private WebElement inputCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement inputCredentialPassword;

    @FindBy(id = "saveCredential")
    private WebElement btnSaveCredential;

    @FindBy(id = "btnEditCredential")
    private WebElement btnEditCredential;

    @FindBy(id = "btnDeleteCredential")
    private WebElement btnDeleteCredential;

    @FindBy(id = "url")
    private WebElement itemUrl;

    @FindBy(id = "username")
    private WebElement itemUsername;

    @FindBy(id = "password")
    private WebElement itemPassword;

    @FindBy(id = "newCredential")
    private WebElement newCredential;

    @FindBy(id = "credential-password")
    private WebElement modalPassword;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logout.submit();
    }

    public void openNotesTab() {
        notesTab.click();
    }

    public void openCredentialsTab() {
        credentialsTab.click();
    }

    public void newNote() {
        newNote.click();
    }

    public void editNote() {
        editNoteButton.click();
    }

    public void saveNote(String title, String description) {
        inputNoteTitle.clear();
        inputNoteDesc.clear();
        addNewNote(title, description);
    }

    public void addNewNote(String title, String description) {
        inputNoteTitle.sendKeys(title);
        inputNoteDesc.sendKeys(description);
        saveNote.click();
    }

    public void deleteNote() throws NoSuchElementException {
        deleteNoteButton.click();
    }

    public boolean checkNoteSaved(String title, String description) {
        return itemNoteTitle.getText().equals(title) && itemNoteDesc.getText().equals(description);
    }

    public void newCredential() {
        newCredential.click();
    }

    public void addNewCredential(String url, String username, String password) {
        inputCredentialUrl.sendKeys(url);
        inputCredentialUsername.sendKeys(username);
        inputCredentialPassword.sendKeys(password);
        btnSaveCredential.click();
    }

    public boolean checkCredentialSaved(String url, String username, String password) {
        return itemUrl.getText().equals(url)
                && itemUsername.getText().equals(username)
                && !itemPassword.getText().equals(password);
    }

    public void saveCredential(String url, String username, String password) {
        inputCredentialUrl.clear();
        inputCredentialUsername.clear();
        inputCredentialPassword.clear();
        addNewCredential(url, username, password);
    }

    public void deleteCredential() throws NoSuchElementException {
        btnDeleteCredential.click();
    }

    public String getSavedUrl() {
        return itemUrl.getText();
    }

    public String getSavedUsername() {
        return itemUsername.getText();
    }

    public String getSavedPassword() {
        return itemPassword.getText();
    }

    public void editCredential() {
        btnEditCredential.click();
    }

    public String getModalPassword() {
        return modalPassword.getText();
    }

}