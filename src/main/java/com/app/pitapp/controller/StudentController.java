package com.app.pitapp.controller;

import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.pitapp.dto.ResponseDto;
import com.app.pitapp.model.Response;
import com.app.pitapp.model.StudentInfo;
import com.app.pitapp.service.MaterialRepository;
import com.app.pitapp.service.ResponseRepository;
import com.app.pitapp.service.StudentInfoRepository;
import com.app.pitapp.model.Material;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentInfoRepository srepo;
	@Autowired
	ResponseRepository rsrepo;
	@Autowired
	MaterialRepository mrepo;
	@GetMapping("/stuhome")
	public String showStudentPannel(HttpSession session,HttpServletResponse response)
	{
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if (session.getAttribute("userid")!=null) {
				return "/student/stuhome";
			}
			else {
				return "redirect:/stulogin";
			}
		
			} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/stulogin";
	}
	}
	@GetMapping("stulogout")
	public String stuLogout(HttpSession session)
	{
		session.invalidate();
		return"redirect:/stulogin";
	}
	
	
	@GetMapping("/changepassword")
	public String showChangePassword(HttpSession session,HttpServletResponse response)
	{
		try {
			if (session.getAttribute("userid")!=null) {
				return "/student/changepassword";
			}
			else {
				return "redirect:/stulogin";
			}
			} catch (Exception e) {
			
		}
		return"/student/changepassword";
	}

	@PostMapping("/changepassword")
		public String SubmitChangePassword(HttpSession session,HttpServletResponse httpServletResponse,HttpServletRequest request,RedirectAttributes redirectAttributes)
	{
		try {
			if (session.getAttribute("userid")!=null) {
				
				String oldpassword = request.getParameter("oldpassword");
				String newpassword = request.getParameter("newpassword");
				String confirmpassword = request.getParameter("confirmpassword");
				if (!newpassword .equals(confirmpassword))
				{
					redirectAttributes.addFlashAttribute("msg", "newpassword and confirm password do not match");
					return"redirect:/student/changepassword";
					
				}
				 StudentInfo  reg = srepo.getById(Long.parseLong(session.getAttribute("userid").toString()));
				if (!oldpassword .equals(reg.getPassword()))
				{
					redirectAttributes.addFlashAttribute("msg", "oldpassword do not match");
					return"redirect:/student/changepassword";
					
				}
				else {
					reg.setPassword(newpassword);
					srepo.save(reg);
					return "redirect:/student/stulogout";
				}
					
				
			}
			else {
				return"redirect:/stulogin";
			}
			
			
		} catch (Exception e) {
			return"redirect:/stulogin";
		}
		
		
	
	}
	
	@GetMapping("/giveresponse")
	public String showGiveResponse(HttpSession session,HttpServletResponse response,Model model)
	{
		try {
			if (session.getAttribute("userid")!=null) {
				ResponseDto dto=new ResponseDto();
				model.addAttribute("dto", dto);
				return "/student/giveresponse";
			}
			else {
				return "redirect:/stulogin";
			}
			} catch (Exception e) {
			
		}
		return"/student/changepassword";
	}
	
	@PostMapping("/giveresponse")
	public String submitResponsePannel(HttpSession session, Model model,@ModelAttribute HttpServletResponse response, ResponseDto dto
			,RedirectAttributes redirectAttributes)
	{
		if(session.getAttribute("userid")!=null) {
			
			StudentInfo reg=srepo.getById(Long.parseLong(session.getAttribute("userid").toString()));
			model.addAttribute("dto", reg.getName());
			Response res = new Response();
			res.setEnrollmentno(reg.getEnrollmentno());
			res.setName(reg.getName());
			res.setBranch(reg.getBranch());
			res.setProgram(reg.getProgram());
			res.setYear(reg.getYear());
			res.setResponsetype(dto.getResponsetype());
			res.setResponsetext(dto.getResponsetext());
			Date dt=new Date();
			SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
			String posteddate =df.format(dt);
			res.setPosteddate(posteddate);
			rsrepo.save(res);
			redirectAttributes.addFlashAttribute("msg", "Response is Submit ");
			return"redirect:/student/giveresponse";
			
			
			
			
		}
		else {
			return "/student/stulogin";
		}	
	
				
				
				
				
			
		
			
	}
	
	
	@GetMapping("/viewstudymaterial")
	public String showStudyMaterial(Model model ,HttpSession session,HttpServletResponse response)
	{
		
		if (session.getAttribute("userid")!=null) {
		StudentInfo s=srepo.findById(Long.parseLong(session.getAttribute("userid").toString())).get();
		List<Material> smat=mrepo.getMaterial(s.getProgram(),s.getBranch(),s.getYear(),"smat");
		model.addAttribute("smat", smat);
		System.out.print("hello");
	        return "/student/viewstudymaterial";
			}
			else {
				return "redirect:/stulogin";
			}
		
		
	}
	
	@GetMapping("/viewassignment")
	public String ViewAssignment(Model model ,HttpSession session,HttpServletResponse response)
	{
		if (session.getAttribute("userid")!=null) {
		StudentInfo s=srepo.findById(Long.parseLong(session.getAttribute("userid").toString())).get();
		List<Material> assign=mrepo.getMaterial(s.getProgram(), s.getBranch(), s.getYear(), "assign");
		model.addAttribute("assign", assign);
		        return "/student/viewassignment";
			}
		
		else {
			return "redirect:/stulogin";
		}
			
	}

}

