package com.onefin.ewallet.common.base.report.service;

import com.onefin.ewallet.common.base.constants.OneFinConstants;
import com.onefin.ewallet.common.base.report.model.*;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;


@Service
public class ExcelExportService {

	private static final String DEFAULT_HEADER_BACKGROUND_COLOR_HEX = "A9D08E";
	private static final String DEFAULT_DATA_COLOR = "AUTOMATIC";
	private static final String DEFAULT_BORDER_STYLE = "THIN";
	private static final String DEFAULT_ALIGNMENT = "CENTER";
	private static final Integer COLUMN_A_INDEX = 0;
	private static final Integer COLUMN_B_INDEX = 1;
	private static final Integer COLUMN_F_INDEX = 5;
	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat(OneFinConstants.DATE_FORMAT_ddSMMSyyyy_HHTDmmTDss);
	private static final String DEFAULT_COUNTER_HEADER = "STT";
	private static final String STRING_DATA_TYPE = "STRING";
	private static final String DATE_DATA_TYPE = "DATE";
	private static final String INTEGER_DATA_TYPE = "INTEGER";
	private static final String INT_DATA_TYPE = "int";
	private static final String DOUBLE_DATA_TYPE = "DOUBLE";
	private static final String BOOLEAN_DATA_TYPE = "BOOLEAN";
	private static final String BIGDECIMAL_DATA_TYPE = "BIGDECIMAL";

	private int getColumnIndexFromName(String columnName) {
		return ALPHABET.indexOf(columnName);
	}

