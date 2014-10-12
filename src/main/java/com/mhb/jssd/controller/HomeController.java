package com.mhb.jssd.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mhb.jssd.domain.Person;
import com.mhb.jssd.util.SpringUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.abc", locale);
		Person meihuabo = (Person)SpringUtil.getBean("meihuabo");
		logger.info("person aaa's name is "+ meihuabo.getName()+". age is "+meihuabo.getAge());
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/aaa", method = RequestMethod.GET)
	public String aaa(Locale locale, Model model){
		return "layout/header";
	}
	
	@RequestMapping(value = "/velocity" , method = RequestMethod.GET)
	public String velocity(Model model) throws SQLException{
		Person meihuabo = (Person)SpringUtil.getBean("meihuabo");
		BasicDataSource da = (BasicDataSource)SpringUtil.getBean("dataSource");
		//BoneCPDataSource da = (BoneCPDataSource)SpringUtil.getBean("bonecpDataSource");
		Connection conn = da.getConnection();
		Statement stm = conn.createStatement();
		stm.execute("select * from person");
		ResultSet rs = stm.getResultSet();
		rs.next();
		System.out.println(rs.getString(2));
		rs.close();
		stm.close();
		conn.close();
		model.addAttribute("meihuabo", meihuabo);
		return "velocity";
	}
}
