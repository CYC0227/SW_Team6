package com.example.see;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Test
    public void 로그인_테스트_성공(){
        String test_id="1111";
        String test_pw="1234";

        String user_id = MainActivity.userid;
        String user_pw = MainActivity.userpw;

        assertEquals(test_id, user_id);
        assertEquals(test_pw,user_pw);
    }

//    @Test
//    void 로그인_테스트_실패(){
//        String test_id="2222";
//        String test_pw="4321";
//
//        String user_id = mainActivity.userid;
//        String user_pw = mainActivity.userpw;
//
//        assertEquals(test_id, user_id);
//        assertEquals(test_pw,user_pw);
//    }
}