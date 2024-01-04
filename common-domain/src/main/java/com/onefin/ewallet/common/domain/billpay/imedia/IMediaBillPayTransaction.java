package com.onefin.ewallet.common.domain.billpay.imedia;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;
import com.onefin.ewallet.common.domain.billpay.iomedia.IomediaDataCard;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "imedia_Billpay_Transaction")
public class IMediaBillPayTransaction extends AbstractBaseEwalletTrans {

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

	@Column
	private BigDecimal merchantBalance;

	@Column
	private String originalTransId;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "iMediaBillPayTransaction")
	private Set<IMediaDataCard> dataCards;

	public Set<IMediaDataCard> getDataCards() {
		if (this.dataCards == null) {
			this.dataCards = new HashSet<>();
		}
		return this.dataCards;
	}

	public void setDataCards(Set<IMediaDataCard> dataCards) {
		this.getDataCards().clear();
		this.getDataCards().addAll(dataCards);
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IMediaBillPayTransaction that = (IMediaBillPayTransaction) o;

        return (this.getId().equals(that.getId()));
    }
	
	@Override
    public int hashCode() {
        int result = this.getId().hashCode();
        return 37 * result;
    }

}
