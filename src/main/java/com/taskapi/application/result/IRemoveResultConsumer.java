package com.taskapi.application.result;

public interface IRemoveResultConsumer {
    void accept();
    void reject(String reason);
}