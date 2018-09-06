package com.liupeiqing.spring.cloud.service.impl;


import com.liupeiqing.spring.cloud.cache.AdminCacheKey;
import com.liupeiqing.spring.cloud.domain.Dept;
import com.liupeiqing.spring.cloud.domain.QDept;
import com.liupeiqing.spring.cloud.jpa.JPAFactoryImpl;
import com.liupeiqing.spring.cloud.repository.DeptRepository;
import com.liupeiqing.spring.cloud.service.DeptService;
import com.liupeiqing.spring.cloud.tree.DeptTree;
import com.liupeiqing.spring.cloud.tree.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeptServiceImpl extends JPAFactoryImpl implements DeptService {

	@Autowired
	private DeptRepository deptRepository;
	
	/**
	 * 当我们在调用一个缓存方法时会把该方法参数和返回结果作为一个键值对存放在缓存中，
	 * 等到下次利用同样的参数来调用该方法时将不再执行该方法，而是直接从缓存中获取结果进行返回。
	 * 所以在使用Spring Cache的时候我们要保证我们缓存的方法对于相同的方法参数要有相同的返回结果.
	 * 其核心主要是@Cacheable和@CacheEvict。
	 * 使用@Cacheable标记的方法在执行后Spring Cache将缓存其返回结果，
	 * 而使用@CacheEvict标记的方法会在方法执行前或者执行后移除Spring Cache中的某些元素
	 * 
	 * value属性是必须指定的，其表示当前方法的返回值是会被缓存在哪个Cache上的，对应Cache的名称。
	 *  key属性是用来指定Spring缓存方法的返回结果时对应的key的
	 *  
	 *   有的时候我们可能并不希望缓存一个方法所有的返回结果。
	 *   通过condition属性可以实现这一功能。condition属性默认为空，表示将缓存所有的调用情形。
	 *   其值是通过SpringEL表达式来指定的，当为true时表示进行缓存处理；当为false时表示不进行缓存处理，
	 *   即每次调用该方法时该方法都会执行一次。如下示例表示只有当user的id为偶数时才会进行缓存。
	 *    @Cacheable(value={"users"}, key="#user.id", condition="#user.id%2==0")
	 *    
	 *    在支持Spring Cache的环境下，对于使用@Cacheable标注的方法，
	 *    Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，
	 *    如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
	 *    @CachePut也可以声明一个方法支持缓存功能。
	 *    与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，
	 *    而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
	 */
	@Override
	@Cacheable(value = AdminCacheKey.DEPT_INFO, key = "'dept_list'")
	public List<DeptTree> getDeptTreeList() {
		QDept dept = QDept.dept;
		List<Dept> list = this.queryFactory.selectFrom(dept).where(dept.statu.eq(0)).fetch();
		if (null == list || list.size() == 0) return new ArrayList<DeptTree>();

		return getDeptTree(list, 0);
	}

	private List<DeptTree> getDeptTree(List<Dept> list, int rootId) {
		List<DeptTree> treeList = new ArrayList<DeptTree>();
		for (Dept dept : list) {
			// 排除父节点和自己节点相同的数据
			if (dept.getPid().intValue() == dept.getId().intValue()) continue;
			DeptTree node = new DeptTree();
			node.setId(dept.getId() + "");
			node.setPid(dept.getPid() + "");
			node.setName(dept.getDeptName());
			treeList.add(node);
		}
		return TreeUtil.build(treeList, "0");
	}

}
