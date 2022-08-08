package com.zhihao.newretail.api.message.feign;

import com.zhihao.newretail.api.message.dto.DelayedMessageDTO;
import com.zhihao.newretail.api.message.dto.NotifyMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@FeignClient(name = "new-retail-message", path = "/message")
public interface MessageFeignService {

    /*
    * 发送延迟消息
    * */
    @PostMapping("/api/send/delayed")
    Long sendDelayedMessage(@RequestBody DelayedMessageDTO delayedMessageDTO);

    /*
    * 发送通知消息
    * */
    @PostMapping("/api/send/notify")
    Long sendNotifyMessage(@RequestBody NotifyMessageDTO notifyMessageDTO);

}
