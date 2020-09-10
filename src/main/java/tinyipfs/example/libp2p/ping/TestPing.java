package tinyipfs.example.libp2p.ping;

import io.libp2p.core.Host;
import io.libp2p.core.dsl.HostBuilder;
import io.libp2p.core.multiformats.Multiaddr;
import io.libp2p.protocol.Ping;
import io.libp2p.protocol.PingController;

/**
 * description: TestPing <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2020/9/8 18:29 <br>
 */
public class TestPing {
    public static void main(String[] args) throws Exception{
        Host node = new HostBuilder().protocol(new Ping())
                .listen("/ip4/127.0.0.1/tcp/0").build();
        node.start().get();
        System.out.print("Node started and listening on ");
        System.out.println(node.listenAddresses());

        if (args.length > 0) {
            Multiaddr address = Multiaddr.fromString(args[0]);
            PingController pinger = new Ping().dial(
                    node,
                    address
            ).getController().get();

            System.out.println("Sending 5 ping messages to " + address.toString());
            for (int i = 1; i <= 5; ++i) {
                long latency = pinger.ping().get();
                System.out.println("Ping " + i + ", latency " + latency + "ms");
            }
            node.stop().get();
        }
    }
}
