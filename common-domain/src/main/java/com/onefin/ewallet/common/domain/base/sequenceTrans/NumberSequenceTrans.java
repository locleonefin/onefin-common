package com.onefin.ewallet.common.domain.base.sequenceTrans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "number_sequence_trans")
public class NumberSequenceTrans {

	@Id
	@Column(nullable = false, length = 40, name = "name")
	private String name;

	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", name = "last_modified")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;

	@Column(nullable = false , name = "current_number")
	private long currentNumber;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 100", name = "dflt_block_size")
	private int dfltBlockSize;
}
