package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.modal.UserModal;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModal, UUID> {

	UserModal findByUserName(String userName);
}
