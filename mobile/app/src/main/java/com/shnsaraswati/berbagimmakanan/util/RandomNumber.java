package com.shnsaraswati.berbagimmakanan.util;

import java.util.Random;

public class RandomNumber {

    public String generateOTP(){
        int OTP = new Random().nextInt((9999 - 1000) + 1) + 1000;
        return String.valueOf(OTP);
    }
}
