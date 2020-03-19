package xml;

import java.util.List;

public interface XMLGenerator {

    String prepareXML(List customerList, List categoryList, List supplierList,
                      String startDate, String endDate) throws Exception;

}
