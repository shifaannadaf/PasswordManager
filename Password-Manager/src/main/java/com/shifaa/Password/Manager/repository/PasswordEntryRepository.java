package com.shifaa.Password.Manager.repository;
import com.shifaa.Password.Manager.model.PasswordEntry;
import com.shifaa.Password.Manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordEntryRepository extends JpaRepository<PasswordEntry, Long> {
    List<PasswordEntry> findByUser(User user);

}
