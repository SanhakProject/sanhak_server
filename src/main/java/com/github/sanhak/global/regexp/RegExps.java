package com.github.sanhak.global.regexp;

public class RegExps {

    public static final String NAME_EXPRESSION = "^[가-힣a-zA-Z()\\s]{2,}$";
    public static final String NAME_MESSAGE = "이름은 한글, 영문, 괄호, 공백을 포함해 2자 이상 입력해 주세요.";

    public static final String RegPHONE_NUMBER = "^\\d{3}-\\d{4}-\\d{4}$";
    public static final String PHONE_NUMBER_MESSAGE = "올바른 전화번호 형식이 아닙니다. (예: 010-1234-5678)";

}
