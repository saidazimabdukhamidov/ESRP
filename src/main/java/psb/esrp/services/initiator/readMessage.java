package psb.esrp.services.initiator;
//PostMapping

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import psb.esrp.models.Application;
import psb.esrp.utils.DB;
import psb.esrp.utils.dateFormatter;

import javax.servlet.http.HttpServletRequest;
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
      conn = hds.getConnection(hds);
      ps = conn.prepareStatement("select * from APPLICATION");
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
        applications.setPhone_number(rs.getInt("phone_number"));
        applications.setVising_id(rs.getInt("visiting_id"));
        applications.setState_id(rs.getInt("state_id"));
        applications.setCreated_by(rs.getString("created_by"));
        applications.setCreated_on(rs.getDate("created_on"));
        applications.setRejection(rs.getString("rejection"));
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
    return "index";
  }
}


