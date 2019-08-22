package psb.esrp.services;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import psb.esrp.models.Application;
import psb.esrp.utils.DB;
import psb.esrp.utils.dateFormatter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Controller
public class readMessage {
  @Autowired
  HikariDataSource hds;
  Connection conn = null;
  PreparedStatement  ps = null;
  CallableStatement cs = null;
  ResultSet rs = null;
  dateFormatter date;

  @GetMapping("/read")
  public String show(Model model) {
    ArrayList<Application> application = new ArrayList<>();
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("select application_id, " +
              "visitor_name, visitor_info, department_id, " +
              "cabinet_number, begin_time, end_time, phone_number, " +
              "user_id, type_id, object_name from ESRP.APPLICATION");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Application applications = new Application();
        applications.setApplication_id(rs.getInt("application_id"));
        applications.setVisitor_name(rs.getString("visitor_name"));
        applications.setVisitor_info(rs.getString("visitor_info"));
        applications.setDepartment_id(rs.getInt("department_id"));
        applications.setCabinet_number(rs.getInt("cabinet_number"));
        applications.setBegin_time(rs.getString("begin_time"));
        applications.setEnd_time(rs.getString("end_time"));
        applications.setPhone_number(rs.getString("phone_number"));
        applications.setUser_id(rs.getInt("user_id"));
        applications.setType_id(rs.getInt("type_id"));
        applications.setObject_name(rs.getString("object_name"));
        application.add(applications);
      }
      model.addAttribute("applications", application);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DB.done(conn);
      DB.done(ps);
      DB.done(rs);
    }
    return "show_message";
  }
}
