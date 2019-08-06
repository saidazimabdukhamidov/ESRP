package psb.esrp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Core_Roles {
  private Integer role_id;
  private String role_name;
  private String state;
  private Integer app_id;
}
