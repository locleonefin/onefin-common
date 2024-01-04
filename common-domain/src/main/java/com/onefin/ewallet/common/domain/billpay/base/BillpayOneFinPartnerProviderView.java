package com.onefin.ewallet.common.domain.billpay.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Query for this view:
 * 
	DROP TABLE billpay_onefin_partner_provider_view;
	CREATE OR REPLACE VIEW billpay_onefin_partner_provider_view AS
	SELECT op.code as onefin_provider_code, 
		pp.provider_code as partner_provider_code, 
		pp.partner as partner, 
		op.name as provider_name, 
		op.billpay_service_code as service_code, 
		pp.service_type as service_type,
		pp.bill_prefix as bill_prefix,
		op.pay_index as pay_index,
		pp.active as active
	FROM billpay_onefin_provider op, billpay_partner_provider pp 
	WHERE op.code = pp.billpay_onefin_provider_code;
 * 
 * 
 * 
 * @author khanh
 *
 */
@Data
@Entity
@Table(name = "Billpay_Onefin_Partner_Provider_View")
public class BillpayOneFinPartnerProviderView {

	@Id
	@Column(unique = true, nullable = false)
	private String onefinProviderCode;
	
	@Column
	private String partnerProviderCode;

	@Column
	private String partner;

	@Column
	private String providerName;
	
	@Column
	private String serviceCode;		// OneFin Service Code
	
	@Column
	private String serviceType;		// Used for services from VietinBank only
	
	@Column
	private String billPrefix;
	
	@Column
	private Integer payIndex;

	@Column
	private boolean active;
}
