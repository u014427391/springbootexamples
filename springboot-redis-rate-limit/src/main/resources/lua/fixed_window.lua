-- 固定窗口限流脚本
-- KEYS[1]  : 限流业务 key，如 "rate:login:192.168.1.1"
-- ARGV[1]  : 窗口大小（秒），例如 1 秒
-- ARGV[2]  : 该窗口内的最大请求数，例如 5

-- 把 key 的计数器 +1，并返回当前值
local c = redis.call('INCR', KEYS[1])

-- 如果是第一次出现（c==1），则给 key 设置过期时间
-- 过期后自动清零，实现“固定窗口”重新开始
if c == 1 then
    redis.call('EXPIRE', KEYS[1], ARGV[1])
end

-- 如果当前累计值 <= 阈值，返回 1（放行），否则返回 0（拒绝）
return c <= tonumber(ARGV[2])