package com.onefin.ewallet.common.domain.merchant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Entity
@Table(name = "merchant_info_view")
public class MerchantInfo {

	@Id
	@Column(name = "ID")
	@JsonIgnore
	private long id;

	@Column(name = "PRIMARY_MID")
	private String primaryMid;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "TID")
	private String tid;

	@Lob
	@Column(name = "ENABLED_CURRENCY")
	private String enableCurrency;

	@Column(name = "BUSINESS_NAME")
	private String businessName;

	@Column(name = "CONTACT_NO")
	private String contactNo;

	@Lob
	@Column(name = "SHOP_LOGO")
	private String shopLogo;

	@Column(name = "MERCHANT_CODE")
	private String merchantCode;

	@Column(name = "AUTHORISER_EMAIL")
	private String authoriserEmail;

//	@Column(name = "PARTNER_REF_MID")
//	private String partnerRefMid;

	@Column(name = "BUSINESS_REG_NO")
	private String businessRegNo;

	@Column(name = "PROCESSING_FEE_FIXED_AMT")
	private BigDecimal processingFeeFixedAmt;

	@Column(name = "PROCESSING_FEE_FIXED_PERCENT")
	private BigDecimal processingFeeFixedPercent;

	@Column(name = "DOMESTIC_PROCESSING_FEE_FIXED_AMT")
	private String domesticProcessingFeeFixedAmt;

	@Column(name = "DOMESTIC_PROCESSING_FEE_FIXED_PERCENT")
	private String domesticProcessingFeeFixedPercent;

	@Column(name = "INTERNATIONAL_PROCESSING_FEE_FIXED_AMT")
	private String internationalProcessingFeeFixedAmt;

	@Column(name = "INTERNATIONAL_PROCESSING_FEE_FIXED_PERCENT")
	private String internationalProcessingFeeFixedPercent;

	@Column(name = "SHOP_NAME")
	private String shopName;

	@Column(name = "MASTER_MERCHANT_MID")
	private String masterMerchantMid;

}
