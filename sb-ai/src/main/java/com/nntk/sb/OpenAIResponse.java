package com.nntk.sb;

import lombok.Data;

@Data
public class OpenAIResponse {
    private String id;
    private String object;
    private Long created;
    private String model;
    private Choice[] choices;
    private Usage usage;

    @Data
    public static class Choice {
        private String text;
        private Integer index;
        private Object logprobs;
        private String finish_reason;
        // 对于 chat completions
        private Message message;

        @Data
        public static class Message {
            private String role;
            private String content;
        }
    }

    @Data
    public static class Usage {
        private Integer prompt_tokens;
        private Integer completion_tokens;
        private Integer total_tokens;
    }
}