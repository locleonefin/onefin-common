package com.onefin.ewallet.common.base.report.model;

import lombok.Data;

import java.util.List;

@Data
public class WorkbookConfiguration {
	private String templateName;
	private List<DetailConfiguration> sheets;
	private String uniqueFieldCounterName;
	private String dateFieldName;
	private String amountFieldName;
	private Boolean calculateSettledAmount;
	private Integer settledAmountRow;
	private String settledAmountColumn;
	private String statusFieldName;
	private Integer earliestDateRow;
	private String earliestDateColumn;
	private Integer latestDateRow;
	private String latestDateColumn;
	private Integer valueCountRow;
	private String valueCountColumn;
	private Integer totalAmountRow;
	private String totalAmountColumn;
	private Boolean writeHeader;
	private Integer dataStartRow;
	private Integer headerStartRow;
	private Boolean calculateAggregatedData;
}
