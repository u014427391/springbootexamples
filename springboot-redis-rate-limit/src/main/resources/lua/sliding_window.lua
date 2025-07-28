-- 滑动窗口限流脚本（基于有序集合 ZSET）
-- KEYS[1]  : 限流 key
-- ARGV[1]  : 当前毫秒时间戳
-- ARGV[2]  : 窗口毫秒大小，例如 1000（1 秒）
-- ARGV[3]  : 窗口内最大请求数

-- 1. 计算窗口左边界：当前时间 - 窗口大小
local leftBound = tonumber(ARGV[1]) - tonumber(ARGV[2])

-- 2. 删除窗口外过期的时间戳（score < leftBound）
redis.call('ZREMRANGEBYSCORE', KEYS[1], 0, leftBound)

-- 3. 统计窗口内剩余元素数量
local current = redis.call('ZCARD', KEYS[1])

-- 4. 如果当前数量 < 阈值，则放行
if current < tonumber(ARGV[3]) then
    -- 把当前时间戳作为 score 和 member 写入
    redis.call('ZADD', KEYS[1], ARGV[1], ARGV[1])
    -- 设置兜底过期，防止冷 key 堆积
    redis.call('EXPIRE', KEYS[1], math.ceil(tonumber(ARGV[2]) / 1000))
    return 1
else
    return 0
end