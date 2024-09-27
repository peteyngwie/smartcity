package com.tra.loginscreen;


import static com.tra.loginscreen.DataManipulation2.TAG;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CableDataRFIDItem {

    // rfid list item

    int     rfidsId   ;   // rfids Id
    String  tagId     ;   // tag Id
    int     oldfId       ;   // oldf Id
    String  cableName    ;   // cable name
    String  port         ;   // port
    String  status       ;   // status
    String  devicename   ;   // device name
    String  updateUser   ;   // update user
    String  updateTime   ;   // update Time
    String  jsonData     ;   // json data string


    public  CableDataRFIDItem (
            int     rfidsId      ,
            String  tagId        ,
            int     oldfId       ,
            String  cableName    ,
            String  port         ,
            String  status       ,
            String  devicename   ,
            String  updateUser   ,
            String  updateTime   ,
            String  jsonData
    )
    {

        assert rfidsId > -1  ;
        this.rfidsId    = rfidsId  ;
        this.tagId  = tagId ;
        this.oldfId  = oldfId ;
        this.cableName = cableName  ;
        this.port = port ;
        this.status = status ;
        this.devicename = devicename ;
        this.updateUser = updateUser  ;
        this.updateTime = updateTime  ;
        this.jsonData  = jsonData ;

        return ;
    }

    public int GetrfidsId()        { return this.rfidsId ;      }
    public String GettagId()       { return this.tagId ;        }
    public int GetoldfId()         { return this.oldfId ;       }
    public String GetcableName()   { return this.cableName ;    }
    public String Getport()        { return this.port ;         }
    public String Getstatus()      { return this.status ;       }
    public String Getdevicename()  { return this.devicename ;   }
    public String GetupdateUser()  { return this.updateUser ;   }
    public String GetupdateTime()  { return this.updateTime ;   }
    public String GetjsonData()    {

        Log.d("mmx","JsonData -----" + this.jsonData );


        // String jsonData_cabledetails = this.jsonData ;
        // cable 的細部資料

        return this.jsonData = jsonData ; }

}
