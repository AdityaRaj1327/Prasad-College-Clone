package com.app.pitapp.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pitapp.model.StudentInfo;

public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long>{

}
