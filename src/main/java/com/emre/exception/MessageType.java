package com.emre.exception;

import lombok.Getter;

@Getter
public enum MessageType {
    NO_RECORD_EXIST("1004", "Kayıt bulunamadı"),
    GENERAL_EXCEPTION("9999","Genel bir hata oluştu."),
    USERNAME_NOT_FOUND("1006","Username bulunamadı."),
    USERNAME_OR_PASSWORD_INVALID("1007","Kullanıcı adı veya şifre hatalı."),
    REFRESH_TOKEN_NOT_FOUND("1008","Refresh token bulunamadı."),
    REFRESH_TOKEN_IS_EXPIRED("1009","Refresh token'ın süresi bitmiştir."),
    BOOK_NOT_AVAILABLE("1013","Kitap mevcut değil"),
    TOKEN_IS_EXPIRED("1005","Token'ın süresi bitmiştir");

    private String code;

    private String message;

    MessageType(String code, String message){
        this.code=code;
        this.message=message;
    }
}
