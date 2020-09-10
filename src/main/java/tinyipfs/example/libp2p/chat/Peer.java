package tinyipfs.example.libp2p.chat;

import io.libp2p.core.PeerId;

/**
 * description: Peer <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2020/9/10 16:14 <br>
 */
public class Peer {
    private PeerId peerId;
    private ChatController chatController;
    public Peer(){

    }
    public Peer(PeerId peerId, ChatController chatController) {
        this.peerId = peerId;
        this.chatController = chatController;
    }

    public PeerId getPeerId() {
        return peerId;
    }

    public void setPeerId(PeerId peerId) {
        this.peerId = peerId;
    }

    public ChatController getChatController() {
        return chatController;
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }
}
