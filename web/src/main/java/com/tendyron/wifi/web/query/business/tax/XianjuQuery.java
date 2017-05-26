package com.tendyron.wifi.web.query.business.tax;

import com.tendyron.wifi.web.query.BaseQuery;
import com.tendyron.wifi.web.utils.StringTools;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Neo on 2017/5/11.
 */
public class XianjuQuery extends StatisticsQuery {

    private String agencyIdsStr; //xx,xx,xx

    public String[] getAgencyIds() {
        return StringTools.isEmpty(agencyIdsStr) ? new String[0] : agencyIdsStr.split(",");
    }

    public String getAgencyIdsStr() {
        return agencyIdsStr;
    }

    public void setAgencyIdsStr(String agencyIdsStr) {
        this.agencyIdsStr = agencyIdsStr;
    }
}
