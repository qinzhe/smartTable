package com.bin.david.form.data;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.listener.OnColumnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huang on 2018/1/14.
 * 数组TableData
 */

public class ArrayTableData<T> extends TableData<T>{

    private  T[][] data;
    private List<Column<T>> arrayColumns;
    private OnItemClickListener onItemClickListener;

    public static<T> ArrayTableData<T> create(String tableName,String[] titleNames, T[][] data, IDrawFormat<T> drawFormat){
        List<Column<T>> columns = new ArrayList<>();
        for(int i = 0;i <data.length;i++){
            T[] dataArray = data[i];
            Column<T> column = new Column<>(titleNames == null?"":titleNames[i], null,drawFormat);
            column.setDatas(Arrays.asList(dataArray));
            columns.add(column);
        }
        ArrayList<T> arrayList = new ArrayList<>(Arrays.asList(data[0]));
        ArrayTableData<T> tableData =  new ArrayTableData<>(tableName,arrayList,columns);

        tableData.setData(data);
        return tableData;
    }

    public static<T> ArrayTableData<T> create(SmartTable<T> table,String tableName, T[][] data, IDrawFormat<T> drawFormat){
        table.getConfig().setShowColumnTitle(false);
        return create(tableName,null,data,drawFormat);
    }

    /**
     * 设置默认格式化
     * @param format
     */
    private void setFormat(IFormat<T> format){
        for(Column<T> column:arrayColumns){
            column.setFormat(format);
        }
    }
    private ArrayTableData(String tableName, List<T> t, List<Column<T>> columns) {
        super(tableName, t, new ArrayList<Column>(columns));
        this.arrayColumns = columns;
    }

    public List<Column<T>> getArrayColumns() {
        return arrayColumns;
    }



    public T[][] getData() {
        return data;
    }

    public void setData(T[][] data) {
        this.data = data;
    }




    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;

    }

    public void setOnItemClickListener(final OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        for(Column<T> column: arrayColumns){
            column.setOnColumnItemClickListener(new OnColumnItemClickListener<T>() {
                @Override
                public void onClick(Column<T> column, String value, T t, int position) {
                    if(onItemClickListener !=null){
                        int index = arrayColumns.indexOf(column);
                        onItemClickListener.onClick(column,value,t,index,position);
                    }
                }
            });
        }
    }

    public interface  OnItemClickListener<T>{
        void onClick(Column<T> column,String value, T t, int col,int row);
    }
}
