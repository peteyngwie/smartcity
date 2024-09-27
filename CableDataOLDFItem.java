package com.tra.loginscreen;




public class CableDataOLDFItem {

    int     oldfId    ;
    String  oldfName  ;
    int     idcId     ;
    int     ports     ;
    String  tagId     ;
    String  connector ;
    String  floor     ;
    String  row       ;
    String  cabinet   ;
    String  box       ;

    public  CableDataOLDFItem(
            int     oldfId    ,
            String  oldfName  ,
            int     idcId     ,
            int     ports     ,
            String  tagId     ,
            String  connector ,
            String  floor     ,
            String  row       ,
            String  cabinet   ,
            String  box

    )
    {

        assert oldfId > -1  ;
        assert oldfName != null ;

        this.oldfId = oldfId ;
        this.oldfName = oldfName ;
        this.idcId = idcId ;
        this.ports = ports ;
        this.tagId = tagId ;
        this.connector  = connector  ;
        this.floor = floor ;
        this.row = row ;
        this.cabinet = cabinet ;
        this.box = box ;


        return ;
    }

    public int GetoldfId()        { return this.oldfId ;     }
    public String GetoldfName()   { return this.oldfName ;   }
    public int GetidcId()         { return this.idcId ;      }
    public int Getports()         { return this.ports ;      }
    public String GettagId()      { return this.tagId ;      }
    public String Getconnector()  { return this.connector ;  }
    public String Getfloor()      { return this.floor ;      }
    public String Getrow()        { return this.row ;        }
    public String Getcabinet()    { return this.cabinet ;    }
    public String Getbox()        { return this.box ;        }


}
