package com.example.dabbaapp.Model;

public class ModelOrder {
    String DabbaWala,Dabba_Size,Food_Type,Quantity,User_Address,User_Name,User_Phone,uid;

    public ModelOrder() {
    }

    public ModelOrder(String dabbaWala, String dabba_Size,
                      String food_Type, String quantity, String user_Address,
                      String user_Name, String user_Phone, String uid) {
        DabbaWala = dabbaWala;
        Dabba_Size = dabba_Size;
        Food_Type = food_Type;
        Quantity = quantity;
        User_Address = user_Address;
        User_Name = user_Name;
        User_Phone = user_Phone;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDabbaWala() {
        return DabbaWala;
    }

    public void setDabbaWala(String dabbaWala) {
        DabbaWala = dabbaWala;
    }

    public String getDabba_Size() {
        return Dabba_Size;
    }

    public void setDabba_Size(String dabba_Size) {
        Dabba_Size = dabba_Size;
    }

    public String getFood_Type() {
        return Food_Type;
    }

    public void setFood_Type(String food_Type) {
        Food_Type = food_Type;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }



    public String getUser_Address() {
        return User_Address;
    }

    public void setUser_Address(String user_Address) {
        User_Address = user_Address;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_Phone() {
        return User_Phone;
    }

    public void setUser_Phone(String user_Phone) {
        User_Phone = user_Phone;
    }
}
