package tinyipfs.example.libp2p.chat;

import io.libp2p.core.Stream;
import io.libp2p.protocol.ProtocolHandler;
import io.libp2p.protocol.ProtocolMessageHandler;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;
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
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    @NotNull
    @Override
    public CompletableFuture<? extends ChatController> onStartInitiator(@NotNull Stream stream) {
        log.info("remote peerId :"+stream.remotePeerId().toString());
        ChatInitiator handler = new ChatInitiator();
        stream.pushHandler(handler);
        return handler.activeFuture;
    }

    @NotNull
    @Override
    public CompletableFuture<? extends ChatController> onStartResponder(@NotNull Stream stream) {
        ChatResponder handler = new ChatResponder();
        stream.pushHandler(handler);
        return CompletableFuture.completedFuture(handler);
    }
    public class ChatResponder implements ProtocolMessageHandler<ByteBuf>, ChatController{

        @Override
        public CompletableFuture<String> chat(String msg) {
            throw new ChatException();
        }

        @Override
        public void fireMessage(@NotNull Stream stream, @NotNull Object o) {

        }

        @Override
        public void onActivated(@NotNull Stream stream) {

        }

        @Override
        public void onClosed(@NotNull Stream stream) {

        }

        @Override
        public void onException(@Nullable Throwable throwable) {

        }

        @Override
        public void onMessage(@NotNull Stream stream, ByteBuf msg) {
            stream.writeAndFlush(msg);
        }
    }
    public class ChatInitiator implements ChatController, ProtocolMessageHandler<ByteBuf> {
        CompletableFuture activeFuture = new CompletableFuture<ChatController>();
        Map<String,Long> requests = Collections.synchronizedMap(new HashMap<>());
        Stream stream;
        boolean closed = false;

        @Override
        public void onActivated(@NotNull Stream stream) {
            this.stream = stream;
            activeFuture.complete(this);
        }
        @Override
        public void onMessage(@NotNull Stream stream, ByteBuf msg) {
            log.info("ChatInitiator onMessage");
        }
        @Override
        public void onClosed(@NotNull Stream stream) {
            closed = true;
            scheduler.shutdownNow();
        }

        @Override
        public CompletableFuture<String> chat(String msg) {
            if(closed) {
                throw new ChatException();
            }
            CompletableFuture<String> ret = CompletableFuture.supplyAsync(new Supplier<String>() {
                @Override
                public String get() {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        throw new IllegalStateException(e);
                    }
                    return "Result of the asynchronous computation";
                }
            });
            stream.writeAndFlush("hello");
            return ret;
        }

        @Override
        public void fireMessage(@NotNull Stream stream, @NotNull Object o) {

        }

        @Override
        public void onException(@Nullable Throwable throwable) {

        }
    }
}
