package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.City;

import com.google.gson.Gson;

import database.DAO;

/**
 * Servlet implementation class CitySearch
 */
public class CitySearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CitySearch() {
        super();
		System.out.println("CitySearch()");
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("CitySearch.init()");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CitySearch.doGet()");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		try {
			String term = request.getParameter("term");
			String country = request.getParameter("country");
			System.out.println("Searching for cities with term '" + term + "' within country '" + country + "'...");

			List<City> list = DAO.getInstance().getCity(term, country);

			String searchList = new Gson().toJson(list);
			System.out.println(" --> " + searchList);
			response.getWriter().write(searchList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
