package com.example.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class SheetCountListener extends AnalysisEventListener<Object> {
    private int sheetCount = 0;
    private int lastSheetNo = -1;

    @Override
    public void invoke(Object data, AnalysisContext context) {
        int currentSheetNo = context.readSheetHolder().getSheetNo();
        if (currentSheetNo != lastSheetNo) {
            sheetCount++;
            lastSheetNo = currentSheetNo;
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    public int getSheetCount() {
        return sheetCount;
    }
}