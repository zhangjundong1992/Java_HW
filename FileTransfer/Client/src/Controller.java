import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

/*文件传输Client端*/
public class Controller implements ClickDelegate {
    /*内部类，用于文件传输*/
    private class ClientSocket extends Socket {
        /*构造函数,与服务器建立连*/
        private ClientSocket(String ip, int port) throws Exception {
            super(ip, port);
            System.out.println("---------成功连接服务端-------------");
        }

        /*发送文件*/
        private int sendFile(String filePath) throws Exception {
            /*创建文件*/
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("---------文件不存在-------------");
                return -1;
            }

            FileInputStream input = new FileInputStream(file);
            DataOutputStream output = new DataOutputStream(this.getOutputStream());

            /*文件名和长度*/
            output.writeUTF(file.getName());
            output.flush();
            output.writeLong(file.length());
            output.flush();

            /*开始传输文件*/
            System.out.println("---------开始传输文件--------------");
            byte[] bytes = new byte[1024];
            int length = 0;
            long progress = 0;
            while ((length = input.read(bytes, 0, bytes.length)) != -1) {
                output.write(bytes, 0, length);
                output.flush();
                progress += length;
                System.out.print("| " + (100 * progress / file.length()) + "% |");
            }
            System.out.println();
            System.out.println("-----------文件传输成功-------------");

            /*关闭流和客户端socket*/
            input.close();
            output.close();
            this.close();

            return 0;
        }
    }

    @Override
    public int OnClick(String path, String file) {
        try {

            String[] res = path.split("：");
            String ip = res[0];
            int port = Integer.parseInt(res[1]);
            ClientSocket client = new ClientSocket(res[0], Integer.parseInt(res[1]));
            return client.sendFile(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
