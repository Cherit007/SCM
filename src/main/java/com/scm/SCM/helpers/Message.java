package com.scm.SCM.helpers;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class Message {
    private String content;
    @Builder.Default
    private MessageType type = MessageType.valueOf("green");
}
