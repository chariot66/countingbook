package com.example.attemptbookkeeping.tools;

import java.util.HashMap;

public class TranslateTool {

    HashMap E2C, C2E;

    // because of the db uses eng
    // handle problems when using CN language device
    public TranslateTool(){
        E2C = getHMEng2Ch();
        C2E = getHMCh2Eng();
    }

    public String getTransE(String str){
        return (String) E2C.get(str);
    }

    public String getTransC(String str){
        return (String) C2E.get(str);
    }

    public HashMap getHMEng2Ch(){
        HashMap<String, String> Sites = new HashMap<String, String>();
        Sites.put("income", "收入");
        Sites.put("spend", "支出");
        Sites.put("Food", "饮食");
        Sites.put("Transportation", "交通出行");
        Sites.put("Health", "健康医疗");
        Sites.put("Social Life", "社交生活");
        Sites.put("Entertainment", "娱乐");
        Sites.put("Living", "生活");
        Sites.put("ALL", "全部");
        return Sites;
    }

    public HashMap getHMCh2Eng(){
        HashMap<String, String> Sites = new HashMap<String, String>();
        Sites.put("收入", "income");
        Sites.put("支出", "spend");
        Sites.put("饮食", "Food");
        Sites.put("交通出行", "Transportation");
        Sites.put("健康医疗", "Health");
        Sites.put("社交生活", "Social Life");
        Sites.put("娱乐", "Entertainment");
        Sites.put("生活", "Living");
        Sites.put("全部", "ALL");
        return Sites;
    }
}
