/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;

/**
 *
 * @author remi
 */
public class Message implements Serializable {

    private Code code;
    private String content;
    
    public Message(Code code, String content) {
        this.code = code;
        this.content = content;
    }

    /**
     * @return the code
     */
    public Code getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Code code) {
        this.code = code;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    public String toString() {
        return content;
    }
    
}
