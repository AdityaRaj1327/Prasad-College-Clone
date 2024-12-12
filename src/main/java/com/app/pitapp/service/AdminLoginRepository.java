package com.app.pitapp.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pitapp.model.AdminLogin;

public interface AdminLoginRepository extends JpaRepository<AdminLogin, String> {

}
