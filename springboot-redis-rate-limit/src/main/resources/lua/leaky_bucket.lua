-- 漏桶限流脚本
-- KEYS[1]  : 业务前缀
-- ARGV[1]  : 桶容量（capacity）
-- ARGV[2]  : 每毫秒流出量（rate）
-- ARGV[3]  : 当前毫秒时间戳

-- 拼装两个子 key：桶内水量、上次漏水时间
local kWater = KEYS[1] .. ':w'
local kTime  = KEYS[1] .. ':ts'

-- 解析参数
local cap   = tonumber(ARGV[1])
local rate  = tonumber(ARGV[2])
local now   = tonumber(ARGV[3])

-- 取出当前水量（若不存在，默认为 0）
local level = tonumber(redis.call('GET', kWater)) or 0
-- 取出上次漏水时间（若不存在，默认为现在）
local last  = tonumber(redis.call('GET', kTime))  or now

-- 先漏水：这段时间漏掉的水量 = (now - last) * rate
level = math.max(0, level - (now - last) * rate)

-- 再加水：如果加 1 后超过容量，则拒绝
if level + 1 > cap then
    return 0
else
    level = level + 1
    redis.call('SET', kWater, level)  -- 更新水量
    redis.call('SET', kTime, now)     -- 更新时间
    return 1
end