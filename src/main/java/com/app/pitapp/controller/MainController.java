package com.app.pitapp.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.pitapp.api.SmsSender;
import com.app.pitapp.dto.AdminLoginDto;
import com.app.pitapp.dto.EnquiryDto;
import com.app.pitapp.dto.StudentInfoDto;
import com.app.pitapp.model.AdminLogin;
import com.app.pitapp.model.Enquiry;
import com.app.pitapp.model.StudentInfo;
import com.app.pitapp.service.AdminLoginRepository;
import com.app.pitapp.service.EnquiryRepository;
import com.app.pitapp.service.StudentInfoRepository;

import jakarta.servlet.http.HttpSession;




@Controller
public class MainController {
	@Autowired
	EnquiryRepository erepo;
	@Autowired
	StudentInfoRepository srepo;

	
	@Autowired
	AdminLoginRepository adrepo;
	@GetMapping("/")
	 public String showIndex()
	 {
		return "index"; 
	 }
	
	@GetMapping("/aboutus")
	public String showAbout()
	{
		return"aboutus";
	}
	@GetMapping("pgilife")
	public String showLIFEPGI()
	{
		return"pgilife";
	}
	@GetMapping("gallery")
	public String showGallery()
	{
		return"gallery";
	}
	@GetMapping("/registration")
	public String showRegistration(Model model)
	{
		StudentInfoDto dto = new StudentInfoDto();
		model.addAttribute("dto", dto);
		
		return "registration";
	}
	@PostMapping("/registration")
	public String submitRegistration(@ModelAttribute StudentInfoDto dto ,RedirectAttributes redirectAttributes)
	{
		StudentInfo s = new StudentInfo();
		s.setEnrollmentno(dto.getEnrollmentno());
		s.setName(dto.getName());
		s.setFname(dto.getFname());
		s.setMname(dto.getMname());
		s.setGender(dto.getGender());
		s.setAddress(dto.getAddress());
		s.setProgram(dto.getProgram());
		s.setBranch(dto.getBranch());
		s.setYear(dto.getYear());
		s.setContactno(dto.getContactno());
		s.setEmailaddress(dto.getEmailaddress());
		s.setPassword(dto.getPassword());
		s.setRegdate(new Date()+"");
		srepo.save(s);
		
		
		
		return "redirect:/stulogin";
	}
	
	@GetMapping( value="/contactus")
	public String showContact(Model model)
	
	{
		EnquiryDto enquiryDto = new EnquiryDto();
		model.addAttribute("enquiryDto", enquiryDto);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		return "contactus";
	}
	
	@PostMapping("/contactus")
	public String SubmitEnquiry( @Validated @ModelAttribute EnquiryDto enquiryDto,BindingResult result,RedirectAttributes redirectAttributes)
	{
		
		Enquiry enquiry = new Enquiry();
		enquiry.setName(enquiryDto.getName());
		enquiry.setContactno(enquiryDto.getContactno());
		enquiry.setEmail(enquiryDto.getEmail());
		enquiry.setEnquirytext(enquiryDto.getEnquirytext());
		enquiry.setPosteddate(new Date()+"");
		erepo.save(enquiry);
		SmsSender ss=new SmsSender();
		ss.sendSms(enquiryDto.getContactno());
		
		redirectAttributes.addFlashAttribute("msg", "Enquiry is Saved ");
		
		return"redirect:/contactus";
	}
	
	
	
	@GetMapping("stulogin")
	public String showStulogin(Model model)
	{
		StudentInfoDto dto = new StudentInfoDto();
		model.addAttribute("dto", dto);
		return "stulogin";
	}
	@PostMapping("/stulogin")
	public String SubmitStulogin(@ModelAttribute StudentInfoDto dto,HttpSession session,RedirectAttributes redirectAttributes)
	{
		
		StudentInfo studentInfo=srepo.getById(dto.getEnrollmentno());
		if (studentInfo.getPassword().equals( dto.getPassword()))
			
		{
		session.setAttribute("userid", studentInfo.getEnrollmentno());
		return "redirect:/student/stuhome";
		}
		redirectAttributes.addFlashAttribute("message", "Student not register");
		return "redirect:/stulogin";
	}
	
	@GetMapping("adminlogin")
	public String showAdminlogin(Model model)
	{
		AdminLoginDto dto= new AdminLoginDto();
		model.addAttribute("dto", dto);
		return "adminlogin";
	}
	
	@PostMapping("/adminlogin")
	public String submitLogin(@ModelAttribute AdminLoginDto dto,HttpSession session, RedirectAttributes redirectAttributes)
	{
		AdminLogin adminLogin = adrepo.getById(dto.getUserid());
		if (adminLogin.getPassword().equals(dto.getPassword())) 
		{
			session.setAttribute("adminid", adminLogin.getUserid());
				
			
			return "redirect:/admin/adminhome";
				
		}
		redirectAttributes.addFlashAttribute("message", "User Does not exists");
		
			return"redirect:/adminlogin";
	}
	
}
