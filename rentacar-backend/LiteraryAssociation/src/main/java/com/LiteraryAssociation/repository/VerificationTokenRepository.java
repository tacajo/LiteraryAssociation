package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.model.User;
import com.LiteraryAssociation.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByConfirmationToken(String confirmationToken);

    VerificationToken findByUser(User user);
}
