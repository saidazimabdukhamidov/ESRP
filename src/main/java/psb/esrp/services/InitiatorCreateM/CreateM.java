package psb.esrp.services.InitiatorCreateM;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import psb.esrp.models.Core_Departments;
import psb.esrp.models.Core_Users;
import psb.esrp.utils.dataBase;

import javax.servlet.http.HttpServletRequest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Service
@Controller
public class CreateM {
  @Autowired
  HikariDataSource hds;
  Connection conn = null;
  Connection conn1 = null;
  PreparedStatement ps = null;
  PreparedStatement ps1 = null;
  CallableStatement cs = null;
  ResultSet rs = null;
  ResultSet rs1 = null;

  @GetMapping("/add_message")
  public String get(Model model) {
    ArrayList<Core_Departments> department = new ArrayList<>();
    ArrayList<Core_Users> users = new ArrayList<>();
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM ESRP.CORE_DEPARTMENTS");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Core_Departments dep = new Core_Departments();
        dep.setDepartment_id(rs.getInt("department_id"));
        dep.setDep_name(rs.getString("dep_name"));
        department.add(dep);
      }
      model.addAttribute("Core_Departments", department);


      conn1 = hds.getConnection();
      ps1 = conn1.prepareStatement("SELECT USER_ID, FULL_NAME FROM ESRP.CORE_USERS");
      ps1.execute();
      rs1 = ps1.getResultSet();
      while (rs1.next()) {
        Core_Users user = new Core_Users();
        user.setUser_id(rs1.getInt("user_id"));
        user.setFull_name(rs1.getString("full_name"));
        users.add(user);
        model.addAttribute(user);
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      dataBase.done(conn);
      dataBase.done(ps);
      dataBase.done(rs);
    }
    return "add_message";
  }

  @PostMapping("/add_message")
  public String createMessage(HttpServletRequest request) {
    try {
      String visitor_name = request.getParameter("visitor_name");
      String visitor_info = request.getParameter("visitor_info");
      String pass_type = request.getParameter("type_id");
      Integer department_id = Integer.parseInt(request.getParameter("department_name"));
      Integer cabinet_number = Integer.parseInt(request.getParameter("cabinet_number"));
      String begin_time = request.getParameter("begin_time");
      String end_time = request.getParameter("end_time");
      Integer phone_number = Integer.parseInt(request.getParameter("phone_number"));
      Integer vising_id = Integer.parseInt(request.getParameter("full_name"));
      conn = hds.getConnection();
      cs = conn.prepareCall("{call core_pck.application(?,?,?,?,?,?,?,?,?)}");
      cs.setString(1, visitor_name);
      cs.setString(2, visitor_info);
      cs.setString(3, pass_type);
      cs.setInt(4, department_id);
      cs.setInt(5, cabinet_number);
      cs.setString(6, begin_time);
      cs.setString(7, end_time);
      cs.setInt(8, phone_number);
      cs.setInt(9, vising_id);
      int result = cs.executeUpdate();

      if (result > 0) {
        return "redirect:/next?msg=success";
      } else
        return "redirect:/add_message?msg=unsuccess";

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataBase.done(conn1);
      dataBase.done(ps);
      dataBase.done(rs);
    }

    return "redirect:/next?msg=success";
  }

}
