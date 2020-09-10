package tinyipfs.example.libp2p.chat;

import io.libp2p.core.multiformats.Multiaddr;
import io.libp2p.protocol.Ping;
import io.libp2p.protocol.PingController;

import java.util.Scanner;

/**
 * description: App <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2020/9/9 16:19 <br>
 */
public class App {
    private static final String MASTER ="master";
    private static int count = 5;
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入命令（master or slave）");
        String cmd = scanner.nextLine();
        ChatNode node = new ChatNode();
        node.start();
        if(!MASTER.equals(cmd)){
            System.out.println("请输入 master 地址");
            String ip = scanner.nextLine();
            Multiaddr address = Multiaddr.fromString(ip);
            ChatController chatter = new Chat().dial(
                    node.getNode(),
                    address
            ).getController().get();

            System.out.println("Sending 5 ping messages to " + address.toString());
            for (int i = 1; i <= count; ++i) {
                String latency = chatter.chat("hello").get();
                System.out.println("chat " + i + ", chat " + latency + "ms");
            }
        }


    }
}
