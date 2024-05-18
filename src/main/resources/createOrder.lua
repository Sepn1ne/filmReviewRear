-- 下单之后，扣减库存
-- csId
local csId = ARGV[1]
local userId = ARGV[2]

-- 拼接字符串
-- stockKey是某场电影票库存的key
local stockKey = 'film:screening:stock:' .. csId
-- userListKey是购买了某场放映电影的用户set
local userListKey = 'film:screening:order:' .. csId

-- 判断是否存在当前放映信息
if(tonumber(redis.call('exists', stockKey)) == 0) then
    -- 不存在当前放映信息
    return 1
end

-- 判断库存是否充足 get csKey
-- 需要将字符串转成数字，再进行判断
if(tonumber(redis.call('get', stockKey)) <= 0) then
    -- 库存不足，返回1
    return 2
end

-- 扣库存 incrby csId -1
redis.call('incrby',stockKey, -1)
redis.call('sadd', userListKey, userId)


return 0