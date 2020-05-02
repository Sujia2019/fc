package com.easyarch.api.chat;

import com.easyarch.model.chat.FriendRequest;

public abstract class FriendService {


    /**
     *
     * @param fq 好友请求
     * @return 是否发送成功 (因为有人是拒绝好友申请的设置)
     */
    public abstract boolean sendFriendRequest(FriendRequest fq);

    /**
     *
     * @param fq 好友请求
     * @return 是否发送成功
     *
     * 客户端拿到request后决定是否要接受/拒绝，修改这其中的值，
     * 可以将拒绝信息返回，也可以不给那个用户看
     *
     * 也有可能玩家一直不处理，这个是要入库的，在前端会一直提示存在
     *
     */
    public abstract boolean sendFriendResponse(FriendRequest fq);



}
