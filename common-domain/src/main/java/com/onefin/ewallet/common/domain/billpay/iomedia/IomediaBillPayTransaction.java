package com.onefin.ewallet.common.domain.billpay.iomedia;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;

import lombok.Data;

@Data
@Entity
@Table(name = "Iomedia_Billpay_Transaction")
public class IomediaBillPayTransaction extends AbstractBaseEwalletTrans {

	@Column
	private String serviceCode;		// buyCard | directTopup | payBill | sendSMS
	
	@Column
	private String providerCode;

	@Column
	private String partnerCode;

	@Column
	private String productCode;			// telcoCode

	@Column
	private String providerName;

	@Column
	private String billingCode;			// mobileNo 

	@Column
	private String billName;

	@Column
	private String billAddress;

	@Column
	private String billCycle;
	
	@Column
	private int quantity;

	@Column
	private BigDecimal paidAmount;		// topupAmount   : số tiền KH đã thanh toán cho giao dịch, yêu cầu phải hợp lệ với số tiền nợ cước trên hàm query
	
	@Column
	private String reciever;			// Email/Số điện thoại người nhận (mặc định là chuỗi rỗng)
	
	@Column
	private String resCode;

	@Column
	private String resMessage;
	
	@Column
	private BigDecimal amount;			// Số tiền hóa đơn đã được thanh toán thành công
	
	@Column
	private BigDecimal discountValue;
	
	@Column
	private BigDecimal debitValue;
	
	@Column
	private String mobileType;			// Loại thuê bao (TT/TS) hiện tại chỉ áp dụng với mạng Viettel

	@Column
	private String walletId;
	
	@Column
	private String phone;

	@Column
	private String email;

	@Column
	private String transTime;

	@Column
	private String smsTemplate;
	
	@Column
	private String transType;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "iomediaTransaction")
	private Set<IomediaDataCard> dataCards;

	public Set<IomediaDataCard> getDataCards() {
		if (this.dataCards == null) {
			this.dataCards = new HashSet<>();
		}
		return this.dataCards;
	}

	public void setDataCards(Set<IomediaDataCard> dataCards) {
		this.getDataCards().clear();
		this.getDataCards().addAll(dataCards);
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IomediaBillPayTransaction that = (IomediaBillPayTransaction) o;

        return (this.getId().equals(that.getId()));
    }
	
	@Override
    public int hashCode() {
        int result = this.getId().hashCode();
        return 37 * result;
    }

}
