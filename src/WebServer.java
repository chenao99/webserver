import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Web Server
 * @author Louis
 */
public class WebServer {

    /**
     * 服务器启动端口
     */
    private int port = 8081;
    /**
     * 服务端Socket
     */
    private ServerSocket serverSocket;
    public static final String WEB_ROOT = System.getProperty("user.dir");


    public WebServer() {
        init();
    }

    /**
     * 初始化服务端Socket
     */
    private void init() {
        try {
            // 创建服务端Socket
            serverSocket = new ServerSocket(port);
            System.out.println("服务端已启动，等待客户端连接..");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动服务器，监听并处理客户请求
     *
     * @throws IOException
     */


    public void start() throws IOException {
        while (true) {
            // 侦听并接受客户请求
            if (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                // 新启线程，处理客户请求
//            t=new ThreadSafe(socket);
//            t.start();
                new Thread() {
                    @Override
                    public void run() {
                        service(socket);
                    }
                }.start();
            }
        }
    }

    public void stopsocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }

    /**
     * 处理客户请求
     *
     * @param socket
     */
    private void service(Socket socket) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            // 读取请求信息内容
            Request request = new RequestParser().parse(inputStream);
            Response response = new Response(outputStream);
            service(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("接收到客户端连接, " + socket.getInetAddress() + ":" + socket.getPort());
    }


    private void service(Request request, Response response) {
        response.writeText(request);
    }
}

