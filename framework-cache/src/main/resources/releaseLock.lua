--
-- Created by IntelliJ IDEA.
-- User: lindj
-- Date: 2018/11/27
-- Time: 10:09
-- To change this template use File | Settings | File Templates.
--
if redis.call('get', KEYS[1]) == ARGV[1] then
    return redis.call('del', KEYS[1])
else
    return 0
end

