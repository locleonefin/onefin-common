package com.onefin.ewallet.common.base.search;

import com.onefin.ewallet.common.base.constants.OneFinConstants;
import com.onefin.ewallet.common.base.errorhandler.RuntimeInternalServerException;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class GenericRsqlSpecification<T> implements Specification<T> {

	private String property;
	private ComparisonOperator operator;
	private List<String> arguments;

	public GenericRsqlSpecification(String selector, ComparisonOperator operator, List<String> arguments) {
		this.property = selector;
		this.operator = operator;
		this.arguments = arguments;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Path propertyExpression = parseProperty(root);
		List<Object> args = castArguments(propertyExpression);
		Object argument = args.get(0);
		switch (RsqlSearchOperation.getSimpleOperator(operator)) {

			case EQUAL: {
				if (argument instanceof String) {
					return builder.like(propertyExpression, argument.toString().replace('*', '%'));
				} else if (argument == null) {
					return builder.isNull(propertyExpression);
				} else if (argument instanceof Date) {
					return builder.between(propertyExpression, (Date) argument, (Date) argument);
				} else if (argument instanceof UUID) {
					return builder.equal(propertyExpression, argument);
				} else {
					return builder.equal(propertyExpression, argument);
				}
			}
			case NOT_EQUAL: {
				if (argument instanceof String) {
					return builder.notLike(propertyExpression, argument.toString().replace('*', '%'));
				} else if (argument == null) {
					return builder.isNotNull(propertyExpression);
				} else {
					return builder.notEqual(propertyExpression, argument);
				}
			}
			case GREATER_THAN: {
				return builder.greaterThan(propertyExpression, argument.toString());
			}
			case GREATER_THAN_OR_EQUAL: {
				if (argument instanceof Date) {
					return builder.greaterThanOrEqualTo(propertyExpression, (Date) argument);
				} else {
					return builder.greaterThanOrEqualTo(propertyExpression, argument.toString());
				}
			}
			case LESS_THAN: {
				return builder.lessThan(propertyExpression, argument.toString());
			}
			case LESS_THAN_OR_EQUAL: {
				if (argument instanceof Date) {
					return builder.lessThanOrEqualTo(propertyExpression, (Date) argument);
				} else {
					return builder.lessThanOrEqualTo(propertyExpression, argument.toString());
				}
			}
			case IN:
				return propertyExpression.in(args);
			case NOT_IN:
				return builder.not(propertyExpression.in(args));
		}

		return null;
	}

	private Date getEndOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 23);
		calendar.add(Calendar.MINUTE, 59);
		calendar.add(Calendar.SECOND, 59);
		calendar.add(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	private Date getBeginOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 00);
		calendar.add(Calendar.MINUTE, 00);
		calendar.add(Calendar.SECOND, 00);
		calendar.add(Calendar.MILLISECOND, 000);
		return calendar.getTime();
	}

	// This method will help us diving deep into nested property using the dot convention
	// The originial tutorial did not have this, so it can only parse the shallow properties.
	private Path<String> parseProperty(Root<T> root) {
		Path<String> path;
		if (property.contains(".")) {
			// Nested properties
			String[] pathSteps = property.split("\\.");
			String step = pathSteps[0];
			path = root.get(step);

			for (int i = 1; i <= pathSteps.length - 1; i++) {
				path = path.get(pathSteps[i]);
			}
		} else {
			path = root.get(property);
		}
		return path;
	}

	private List<Object> castArguments(Path<?> propertyExpression) {
		Class<?> type = propertyExpression.getJavaType();

		List<Object> args = arguments.stream().map(arg -> {
			if (type.equals(Integer.class)) {
				return Integer.parseInt(arg);
			} else if (type.equals(UUID.class)) {
				return UUID.fromString(arg);
			} else if (type.equals(Long.class)) {
				return Long.parseLong(arg);
			} else if (type.equals(Date.class)) {
				if (arg.length() == 19) {
					String[] date = arg.split("\\*");
					DateTime dateTime = DateTime.parse(date[0] + " " + date[1], DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss")).withZone(DateTimeZone.forID(OneFinConstants.HO_CHI_MINH_TIME_ZONE));
					return dateTime.toDate();
				} else if (arg.length() == 10) {
					DateTime dateTime = DateTime.parse(arg, DateTimeFormat.forPattern("dd-MM-yyyy")).withZone(DateTimeZone.forID(OneFinConstants.HO_CHI_MINH_TIME_ZONE));
					switch (RsqlSearchOperation.getSimpleOperator(operator)) {
						case GREATER_THAN_OR_EQUAL: {
							return getBeginOfDate(dateTime.toDate());
						}
						case LESS_THAN_OR_EQUAL: {
							return getEndOfDate(dateTime.toDate());
						}
					}
					throw new RuntimeInternalServerException("RSQL - Invalid date conditional: " + arg + ", " + RsqlSearchOperation.getSimpleOperator(operator));
				} else {
					throw new RuntimeInternalServerException("RSQL - Invalid date format: " + arg + ", " + RsqlSearchOperation.getSimpleOperator(operator));
				}
			} else if (type.equals(BigDecimal.class)) {
				return new BigDecimal(arg);
			} else if (type.equals(Boolean.class)) {
				return Boolean.parseBoolean(arg);
			} else {
				return arg;
			}
		}).collect(Collectors.toList());

		return args;
	}

	// standard constructor, getter, setter
}
