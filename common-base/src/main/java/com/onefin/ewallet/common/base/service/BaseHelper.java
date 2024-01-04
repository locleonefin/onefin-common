package com.onefin.ewallet.common.base.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.naming.Name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onefin.ewallet.common.base.constants.OneFinConstants;

public class BaseHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseHelper.class);

	/**
	 * @return
	 * @throws Exception
	 */
	public Object createModelStructure(Object emptyModel) throws Exception {

		List<Map<String, Object>> nodes = new ArrayList<>();

		Map<String, Object> firstNode = new HashMap<>();
		firstNode.put(OneFinConstants.NODE, emptyModel);
		firstNode.put(OneFinConstants.PARENT_PATH, emptyModel.getClass().getSimpleName());
		nodes.add(firstNode);

		while (nodes.size() > 0) {
			Map<String, Object> loopNode = nodes.get(0);
			nodes.remove(0);

			Object object = loopNode.get(OneFinConstants.NODE);
			String parentPath = (String) loopNode.get(OneFinConstants.PARENT_PATH);

			nodes.addAll(0, doCreateFields(object.getClass().getDeclaredFields(),
					object.getClass().getDeclaredMethods(), object, parentPath));

			// Filed in Supper Class
			Class<?> supperClazz = object.getClass().getSuperclass();
			while (supperClazz != null) {
				if (!"ParentPath".equals(supperClazz.getSimpleName())) {
					nodes.addAll(0, doCreateFields(supperClazz.getDeclaredFields(), supperClazz.getDeclaredMethods(),
							object, parentPath));
					supperClazz = supperClazz.getSuperclass();
				} else {
					break;
				}
			}
		}
		return emptyModel;
	}

	/**
	 * create empty field
	 *
	 * @param objects
	 * @param fields
	 * @param methods
	 * @param object
	 * @throws Exception
	 */
	protected List<Map<String, Object>> doCreateFields(Field[] fields, Method[] methods, Object object,
	                                                   String parentPath) throws Exception {
		List<Map<String, Object>> nodes = new ArrayList<>();
		for (Field field : fields) {
			Class<?> clazzField = field.getType();
			if (isValidDataTypeInModel(clazzField) || field.getName().equals("subsidiaries")) {
				if (OneFinConstants.UID.equals(field.getName())) {
					field.setAccessible(true);
					field.set(object, buildId());
				}
				continue;
			} else if (List.class.isAssignableFrom(clazzField)) {
				// Handle List
				List<Object> list = new ArrayList<>();
				ParameterizedType listType = (ParameterizedType) field.getGenericType();
				Class<?> member = (Class<?>) listType.getActualTypeArguments()[0];
				Object memberObject = member.newInstance();
				//list.add(memberObject);
				Method setMethod = getMethodByName(OneFinConstants.SET + field.getName(), methods);
				setMethod.invoke(object, list);
				Map<String, Object> node = new HashMap<>();
				node.put(OneFinConstants.NODE, memberObject);
				node.put(OneFinConstants.PARENT_PATH, parentPath + OneFinConstants.UNDERSCORE + field.getName());
				nodes.add(node);
				setParentPathField(memberObject, parentPath + OneFinConstants.UNDERSCORE + field.getName());
			} else if (Object.class.isAssignableFrom(clazzField)) {
				// handle object
				Method setMethod = getMethodByName(OneFinConstants.SET + field.getName(), methods);
				Object fieldObject = null;
				try {
					fieldObject = clazzField.newInstance();
				} catch (Exception e) {
					continue;
				}
				setMethod.invoke(object, fieldObject);

				Map<String, Object> node = new HashMap<>();
				node.put(OneFinConstants.NODE, fieldObject);
				node.put(OneFinConstants.PARENT_PATH, parentPath + OneFinConstants.UNDERSCORE + field.getName());
				nodes.add(node);

				setParentPathField(fieldObject, parentPath + OneFinConstants.UNDERSCORE + field.getName());
			}
		}
		return nodes;
	}

	/**
	 * @param clazz
	 * @return
	 */
	protected boolean isValidDataTypeInModel(Class<?> clazz) {
		return String.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz)
				|| Double.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz)
				|| Float.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz)
				|| Long.class.isAssignableFrom(clazz) || Name.class.isAssignableFrom(clazz)
				|| byte[].class.isAssignableFrom(clazz);
	}

	/**
	 * @return
	 */
	public String buildId() {
		UUID id = UUID.randomUUID();
		return id.toString();
	}

	/**
	 * get method by method by name
	 *
	 * @param name
	 * @param methods
	 * @return
	 */
	public Method getMethodByName(String name, Method[] methods) {
		Stream<Method> stream = Stream.of(methods);
		return stream.filter(method -> name.equalsIgnoreCase(method.getName())).findFirst().orElse(null);
	}

	/**
	 * set parent path to object
	 *
	 * @param nodez
	 * @param value
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	protected void setParentPathField(Object node, String value)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> supperClazz = node.getClass().getSuperclass();
		while (supperClazz != null) {
			if ("ParentPath".equals(supperClazz.getSimpleName())) {
				Method method = getMethodByName(OneFinConstants.SET + OneFinConstants.PARENT_PATH_FIELD_NAME,
						supperClazz.getDeclaredMethods());
				method.invoke(node, value);
			}
			supperClazz = supperClazz.getSuperclass();
		}
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
