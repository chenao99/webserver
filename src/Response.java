import java.io.*;

/**
 * Response
 *
 * @author Louis
 */
public class Response {
    private OutputStream output;
    private static final int BUFFER_SIZE=1024;

    public Response(OutputStream output) {
        this.output = output;
    }

    /**
     * 输出文本信息
     * @throws IOException
     */
    public void writeText(Request request) {
        byte[] bytes=new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            System.out.println(request.getUri());
            File file=new File(WebServer.WEB_ROOT,request.getUri());
            //WebServer.WEB_ROOT
            if(file.exists()){
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    StringBuffer sb = new StringBuffer();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\r\n");
                    }
                    StringBuffer result = new StringBuffer();
                    result.append("HTTP /1.1 200 ok \r\n");
                    result.append("Content-Type:text/html \r\n");
                    result.append("Content-Length:" + file.length() + "\r\n");
                    result.append("\r\n" + sb.toString());
                    output.write(result.toString().getBytes());
                    output.flush();
                    output.close();
            }else{
                //file not found
                String errorMessage="HTTP/1.1 404 File Not Found\r\n"+
                        "Content-Type:text/html\r\n"+
                        "Content-Length:23\r\n"+
                        "\r\n"+
                        "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}