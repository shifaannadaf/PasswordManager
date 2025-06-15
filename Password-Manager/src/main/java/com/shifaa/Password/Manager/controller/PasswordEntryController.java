package com.shifaa.Password.Manager.controller;

import com.shifaa.Password.Manager.model.PasswordEntry;
import com.shifaa.Password.Manager.service.PasswordEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.List;

@RestController
@RequestMapping("/api/passwords")
@CrossOrigin(origins = "*") // Allow frontend apps to access this API
public class PasswordEntryController {
    private final PasswordEntryService service;

    public PasswordEntryController(PasswordEntryService service) {
        this.service = service;
    }

    // 🔹 Create a new password entry
    @PostMapping
    public PasswordEntry addPassword(@RequestBody PasswordEntry entry, Authentication authentication) {
        String username = authentication.getName();
        return service.saveEntry(entry, username);
    }

    // 🔹 Get all password entries
    @GetMapping
    public List<PasswordEntry> getAllPasswords(Authentication authentication) {
        String username = authentication.getName();
        return service.getAllEntriesByUser(username);
    }

    // 🔹 Update an existing password entry
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable Long id, @RequestBody PasswordEntry newEntry, Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(service.updateEntry(id, newEntry, username));
    }

    // 🔹 Delete a password entry
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long id, Authentication auth) {
        String username = auth.getName();
        service.deleteEntry(id, username);
        return ResponseEntity.ok("Deleted successfully");
    }
}
