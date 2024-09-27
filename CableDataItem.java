package com.tra.loginscreen;


public class CableDataItem {


    String  ID       ;
    String  Display  ;
    Boolean Json     ;
    Boolean Required ;
    Boolean Editable ;
    Boolean Visible  ;


    public  CableDataItem(
            String           id,          // tag id , cable name , column1 , column2 , floor , ... etc.
            String           display ,    // Show each item's name
            Boolean          json ,       // json data string
            Boolean          required ,
            Boolean          editable ,
            Boolean          visible
                            )
    {

        assert id != null ;
        assert display != null ;

        this.ID = id ;
        this.Display = display ;
        this.Json = json ;
        this.Required = required ;
        this.Editable = editable ;
        this.Visible  = visible  ;

        return ;
    }

    public String GetId()        { return this.ID ;       }
    public String GetDisplay()   { return this.Display ;  }
    public Boolean GetJson()     { return this.Json ;     }
    public Boolean GetRequired() { return this.Required ; }
    public Boolean GetEditable() { return this.Editable ; }
    public Boolean GetVisible()  { return this.Visible ;  }

}
