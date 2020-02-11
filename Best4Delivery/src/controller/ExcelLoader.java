package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import price.PriceSheet;

/**
 * Servlet implementation class ExcelGenerater
 */
public class ExcelLoader extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// location to store file uploaded
	private static final String UPLOAD_DIRECTORY = "ressources/csv";

	// upload settings
	private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 30;  // 30MiB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 400; // 400MiB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 500; // 500MiB 	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExcelLoader() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=filename.xls");
		HSSFWorkbook workbook = new HSSFWorkbook();
		// ...
		// Now populate workbook the usual way.
		// ...
		workbook.write(response.getOutputStream()); // Write workbook to response.
		workbook.close();
		
		/*
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String filename = "test.txt";
		String filepath = "c:\\Temp\\";
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=\""	+ filename + "\"");
 
		// use inline if you want to view the content in browser, helpful for
		// pdf file
		// response.setHeader("Content-Disposition","inline; filename=\"" +
		// filename + "\"");
		FileInputStream fileInputStream = new FileInputStream(filepath + filename);
 
		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.close();
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = "unknown";
		if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // sets memory threshold - beyond which files are stored in disk
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            // sets temporary location to store files
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);

            // sets maximum size of upload file
            upload.setFileSizeMax(MAX_FILE_SIZE);

            // sets maximum size of request (include file + form data)
            upload.setSizeMax(MAX_REQUEST_SIZE);

            // constructs the directory path to store upload file
            String uploadPath = getServletContext().getRealPath("") + File.separator+UPLOAD_DIRECTORY;

            //Create dir if needed
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            try {
                // parses the request's content to extract file data
            	FileItemIterator ii = upload.getItemIterator(request);
            	while ( ii.hasNext() ) {
            		FileItemStream item = ii.next();
            		if ( item != null && !item.isFormField() ) {
            			
                        InputStream fis = item.openStream();
                        fileName = item.getName();

                		Workbook wb = null;
                		String fileType = getFileExtension(new File(item.getName()));
                		switch (fileType) {
                		case "xls":
                			wb = new HSSFWorkbook(fis);
                			break;
                		case "xlsx":
                			wb = new XSSFWorkbook(fis);
                			break;
                		default:
                			break;
                		}
                		if ( wb == null ) {
                			request.setAttribute("messageType", "warning");
                			request.setAttribute("message", "The type '" + fileType + "' of Excel in file '" + fileName + "' is not supported!");
                			request.getRequestDispatcher("/setprice.jsp").forward(request, response);
                			return;
                		}
                		
                		PriceSheet ps = new PriceSheet(wb);
              			ps.loadWorkbook();
            		}
            	}
    			request.setAttribute("messageType", "success");
    			request.setAttribute("message", "The price file '" + fileName + "' was loaded successfully!");
            } catch (Exception ex) {
				request.setAttribute("messageType", "danger");
				request.setAttribute("message", "The format of the loaded price sheet seems to be invalid : " + ex);
            }		
			request.getRequestDispatcher("/setprice.jsp").forward(request, response);
        }
	}

	private String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}
}
