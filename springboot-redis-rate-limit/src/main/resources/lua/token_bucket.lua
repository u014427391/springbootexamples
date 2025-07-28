-- 令牌桶限流脚本
-- KEYS[1]  : 业务前缀
-- ARGV[1]  : 每秒产生令牌数（rate）
-- ARGV[2]  : 桶容量（capacity）
-- ARGV[3]  : 当前毫秒时间戳
-- ARGV[4]  : 本次要取的令牌数（通常 1）

-- 拼装两个子 key：令牌数、上次更新时间
local kToken = KEYS[1] .. ':t'
local kTime  = KEYS[1] .. ':ts'

-- 解析参数
local rate   = tonumber(ARGV[1])
local cap    = tonumber(ARGV[2])
local now    = tonumber(ARGV[3])
local req    = tonumber(ARGV[4])

-- 取出上次剩余令牌（若不存在，默认桶满）
local tokens = tonumber(redis.call('GET', kToken)) or cap
-- 取出上次更新时间（若不存在，默认为现在）
local last   = tonumber(redis.call('GET', kTime))  or now

-- 计算这段时间应补充的令牌 = (now - last) * rate / 1000
tokens = math.min(cap, tokens + (now - last) * rate / 1000)

-- 令牌够就扣减，否则拒绝
if tokens < req then
    return 0
else
    tokens = tokens - req
    redis.call('SET', kToken, tokens)   -- 更新剩余令牌
    redis.call('SET', kTime, now)       -- 更新更新时间
    return 1
end