package tinyipfs.example.libp2p.chat;

import io.libp2p.core.P2PChannelHandler;
import io.libp2p.core.multistream.StrictProtocolBinding;
import org.jetbrains.annotations.NotNull;

/**
 * description: ChatBinding <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2020/9/10 13:45 <br>
 */
public class ChatBinding extends StrictProtocolBinding<ChatController> {
    public ChatBinding(@NotNull String announce, @NotNull P2PChannelHandler<? extends ChatController> protocol) {
        super(announce, protocol);
    }
}
