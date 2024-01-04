package com.onefin.ewallet.common.domain.bank.vietin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "VietinNotifyStatus")
public class VietinNotifyStatus {

  @Id
  @GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
  @GeneratedValue(generator = DomainConstants.UUID)
  @Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
  private UUID id;

  @Column
  private String transId;
  @Column
  private String originalId;
  @Column
  private String channelId;
  @Column
  private String transTime;
  @Column
  private String transType;
  @Column
  private String serviceType;
  @Column
  private String sendBankId;
  @Column
  private String sendBranchId;
  @Column
  private String sendAcctId;
  @Column
  private String sendAcctName;
  @Column
  private String recvBankId;
  @Column
  private String recvBranchId;
  @Column
  private String recvAcctId;
  @Column
  private String recvAcctName;
  @Column
  private String statusCode;
  @Column
  private String statusMessage;
  @Column
  private String makerUserName;
  @Column
  private String makerRole;
  @Column
  private String makerFullName;
  @Column
  private String checkerUserName;
  @Column
  private String checkerRole;
  @Column
  private String checkerFullName;
  @Column
  private String custCode;
  @Column
  private String custName;
  @Column
  private String custAcct;
  @Column
  private String idCard;
  @Column
  private String phoneNo;
  @Column
  private String email;
  @Column
  private String term;
  @Column
  private String interest;
  @Column
  private String amount;
  @Column
  private String fee;
  @Column
  private String vat;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
  @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

}
