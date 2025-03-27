package com.amcamp.domain.chat.application;

import org.springframework.stereotype.Component;

@Component
public class PromptGenerator {
    // GOAL에는 얻으려는 결과를 작성합니다.
    // CONDITION에는 결과의 조건을 작성합니다.
    private static final String GOAL = "다음 줄에 기분을 입력할 거야. 그 기분에 맞는 음악 장르를 2개만 추천해줘.";
    private static final String CONDITION = "답변은 반드시 영어로 장르 이름만 출력해. 출력 형태는 장르를 쉼표(,)로 구분된 한 줄의 텍스트이고, 다른 설명은 포함하면 안돼.";
    private static final String SYSTEM_PROMPT = GOAL + CONDITION;

    public String generatePrompt(String input) {
        return SYSTEM_PROMPT + System.lineSeparator() + input;
    }
}
