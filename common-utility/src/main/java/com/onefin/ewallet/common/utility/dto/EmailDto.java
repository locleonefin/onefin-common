package com.onefin.ewallet.common.utility.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class EmailDto {

	private List<String> email;

	public EmailDto() {

	}

	@JsonProperty("email")
	public void setEmail(List<String> email) {
		this.email = (email != null && email.size() > 0) ? email : new ArrayList<>();
	}

	private List<String> emailCC;

	@JsonProperty("emailCC")
	public void setEmailCC(List<String> emailCC) {
		this.emailCC = (emailCC != null && emailCC.size() > 0) ? emailCC : new ArrayList<>();
	}

	private List<String> emailBCC;

	@JsonProperty("emailBCC")
	public void setEmailBCC(List<String> emailBCC) {
		this.emailBCC = (emailBCC != null && emailBCC.size() > 0) ? emailBCC : new ArrayList<>();
	}

	private Map<String, Object> payload;

	private String bucket;

	private List<String> attachments;

	@JsonProperty("attachments")
	public void setAttachments(List<String> attachments) {
		this.attachments = (attachments != null && attachments.size() > 0) ? attachments : new ArrayList<>();
	}

	private String subject;

	@NotEmpty(message = "Not empty templateId")
	private String templateId;

	public EmailDto(List<String> email, List<String> emailCC, List<String> emailBCC, Map<String, Object> payload, String bucket,
					List<String> attachments, String subject, String templateId) {
		this.email = email;
		this.emailCC = emailCC;
		this.emailBCC = emailBCC;
		this.payload = payload;
		this.bucket = bucket;
		this.attachments = attachments;
		this.subject = subject;
		this.templateId = templateId;
	}

}
