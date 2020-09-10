package tinyipfs.example.libp2p.chat;


import io.libp2p.core.Host;
import io.libp2p.core.dsl.HostBuilder;
import io.libp2p.discovery.MDnsDiscovery;
import kotlin.Unit;
import tinyipfs.util.IpUtil;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;


/**
 * description: ChatNode <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2020/9/8 18:43 <br>
 */
public class ChatNode {
    private static final Logger log = Logger.getLogger(ChatNode.class.getName());
    private Host node;
    private InetAddress privateAddress;
    private Set<Peer> peers = new HashSet<>();
    private MDnsDiscovery peerFinder;
    private final String serviceTag = "_ipfs-discovery._udp";
    private final String serviceTagLocal = serviceTag + ".local.";
    private int queryInterval = 120;

    public ChatNode() {
        privateAddress = IpUtil.getLocalAddress();
        node = new HostBuilder().protocol(new Chat()).listen("/ip4/" + privateAddress.getHostAddress() + "/tcp/0").build();
    }

    public void start() {
        try {
            node.start().get();
            log.info("Node started and listening on ");
            log.info(node.listenAddresses().toString());
            peerFinder = new MDnsDiscovery(node, serviceTagLocal, queryInterval, privateAddress);
            System.out.println(peerFinder.getNewPeerFoundListeners().size());
            peerFinder.getNewPeerFoundListeners().add(peerInfo -> {
                System.out.println("find peer : " + peerInfo.getPeerId().toString());
                Unit u = Unit.INSTANCE;
                return u;
            });
            peerFinder.start();
            log.info("Peer finder started ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) {
        for (Peer peer : peers) {
            peer.getChatController().chat(msg);
        }
    }

    public void stop() {
        node.stop();
        peerFinder.stop();
    }

    public Host getNode() {
        return node;
    }
}
