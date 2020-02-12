package price;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import database.DAO;

public class PriceSheet {
	
	private Workbook wb;
	private Integer moverId;
	private Integer priceModelId;
	private String countryFrom;
	private String countryTo;
	private ArrayList<Price> priceList = new ArrayList<Price>();

	public PriceSheet(Workbook wb) {
		this.wb = wb;
	}
	
	public void loadWorkbook() throws SQLException, Exception {

		Sheet sheet = wb.getSheet("base");
		if ( sheet == null ) {
			throw new PriceSheetInvalidFormatException("Base sheet not found in price file!");
		}

		Iterator<Row> rowIterator = sheet.iterator();
		while ( rowIterator.hasNext() ) {
			Row row = rowIterator.next();
			System.out.println("Row #" + row.getRowNum());
			Iterator<Cell> cellIterator = row.cellIterator();
			Cell cellLabel = cellIterator.next();
			System.out.println("  Label = " + cellLabel);
			Cell cellValue = null;
			if ( cellIterator.hasNext() ) {
				cellValue = cellIterator.next();
				System.out.println("  Label = " + cellValue);
				switch (cellLabel.getStringCellValue()) {
				case "Mover":
					this.moverId = (int) cellValue.getNumericCellValue();
					break;
				case "Price Model":
					this.priceModelId = (int) cellValue.getNumericCellValue();
					break;
				default:
					break;
				}
				if ( cellLabel.getStringCellValue().equals("User-ID") ) {
					this.moverId = (int) cellValue.getNumericCellValue();
				}
			}
		}
		
		if ( this.moverId == null || this.priceModelId == null ) {
			throw new PriceSheetInvalidFormatException("Invalid format of base sheet in price file!");
		}
		
		Iterator<Sheet> sheetIterator = wb.sheetIterator();
		while ( sheetIterator.hasNext() ) {
			sheet = sheetIterator.next();
			System.out.println("Sheet '" + sheet.getSheetName() + "':");
			boolean isPriceSheet = true;
			String zoneFrom = null;
			String zoneTo = null;
			ArrayList<String> destinationZones = null;
			Integer sizeId = null;
			rowIterator = sheet.iterator();
			while ( rowIterator.hasNext() && isPriceSheet) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while ( cellIterator.hasNext() && isPriceSheet) {
					Cell cell = cellIterator.next();
					if ( row.getRowNum() == 0 ) {
						// Read header information
						switch (cell.getColumnIndex()) {
						case 0:
							if ( !getCellContent(cell).equals("Prices") ) {
								isPriceSheet = false;
							}
							break;
						case 2:
							countryFrom = getCellContent(cell);
						case 4:
							countryTo = getCellContent(cell);
						default:
							break;
						}
					} else {
						if ( cell.getColumnIndex() == 0 ) {
							if ( getCellContent(cell).equals("Weight") ) {
								// Start of new Weight range grid
								cell = cellIterator.next();
								sizeId = (int) cell.getNumericCellValue();
								break;
							}
							else if ( getCellContent(cell).equals("From/To") ) {
								// Get array of destination zones
								if ( destinationZones == null ) {
									destinationZones = new ArrayList<String>();
									while ( cellIterator.hasNext() ) {
										destinationZones.add(getCellContent(cellIterator.next()));
									}
								} else {
									break;
								}
							} else {
								zoneFrom = getCellContent(cell);
							}
						} else {
							// Read Prices
							try {
								zoneTo = destinationZones.get(cell.getColumnIndex()-1);
								Price price = new Price(zoneFrom, zoneTo, sizeId, cell.getNumericCellValue());
								priceList.add(price);
							} catch (Exception e) {
								throw new PriceSheetInvalidPriceFromatException("Invalid price format in weight range " + sizeId + " from " + zoneFrom + " to " + zoneTo);
							}
						}
					}
					
				}
			}
		}
		
		DAO.getInstance().savePriceSheet(this);
	}

	public Integer getMoverId() {
		return moverId;
	}

	public Integer getPriceModelId() {
		return priceModelId;
	}

	public String getCountryFrom() {
		return countryFrom;
	}

	public String getCountryTo() {
		return countryTo;
	}

	public ArrayList<Price> getPriceList() {
		return priceList;
	}

	private String getCellContent(Cell cell) {
		String value = null;
		CellType ct = cell.getCellType();
		if (CellType.BOOLEAN.equals(ct)) {
			value = Boolean.toString(cell.getBooleanCellValue());
		} else if (CellType.NUMERIC.equals(ct)) {
			value = String.valueOf(cell.getNumericCellValue());
		} else if (CellType.STRING.equals(ct)) {
			value = cell.getStringCellValue();
		} else {
				value = null;
		}
		return value;
	}
}
