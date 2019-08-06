package psb.esrp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Core_Forms {
  private Integer form_id;
  private String form_name;
  private Integer app_id;
  private Integer order;
  private Integer state;
  private String url;
}