	private void removeRowAndShiftCellsUp(XSSFSheet sheet, int rowIndex) {
		int lastRowNum = sheet.getLastRowNum();
		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
		}
		if (rowIndex == lastRowNum) {
			XSSFRow removingRow = sheet.getRow(rowIndex);
			if (removingRow != null) {
				sheet.removeRow(removingRow);
			}
		}
	}

	private Row getOrCreateRow(Sheet sheet, Integer rowIndex) {
		if (rowIndex != null) {
			Row row = sheet.getRow(rowIndex - 1);
			return (row != null) ? row : sheet.createRow(rowIndex - 1);
		} else {
			Row row = sheet.getRow(1);
			return (row != null) ? row : sheet.createRow(1);
		}
	}

	private Cell getOrCreateCell(Row row, String columnName) {
		if (columnName != null) {
			int columnIndex = getColumnIndexFromName(columnName.toUpperCase());
			Cell cell = row.getCell(columnIndex);
			return (cell != null) ? cell : row.createCell(columnIndex);
		} else {
			Cell cell = row.getCell(COLUMN_F_INDEX);
			return (cell != null) ? cell : row.createCell(COLUMN_F_INDEX);
		}
	}

	private Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		if (fieldName.contains(".")) {  // If the field name represents a hierarchy
			String[] splitField = fieldName.split("\\.", 2);
			Field field = clazz.getDeclaredField(splitField[0]);
			return getField(field.getType(), splitField[1]);
		} else {
			return getDeclaredField(clazz, fieldName);
		}
	}

	private Field getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			Class<?> superclass = clazz.getSuperclass();
			if (superclass == null) {
				throw e;
			} else {
				return getDeclaredField(superclass, fieldName);
			}
		}
	}

	private Object getNestedInstance(Object instance, String fieldName) throws IllegalAccessException, NoSuchFieldException {
		if (fieldName.contains(".")) {
			String[] splitField = fieldName.split("\\.", 2);
			Field field = getDeclaredField(instance.getClass(), splitField[0]);
			field.setAccessible(true);
			Object nestedInstance = field.get(instance);
			return getNestedInstance(nestedInstance, splitField[1]);
		} else {
			return instance;
		}
	}

	public <T> long getUniqueRecords(List<T> records, String uniqueFieldName) throws NoSuchFieldException {
		if (records.isEmpty()) {
			return 0;
		}
		Field uniqueField = records.get(0).getClass().getDeclaredField(uniqueFieldName);
		uniqueField.setAccessible(true);
		return StreamSupport.stream(records.spliterator(), false)
				.filter(Objects::nonNull)
				.map(record -> {
					try {
						return uniqueField.get(record);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				})
				.filter(Objects::nonNull)
				.distinct()
				.count();
	}

	public <T> String getEarliestDate(List<T> records, String uniqueFieldName) throws Exception {
		if (records.isEmpty()) {
			return "";
		}
		Field uniqueField = records.get(0).getClass().getDeclaredField(uniqueFieldName);
		uniqueField.setAccessible(true);
		ZoneId hoChiMinhZoneId = ZoneId.of(DomainConstants.HO_CHI_MINH_TIME_ZONE);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(OneFinConstants.DATE_FORMAT_dd_MM_yyyy);
		return StreamSupport.stream(records.spliterator(), false)
				.filter(Objects::nonNull)
				.map(record -> {
					try {
						return (Date) uniqueField.get(record);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				})
				.filter(Objects::nonNull)
				.map(date -> date.toInstant().atZone(hoChiMinhZoneId).toLocalDateTime())
				.filter(Objects::nonNull)
				.min(LocalDateTime::compareTo)
				.map(time -> time.toLocalDate().format(outputFormatter))
				.orElse(null);
	}

	public <T> String getLatestDate(List<T> records, String uniqueFieldName) throws Exception {
		if (records.isEmpty()) {
			return "";
		}
		ZoneId hoChiMinhZoneId = ZoneId.of(DomainConstants.HO_CHI_MINH_TIME_ZONE);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(OneFinConstants.DATE_FORMAT_dd_MM_yyyy);
		Field uniqueField = records.get(0).getClass().getDeclaredField(uniqueFieldName);
		uniqueField.setAccessible(true);
		return StreamSupport.stream(records.spliterator(), false)
				.filter(Objects::nonNull)
				.map(record -> {
					try {
						return (Date) uniqueField.get(record);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				})
				.filter(Objects::nonNull)
				.map(date -> date.toInstant().atZone(hoChiMinhZoneId).toLocalDateTime())
				.filter(Objects::nonNull)
				.max(LocalDateTime::compareTo)
				.map(time -> time.toLocalDate().format(outputFormatter))
				.orElse(null);
	}

	public <T> BigDecimal getTotalAmount(List<T> records, String uniqueFieldName) throws Exception {
		if (records.isEmpty()) {
			return BigDecimal.ZERO;
		}
		Field uniqueField = records.get(0).getClass().getDeclaredField(uniqueFieldName);
		uniqueField.setAccessible(true);
		return StreamSupport.stream(records.spliterator(), false)
				.filter(Objects::nonNull)
				.map(record -> {
					try {
						Object fieldValue = uniqueField.get(record);
						if (fieldValue instanceof BigDecimal) {
							return (BigDecimal) fieldValue;
						} else if (fieldValue instanceof Double) {
							return BigDecimal.valueOf((Double) fieldValue);
						} else {
							throw new IllegalArgumentException("Unexpected value type: " + fieldValue.getClass());
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				})
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public <T> BigDecimal getSettledTotalAmount(List<T> records, String amountFieldName, String statusFieldName) throws Exception {
		if (records.isEmpty()) {
			return BigDecimal.ZERO;
		}
		Field amountField = records.get(0).getClass().getDeclaredField(amountFieldName);
		amountField.setAccessible(true);
		Field statusField = records.get(0).getClass().getDeclaredField(statusFieldName);
		statusField.setAccessible(true);
		return StreamSupport.stream(records.spliterator(), false)
				.filter(Objects::nonNull)
				.map(record -> {
					try {
						if ("SETTLED".equals(statusField.get(record))) {
							Object fieldValue = amountField.get(record);
							if (fieldValue instanceof BigDecimal) {
								return (BigDecimal) fieldValue;
							} else if (fieldValue instanceof Double) {
								return BigDecimal.valueOf((Double) fieldValue);
							} else {
								throw new IllegalArgumentException("Unexpected value type: " + fieldValue.getClass());
							}
						}
						return null;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				})
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public ByteArrayResource exportToExcel(List<List<?>> recordSheets, ExcelConfiguration excelConfig) throws Exception {
		WorkbookConfiguration workbookConfig = excelConfig.getWorkbook();
		XSSFWorkbook templateWorkbook = new XSSFWorkbook(new ClassPathResource(workbookConfig.getTemplateName()).getInputStream());
		ByteArrayResource result;
		XSSFCellStyle headerStyle = null;
		if (!recordSheets.isEmpty()) {
			for (int i = 0; i < recordSheets.size() - 1; i++) {
				templateWorkbook.cloneSheet(0);
			}
			for (int i = 0; i < recordSheets.size(); i++) {
				List<?> recordSheet = recordSheets.get(i);
				Sheet templateSheet = templateWorkbook.getSheetAt(i);
				templateWorkbook.setSheetName(i, workbookConfig.getSheets().get(i).getSheetName());
				if (workbookConfig.getCalculateAggregatedData()) {
					Row earliestDateRow = getOrCreateRow(templateSheet, workbookConfig.getEarliestDateRow());
					Cell earliestDateCell = getOrCreateCell(earliestDateRow, workbookConfig.getEarliestDateColumn());
					earliestDateCell.setCellValue(getEarliestDate(recordSheet, workbookConfig.getDateFieldName()));
					Row latestDateRow = getOrCreateRow(templateSheet, workbookConfig.getLatestDateRow());
					Cell latestDateCell = getOrCreateCell(latestDateRow, workbookConfig.getLatestDateColumn());
					latestDateCell.setCellValue(getLatestDate(recordSheet, workbookConfig.getDateFieldName()));
					Row valueCountRow = getOrCreateRow(templateSheet, workbookConfig.getValueCountRow());
					Cell valueCountCell = getOrCreateCell(valueCountRow, workbookConfig.getValueCountColumn());
					valueCountCell.setCellValue(getUniqueRecords(recordSheet, workbookConfig.getUniqueFieldCounterName()));
					Row totalAmountRow = getOrCreateRow(templateSheet, workbookConfig.getTotalAmountRow());
					Cell totalAmountCell = getOrCreateCell(totalAmountRow, workbookConfig.getTotalAmountColumn());
					BigDecimal amountTotal = getTotalAmount(recordSheet, workbookConfig.getAmountFieldName());
					totalAmountCell.setCellValue(amountTotal.doubleValue());
					int lastWrittenRowIndex;
					if (workbookConfig.getCalculateSettledAmount()) {
						Row settledAmountRow = getOrCreateRow(templateSheet, workbookConfig.getSettledAmountRow());
						Cell settledAmountCell = getOrCreateCell(settledAmountRow, workbookConfig.getSettledAmountColumn());
						BigDecimal settledTotal = getSettledTotalAmount(recordSheet, workbookConfig.getAmountFieldName(), workbookConfig.getStatusFieldName());
						lastWrittenRowIndex = settledAmountRow.getRowNum();
						settledAmountCell.setCellValue(settledTotal.doubleValue());
					} else {
						lastWrittenRowIndex = totalAmountRow.getRowNum();
					}
					if (workbookConfig.getWriteHeader()) {
						int startRowIndex = lastWrittenRowIndex + 1;
						int endRowIndex = startRowIndex + 5;
						for (int j = endRowIndex; j >= startRowIndex; j--) {
							removeRowAndShiftCellsUp((XSSFSheet) templateSheet, j);
						}
					}
				}
				DetailConfiguration detailConfiguration = workbookConfig.getSheets().get(i);
				if (workbookConfig.getWriteHeader()){
					headerStyle = templateWorkbook.createCellStyle();
					// Horizontal Alignment
					String headerHorizontalAlignment = (detailConfiguration.getHeaderHorizontalAlignment() != null)
							? detailConfiguration.getHeaderHorizontalAlignment().toUpperCase()
							: DEFAULT_ALIGNMENT;
					headerStyle.setAlignment(HorizontalAlignment.valueOf(headerHorizontalAlignment));
					// Vertical Alignment
					String headerVerticalAlignment = (detailConfiguration.getHeaderVerticalAlignment() != null)
							? detailConfiguration.getHeaderVerticalAlignment().toUpperCase()
							: DEFAULT_ALIGNMENT;
					headerStyle.setVerticalAlignment(VerticalAlignment.valueOf(headerVerticalAlignment));
					// Border
					String borderStyleName = (detailConfiguration.getHeaderBorder() != null)
							? detailConfiguration.getHeaderBorder().toUpperCase()
							: DEFAULT_BORDER_STYLE;
					BorderStyle borderStyle = BorderStyle.valueOf(borderStyleName);
					headerStyle.setBorderBottom(borderStyle);
					headerStyle.setBorderLeft(borderStyle);
					headerStyle.setBorderRight(borderStyle);
					headerStyle.setBorderTop(borderStyle);
					// header color similar to OneFin's header
					int rgb = Integer.parseInt(DEFAULT_HEADER_BACKGROUND_COLOR_HEX, 16);
					byte red = (byte) ((rgb >> 16) & 0xFF);
					byte green = (byte) ((rgb >> 8) & 0xFF);
					byte blue = (byte) (rgb & 0xFF);
					byte[] rgbBytes = new byte[] { red, green, blue };
					headerStyle.setFillForegroundColor(new XSSFColor(rgbBytes, new DefaultIndexedColorMap()));
					headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					Font headerFont = templateWorkbook.createFont();
					headerFont.setBold(true);
					headerStyle.setFont(headerFont);
				}
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			templateWorkbook.write(baos);
			SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(new ByteArrayInputStream(baos.toByteArray())), 100);
			baos.close();
			for (int i = 0; i < recordSheets.size(); i++) {
				List<?> recordSheet = recordSheets.get(i);
				SXSSFSheet sheet = workbook.getSheetAt(i);
				int cellIndex = COLUMN_B_INDEX;
				DetailConfiguration detailConfiguration = workbookConfig.getSheets().get(i);
				if (workbookConfig.getWriteHeader()) {
					Row headerRow = sheet.getRow(workbookConfig.getHeaderStartRow() - 1);
					if (headerRow == null) {
						headerRow = sheet.createRow(workbookConfig.getHeaderStartRow() - 1);
					}
					Cell counterCell = headerRow.createCell(COLUMN_A_INDEX);

					String counterName = (detailConfiguration.getCounterName() != null)
							? detailConfiguration.getCounterName()
							: DEFAULT_COUNTER_HEADER;
					counterCell.setCellValue(counterName);
					counterCell.setCellStyle(headerStyle);
					for (ColumnConfiguration columnConfig : detailConfiguration.getColumn()) {
						Cell cell = headerRow.getCell(cellIndex);
						if (cell == null) {
							cell = headerRow.createCell(cellIndex);
						}
						cell.setCellValue(columnConfig.getDisplayName());
						cell.setCellStyle(headerStyle);
						cellIndex++;
					}
					while (cellIndex <= headerRow.getLastCellNum()) {
						Cell cell = headerRow.getCell(cellIndex);
						if (cell != null) {
							headerRow.removeCell(cell);
						}
						cellIndex++;
					}
				}
				for (int j = 1; j <= detailConfiguration.getColumn().size(); j++) {
					sheet.setColumnWidth(j, 37 * 256);
				}
				CellStyle style = workbook.createCellStyle();
				// Horizontal Alignment
				String horizontalAlignment = (detailConfiguration.getDataHorizontalAlignment() != null)
						? detailConfiguration.getDataHorizontalAlignment().toUpperCase()
						: DEFAULT_ALIGNMENT;
				style.setAlignment(HorizontalAlignment.valueOf(horizontalAlignment));
				// Vertical Alignment
				String verticalAlignment = (detailConfiguration.getDataVerticalAlignment() != null)
						? detailConfiguration.getDataVerticalAlignment().toUpperCase()
						: DEFAULT_ALIGNMENT;
				style.setVerticalAlignment(VerticalAlignment.valueOf(verticalAlignment));
				// Border
				String borderStyleName = (detailConfiguration.getDataBorder() != null)
						? detailConfiguration.getDataBorder().toUpperCase()
						: DEFAULT_BORDER_STYLE;
				BorderStyle borderStyle = BorderStyle.valueOf(borderStyleName);
				style.setBorderBottom(borderStyle);
				style.setBorderLeft(borderStyle);
				style.setBorderRight(borderStyle);
				style.setBorderTop(borderStyle);
				// Color
				String colorName = (detailConfiguration.getDataColor() != null)
						? detailConfiguration.getDataColor().toUpperCase()
						: DEFAULT_DATA_COLOR;
				IndexedColors indexedDataColor = IndexedColors.valueOf(colorName);
				Font dataFont = workbook.createFont();
				dataFont.setColor(indexedDataColor.getIndex());
				style.setFont(dataFont);
				int counter = 1;
				int rowNumber = workbookConfig.getDataStartRow() - 1;
				// Insert data into cells
				for (Object record : recordSheet) {
					Row row = sheet.createRow(rowNumber);
					cellIndex = COLUMN_B_INDEX;
					for (DataConfiguration dataConfig : detailConfiguration.getData()) {
						Class<?> clazz = record.getClass();
						Field field = getField(clazz, dataConfig.getName());
						field.setAccessible(true);
						Object nestedInstance = getNestedInstance(record, dataConfig.getName());
						Object fieldValue = field.get(nestedInstance);
						Cell cell1 = row.createCell(COLUMN_A_INDEX);
						cell1.setCellValue(counter);
						cell1.setCellStyle(style);
						Cell cell = row.createCell(cellIndex);
						cell.setCellStyle(style);
						String type = dataConfig.getDataType();
						if (fieldValue == null) {
							cell.setCellValue("");
						} else {
							if (DATE_DATA_TYPE.equalsIgnoreCase(type)) {
								SimpleDateFormat sdf;
								sdf = (detailConfiguration.getDateFormat() != null) ? new SimpleDateFormat(detailConfiguration.getDateFormat())
										: DEFAULT_DATE_FORMATTER;
								java.util.Date dateValue = (java.util.Date) fieldValue;
								cell.setCellValue(sdf.format(dateValue));
							} else if (STRING_DATA_TYPE.equalsIgnoreCase(type)) {
								cell.setCellValue((String) fieldValue);
							} else if (INTEGER_DATA_TYPE.equalsIgnoreCase(type) || (INT_DATA_TYPE.equalsIgnoreCase(type))) {
								Integer value = (Integer) fieldValue;
								cell.setCellValue(value);
							} else if (DOUBLE_DATA_TYPE.equalsIgnoreCase(type)) {
								Double value = (Double) fieldValue;
								cell.setCellValue(value);
							} else if (BOOLEAN_DATA_TYPE.equalsIgnoreCase(type)) {
								Boolean value = (Boolean) fieldValue;
								cell.setCellValue(value);
							} else if (BIGDECIMAL_DATA_TYPE.equalsIgnoreCase(type)) {
								BigDecimal value = (BigDecimal) fieldValue;
								cell.setCellValue(value.doubleValue());
							}
						}
						cellIndex++;
					}
					counter++;
					rowNumber++;
				}
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				workbook.write(bos);
				workbook.dispose();
			} finally {
				bos.close();
			}
			byte[] bytes = bos.toByteArray();
			result = new ByteArrayResource(bytes);
		} else {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			templateWorkbook.write(baos);
			SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(new ByteArrayInputStream(baos.toByteArray())), 100);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				workbook.write(bos);
				workbook.dispose();
			} finally {
				bos.close();
			}
			byte[] bytes = bos.toByteArray();
			result = new ByteArrayResource(bytes);
		}
		return result;
	}

}
