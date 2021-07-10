package cn.cerc.mis.task;

import cn.cerc.core.TDateTime;
import cn.cerc.db.mysql.MysqlServerMaster;

/**
 * 清理在线用户记录表
 */
public class TaskTrackCurrentUser extends AbstractTask {
    public static final int FOREVER_VIABILITY = 999999;

    @Override
    public void execute() {
        // 清理在线用户记录表
        MysqlServerMaster conn = this.getMysql();

        // 删除超过100天的登录记录
        conn.execute(String.format("delete from %s where datediff(now(),LoginTime_)>100 and Viability_<>%s", 
                systemTable.getCurrentUser(), FOREVER_VIABILITY));

        // 清除所有未正常登录的用户记录
        StringBuilder sql2 = new StringBuilder();
        sql2.append(String.format("update %s set Viability_=-1,LogoutTime_='%s' ", systemTable.getCurrentUser(),
                TDateTime.now()));

        // 在线达24小时以上的用户
        sql2.append(String.format("where (Viability_>0 and Viability_<>%s) and (", FOREVER_VIABILITY));
        sql2.append("(hour(timediff(now(),LoginTime_)) > 24 and LogoutTime_ is null)");

        // 在早上5点以后，清除昨天的用户
        if (TDateTime.now().getHours() > 5) {
            sql2.append(" or (datediff(now(),LoginTime_)=1)");
        }

        // 已登出超过4小时的用户
        sql2.append(" or (hour(timediff(now(),LogoutTime_)) > 4)");
        sql2.append(")");
        conn.execute(sql2.toString());
    }

}
