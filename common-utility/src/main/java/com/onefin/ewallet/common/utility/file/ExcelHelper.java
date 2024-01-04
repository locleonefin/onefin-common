package com.onefin.ewallet.common.utility.file;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.Optional;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

@Component("excelHelper")
public class ExcelHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelHelper.class);

	public static final String DATE = "DATE";

	public Workbook getWorkbook(MultipartFile multipartFile) throws IOException {
		Workbook workbook;
		if (multipartFile.getOriginalFilename().endsWith("xlsx")) {
			workbook = new XSSFWorkbook(multipartFile.getInputStream());
		} else if (multipartFile.getOriginalFilename().endsWith("xls")) {
			workbook = new HSSFWorkbook(multipartFile.getInputStream());
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}

	public Workbook getWorkbook(File file) throws IOException {
		Workbook workbook;
		if (file.getName().endsWith("xlsx")) {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} else if (file.getName().endsWith("xls")) {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}

	// Get Workbook
	public static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
		Workbook workbook = null;
		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}

	public Workbook createWorkbook(String excelFilePath) {
		Workbook workbook;

		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook();
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}

	public CellStyle createStyleForHeader(Sheet sheet) {
		// Create font
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		// font.setColor(IndexedColors.WHITE.getIndex());
		// font.setFontHeightInPoints((short) 14);

		// Create CellStyle
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		return cellStyle;
	}

	public Sheet getSheet(Workbook workbook, int sheetIndex) {
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		if (sheet == null) {
			sheet = workbook.getSheetAt(sheetIndex);
		}

		return sheet;
	}

	public Row getRow(Sheet sheet, int rowIndex) {
		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}

		return row;
	}

	public Cell getCell(Row row, int columnIndex) {
		Cell cell = row.getCell(columnIndex);
		if (cell == null) {
			cell = row.createCell(columnIndex);
		}

		return cell;
	}

	public static Object getCellValue(Cell cell, String subType) {
		CellType cellType = cell.getCellType();
		Object cellValue;

		switch (cellType) {
			case BOOLEAN:
				cellValue = cell.getBooleanCellValue();
				break;
			case FORMULA:
				Workbook workbook = cell.getSheet().getWorkbook();
				FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
				cellValue = evaluator.evaluate(cell).getNumberValue();
				break;
			case NUMERIC:
				if (DATE.equals(subType)) {
					cellValue = cell.getDateCellValue();
				} else {
					cellValue = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
				}
				break;
			case STRING:
				cellValue = cell.getStringCellValue();
				break;
			default:
				cellValue = "";
				break;
		}

		return cellValue;
	}

	public void autosizeColumn(Sheet sheet, int lastColumn) {
		for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
			sheet.autoSizeColumn(columnIndex);
		}
	}

	public void evaluateAllCellInWorkbook(Workbook workbook, Sheet sheet) {
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == CellType.FORMULA) {
					evaluator.evaluateFormulaCell(cell);
				}
			}
		}
	}

	public void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
		try (OutputStream os = new FileOutputStream(excelFilePath)) {
			workbook.write(os);
		}
	}

	public byte[] writeWorkbookToByteArray(File file) throws IOException {
		int index = file.getName().lastIndexOf(".");
		String type = file.getName().substring(index);

		if (".xlsx".equalsIgnoreCase(type) || ".xls".equalsIgnoreCase(type)) {
			Workbook workbook = getWorkbook(file);

			// write excel work book to byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				workbook.write(bos);
			} catch (Exception ex) {
				throw new RuntimeException("Can not write data to output stream: " + ex);
			} finally {
				try {
					workbook.close();
					bos.close();
				} catch (IOException e) {
					// todo
				}
			}

			return bos.toByteArray();
		} else {
			throw new IOException("Unsupported file type: " + type);
		}
	}

	public CellStyle styleForSettleExcel(Workbook workbook) {
		// Create font
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);

		return style;
	}

	public static Integer isBelongToMergedCell(Sheet sheet, Cell cell) {

		try {
			for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
				CellRangeAddress region = sheet.getMergedRegion(i); //Region of merged cells
//				LOGGER.info("region: {}", region.formatAsString());
				if (region.isInRange(cell)) {
					LOGGER.info("Found region with index: {} and region: {}", i, region.formatAsString());
					return i;
				}
			}
			LOGGER.info("Not found merged region match, return null");
			return null;

		} catch (Exception e) {
			LOGGER.error("Error Occurred", e);
			return null;

		}

	}

//	public static String readCell(FormulaEvaluator formulaEvaluator, Sheet sheet, Cell cell) {
//		Integer indexRegion = ExcelHelper.isBelongToMergedCell(sheet, cell);
//		if (indexRegion != null) {
//			CellRangeAddress region = sheet.getMergedRegion(indexRegion);
////					Row firstRow = sheet.getRow(region.getFirstRow());
////					Cell firstCell = firstRow.getCell(region.getFirstColumn());
//			return ExcelHelper.readContentFromMergedCells(formulaEvaluator, sheet, region);
//		} else {
//			return ExcelHelper.readStringValue(formulaEvaluator, cell);
//		}
//	}

	public static String readCell(FormulaEvaluator formulaEvaluator, Sheet sheet, Cell cell) {
		if (cell != null){
			Optional<CellRangeAddress> cellAddresses = sheet.getMergedRegions().stream()
					.filter(region-> region.isInRange(cell))
					.findFirst();
			if (cellAddresses.isPresent()){
				return ExcelHelper.readContentFromMergedCells(formulaEvaluator, sheet, cellAddresses.get());
			}else{
				return ExcelHelper.readStringValue(formulaEvaluator, cell);
			}
		} else{
			return "";
		}
	}

	public static String readContentFromMergedCells(FormulaEvaluator formulaEvaluator, Sheet sheet, CellRangeAddress mergedCells) {
		return readStringValue(formulaEvaluator, sheet.getRow(mergedCells.getFirstRow()).getCell(mergedCells.getFirstColumn()));
	}

	public static String readStringValue(FormulaEvaluator formulaEvaluator, Cell cell) {
		try {
			switch (cell.getCellType()) {
				case STRING:
					return cell.getStringCellValue();
				case NUMERIC:
					return String.valueOf(cell.getNumericCellValue());
				case FORMULA:
					switch (formulaEvaluator.evaluateFormulaCell(cell)) {
						case NUMERIC:
							return String.valueOf(cell.getNumericCellValue());
						case STRING:
							return cell.getStringCellValue();
						default:
							return "";
					}
				default:
					return "";
			}
		} catch (Exception e) {
			LOGGER.error("Error occurred, return null ", e);
			return "";
		}

	}

}