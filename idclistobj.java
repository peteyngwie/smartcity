package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import android.util.Log;

public class idclistobj {

    private int Id  ;
    private String  Name ;

    idclistobj (
            int Id  ,
            String Name )
    {

        Log.d(TAG,"idclistobj()") ;

        this.Id = Id ;
        this.Name = Name  ;
    }

    public int GetId() { return this.Id ; }
    public String GetName(){ return this.Name  ;   }


}

