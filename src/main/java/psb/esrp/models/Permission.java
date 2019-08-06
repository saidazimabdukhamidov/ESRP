package psb.esrp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
  private Integer permission_id;
  private String visitor_name;
  private String visitor_info;
  private Integer dep_id;
  private Integer cabinet_num;
  private String visit_begin_time;
  private String visit_end_time;
}
