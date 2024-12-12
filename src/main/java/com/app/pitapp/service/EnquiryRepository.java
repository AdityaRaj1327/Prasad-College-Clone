package com.app.pitapp.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pitapp.model.Enquiry;

public interface EnquiryRepository extends JpaRepository<Enquiry, Integer> {

}
