package com.onefin.ewallet.common.domain.notification.email;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.base.StringListConverter;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import com.onefin.ewallet.common.domain.notification.common.KeyValueModel;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Email_Log")
public class EmailLog {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private String status;

	private String bucket;

	@Column(columnDefinition = "LONGTEXT")
	@Convert(converter = StringListConverter.class)
	private List<String> attachments;

	@ElementCollection
	private List<KeyValueModel> payload;

	/********** Header ************/

	private String fromSender;

	private String fromName;

	@Column
	@Convert(converter = StringListConverter.class)
	private List<String> toReceiver;

	@Column
	@Convert(converter = StringListConverter.class)
	private List<String> cc;

	@Column
	@Convert(converter = StringListConverter.class)
	private List<String> bcc;

	private String replyTo;

	private String replyToName;

	private String subject;

	private String bodyText;

	/********** Header ************/

	private String templateId;

}
