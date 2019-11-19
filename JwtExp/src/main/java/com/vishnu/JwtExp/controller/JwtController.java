package com.vishnu.JwtExp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/get")
public class JwtController {
	@GetMapping("/user")
	public String auth(@RequestParam String user,@RequestParam String password) {
		if(user.equals("admin") && password.equals("admin"))
			return "urAdmin";
		else if(user.equals("vishnu1545") && password.equals("vishnu1545"))
			return "urPatient";
		return null;
	}
	
	@GetMapping("/jwt")
	public String jwt(@RequestParam String auth) {
		if(auth.equals("urAdmin"))
		{
			Claims claims = Jwts.claims().setSubject("admin");
			claims.put("auth", "ROLE_ADMIN");
			
	    Date now = new Date();
	    Date validity = new Date(now.getTime() + (10*60*60));

	    return Jwts.builder()//
	        .setClaims(claims)//
	        .setIssuedAt(now)//
	        .setExpiration(validity)//
	        .signWith(SignatureAlgorithm.HS256, "Vishnu1545")//
	        .compact();
	    }
		else if(auth.equals("urPatient"))
		{
			Claims claims = Jwts.claims().setSubject("patient");
			claims.put("auth", "ROLE_PATIENT");
			
	    Date now = new Date();
	    Date validity = new Date(now.getTime() + (10*60*60));

	    return Jwts.builder()//
	        .setClaims(claims)//
	        .setIssuedAt(now)//
	        .setExpiration(validity)//
	        .signWith(SignatureAlgorithm.HS256, "Vishnu1545")//
	        .compact();
		}
		return null;
	}
	@RequestMapping("/resource")
	public List<String> resources(@RequestParam String role){
		List<String> resources=new ArrayList<String>();
		if(role.equals("ROLE_PATIENT"))
			resources.add(".*/patientService");
		else if(role.equals("ROLE_ADMIN"))
			resources.add(".*");
		return resources;
		
	}
	@RequestMapping("/patientService")
	public String patient() {
		return "You have accessed patient service";
	}
	@RequestMapping("/anyPath")
	public String anyPath() {
		return "You have accessed path not ordained to patient";
	}
	
}
