package com.app.pitapp.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.pitapp.model.Response;

public interface ResponseRepository extends JpaRepository<Response, Integer>{

	@Query("select r from Response r where r.responsetype=:responsetype")
	List<Response> findByResponsetype(@Param("responsetype")String responsetype);

}
