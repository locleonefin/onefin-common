package com.onefin.ewallet.common.base.report.model;

import lombok.Data;

import java.util.List;

@Data
public class DetailConfiguration {
	private List<ColumnConfiguration> column;
	private String sheetName;

	private String headerBorder;
	//private String headerColor;
	private String headerVerticalAlignment;
	private String headerHorizontalAlignment;

	private List<DataConfiguration> data;

	private String dataBorder;
	private String dataColor;
	private String dataVerticalAlignment;
	private String dataHorizontalAlignment;
	private String dateFormat;

	private String counterName;

}