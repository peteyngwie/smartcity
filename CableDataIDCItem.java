package com.tra.loginscreen;

public class CableDataIDCItem {

    // idc list item

    int     idcId       ;   // idc  Id
    String  idcName     ;   // idc name

    public  CableDataIDCItem  (int  idcId  ,  String  idcName )
    {
        assert (idcId > -1 ) ;
        this.idcId = idcId ;
        this.idcName = idcName ;

        return ;
    }

    public int    GetidcId()            { return this.idcId ;     }
    public String GetidcName()          { assert this.idcName != null ;
                                          return this.idcName ;   }

}
