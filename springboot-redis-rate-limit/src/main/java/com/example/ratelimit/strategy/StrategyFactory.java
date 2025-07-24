package com.example.ratelimit.strategy;

import com.example.ratelimit.core.RateLimitType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class StrategyFactory {

    private final Map<RateLimitType, AbstractRateLimitStrategy> map = new EnumMap<>(RateLimitType.class);

    public StrategyFactory(List<AbstractRateLimitStrategy> strategies) {
        strategies.forEach(bean -> {
            String enumName = bean.getClass().getSimpleName()
                    .replace("Strategy", "")
                    .toUpperCase();
            map.put(RateLimitType.valueOf(enumName), bean);
        });
    }

    public AbstractRateLimitStrategy get(RateLimitType type) {
        return Optional.ofNullable(map.get(type)).orElseThrow(() -> new IllegalArgumentException("未找到策略" + type));
    }
}
