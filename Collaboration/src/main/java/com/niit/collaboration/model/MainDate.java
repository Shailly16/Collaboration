package com.niit.collaboration.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainDate {
	public static void main(String[] args) {
	    String pattern = "yyyy-MM-dd'T'HH:mm:ssz";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
	    try {
	      Date date = format.parse("12/31/2006");
	      System.out.println(date);
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
	    // formatting
	    System.out.println(format.format(new Date()));
	  }
	}

