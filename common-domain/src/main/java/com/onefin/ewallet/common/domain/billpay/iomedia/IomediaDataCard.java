package com.onefin.ewallet.common.domain.billpay.iomedia;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.constants.DomainConstants;

import lombok.Data;

@Data
@Entity
@Table(name = "Iomedia_Data_Card")
public class IomediaDataCard {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	@JsonIgnore
	private UUID id;

	@Column
	private String serial;
	
	@JsonIgnore
	@Column(length = 1000)
	private String pinCodeEncrypted;
	
	@JsonIgnore
	@Column
	private Date expiredate;

	@Column
	private String pinCode;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iomedia_bill_pay_transaction_id")
    //@OnDelete(action = OnDeleteAction.CASCADE)
	private IomediaBillPayTransaction iomediaTransaction;

	@JsonIgnore
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	public String getExpireDate() {
		if (this.expiredate != null) {
			return (new SimpleDateFormat(DomainConstants.DATE_FORMAT_TRANS)).format(expiredate);
		}
		return "";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IomediaDataCard that = (IomediaDataCard) o;

        try {
        	return (this.serial.equals(that.serial) && this.pinCode.equals(that.pinCode));
        } catch (Exception e) {}
        
        return false;
    }
	
	@Override
    public int hashCode() {
		try {
	        int result = this.serial.hashCode() + this.pinCode.hashCode();
	        return 37 * result;
		} catch (Exception e) {}
		return -1;
    }
}
