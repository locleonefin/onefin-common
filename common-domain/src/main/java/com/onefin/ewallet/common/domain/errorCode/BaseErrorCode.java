package com.onefin.ewallet.common.domain.errorCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Base_Error_Code")
public class BaseErrorCode {

	@Id
	@Column(unique = true, nullable = false)
	@CsvBindByName(column = "code")
	protected String code;

	@Column
	@CsvBindByName(column = "message_vi")
	protected String messageVi;

	@Column
	@CsvBindByName(column = "message_en")
	protected String messageEn;

	@Column
	protected String connCode;

	public BaseErrorCode(String[] params) {
		if (params == null || params.length == 0) {
			return;
		}

		if (params.length >= 1) {
			this.code = params[0];
		}
		if (params.length >= 2) {
			this.messageVi = params[1];
		}
		if (params.length >= 3) {
			this.messageEn = params[2];
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		BaseErrorCode that = (BaseErrorCode) o;

		return (this.code.equals(that.code));
	}

	@Override
	public int hashCode() {
		int result = this.code.hashCode();
		return 31 * result;
	}

}
