package tinyipfs.example.libp2p.chat;

import io.libp2p.core.P2PChannelHandler;
import org.jetbrains.annotations.NotNull;

/**
 * description: Chat <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2020/9/10 13:44 <br>
 */
public class Chat extends ChatBinding{
    public Chat() {
        super("/ipfs/chat/1.0.0", new ChatProtocol());
    }
}
