package com.datastorage;

import java.sql.SQLException;

public class CourseDAO {

    public static void main(String[] args) {
        
        try{
            SQLServerDatabase.getDatabase().connect();
             System.out.println("connected!");
         } catch (SQLException e) {
             e.printStackTrace();
         }
         
    }
    
}
