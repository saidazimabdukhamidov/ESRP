package psb.esrp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Application {
  private Integer application_id;
  private String visitor_name;
  private String visitor_info;
  private Integer department_id;
  private Integer cabinet_number;
  private String begin_time;
  private String end_time;
  private String phone_number;
  private Integer user_id;
  private Integer state_id;
  private String created_by;
  private Date created_on;
  private String rejection;
  private Integer type_id;
  private String object_name;

}
