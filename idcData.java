package com.tra.loginscreen;

public class idcData {

      int idcId ;
      String idcName ;

    public idcData(int id, String idcName) {
        assert id > -1 ;
        assert idcName != null ;

        this.idcId = id ;
        this.idcName = idcName ;

    }

    public int Getid() {
        return this.idcId ;
    }
    public String GetidcName() {
        return this.idcName ;
    }

    public void SetidcId(int id ) {
        this.idcId = id ;
    }
    public void SetidcName(String idcname) {
        this.idcName = idcname ;
    }

}
