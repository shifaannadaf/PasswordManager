package com.shifaa.Password.Manager.service;
import com.shifaa.Password.Manager.config.AESUtil;
import com.shifaa.Password.Manager.model.User;
import com.shifaa.Password.Manager.repository.UserRepository;
import com.shifaa.Password.Manager.model.PasswordEntry;
import com.shifaa.Password.Manager.repository.PasswordEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PasswordEntryService {
    private final PasswordEntryRepository repository;
    private final UserRepository userRepository;

    // ✅ Constructor Injection
    public PasswordEntryService(PasswordEntryRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    // 🔹 Save a new password entry
    public PasswordEntry saveEntry(PasswordEntry entry, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        entry.setPassword(AESUtil.encrypt(entry.getPassword()));
        entry.setUser(user);
        return repository.save(entry);

    }

    // 🔹 Get all password entries
    public List<PasswordEntry> getAllEntries() {
        return repository.findAll();
    }
    public List<PasswordEntry> getAllEntriesByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<PasswordEntry> entries = repository.findByUser(user);

        // Decrypt passwords before returning
        for (PasswordEntry entry : entries) {
            entry.setPassword(AESUtil.decrypt(entry.getPassword()));
        }
        return entries;
    }

    // 🔹 Delete a password entry by ID
    public void deleteEntry(Long id, String username) {
        PasswordEntry entry = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        if (!entry.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }

        repository.delete(entry);
    }

    // 🔹 Update a password entry by ID
    public PasswordEntry updateEntry(Long id, PasswordEntry newData, String username) {
        PasswordEntry entry = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        if (!entry.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }

        entry.setSiteName(newData.getSiteName());
        entry.setUsername(newData.getUsername());
        entry.setPassword(AESUtil.encrypt(newData.getPassword()));

        return repository.save(entry);
    }
}
