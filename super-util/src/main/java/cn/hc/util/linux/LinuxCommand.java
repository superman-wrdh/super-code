package cn.hc.util.linux;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * SSH工具类
 * @author 何超
 * 2017-5-23
 */
public class LinuxCommand {
    String backupShell="";

    public LinuxCommand(){
        //自动备份脚本内容 当/root/backupDB.sh不存在时请使用当前脚本或者将当前脚本复制到指定位置
        backupShell="mysqlhost=\"rm-bp1wyy921u8g922sy.mysql.rds.aliyuncs.com\"\n" +
                "backupDB=\"xilong\"\n" +
                "sqlfileName=$backupDB\"-\"$(date +%Y%m%d%H%M)\".sql\"\n" +
                "nowDate=\"$(date +%Y%m%d%H%M)\"\n" +
                "mysqldump -h rm-bp1wyy921u8g922sy.mysql.rds.aliyuncs.com -uxilong -pxilong@2017 $backupDB >/opt/backup/$sqlfileName";
    }
    /**
     * 远程 执行命令并返回结果调用过程 是同步的（执行完才会返回）
     * @param host	主机名
     * @param user	用户名
     * @param psw	密码
     * @param port	端口
     * @param command	命令
     * return -1 success
     */
    public static Map<String,Object> exec(String host,String user,String psw,int port,String command,String spitCom){
        Map<String,Object> resultMap=new HashMap<>();
        String result="";
        Session session =null;
        ChannelExec openChannel =null;
        try {
            JSch jsch=new JSch();
            session = jsch.getSession(user, host, port);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(psw);
            session.connect();
            openChannel = (ChannelExec) session.openChannel("exec");
            openChannel.setCommand(command);
            int exitStatus = openChannel.getExitStatus();
            resultMap.put("status",exitStatus);
            //System.out.println(exitStatus);
            openChannel.connect();
            InputStream in = openChannel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String buf = null;
            while ((buf = reader.readLine()) != null) {
                //result+= new String(buf.getBytes("gbk"),"UTF-8")+"    <br />rn";
                if("".equals(spitCom) || spitCom==null)
                    spitCom=" ";
                result += new String(buf.getBytes("gbk"),"UTF-8")+spitCom;
            }
            //System.out.println(":"+result);
        } catch (JSchException | IOException e) {
            result+=e.getMessage();
            System.out.println("exception:"+result);
        }finally{
            if(openChannel!=null&&!openChannel.isClosed()){
                openChannel.disconnect();
            }
            if(session!=null&&session.isConnected()){
                session.disconnect();
            }
        }
        resultMap.put("result",result);
        return resultMap;
    }

    /**
     * 备份数据库
     */
    public Map<String,Object> backupDB(){
        Map<String, Object> map = exec("118.178.85.185", "root", "xilong@2017", 22, "sh /root/backupDB.sh", " ");
        return map;
    }

    /**
     *备份全部 数据库加web程序和资源
     */
    public Map<String,Object> backupALL(){
        Map<String, Object> map = exec("118.178.85.185", "root", "xilong@2017", 22, "sh /root/backupALL.sh", " ");
        return map;
    }

}
