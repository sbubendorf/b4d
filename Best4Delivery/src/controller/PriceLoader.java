package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import beans.Country;
import beans.CountryList;
import beans.Zone;

/**
 * Servlet implementation class PriceLoader
 */
public class PriceLoader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PriceLoader() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		response.setContentType("application/json");
		
		String action = request.getParameter("action");
		String result = "";
		
		switch (action) {
		case "getZones": 
			result = getZones(request);
			break;
		case "getPrices": 
			result = getPrices(request);
			break;
		default:
			break;
		}
		response.getWriter().write(result);
	}
	
	private String getZones(HttpServletRequest request) {
		CountryList cl = (CountryList)request.getSession().getAttribute("countryList");
		Country country = cl.getCountry(request.getParameter("country"));
		List<Zone> list = country.getZoneList();
		String retval = new Gson().toJson(list);
		return retval;
	}

	private String getPrices(HttpServletRequest request) {
		String countryFrom = request.getParameter("country_from");
		String countryTo   = request.getParameter("country_to");
		String sizeRange   = request.getParameter("size_range");
		CountryList cl = (CountryList)request.getSession().getAttribute("countryList");
		Country country = cl.getCountry(request.getParameter("country"));
		List<Zone> list = country.getZoneList();
		String retval = new Gson().toJson(list);
		return retval;
	}

}
