package com.app.pitapp.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.pitapp.model.Enquiry;
import com.app.pitapp.model.Response;
import com.app.pitapp.model.StudentInfo;
import com.app.pitapp.service.EnquiryRepository;
import com.app.pitapp.service.MaterialRepository;
import com.app.pitapp.service.ResponseRepository;
import com.app.pitapp.service.StudentInfoRepository;
import com.app.pitapp.dto.MaterialDto;
import com.app.pitapp.model.Material;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	StudentInfoRepository srepo;
	@Autowired
	EnquiryRepository erepo;
	@Autowired
	ResponseRepository rrepo;
	@Autowired
	MaterialRepository mrepo;
	
				@GetMapping("/adminhome")
				public String ShowAdminHome(HttpSession session , HttpServletResponse response)
				{
					try {
						response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		if (session.getAttribute("adminid")!=null)
						{
						return "/admin/adminhome";	
						}
						else {
							return "/admin/adminhome";
							
						}
						} catch (Exception e) {
						return "redirect:/adminlogin";
					}
				
				}
	@GetMapping("/adminlogout")
	public String adminLogout(HttpSession session)
	{
	 session.invalidate();
	 return "redirect:/adminlogin";
	}
	
	@GetMapping("/managestudent")
	public String ShowStudent(HttpSession session , HttpServletResponse response,Model model)
	{
		try {
			response.setHeader("Cache-Control", "no-store,  no-cache, must-revalidate");
			if (session.getAttribute("adminid")!=null)
			{
				List<StudentInfo>silist=srepo.findAll();
				model.addAttribute("silist", silist);
			return "/admin/managestudent";	
			}
			else {
				return "/admin/adminhome";
				
			}
			} catch (Exception e) {
			return "redirect:/adminlogin";
		}
	
	}
	
	@GetMapping("/manageenquiries")
	public String ShowEnquiry(HttpSession session , HttpServletResponse response,Model model)
	{
		try {
			response.setHeader("Cache-Control", "no-store,  no-cache, must-revalidate");
			if (session.getAttribute("adminid")!=null)
			{
				List<Enquiry>enq=erepo.findAll();
				model.addAttribute("enq", enq);
			return "/admin/manageenquiries";	
			}
			else {
				return "/admin/adminhome";
				
			}
			} catch (Exception e) {
			return "redirect:/adminlogin";
		}
	
	}
	@GetMapping("/delenq")
	public String ShowAdminHome(@RequestParam int id,HttpSession session , HttpServletResponse response)
	{
		try {
			response.setHeader("Cache-Control", "no-store, no-cache,must-revalidate");
				if (session.getAttribute("adminid")!=null)
			{
					Enquiry enq=erepo.getById(id);
					erepo.delete(enq);
			return "redirect:/admin/manageenquiries";	
			}
			else {
				return "/admin/adminhome";
				
			}
			} catch (Exception e) {
			return "redirect:/adminlogin";
		}
	
	}
	
	@GetMapping("/managefeedback")
	public String ShowFeedbackMethod(Model model,HttpSession session , HttpServletResponse response)
	{
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
if (session.getAttribute("adminid")!=null)
			{
	          List<Response> listfeedback=rrepo.findByResponsetype("feedback");
	          model.addAttribute("listfeedback", listfeedback);
			return "/admin/managefeedback";	
			}
			else {
				return "/admin/adminhome";
				
			}
			} catch (Exception e) {
			return "redirect:/adminlogin";
		}
	
	}
	@GetMapping("/managecomplaint")
	public String ShowComplaintMethod(Model model,HttpSession session , HttpServletResponse response)
	{
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
if (session.getAttribute("adminid")!=null)
			{
	          List<Response> listcomp=rrepo.findByResponsetype("complaint");
	          model.addAttribute("listcomp", listcomp);
			return "/admin/managecomplaint";	
			}
			else {
				return "/admin/adminhome";
				
			}
			} catch (Exception e) {
			return "redirect:/adminlogin";
		}
	
	}
	
	@GetMapping("/addstudymaterial")
	public String showAddStudyMaterial(HttpSession session, HttpServletResponse response, Model model) {
		try {
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			if (session.getAttribute("adminid") != null) {
				MaterialDto dto=new MaterialDto();
				model.addAttribute("dto", dto);
				return "/admin/addstudymaterial";
			}
			else {
				return "redirect:/adminlogin";
			}
		} catch (Exception ex) {
			return "redirect:/adminlogin";
		}
	}
	@PostMapping("/addstudymaterial")
	public String addStudyMaterial(HttpSession session, HttpServletResponse response, @ModelAttribute MaterialDto dto, RedirectAttributes attrib) {
		try {
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			if (session.getAttribute("adminid") != null) {
				
				MultipartFile uploadfile=dto.getUploadfile();
				String storageFileName=new Date().getTime()+"_"+uploadfile.getOriginalFilename();
				String uploadDir="public/mat/";
				Path uploadPath=Paths.get(uploadDir);
				if(!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				try(InputStream inputStream=uploadfile.getInputStream()){
					Files.copy(inputStream, Paths.get(uploadDir+storageFileName),StandardCopyOption.REPLACE_EXISTING);					
				}
				Material m=new Material();
				m.setProgram(dto.getProgram());
				m.setBranch(dto.getBranch());
				m.setYear(dto.getYear());
				m.setMaterialtype(dto.getMaterialtype());
				m.setSubject(dto.getSubject());
				m.setTopic(dto.getTopic());
				Date dt=new Date();
				SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
				String posteddate=df.format(dt);
				m.setPosteddate(posteddate);
				m.setFilename(storageFileName);
				mrepo.save(m);
				attrib.addFlashAttribute("msg", "Material is uploaded");
				return "redirect:/admin/addstudymaterial";
			}
			else {
				return "redirect:/adminlogin";
			}
		} catch (Exception ex) {
			return "redirect:/adminlogin";
		}
	}
	@GetMapping("/managestudymaterial")
	public String showStudyMaterial(HttpSession session, HttpServletResponse response, Model model) {
		try {
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			if (session.getAttribute("adminid") != null) {
				List<Material> mat=mrepo.findAll();
				model.addAttribute("mat", mat);
				return "/admin/managestudymaterial";
			}
			else {
				return "redirect:/adminlogin";
			}
		} catch (Exception ex) {
			return "redirect:/adminlogin";
		}
	}
	
}
