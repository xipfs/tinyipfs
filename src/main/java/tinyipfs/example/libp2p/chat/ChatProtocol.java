package tinyipfs.example.libp2p.chat;

import io.libp2p.core.Stream;
import io.libp2p.protocol.ProtocolHandler;
import io.libp2p.protocol.ProtocolMessageHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

/**
 * description: ChatProtocol <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2020/9/10 11:06 <br>
 */
public class ChatProtocol extends ProtocolHandler<ChatController> {
    private static final Logger log = Logger.getLogger(ChatProtocol.class.getName());
    private CompletableFuture<ChatController> completableFuture = new CompletableFuture<>();
    private ChatHandler chatHandler = new ChatHandler(completableFuture);
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    @NotNull
    @Override
    public CompletableFuture<? extends ChatController> onStartInitiator(@NotNull Stream stream) {
        log.info("onStartInitiator remote peerId :"+stream.remotePeerId().toString());
        stream.pushHandler(chatHandler);
        return completableFuture;
    }

    @NotNull
    @Override
    public CompletableFuture<? extends ChatController> onStartResponder(@NotNull Stream stream) {
        log.info("onStartResponder remote peerId :"+stream.remotePeerId().toString());
        stream.pushHandler(chatHandler);
        return completableFuture;
    }

    public class ChatHandler implements ProtocolMessageHandler<ByteBuf>,ChatController{
        CompletableFuture<ChatController> completableFuture;
        private Stream stream;
        public ChatHandler(CompletableFuture<ChatController> completableFuture){
            this.completableFuture = completableFuture;
        }
        @Override
        public void onMessage(@NotNull Stream stream, ByteBuf msg) {
            String msgStr = msg.toString(Charset.defaultCharset());
            log.info("ChatHandler onMessage, remote peerId :"+stream.remotePeerId().toString()+" , msg :"+msgStr);
        }
        @Override
        public void onException(@Nullable Throwable throwable) {
            log.info("ChatHandler onException");
        }
        @Override
        public void onClosed(@NotNull Stream stream) {
            log.info("ChatHandler onClosed");
        }
        @Override
        public void onActivated(@NotNull Stream stream) {
            log.info("ChatHandler onActivated");
            this.stream = stream;
            completableFuture.complete(this);
        }
        @Override
        public void fireMessage(@NotNull Stream stream, @NotNull Object o) {
            log.info("ChatHandler fireMessage "+o.getClass().getName());
            onMessage(stream,(ByteBuf) o);
        }

        @Override
        public CompletableFuture<String> chat(String msg) {
            log.info("send msg :"+msg);
            CompletableFuture ret = new CompletableFuture<String>();
            ByteBuf data = Unpooled.wrappedBuffer(msg.getBytes());
            stream.writeAndFlush(data);
            ret.complete("chat ok");
            return ret;
        }
    }

}
