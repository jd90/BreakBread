package com.example.borris.poppysplitter;

/**
 * Created by Borris on 6/14/2016.
 */
public class Object {

    double amount;
    int status; //pending, accepted, declined, questioned, settled, part-settled, removed (eg. when agree it was bogus)
    //use ENUMS
    String createdBy;
    String sentTo;
    String created;
    String desc;

    public Object(){
    }


    public Object(double amount, String desc, int status, String createdBy, String sentTo, String created){

        this.amount=amount;
        this.desc=desc;
        this.status=status;
        this.createdBy=createdBy;
        this.sentTo=sentTo;
        this.created=created;


    }

    public Object(double amount, String desc, int status, String createdBy, String sentTo){

        this.amount=amount;
        this.desc=desc;
        this.status=status;
        this.createdBy=createdBy;
        this.sentTo=sentTo;


    }


    public void changeStatus(int status){

        this.status=status;
    }


    public void paidOff(double cash){
        this.amount = amount-cash;
        if(amount==0){
            changeStatus(5);
        }
        if(cash>0&&amount>0){
            changeStatus(6);
        }
    }





}
