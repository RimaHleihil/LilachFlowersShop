package il.cshaifasweng.OCSFMediatorExample.entities;


import java.io.Serializable;

// this class is intended for sending messages between the server and the client.
public class Message implements Serializable {


    public static final int getAllItems = 1;
    public static final int recieveAllItems= 2;
    public static final int updateItem= 3;
    public static final int addProduct=4;
    public static final int deleteProduct=5;
    public static final int deleteProductResponse=6;
    public static final int addProductResponse=7;
    public static final int updateAllItems=8;
    public static final int LoggingIn_S=9;
    public static final int LoggingIn_C=10;
    public static final int SignUp_S=11;
    public static final int SignUp_C=12;
    public static final int Add2Basket_S=13;
    public static final int Add2Basket_C=14;
    public static final int Complaint_S=15;
    public static final int Complaint_C=16;
    public static final int ResComplaint_S=17;
    public static final int ResComplaint_C=18;
    public static final int ItemListForC_S=19;
    public static final int ItemListForC_C=20;
    public static final int SaveOrder_S=21;
    public static final int SaveOrder_C=22;
    public static final int ManagerSignUp_S=23;
    public static final int ManagerSignUp_C=24;
    public static final int ManagerLogIn_S=25;
    public static final int ManagerLogIn_C=26;



    //////////////
    private final int msg;
    private String info_Msg;
    private Object object;


    public Message(int msg,String MsgInfo) {
        this.msg = msg;
        this.object=null;
        this.info_Msg=MsgInfo;
    }

    public Message(int msg) {
        this.msg = msg;
        this.info_Msg="-11";
        this.object=null;

    }
    public Message(int msg, Object object) {
        this.msg = msg;
        this.info_Msg="-11";
        this.object = object;

    }
    public Message(int msg, Object object,String MsgInfo) {
        this.msg = msg;
        this.info_Msg=MsgInfo;
        this.object = object;

    }

    public int getMsg() {
        return this.msg;
    }
    //public void setMsg(int msg1){msg=msg1;}

    public Object getObject() {
        return object;
    }
    public void setObject(Object obj) {
        this.object=obj;
    }
    public String getInfo_Msg() {
        return this.info_Msg;

    }
    public void setInfo_Msg(String info_Msg) {
        this.info_Msg = info_Msg;
    }


}
