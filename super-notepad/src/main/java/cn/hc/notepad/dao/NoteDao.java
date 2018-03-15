package cn.hc.notepad.dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.hc.notepad.bean.NoteBean;
import cn.hc.notepad.util.DBUtil;
public class NoteDao {
    public int myInsert(Connection con,String title,String content,String other,String otherkey) throws Exception    //通过测试
    {
        String sql="insert  into notetext values(null,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, content);
        ps.setString(3, other);
        ps.setString(4, otherkey);
        return ps.executeUpdate();
    }
    public NoteBean myQuarry(Connection con,int id) throws Exception        //测试通过
    {
        NoteBean notebean = new NoteBean();
        String sql ="select * from notetext where id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            notebean.setId(rs.getInt("id"));
            notebean.setTitle(rs.getString("title"));
            notebean.setContent(rs.getString("content"));
            notebean.setOther(rs.getString("other"));
            notebean.setOtherkey(rs.getString("otherkey"));
        }
        return notebean;
    }
    public int isTitleExit(Connection con,String title) throws SQLException  //通过测试
    {
        String sql= "select id from notetext where title=?";
        int resultcode=0;
        PreparedStatement ps =con.prepareStatement(sql);
        ps.setString(1, title);
        ResultSet rs =ps.executeQuery();
        while (rs.next()) {
            resultcode=rs.getInt("id");
        }
        //System.out.println("查询标题成功");
        return resultcode;
    }

    public int myModify(Connection con,String title,String content,String other,String otherkey,int id) throws SQLException
    {
        String sql ="update notetext set title=?,content=?,other=?,otherkey=? where id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, content);
        ps.setString(3, other);
        ps.setString(4, otherkey);
        ps.setInt(5, id);

        return ps.executeUpdate();
    }
    public int myUpdate(Connection con,int id,String title,String content,String other,String otherkey) throws SQLException
    {
        String sql="update notetext set title=?,content=?,other=?,otherkey=? where id=?";
        PreparedStatement ps =con.prepareStatement(sql);
        ps.setString(1,title);
        ps.setString(2, content);
        ps.setString(3, other);
        ps.setString(4, otherkey);
        ps.setInt(5, id);
        System.out.println("跟新成功");
        return ps.executeUpdate();
    }

    public int myDelete(Connection con,int id)
    {
        String sql="delete notetext where  id";

        return 1;
    }
    public ArrayList<String> quarryid(Connection con)throws SQLException              //通过测试
    {
        String sql ="select title from notetext";
        ArrayList<String> list= new ArrayList<String>();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs =ps.executeQuery();
        int i=1;
        while (rs.next()) {
            System.out.println(rs.getString(1)+" <>  "+i);
            list.add("标题--"+i+"  "+rs.getString(1));
            i++;
        }
        return list;
    }
    public static void main(String[] args) throws Exception {
        DBUtil dbUtil = new DBUtil();
        Connection con = dbUtil.con();
        NoteDao noteDao = new NoteDao();

        NoteBean noteBean=noteDao.myQuarry(con, 2);
        System.out.println(noteBean.getTitle());
//		try {
//			int a=noteDao.myUpdate(con, 2, "测试测试测试测试测试", "测试测试", "", "");
//			noteDao.myModify(con, "3333", "3333", "3333", "3333", 3);
//			//System.out.println(a);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

        //noteDao.myInsert(conh, "标题66666", "内容6666666", "未知66666", "未知2");  //通过
        //int resu=noteDao.isTitleExit(con, "标题");
        //System.out.println("文章在"+resu+"位置");
        //noteDao.quarryid(con);


        dbUtil.DBclose(con);
    }

}

