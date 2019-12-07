import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*文件传输Server端*/
class FileTransferServer extends ServerSocket {
    private static final int SERVER_PORT = 8899; // 服务端端口
    private static final String FILE_PATH = "D:\\Java\\FileTransfer\\Server";

    /*构造函数*/
    FileTransferServer() throws Exception {
        super(SERVER_PORT);
        System.out.println("---------服务器启动------------");
    }

    /*使用线程处理每个客户端传输的文件*/
    void load() throws Exception {
        while (true) {
            /*接收其他Socket的连接请求，accept方法是阻塞的*/
            Socket socket = this.accept();
            /*每接收到一个Socket就建立一个新的线程来处理它*/
            new Thread(new Task(socket)).start();

            System.out.println("----------接收到文件-------------");
        }
    }

    /*处理客户端传输过来的文件线程类*/
    class Task implements Runnable {
        private Socket socket;
        private DataInputStream input;
        private FileOutputStream output;

        Task(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                input = new DataInputStream(socket.getInputStream());

                /*文件名和长度*/
                String fileName = input.readUTF();
                long fileLength = input.readLong();
                File directory = new File(FILE_PATH);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
                output = new FileOutputStream(file);

                /* 开始接收文件*/
                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = input.read(bytes, 0, bytes.length)) != -1) {
                    output.write(bytes, 0, length);
                    output.flush();
                }
                System.out.println("--------文件接收成功----------");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                    socket.close();
                }
                catch (Exception ignored) {
                }
            }
        }
    }
}