import java.io.InputStream;

/**
 * Request Parser
 * @author Louis
 */
public class RequestParser {
    private final static int BUFFER_SIZE = 1024;

    /**
     * 解析请求
     * @param inputStream
     * @return Request
     */
    public Request parse(InputStream inputStream) {
        Request request = new Request();
        // 读取请求信息
        String requestMessage = readRequestMessage(inputStream);
        // 解析请求方式
        String type = parseType(requestMessage);
        request.setType(type);
        // 解析请求类型
        String uri = parseUri(requestMessage);
        request.setUri(uri);
        return request;
    }

    /**
     * 读取请求信息
     * @param input
     * @return
     */
    private String readRequestMessage(InputStream input) {
        StringBuffer requestMessage = new StringBuffer();
        int readLength = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            readLength = input.read(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            readLength = -1;
        }
        for(int i = 0; i < readLength; i++) {
            requestMessage.append((char) buffer[i]);
        }
        return requestMessage.toString();
    }

    /**
     * 解析请求方式
     * @param requestString
     * @return
     */
    private String parseType(String requestString) {
        int index = 0;
        index = requestString.indexOf(' ');
        if (index != -1) {
            return requestString.substring(0, index);
        }
        return null;
    }

    /**
     * 解析请求类型
     * @param requestString
     * @return
     */
    private String parseUri(String requestString) {
        System.out.println(requestString);
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1){
                String a=requestString.substring(index1 + 1,index2);
                if(a.equals("/")) return "/index.html";
                else return requestString.substring(index1 + 1,index2);
            }
        }
        return "index.html";
    }

}