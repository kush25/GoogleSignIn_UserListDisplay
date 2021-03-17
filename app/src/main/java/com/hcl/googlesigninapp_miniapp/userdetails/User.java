package com.hcl.googlesigninapp_miniapp.userdetails;

import com.hcl.googlesigninapp_miniapp.R;

import java.util.Arrays;

public class User {


    private String name;
    private String username;
    private String email;

    public Address address;


    public class Address {

        public String street;
        public String suite;
        public String city;
        public String zipcode;


        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", suite='" + suite + '\'' +
                    ", city='" + city + '\'' +
                    ", zipcode='" + zipcode + '\'' +
                    '}';
        }

    }




    public String phone;

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                '}';
    }


    int[] users_img_url ={

                    R.drawable.user1,
                    R.drawable.user2,
                    R.drawable.user3,
                    R.drawable.user4,
                    R.drawable.user5,
                    R.drawable.user6,
                    R.drawable.user7,
                    R.drawable.user8,
                    R.drawable.user9,
                    R.drawable.user10


    };

    public int[] getUsers_img_url() {
        return users_img_url;
    }


}
