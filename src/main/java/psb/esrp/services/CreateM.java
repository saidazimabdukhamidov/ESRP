package psb.esrp.services;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import psb.esrp.models.Core_Departments;
import psb.esrp.models.Core_Users;
import psb.esrp.models.Permission_type;
import psb.esrp.utils.DB;

import javax.servlet.http.HttpServletRequest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//@Repository

@Service
@Controller
public class CreateM {
    @Autowired
    HikariDataSource hds;
    Connection conn=null;
    Connection conn1=null;
    PreparedStatement ps=null;
    PreparedStatement ps1=null;
    CallableStatement cs=null;
    ResultSet rs=null;
    ResultSet rs1=null;

    @GetMapping("/add_message")
    public String get(Model model){
        ArrayList<Permission_type> perm = new ArrayList<>();
        ArrayList<Core_Departments> department =new ArrayList<>();
        ArrayList<Core_Users> users = new ArrayList<>();
        try{
            conn = hds.getConnection();
            ps=conn.prepareStatement("SELECT * from ESRP.PERMISSION_TYPE" );
            ps.execute();
            rs=ps.getResultSet();
            while(rs.next()){
                Permission_type permission = new Permission_type();
                permission.setPerm_id(rs.getInt("perm_id"));
                permission.setPerm_name(rs.getString("perm_name"));
                perm.add(permission);
            }
            model.addAttribute("Permission_type", perm);

            ps = conn.prepareStatement("Select * from ESRP.CORE_DEPARTMENTS");
            ps.execute();
            rs=ps.getResultSet();
            while(rs.next())
            {
                Core_Departments dep = new Core_Departments();
                dep.setDepartment_id(rs.getInt("department_id"));
                dep.setDep_name(rs.getString("dep_name"));
                department.add(dep);
            }
            model.addAttribute("Core_Departments", department);


            conn1=hds.getConnection();
            ps1=conn1.prepareStatement("Select user_id, full_name from ESRP.CORE_USERS");
            ps1.execute();
            rs1=ps1.getResultSet();
            while(rs1.next()){
                Core_Users user = new Core_Users();
                user.setUser_id(rs1.getInt("user_id"));
                user.setFull_name(rs1.getString("full_name"));
                users.add(user);

            }
            model.addAttribute("Core_Users", users);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    return "add_message";
    }

    @PostMapping("/add_message")
    public String createMessage(HttpServletRequest request) {

        try {
            String visitor_name = request.getParameter("visitor_name");
            String visitor_info = request.getParameter("visitor_info");
            Integer type_id = Integer.parseInt(request.getParameter("perm_name"));
            Integer department_id=Integer.parseInt(request.getParameter("department_name"));
            Integer cabinet_number = Integer.parseInt(request.getParameter("cabinet_number"));
            String begin_time = request.getParameter("begin_time");
            String end_time = request.getParameter("end_time");
            Integer phone_number = Integer.parseInt(request.getParameter("phone_number"));
            Integer user_id=Integer.parseInt(request.getParameter("full_name"));

            conn = hds.getConnection();
            cs = conn.prepareCall("{call ESRP.CORE_PKG.insertApp(?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, visitor_name);
            cs.setString(2, visitor_info);
            cs.setInt(3, type_id);
            cs.setInt(4,department_id);
            cs.setInt(5, cabinet_number);
            cs.setString(6, begin_time);
            cs.setString(7, end_time);
            cs.setInt(8, phone_number);
            cs.setInt(9, user_id);
            int result = cs.executeUpdate();

            if (result>0) {
                return "redirect:/read?msg=success";
            }else
                return "redirect:/add_message?msg=unsuccess";

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.done(conn1);
            DB.done(ps);
            DB.done(rs);
        }

        return "redirect:/read?msg=success";
    }

}
