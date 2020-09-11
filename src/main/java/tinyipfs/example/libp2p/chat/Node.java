package tinyipfs.example.libp2p.chat;

import io.libp2p.core.PeerInfo;

/**
 * description: Peer <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2020/9/10 16:14 <br>
 */
public class Node {
    private PeerInfo peerInfo;
    private ChatController chatController;
    public Node(){

    }
    public Node(PeerInfo peerInfo, ChatController chatController) {
        this.peerInfo = peerInfo;
        this.chatController = chatController;
    }

    public PeerInfo getPeerInfo() {
        return peerInfo;
    }

    public void setPeerInfo(PeerInfo peerInfo) {
        this.peerInfo = peerInfo;
    }

    public ChatController getChatController() {
        return chatController;
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }
}
