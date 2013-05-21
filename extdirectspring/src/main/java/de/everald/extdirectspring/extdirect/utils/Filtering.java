package de.everald.extdirectspring.extdirect.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import ch.ralscha.extdirectspring.filter.BooleanFilter;
import ch.ralscha.extdirectspring.filter.Comparison;
import ch.ralscha.extdirectspring.filter.DateFilter;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.NumericFilter;
import ch.ralscha.extdirectspring.filter.StringFilter;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Filtering<T> {

	public List<T> filter(Collection<Filter> filters, Collection<T> list, Class<T> clazz) {
		Map<String, Field> map = new HashMap<String, Field>();
		for (Field field : clazz.getDeclaredFields()) {
			map.put(field.getName(), field);
		}
		List<Predicate<T>> predicates = Lists.newArrayList();
		System.out.println(filters);
		for (Filter filter : filters) {
			if (map.containsKey(filter.getField())) {
				Field field = map.get(filter.getField());
				if (field.getType().getSimpleName().equals("String")) {
					StringPredicate p = new StringPredicate(((StringFilter) filter).getValue());
					p.setFieldName(field.getName());
					predicates.add(p);
				} else if (field.getType().getSimpleName().equals("Double")) {
					NumericFilter numericFilter = (NumericFilter) filter;
					DoubleComparisionPredicate p = new DoubleComparisionPredicate(numericFilter.getComparison(), numericFilter.getValue().doubleValue());
					p.setFieldName(field.getName());
					predicates.add(p);
				} else if (field.getType().getSimpleName().equals("Integer")) {
					NumericFilter numericFilter = (NumericFilter) filter;
					IntegerComparisionPredicate p = new IntegerComparisionPredicate(numericFilter.getComparison(), numericFilter.getValue().intValue());
					p.setFieldName(field.getName());
					predicates.add(p);
				} else if (field.getType().getSimpleName().equals("Boolean")) {
					BooleanPredicate p = new BooleanPredicate(((BooleanFilter) filter).getValue());
					p.setFieldName(field.getName());
					predicates.add(p);
				} else if (field.getType().getSimpleName().equals("Date")) {
					DateFilter dateFilter = (DateFilter) filter;
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
					try {
						Date d = formatter.parse(dateFilter.getValue());
						DateComparisionPredicate p = new DateComparisionPredicate(dateFilter.getComparison(), d);
						p.setFieldName(field.getName());
						predicates.add(p);
					} catch (ParseException e) {
						// nothing to do
					}
				}
			}
		}
		Iterable<T> filtered = Iterables.filter(list, Predicates.and(predicates));
		return ImmutableList.copyOf(filtered);
	}

	@Data
	private class StringPredicate implements Predicate<T> {
		private final String	value;
		private String			fieldName;

		@Override
		public boolean apply(T t) {
			Method m;
			String s = null;
			try {
				m = t.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
				s = (String) m.invoke(t);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return !Strings.isNullOrEmpty(s) && s.startsWith(value);
		}
	}

	@Data
	private class BooleanPredicate implements Predicate<T> {
		private final Boolean	value;
		private String			fieldName;

		@Override
		public boolean apply(T t) {
			Method m;
			Boolean b = null;
			try {
				m = t.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
				b = (Boolean) m.invoke(t);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return b != null && b == value;
		}
	}

	@Data
	private class IntegerComparisionPredicate implements Predicate<T> {
		private final Comparison	c;
		private final Integer		value;
		private String				fieldName;

		@Override
		public boolean apply(T t) {
			Method m;
			Integer i = null;
			try {
				m = t.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
				i = (Integer) m.invoke(t);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (i == null) {
				return false;
			}
			switch (c) {
				case EQUAL:
					return i == value;
				case GREATER_THAN:
					return i > value;
				case LESS_THAN:
					return i < value;
			}
			return false;
		}
	}

	@Data
	private class DoubleComparisionPredicate implements Predicate<T> {
		private final Comparison	c;
		private final Double		value;

		private String				fieldName;

		@Override
		public boolean apply(T t) {
			Method m;
			Double d = null;
			try {
				m = t.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
				d = (Double) m.invoke(t);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (d == null) {
				return false;
			}
			switch (c) {
				case EQUAL:
					return d == value;
				case GREATER_THAN:
					return d > value;
				case LESS_THAN:
					return d < value;
			}
			return false;
		}
	}

	@Data
	private class DateComparisionPredicate implements Predicate<T> {
		private final Comparison	c;
		private final Date			value;
		private String				fieldName;

		@Override
		public boolean apply(T t) {
			Method m;
			Date d = null;
			try {
				m = t.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
				d = (Date) m.invoke(t);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (d == null) {
				return false;
			}
			switch (c) {
				case EQUAL:
					return d.compareTo(value) == 0;
				case GREATER_THAN:
					return d.compareTo(value) > 0;
				case LESS_THAN:
					return d.compareTo(value) < 0;
			}
			return false;
		}
	}

}