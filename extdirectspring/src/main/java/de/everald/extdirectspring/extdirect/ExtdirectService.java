package de.everald.extdirectspring.extdirect;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import de.everald.extdirectspring.content.ContentService;
import de.everald.extdirectspring.extdirect.utils.Filtering;
import de.everald.extdirectspring.extdirect.utils.PropertyOrderingFactory;
import de.everald.extdirectspring.objects.GridObject;

@Service
public class ExtdirectService {

	@Autowired
	private ContentService	service;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<GridObject> getList(ExtDirectStoreReadRequest r) {
		List<GridObject> list = service.getList();
		if (!r.getFilters().isEmpty()) {
			list = new Filtering<GridObject>().filter(r.getFilters(), list, GridObject.class);
		}
		int totalSize = list.size();
		Ordering<GridObject> ordering = PropertyOrderingFactory.createOrderingFromSorters(r.getSorters());
		if (ordering != null) {
			list = ordering.sortedCopy(list);
		}
		if (r.getPage() != null && r.getLimit() != null) {
			int start = (r.getPage() - 1) * r.getLimit();
			int end = Math.min(totalSize, start + r.getLimit());
			list = Lists.newArrayList(list).subList(start, Math.min(totalSize, end));
		}
		return new ExtDirectStoreReadResult<GridObject>(totalSize, list, true);
	}

	@ExtDirectMethod
	public List<GridObject> getSimpleList() {
		return service.getList();
	}
}
