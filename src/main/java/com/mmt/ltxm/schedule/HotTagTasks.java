package com.mmt.ltxm.schedule;

import com.mmt.ltxm.cache.HotTagCache;
import com.mmt.ltxm.mapper.QuestionMapper;
import com.mmt.ltxm.model.Question;
import com.mmt.ltxm.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HotTagTasks {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void reportCurrentTime() {
        Map<String, Integer> priorities = new HashMap<>();
        List<Question> questions = questionMapper.selectByExample(new QuestionExample());

        for (Question question : questions) {
            String[] tags = StringUtils.split(question.getTag(), ",");
            for (String tag : tags) {
                Integer priority = priorities.get(tag);
                if (priority != null) {
                    priorities.put(tag, priority + 5 + question.getCommentCount());
                } else {
                    priorities.put(tag, 5 + question.getCommentCount());
                }
            }
        }

        hotTagCache.setTagMap(priorities);
        hotTagCache.getTagMap().forEach(
                (k, v) -> {
                    System.out.print(k);
                    System.out.print(":");
                    System.out.print(v);
                    System.out.println();
                }
        );
        log.info("hotTag{}", new Date());
        hotTagCache.updateTags(priorities);
    }
}
