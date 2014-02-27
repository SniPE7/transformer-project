package com.sinopec.siam.am.idp.authn.util;
/**
 * 安全验证邮件 URL Token
 * @author bluesky
 *
 */
public class Token {
        private String id;
        private String date;
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getDate() {
            return date;
        }
        
        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "Token [id=" + id + ", date=" + date + "]";
        }
    }