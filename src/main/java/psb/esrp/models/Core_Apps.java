package psb.esrp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Core_Apps {
  private Integer app_id;
  private String app_name;
  private String state;
}
