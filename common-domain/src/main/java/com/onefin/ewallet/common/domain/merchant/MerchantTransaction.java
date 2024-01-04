package com.onefin.ewallet.common.domain.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "merchant_transaction")
public class MerchantTransaction {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)", name = "id")
	private UUID id;

	@Column(name = "tran_status")
	@CsvBindByName(column = "Status")
	private String tranStatus; // PENDING, SUCCESS, ERROR

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false, name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true, name = "updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column(name = "partner_trx_id")
	@CsvBindByName(column = "Transaction ID")
	private String partnerTrxId;

	@Column(name = "partner")
	private String partner; //PAYDI

	@Column(name = "partner_mid")
	@CsvBindByName(column = "MID (PAYDI)", required = true)
	private String partnerMid;

	@Column(name = "business_license")
	@CsvBindByName(column = "Business License", required = true)
	private String businessLicense; // Paydi: Tax code can be business license

	@Column(name = "merchant_name")
	@CsvBindByName(column = "Merchant Name")
	private String merchantName;

	@Column(name = "tid")
	@CsvBindByName(column = "Terminal ID")
	private String tid;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "settle_date")
	@CsvDate(value = "dd/MM/yyyy")
	@CsvBindByName(column = "Settle date")
	private Date settleDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "trx_date")
	@CsvDate(value = "dd/MM/yyyy")
	@CsvBindByName(column = "Trans Date")
	private Date trxDate;

	@Column(name = "trx_type")
	@CsvBindByName(column = "Transaction Type")
	private String trxType;

	@Column(name = "channel_type")
	@CsvBindByName(column = "Channel Type")
	private String channelType;

	@Column(name = "trx_amount")
	@CsvBindByName(column = "Trans Amount")
	private BigDecimal trxAmount;

	@Column(name = "billing_amount")
	@CsvBindByName(column = "Billing amount")
	private BigDecimal billingAmount;

	@Column(name = "bank_trans_fee")
	@CsvBindByName(column = "Bank Trans Fee")
	private BigDecimal bankTransFee;

	@Column(name = "net_amount")
	@CsvBindByName(column = "Net amount")
	private BigDecimal netAmount;

	@Column(name = "bank_trans_fee_rate")
	@CsvBindByName(column = "Bank Trans Fee Rate")
	private BigDecimal bankTransFeeRate;

	@Column(name = "card_type")
	@CsvBindByName(column = "CardType")
	private String cardType;

	@Column(name = "card_no")
	@CsvBindByName(column = "Card No/Real_card")
	private String cardNo;

	@Column(name = "ref_no")
	@CsvBindByName(column = "Reference No.")
	private String refNo;

	@Column(name = "main_category")
	@CsvBindByName(column = "Main Category")
	private String mainCategory;

	@Column(name = "currency")
	@CsvBindByName(column = "CURR_TYPE")
	private String currency;

	@Column(name = "checksum")
	@JsonIgnore
	private Boolean checksum;

	@Column(name = "bank_code")
	@JsonIgnore
	@CsvBindByName(column = "Bank Name")
	private String bankCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "merchant_settle_date")
	@CsvDate(value = "dd/MM/yyyy")
	@CsvBindByName(column = "Merchant Settle Date")
	private Date merchantSettleDate;

	@Column(name = "merchant_settle_amount")
	@CsvBindByName(column = "Merchant Settle Amount")
	private BigDecimal merchantSettleAmount;

	@Column(name = "merchant_fee")
	@CsvBindByName(column = "Merchant Fee")
	private BigDecimal merchantFee;

	@Column(name = "pay_card")
	private String payCard;

	@Column(name = "primary_mid")
	private String primaryMid;

	@Column(name = "business_name")
	private String businessName;

	@Column(name = "contact_no")
	private String contactNo;

	@Column(name = "merchant_code")
	private String merchantCode;
}
