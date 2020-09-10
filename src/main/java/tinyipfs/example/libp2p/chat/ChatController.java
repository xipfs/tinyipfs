package tinyipfs.example.libp2p.chat;
import java.util.concurrent.CompletableFuture;

/**
 * description: ChatController <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2020/9/8 18:43 <br>
 */
public interface ChatController{

    /**
     * chat
     *
     * @param msg
     * @return
     */
    CompletableFuture<String> chat(String msg);
}
