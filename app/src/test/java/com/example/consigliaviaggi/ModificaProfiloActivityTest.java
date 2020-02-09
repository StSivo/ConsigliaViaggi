package com.example.consigliaviaggi;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModificaProfiloActivityTest {

    @Test
    public void isInvalidUsernameExpectedFalse() {
        String username = "st.sivo";
        //This username is valid (IsInvalidUsername==false)
        assertEquals(ModificaProfiloActivity.IsInvalidUsername(username), false);
    }

    @Test
    public void isInvalidUsernameSpaceBetweenWordsExpectedTrue() {
        String username = "st sivo";
        //This username is invalid (IsInvalidUsername==true),
        //because there's a space in between two words
        assertEquals(ModificaProfiloActivity.IsInvalidUsername(username), true);
    }

    @Test
    public void isInvalidTextExpectedFalse() {
        String nome = "Diego Armando";
        //This nome is valid (IsInvalidText==false)
        assertEquals(ModificaProfiloActivity.IsInvalidText(nome), false);
    }

    @Test
    public void isInvalidTextDoubleSpaceBetweenNamesExpectedTrue() {
        String nome = "Diego  Armando";
        //This nome is invalid (IsInvalidText==true),
        //because it has two spaces in the middle
        assertEquals(ModificaProfiloActivity.IsInvalidText(nome), true);
    }
}