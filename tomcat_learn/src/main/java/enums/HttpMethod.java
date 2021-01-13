package enums;


public enum HttpMethod {
    /**
     * get请求
     */
    GET("get"),
    /**
     * post请求
     */
    POST("post");




    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    HttpMethod(String value) {
        this.value = value;
    }
}
