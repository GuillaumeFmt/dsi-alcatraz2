package core;

import java.io.Serializable;

public class MessageBox<T> implements Serializable {
    T t;
    public T getContent(){
        return t;
    };
    public void setContent(T t){
        this.t = t;
    };
}