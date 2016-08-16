package com.annanya.notes.classes;



public class Config {

    //Shared prefrence
    public static final String SHARED_PREF_NAME = "notes";
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    //sqlite db
    public static final String DBNAME="Db_Note";
    public static final String TBLNAME="Tbl_Note";
    public static final String PID="id";
    public static final String PTITLE="title";
    public static final String PTEXT="text";

    public static final String CREATEQUERY="Create table "+TBLNAME+"("+PID+" integer primary key, "+PTITLE+" text, "+PTEXT+" text)";
    public static final String SELECTQUERY="Select "+PTITLE+" from "+TBLNAME+"";
}
