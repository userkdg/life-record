package pri.jarod.javaweb.util;

import pri.jarod.javaweb.common.Pages;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagesUtils {

    //分页大小
    private final static Integer SIZE = 5;
    //默认页数  0开头
    private final static Integer PAGE = 0;
    //默认排序字段
    private final static String ID = "id";

    public static Pageable createPageRequest(Pages pages) {
        Sort sort=new Sort(null!=pages.getDirection()&&!"".equals(pages.getDirection())&&pages.getDirection().equals("desc")? Sort.Direction.DESC: Sort.Direction.ASC,
                StringUtils.isEmpty(pages.getSortColumn())?ID:pages.getSortColumn());
        Pageable pageable = PageRequest.of(pages.getPage()<=0?PAGE:pages.getPage(),
                pages.getSize()<=0?SIZE:pages.getSize(),sort);
        return pageable;
    }

}
