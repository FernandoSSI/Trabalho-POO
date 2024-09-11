package com.example.gestaodeeventos.db;

//execao personalizada para o banco de dados
public class DbException extends RuntimeException {
    public DbException(String msg) {
        super(msg);
    }
}