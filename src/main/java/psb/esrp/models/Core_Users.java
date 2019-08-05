package psb.esrp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Core_Users {
  private Integer user_id;
  private String full_name;
  private Integer phone_number;
  private String username;
  private Integer password;
  private Integer status;
}
