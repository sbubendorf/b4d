package controller;

import java.awt.Color;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import beans.Country;
import beans.CountryList;
import beans.Mover;
import beans.PriceTable;
import beans.SizeRange;
import beans.Zone;
import database.DAO;

/**
 * Servlet implementation class ExcelGenerater
 */
public class ExcelGenerator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExcelGenerator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		// ...
		// Now populate workbook the usual way.
		// ...
		workbook.write(response.getOutputStream()); // Write workbook to response.
		workbook.close();
		
		response.setContentType("application/ms-excel");
		response.setHeader("Expires:", "0"); // eliminates browser caching
		response.setHeader("Content-Disposition", "attachment; filename=pricesheet_ch_ch.xlsx");
 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Mover mover = (Mover) request.getSession().getAttribute("mover");

		String isoDepa = request.getParameter("country_depa");
		String isoDest = request.getParameter("country_dest");
		
    	if ( isoDepa == null || isoDepa.length() == 0 || isoDest == null || isoDest.length() == 0 ) {
			request.setAttribute("messageType", "warning");
			request.setAttribute("message", "Please select Departure and Destination country!");
			request.getRequestDispatcher("/setprice.jsp").forward(request, response);
    	} else if ( mover == null ) {
			request.setAttribute("messageType", "danger");
			request.setAttribute("message", "Mover is not set. Please log out and in again!");
			request.getRequestDispatcher("/setprice.jsp").forward(request, response);
    	} else {

			XSSFWorkbook workbook = new XSSFWorkbook();
			
			XSSFColor colorRed 		= new XSSFColor(new Color(250,230,215));
			XSSFColor colorYellow 	= new XSSFColor(new Color(255,240,205));
			XSSFColor colorGreen 	= new XSSFColor(new Color(225,240,218));
			XSSFColor colorGrey 	= new XSSFColor(new Color(214,220,228));

			XSSFFont fontBold = workbook.createFont();
			fontBold.setBold(true);

			XSSFCellStyle styleSheetTitle = workbook.createCellStyle();
			XSSFFont txtFont = (XSSFFont)workbook.createFont();
			txtFont.setFontName("Arial");
			txtFont.setFontHeightInPoints((short)15);
			txtFont.setBold(true);
			styleSheetTitle.setFont(txtFont);		
			
			XSSFCellStyle styleRangeTitel = workbook.createCellStyle();
			styleRangeTitel.setFillForegroundColor(colorGrey);
			styleRangeTitel.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			styleRangeTitel.setFont(fontBold);
			styleRangeTitel.setBorderTop(BorderStyle.THIN);
			styleRangeTitel.setBorderBottom(BorderStyle.THIN);
			styleRangeTitel.setAlignment(HorizontalAlignment.CENTER);

			XSSFCellStyle styleFromToTitel = workbook.createCellStyle();
			styleFromToTitel.setFillForegroundColor(colorYellow);
			styleFromToTitel.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			styleFromToTitel.setFont(fontBold);
			styleFromToTitel.setBorderTop(BorderStyle.THIN);
			styleFromToTitel.setBorderBottom(BorderStyle.THIN);
			styleFromToTitel.setBorderLeft(BorderStyle.THIN);
			styleFromToTitel.setBorderRight(BorderStyle.THIN);
			styleFromToTitel.setAlignment(HorizontalAlignment.CENTER);

			XSSFCellStyle styleDestinationTitel = workbook.createCellStyle();
			styleDestinationTitel.setFillForegroundColor(colorRed);
			styleDestinationTitel.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			styleDestinationTitel.setFont(fontBold);
			styleDestinationTitel.setBorderTop(BorderStyle.THIN);
			styleDestinationTitel.setBorderBottom(BorderStyle.THIN);
			styleDestinationTitel.setBorderLeft(BorderStyle.THIN);
			styleDestinationTitel.setBorderRight(BorderStyle.THIN);
			styleDestinationTitel.setAlignment(HorizontalAlignment.CENTER);

			XSSFCellStyle styleDepartureTitel = workbook.createCellStyle();
			styleDepartureTitel.setFillForegroundColor(colorGreen);
			styleDepartureTitel.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			styleDepartureTitel.setFont(fontBold);
			styleDepartureTitel.setBorderTop(BorderStyle.THIN);
			styleDepartureTitel.setBorderBottom(BorderStyle.THIN);
			styleDepartureTitel.setBorderLeft(BorderStyle.THIN);
			styleDepartureTitel.setBorderRight(BorderStyle.THIN);
			styleDepartureTitel.setAlignment(HorizontalAlignment.CENTER);
			
			XSSFCellStyle stylePrice = workbook.createCellStyle();
			stylePrice.setBorderTop(BorderStyle.THIN);
			stylePrice.setBorderBottom(BorderStyle.THIN);
			stylePrice.setBorderLeft(BorderStyle.THIN);
			stylePrice.setBorderRight(BorderStyle.THIN);
			stylePrice.setAlignment(HorizontalAlignment.CENTER);
			
    		String fileName = "b4d-pricesheet-m" + mover.getId() + "-" + isoDepa + "-" + isoDest;
    		response.setContentType("application/vnd.ms-excel");
    		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
    		
    		CountryList cl = (CountryList) request.getSession().getAttribute("countryList");
    		Country countryDepa = cl.getCountry(isoDepa); 
    		Country countryDest = cl.getCountry(isoDepa); 
    		
			XSSFSheet sheet = workbook.createSheet("base");
			setCell(sheet, 0, 0, "B4D Price File", styleSheetTitle);
			setCell(sheet, 2, 0, "Base Information");

			setCell(sheet, 3, 0, "Mover");
			setCell(sheet, 3, 1, mover.getId());
			setCell(sheet, 3, 2, mover.getName());
	
			setCell(sheet, 4, 0, "Price Model");
			setCell(sheet, 4, 1, mover.getPriceModel().getId());
			setCell(sheet, 4, 2, mover.getPriceModel().getName());
			
			sheet.setColumnWidth(0, 3000);;
	
			sheet = workbook.createSheet("prices_" + isoDepa + "_" + isoDest);

			setCell(sheet, 0, 0, "Prices", styleSheetTitle);
			setCell(sheet, 0, 1, "From:");
			setCell(sheet, 0, 2, isoDepa, styleSheetTitle);
			setCell(sheet, 0, 3, "To:");
			setCell(sheet, 0, 4, isoDest, styleSheetTitle);
			
			int rowNum = 2;
			for ( SizeRange range : mover.getSizeRanges() ) {
				setCell(sheet, rowNum, 0, "Weight"				, styleRangeTitel);
				setCell(sheet, rowNum, 1, range.getRangeId()	, styleRangeTitel);
				setCell(sheet, rowNum, 2, range.getWeightFrom()	, styleRangeTitel);
				setCell(sheet, rowNum, 3, "-"					, styleRangeTitel);
				setCell(sheet, rowNum, 4, range.getWeightTo()	, styleRangeTitel);
				for ( int i = 5 ; i <= countryDest.getZoneList().size() ; i++ ) {
					setCell(sheet, rowNum, i, "", styleRangeTitel);
				}

				setCell(sheet, ++rowNum, 0, "From/To", styleFromToTitel);
				
				int cellIndex = 0;
				for ( Zone zone : countryDest.getZoneList() ) {
					setCell(sheet, rowNum, ++cellIndex, zone.getShortName(), styleDestinationTitel);
				}
				
				PriceTable pt = null;
	    		try {
					pt = DAO.getInstance().getPriceTable(mover, countryDepa, countryDest, range);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		for ( Zone zoneDepa : countryDepa.getZoneList() ) {
					cellIndex = 0;
					setCell(sheet, ++rowNum, cellIndex, zoneDepa.getShortName(), styleDepartureTitel);
					for ( Zone zoneDest : countryDest.getZoneList() ) {
						Float price = pt.getPrice(zoneDepa, zoneDest);
						setCell(sheet, rowNum, ++cellIndex, price == null ? new Float(0) : price, stylePrice);
					}
				}
				
				rowNum += 2;
			}
			workbook.write(response.getOutputStream()); // Write workbook to response.
			workbook.close();
			
    	}
		
	}
	
	private void setCell(XSSFSheet sheet, int rowNum, int colNum, String value) {
		getCell(sheet, rowNum, colNum).setCellValue(value);
	}
	
	private void setCell(XSSFSheet sheet, int rowNum, int colNum, String value, XSSFCellStyle style) {
		setCell(sheet, rowNum, colNum, value);
		getCell(sheet, rowNum, colNum).setCellStyle(style);
	}
	
	private void setCell(XSSFSheet sheet, int rowNum, int colNum, Integer value) {
		getCell(sheet, rowNum, colNum).setCellValue(value);
	}
	
	private void setCell(XSSFSheet sheet, int rowNum, int colNum, Integer value, XSSFCellStyle style) {
		setCell(sheet, rowNum, colNum, value);
		getCell(sheet, rowNum, colNum).setCellStyle(style);
	}
	
	private void setCell(XSSFSheet sheet, int rowNum, int colNum, Float value) {
		getCell(sheet, rowNum, colNum).setCellValue(value);
	}
	
	private void setCell(XSSFSheet sheet, int rowNum, int colNum, Float value, XSSFCellStyle style) {
		setCell(sheet, rowNum, colNum, value);
		getCell(sheet, rowNum, colNum).setCellStyle(style);
	}
	
	private XSSFCell getCell(XSSFSheet sheet, int rowNum, int colNum) {
		XSSFRow row = sheet.getRow(rowNum);
		if ( row == null ) {
			row = sheet.createRow(rowNum);
		}
		XSSFCell cell = row.getCell(colNum);
		if ( cell == null ) {
			cell = row.createCell(colNum);
		}
		return cell;
	}

}
