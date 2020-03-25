package xml;

import java.util.List;

//Interface between Controller and the xml generator

public interface XMLGenerator {

    String prepareXML(List customerList, List categoryList, List supplierList,
                      String startDate, String endDate) throws Exception;

}
