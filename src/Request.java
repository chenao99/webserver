public class Request {
    /**
     * 请求方式： GET\POST\DELETE..
     */
    private String type;
    /**
     * 请求URI
     */
    private String uri;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}