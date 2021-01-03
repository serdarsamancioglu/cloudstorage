package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private UserMapper userMapper;

    @Autowired
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper mapper, UserMapper userMapper) {
        this.credentialMapper = mapper;
        this.userMapper = userMapper;
    }

    public int update(Credential credential) {
        String key = credentialMapper.getCredentialKey(credential.getCredentialId());
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), key));
        return credentialMapper.update(credential);
    }

    public int delete(Credential credential) {
        return credentialMapper.delete(credential);
    }

    public int insert(Credential credential, String username) {
        credential.setUserId(userMapper.getUserId(username));

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        credential.setKey(encodedSalt);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), encodedSalt));
        return this.credentialMapper.insert(credential);
    }

    public List<Credential> getCredentials(String username) {
        List<Credential> credentials = credentialMapper.getCredentials(userMapper.getUserId(username));

        for (Credential credential: credentials) {
            credential.setPasswordDecrypted(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }

        return credentials;
    }
}
